package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Maps;
import net.lomeli.worldinventories.api.InventoryWrapper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.dimension.DimensionType;

import java.util.Map;

public class PlayerDimInv implements IPlayerDimInv {
    private Map<DimensionType, InventoryWrapper> inventories = Maps.newHashMap();

    public PlayerDimInv() {
    }

    /**
     * @return true if it's a brand new inventory
     */
    @Override
    public boolean addInventory(InventoryWrapper wrapper, DimensionType dimType) {
        return false;
    }

    @Override
    public InventoryWrapper[] getDimInventories(DimensionType dimType) {
        return new InventoryWrapper[0];
    }

    @Override
    public InventoryWrapper[] getAllInventories() {
        return new InventoryWrapper[0];
    }

    @Override
    public void deserialize(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT serialize() {
        return null;
    }
}
