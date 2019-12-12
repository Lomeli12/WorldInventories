package net.lomeli.worldinventories.handlers;

import net.lomeli.worldinventories.ServerConfig;
import net.lomeli.worldinventories.WorldInventories;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigHandler {

    @SubscribeEvent
    public static void modConfigEvent(final ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        if (config.getSpec() == WorldInventories.SERVER_SPEC) {
            WorldInventories.LOG.debug("Loading configs");
            ServerConfig.bakeConfig(config);
        }
    }
}
