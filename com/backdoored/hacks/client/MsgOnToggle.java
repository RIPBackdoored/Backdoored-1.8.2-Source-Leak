package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.backdoored.event.*;

@b.a(name = "MsgOnToggle", description = "Sends message to chat on module toggle", category = CategoriesInit.CLIENT, defaultOn = true)
public class MsgOnToggle extends BaseHack
{
    public MsgOnToggle() {
        super();
    }
    
    @SubscribeEvent
    public void onHackEnabled(final HackEnabled v) {
        if (this.getEnabled() && !v.hack.name.equalsIgnoreCase("clickgui")) {
            Utils.printMessage(v.hack.name + " was enabled", "green");
        }
    }
    
    @SubscribeEvent
    public void onHackDisabled(final HackDisabled event) {
        if (this.getEnabled() && !event.hack.name.equalsIgnoreCase("clickgui")) {
            Utils.printMessage(event.hack.name + " was disabled", "red");
        }
    }
}
