package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import a.a.d.c.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Instant Portal", description = "ez pz", category = CategoriesInit.PLAYER)
public class InstantPortal extends BaseHack
{
    private Setting<Integer> cooldown;
    private Setting<Integer> waitTime;
    
    public InstantPortal() {
        super();
        this.cooldown = new IntegerSetting("Cooldown", this, 2, 0, 10);
        this.waitTime = new IntegerSetting("Wait Time", this, 2, 0, 80);
    }
    
    @SubscribeEvent
    public void a(final e e) {
        if (this.getEnabled() && (e.entityPlayer == null || e.entityPlayer.getUniqueID().equals(InstantPortal.mc.player.getUniqueID()))) {
            e.dz = this.cooldown.getValInt();
        }
    }
    
    @SubscribeEvent
    public void a(final a.a.d.c.b b) {
        if (this.getEnabled() && (b.entity == null || b.entity.getUniqueID().equals(InstantPortal.mc.player.getUniqueID()))) {
            b.dv = this.waitTime.getValInt();
        }
    }
}
