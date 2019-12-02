package com.backdoored.hacks.ui;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import java.awt.*;
import java.util.*;

@b.a(name = "Players Potions", description = "Show players potions", category = CategoriesInit.UI)
public class PlayersPotions extends BaseHack
{
    private final Setting<Integer> xh;
    private final Setting<Integer> xi;
    
    public PlayersPotions() {
        super();
        this.xh = new IntegerSetting("x", this, 0, 0, (int)Math.round(PlayersPotions.mc.displayWidth * 1.3));
        this.xi = new IntegerSetting("y", this, 0, 0, (int)Math.round(PlayersPotions.mc.displayWidth * 1.3));
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            int n = 0;
            for (final EntityPlayer entityPlayer : PlayersPotions.mc.world.playerEntities) {
                if (entityPlayer.getUniqueID().equals(PlayersPotions.mc.player.getUniqueID())) {
                    continue;
                }
                final String displayNameString = entityPlayer.getDisplayNameString();
                for (final Map.Entry<Potion, V> entry : entityPlayer.getActivePotionMap().entrySet()) {
                    PlayersPotions.mc.fontRenderer.drawString(displayNameString + " : " + entry.getKey().getName() + " : " + Potion.getPotionDurationString((PotionEffect)entry.getValue(), 1.0f), (int)this.xh.getValInt(), this.xi.getValInt() + n, Color.WHITE.getRGB());
                    n += PlayersPotions.mc.fontRenderer.FONT_HEIGHT + 2;
                }
            }
        }
    }
}
