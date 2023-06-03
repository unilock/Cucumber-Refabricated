package com.alex.cucumber.forge.common.extensions;

import com.alex.cucumber.forge.common.ForgeHooks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public interface ForgeBlock {
    private Block self()
    {
        return (Block) this;
    }

    static boolean canHarvestBlock(BlockState state, BlockGetter world, BlockPos pos, Player player)
    {
        return ForgeHooks.isCorrectToolForDrops(state, player);
    }

    static boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid)
    {
        state.getBlock().playerWillDestroy(level, pos, state, player);
        return level.setBlock(pos, fluid.createLegacyBlock(), level.isClientSide ? 11 : 3);
    }
}
