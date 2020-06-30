package net.lomeli.worldinventories.network;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraft.network.PacketBuffer;

import java.util.function.Supplier;

import net.lomeli.worldinventories.WorldInventories;

public class MessagePlayChestAnimation implements IMessage {

    public MessagePlayChestAnimation(PacketBuffer buffer) {
    }

    public MessagePlayChestAnimation() {
    }

    @Override
    public void toBytes(PacketBuffer buffer) {
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            WorldInventories.proxy.displayAngelChestEffect();
        });
        context.get().setPacketHandled(true);
    }
}
