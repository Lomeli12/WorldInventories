package net.lomeli.worldinventories.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;

public interface IPlayerDimInv {
    void addInventory(ResourceLocation dimID, ResourceLocation invID, INBT inventoryTag);

    DimInventory getDimInventories(ResourceLocation dimID);

    void deserialize(CompoundNBT nbt);

    CompoundNBT serialize();

}
