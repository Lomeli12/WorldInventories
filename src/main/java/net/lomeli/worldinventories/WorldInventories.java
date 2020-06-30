package net.lomeli.worldinventories;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.lomeli.worldinventories.capabilities.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInvStorage;
import net.lomeli.worldinventories.network.PacketHandler;
import net.lomeli.worldinventories.proxy.ClientProxy;
import net.lomeli.worldinventories.proxy.CommonProxy;
import net.lomeli.worldinventories.proxy.IProxy;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(WorldInventories.MOD_ID)
public class WorldInventories {
    public static final String MOD_ID = "worldinventories";
    public static final String MOD_NAME = "World Inventories";
    public static final Logger LOG = LogManager.getLogger(MOD_NAME);
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON_CONFIG;
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    static {
        {
            final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
            COMMON_CONFIG = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
    }

    public WorldInventories() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
    }

    private void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IPlayerDimInv.class, new PlayerDimInvStorage(), PlayerDimInv::new);
        PacketHandler.registerPackets();
    }
}
