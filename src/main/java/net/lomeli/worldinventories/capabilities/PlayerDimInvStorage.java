package net.lomeli.worldinventories.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import net.lomeli.worldinventories.api.IPlayerDimInv;

import javax.annotation.Nullable;

public class PlayerDimInvStorage implements Capability.IStorage<IPlayerDimInv> {
    @Nullable
    @Override
    public INBT writeNBT(Capability<IPlayerDimInv> capability, IPlayerDimInv instance, Direction side) {
        return instance.toNBT();
    }

    @Override
    public void readNBT(Capability<IPlayerDimInv> capability, IPlayerDimInv instance, Direction side, INBT nbt) {
        if (nbt instanceof CompoundNBT)
            instance.fromNBT((CompoundNBT) nbt);
    }
}
