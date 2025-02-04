package com.alex.cucumber.crafting.recipe;

import com.alex.cucumber.init.ModRecipeSerializers;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ShapedTransferDamageRecipe extends ShapedRecipe {
    private final ItemStack result;
    private final boolean transferNBT;

    public ShapedTransferDamageRecipe(ResourceLocation id, String group, CraftingBookCategory category, int width, int height, NonNullList<Ingredient> inputs, ItemStack result, boolean showNotification, boolean transferNBT) {
        super(id, group, category, width, height, inputs, result, showNotification);
        this.result = result;
        this.transferNBT = transferNBT;
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        var damageable = ItemStack.EMPTY;

        for (var i = 0; i < inv.getContainerSize(); i++) {
            var slotStack = inv.getItem(i);

            if (slotStack.isDamageableItem()) {
                damageable = slotStack;
                break;
            }
        }

        if (damageable.isEmpty())
            return super.assemble(inv, access);

        var result = this.getResultItem(access).copy();

        if (this.transferNBT) {
            var tag = damageable.getTag();

            if (tag != null) {
                result.setTag(tag.copy());
            }
        } else {
            result.setDamageValue(damageable.getDamageValue());
        }

        return result;
    }

    public static int firstNonSpace(String string) {
        int i;
        for (i = 0; i < string.length() && string.charAt(i) == ' '; ++i) {
        }
        return i;
    }

    public static int lastNonSpace(String string) {
        int i;
        for (i = string.length() - 1; i >= 0 && string.charAt(i) == ' '; --i) {
        }
        return i;
    }

    public static String[] shrink(String ... strings) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;
        for (int m = 0; m < strings.length; ++m) {
            String string = strings[m];
            i = Math.min(i, firstNonSpace(string));
            int n = lastNonSpace(string);
            j = Math.max(j, n);
            if (n < 0) {
                if (k == m) {
                    ++k;
                }
                ++l;
                continue;
            }
            l = 0;
        }
        if (strings.length == l) {
            return new String[0];
        }
        String[] strings2 = new String[strings.length - l - k];
        for (int o = 0; o < strings2.length; ++o) {
            strings2[o] = strings[o + k].substring(i, j + 1);
        }
        return strings2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRAFTING_SHAPED_TRANSFER_DAMAGE;
    }

    public static class Serializer implements RecipeSerializer<ShapedTransferDamageRecipe> {
        @Override
        public ShapedTransferDamageRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            var group = GsonHelper.getAsString(json, "group", "");
            var category = CraftingBookCategory.CODEC.byName(GsonHelper.getAsString(json, "category", null), CraftingBookCategory.MISC);
            var key = ShapedRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            var pattern = shrink(ShapedRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            var width = pattern[0].length();
            var height = pattern.length;
            var ingredients = ShapedRecipe.dissolvePattern(pattern, key, width, height);
            var result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            var showNotification = GsonHelper.getAsBoolean(json, "show_notification", true);
            var transferNBT = GsonHelper.getAsBoolean(json, "transfer_nbt", false);

            return new ShapedTransferDamageRecipe(recipeId, group, category, width, height, ingredients, result, showNotification, transferNBT);
        }

        @Override
        public ShapedTransferDamageRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            var group = buffer.readUtf(32767);
            var category = buffer.readEnum(CraftingBookCategory.class);
            var width = buffer.readVarInt();
            var height = buffer.readVarInt();
            var ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);

            for (var k = 0; k < ingredients.size(); ++k) {
                ingredients.set(k, Ingredient.fromNetwork(buffer));
            }

            var result = buffer.readItem();
            var showNotification = buffer.readBoolean();
            var transferNBT = buffer.readBoolean();

            return new ShapedTransferDamageRecipe(recipeId, group, category, width, height, ingredients, result, showNotification, transferNBT);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ShapedTransferDamageRecipe recipe) {
            buffer.writeUtf(recipe.getGroup());
            buffer.writeEnum(recipe.category());
            buffer.writeVarInt(recipe.getWidth());
            buffer.writeVarInt(recipe.getHeight());

            for (var ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.result);
            buffer.writeBoolean(recipe.showNotification());
            buffer.writeBoolean(recipe.transferNBT);
        }
    }
}