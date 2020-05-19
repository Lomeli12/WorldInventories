package net.lomeli.worldinventories.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import net.lomeli.worldinventories.WorldInventories;

import java.util.function.Supplier;

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
