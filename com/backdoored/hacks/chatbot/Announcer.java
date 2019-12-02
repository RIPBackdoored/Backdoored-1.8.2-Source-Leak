package com.backdoored.hacks.chatbot;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.time.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import com.backdoored.setting.*;
import com.backdoored.utils.*;
import a.a.k.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import net.minecraft.item.*;
import net.minecraftforge.event.world.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraftforge.client.event.*;
import a.a.d.e.*;
import com.backdoored.event.*;

@b.a(name = "Announcer", description = "Don't use this unless your a penis", category = CategoriesInit.CHATBOT)
public class Announcer extends BaseHack
{
    private Setting<Boolean> movement;
    private Instant hs;
    private Vec3d vec3d;
    private Setting<Boolean> blockPlace;
    private Instant hv;
    private Block block;
    private int hx;
    private Setting<Boolean> blockBreak;
    private Instant hz;
    private Block block;
    private int ib;
    private Setting<Boolean> attackEntities;
    private Instant id;
    private Setting<Boolean> gui;
    private Setting<Boolean> screenshot;
    private AnnouncerScriptHandler ig;
    
    public Announcer() {
        super();
        this.movement = new BooleanSetting("Movement", this, true);
        this.hs = Instant.now();
        this.vec3d = null;
        this.blockPlace = new BooleanSetting("Block Place", this, true);
        this.hv = Instant.now();
        this.block = null;
        this.hx = 0;
        this.blockBreak = new BooleanSetting("Block Break", this, true);
        this.hz = Instant.now();
        this.block = null;
        this.ib = 0;
        this.attackEntities = new BooleanSetting("Attack Entities", this, true);
        this.id = Instant.now();
        this.gui = new BooleanSetting("Gui", this, true);
        this.screenshot = new BooleanSetting("Screenshot", this, true);
    }
    
    public void onEnabled() {
        try {
            this.ig = new AnnouncerScriptHandler();
        }
        catch (Exception ex) {
            this.setEnabled(false);
            Utils.printMessage("Failed to initialise Announcer script: " + ex.getMessage(), "red");
            ex.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void a(final k.b b) {
        if (this.getEnabled() && c.a(this.hs, Instant.now(), 60L) && this.movement.getValInt()) {
            if (this.vec3d == null) {
                this.vec3d = Announcer.mc.player.getPositionVector();
                return;
            }
            final int n = (int)Math.round(this.vec3d.distanceTo(Announcer.mc.player.getPositionVector()));
            if (n > 0) {
                this.send(this.ig.a(n));
                this.hs = Instant.now();
            }
        }
    }
    
    @SubscribeEvent
    public void a(final a.a.d.d.c c) {
        if (this.getEnabled() && c.packet instanceof CPacketPlayerTryUseItemOnBlock && this.blockPlace.getValInt()) {
            final ItemStack heldItem = Announcer.mc.player.getHeldItem(((CPacketPlayerTryUseItemOnBlock)c.packet).getHand());
            if (heldItem.getItem() instanceof ItemBlock) {
                final Block block = ((ItemBlock)heldItem.getItem()).getBlock();
                if (this.block == null) {
                    this.block = block;
                }
                if (this.block.equals(block)) {
                    ++this.hx;
                }
            }
            if (c.a(this.hv, Instant.now(), 60L) && this.hx > 0) {
                this.send(this.ig.b(this.hx, heldItem.getDisplayName()));
                this.hv = Instant.now();
                this.block = null;
            }
        }
    }
    
    @SubscribeEvent
    public void a(final BlockEvent.BreakEvent breakEvent) {
        if (this.getEnabled() && breakEvent.getPlayer().equals((Object)Announcer.mc.player)) {
            final Block block = breakEvent.getState().getBlock();
            if (this.block == null) {
                this.block = block;
            }
            if (this.block.equals(block)) {
                ++this.ib;
            }
            if (c.a(this.hz, Instant.now(), 60L) && this.ib > 0) {
                this.send(this.ig.a(this.ib, block.getLocalizedName()));
                this.hz = Instant.now();
                this.block = null;
            }
        }
    }
    
    @SubscribeEvent
    public void a(final AttackEntityEvent attackEntityEvent) {
        if (this.getEnabled() && attackEntityEvent.getTarget() instanceof EntityLivingBase && c.a(this.id, Instant.now(), 60L)) {
            this.send(this.ig.b(attackEntityEvent.getTarget().getDisplayName().getUnformattedText()));
            this.id = Instant.now();
        }
    }
    
    @SubscribeEvent
    public void a(final GuiOpenEvent guiOpenEvent) {
        if (this.getEnabled() && this.gui.getValInt() && guiOpenEvent.getGui() != null && guiOpenEvent.getGui() instanceof GuiInventory) {
            this.send(this.ig.a((GuiInventory)guiOpenEvent.getGui()));
        }
    }
    
    @SubscribeEvent
    public void a(final ScreenshotEvent screenshotEvent) {
        if (this.getEnabled() && this.screenshot.getValInt()) {
            this.send(this.ig.a());
        }
    }
    
    @SubscribeEvent
    public void a(final HackEnabled hackEnabled) {
        if (this.getEnabled()) {
            this.send(this.ig.b());
        }
    }
    
    @SubscribeEvent
    public void a(final HackDisabled hackDisabled) {
        if (this.getEnabled()) {
            this.send(this.ig.c());
        }
    }
    
    @SubscribeEvent
    public void a(final i i) {
        if (this.getEnabled()) {
            this.send(this.ig.d());
        }
    }
    
    @SubscribeEvent
    public void a(final PlayerLeave playerLeave) {
        if (this.getEnabled()) {
            this.send(this.ig.e());
        }
    }
    
    private void send(String a) {
        if (a == null) {
            return;
        }
        a = this.ig.a(a);
        if (a == null) {
            return;
        }
        if (this.getEnabled()) {
            Announcer.mc.player.sendChatMessage(a + " thanks to Backdoored Client");
        }
    }
}
