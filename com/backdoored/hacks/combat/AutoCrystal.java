package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.entity.player.*;
import a.a.k.*;
import net.minecraft.entity.item.*;
import a.a.g.b.c.*;
import java.util.function.*;
import com.backdoored.utils.*;
import java.util.stream.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

@b.a(name = "Auto Crystal", description = "Auto Place Crystals, Modified Kami paste", category = CategoriesInit.COMBAT)
public class AutoCrystal extends BaseHack
{
    private Setting<Integer> placePerTick;
    private Setting<Boolean> place;
    private Setting<Boolean> break;
    private Setting<Boolean> switch;
    private Setting<Boolean> noGappleSwitch;
    private Setting<Boolean> dontcancelmining;
    private Setting<Double> placeRange;
    private Setting<Double> breakRange;
    private Setting<Double> rayPlaceRange;
    private Setting<Double> minDamage;
    private BlockPos currentTarget;
    private Entity currentEntTarget;
    private long systemTime;
    
    public AutoCrystal() {
        super();
        this.placePerTick = new IntegerSetting("Place Per Tick", this, 1, 0, 6);
        this.place = new BooleanSetting("Place", this, true);
        this.break = new BooleanSetting("Break", this, true);
        this.switch = new BooleanSetting("Switch", this, true);
        this.noGappleSwitch = new BooleanSetting("No Gapple Switch", this, true);
        this.dontcancelmining = new BooleanSetting("Dont cancel mining", this, true);
        this.placeRange = new DoubleSetting("Place Range", this, 4.0, 0.0, 10.0);
        this.breakRange = new DoubleSetting("Break Range", this, 4.0, 0.0, 10.0);
        this.rayPlaceRange = new DoubleSetting("Raytrace Place Range", this, 3.0, 0.0, 10.0);
        this.minDamage = new DoubleSetting("Min Damage", this, 4.0, 0.0, 20.0);
        this.systemTime = -1L;
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        if (this.break.getValInt()) {
            this.breakCrystals();
        }
        if (this.place.getValInt()) {
            for (int i = 0; i < this.placePerTick.getValInt(); ++i) {
                this.placeCrystals();
            }
        }
    }
    
