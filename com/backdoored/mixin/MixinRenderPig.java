package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;

@Mixin({ RenderPig.class })
public class MixinRenderPig
{
    public MixinRenderPig() {
        super();
    }
}
