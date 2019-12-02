package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import a.a.d.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Jesus", description = "Walk on water", category = CategoriesInit.MOVEMENT)
public class Jesus extends BaseHack
{
    public Jesus() {
        super();
    }
    
    @SubscribeEvent
    public void a(final f f) {
        if (this.getEnabled()) {
            f.eb = 0.4;
        }
    }
}
