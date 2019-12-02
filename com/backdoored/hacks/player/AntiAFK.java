package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.util.regex.*;
import com.backdoored.setting.*;
import java.time.*;
import java.time.temporal.*;
import net.minecraft.network.*;
import a.a.g.b.g.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.common.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.client.settings.*;
import net.minecraft.util.math.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "AntiAFK", description = "prevent being afk kicked", category = CategoriesInit.PLAYER)
public class AntiAFK extends BaseHack
{
    private static final Pattern rr;
    private final Random rs;
    private Instant rt;
    private Setting<Boolean> autoReply;
    private Setting<Integer> delayms;
    
    public AntiAFK() {
        super();
        this.rs = new Random();
        this.rt = Instant.EPOCH;
        this.autoReply = new BooleanSetting("Auto Reply", this, true);
        this.delayms = new IntegerSetting("Delay ms", this, 1000, 100, 5000);
    }
    
    @Override
    protected void onTick() {
        if (this.getEnabled()) {
            if (Duration.between(this.rt, Instant.now()).toMillis() >= this.delayms.getValInt()) {
                this.rt = Instant.now();
                switch (this.rs.nextInt(9)) {
                    case 0: {
                        AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(this.rs.nextBoolean() ? ((float)this.rs.nextInt(90)) : ((float)(-this.rs.nextInt(90))), this.rs.nextBoolean() ? ((float)this.rs.nextInt(90)) : ((float)(-this.rs.nextInt(90))), AntiAFK.mc.player.onGround));
                        break;
                    }
                    case 1: {
                        AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketAnimation(this.rs.nextBoolean() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND));
                        break;
                    }
                    case 2: {
                        if (AntiAFK.mc.objectMouseOver != null) {
                            switch (a.a$a.rq[AntiAFK.mc.objectMouseOver.typeOfHit.ordinal()]) {
                                case 1: {
                                    AntiAFK.mc.playerController.attackEntity((EntityPlayer)AntiAFK.mc.player, AntiAFK.mc.objectMouseOver.entityHit);
                                    break;
                                }
                                case 2: {
                                    final BlockPos blockPos = AntiAFK.mc.objectMouseOver.getBlockPos();
                                    if (!AntiAFK.mc.world.isAirBlock(blockPos)) {
                                        AntiAFK.mc.playerController.clickBlock(blockPos, AntiAFK.mc.objectMouseOver.sideHit);
                                        break;
                                    }
                                }
                                case 3: {
                                    ForgeHooks.onEmptyLeftClick((EntityPlayer)AntiAFK.mc.player);
                                    AntiAFK.mc.player.swingArm(AntiAFK.mc.player.getActiveHand());
                                    break;
                                }
                            }
                            break;
                        }
                        break;
                    }
                    case 3: {
                        AntiAFK.mc.player.setSneaking(this.rs.nextBoolean());
                        break;
                    }
                    case 4: {
                        AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketTabComplete("/" + UUID.randomUUID().toString().replace('-', 'v'), AntiAFK.mc.player.getPosition(), false));
                        break;
                    }
                    case 5: {
                        AntiAFK.mc.player.jump();
                        break;
                    }
                    case 6: {
                        AntiAFK.mc.player.sendChatMessage("/" + UUID.randomUUID().toString().replace('-', 'v'));
                        break;
                    }
                    case 7: {
                        AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketClickWindow(1, 1, 1, ClickType.CLONE, new ItemStack(Blocks.BEDROCK), (short)1));
                        break;
                    }
                    case 8: {
                        AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, AntiAFK.mc.player.getPosition(), EnumFacing.DOWN));
                        break;
                    }
                    case 9: {
                        AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, AntiAFK.mc.player.getPosition(), EnumFacing.DOWN));
                        break;
                    }
                }
            }
            KeyBinding.setKeyBindState(AntiAFK.mc.gameSettings.keyBindLeft.getKeyCode(), this.rs.nextBoolean());
            KeyBinding.setKeyBindState(AntiAFK.mc.gameSettings.keyBindForward.getKeyCode(), this.rs.nextBoolean());
            KeyBinding.setKeyBindState(AntiAFK.mc.gameSettings.keyBindRight.getKeyCode(), this.rs.nextBoolean());
            KeyBinding.setKeyBindState(AntiAFK.mc.gameSettings.keyBindBack.getKeyCode(), this.rs.nextBoolean());
        }
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled() && this.autoReply.getValInt() && packetRecieved.packet instanceof SPacketChat && AntiAFK.rr.matcher(((SPacketChat)packetRecieved.packet).getChatComponent().getUnformattedText()).matches()) {
            AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/r I am currently afk but still online thanks to Backdoored\u2122 always online technology"));
        }
    }
    
    static {
        rr = Pattern.compile("/^([A-z_])+ whispers.*/gm");
    }
}
