package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.e.*;

@b.a(name = "Full Bright", description = "Big Brightness bois", category = CategoriesInit.RENDER)
public class FullBright extends BaseHack
{
    public FullBright() {
        super();
    }
    
    @SubscribeEvent
    public void a(final a.a.d.e.b b) {
        if (this.getEnabled()) {
            b.ej = 1.0f;
        }
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (this.getEnabled()) {
            c.ek = 1.0f;
        }
    }
    
    @SubscribeEvent
    public void a(final d d) {
        if (this.getEnabled()) {
            d.el = 1000;
        }
    }
    
    @SubscribeEvent
    public void a(final a.a.d.a.b b) {
        if (this.getEnabled()) {
            b.by = false;
        }
    }
}
