package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.e.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import a.a.d.a.*;

@Mixin({ World.class })
public class MixinWorld
{
    public MixinWorld() {
        super();
    }
    
    @Inject(method = { "getSunBrightnessFactor" }, at = { @At("RETURN") }, cancellable = true, remap = false)
    private void getBrightnessOfSun(final float a1, final CallbackInfoReturnable<Float> a2) {
        final c v1 = new c(a2.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        a2.setReturnValue(v1.ek);
    }
    
    @Inject(method = { "getSunBrightnessBody" }, at = { @At("RETURN") }, cancellable = true, remap = false)
    private void getBrightnessBodyOfSun(final float a1, final CallbackInfoReturnable<Float> a2) {
        final c v1 = new c(a2.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        a2.setReturnValue(v1.ek);
    }
    
    @Inject(method = { "checkLightFor" }, at = { @At("HEAD") }, cancellable = true)
    private void checkLightForWrapper(final EnumSkyBlock a1, final BlockPos a2, final CallbackInfoReturnable<Boolean> a3) {
        final b v1 = new b(null);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.by != null) {
            a3.setReturnValue(v1.by);
        }
    }
}
