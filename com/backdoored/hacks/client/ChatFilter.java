package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.hacks.chatbot.ChatBotScriptHandler.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import java.util.regex.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.g.b.a.*;
import org.apache.logging.log4j.*;
import com.backdoored.utils.*;
import java.util.*;

@b.a(name = "Chat Filter", description = "Filter your chat", category = CategoriesInit.CLIENT)
public class ChatFilter extends BaseHack
{
    private Setting<Boolean> whispers;
    private Setting<Boolean> mentions;
    private Setting<Boolean> gameInfo;
    private ScriptHandler scriptHandler;
    
    public ChatFilter() {
        super();
        this.whispers = new BooleanSetting("Allow Whispers", this, true);
        this.mentions = new BooleanSetting("Allow Mentions", this, true);
        this.gameInfo = new BooleanSetting("Allow Game Info", this, true);
    }
    
    @SubscribeEvent
    public void onClientChatRecieved(final ClientChatReceivedEvent event) {
        if (this.getEnabled()) {
            event.setCanceled(true);
            final String lowerCase = event.getMessage().getUnformattedText().toLowerCase();
            if (this.whispers.getValInt()) {
                final String[] split = lowerCase.split(Pattern.quote(" "));
                if (split.length >= 3 && split[1].equals("whispers:")) {
                    event.setCanceled(false);
                }
            }
            if (this.mentions.getValInt() && lowerCase.contains(ChatFilter.mc.player.getName().toLowerCase())) {
                event.setCanceled(false);
            }
            if (this.gameInfo.getValInt() && event.getType() == ChatType.GAME_INFO) {
                event.setCanceled(false);
            }
            if (!event.isCanceled()) {
                event.setCanceled(this.c(event.getMessage().getUnformattedText()));
            }
        }
    }
    
    public boolean c(final String s) {
        if (this.scriptHandler == null) {
            try {
                this.scriptHandler = new ScriptHandler().a(d.a("Backdoored/chatfilter.js")).a(LogManager.getLogger("BackdooredChatFilter"));
            }
            catch (Exception ex) {
                this.setEnabled(false);
                Utils.printMessage("Failed to initialise Chat Filter script: " + ex.getMessage(), "red");
                ex.printStackTrace();
                return false;
            }
        }
        try {
            return Objects.requireNonNull(this.scriptHandler.a("isExcluded", s));
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
            return false;
        }
    }
}
