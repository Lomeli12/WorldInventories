package net.lomeli.worldinventories.network;

import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import net.lomeli.worldinventories.WorldInventories;

public class PacketHandler {
    private static final ResourceLocation CHANNEL_NAME = new ResourceLocation(WorldInventories.MOD_ID, "main_channel");
    private static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(CHANNEL_NAME, () -> "1.0", s -> true, s -> true);

    public static void registerPackets() {
        //int packetID = 0;
        HANDLER.registerMessage(0, MessagePlayChestAnimation.class, MessagePlayChestAnimation::toBytes,
                MessagePlayChestAnimation::new, MessagePlayChestAnimation::handle);
    }

    public static void sendToClient(IMessage message, ServerPlayerEntity player) {
        HANDLER.sendTo(message, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
    }
}
