package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.chunk.*;

@Mixin({ VisGraph.class })
public class MixinVisGraph
{
    public MixinVisGraph() {
        super();
    }
}
