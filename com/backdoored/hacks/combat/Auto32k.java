package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.utils.*;
import a.a.k.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraft.init.*;

@b.a(name = "Auto32k", description = "Instantly places shulker and hopper and grabs a 32k sword", category = CategoriesInit.COMBAT)
public class Auto32k extends BaseHack
{
    private static final String ko = "blue";
    private Setting<a.a.g.b.c.b.a> mode;
    private Setting<Integer> dispDelay;
    private Setting<Boolean> secretClose;
    private BlockPos blockPos;
    private int kt;
    private int ku;
    private int kv;
    private int kw;
    private int kx;
    private int ky;
    private int kz;
    
    public Auto32k() {
        super();
        this.mode = new EnumSetting<a.a.g.b.c.b.a>("Mode", (BaseHack)this, (Enum)a.a.g.b.c.b.a.kn);
        this.dispDelay = new IntegerSetting("Disp.-Delay", this, 15, 0, 20);
        this.secretClose = new BooleanSetting("SecretClose", this, false);
        this.ku = 0;
    }
    
    public void onEnabled() {
        if (Auto32k.mc.objectMouseOver == null || Auto32k.mc.objectMouseOver.sideHit == null) {
            return;
        }
        if (!this.e()) {
            this.setEnabled(false);
        }
    }
    
    private boolean e() {
        this.blockPos = null;
        this.ku = 0;
        if (Auto32k.mc.objectMouseOver == null || Auto32k.mc.objectMouseOver.sideHit == null) {
            return false;
        }
        this.blockPos = Auto32k.mc.objectMouseOver.getBlockPos().offset(Auto32k.mc.objectMouseOver.sideHit);
        if (new Vec3d(Auto32k.mc.player.posX, Auto32k.mc.player.posY + Auto32k.mc.player.getEyeHeight(), Auto32k.mc.player.posZ).squareDistanceTo(new Vec3d((double)this.blockPos.getX(), (double)this.blockPos.getY(), (double)this.blockPos.getZ())) > 16.0) {
            Utils.printMessage("Location too far away!", "blue");
            return false;
        }
        this.kv = f.a(Item.getItemById(154));
        if (f.a(Item.getItemById(154)) == -1) {
            Utils.printMessage("A hopper was not found in your hotbar!", "blue");
            return false;
        }
        for (int i = 219; i <= 234; ++i) {
            this.kw = f.a(Item.getItemById(i));
            if (this.kw != -1) {
                break;
            }
            if (i == 234) {
                Utils.printMessage("A shulker was not found in your hotbar!", "blue");
                return false;
            }
        }
        if (this.mode.getValInt() == a.a.g.b.c.b.a.kn) {
            this.kx = f();
            if (this.kx == -1) {
                Utils.printMessage("No blocks found in hotbar!", "blue");
                return false;
            }
            this.ky = f.a(Item.getItemById(23));
            if (this.ky == -1) {
                Utils.printMessage("No dispenser found in hotbar!", "blue");
                return false;
            }
            this.kz = f.a(Item.getItemById(152));
            if (this.kz == -1) {
                Utils.printMessage("No redstone block found in hotbar!", "blue");
                return false;
            }
        }
        this.kt = (MathHelper.floor(Auto32k.mc.player.rotationYaw * 4.0f / 360.0f + 0.5) & 0x3);
        return true;
    }
    
