package net.lomeli.worldinventories.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import net.lomeli.worldinventories.WorldInventories;

import javax.annotation.Nullable;
import java.util.List;

public class AngelChestItem extends Item {
    public AngelChestItem() {
        super(new Item.Properties().rarity(Rarity.EPIC).group(ItemGroup.MISC));
        this.setRegistryName(WorldInventories.MOD_ID, "angel_chest");
    }

    public static int getAngelChestSlot(PlayerEntity player) {
        for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
            ItemStack stack = player.inventory.getStackInSlot(slot);
            if (stack.getItem() == ModItems.angelChest)
                return slot;
        }
        return -1;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.worldinventories.angel_chest.info"));
    }
}
