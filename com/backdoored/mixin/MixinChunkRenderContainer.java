package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.e.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ChunkRenderContainer.class })
public class MixinChunkRenderContainer
{
    public MixinChunkRenderContainer() {
        super();
    }
    
    @Inject(method = { "preRenderChunk" }, at = { @At("HEAD") })
    public void preRenderChunk(final RenderChunk a1, final CallbackInfo a2) {
        final a.a v1 = new a.a(a1);
        MinecraftForge.EVENT_BUS.post((Event)v1);
    }
}
