package com.alex.cucumber.item;

import com.alex.cucumber.iface.Enableable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class BaseItem extends Item {
    public BaseItem() {
        super(new Properties());
    }

    public BaseItem(Function<Properties, Properties> properties) {
        super(properties.apply(new Properties()));
    }
}