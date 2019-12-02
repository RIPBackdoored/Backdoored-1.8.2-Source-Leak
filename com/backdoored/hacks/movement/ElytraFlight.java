package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.math.*;
import net.minecraft.client.entity.*;

@b.a(name = "ElytraFlight", description = "Rockets aren't needed", category = CategoriesInit.MOVEMENT)
public class ElytraFlight extends BaseHack
{
    private Setting<a.a.g.b.f.b.a> mode;
    private Setting<Double> boost;
    private Setting<Double> flightSpeed;
    private Setting<Double> ctrlSpeed;
    
    public ElytraFlight() {
        super();
        this.mode = new EnumSetting<a.a.g.b.f.b.a>("Mode", (BaseHack)this, (Enum)a.a.g.b.f.b.a.qz);
        this.boost = new DoubleSetting("Boost-Speed", this, 0.05, 0.01, 0.2);
        this.flightSpeed = new DoubleSetting("Flight-Speed", this, 0.05, 0.01, 0.2);
        this.ctrlSpeed = new DoubleSetting("Control-Speed", this, 0.9, 0.01, 4.0);
    }
    
    public void onTick() {
        if (!ElytraFlight.mc.player.isElytraFlying() || !this.getEnabled()) {
            return;
        }
        if (this.mode.getValInt() == a.a.g.b.f.b.a.qz) {
            if (ElytraFlight.mc.player.capabilities.isFlying) {
                ElytraFlight.mc.player.capabilities.isFlying = false;
            }
            if (ElytraFlight.mc.player.isInWater()) {
                ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            final float n = (float)Math.toRadians(ElytraFlight.mc.player.rotationYaw);
            if (ElytraFlight.mc.gameSettings.keyBindForward.isKeyDown()) {
                final EntityPlayerSP player = ElytraFlight.mc.player;
                player.motionX -= MathHelper.sin(n) * this.boost.getValInt();
                final EntityPlayerSP player2 = ElytraFlight.mc.player;
                player2.motionZ += MathHelper.cos(n) * this.boost.getValInt();
            }
            else if (ElytraFlight.mc.gameSettings.keyBindBack.isKeyDown()) {
                final EntityPlayerSP player3 = ElytraFlight.mc.player;
                player3.motionX += MathHelper.sin(n) * this.boost.getValInt();
                final EntityPlayerSP player4 = ElytraFlight.mc.player;
                player4.motionZ -= MathHelper.cos(n) * this.boost.getValInt();
            }
        }
        if (this.mode.getValInt() == a.a.g.b.f.b.a.rb) {
            ElytraFlight.mc.player.capabilities.isFlying = true;
            ElytraFlight.mc.player.jumpMovementFactor = this.flightSpeed.getValInt().floatValue();
            if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP player5 = ElytraFlight.mc.player;
                player5.motionY += this.flightSpeed.getValInt();
            }
            if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                final EntityPlayerSP player6 = ElytraFlight.mc.player;
                player6.motionY -= this.flightSpeed.getValInt();
            }
        }
        if (this.mode.getValInt() == a.a.g.b.f.b.a.ra) {
            ElytraFlight.mc.player.motionY = 0.0;
            ElytraFlight.mc.player.motionX = 0.0;
            ElytraFlight.mc.player.motionZ = 0.0;
            final float n2 = (float)Math.toRadians(ElytraFlight.mc.player.rotationYaw);
            final float n3 = (float)Math.toRadians(ElytraFlight.mc.player.rotationPitch);
            if (ElytraFlight.mc.gameSettings.keyBindForward.isKeyDown()) {
                ElytraFlight.mc.player.motionX = -(MathHelper.sin(n2) * MathHelper.cos(n3) * this.ctrlSpeed.getValInt());
                ElytraFlight.mc.player.motionZ = MathHelper.cos(n2) * MathHelper.cos(n3) * this.ctrlSpeed.getValInt();
                ElytraFlight.mc.player.motionY = -(MathHelper.sin(n3) * this.ctrlSpeed.getValInt());
            }
            else if (ElytraFlight.mc.gameSettings.keyBindBack.isKeyDown()) {
                ElytraFlight.mc.player.motionX = MathHelper.sin(n2) * MathHelper.cos(n3) * this.ctrlSpeed.getValInt();
                ElytraFlight.mc.player.motionZ = -(MathHelper.cos(n2) * MathHelper.cos(n3) * this.ctrlSpeed.getValInt());
                ElytraFlight.mc.player.motionY = MathHelper.sin(n3) * this.ctrlSpeed.getValInt();
            }
            if (ElytraFlight.mc.gameSettings.keyBindLeft.isKeyDown()) {
                ElytraFlight.mc.player.motionZ = MathHelper.sin(n2) * MathHelper.cos(n3) * this.ctrlSpeed.getValInt();
                ElytraFlight.mc.player.motionX = MathHelper.cos(n2) * MathHelper.cos(n3) * this.ctrlSpeed.getValInt();
            }
            else if (ElytraFlight.mc.gameSettings.keyBindRight.isKeyDown()) {
                ElytraFlight.mc.player.motionZ = -(MathHelper.sin(n2) * this.ctrlSpeed.getValInt());
                ElytraFlight.mc.player.motionX = -(MathHelper.cos(n2) * this.ctrlSpeed.getValInt());
            }
            if (ElytraFlight.mc.gameSettings.keyBindJump.isKeyDown()) {
                ElytraFlight.mc.player.motionY = this.ctrlSpeed.getValInt();
            }
            else if (ElytraFlight.mc.gameSettings.keyBindSneak.isKeyDown()) {
                ElytraFlight.mc.player.motionY = -this.ctrlSpeed.getValInt();
            }
        }
    }
    
    public void onEnabled() {
        if (this.mode.getValInt() != a.a.g.b.f.b.a.rb) {
            return;
        }
        ElytraFlight.mc.player.capabilities.setFlySpeed(this.flightSpeed.getValInt().floatValue());
        ElytraFlight.mc.addScheduledTask(this::c);
    }
    
    public void onDisabled() {
        if (this.mode.getValInt() == a.a.g.b.f.b.a.rb) {
            ElytraFlight.mc.player.capabilities.isFlying = false;
            ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    
    private /* synthetic */ void c() {
        if (ElytraFlight.mc.player != null && ElytraFlight.mc.player.isElytraFlying() && this.getEnabled() && this.mode.getValInt() == a.a.g.b.f.b.a.rb) {
            ElytraFlight.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)ElytraFlight.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
}
