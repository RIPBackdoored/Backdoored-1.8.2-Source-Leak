package com.backdoored.mixin;

import net.minecraft.client.renderer.*;
import java.nio.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ BufferBuilder.class })
public class MixinBufferBuilder
{
    private int startBufferSizeIn;
    @Shadow
    private IntBuffer field_178999_b;
    
    public MixinBufferBuilder() {
        super();
    }
}
