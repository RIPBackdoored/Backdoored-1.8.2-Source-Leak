package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;

@b.a(name = "BoatFly", description = "Experimental boatfly", category = CategoriesInit.MOVEMENT)
public class BoatFly extends BaseHack
{
    public BoatFly() {
        super();
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        if (BoatFly.mc.player.isRiding()) {
            final boolean keyDown = BoatFly.mc.gameSettings.keyBindForward.isKeyDown();
            final boolean keyDown2 = BoatFly.mc.gameSettings.keyBindLeft.isKeyDown();
            final boolean keyDown3 = BoatFly.mc.gameSettings.keyBindRight.isKeyDown();
            final boolean keyDown4 = BoatFly.mc.gameSettings.keyBindBack.isKeyDown();
            int n;
            if (keyDown2 && keyDown3) {
                n = (keyDown ? 0 : (keyDown4 ? 180 : -1));
            }
            else if (keyDown && keyDown4) {
                n = (keyDown2 ? -90 : (keyDown3 ? 90 : -1));
            }
            else {
                n = (keyDown2 ? -90 : (keyDown3 ? 90 : 0));
                if (keyDown) {
                    n /= 2;
                }
                else if (keyDown4) {
                    n = 180 - n / 2;
                }
            }
            if (n != -1 && (keyDown || keyDown2 || keyDown3 || keyDown4)) {
                final float n2 = BoatFly.mc.player.rotationYaw + n;
                BoatFly.mc.player.getRidingEntity().motionX = this.getRelativeX(n2) * 0.20000000298023224;
                BoatFly.mc.player.getRidingEntity().motionZ = this.getRelativeZ(n2) * 0.20000000298023224;
            }
            BoatFly.mc.player.motionY = 0.0;
            BoatFly.mc.player.getRidingEntity().motionY = 0.0;
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.player.getRidingEntity().posX + BoatFly.mc.player.getRidingEntity().motionX, BoatFly.mc.player.getRidingEntity().posY, BoatFly.mc.player.getRidingEntity().posZ + BoatFly.mc.player.getRidingEntity().motionZ, BoatFly.mc.player.rotationYaw, BoatFly.mc.player.rotationPitch, false));
            BoatFly.mc.player.getRidingEntity().motionY = 0.0;
            if (BoatFly.mc.gameSettings.keyBindJump.isKeyDown()) {
                BoatFly.mc.player.getRidingEntity().motionY = 0.3;
            }
            if (BoatFly.mc.gameSettings.keyBindSprint.isKeyDown()) {
                BoatFly.mc.player.getRidingEntity().motionY = -0.3;
            }
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove());
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(BoatFly.mc.player.getRidingEntity().posX + BoatFly.mc.player.getRidingEntity().motionX, BoatFly.mc.player.getRidingEntity().posY - 42069.0, BoatFly.mc.player.getRidingEntity().posZ + BoatFly.mc.player.getRidingEntity().motionZ, BoatFly.mc.player.rotationYaw, BoatFly.mc.player.rotationPitch, true));
            BoatFly.mc.player.getRidingEntity().posY -= 42069.0;
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketVehicleMove());
            BoatFly.mc.player.connection.sendPacket((Packet)new CPacketSteerBoat(true, true));
        }
    }
    
    private double[] moveLooking(final int n) {
        return new double[] { BoatFly.mc.player.rotationYaw, n };
    }
    
    public double getRelativeX(final float n) {
        return MathHelper.sin(-n * 0.017453292f);
    }
    
    public double getRelativeZ(final float n) {
        return MathHelper.cos(n * 0.017453292f);
    }
}
