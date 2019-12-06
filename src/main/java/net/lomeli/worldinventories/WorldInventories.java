package net.lomeli.worldinventories;

import net.lomeli.worldinventories.api.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInvStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WorldInventories.MOD_ID)
public class WorldInventories {
    public static final String MOD_ID = "worldinventories";
    public static final String MOD_NAME = "World Inventories";
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);

    public WorldInventories() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerDimInv.class, new PlayerDimInvStorage(), PlayerDimInv::new);
    }
}
