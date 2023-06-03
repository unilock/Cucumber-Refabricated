package com.alex.cucumber.iface;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public interface Colored {
    default int getColor(int index) {
        return -1;
    }

    default int getColor(int index, ItemStack stack) {
        return this.getColor(index);
    }

    class BlockColors implements BlockColor {
        @Override
        public int getColor(BlockState state, BlockAndTintGetter level, BlockPos pos, int index) {
            return ((Colored) state.getBlock()).getColor(index);
        }
    }

    class ItemColors implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int index) {
            return ((Colored) stack.getItem()).getColor(index, stack);
        }
    }

    class ItemBlockColors implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int index) {
            return ((Colored) Block.byItem(stack.getItem())).getColor(index, stack);
        }
    }
}
