package net.lomeli.worldinventories.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IMessage {
    void toBytes(PacketBuffer buffer);

    void handle(Supplier<NetworkEvent.Context> context);
}
