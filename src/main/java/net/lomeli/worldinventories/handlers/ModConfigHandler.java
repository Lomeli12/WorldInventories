package net.lomeli.worldinventories.handlers;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import net.lomeli.worldinventories.CommonConfig;
import net.lomeli.worldinventories.WorldInventories;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModConfigHandler {

    @SubscribeEvent
    public static void modConfigEvent(final ModConfig.ModConfigEvent event) {
        final ModConfig config = event.getConfig();
        if (config.getSpec() == WorldInventories.COMMON_SPEC) {
            WorldInventories.LOG.debug("Loading configs");
            CommonConfig.bakeConfig(config);
        }
    }
}
