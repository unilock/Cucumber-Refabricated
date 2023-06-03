package com.alex.cucumber.inventory.slot;

import com.alex.cucumber.forge.items.SlotItemHandler;
import com.alex.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BaseItemStackHandlerSlot extends SlotItemHandler {
    private final BaseItemStackHandler inventory;
    private final int index;

    public BaseItemStackHandlerSlot(BaseItemStackHandler inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.inventory = inventory;
        this.index = index;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return inventory.canPlaceItem(index, stack);
    }

    @Override
    public boolean mayPickup(Player player) {
        this.inventory.setSimulate(true);
        return !this.inventory.extractItem(this.index, 1, true).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        this.inventory.setSimulate(false);
        return this.inventory.extractItem(this.index, amount, true);
    }
}
