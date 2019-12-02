package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.hacks.player.*;
import a.a.g.b.*;
import net.minecraft.init.*;
import a.a.k.*;
import com.backdoored.utils.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;

@b.a(name = "Auto Bed Bomb", description = "Automatically suicide bomb with beds", category = CategoriesInit.COMBAT)
public class AutoBedBomb extends BaseHack
{
    private final Setting<Float> range;
    
    public AutoBedBomb() {
        super();
        this.range = new FloatSetting("Range", this, 5.0f, 1.0f, 10.0f);
    }
    
    public void onEnabled() {
        if (AutoBedBomb.mc.objectMouseOver == null || AutoBedBomb.mc.objectMouseOver.sideHit == null) {
            return;
        }
        final BaseHack a = c.a((Class)AntiBedTrap.class);
        boolean enabled = false;
        if (a != null) {
            enabled = a.getEnabled();
            a.setEnabled(false);
        }
        this.c();
        this.setEnabled(false);
        if (a != null) {
            a.setEnabled(enabled);
        }
    }
    
    private boolean c() {
        if (AutoBedBomb.mc.objectMouseOver == null || AutoBedBomb.mc.objectMouseOver.sideHit == null) {
            return false;
        }
        final BlockPos offset = AutoBedBomb.mc.objectMouseOver.getBlockPos().offset(AutoBedBomb.mc.objectMouseOver.sideHit);
        final int a = f.a(Items.BED);
        if (a == -1) {
            Utils.printMessage("A bed was not found in your hotbar!", "red");
            this.setEnabled(false);
            return false;
        }
        AutoBedBomb.mc.player.inventory.currentItem = a;
        f.a(offset);
        AutoBedBomb.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(offset, EnumFacing.UP, EnumHand.MAIN_HAND, 0.5f, 0.5f, 0.5f));
        return true;
    }
}
