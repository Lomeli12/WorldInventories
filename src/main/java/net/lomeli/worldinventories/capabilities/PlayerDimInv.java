package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Maps;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;

import java.util.Map;

public class PlayerDimInv implements IPlayerDimInv {
    private Map<DimensionType, CompoundNBT> inventories = Maps.newHashMap();

    public PlayerDimInv() {
    }

    /**
     * @return true if it's a brand new inventory
     */
    @Override
    public boolean addInventory(CompoundNBT wrapper, DimensionType dimType) {
        return false;
    }

    @Override
    public CompoundNBT[] getDimInventories(DimensionType dimType) {
        return new CompoundNBT[0];
    }

    @Override
    public CompoundNBT[] getAllInventories() {
        return new CompoundNBT[0];
    }

    @Override
    public void deserialize(CompoundNBT nbt) {
        if (nbt.isEmpty()) return;
        nbt.keySet().forEach(key -> {
            DimensionType type = DimensionType.byName(new ResourceLocation(key));
            if (type != null) {

            }
        });
    }

    @Override
    public CompoundNBT serialize() {
        CompoundNBT nbt = new CompoundNBT();

        inventories.entrySet().forEach(entry -> {
            nbt.put(entry.getKey().getRegistryName().toString(), entry.getValue());
        });

        return nbt;
    }
}
