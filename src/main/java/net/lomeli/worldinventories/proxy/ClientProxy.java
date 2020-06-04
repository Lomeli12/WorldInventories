package net.lomeli.worldinventories.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;

import net.lomeli.worldinventories.items.ModItems;

public class ClientProxy implements IProxy {
    @Override
    public void displayAngelChestEffect() {
        Minecraft mc = Minecraft.getInstance();
        mc.gameRenderer.displayItemActivation(new ItemStack(ModItems.angelChest));
        if (mc.player != null)
            mc.particles.emitParticleAtEntity(mc.player, ParticleTypes.ENCHANT, 30);
    }
}
