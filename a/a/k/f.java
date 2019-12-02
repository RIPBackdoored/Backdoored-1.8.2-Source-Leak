package a.a.k;

import a.a.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;

public class f implements e
{
    public f() {
        super();
    }
    
    public static void a(final BlockPos blockPos) {
        a(EnumHand.MAIN_HAND, blockPos);
    }
    
    public static void a(final EnumHand enumHand, final BlockPos blockPos) {
        final Vec3d vec3d = new Vec3d(f.mc.player.posX, f.mc.player.posY + f.mc.player.getEyeHeight(), f.mc.player.posZ);
        for (final EnumFacing enumFacing : EnumFacing.values()) {
            final BlockPos offset = blockPos.offset(enumFacing);
            final EnumFacing opposite = enumFacing.getOpposite();
            if (f.mc.world.getBlockState(offset).getBlock().canCollideCheck(f.mc.world.getBlockState(offset), false)) {
                final Vec3d add = new Vec3d((Vec3i)offset).add(0.5, 0.5, 0.5).add(new Vec3d(opposite.getDirectionVec()).scale(0.5));
                if (vec3d.squareDistanceTo(add) <= 18.0625) {
                    final double n = add.x - vec3d.x;
                    final double n2 = add.y - vec3d.y;
                    final double n3 = add.z - vec3d.z;
                    final float[] array = { f.mc.player.rotationYaw + MathHelper.wrapDegrees((float)Math.toDegrees(Math.atan2(n3, n)) - 90.0f - f.mc.player.rotationYaw), f.mc.player.rotationPitch + MathHelper.wrapDegrees((float)(-Math.toDegrees(Math.atan2(n2, Math.sqrt(n * n + n3 * n3)))) - f.mc.player.rotationPitch) };
                    f.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(array[0], array[1], f.mc.player.onGround));
                    f.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)f.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                    f.mc.playerController.processRightClickBlock(f.mc.player, f.mc.world, offset, opposite, add, enumHand);
                    f.mc.player.swingArm(enumHand);
                    f.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)f.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                    return;
                }
            }
        }
    }
    
    public static int a(final Block block) {
        return a(new ItemStack(block).getItem());
    }
    
    public static int a(final Item item) {
        try {
            for (int i = 0; i < 9; ++i) {
                if (item == f.mc.player.inventory.getStackInSlot(i).getItem()) {
                    return i;
                }
            }
        }
        catch (Exception ex) {}
        return -1;
    }
    
    public static double[] a(final double n, final double n2, final double n3, final EntityPlayer entityPlayer) {
        final double n4 = entityPlayer.posX - n;
        final double n5 = entityPlayer.posY - n2;
        final double n6 = entityPlayer.posZ - n3;
        final double sqrt = Math.sqrt(n4 * n4 + n5 * n5 + n6 * n6);
        return new double[] { Math.atan2(n6 / sqrt, n4 / sqrt) * 180.0 / 3.141592653589793 + 90.0, Math.asin(n5 / sqrt) * 180.0 / 3.141592653589793 };
    }
    
    public static void a(final float rotationYaw, final float rotationPitch) {
        f.mc.player.rotationYaw = rotationYaw;
        f.mc.player.rotationPitch = rotationPitch;
    }
    
    public static void a(final double[] array) {
        f.mc.player.rotationYaw = (float)array[0];
        f.mc.player.rotationPitch = (float)array[1];
    }
    
    public static void b(final BlockPos blockPos) {
        a(a(blockPos.getX(), blockPos.getY(), blockPos.getZ(), (EntityPlayer)f.mc.player));
    }
    
    public static BlockPos a(final EntityPlayer entityPlayer, final int n, final int n2, final int n3) {
        final int[] array = { (int)entityPlayer.posX, (int)entityPlayer.posY, (int)entityPlayer.posZ };
        BlockPos blockPos;
        if (entityPlayer.posX < 0.0 && entityPlayer.posZ < 0.0) {
            blockPos = new BlockPos(array[0] + n - 1, array[1] + n2, array[2] + n3 - 1);
        }
        else if (entityPlayer.posX < 0.0 && entityPlayer.posZ > 0.0) {
            blockPos = new BlockPos(array[0] + n - 1, array[1] + n2, array[2] + n3);
        }
        else if (entityPlayer.posX > 0.0 && entityPlayer.posZ < 0.0) {
            blockPos = new BlockPos(array[0] + n, array[1] + n2, array[2] + n3 - 1);
        }
        else {
            blockPos = new BlockPos(array[0] + n, array[1] + n2, array[2] + n3);
        }
        return blockPos;
    }
    
    public static int b() {
        for (int i = 0; i < 9; ++i) {
            if (f.mc.player.inventory.getStackInSlot(i) != ItemStack.EMPTY && f.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && Block.getBlockFromItem(f.mc.player.inventory.getStackInSlot(i).getItem()).getDefaultState().isFullBlock()) {
                return i;
            }
        }
        return -1;
    }
}