    private void placeCrystals() {
        this.currentTarget = null;
        this.currentEntTarget = null;
        final boolean b = !this.noGappleSwitch.getValInt() || AutoCrystal.mc.player.getActiveItemStack().getItem() != Items.GOLDEN_APPLE;
        final boolean b2 = !this.dontcancelmining.getValInt() || AutoCrystal.mc.player.getActiveItemStack().getItem() != Items.DIAMOND_PICKAXE;
        if (!b || !b2) {
            return;
        }
        final List<BlockPos> availableCrystalBlocks = this.findAvailableCrystalBlocks();
        final ArrayList<EntityPlayer> list = new ArrayList<EntityPlayer>();
        for (final EntityPlayer entityPlayer : AutoCrystal.mc.world.playerEntities) {
            if (!FriendUtils.a(entityPlayer)) {
                list.add(entityPlayer);
            }
        }
        double n = 0.1;
        double n2 = 1000.0;
        Vec3i vec3i = null;
        for (final EntityPlayer entityPlayer2 : list) {
            if (!entityPlayer2.getUniqueID().equals(AutoCrystal.mc.player.getUniqueID())) {
                if (entityPlayer2.isDead) {
                    continue;
                }
                for (final BlockPos blockPos : availableCrystalBlocks) {
                    if (entityPlayer2.getDistanceSq(blockPos) >= 169.0) {
                        continue;
                    }
                    final double n3 = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)entityPlayer2) / 10.0f;
                    final double n4 = calculateDamage(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, (Entity)AutoCrystal.mc.player) / 10.0f;
                    if (n3 < this.minDamage.getValInt()) {
                        continue;
                    }
                    final RayTraceResult rayTraceBlocks = AutoCrystal.mc.world.rayTraceBlocks(new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY + AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ), new Vec3d(blockPos.getX() + 0.5, blockPos.getY() - 0.5, blockPos.getZ() + 0.5));
                    final boolean b3 = (rayTraceBlocks != null && rayTraceBlocks.typeOfHit == RayTraceResult.Type.BLOCK) || AutoCrystal.mc.player.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()) <= this.rayPlaceRange.getValInt();
                    if (AutoCrystal.mc.player.getDistance((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ()) > this.placeRange.getValInt() || !b3) {
                        continue;
                    }
                    if (n3 > n) {
                        n = n3;
                        n2 = n4;
                        vec3i = (Vec3i)blockPos;
                        this.currentEntTarget = (Entity)entityPlayer2;
                        this.currentTarget = blockPos;
                    }
                    else {
                        if (n3 != n || n4 >= n2) {
                            continue;
                        }
                        n = n3;
                        n2 = n4;
                        vec3i = (Vec3i)blockPos;
                        this.currentEntTarget = (Entity)entityPlayer2;
                        this.currentTarget = blockPos;
                    }
                }
            }
        }
        if (n < this.minDamage.getValInt() || vec3i == null) {
            return;
        }
        final boolean b4 = AutoCrystal.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL || AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL;
        if (!b4 && !this.switch.getValInt()) {
            return;
        }
        if (b4) {
            final RayTraceResult rayTraceBlocks2 = AutoCrystal.mc.world.rayTraceBlocks(new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY + AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ), new Vec3d(((BlockPos)vec3i).getX() + 0.5, ((BlockPos)vec3i).getY() - 0.5, ((BlockPos)vec3i).getZ() + 0.5));
            EnumFacing enumFacing;
            if (rayTraceBlocks2 == null || rayTraceBlocks2.sideHit == null) {
                enumFacing = EnumFacing.UP;
            }
            else {
                enumFacing = rayTraceBlocks2.sideHit;
            }
            EnumHand enumHand = EnumHand.MAIN_HAND;
            if (AutoCrystal.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL) {
                enumHand = EnumHand.OFF_HAND;
            }
            AutoCrystal.mc.playerController.processRightClickBlock(AutoCrystal.mc.player, AutoCrystal.mc.world, (BlockPos)vec3i, enumFacing, new Vec3d(vec3i).add(0.5, 0.5, 0.5).add(new Vec3d(enumFacing.getDirectionVec()).scale(0.5)), enumHand);
            AutoCrystal.mc.player.swingArm(enumHand);
            return;
        }
        final int a = f.a(Items.END_CRYSTAL);
        if (a >= 0) {
            AutoCrystal.mc.player.inventory.currentItem = a;
        }
    }
    
    private void breakCrystals() {
        final EntityEnderCrystal entityEnderCrystal = (EntityEnderCrystal)AutoCrystal.mc.world.loadedEntityList.stream().filter(d::b).map(d::a).min(Comparator.comparing((Function<? super T, ? extends Comparable>)d::a)).orElse(null);
        if (entityEnderCrystal != null) {
            final double n = AutoCrystal.mc.player.getDistance((Entity)entityEnderCrystal);
            boolean b = true;
            final RayTraceResult rayTraceBlocks = AutoCrystal.mc.world.rayTraceBlocks(new Vec3d(AutoCrystal.mc.player.posX, AutoCrystal.mc.player.posY + AutoCrystal.mc.player.getEyeHeight(), AutoCrystal.mc.player.posZ), new Vec3d(entityEnderCrystal.posX + 0.5, entityEnderCrystal.posY - 0.5, entityEnderCrystal.posZ + 0.5));
            if (rayTraceBlocks != null && rayTraceBlocks.typeOfHit == RayTraceResult.Type.BLOCK) {
                b = (n <= this.rayPlaceRange.getValInt());
            }
            if (n <= this.placeRange.getValInt() && b && System.nanoTime() / 1000000L - this.systemTime >= 250L) {
                AutoCrystal.mc.playerController.attackEntity((EntityPlayer)AutoCrystal.mc.player, (Entity)entityEnderCrystal);
                AutoCrystal.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.systemTime = System.nanoTime() / 1000000L;
            }
        }
    }
    
    public void onRender() {
        RenderUtils.glStart(255.0f, 255.0f, 255.0f, 1.0f);
        if (this.currentTarget != null) {
            RenderUtils.drawOutlinedBox(RenderUtils.getBoundingBox(this.currentTarget));
        }
        if (this.currentEntTarget != null) {
            RenderUtils.drawOutlinedBox(this.currentEntTarget.getEntityBoundingBox());
        }
        RenderUtils.glEnd();
    }
    
    public void onUpdate() {
    }
    
    public void onDisabled() {
        this.currentTarget = null;
        this.currentEntTarget = null;
    }
    
    private List<BlockPos> findAvailableCrystalBlocks() {
        final double doubleValue = this.placeRange.getValInt();
        final NonNullList create = NonNullList.create();
        create.addAll((Collection)getSphere(new BlockPos(Math.floor(AutoCrystal.mc.player.posX), Math.floor(AutoCrystal.mc.player.posY), Math.floor(AutoCrystal.mc.player.posZ)), (float)doubleValue, (int)Math.round(this.placeRange.getValInt()), false, true, 0).stream().filter((Predicate<? super Object>)this::a).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList()));
        return (List<BlockPos>)create;
    }
    
    public static List<BlockPos> getSphere(final BlockPos blockPos, final float n, final int n2, final boolean b, final boolean b2, final int n3) {
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final int x = blockPos.getX();
        final int y = blockPos.getY();
        final int z = blockPos.getZ();
        for (int n4 = x - (int)n; n4 <= x + n; ++n4) {
            for (int n5 = z - (int)n; n5 <= z + n; ++n5) {
                for (int n6 = b2 ? (y - (int)n) : y; n6 < (b2 ? (y + n) : ((float)(y + n2))); ++n6) {
                    final double n7 = (x - n4) * (x - n4) + (z - n5) * (z - n5) + (b2 ? ((y - n6) * (y - n6)) : 0);
                    if (n7 < n * n && (!b || n7 >= (n - 1.0f) * (n - 1.0f))) {
                        list.add(new BlockPos(n4, n6 + n3, n5));
                    }
                }
            }
        }
        return list;
    }
    
    private boolean canPlaceCrystal(final BlockPos blockPos) {
        final BlockPos add = blockPos.add(0, 1, 0);
        final BlockPos add2 = blockPos.add(0, 2, 0);
        return (AutoCrystal.mc.world.getBlockState(blockPos).getBlock() == Blocks.BEDROCK || AutoCrystal.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) && AutoCrystal.mc.world.getBlockState(add).getBlock() == Blocks.AIR && AutoCrystal.mc.world.getBlockState(add2).getBlock() == Blocks.AIR && AutoCrystal.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(add)).isEmpty();
    }
    
    private static float calculateDamage(final double n, final double n2, final double n3, final Entity entity) {
        final double n4 = (1.0 - entity.getDistance(n, n2, n3) / 12.0) * entity.world.getBlockDensity(new Vec3d(n, n2, n3), entity.getEntityBoundingBox());
        final float n5 = (float)(int)((n4 * n4 + n4) / 2.0 * 7.0 * 12.0 + 1.0);
        double n6 = 1.0;
        if (entity instanceof EntityLivingBase) {
            n6 = getBlastReduction((EntityLivingBase)entity, getDamageMultiplied(n5), new Explosion((World)AutoCrystal.mc.world, (Entity)null, n, n2, n3, 6.0f, false, true));
        }
        return (float)n6;
    }
    
    private static float getBlastReduction(final EntityLivingBase entityLivingBase, float n, final Explosion explosion) {
        if (entityLivingBase instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            n = CombatRules.getDamageAfterAbsorb(n, (float)entityPlayer.getTotalArmorValue(), (float)entityPlayer.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
            return n;
        }
        n = CombatRules.getDamageAfterAbsorb(n, (float)entityLivingBase.getTotalArmorValue(), (float)entityLivingBase.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
        return n;
    }
    
    private static float getDamageMultiplied(final float n) {
        final int id = AutoCrystal.mc.world.getDifficulty().getId();
        return n * ((id == 0) ? 0.0f : ((id == 2) ? 1.0f : ((id == 1) ? 0.5f : 1.5f)));
    }
    
    private static /* synthetic */ Float a(final EntityEnderCrystal entityEnderCrystal) {
        return AutoCrystal.mc.player.getDistance((Entity)entityEnderCrystal);
    }
    
    private static /* synthetic */ EntityEnderCrystal a(final Entity entity) {
        return (EntityEnderCrystal)entity;
    }
    
    private static /* synthetic */ boolean b(final Entity entity) {
        return entity instanceof EntityEnderCrystal;
    }
}
