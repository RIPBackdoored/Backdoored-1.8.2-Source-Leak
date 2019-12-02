package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.entity.*;
import a.a.k.d.*;
import com.backdoored.setting.*;
import a.a.d.b.f.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.d.*;
import net.minecraft.network.play.client.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.network.*;

@b.a(name = "Smooth Movement", description = "Better Movement", category = CategoriesInit.MOVEMENT)
public class SmoothMovement extends BaseHack
{
    private Setting<Boolean> elytraLand;
    private Setting<Boolean> faceMovement;
    private Setting<Float> sensitivity;
    private Setting<Boolean> faceSilent;
    private Setting<Boolean> antiStopSprint;
    private Entity entity;
    private a ro;
    
    public SmoothMovement() {
        super();
        this.elytraLand = new BooleanSetting("Elytra Land", this, true);
        this.faceMovement = new BooleanSetting("Face Movement", this, false);
        this.sensitivity = new FloatSetting("Face Sensitivity", this, 0.001f, 0.0f, 0.1f);
        this.faceSilent = new BooleanSetting("Face Silent", this, true);
        this.antiStopSprint = new BooleanSetting("Anti StopSprint", this, true);
    }
    
    @SubscribeEvent
    public void a(final e e) {
        if (this.getEnabled()) {
            e.da = false;
        }
    }
    
    @SubscribeEvent
    public void a(final c c) {
        if (c.packet instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)c.packet;
            final float yaw = cPacketPlayer.getYaw(-420.0f);
            if (yaw != -420.0f) {
                final float pitch = cPacketPlayer.getPitch(-420.0f);
                if (pitch != -420.0f) {
                    this.ro = a.b(pitch, yaw);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void a(final a.a.d.b.f.a a) {
        if (this.getEnabled() && this.antiStopSprint.getValInt()) {
            a.ct = false;
        }
    }
    
    public void onTick() {
        if (this.getEnabled()) {
            if (this.entity == null) {
                this.entity = (Entity)new EntityOtherPlayerMP((World)SmoothMovement.mc.world, SmoothMovement.mc.player.getGameProfile());
            }
            if (this.ro != null) {
                this.entity.setPosition(SmoothMovement.mc.player.posX, SmoothMovement.mc.player.posY, SmoothMovement.mc.player.posZ);
                this.entity.noClip = true;
                this.entity.rotationYaw = this.ro.f();
                this.entity.rotationPitch = this.ro.e();
            }
            else {
                this.entity.setPosition(0.0, -420.0, 0.0);
            }
        }
        if (this.getEnabled() && this.faceMovement.getValInt()) {
            final Vec3d add = new Vec3d(SmoothMovement.mc.player.motionX, 0.0, SmoothMovement.mc.player.motionZ).add(SmoothMovement.mc.player.getPositionVector());
            if (SmoothMovement.mc.player.motionX > this.sensitivity.getValInt() || SmoothMovement.mc.player.motionZ > this.sensitivity.getValInt()) {
                final a b = a.a.k.d.b.b(add.subtract(new Vec3d(SmoothMovement.mc.player.posX, SmoothMovement.mc.player.posY + SmoothMovement.mc.player.getEyeHeight(), SmoothMovement.mc.player.posZ)).normalize());
                if (this.faceSilent.getValInt()) {
                    SmoothMovement.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(b.f(), SmoothMovement.mc.player.rotationPitch, SmoothMovement.mc.player.onGround));
                }
                else {
                    SmoothMovement.mc.player.rotationYaw = b.f();
                }
            }
        }
    }
}
