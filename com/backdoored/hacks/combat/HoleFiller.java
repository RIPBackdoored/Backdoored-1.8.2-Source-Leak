package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import a.a.k.*;
import java.util.*;

@b.a(name = "Hole Filler", description = "Fill holes that enemies could jump into", category = CategoriesInit.COMBAT)
public class HoleFiller extends BaseHack
{
    private Setting<Double> radius;
    private Setting<Double> range;
    
    public HoleFiller() {
        super();
        this.radius = new DoubleSetting("Radius", this, 3.0, 1.0, 5.0);
        this.range = new DoubleSetting("Range", this, 5.0, 1.0, 10.0);
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            for (final EntityPlayer entityPlayer : HoleFiller.mc.world.playerEntities) {
                if (!entityPlayer.getUniqueID().equals(HoleFiller.mc.player.getUniqueID())) {
                    final double doubleValue = this.radius.getValInt();
                    final BlockPos position = entityPlayer.getPosition();
                    for (final BlockPos blockPos : BlockPos.getAllInBox(position.add(-doubleValue, -doubleValue, -doubleValue), position.add(doubleValue, doubleValue, doubleValue))) {
                        if (HoleFiller.mc.player.getDistanceSqToCenter(blockPos) > this.range.getValInt()) {
                            continue;
                        }
                        if (!HoleFiller.mc.world.getBlockState(blockPos).getMaterial().isReplaceable() || !HoleFiller.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial().isReplaceable() || (!HoleFiller.mc.world.getBlockState(blockPos.add(0, -1, 0)).getMaterial().isSolid() || !HoleFiller.mc.world.getBlockState(blockPos.add(1, 0, 0)).getMaterial().isSolid() || !HoleFiller.mc.world.getBlockState(blockPos.add(0, 0, 1)).getMaterial().isSolid() || !HoleFiller.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getMaterial().isSolid() || !HoleFiller.mc.world.getBlockState(blockPos.add(0, 0, -1)).getMaterial().isSolid() || HoleFiller.mc.world.getBlockState(blockPos.add(0, 0, 0)).getMaterial() != Material.AIR || HoleFiller.mc.world.getBlockState(blockPos.add(0, 1, 0)).getMaterial() != Material.AIR || HoleFiller.mc.world.getBlockState(blockPos.add(0, 2, 0)).getMaterial() != Material.AIR) || !HoleFiller.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(blockPos)).isEmpty()) {
                            continue;
                        }
                        final int a = f.a(Blocks.OBSIDIAN);
                        if (a == -1) {
                            continue;
                        }
                        final int currentItem = HoleFiller.mc.player.inventory.currentItem;
                        HoleFiller.mc.player.inventory.currentItem = a;
                        f.a(blockPos);
                        HoleFiller.mc.player.inventory.currentItem = currentItem;
                    }
                }
            }
        }
    }
}
