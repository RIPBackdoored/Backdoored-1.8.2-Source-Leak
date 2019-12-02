package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.init.*;
import a.a.k.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraft.entity.*;

@b.a(name = "Web Aura", description = "Trap people camping in holes", category = CategoriesInit.COMBAT)
public class WebAura extends BaseHack
{
    private Setting<Double> ny;
    
    public WebAura() {
        super();
        this.ny = new DoubleSetting("Range", this, 4.0, 1.0, 10.0);
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        final int a = f.a(Blocks.WEB);
        if (a == -1) {
            return;
        }
        final List list = (List)WebAura.mc.world.playerEntities.stream().filter(this::a).collect(Collectors.toList());
        if (list.size() > 0) {
            WebAura.mc.player.inventory.currentItem = a;
        }
        for (final EntityPlayer entityPlayer : list) {
            final BlockPos blockPos = new BlockPos((int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ);
            if (WebAura.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                f.a(blockPos);
            }
            if (WebAura.mc.world.getBlockState(blockPos.add(1, 0, 0)).getMaterial().isReplaceable()) {
                f.a(blockPos.add(1, 0, 0));
            }
            if (WebAura.mc.world.getBlockState(blockPos.add(0, 0, 1)).getMaterial().isReplaceable()) {
                f.a(blockPos.add(0, 0, 1));
            }
            if (WebAura.mc.world.getBlockState(blockPos.add(0, 0, -1)).getMaterial().isReplaceable()) {
                f.a(blockPos.add(0, 0, -1));
            }
            if (WebAura.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getMaterial().isReplaceable()) {
                f.a(blockPos.add(-1, 0, 0));
            }
        }
    }
    
    private /* synthetic */ boolean a(final EntityPlayer entityPlayer) {
        return WebAura.mc.player.getDistance((Entity)entityPlayer) <= this.ny.getValInt() && !WebAura.mc.player.equals((Object)entityPlayer);
    }
}
