package net.lomeli.worldinventories.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.dimension.DimensionType;

import java.util.Collection;

public interface IPlayerDimInv {
    void addInventory(ListNBT inventoryTag, DimensionType dimType);

    Collection<ListNBT> getDimInventories(DimensionType dimType);

    void deserialize(CompoundNBT nbt);

    CompoundNBT serialize();

}
