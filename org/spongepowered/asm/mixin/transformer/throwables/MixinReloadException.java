package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

public class MixinReloadException extends MixinException
{
    private static final long serialVersionUID = 2L;
    private final IMixinInfo mixinInfo;
    
    public MixinReloadException(final IMixinInfo a1, final String a2) {
        super(a2);
        this.mixinInfo = a1;
    }
    
    public IMixinInfo getMixinInfo() {
        return this.mixinInfo;
    }
}
