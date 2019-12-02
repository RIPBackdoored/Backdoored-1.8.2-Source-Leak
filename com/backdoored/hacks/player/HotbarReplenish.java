package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.utils.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.player.*;

@b.a(name = "Hotbar Replenish", description = "Replenish items in your hotbar when they are used", category = CategoriesInit.PLAYER)
public class HotbarReplenish extends BaseHack
{
    private Setting<Integer> cooldowninticks;
    private Setting<Integer> minStackSizepercent;
    private int sz;
    
    public HotbarReplenish() {
        super();
        this.cooldowninticks = new IntegerSetting("Cooldown in ticks", this, 100, 0, 200);
        this.minStackSizepercent = new IntegerSetting("Min Stack Size (percent)", this, 20, 1, 99);
        this.sz = 0;
    }
    
    public void onEnabled() {
        this.setEnabled(false);
        Utils.printMessage("Still in development...", "red");
    }
    
    public void onTick() {
        --this.sz;
        if (this.sz <= 0) {
            for (final ItemStack itemStack : c()) {
                if (itemStack == null || a(itemStack) < this.minStackSizepercent.getValInt()) {}
            }
            this.sz = this.cooldowninticks.getValInt();
        }
    }
    
    private static int a(final ItemStack itemStack) {
        return (int)Math.ceil(itemStack.getCount() * 100.0f / itemStack.getMaxStackSize());
    }
    
    private static List<ItemStack> c() {
        return (List<ItemStack>)HotbarReplenish.mc.player.inventory.mainInventory.subList(0, InventoryPlayer.getHotbarSize());
    }
}
