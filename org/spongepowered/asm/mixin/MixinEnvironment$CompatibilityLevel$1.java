package org.spongepowered.asm.mixin;

import org.spongepowered.asm.util.*;

enum MixinEnvironment$CompatibilityLevel$1
{
    MixinEnvironment$CompatibilityLevel$1(final String a4, final int a5, final int a1, final int a2, final boolean a3) {
    }
    
    @Override
    boolean isSupported() {
        return JavaVersion.current() >= 1.7;
    }
}