package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.e.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { EntityRenderer.class }, priority = 999999999)
public class MixinEntityRenderer
{
    public MixinEntityRenderer() {
        super();
    }
    
    @Inject(method = { "renderWorld(FJ)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onRenderWorld(final CallbackInfo a1) {
        final m v1 = new m();
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.isCanceled()) {
            a1.cancel();
        }
    }
}
