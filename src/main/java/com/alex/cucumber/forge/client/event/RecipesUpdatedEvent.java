package com.alex.cucumber.forge.client.event;

import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.ApiStatus;

public class RecipesUpdatedEvent {
    private final RecipeManager recipeManager;

    @ApiStatus.Internal
    public RecipesUpdatedEvent(RecipeManager recipeManager)
    {
        this.recipeManager = recipeManager;
    }

    /**
     * {@return the recipe manager}
     */
    public RecipeManager getRecipeManager()
    {
        return recipeManager;
    }
}
