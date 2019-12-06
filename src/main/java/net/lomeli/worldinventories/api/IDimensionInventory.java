package net.lomeli.worldinventories.api;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;

public interface IDimensionInventory {
    void addInventoryNBT(ResourceLocation inventoryID, INBT inventoryTag);
    INBT getInventoryNBT(ResourceLocation inventoryID);
    ResourceLocation getDimensionID();
    void clear();
    boolean isEmpty();
    CompoundNBT toNBT();
    void fromNBT(CompoundNBT nbt);
}
