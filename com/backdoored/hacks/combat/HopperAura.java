package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.util.math.*;
import a.a.g.b.c.*;
import com.backdoored.setting.*;
import java.util.function.*;
import java.util.stream.*;
import net.minecraft.item.*;
import a.a.k.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import java.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.tileentity.*;

@b.a(name = "HopperAura", description = "Break nearby hoppers", category = CategoriesInit.COMBAT)
public class HopperAura extends BaseHack
{
    private Set<BlockPos> nl;
    private int[] nm;
    private Setting<Double> nn;
    private Setting<Boolean> lockRotations;
    private Setting<Boolean> breakOwn;
    
    public HopperAura() {
        super();
        this.nl = (Set<BlockPos>)new l.l$a(this);
        this.nm = new int[] { 278, 285, 274, 270, 257 };
        this.nn = new DoubleSetting("Distance", this, 5.0, 1.0, 10.0);
        this.lockRotations = new BooleanSetting("LockRotations", this, true);
        this.breakOwn = new BooleanSetting("Break Own", this, false);
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        final List list = (List)HopperAura.mc.world.loadedTileEntityList.stream().filter(l::a).collect(Collectors.toList());
        if (list.size() > 0) {
            for (final TileEntity tileEntity : list) {
                final BlockPos pos = tileEntity.getPos();
                if (!this.breakOwn.getValInt() && this.nl.contains(pos)) {
                    continue;
                }
                if (HopperAura.mc.player.getDistance((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()) > this.nn.getValInt()) {
                    continue;
                }
                final int[] nm = this.nm;
                for (int length = nm.length, i = 0; i < length; ++i) {
                    final int a = f.a(Item.getItemById(nm[i]));
                    if (a != -1) {
                        HopperAura.mc.player.inventory.currentItem = a;
                        if (this.lockRotations.getValInt()) {
                            f.b(pos);
                        }
                        HopperAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, tileEntity.getPos(), EnumFacing.UP));
                        HopperAura.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, tileEntity.getPos(), EnumFacing.UP));
                        return;
                    }
                }
            }
        }
    }
    
    public void onDisabled() {
        this.nl.clear();
    }
    
    @SubscribeEvent
    public void a(final PlayerInteractEvent.RightClickBlock rightClickBlock) {
        if (HopperAura.mc.player.inventory.getStackInSlot(HopperAura.mc.player.inventory.currentItem).getItem().equals(Item.getItemById(154))) {
            this.nl.add(HopperAura.mc.objectMouseOver.getBlockPos().offset(HopperAura.mc.objectMouseOver.sideHit));
        }
    }
    
    private static /* synthetic */ boolean a(final TileEntity tileEntity) {
        return tileEntity instanceof TileEntityHopper;
    }
}
