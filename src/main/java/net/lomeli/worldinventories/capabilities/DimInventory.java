package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Maps;
import net.lomeli.worldinventories.api.IDimensionInventory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Map;

public class DimInventory implements IDimensionInventory {
    private Map<ResourceLocation, INBT> inventories;
    private final ResourceLocation dimID;

    public DimInventory(ResourceLocation dimID) {
        inventories = Maps.newHashMap();
        this.dimID = dimID;
    }

    @Override
    public void addInventoryNBT(ResourceLocation inventoryID, INBT inventoryTag) {
        if (inventoryID == null || inventoryTag == null) return;
        inventories.put(inventoryID, inventoryTag);
    }

    @Override
    public INBT getInventoryNBT(ResourceLocation inventoryID) {
        return inventoryID != null && inventories.containsKey(inventoryID) ? inventories.get(inventoryID) : null;
    }

    public Map<ResourceLocation, INBT> getInventories() {
        return Collections.unmodifiableMap(inventories);
    }

    @Override
    public boolean isEmpty() {
        return inventories.isEmpty();
    }

    @Override
    public ResourceLocation getDimensionID() {
        return dimID;
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        inventories.forEach((key, value) -> nbt.put(key.toString(), value));
        return nbt;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        nbt.keySet().forEach(key -> {
            ResourceLocation inventoryID = new ResourceLocation(key);
            inventories.put(inventoryID, nbt.get(key));
        });
    }
}