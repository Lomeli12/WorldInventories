package net.lomeli.worldinventories.network;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

public interface IMessage {
    void toBytes(PacketBuffer buffer);

    void handle(Supplier<NetworkEvent.Context> context);
}
