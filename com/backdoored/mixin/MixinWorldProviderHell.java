package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.world.*;
import a.a.d.e.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ WorldProviderHell.class })
public class MixinWorldProviderHell
{
    public MixinWorldProviderHell() {
        super();
    }
    
    @ModifyConstant(method = { "generateLightBrightnessTable" }, constant = { @Constant(floatValue = 0.9f) })
    private float getBrightness(final float a1) {
        final c v1 = new c(a1);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.ek;
    }
}
