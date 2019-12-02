package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;

@Mixin({ BlockShulkerBox.class })
public class MixinBlockShulkerBox
{
    public MixinBlockShulkerBox() {
        super();
    }
}
