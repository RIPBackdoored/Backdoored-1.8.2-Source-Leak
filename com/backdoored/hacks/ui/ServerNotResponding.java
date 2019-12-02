package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.time.*;
import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.k.*;
import java.text.*;
import net.minecraft.client.gui.*;
import java.awt.*;

@b.a(name = "Server Not Responding", description = "Get notified when the server isnt responding", category = CategoriesInit.UI)
public class ServerNotResponding extends BaseHack
{
    private Instant xm;
    
    public ServerNotResponding() {
        super();
        this.xm = Instant.EPOCH;
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        this.xm = Instant.now();
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            final long b = c.b(this.xm, Instant.now());
            if (b >= 1000L) {
                final String string = "§7Server has not responded for §r" + new DecimalFormat("0.0").format(b / 1000L) + "s";
                ServerNotResponding.mc.fontRenderer.drawString(string, new ScaledResolution(ServerNotResponding.mc).getScaledWidth() / 2 - ServerNotResponding.mc.fontRenderer.getStringWidth(string), 4, Color.WHITE.getRGB());
            }
        }
    }
}
