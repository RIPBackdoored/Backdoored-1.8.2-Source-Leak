package org.spongepowered.asm.mixin;

import org.spongepowered.asm.service.*;

enum MixinEnvironment$Side$2
{
    MixinEnvironment$Side$2(final String a1, final int a2) {
    }
    
    @Override
    protected boolean detect() {
        final String v1 = MixinService.getService().getSideName();
        return "CLIENT".equals(v1);
    }
}