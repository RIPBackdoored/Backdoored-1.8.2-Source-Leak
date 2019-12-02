package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.k.*;
import com.backdoored.utils.*;
import com.backdoored.*;
import net.minecraft.util.*;

@b.a(name = "Auto Wither", description = "2 tick withers", category = CategoriesInit.PLAYER)
public class AutoWither extends BaseHack
{
    private Item item;
    private Item item;
    private BlockPos blockPos;
    private int sd;
    
    public AutoWither() {
        super();
        this.item = new ItemStack(Blocks.SOUL_SAND).getItem();
        this.item = new ItemStack((Block)Blocks.SKULL).getItem();
        this.blockPos = new BlockPos(0, 0, 0);
        this.sd = -1;
    }
    
    public void onEnabled() {
        ++this.sd;
        this.c();
        ++this.sd;
    }
    
    @SubscribeEvent
    public void a(final ServerTick serverTick) {
        if (!this.getEnabled() || this.sd > 1) {
            this.sd = -1;
            this.setEnabled(false);
            return;
        }
        if (this.sd == 0) {
            this.c();
        }
        if (this.sd == 1) {
            this.e();
            this.sd = -1;
            this.setEnabled(false);
            return;
        }
        ++this.sd;
    }
    
    private boolean c() {
        if (AutoWither.mc.objectMouseOver == null || AutoWither.mc.objectMouseOver.sideHit == null) {
            this.blockPos = AutoWither.mc.player.getPosition().add(2, 0, 0);
        }
        else {
            this.blockPos = AutoWither.mc.objectMouseOver.getBlockPos().offset(AutoWither.mc.objectMouseOver.sideHit);
        }
        final int a = f.a(this.item);
        final int f = this.f();
        if (f == -1 || a == -1) {
            Utils.printMessage(((f == -1) ? "Wither Skull" : "Soul Sand") + " was not found in your hotbar!", "red");
            this.setEnabled(false);
            return false;
        }
        AutoWither.mc.player.inventory.currentItem = a.a.k.f.a(this.item);
        a.a.k.f.a(this.blockPos);
        if (g()) {
            a.a.k.f.a(this.blockPos.add(0, 1, 0));
            a.a.k.f.a(this.blockPos.add(1, 1, 0));
            a.a.k.f.a(this.blockPos.add(-1, 1, 0));
        }
        else {
            a.a.k.f.a(this.blockPos.add(0, 1, 0));
            a.a.k.f.a(this.blockPos.add(0, 1, 1));
            a.a.k.f.a(this.blockPos.add(0, 1, -1));
        }
        return true;
    }
    
    private boolean e() {
        final int f = this.f();
        if (f != -1) {
            AutoWither.mc.player.inventory.currentItem = f;
            if (g()) {
                a.a.k.f.a(this.blockPos.add(0, 2, 0));
                a.a.k.f.a(this.blockPos.add(1, 2, 0));
                a.a.k.f.a(this.blockPos.add(-1, 2, 0));
            }
            else {
                a.a.k.f.a(this.blockPos.add(0, 2, 0));
                a.a.k.f.a(this.blockPos.add(0, 2, 1));
                a.a.k.f.a(this.blockPos.add(0, 2, -1));
            }
            return true;
        }
        return false;
    }
    
    private int f() {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stackInSlot = Globals.mc.player.inventory.getStackInSlot(i);
            if (stackInSlot.getItem().getItemStackDisplayName(stackInSlot).equals("Wither Skeleton Skull")) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean g() {
        final EnumFacing horizontalFacing = AutoWither.mc.player.getHorizontalFacing();
        return horizontalFacing != EnumFacing.EAST && horizontalFacing != EnumFacing.WEST;
    }
}
