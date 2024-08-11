package com.alex.cucumber.forge.client;

import com.alex.cucumber.helper.RecipeHelper;
import com.alex.cucumber.iface.CustomBow;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;

public class ForgeHooksClient {
    public static float getFieldOfViewModifier(Player entity, float fovModifier)
    {
        var stack = entity.getUseItem();

        if (!stack.isEmpty()) {
            var item = stack.getItem();
            if (item instanceof CustomBow bow && bow.hasFOVChange()) {
                float f = Mth.clamp((stack.getUseDuration() - entity.getUseItemRemainingTicks()) * bow.getDrawSpeedMulti(stack) / 20.0F, 0, 1.0F);

                fovModifier -= f * f * 0.15F;
            }
        }

        return fovModifier;
    }

    public static void onRecipesUpdated(RecipeManager mgr)
    {
        RecipeHelper.recipeManager = mgr;
    }
}
