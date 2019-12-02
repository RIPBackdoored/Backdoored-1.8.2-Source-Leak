package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.init.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

@b.a(name = "Auto Totem", description = "Works in guis", category = CategoriesInit.COMBAT)
public class AutoTotem extends BaseHack
{
    private boolean shouldEquip;
    private Setting<Boolean> alwaysHolding;
    private Setting<Integer> minHealthtoEquip;
    private Setting<Boolean> refillHotbarSlot;
    private Setting<Integer> hotbarSlot;
    
    public AutoTotem() {
        super();
        this.shouldEquip = true;
        this.alwaysHolding = new BooleanSetting("Always Holding", this, true);
        this.minHealthtoEquip = new IntegerSetting("Min Health to Equip", this, 6, 0, 20);
        this.refillHotbarSlot = new BooleanSetting("Refill Hotbar Slot", this, false);
        this.hotbarSlot = new IntegerSetting("Hotbar Slot", this, 9, 0, 9);
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            if (AutoTotem.mc.player.getHealth() <= this.minHealthtoEquip.getValInt() && !this.alwaysHolding.getValInt()) {
                this.shouldEquip = true;
            }
            if (this.shouldEquip && AutoTotem.mc.player.getHeldItemOffhand().isEmpty()) {
                final int itemInWholeInv = this.findItemInWholeInv(Items.TOTEM_OF_UNDYING);
                if (itemInWholeInv != -1) {
                    AutoTotem.mc.playerController.windowClick(0, itemInWholeInv, 0, ClickType.PICKUP_ALL, (EntityPlayer)AutoTotem.mc.player);
                    AutoTotem.mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP_ALL, (EntityPlayer)AutoTotem.mc.player);
                }
            }
            if (this.alwaysHolding.getValInt()) {
                this.shouldEquip = true;
            }
            if (!this.alwaysHolding.getValInt()) {
                this.shouldEquip = false;
            }
            if (this.refillHotbarSlot.getValInt() && AutoTotem.mc.player.inventory.getStackInSlot((int)this.hotbarSlot.getValInt()).isEmpty()) {
                AutoTotem.mc.playerController.windowClick(0, this.findItemInWholeInv(Items.TOTEM_OF_UNDYING), 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
                AutoTotem.mc.playerController.windowClick(0, (int)this.hotbarSlot.getValInt(), 0, ClickType.PICKUP, (EntityPlayer)AutoTotem.mc.player);
            }
        }
    }
    
    private int findItemInWholeInv(final Item item) {
        for (int i = 9; i <= 44; ++i) {
            if (AutoTotem.mc.player.inventoryContainer.getSlot(i).getStack().getItem() == item) {
                return i;
            }
        }
        return -1;
    }
}
