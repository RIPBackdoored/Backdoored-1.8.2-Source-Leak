package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import a.a.d.e.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ WorldClient.class })
public class MixinWorldClient
{
    public MixinWorldClient() {
        super();
    }
    
    @Redirect(method = { "makeFireworks" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleManager;addEffect(Lnet/minecraft/client/particle/Particle;)V"))
    private void makeFireworkParticles(final ParticleManager a1, final Particle a2) {
        final f v1 = new f();
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (!v1.isCanceled()) {
            a1.addEffect(a2);
        }
    }
}
