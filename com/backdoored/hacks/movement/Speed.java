package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import a.a.d.e.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.k.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;
import com.backdoored.*;
import net.minecraft.util.*;

@b.a(name = "Speed", description = "Speeeeeeeeeeeeeeed", category = CategoriesInit.MOVEMENT)
public class Speed extends BaseHack
{
    private Setting<Boolean> onlyForward;
    
    public Speed() {
        super();
        this.onlyForward = new BooleanSetting("Only Forward", this, false);
    }
    
    @SubscribeEvent
    public void a(final k.a a) {
        if (!this.getEnabled()) {
            return;
        }
        this.a(0.405, 0.2f, 1.0064);
    }
    
    private void a(final double motionY, final float n, final double n2) {
        if ((!this.onlyForward.getValInt() && Speed.mc.player.moveForward != 0.0f) || Speed.mc.player.moveForward > 0.0f || Speed.mc.player.moveStrafing != 0.0f) {
            Speed.mc.player.setSprinting(true);
            if (Speed.mc.player.onGround) {
                Speed.mc.player.motionY = motionY;
                final float a = e.a();
                final EntityPlayerSP player = Speed.mc.player;
                player.motionX -= MathHelper.sin(a) * n;
                final EntityPlayerSP player2 = Speed.mc.player;
                player2.motionZ += MathHelper.cos(a) * n;
            }
            else {
                final double sqrt = Math.sqrt(Speed.mc.player.motionX * Speed.mc.player.motionX + Speed.mc.player.motionZ * Speed.mc.player.motionZ);
                final double n3 = e.a();
                Speed.mc.player.motionX = -Math.sin(n3) * n2 * sqrt;
                Speed.mc.player.motionZ = Math.cos(n3) * n2 * sqrt;
            }
        }
    }
    
    public void a(final double n, final double n2, final EntityPlayerSP entityPlayerSP) {
        final MovementInput movementInput = Globals.mc.player.movementInput;
        float moveForward = movementInput.moveForward;
        float moveStrafe = movementInput.moveStrafe;
        float rotationYaw = Globals.mc.player.rotationYaw;
        if (moveForward != 0.0) {
            if (moveStrafe > 0.0) {
                rotationYaw += ((moveForward > 0.0) ? -45 : 45);
            }
            else if (moveStrafe < 0.0) {
                rotationYaw += ((moveForward > 0.0) ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0) {
                moveForward = 1.0f;
            }
            else if (moveForward < 0.0) {
                moveForward = -1.0f;
            }
        }
        if (moveStrafe > 0.0) {
            moveStrafe = 1.0f;
        }
        else if (moveStrafe < 0.0) {
            moveStrafe = -1.0f;
        }
        entityPlayerSP.motionX = n + (moveForward * 0.2 * Math.cos(Math.toRadians(rotationYaw + 90.0f)) + moveStrafe * 0.2 * Math.sin(Math.toRadians(rotationYaw + 90.0f)));
        entityPlayerSP.motionZ = n2 + (moveForward * 0.2 * Math.sin(Math.toRadians(rotationYaw + 90.0f)) - moveStrafe * 0.2 * Math.cos(Math.toRadians(rotationYaw + 90.0f)));
    }
}
