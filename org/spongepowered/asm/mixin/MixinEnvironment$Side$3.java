package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

enum MixinEnvironment$Side$3
{
    MixinEnvironment$Side$3(final String a1, final int a2) {
    }
    
    @Override
    protected boolean detect() {
        final String v1 = MixinService.getService().getSideName();
        return "SERVER".equals(v1) || "DEDICATEDSERVER".equals(v1);
    }
}