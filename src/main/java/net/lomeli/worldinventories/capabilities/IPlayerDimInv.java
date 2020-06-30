package net.lomeli.worldinventories.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

import net.lomeli.worldinventories.api.IDimensionInventory;

public interface IPlayerDimInv {
    void addInventory(IDimensionInventory dimInventory);

    IDimensionInventory getDimInventories(ResourceLocation dimID);

    void copy(IPlayerDimInv dimInv);

    Map<ResourceLocation, IDimensionInventory> getInventories();

    void setDimensionDiedIn(ResourceLocation dimID);

    ResourceLocation lastDimensionDiedIn();

    void fromNBT(CompoundNBT nbt);

    CompoundNBT toNBT();
}
