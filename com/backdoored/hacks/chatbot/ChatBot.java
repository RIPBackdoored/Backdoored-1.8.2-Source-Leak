package com.backdoored.hacks.chatbot;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.g.b.a.*;
import com.backdoored.utils.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Chat Bot", description = "Scriptable chat bot", category = CategoriesInit.CHATBOT)
public class ChatBot extends BaseHack
{
    private d ii;
    private long ij;
    
    public ChatBot() {
        super();
        this.ij = 0L;
    }
    
    public void onEnabled() {
        try {
            this.ii = new d();
        }
        catch (Exception ex) {
            this.setEnabled(false);
            Utils.printMessage("Failed to initialise chatbot script: " + ex.getMessage(), "red");
            ex.printStackTrace();
        }
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled() && packetRecieved.packet instanceof SPacketChat && System.currentTimeMillis() - this.ij > 5000L) {
            final SPacketChat sPacketChat = (SPacketChat)packetRecieved.packet;
            this.a(sPacketChat.getChatComponent().getUnformattedText(), sPacketChat.getType().name());
            this.ij = System.currentTimeMillis();
        }
    }
    
    private void a(final String s, final String s2) {
        if (ChatBot.mc.player == null || s.startsWith("<" + ChatBot.mc.player.getName()) || s.startsWith("<" + ChatBot.mc.player.getDisplayNameString())) {
            return;
        }
        try {
            if (this.ii == null) {
                this.ii = new d();
            }
            final String a = this.ii.a(s, s2);
            if (a != null) {
                ChatBot.mc.player.sendChatMessage(a);
            }
        }
        catch (Exception ex) {
            this.setEnabled(false);
            Utils.printMessage("Failure while invoking chatbot script: " + ex.getMessage(), "red");
            ex.printStackTrace();
        }
    }
}
