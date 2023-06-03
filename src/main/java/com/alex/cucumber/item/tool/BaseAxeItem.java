package com.alex.cucumber.item.tool;

import com.alex.cucumber.iface.Enableable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;

import java.util.function.Function;

public class BaseAxeItem extends AxeItem {
    private final float attackDamage;
    private final float attackSpeed;

    public BaseAxeItem(Tier tier, Function<Item.Properties, Item.Properties> properties) {
        this(tier, 6.0F, -3.0F, properties);
    }

    public BaseAxeItem(Tier tier, float attackDamage, float attackSpeed, Function<Item.Properties, Item.Properties> properties) {
        super(tier, attackDamage, attackSpeed, properties.apply(new Item.Properties()));
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
        if (this instanceof Enableable enableable) {
            if (enableable.isEnabled())
                super.fillItemCategory(group, items);
        } else {
            super.fillItemCategory(group, items);
        }
    }

    public float getAttackDamage() {
        return this.attackDamage + this.getTier().getAttackDamageBonus();
    }

    public float getAttackSpeed() {
        return this.attackSpeed;
    }
}
