package net.lomeli.worldinventories.api;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraft.entity.player.PlayerEntity;

/**
 * This event is fired when a player changes dimension and we need to save their old inventory and load in their
 * new inventory.<br>
 * <br>
 * You can also use {@link net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent}, but to
 * simplify it to add support for other mods, we're running it as a separate event.<br>
 * <br>
 * {@link #getPrevDimInventory()} is the only one that gets saved after the event is finished. <br>
 * {@link #getNextDimInventory()} will contain the inventories for the dimension the player is entering.
 * <br>
 * This event is not {@link net.minecraftforge.eventbus.api.Cancelable}.<br>
 * <br>
 * This event is fired on the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS}.
 */
public class SwapInventoryEvent extends PlayerEvent {
    private final IDimensionInventory prevDimInv;
    private final IDimensionInventory nextDimInv;
    private final boolean keepingInventory;

    public SwapInventoryEvent(PlayerEntity player, IDimensionInventory prevDimInv, IDimensionInventory nextDimInv,
                              boolean keepingInventory) {
        super(player);
        this.prevDimInv = prevDimInv;
        this.nextDimInv = nextDimInv;
        this.keepingInventory = keepingInventory;
    }

    public IDimensionInventory getNextDimInventory() {
        return nextDimInv;
    }

    public IDimensionInventory getPrevDimInventory() {
        return prevDimInv;
    }

    public boolean isKeepingInventory() {
        return keepingInventory;
    }
}
