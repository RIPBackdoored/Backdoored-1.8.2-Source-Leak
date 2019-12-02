package com.backdoored.commands;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import a.a.k.*;
import com.backdoored.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Nomadbase extends CommandBase
{
    public boolean be;
    public int bf;
    public boolean bg;
    public int bh;
    public BlockPos[] blockPos;
    
    public Nomadbase() {
        super(new String[] { "nomadbase", "fitbase", "autonomadbase" });
        this.be = false;
        this.bf = 0;
        this.bg = false;
        this.bh = 0;
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length == 0) {
            this.bh = 0;
            this.be = true;
        }
        if ((array.length > 1 && array[0].equalsIgnoreCase("delay")) || array[0].equalsIgnoreCase("setdelay")) {
            this.bf = Integer.valueOf(array[1]);
            if (this.bf == 0) {
                this.bg = false;
            }
            else {
                this.bg = true;
            }
        }
        return true;
    }
    
    @SubscribeEvent
    public void a(final TickEvent.ClientTickEvent clientTickEvent) {
        if (!this.be) {
            return;
        }
        if (this.bg && this.bh % this.bf != 0) {
            ++this.bh;
            return;
        }
        final BlockPos[] array = { new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, -1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, -1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, -1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, -1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, -1, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, -1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, -1, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, -1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, -1, -1)) };
        final BlockPos[] array2 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 1, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 1, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 1, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 1, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 1, -2)) };
        final BlockPos[] array3 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 2, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 2, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 2, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 2, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 2, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 2, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 2, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 2, -2)) };
        final BlockPos[] array4 = { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 2, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 2, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 2, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 2, -2)) };
        final int n = MathHelper.floor(this.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3;
        BlockPos[] array5;
        BlockPos[] array6;
        if (n == 0) {
            array5 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            array6 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)) };
        }
        else if (n == 1) {
            array5 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            array6 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)) };
        }
        else if (n == 2) {
            array5 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            array6 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)) };
        }
        else {
            array5 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 0, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, 2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 0, -2)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 0, -2)) };
            array6 = new BlockPos[] { new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-2, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(-1, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(0, 3, -1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 0)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, 1)), new BlockPos((Vec3i)this.mc.player.getPosition().add(1, 3, -1)) };
        }
        final int currentItem = this.mc.player.inventory.currentItem;
        int currentItem2 = -1;
        for (int i = 0; i < 9; ++i) {
            if (this.mc.player.inventory.getStackInSlot(i) != ItemStack.EMPTY && this.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && Block.getBlockFromItem(this.mc.player.inventory.getStackInSlot(i).getItem()).getDefaultState().isFullBlock()) {
                currentItem2 = i;
                break;
            }
        }
        if (currentItem2 != -1) {
            this.mc.player.inventory.currentItem = currentItem2;
            for (final BlockPos blockPos : array) {
                if (this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                    f.a(blockPos);
                    if (this.bg) {
                        ++this.bh;
                        return;
                    }
                }
            }
            for (final BlockPos blockPos2 : array5) {
                if (this.mc.world.getBlockState(blockPos2).getMaterial().isReplaceable()) {
                    f.a(blockPos2);
                    if (this.bg) {
                        ++this.bh;
                        return;
                    }
                }
            }
            for (final BlockPos blockPos3 : array2) {
                if (this.mc.world.getBlockState(blockPos3).getMaterial().isReplaceable()) {
                    f.a(blockPos3);
                    if (this.bg) {
                        ++this.bh;
                        return;
                    }
                }
            }
            for (final BlockPos blockPos4 : array3) {
                if (this.mc.world.getBlockState(blockPos4).getMaterial().isReplaceable()) {
                    f.a(blockPos4);
                    if (this.bg) {
                        ++this.bh;
                        return;
                    }
                }
            }
            for (final BlockPos blockPos5 : array4) {
                if (this.mc.world.getBlockState(blockPos5).getMaterial().isReplaceable()) {
                    f.a(blockPos5);
                    if (this.bg) {
                        ++this.bh;
                        return;
                    }
                }
            }
            for (final BlockPos blockPos6 : array6) {
                if (this.mc.world.getBlockState(blockPos6).getMaterial().isReplaceable()) {
                    f.a(blockPos6);
                    if (this.bg) {
                        ++this.bh;
                        return;
                    }
                }
            }
            this.mc.player.inventory.currentItem = currentItem;
            this.be = false;
            return;
        }
        Utils.printMessage("No blocks found in hotbar!", "red");
        this.be = false;
    }
    
    @Override
    public String a() {
        return "-nomadbase or -nomadbase setdelay <0/1/2/..> (6 is the best)";
    }
}
