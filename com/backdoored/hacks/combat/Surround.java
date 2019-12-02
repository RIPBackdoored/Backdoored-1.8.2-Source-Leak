package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.init.*;
import a.a.k.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;

@b.a(name = "Surround", description = "Surrounds your feet with obsidian", category = CategoriesInit.COMBAT)
public class Surround extends BaseHack
{
    private BlockPos blockPos;
    
    public Surround() {
        super();
        this.blockPos = new BlockPos(0, -100, 0);
    }
    
    public void onTick() {
        if (!this.getEnabled() || !Surround.mc.player.onGround) {
            return;
        }
        final int currentItem = Surround.mc.player.inventory.currentItem;
        final int a = f.a(Blocks.OBSIDIAN);
        if (a != -1) {
            final BlockPos blockPos = new BlockPos(Surround.mc.player.getPositionVector());
            if (blockPos.equals((Object)this.blockPos)) {
                for (final BlockPos blockPos2 : new BlockPos[] { blockPos.add(0, -1, 1), blockPos.add(1, -1, 0), blockPos.add(0, -1, -1), blockPos.add(-1, -1, 0), blockPos.add(0, 0, 1), blockPos.add(1, 0, 0), blockPos.add(0, 0, -1), blockPos.add(-1, 0, 0) }) {
                    if (Surround.mc.world.getBlockState(blockPos2).getMaterial().isReplaceable() && Surround.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(blockPos2)).isEmpty()) {
                        Surround.mc.player.inventory.currentItem = a;
                        f.a(blockPos2);
                    }
                }
                Surround.mc.player.inventory.currentItem = currentItem;
            }
            else {
                this.setEnabled(false);
            }
        }
    }
    
    public void onEnabled() {
        this.blockPos = new BlockPos(Surround.mc.player.getPositionVector());
    }
}
