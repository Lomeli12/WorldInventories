package net.lomeli.worldinventories.capabilities;

import net.lomeli.worldinventories.api.InventoryWrapper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.dimension.DimensionType;

public interface IPlayerDimInv {
    boolean addInventory(InventoryWrapper wrapper, DimensionType dimType);

    InventoryWrapper[] getDimInventories(DimensionType dimType);

    InventoryWrapper[] getAllInventories();

    void deserialize(CompoundNBT nbt);

    CompoundNBT serialize();

}
