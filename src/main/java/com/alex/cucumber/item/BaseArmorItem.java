package com.alex.cucumber.item;

import com.alex.cucumber.iface.Enableable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class BaseArmorItem extends ArmorItem {
    public BaseArmorItem(ArmorMaterial material, Type type) {
        super(material, type, new Properties());
    }

    public BaseArmorItem(ArmorMaterial material, Type type, Function<Properties, Properties> properties) {
        super(material, type, properties.apply(new Properties()));
    }
}
