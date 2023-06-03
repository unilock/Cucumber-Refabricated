package com.alex.cucumber.forge.common.extensions;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ForgeItem {
    private Item self()
    {
        return (Item) this;
    }

    default boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player)
    {
        return false;
    }

    default boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity)
    {
        return false;
    }
}
