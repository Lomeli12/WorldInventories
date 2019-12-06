package net.lomeli.worldinventories.capabilities;

import net.lomeli.worldinventories.api.IDimensionInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public interface IPlayerDimInv {
    void addInventory(IDimensionInventory dimInventory);

    IDimensionInventory getDimInventories(ResourceLocation dimID);

    void copy(Map<ResourceLocation, IDimensionInventory> inventories);

    Map<ResourceLocation, IDimensionInventory> getInventories();

    void fromNBT(CompoundNBT nbt);

    CompoundNBT toNBT();
}
