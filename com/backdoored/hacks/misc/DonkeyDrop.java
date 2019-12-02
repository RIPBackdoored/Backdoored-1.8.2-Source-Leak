package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

@b.a(name = "Donkey Drop", description = "Drop all items in donkeys inv", category = CategoriesInit.MISC)
public class DonkeyDrop extends BaseHack
{
    public DonkeyDrop() {
        super();
    }
    
    public void onTick() {
        if (this.getEnabled() && DonkeyDrop.mc.player.getRidingEntity() instanceof AbstractHorse && DonkeyDrop.mc.player.openContainer instanceof ContainerHorseInventory) {
            for (int i = 2; i < 17; ++i) {
                final ItemStack itemStack = (ItemStack)DonkeyDrop.mc.player.openContainer.getInventory().get(i);
                if (!itemStack.isEmpty() && itemStack.getItem() != Items.AIR) {
                    DonkeyDrop.mc.playerController.windowClick(DonkeyDrop.mc.player.openContainer.windowId, i, 0, ClickType.PICKUP, (EntityPlayer)DonkeyDrop.mc.player);
                    DonkeyDrop.mc.playerController.windowClick(DonkeyDrop.mc.player.openContainer.windowId, -999, 0, ClickType.PICKUP, (EntityPlayer)DonkeyDrop.mc.player);
                }
            }
        }
        this.setEnabled(false);
    }
}
