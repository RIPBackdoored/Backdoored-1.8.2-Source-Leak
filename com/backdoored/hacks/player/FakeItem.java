package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.event.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

@b.a(name = "Fake Item", description = "Always be holding your first item", category = CategoriesInit.PLAYER)
public class FakeItem extends BaseHack
{
    private boolean sq;
    
    public FakeItem() {
        super();
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled()) {
            if (packetRecieved.packet instanceof CPacketPlayerTryUseItemOnBlock) {
                if (this.sq) {
                    this.sq = false;
                    return;
                }
                packetRecieved.setCanceled(true);
                final CPacketPlayerTryUseItemOnBlock cPacketPlayerTryUseItemOnBlock = (CPacketPlayerTryUseItemOnBlock)packetRecieved.packet;
                final BlockPos pos = cPacketPlayerTryUseItemOnBlock.getPos();
                final EnumFacing direction = cPacketPlayerTryUseItemOnBlock.getDirection();
                final EnumHand hand = cPacketPlayerTryUseItemOnBlock.getHand();
                final float facingX = cPacketPlayerTryUseItemOnBlock.getFacingX();
                final float facingY = cPacketPlayerTryUseItemOnBlock.getFacingY();
                final float facingZ = cPacketPlayerTryUseItemOnBlock.getFacingZ();
                c();
                this.sq = true;
                FakeItem.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(pos, direction, hand, facingX, facingY, facingZ));
                c();
            }
            if (packetRecieved.packet instanceof CPacketPlayerTryUseItem) {
                if (this.sq) {
                    this.sq = false;
                    return;
                }
                final EnumHand hand2 = ((CPacketPlayerTryUseItem)packetRecieved.packet).getHand();
                packetRecieved.setCanceled(true);
                c();
                this.sq = true;
                FakeItem.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(hand2));
                c();
            }
            if (packetRecieved.packet instanceof CPacketUseEntity) {
                if (this.sq) {
                    this.sq = false;
                    return;
                }
                final CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)packetRecieved.packet;
                if (cPacketUseEntity.getAction() == CPacketUseEntity.Action.ATTACK) {
                    final Entity entityFromWorld = cPacketUseEntity.getEntityFromWorld((World)FakeItem.mc.world);
                    if (entityFromWorld != null) {
                        packetRecieved.setCanceled(true);
                        c();
                        this.sq = true;
                        FakeItem.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(entityFromWorld));
                        c();
                    }
                }
            }
        }
    }
    
    private static void c() {
        FakeItem.mc.playerController.windowClick(FakeItem.mc.player.inventoryContainer.windowId, 9, FakeItem.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)FakeItem.mc.player);
    }
}
