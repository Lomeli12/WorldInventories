package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.Map;

public class DimInventory {
    private Map<ResourceLocation, INBT> inventories;

    public DimInventory() {
        inventories = Maps.newHashMap();
    }

    public void addInventoryNBT(ResourceLocation inventoryID, INBT inventoryTag) {
        if (inventoryID == null || inventoryTag == null) return;
        inventories.put(inventoryID, inventoryTag);
    }

    public INBT getInventoryNBT(ResourceLocation inventoryID) {
        return inventoryID != null && inventories.containsKey(inventoryID) ? inventories.get(inventoryID) : null;
    }

    public Map<ResourceLocation, INBT> getInventories() {
        return Collections.unmodifiableMap(inventories);
    }

    public boolean isEmpty() {
        return inventories.isEmpty();
    }

    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        inventories.forEach((key, value) -> nbt.put(key.toString(), value));
        return nbt;
    }

    public void fromNBT(CompoundNBT nbt) {
        nbt.keySet().forEach(key -> {
            ResourceLocation inventoryID = new ResourceLocation(key);
            inventories.put(inventoryID, nbt.get(key));
        });
    }
}