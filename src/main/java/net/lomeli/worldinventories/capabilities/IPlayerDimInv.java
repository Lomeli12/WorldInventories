package net.lomeli.worldinventories.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.dimension.DimensionType;

public interface IPlayerDimInv {
    boolean addInventory(CompoundNBT inventoryTag, DimensionType dimType);

    CompoundNBT[] getDimInventories(DimensionType dimType);

    CompoundNBT[] getAllInventories();

    void deserialize(CompoundNBT nbt);

    CompoundNBT serialize();

}
