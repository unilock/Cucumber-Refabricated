package com.alex.cucumber.init;

import com.alex.cucumber.crafting.recipe.ShapedNoMirrorRecipe;
import com.alex.cucumber.crafting.recipe.ShapedTagRecipe;
import com.alex.cucumber.crafting.recipe.ShapedTransferDamageRecipe;
import com.alex.cucumber.crafting.recipe.ShapelessTagRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<?> CRAFTING_SHAPED_NO_MIRROR = new ShapedNoMirrorRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TRANSFER_DAMAGE = new ShapedTransferDamageRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TAG = new ShapedTagRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPELESS_TAG = new ShapelessTagRecipe.Serializer();

    public static void registerModRecipeSerializers() {
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("cucumber:shaped_no_mirror"), CRAFTING_SHAPED_NO_MIRROR);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("cucumber:shaped_transfer_damage"), CRAFTING_SHAPED_TRANSFER_DAMAGE);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("cucumber:shaped_tag"), CRAFTING_SHAPED_TAG);
        Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, new ResourceLocation("cucumber:shapeless_tag"), CRAFTING_SHAPELESS_TAG);
    }
}
