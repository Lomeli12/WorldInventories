package net.lomeli.worldinventories.handlers;

import net.lomeli.worldinventories.WorldInventories;
import net.lomeli.worldinventories.api.IDimensionInventory;
import net.lomeli.worldinventories.api.SwapInventoryEvent;
import net.lomeli.worldinventories.capabilities.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInvProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID)
public class CapabilityHandler {
    @SubscribeEvent
    public static void changeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        IPlayerDimInv dimInv = PlayerDimInv.getDimInventories(player);
        if (dimInv == null)
            return;
        IDimensionInventory newDimInventory = dimInv.getDimInventories(event.getTo().getRegistryName());
        IDimensionInventory prevDimInventory = dimInv.getDimInventories(event.getFrom().getRegistryName());
        MinecraftForge.EVENT_BUS.post(new SwapInventoryEvent(player, newDimInventory, prevDimInventory));
        dimInv.addInventory(prevDimInventory);
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity)
            event.addCapability(PlayerDimInvProvider.CAPABILITY_ID, new PlayerDimInvProvider());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            IPlayerDimInv oldPlayerInfo = PlayerDimInv.getDimInventories(event.getOriginal());
            IPlayerDimInv newPlayerInfo = PlayerDimInv.getDimInventories(event.getPlayer());
            if (oldPlayerInfo != null && newPlayerInfo != null) {
                newPlayerInfo.copy(oldPlayerInfo.getInventories());
            }
        }
    }
}
