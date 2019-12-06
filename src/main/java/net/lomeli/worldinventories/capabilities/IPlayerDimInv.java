package net.lomeli.worldinventories.capabilities;

import net.lomeli.worldinventories.api.IDimensionInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public interface IPlayerDimInv {
    void addInventory(IDimensionInventory dimInventory);

    IDimensionInventory getDimInventories(ResourceLocation dimID);

    void deserialize(CompoundNBT nbt);

    CompoundNBT serialize();

    void copy(Map<ResourceLocation, IDimensionInventory> inventories);

    Map<ResourceLocation, IDimensionInventory> getInventories();
}