    public void onTick() {
        if (Auto32k.mc.player.openContainer instanceof ContainerHopper) {
            for (int i = 0; i < Auto32k.mc.player.openContainer.inventorySlots.size(); ++i) {
                if (a(((Slot)Auto32k.mc.player.openContainer.inventorySlots.get(i)).getStack()) && !a(Auto32k.mc.player.inventoryContainer.inventorySlots.get(Auto32k.mc.player.inventory.currentItem).getStack())) {
                    Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, i, Auto32k.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Auto32k.mc.player);
                    if (this.secretClose.getValInt()) {
                        Auto32k.mc.player.closeScreen();
                    }
                    return;
                }
            }
        }
        if (!this.getEnabled() || this.ku++ == 0) {
            return;
        }
        if (this.mode.getValInt() == a.a.g.b.c.b.a.km) {
            Auto32k.mc.player.inventory.currentItem = this.kv;
            f.a(this.blockPos);
            Auto32k.mc.player.inventory.currentItem = this.kw;
            f.a(new BlockPos(this.blockPos.getX(), this.blockPos.getY() + 1, this.blockPos.getZ()));
            this.c();
        }
        else if (this.mode.getValInt() == a.a.g.b.c.b.a.kn) {
            if (this.ku % this.dispDelay.getValInt() != 0) {
                ++this.ku;
                return;
            }
            BlockPos blockPos = null;
            switch (this.kt) {
                case 0: {
                    blockPos = new BlockPos((Vec3i)this.blockPos.add(0, 0, 1));
                    break;
                }
                case 1: {
                    blockPos = new BlockPos((Vec3i)this.blockPos.add(-1, 0, 0));
                    break;
                }
                case 2: {
                    blockPos = new BlockPos((Vec3i)this.blockPos.add(0, 0, -1));
                    break;
                }
                default: {
                    blockPos = new BlockPos((Vec3i)this.blockPos.add(1, 0, 0));
                    break;
                }
            }
            if (Auto32k.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                Auto32k.mc.player.inventory.currentItem = this.kx;
                f.a(blockPos);
            }
            BlockPos blockPos2 = null;
            switch (this.kt) {
                case 0: {
                    blockPos2 = new BlockPos((Vec3i)this.blockPos.add(0, 1, 1));
                    break;
                }
                case 1: {
                    blockPos2 = new BlockPos((Vec3i)this.blockPos.add(-1, 1, 0));
                    break;
                }
                case 2: {
                    blockPos2 = new BlockPos((Vec3i)this.blockPos.add(0, 1, -1));
                    break;
                }
                default: {
                    blockPos2 = new BlockPos((Vec3i)this.blockPos.add(1, 1, 0));
                    break;
                }
            }
            if (Auto32k.mc.world.getBlockState(blockPos2).getMaterial().isReplaceable()) {
                Auto32k.mc.player.inventory.currentItem = this.ky;
                f.a(blockPos2);
                Auto32k.mc.player.inventory.currentItem = this.kw;
                Auto32k.mc.playerController.processRightClickBlock(Auto32k.mc.player, Auto32k.mc.world, blockPos2, EnumFacing.UP, new Vec3d((double)blockPos2.getX(), (double)blockPos2.getY(), (double)blockPos2.getZ()), EnumHand.MAIN_HAND);
                ++this.ku;
                return;
            }
            if (Auto32k.mc.player.openContainer.inventorySlots.get(1).getStack().isEmpty()) {
                Auto32k.mc.playerController.windowClick(Auto32k.mc.player.openContainer.windowId, Auto32k.mc.player.openContainer.inventorySlots.get(1).slotNumber, Auto32k.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Auto32k.mc.player);
                Auto32k.mc.player.closeScreen();
            }
            BlockPos blockPos3 = null;
            switch (this.kt) {
                case 0: {
                    blockPos3 = new BlockPos((Vec3i)this.blockPos.add(0, 2, 1));
                    break;
                }
                case 1: {
                    blockPos3 = new BlockPos((Vec3i)this.blockPos.add(-1, 2, 0));
                    break;
                }
                case 2: {
                    blockPos3 = new BlockPos((Vec3i)this.blockPos.add(0, 2, -1));
                    break;
                }
                default: {
                    blockPos3 = new BlockPos((Vec3i)this.blockPos.add(1, 2, 0));
                    break;
                }
            }
            if (Auto32k.mc.world.getBlockState(blockPos3).getMaterial().isReplaceable()) {
                Auto32k.mc.player.inventory.currentItem = f.a(Item.getItemById(152));
                f.a(blockPos3);
                ++this.ku;
                return;
            }
            Auto32k.mc.player.inventory.currentItem = this.kv;
            f.a(this.blockPos);
            Auto32k.mc.player.inventory.currentItem = this.kw;
            this.c();
            this.setEnabled(false);
        }
    }
    
    private void c() {
        if (this.secretClose.getValInt()) {
            BaseHack.a("Secret Close", false);
            BaseHack.a("Secret Close", true);
        }
        Auto32k.mc.playerController.processRightClickBlock(Auto32k.mc.player, Auto32k.mc.world, this.blockPos, EnumFacing.UP, new Vec3d((double)this.blockPos.getX(), (double)this.blockPos.getY(), (double)this.blockPos.getZ()), EnumHand.MAIN_HAND);
    }
    
    public static boolean a(final ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.getTagCompound() == null) {
            return false;
        }
        if (itemStack.getEnchantmentTagList().getTagType() == 0) {
            return false;
        }
        final NBTTagList list = (NBTTagList)itemStack.getTagCompound().getTag("ench");
        int i = 0;
        while (i < list.tagCount()) {
            final NBTTagCompound compoundTag = list.getCompoundTagAt(i);
            if (compoundTag.getInteger("id") == 16) {
                if (compoundTag.getInteger("lvl") >= 16) {
                    return true;
                }
                break;
            }
            else {
                ++i;
            }
        }
        return false;
    }
    
    public static int f() {
        for (int i = 0; i < 9; ++i) {
            if (Auto32k.mc.player.inventory.getStackInSlot(i) != ItemStack.EMPTY && Auto32k.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && Block.getBlockFromItem(Auto32k.mc.player.inventory.getStackInSlot(i).getItem()).getDefaultState().isFullBlock() && !Block.getBlockFromItem(Auto32k.mc.player.inventory.getStackInSlot(i).getItem()).equals(Blocks.REDSTONE_BLOCK) && !Block.getBlockFromItem(Auto32k.mc.player.inventory.getStackInSlot(i).getItem()).equals(Blocks.DISPENSER)) {
                return i;
            }
        }
        return -1;
    }
}
