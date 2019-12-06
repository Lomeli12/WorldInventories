package net.lomeli.worldinventories.handlers;

import net.lomeli.worldinventories.WorldInventories;
import net.lomeli.worldinventories.api.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInvProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID)
public class CapabilityHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity)
            event.addCapability(PlayerDimInvProvider.CAPABILITY_ID, new PlayerDimInvProvider());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        IPlayerDimInv oldPlayerInfo = PlayerDimInv.getDimInventories(event.getOriginal());
        IPlayerDimInv newPlayerInfo = PlayerDimInv.getDimInventories(event.getPlayer());
        if (oldPlayerInfo != null && newPlayerInfo != null) {
            if (event.isWasDeath()) {
                // Remove the inventory from the dimension the player died in.
                ResourceLocation dimID = event.getOriginal().dimension.getRegistryName();
                oldPlayerInfo.getDimInventories(dimID).clear();
            }
            newPlayerInfo.copy(oldPlayerInfo.getInventories());
        }
    }
}
