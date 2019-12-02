package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import a.a.g.b.h.*;

@b.a(name = "No Render", description = "Dont render things", category = CategoriesInit.RENDER)
public class NoRender extends BaseHack
{
    private Setting<Boolean> stopExplosions;
    private Setting<Boolean> stopParticles;
    private Setting<Boolean> helmet;
    private Setting<Boolean> portal;
    private Setting<Boolean> crosshair;
    private Setting<Boolean> bosshealth;
    private Setting<Boolean> bossinfo;
    private Setting<Boolean> armor;
    private Setting<Boolean> health;
    private Setting<Boolean> food;
    private Setting<Boolean> air;
    private Setting<Boolean> hotbar;
    private Setting<Boolean> experience;
    private Setting<Boolean> text;
    private Setting<Boolean> horsehealth;
    private Setting<Boolean> horsejump;
    private Setting<Boolean> chat;
    private Setting<Boolean> playerlist;
    private Setting<Boolean> potionicon;
    private Setting<Boolean> subtitles;
    private Setting<Boolean> fpsgraph;
    private Setting<Boolean> vignette;
    
    public NoRender() {
        super();
        this.stopExplosions = new BooleanSetting("Stop Explosions", this, true);
        this.stopParticles = new BooleanSetting("Stop Particles", this, true);
        this.helmet = new BooleanSetting("helmet", this, false);
        this.portal = new BooleanSetting("portal", this, false);
        this.crosshair = new BooleanSetting("crosshair", this, false);
        this.bosshealth = new BooleanSetting("bosshealth", this, false);
        this.bossinfo = new BooleanSetting("bossinfo", this, false);
        this.armor = new BooleanSetting("armor", this, false);
        this.health = new BooleanSetting("health", this, false);
        this.food = new BooleanSetting("food", this, false);
        this.air = new BooleanSetting("air", this, false);
        this.hotbar = new BooleanSetting("hotbar", this, false);
        this.experience = new BooleanSetting("experience", this, false);
        this.text = new BooleanSetting("text", this, false);
        this.horsehealth = new BooleanSetting("horse health", this, false);
        this.horsejump = new BooleanSetting("horse jump", this, false);
        this.chat = new BooleanSetting("chat", this, false);
        this.playerlist = new BooleanSetting("playerlist", this, false);
        this.potionicon = new BooleanSetting("potion icon", this, false);
        this.subtitles = new BooleanSetting("subtitles", this, false);
        this.fpsgraph = new BooleanSetting("fps graph", this, false);
        this.vignette = new BooleanSetting("vignette", this, false);
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled() && this.stopExplosions.getValInt() && packetRecieved.packet instanceof SPacketExplosion) {
            packetRecieved.setCanceled(true);
        }
        if (this.getEnabled() && this.stopParticles.getValInt() && packetRecieved.packet instanceof SPacketParticles) {
            packetRecieved.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void a(final RenderGameOverlayEvent renderGameOverlayEvent) {
        switch (j.j$a.vy[renderGameOverlayEvent.getType().ordinal()]) {
            case 1: {
                if (this.helmet.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 2: {
                if (this.portal.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 3: {
                if (this.crosshair.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 4: {
                if (this.bosshealth.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 5: {
                if (this.bossinfo.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 6: {
                if (this.armor.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 7: {
                if (this.health.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 8: {
                if (this.food.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 9: {
                if (this.air.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 10: {
                if (this.hotbar.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 11: {
                if (this.experience.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 12: {
                if (this.text.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 13: {
                if (this.horsehealth.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 14: {
                if (this.horsejump.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 15: {
                if (this.chat.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 16: {
                if (this.playerlist.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 17: {
                if (this.potionicon.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 18: {
                if (this.subtitles.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 19: {
                if (this.fpsgraph.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
            case 20: {
                if (this.vignette.getValInt()) {
                    renderGameOverlayEvent.setCanceled(true);
                    break;
                }
                break;
            }
        }
    }
}
