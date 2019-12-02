package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.e.*;

@b.a(name = "Anti Chunk Ban", description = "Prevent being chunk banned", category = CategoriesInit.MISC)
public class AntiChunkBan extends BaseHack
{
    private Setting<Boolean> noChunkPacket;
    private Setting<Boolean> noWorldRender;
    private Setting<Boolean> spamKill;
    private Setting<Integer> killDelay;
    private int qk;
    
    public AntiChunkBan() {
        super();
        this.noChunkPacket = new BooleanSetting("No Chunk Packet", this, true);
        this.noWorldRender = new BooleanSetting("No World Render", this, true);
        this.spamKill = new BooleanSetting("Spam Kill", this, false);
        this.killDelay = new IntegerSetting("Kill Delay", this, 1, 0, 20);
        this.qk = 0;
    }
    
    public void onTick() {
        if (this.getEnabled() && this.spamKill.getValInt()) {
            --this.qk;
            if (this.qk <= 0) {
                this.qk = this.killDelay.getValInt();
                AntiChunkBan.mc.player.sendChatMessage("/kill");
            }
        }
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled() && this.noChunkPacket.getValInt() && packetRecieved.packet instanceof SPacketChunkData) {
            packetRecieved.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void a(final m m) {
        if (this.getEnabled() && this.noWorldRender.getValInt()) {
            m.setCanceled(true);
        }
    }
}
