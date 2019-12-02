package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import java.awt.*;
import net.minecraft.util.math.*;

@b.a(name = "Coordinates", description = "Show your coordinates", category = CategoriesInit.UI)
public class Coordinates extends BaseHack
{
    private Setting<Integer> ww;
    private Setting<Integer> wx;
    
    public Coordinates() {
        super();
        this.ww = new IntegerSetting("x", this, 50, 0, (int)Math.round(Coordinates.mc.displayWidth * 1.2));
        this.wx = new IntegerSetting("y", this, 50, 0, (int)Math.round(Coordinates.mc.displayHeight * 1.2));
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            Coordinates.mc.fontRenderer.drawString(this.a(Coordinates.mc.player.getPositionVector()), (int)this.ww.getValInt(), (int)this.wx.getValInt(), Color.WHITE.getRGB());
        }
    }
    
    private String a(final Vec3d vec3d) {
        return (int)Math.floor(vec3d.x) + ", " + (int)Math.floor(vec3d.y) + ", " + (int)Math.floor(vec3d.z) + " (" + (int)Math.floor(vec3d.x) / 8 + ", " + (int)Math.floor(vec3d.z) / 8 + ")";
    }
}
