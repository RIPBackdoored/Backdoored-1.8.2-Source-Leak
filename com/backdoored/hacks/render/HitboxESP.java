package com.backdoored.hacks.render;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import org.lwjgl.opengl.*;
import net.minecraft.entity.player.*;
import com.backdoored.utils.*;
import java.util.*;

@b.a(name = "Hitbox ESP", description = "See outlines of players through walls", category = CategoriesInit.RENDER, defaultOn = true, defaultIsVisible = false)
public class HitboxESP extends BaseHack
{
    private Setting<Boolean> showfriendshitbox;
    private Setting<Boolean> showothershitbox;
    
    public HitboxESP() {
        super();
        this.showfriendshitbox = new BooleanSetting("Show friends hitbox", this, true);
        this.showothershitbox = new BooleanSetting("Show others hitbox", this, false);
    }
    
    public void onRender() {
        if (!this.getEnabled() || HitboxESP.mc.world.playerEntities.size() <= 0) {
            return;
        }
        RenderUtils.glStart(0.0f, 0.0f, 0.0f, 1.0f);
        this.e();
        GL11.glColor4f(0.0f, 255.0f, 0.0f, 1.0f);
        this.c();
        RenderUtils.glEnd();
    }
    
    private void c() {
        if (this.showfriendshitbox.getValInt()) {
            for (final EntityPlayer entityPlayer : HitboxESP.mc.world.playerEntities) {
                if (FriendUtils.a(entityPlayer) && !entityPlayer.getUniqueID().equals(HitboxESP.mc.player.getUniqueID())) {
                    RenderUtils.drawOutlinedBox(entityPlayer.getEntityBoundingBox());
                }
            }
        }
    }
    
    private void e() {
        if (this.showothershitbox.getValInt()) {
            for (final EntityPlayer entityPlayer : HitboxESP.mc.world.playerEntities) {
                if (!FriendUtils.a(entityPlayer) && !entityPlayer.getUniqueID().equals(HitboxESP.mc.player.getUniqueID())) {
                    RenderUtils.drawOutlinedBox(entityPlayer.getEntityBoundingBox());
                }
            }
        }
    }
}
