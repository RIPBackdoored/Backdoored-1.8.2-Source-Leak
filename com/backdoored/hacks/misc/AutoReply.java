package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Auto Reply", description = "Tell those scrubs whos boss", category = CategoriesInit.MISC)
public class AutoReply extends BaseHack
{
    private final String qn = "Ebic/autoreplies.txt";
    private String[] qo;
    
    public AutoReply() {
        super();
        this.qo = new String[0];
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled() && packetRecieved.packet instanceof SPacketChat && ((SPacketChat)packetRecieved.packet).getChatComponent().getUnformattedText().contains(" whispers: ")) {
            AutoReply.mc.player.sendChatMessage("/r Shut up Scrub");
        }
    }
}
