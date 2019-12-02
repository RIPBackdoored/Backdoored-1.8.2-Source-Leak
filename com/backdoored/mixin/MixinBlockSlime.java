package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;

@Mixin({ BlockSlime.class })
public class MixinBlockSlime
{
    public MixinBlockSlime() {
        super();
    }
}
