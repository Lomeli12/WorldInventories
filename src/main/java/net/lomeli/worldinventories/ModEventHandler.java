package net.lomeli.worldinventories;

import net.lomeli.worldinventories.capabilities.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInvProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID)
public class ModEventHandler {
    @SubscribeEvent
    public static void changeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        WorldInventories.LOGGER.info("Did this fire first?");
        PlayerEntity player = event.getPlayer();
        IPlayerDimInv dimInv = PlayerDimInv.getDimInventories(player);
        if (dimInv == null)
            return;
        //dimInv.addInventory(event.getFrom(), player.inventory.)
    }

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity)
            event.addCapability(PlayerDimInvProvider.CAPABILITY_ID, new PlayerDimInvProvider());
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        WorldInventories.LOGGER.info("Or did this fire first?");
    }
}
