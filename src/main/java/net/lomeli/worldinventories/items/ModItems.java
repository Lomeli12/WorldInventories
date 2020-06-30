package net.lomeli.worldinventories.items;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraft.item.Item;

import net.lomeli.worldinventories.WorldInventories;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    @ObjectHolder(WorldInventories.MOD_ID + ":angel_chest")
    public static Item angelChest;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(angelChest = new AngelChestItem());
    }
}
