package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.d.b.f.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Safe Walk", description = "Prevent you from walking off edges", category = CategoriesInit.MOVEMENT)
public class SafeWalk extends BaseHack
{
    public SafeWalk() {
        super();
    }
    
    @SubscribeEvent
    public void a(final d d) {
        if (this.getEnabled()) {
            d.cz = true;
        }
    }
}
