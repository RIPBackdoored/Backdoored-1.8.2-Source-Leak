package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.k.a.a.*;
import com.backdoored.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Chat Modifier", description = "Modify your chat messages", category = CategoriesInit.PLAYER)
public class ChatModifier extends BaseHack
{
    private a[] se;
    private Setting<Boolean> emphasize;
    private Setting<Boolean> reverse;
    private Setting<Boolean> chav;
    private Setting<Boolean> justLearntEngrish;
    private Setting<Boolean> l33t;
    private Setting<Boolean> disabled;
    private Setting<Boolean> fancy;
    private Setting<Boolean> soviet;
    
    public ChatModifier() {
        super();
        this.se = new a[] { new d(), new h(), new a.a.k.a.a.b(), new f(), new g(), new c(), new e(), new i() };
        this.emphasize = new BooleanSetting("Emphasize", this, false);
        this.reverse = new BooleanSetting("Reverse", this, false);
        this.chav = new BooleanSetting("Chav", this, false);
        this.justLearntEngrish = new BooleanSetting("JustLearntEngrish", this, false);
        this.l33t = new BooleanSetting("L33t", this, false);
        this.disabled = new BooleanSetting("Disabled", this, false);
        this.fancy = new BooleanSetting("Fancy", this, false);
        this.soviet = new BooleanSetting("Soviet", this, false);
    }
    
    @SubscribeEvent
    public void a(final a.a.d.d.c c) {
        if (c.packet instanceof CPacketChatMessage) {
            System.out.println("Was packet");
            if (this.getEnabled()) {
                final CPacketChatMessage cPacketChatMessage = (CPacketChatMessage)c.packet;
                String s = cPacketChatMessage.getMessage();
                if (!s.startsWith("/") && !s.startsWith("!")) {
                    for (final a a : this.se) {
                        try {
                            boolean b = false;
                            final String b2 = a.b();
                            switch (b2) {
                                case "Emphasize": {
                                    b = this.emphasize.getValInt();
                                    break;
                                }
                                case "Reverse": {
                                    b = this.reverse.getValInt();
                                    break;
                                }
                                case "Chav": {
                                    b = this.chav.getValInt();
                                    break;
                                }
                                case "JustLearntEngrish": {
                                    b = this.justLearntEngrish.getValInt();
                                    break;
                                }
                                case "L33t": {
                                    b = this.l33t.getValInt();
                                    break;
                                }
                                case "Disabled": {
                                    b = this.disabled.getValInt();
                                    break;
                                }
                                case "Fancy": {
                                    b = this.fancy.getValInt();
                                    break;
                                }
                                case "Soviet": {
                                    b = this.soviet.getValInt();
                                    break;
                                }
                            }
                            if (b) {
                                s = a.b(s);
                            }
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                try {
                    ObfuscationReflectionHelper.setPrivateValue((Class)CPacketChatMessage.class, (Object)cPacketChatMessage, (Object)s, new String[] { "message", "field_149440_a" });
                }
                catch (Exception ex2) {
                    Utils.printMessage("Disabled chat modifier due to error: " + ex2.getMessage());
                    this.setEnabled(false);
                    ex2.printStackTrace();
                }
            }
        }
    }
}
