package com.alex.cucumber.forge.items;

import com.alex.cucumber.inventory.BaseItemStackHandler;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SlotItemHandler extends Slot {

    private static Container emptyInventory = new SimpleContainer(0);
    private final BaseItemStackHandler itemHandler;
    private final int index;

    public SlotItemHandler(BaseItemStackHandler itemHandler, int index, int x, int y) {
        super(emptyInventory, index, x, y);
        this.itemHandler = itemHandler;
        this.index = index;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        if (stack.isEmpty())
            return false;
        return itemHandler.canPlaceItem(index, stack);
    }

    @Override
    public ItemStack getItem() {
        return this.getItemHandler().getItem(index);
    }

    @Override
    public void set(ItemStack stack) {
        this.getItemHandler().setStackInSlot(index, stack);
        this.setChanged();
    }

    /*@Override
    public void initialize(ItemStack stack) {
        this.getItemHandler().setStackInSlot(index, stack);
        this.setChanged();
    }*/

    @Override
    public void onQuickCraft(ItemStack newItem, ItemStack original) {
    }

    @Override
    public int getMaxStackSize() {
        return this.itemHandler.getSlotLimit(this.index);
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);

        BaseItemStackHandler handler = this.getItemHandler();
        ItemStack currentStack = handler.getItem(index);
        handler.setStackInSlot(index, ItemStack.EMPTY);

        handler.setSimulate(true);
        ItemStack remainder = handler.insertItem(index, maxAdd);

        handler.setStackInSlot(index, currentStack);

        return maxInput - remainder.getCount();
    }

    @Override
    public boolean mayPickup(Player player) {
        this.getItemHandler().setSimulate(true);
        return !this.getItemHandler().extractItem(index, 1).isEmpty();
    }

    @Override
    public ItemStack remove(int amount) {
        return this.getItemHandler().extractItem(index, amount);
    }

    public BaseItemStackHandler getItemHandler() {
        return itemHandler;
    }
}
