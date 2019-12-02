package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderChunk.class })
public class MixinRenderChunk
{
    public MixinRenderChunk() {
        super();
    }
    
    @Inject(method = { "setPosition" }, at = { @At("HEAD") })
    public void setPosition(final int a1, final int a2, final int a3, final CallbackInfo a4) {
    }
}
