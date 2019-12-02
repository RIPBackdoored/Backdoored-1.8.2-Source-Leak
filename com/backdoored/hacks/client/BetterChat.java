package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.k.*;
import com.backdoored.setting.*;
import net.minecraftforge.client.event.*;
import java.time.format.*;
import java.time.*;
import java.time.temporal.*;
import net.minecraft.util.text.*;
import java.awt.*;
import a.a.d.b.d.a.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Better Chat", description = "Client side chat tweaks", category = CategoriesInit.CLIENT)
public class BetterChat extends BaseHack
{
    private final Setting<Boolean> timestamps;
    private final Setting<Boolean> timestampSecs;
    private final Setting<a.b> timestampColour;
    private final Setting<Boolean> persistance;
    private final Setting<Double> transparency;
    private final Setting<Boolean> infiniteLength;
    
    public BetterChat() {
        super();
        this.timestamps = new BooleanSetting("Timestamps", this, true);
        this.timestampSecs = new BooleanSetting("Timestamp Secs", this, true);
        this.timestampColour = new EnumSetting<a.b>("Timestamp Colour", (BaseHack)this, (Enum)a.b.bbm);
        this.persistance = new BooleanSetting("Persistance", this, true);
        this.transparency = new DoubleSetting("Transparency", this, 1.0, 0.0, 0.5);
        this.infiniteLength = new BooleanSetting("Infinite Length", this, false);
    }
    
    @SubscribeEvent
    public void a(final ClientChatReceivedEvent clientChatReceivedEvent) {
        if (this.getEnabled() && this.timestamps.getValInt()) {
            clientChatReceivedEvent.setMessage(new TextComponentString(a.a(this.timestampColour.getValInt().toString()) + "<" + DateTimeFormatter.ofPattern(this.timestampSecs.getValInt() ? "hh:mm:ss" : "hh:mm").format(LocalDateTime.now()) + ">§r ").appendSibling(clientChatReceivedEvent.getMessage()));
        }
    }
    
    @SubscribeEvent
    public void a(final a.a.d.b.d.a.b b) {
        if (this.getEnabled()) {
            b.cp = new Color(b.cp.getRed(), b.cp.getBlue(), b.cp.getRed(), (int)Math.round(this.transparency.getValInt() * 255.0));
        }
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (this.getEnabled() && this.persistance.getValInt()) {
            System.out.println("Made last: " + c.cr);
            c.cq = c.cr;
        }
    }
    
    @SubscribeEvent
    public void a(final d d) {
        if (this.getEnabled() && this.infiniteLength.getValInt()) {
            d.setResult(Event.Result.ALLOW);
        }
        else {
            d.setResult(Event.Result.DENY);
        }
    }
}
