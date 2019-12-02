package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.time.*;
import com.backdoored.setting.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import a.a.k.*;
import net.minecraft.entity.player.*;
import com.backdoored.utils.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

@b.a(name = "Auto Trap", description = "Trap nearby players", category = CategoriesInit.COMBAT)
public class AutoTrap extends BaseHack
{
    private Setting<Double> nc;
    private Setting<Integer> milliseconddelay;
    private Instant ne;
    
    public AutoTrap() {
        super();
        this.nc = new DoubleSetting("Range", this, 8.0, 0.0, 15.0);
        this.milliseconddelay = new IntegerSetting("Millisecond delay", this, 1000, 100, 1500);
        this.ne = Instant.EPOCH;
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        final Instant now = Instant.now();
        if (!c.b(this.ne, now, new Long(this.milliseconddelay.getValInt()))) {
            return;
        }
        final int currentItem = AutoTrap.mc.player.inventory.currentItem;
        final int a = f.a(Item.getItemFromBlock(Blocks.OBSIDIAN));
        if (a == -1) {
            this.setEnabled(false);
            Utils.printMessage("Obsidian was not found in your hotbar!", "red");
            return;
        }
        AutoTrap.mc.player.inventory.currentItem = a;
        for (final EntityPlayer entityPlayer : AutoTrap.mc.world.playerEntities) {
            if (FriendUtils.a(entityPlayer)) {
                continue;
            }
            if (AutoTrap.mc.player.getDistance((Entity)entityPlayer) > this.nc.getValInt() || entityPlayer == AutoTrap.mc.player || FriendUtils.a(entityPlayer)) {
                continue;
            }
            for (final BlockPos blockPos : new BlockPos[] { f.a(entityPlayer, 1, 0, 0), f.a(entityPlayer, 1, 1, 0), f.a(entityPlayer, 0, 1, 1), f.a(entityPlayer, -1, 1, 0), f.a(entityPlayer, 0, 1, -1), f.a(entityPlayer, 0, 2, -1), f.a(entityPlayer, 0, 2, 0) }) {
                if (AutoTrap.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() && AutoTrap.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos)).isEmpty()) {
                    f.a(blockPos);
                    this.ne = now;
                    return;
                }
            }
        }
        AutoTrap.mc.player.inventory.currentItem = currentItem;
    }
}
