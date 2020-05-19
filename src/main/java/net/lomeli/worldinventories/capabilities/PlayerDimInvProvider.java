package net.lomeli.worldinventories.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import net.lomeli.worldinventories.WorldInventories;
import net.lomeli.worldinventories.api.IPlayerDimInv;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDimInvProvider implements ICapabilitySerializable<CompoundNBT> {
    public static final ResourceLocation CAPABILITY_ID = new ResourceLocation(WorldInventories.MOD_ID, "dim_inventories");

    @CapabilityInject(IPlayerDimInv.class)
    public static final Capability<IPlayerDimInv> DIM_INV = null;
    @SuppressWarnings("all")
    private final LazyOptional<IPlayerDimInv> INSTANCE = LazyOptional.of(DIM_INV::getDefaultInstance);

    @Nonnull
    @Override
    @SuppressWarnings("all")
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == DIM_INV ? INSTANCE.cast() : LazyOptional.empty();
    }

    @Override
    @SuppressWarnings("all")
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) DIM_INV.getStorage().writeNBT(DIM_INV, INSTANCE.orElseThrow(
                () -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
    }

    @Override
    @SuppressWarnings("all")
    public void deserializeNBT(CompoundNBT nbt) {
        DIM_INV.getStorage().readNBT(DIM_INV, this.INSTANCE.orElseThrow(
                () -> new IllegalArgumentException("LazyOptional must not be empty!")), null, nbt);
    }
}
