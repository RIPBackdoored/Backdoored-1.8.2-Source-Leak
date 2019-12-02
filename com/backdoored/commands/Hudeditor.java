package com.backdoored.commands;

import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.h.b.*;
import net.minecraft.client.gui.*;
import com.backdoored.utils.*;

public class Hudeditor extends CommandBase
{
    private boolean bd;
    
    public Hudeditor() {
        super(new String[] { "hudeditor", "HudEditor", "EditHud" });
        this.bd = false;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public boolean a(final String[] array) {
        return this.bd = true;
    }
    
    @SubscribeEvent
    public void a(final TickEvent.ClientTickEvent clientTickEvent) {
        if (clientTickEvent.phase == TickEvent.Phase.END && this.bd) {
            this.mc.addScheduledTask(this::b);
            this.bd = false;
        }
    }
    
    @Override
    public String a() {
        return "-hudeditor";
    }
    
    private /* synthetic */ void b() {
        this.mc.displayGuiScreen((GuiScreen)new b());
        Utils.printMessage("Opened Hud Editor");
    }
}
