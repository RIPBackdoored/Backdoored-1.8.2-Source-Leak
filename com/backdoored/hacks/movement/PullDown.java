package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Pull Down", description = "Fast fall", category = CategoriesInit.MOVEMENT)
public class PullDown extends BaseHack
{
    private boolean rg;
    private Setting<Float> rh;
    
    public PullDown() {
        super();
        this.rg = false;
        this.rh = new FloatSetting("Speed", this, 10.0f, 0.0f, 20.0f);
    }
    
    public void onTick() {
        if (this.rg && PullDown.mc.player.onGround) {
            this.rg = false;
        }
        if (!this.getEnabled() || PullDown.mc.player.isElytraFlying() || PullDown.mc.player.capabilities.isFlying) {
            return;
        }
        final boolean b = !PullDown.mc.world.isAirBlock(PullDown.mc.player.getPosition().add(0, -1, 0)) || !PullDown.mc.world.isAirBlock(PullDown.mc.player.getPosition().add(0, -2, 0));
        if (!PullDown.mc.player.onGround && !b) {
            PullDown.mc.player.motionY = -this.rh.getValInt();
        }
    }
    
    @SubscribeEvent
    public void a(final LivingEvent.LivingJumpEvent livingJumpEvent) {
        if (livingJumpEvent.getEntityLiving().equals((Object)PullDown.mc.player)) {
            this.rg = true;
        }
    }
}
