package com.alex.cucumber.item;

import com.alex.cucumber.iface.Enableable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

public class BaseBlockItem extends BlockItem {
    public BaseBlockItem(Block block) {
        super(block, new Properties());
    }

    public BaseBlockItem(Block block, Function<Properties, Properties> properties) {
        super(block, properties.apply(new Properties()));
    }
}
