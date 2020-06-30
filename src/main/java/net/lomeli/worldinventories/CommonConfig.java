package net.lomeli.worldinventories;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.util.Strings;

public class CommonConfig {
    public static ModConfig modConfig;
    public static boolean affectCreative = false;
    public static boolean stopItemTeleport = true;
    public static List<String> ignoredDims = Lists.newArrayList();

    final ForgeConfigSpec.BooleanValue affectCreativeSpec;
    final ForgeConfigSpec.BooleanValue stopItemSpec;
    final ForgeConfigSpec.ConfigValue<String> ignoredDimsSpec;

    public CommonConfig(final ForgeConfigSpec.Builder builder) {
        builder.push("common");

        affectCreativeSpec = builder.comment("Set if Creative Players also change inventories.")
                .translation("config.worldinventories.affect_creative")
                .define("affect_creative", false);

        ignoredDimsSpec = builder
                .comment("List of dimensions that the player can freely move their inventory into. Dimensions should" +
                        " be added via their IDs, and separated by ';'. Example: minecraft:overworld;minecraft:the_end")
                .translation("config.worldinventories.ignored_dimensions")
                .define("ignored_dimensions", "");

        stopItemSpec = builder
                .comment("Stops items, except the Angel Chest, from teleporting through portals when thrown in, " +
                        "which would defeat the purpose of this mod.")
                .translation("config.worldinventories.disable_item_portal_interaction")
                .define("stopItemTeleport", true);

        builder.pop();
    }

    public static void bakeConfig(final ModConfig config) {
        modConfig = config;

        affectCreative = WorldInventories.COMMON_CONFIG.affectCreativeSpec.get();
        stopItemTeleport = WorldInventories.COMMON_CONFIG.stopItemSpec.get();

        String dimIDString = WorldInventories.COMMON_CONFIG.ignoredDimsSpec.get();
        if (Strings.isNotBlank(dimIDString)) {
            String[] dimIDs = dimIDString.split(";");
            if (dimIDs.length < 1)
                return;
            Collections.addAll(ignoredDims, dimIDs);
        }
    }
}
