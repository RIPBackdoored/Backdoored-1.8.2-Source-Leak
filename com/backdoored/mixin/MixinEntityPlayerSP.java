package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.e.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.backdoored.hacks.movement.*;
import a.a.g.b.*;
import net.minecraft.client.network.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.*;
import a.a.d.b.f.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer
{
    private Minecraft mc;
    
    public MixinEntityPlayerSP() {
        super();
        this.mc = Minecraft.getMinecraft();
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("HEAD") })
    private void preMotion(final CallbackInfo a1) {
        final k.b v1 = new k.b(this.mc.player.rotationYaw, this.mc.player.rotationPitch, this.mc.player.onGround);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.mc.player.rotationYaw = v1.ep;
        this.mc.player.rotationPitch = v1.eq;
        this.mc.player.onGround = v1.er;
    }
    
    @Inject(method = { "onUpdateWalkingPlayer" }, at = { @At("RETURN") })
    private void postMotion(final CallbackInfo a1) {
        final k.a v1 = new k.a(this.mc.player.rotationYaw, this.mc.player.rotationPitch, this.mc.player.onGround);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.mc.player.rotationYaw = v1.ep;
        this.mc.player.rotationPitch = v1.eq;
        this.mc.player.onGround = v1.er;
    }
    
    @Override
    public void jump() {
        try {
            final double v1 = ((EntityPlayerSP)this).motionX;
            final double v2 = ((EntityPlayerSP)this).motionZ;
            super.jump();
            ((Speed)c.a((Class)Speed.class)).a(v1, v2, (EntityPlayerSP)this);
        }
        catch (Exception v3) {
            v3.printStackTrace();
        }
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetHandlerPlayClient;sendPacket(Lnet/minecraft/network/Packet;)V"))
    private void onSendElytraStartPacket(final NetHandlerPlayClient a1, final Packet<?> a2) {
        final e v1 = new e(this.mc.world.isRemote);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (!v1.da && !this.isInWater()) {
            this.setFlag(7, true);
        }
        a1.sendPacket((Packet)a2);
    }
    
    @Redirect(method = { "onLivingUpdate" }, at = @At(value = "FIELD", target = "Lnet/minecraft/client/entity/EntityPlayerSP;collidedHorizontally:Z"))
    private boolean overrideCollidedHorizontally(final EntityPlayerSP a1) {
        final a v1 = new a(a1.collidedHorizontally);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.ct;
    }
}
