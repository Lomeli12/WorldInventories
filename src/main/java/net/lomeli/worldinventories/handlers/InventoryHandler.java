package net.lomeli.worldinventories.handlers;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.lomeli.worldinventories.ServerConfig;
import net.lomeli.worldinventories.WorldInventories;
import net.lomeli.worldinventories.api.IDimensionInventory;
import net.lomeli.worldinventories.api.IPlayerDimInv;
import net.lomeli.worldinventories.api.SwapInventoryEvent;
import net.lomeli.worldinventories.capabilities.PlayerDimInv;
import net.lomeli.worldinventories.items.AngelChestItem;
import net.lomeli.worldinventories.network.MessagePlayChestAnimation;
import net.lomeli.worldinventories.network.PacketHandler;

@Mod.EventBusSubscriber(modid = WorldInventories.MOD_ID)
public class InventoryHandler {
    private static final ResourceLocation VANILLA_INVENTORY = new ResourceLocation("minecraft", "player_inventory");

    @SubscribeEvent
    public static void onSwapVanillaInventories(SwapInventoryEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player == null || player instanceof FakePlayer)
            return;

        ListNBT newInventory = (ListNBT) event.getNextDimInventory().getInventoryNBT(VANILLA_INVENTORY);
        if (event.isKeepingInventory()) {
            // If the player had an Angel Chest, remove the previous inventory to avoid duping items when they return.
            event.getPrevDimInventory().removeInventoryNBT(VANILLA_INVENTORY);

            // If the player had an inventory in the new dimension, drop their items.
            if (newInventory != null) {
                PlayerInventory dummyInventory = new PlayerInventory(event.getPlayer());
                dummyInventory.read(newInventory);
                dummyInventory.dropAllItems();
            }
        } else {
            // Save previous inventory if the player didn't use an Angel Chest
            ListNBT prevInventory = new ListNBT();
            prevInventory = player.inventory.write(prevInventory);
            event.getPrevDimInventory().addInventoryNBT(VANILLA_INVENTORY, prevInventory);

            //Load the next dimension's inventory
            player.inventory.clear();
            if (newInventory != null)
                player.inventory.read(newInventory);
        }
    }

    @SubscribeEvent
    public static void changeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!ServerConfig.affectCreative && player.abilities.isCreativeMode)
            return;
        if (ServerConfig.ignoredDims.contains(event.getTo().getRegistryName().toString()))
            return;
        IPlayerDimInv dimInv = PlayerDimInv.getDimInventories(player);
        int slot = AngelChestItem.getAngelChestSlot(player);
        boolean swapping = true;
        if (slot > -1) {
            WorldInventories.LOG.debug("Using {}'s angel chest", player.getDisplayName().getFormattedText());
            if (player instanceof ServerPlayerEntity)
                PacketHandler.sendToClient(new MessagePlayChestAnimation(), (ServerPlayerEntity) player);
            player.inventory.decrStackSize(slot, 1);
            swapping = false;
        }
        fireSwapEvent(event.getPlayer(), dimInv, event.getFrom().getRegistryName(),
                event.getTo().getRegistryName(), false, false, swapping);
    }

    @SubscribeEvent
    public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (!ServerConfig.affectCreative && event.getPlayer().abilities.isCreativeMode)
            return;
        if (ServerConfig.ignoredDims.contains(event.getPlayer().dimension.getRegistryName().toString()))
            return;
        IPlayerDimInv dimInv = PlayerDimInv.getDimInventories(event.getPlayer());
        boolean keepInventory = event.getPlayer().world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY);
        fireSwapEvent(event.getPlayer(), dimInv, dimInv.lastDimensionDiedIn(),
                event.getPlayer().dimension.getRegistryName(), true, keepInventory, false);
    }

    private static void fireSwapEvent(PlayerEntity player, IPlayerDimInv dimInv, ResourceLocation prevDim,
                                      ResourceLocation nextDim, boolean death, boolean keepInventory, boolean swapping) {
        if (dimInv == null || prevDim == null || nextDim == null)
            return;
        IDimensionInventory prevDimInventory = dimInv.getDimInventories(prevDim);
        IDimensionInventory newDimInventory = dimInv.getDimInventories(nextDim);
        MinecraftForge.EVENT_BUS.post(new SwapInventoryEvent(player, prevDimInventory, newDimInventory, !swapping));
        if (death && !keepInventory)
            prevDimInventory.clear(); //If keepInventory is off, clear prevDimInventory cause they dropped those items

        dimInv.addInventory(prevDimInventory);
    }

    @SubscribeEvent
    public static void playerDeath(LivingDeathEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            IPlayerDimInv dimInv = PlayerDimInv.getDimInventories(player);
            if (dimInv != null)
                dimInv.setDimensionDiedIn(player.dimension.getRegistryName());
        }
    }
}
