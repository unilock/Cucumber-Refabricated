package com.alex.cucumber.inventory;

import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.BiFunction;

public class SidedItemStackHandler extends BaseItemStackHandler {

    Direction direction;
    TriFunction<Integer, ItemStack, Direction, Boolean> canInsert;
    BiFunction<Integer, Direction, Boolean> canExtract;

    public SidedItemStackHandler(BaseItemStackHandler inventory, Direction direction, TriFunction<Integer, ItemStack, Direction, Boolean> canInsert, BiFunction<Integer, Direction, Boolean> canExtract) {
        super(inventory.getContainerSize(), inventory.getOnContentsChanged());
        this.stacks = inventory.getStacks();
        this.direction = direction;
        this.canInsert = canInsert;
        this.canExtract = canExtract;

        this.outputSlots = inventory.getOutputSlots();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static LazyOptional<SidedItemStackHandler>[] create(BaseItemStackHandler inventory, Direction[] sides, TriFunction<Integer, ItemStack, Direction, Boolean> canInsert, BiFunction<Integer, Direction, Boolean> canExtract) {
        LazyOptional[] ret = new LazyOptional[sides.length];

        for(int x = 0; x < sides.length; ++x) {
            Direction side = sides[x];
            ret[x] = LazyOptional.of(() -> {
                return new SidedItemStackHandler(inventory, side, canInsert, canExtract);
            });
        }

        return ret;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            return !this.canPlaceItem(slot, stack) ? stack : super.insertItem(slot, stack, simulate);
        }
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return this.canExtract != null && !(Boolean)this.canExtract.apply(slot, this.direction) ? ItemStack.EMPTY : super.extractItem(slot, amount, simulate);
    }

    public Direction direction() {
        return this.direction;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return this.canInsert == null || (Boolean)this.canInsert.apply(slot, stack, this.direction);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return (canExtract == null || canExtract.apply(slot, dir)) && (outputSlots == null || ArrayUtils.contains(outputSlots, slot));
    }
}
