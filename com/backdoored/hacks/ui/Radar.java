package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import a.a.e.*;
import net.minecraft.entity.player.*;
import java.awt.*;
import java.util.*;

@b.a(name = "Radar", description = "See nearby players", category = CategoriesInit.UI)
public class Radar extends BaseHack
{
    private Setting<Integer> x;
    private Setting<Integer> y;
    private Setting<Integer> textHeight;
    
    public Radar() {
        super();
        this.x = new IntegerSetting("x", this, 0, 0, Radar.mc.displayWidth + 50);
        this.y = new IntegerSetting("y", this, 0, 0, Radar.mc.displayWidth + 50);
        this.textHeight = new IntegerSetting("Text Height", this, 20, 1, 50);
    }
    
    public void onUpdate() {
        if (!this.getEnabled()) {
            return;
        }
        int n = 0;
        final int font_HEIGHT = c.fontRenderer.FONT_HEIGHT;
        c.fontRenderer.FONT_HEIGHT = this.textHeight.getValInt();
        for (final EntityPlayer entityPlayer : Radar.mc.world.playerEntities) {
            if (!entityPlayer.equals((Object)Radar.mc.player)) {
                c.fontRenderer.drawString(entityPlayer.getDisplayNameString(), (int)this.x.getValInt(), this.y.getValInt() + n, Color.WHITE.getRGB());
                n += c.fontRenderer.FONT_HEIGHT + 2;
            }
        }
        c.fontRenderer.FONT_HEIGHT = font_HEIGHT;
    }
}
