package com.backdoored.hacks.chatbot;

import a.a.g.b.a.*;
import com.backdoored.hacks.chatbot.ChatBotScriptHandler.*;
import java.util.*;
import net.minecraft.client.gui.inventory.*;

public class AnnouncerScriptHandler extends d
{
    private static final String ih = "Backdoored/announcer.js";
    
    public AnnouncerScriptHandler() throws Exception {
        super();
        this.il = new ScriptHandler().a(a("Backdoored/announcer.js")).a(AnnouncerScriptHandler.im);
    }
    
    public String a(final String s) {
        try {
            return Objects.requireNonNull(this.il.a("onSendMessage", s));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return s;
        }
    }
    
    public String a(final int n) {
        return this.a("onMove", n);
    }
    
    public String b(final String s) {
        return this.a("onAttack", new Object[] { s });
    }
    
    public String a(final int n, final String s) {
        return this.a("onBlocksBreak", n, s);
    }
    
    public String b(final int n, final String s) {
        return this.a("onBlocksPlace", n, s);
    }
    
    public String a(final GuiInventory guiInventory) {
        return this.a("onOpenInventory", new Object[0]);
    }
    
    public String a() {
        return this.a("onScreenshot", new Object[0]);
    }
    
    public String b() {
        return this.a("onModuleEnabled", new Object[0]);
    }
    
    public String c() {
        return this.a("onModuleDisabled", new Object[0]);
    }
    
    public String d() {
        return this.a("onPlayerJoin", new Object[0]);
    }
    
    public String e() {
        return this.a("onPlayerLeave", new Object[0]);
    }
    
    private String a(final String s, final Object... array) {
        try {
            return (String)this.il.a(s, array);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
