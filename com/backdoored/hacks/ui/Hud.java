package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import org.lwjgl.input.*;
import a.a.h.b.*;

@b.a(name = "Hud", description = "Hud Overlay", category = CategoriesInit.UI, defaultOn = true, defaultIsVisible = false)
public class Hud extends BaseHack
{
    public static Hud wy;
    
    public Hud() {
        super();
        Hud.wy = this;
    }
    
    public void onUpdate() {
        if (this.getEnabled() && !Hud.mc.gameSettings.showDebugInfo && !(Hud.mc.currentScreen instanceof a.a.h.b.b)) {
            c.a(Mouse.getX(), Mouse.getY(), Hud.mc.getRenderPartialTicks());
        }
    }
}
