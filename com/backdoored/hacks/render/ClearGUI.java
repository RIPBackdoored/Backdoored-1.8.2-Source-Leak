package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.d.b.d.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Clear GUI", description = "No Gui Background", category = CategoriesInit.RENDER)
public class ClearGUI extends BaseHack
{
    public ClearGUI() {
        super();
    }
    
    @SubscribeEvent
    public void a(final a a) {
        if (this.getEnabled()) {
            a.setResult(Event.Result.ALLOW);
        }
        else {
            a.setResult(Event.Result.DENY);
        }
    }
}
