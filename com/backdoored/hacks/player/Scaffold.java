package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import a.a.k.*;
import com.backdoored.utils.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.entity.*;
import a.a.d.b.f.*;

@b.a(name = "Scaffold", description = "Automatically bridges for you", category = CategoriesInit.PLAYER)
public class Scaffold extends BaseHack
{
    private Setting<Integer> radius;
    private Setting<Boolean> down;
    private Setting<Boolean> tower;
    
    public Scaffold() {
        super();
        this.radius = new IntegerSetting("Radius", this, 0, 0, 2);
        this.down = new BooleanSetting("Down", this, true);
        this.tower = new BooleanSetting("Tower", this, true);
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        final int currentItem = Scaffold.mc.player.inventory.currentItem;
        final int b = f.b();
        if (b == -1) {
            Utils.printMessage("No blocks found in hotbar!", "red");
            this.setEnabled(false);
            return;
        }
        Scaffold.mc.player.inventory.currentItem = b;
        if (this.radius.getValInt() != 0 && this.down.getValInt()) {
            this.radius.a(0);
        }
        if (Scaffold.mc.gameSettings.keyBindSprint.isKeyDown() && this.down.getValInt()) {
            final float n = (float)Math.toRadians(Scaffold.mc.player.rotationYaw);
            if (Scaffold.mc.gameSettings.keyBindForward.isKeyDown()) {
                Scaffold.mc.player.motionX = -MathHelper.sin(n) * 0.03f;
                Scaffold.mc.player.motionZ = MathHelper.cos(n) * 0.03f;
            }
            if (Scaffold.mc.gameSettings.keyBindBack.isKeyDown()) {
                Scaffold.mc.player.motionX = MathHelper.sin(n) * 0.03f;
                Scaffold.mc.player.motionZ = -MathHelper.cos(n) * 0.03f;
            }
            if (Scaffold.mc.gameSettings.keyBindLeft.isKeyDown()) {
                Scaffold.mc.player.motionX = MathHelper.cos(n) * 0.03f;
                Scaffold.mc.player.motionZ = MathHelper.sin(n) * 0.03f;
            }
            if (Scaffold.mc.gameSettings.keyBindRight.isKeyDown()) {
                Scaffold.mc.player.motionX = -MathHelper.cos(n) * 0.03f;
                Scaffold.mc.player.motionZ = -MathHelper.sin(n) * 0.03f;
            }
            final BlockPos blockPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 2.0, Scaffold.mc.player.posZ);
            if (Scaffold.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                f.a(blockPos);
            }
            if (Math.abs(Scaffold.mc.player.motionX) > 0.03 && Scaffold.mc.world.getBlockState(new BlockPos(blockPos.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)(blockPos.getY() - 1), (double)blockPos.getZ())).getMaterial().isReplaceable()) {
                f.a(new BlockPos(blockPos.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)(blockPos.getY() - 1), (double)blockPos.getZ()));
            }
            else if (Math.abs(Scaffold.mc.player.motionZ) > 0.03 && Scaffold.mc.world.getBlockState(new BlockPos((double)blockPos.getX(), (double)(blockPos.getY() - 1), blockPos.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ))).getMaterial().isReplaceable()) {
                f.a(new BlockPos((double)blockPos.getX(), (double)(blockPos.getY() - 1), blockPos.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ)));
            }
            Scaffold.mc.player.inventory.currentItem = currentItem;
            return;
        }
        if (this.radius.getValInt() == 0) {
            final BlockPos blockPos2 = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ);
            if (Scaffold.mc.world.getBlockState(blockPos2).getMaterial().isReplaceable()) {
                f.a(blockPos2);
            }
            if (Math.abs(Scaffold.mc.player.motionX) > 0.033 && Scaffold.mc.world.getBlockState(new BlockPos(blockPos2.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)blockPos2.getY(), (double)blockPos2.getZ())).getMaterial().isReplaceable()) {
                f.a(new BlockPos(blockPos2.getX() + Scaffold.mc.player.motionX / Math.abs(Scaffold.mc.player.motionX), (double)blockPos2.getY(), (double)blockPos2.getZ()));
            }
            else if (Math.abs(Scaffold.mc.player.motionZ) > 0.033 && Scaffold.mc.world.getBlockState(new BlockPos((double)blockPos2.getX(), (double)blockPos2.getY(), blockPos2.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ))).getMaterial().isReplaceable()) {
                f.a(new BlockPos((double)blockPos2.getX(), (double)blockPos2.getY(), blockPos2.getZ() + Scaffold.mc.player.motionZ / Math.abs(Scaffold.mc.player.motionZ)));
            }
            Scaffold.mc.player.inventory.currentItem = currentItem;
            return;
        }
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        for (int i = -this.radius.getValInt(); i <= this.radius.getValInt(); ++i) {
            for (int j = -this.radius.getValInt(); j <= this.radius.getValInt(); ++j) {
                list.add(new BlockPos(Scaffold.mc.player.posX + i, Scaffold.mc.player.posY - 1.0, Scaffold.mc.player.posZ + j));
            }
        }
        for (final BlockPos blockPos3 : list) {
            if (Scaffold.mc.world.getBlockState(blockPos3).getMaterial().isReplaceable()) {
                f.a(blockPos3);
            }
        }
        Scaffold.mc.player.inventory.currentItem = currentItem;
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingUpdateEvent livingUpdateEvent) {
        if (!this.getEnabled() || !this.down.getValInt()) {
            return;
        }
        if (livingUpdateEvent.getEntityLiving() instanceof EntityPlayer && ((EntityPlayer)livingUpdateEvent.getEntityLiving()).isSneaking()) {
            if (Math.abs(Scaffold.mc.player.posX) - (int)Math.abs(Scaffold.mc.player.posX) < 0.1 || Math.abs(Scaffold.mc.player.posX) - (int)Math.abs(Scaffold.mc.player.posX) > 0.9) {
                Scaffold.mc.player.posX = (double)Math.round(Math.abs(Scaffold.mc.player.posX) - (int)Math.abs(Scaffold.mc.player.posX));
            }
            if (Math.abs(Scaffold.mc.player.posZ) - (int)Math.abs(Scaffold.mc.player.posZ) < 0.1 || Math.abs(Scaffold.mc.player.posZ) - (int)Math.abs(Scaffold.mc.player.posZ) > 0.9) {
                Scaffold.mc.player.posZ = (double)Math.round(Math.abs(Scaffold.mc.player.posZ) - (int)Math.abs(Scaffold.mc.player.posZ));
            }
        }
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingJumpEvent livingJumpEvent) {
        if (this.getEnabled() && this.tower.getValInt()) {
            final EntityPlayerSP player = Scaffold.mc.player;
            player.motionY += 0.1;
        }
    }
    
    @SubscribeEvent
    public void a(final d d) {
        if (this.getEnabled()) {
            d.cz = true;
        }
    }
}
