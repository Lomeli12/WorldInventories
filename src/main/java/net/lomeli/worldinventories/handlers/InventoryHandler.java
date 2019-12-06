package net.lomeli.worldinventories.handlers;

import net.lomeli.worldinventories.WorldInventories;
import net.lomeli.worldinventories.api.IDimensionInventory;
import net.lomeli.worldinventories.api.SwapInventoryEvent;
import net.lomeli.worldinventories.capabilities.IPlayerDimInv;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID)
public class InventoryHandler {
    private static final ResourceLocation VANILLA_INVENTORY = new ResourceLocation("minecraft", "player_inventory");

    @SubscribeEvent
    public static void onSwapVanillaInventories(SwapInventoryEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player == null || player instanceof FakePlayer)
            return;
        //Save previous dimension's inventory
        ListNBT prevInventory = new ListNBT();
        prevInventory = player.inventory.write(prevInventory);
        event.getPrevDimInventory().addInventoryNBT(VANILLA_INVENTORY, prevInventory);

        //Load the next dimension's inventory
        ListNBT newInventory = (ListNBT) event.getNextDimInventory().getInventoryNBT(VANILLA_INVENTORY);
        player.inventory.clear();
        if (newInventory != null)
            player.inventory.read(newInventory);
    }

    @SubscribeEvent
    public static void changeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        IPlayerDimInv dimInv = PlayerDimInv.getDimInventories(player);
        IDimensionInventory prevDimInventory = dimInv.getDimInventories(event.getFrom().getRegistryName());
        IDimensionInventory newDimInventory = dimInv.getDimInventories(event.getTo().getRegistryName());
        MinecraftForge.EVENT_BUS.post(new SwapInventoryEvent(player, prevDimInventory, newDimInventory));
        dimInv.addInventory(prevDimInventory);
    }
}
