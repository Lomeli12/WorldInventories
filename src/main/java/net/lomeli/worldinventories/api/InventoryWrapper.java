package net.lomeli.worldinventories.api;

import net.minecraft.nbt.CompoundNBT;

public abstract class InventoryWrapper<T> {
    private T inventory;

    public InventoryWrapper(T inventory) {
        this.inventory = inventory;
    }

    public abstract T getInventory();

    public abstract CompoundNBT inventoryToNBT();

    public abstract void inventoryFromNBT(CompoundNBT nbt);
}
