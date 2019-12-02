package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.entity.player.*;
import a.a.g.b.e.*;
import com.backdoored.setting.*;
import com.backdoored.hacks.client.*;
import java.util.*;
import net.minecraft.util.math.*;
import a.a.k.*;
import com.backdoored.utils.*;

@b.a(name = "Visual Range", description = "Get notified when someone enters your render distance", category = CategoriesInit.MISC)
public class VisualRange extends BaseHack
{
    private List<EntityPlayer> qw;
    private Setting<a.b> color;
    private Setting<i.a> mode;
    
    public VisualRange() {
        super();
        this.qw = new ArrayList<EntityPlayer>();
        this.color = new EnumSetting<a.b>("Color", (BaseHack)this, (Enum)a.b.bbm);
        this.mode = new EnumSetting<i.a>("Mode", (BaseHack)this, (Enum)i.a.qu);
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        final ArrayList<EntityPlayer> list = (ArrayList<EntityPlayer>)new ArrayList<Object>(VisualRange.mc.world.playerEntities);
        list.removeAll(this.qw);
        for (final EntityPlayer player : list) {
            if (player == null) {
                continue;
            }
            if (VisualRange.mc.world.playerEntities.contains(player)) {
                this.a("PlayerPreviewElement '" + a(player) + "' entered your render distance at " + a(player.getPositionVector()), this.color.getValInt().toString());
                Notifications.INSTANCE.visualRangeTrigger(player);
            }
            else {
                if (!this.qw.contains(player)) {
                    continue;
                }
                this.a("PlayerPreviewElement '" + a(player) + "' left your render distance at " + a(player.getPositionVector()), this.color.getValInt().toString());
            }
        }
        this.qw = (List<EntityPlayer>)VisualRange.mc.world.playerEntities;
    }
    
    private static String a(final Vec3d vec3d) {
        if (vec3d != null) {
            return e.a(vec3d, new boolean[0]);
        }
        return "[null]";
    }
    
    private static String a(final EntityPlayer entityPlayer) {
        if (entityPlayer != null && entityPlayer.getDisplayNameString() != null) {
            return entityPlayer.getDisplayNameString();
        }
        return "[null]";
    }
    
    private void a(final String s, final String s2) {
        if (this.mode.getValInt() == i.a.qu) {
            Utils.printMessage(s, s2);
        }
        else {
            VisualRange.mc.player.sendChatMessage(s);
        }
    }
}
