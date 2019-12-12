package net.lomeli.worldinventories;

import net.lomeli.worldinventories.api.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInvStorage;
import net.lomeli.worldinventories.network.PacketHandler;
import net.lomeli.worldinventories.proxy.ClientProxy;
import net.lomeli.worldinventories.proxy.CommonProxy;
import net.lomeli.worldinventories.proxy.IProxy;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WorldInventories.MOD_ID)
public class WorldInventories {
    public static final String MOD_ID = "worldinventories";
    public static final String MOD_NAME = "World Inventories";
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);

    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public static final ForgeConfigSpec SERVER_SPEC;

    public static final ServerConfig SERVER_CONFIG;

    static {
        {
            final Pair<ServerConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerConfig::new);
            SERVER_CONFIG = specPair.getLeft();
            SERVER_SPEC = specPair.getRight();
        }
    }

    public WorldInventories() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerDimInv.class, new PlayerDimInvStorage(), PlayerDimInv::new);
        PacketHandler.registerPackets();
    }
}
