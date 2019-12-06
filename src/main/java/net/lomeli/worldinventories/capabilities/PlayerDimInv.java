package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Maps;
import net.lomeli.worldinventories.api.IDimensionInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Map;

public class PlayerDimInv implements IPlayerDimInv {
    private Map<ResourceLocation, IDimensionInventory> inventories;

    public PlayerDimInv() {
        inventories = Maps.newHashMap();
    }

    @Override
    public void addInventory(IDimensionInventory dimInventory) {
        if (dimInventory == null) return;
        inventories.put(dimInventory.getDimensionID(), dimInventory);
    }

    @Override
    public IDimensionInventory getDimInventories(ResourceLocation dimID) {
        return inventories.containsKey(dimID) ? inventories.get(dimID) : new DimInventory(dimID);
    }

    @Override
    public void copy(Map<ResourceLocation, IDimensionInventory> inventories) {
        this.inventories.putAll(inventories);
    }

    @Override
    public Map<ResourceLocation, IDimensionInventory> getInventories() {
        return inventories;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        if (nbt.isEmpty()) return;
        nbt.keySet().forEach(key -> {
            CompoundNBT dimNBT = nbt.getCompound(key);
            DimInventory dimInv = new DimInventory(new ResourceLocation(key));
            dimInv.fromNBT(dimNBT);
            inventories.put(new ResourceLocation(key), dimInv);
        });
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();
        inventories.forEach((dimID, dimInv) -> {
            if (!dimInv.isEmpty()) {
                CompoundNBT dimNBT = dimInv.toNBT();
                nbt.put(dimID.toString(), dimNBT);
            }
        });
        return nbt;
    }

    @SuppressWarnings("all")
    public static IPlayerDimInv getDimInventories(PlayerEntity player) {
        if (player == null || player instanceof FakePlayer) return null;
        return player.getCapability(PlayerDimInvProvider.DIM_INV, null)
                .orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }
}