package com.alex.cucumber.init;

import com.alex.cucumber.crafting.recipe.ShapedNoMirrorRecipe;
import com.alex.cucumber.crafting.recipe.ShapedTagRecipe;
import com.alex.cucumber.crafting.recipe.ShapedTransferDamageRecipe;
import com.alex.cucumber.crafting.recipe.ShapelessTagRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final RecipeSerializer<?> CRAFTING_SHAPED_NO_MIRROR = new ShapedNoMirrorRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TRANSFER_DAMAGE = new ShapedTransferDamageRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPED_TAG = new ShapedNoMirrorRecipe.Serializer();
    public static final RecipeSerializer<?> CRAFTING_SHAPELESS_TAG = new ShapelessTagRecipe.Serializer();

    public static void registerRecipeSerializers() {
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:shaped_no_mirror"), CRAFTING_SHAPED_NO_MIRROR);
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:shaped_transfer_damage"), CRAFTING_SHAPED_TRANSFER_DAMAGE);
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:shaped_tag"), CRAFTING_SHAPED_TAG);
        Registry.register(Registry.RECIPE_SERIALIZER, new ResourceLocation("mysticalagriculture:shapeless_tag"), CRAFTING_SHAPELESS_TAG);
    }
}
