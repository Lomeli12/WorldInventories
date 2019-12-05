package net.lomeli.worldinventories.capabilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.FakePlayer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PlayerDimInv implements IPlayerDimInv {
    private Map<DimensionType, List<INBT>> inventories = Maps.newHashMap();

    public PlayerDimInv() {
    }

    @Override
    public void addInventory(INBT inventoryNBT, DimensionType dimType) {
        List<INBT> inbts = inventories.get(dimType);
        if (inbts == null) inbts = Lists.newArrayList();
        inbts.add(inventoryNBT);
        inventories.put(dimType, inbts);
    }

    @Override
    public Collection<ListNBT> getDimInventories(DimensionType dimType) {
        return inventories.containsKey(dimType) ? inventories.get(dimType) : Collections.emptyList();
    }

    @Override
    public void deserialize(CompoundNBT nbt) {
        if (nbt.isEmpty()) return;
        nbt.keySet().forEach(key -> {
            ListNBT dimList = nbt.getList(key, 10);

            /*
            DimensionType type = DimensionType.byName(new ResourceLocation(key));
            if (type != null)
                addInventory(nbt.get(key), type);*/
        });
    }

    @Override
    public CompoundNBT serialize() {
        CompoundNBT nbt = new CompoundNBT();

        inventories.forEach((key, value) -> {
            ListNBT dimList = new ListNBT();
            dimList.addAll(value);
            nbt.put(key.getRegistryName().toString(), dimList);
        });

        return nbt;
    }


    public static IPlayerDimInv getDimInventories(PlayerEntity player) {
        if (player == null || player instanceof FakePlayer) return null;
        return player.getCapability(PlayerDimInvProvider.DIM_INV, null)
                .orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!"));
    }
}
