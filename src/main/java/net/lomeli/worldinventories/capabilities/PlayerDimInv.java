package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Maps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Map;

public class PlayerDimInv implements IPlayerDimInv {
    private Map<ResourceLocation, DimInventory> inventories;

    public PlayerDimInv() {
        inventories = Maps.newHashMap();
    }

    @Override
    public void addInventory(ResourceLocation dimID, ResourceLocation invID, INBT inventoryTag) {
        if (dimID == null || inventoryTag == null) return;
        DimInventory dimInventory = this.getDimInventories(dimID);
        dimInventory.addInventoryNBT(invID, inventoryTag);
        inventories.put(dimID, dimInventory);
    }

    @Override
    public DimInventory getDimInventories(ResourceLocation dimID) {
        return inventories.containsKey(dimID) ? inventories.get(dimID) : new DimInventory();
    }

    @Override
    public void deserialize(CompoundNBT nbt) {
        if (nbt.isEmpty()) return;
        nbt.keySet().forEach(key -> {
            CompoundNBT dimNBT = nbt.getCompound(key);
            DimInventory dimInv = new DimInventory();
            dimInv.fromNBT(dimNBT);
            inventories.put(new ResourceLocation(key), dimInv);
        });
    }

    @Override
    public CompoundNBT serialize() {
        CompoundNBT nbt = new CompoundNBT();
        inventories.forEach((dimID, dimInv) -> {
            if (!dimInv.isEmpty()) {
                CompoundNBT dimNBT = dimInv.toNBT();
                nbt.put(dimID.toString(), dimNBT);
            }
        });
        return nbt;
    }

    public static IPlayerDimInv getDimInventories(PlayerEntity player) {
        if (player == null || player instanceof FakePlayer) return null;
        return player.getCapability(PlayerDimInvProvider.DIM_INV, null)
                .orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }
}