package net.lomeli.worldinventories.capabilities;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Maps;

import java.util.Map;

import net.lomeli.worldinventories.api.IDimensionInventory;
import net.lomeli.worldinventories.api.IPlayerDimInv;

public class PlayerDimInv implements IPlayerDimInv {
    private final Map<ResourceLocation, IDimensionInventory> inventories;
    private ResourceLocation deathDim;

    public PlayerDimInv() {
        inventories = Maps.newHashMap();
    }

    @SuppressWarnings("ConstantConditions")
    public static IPlayerDimInv getDimInventories(PlayerEntity player) {
        if (player == null || player instanceof FakePlayer) return null;
        return player.getCapability(PlayerDimInvProvider.DIM_INV, null)
                .orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
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
    public void copy(IPlayerDimInv dimInv) {
        inventories.clear();
        inventories.putAll(dimInv.getInventories());
        setDimensionDiedIn(dimInv.lastDimensionDiedIn());
    }

    @Override
    public Map<ResourceLocation, IDimensionInventory> getInventories() {
        return inventories;
    }

    @Override
    public void setDimensionDiedIn(ResourceLocation dimID) {
        deathDim = dimID;
    }

    @Override
    public ResourceLocation lastDimensionDiedIn() {
        return deathDim;
    }

    @Override
    public void fromNBT(CompoundNBT nbt) {
        if (nbt.isEmpty()) return;

        if (nbt.contains("death_dimension_id", 10))
            deathDim = new ResourceLocation(nbt.getString("death_dimension_id"));

        nbt.keySet().forEach(key -> {
            if (nbt.getTagId(key) == 10) { //Make sure it's a CompoundTag
                CompoundNBT dimNBT = nbt.getCompound(key);
                DimInventory dimInv = new DimInventory(new ResourceLocation(key));
                dimInv.fromNBT(dimNBT);
                inventories.put(new ResourceLocation(key), dimInv);
            }
        });
    }

    @Override
    public CompoundNBT toNBT() {
        CompoundNBT nbt = new CompoundNBT();

        if (deathDim != null)
            nbt.putString("death_dimension_id", deathDim.toString());

        inventories.forEach((dimID, dimInv) -> {
            if (!dimInv.isEmpty()) {
                CompoundNBT dimNBT = dimInv.toNBT();
                nbt.put(dimID.toString(), dimNBT);
            }
        });
        return nbt;
    }
}