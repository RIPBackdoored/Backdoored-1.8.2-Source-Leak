package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.*;

public class MixinTargetAlreadyLoadedException extends InvalidMixinException
{
    private static final long serialVersionUID = 1L;
    private final String target;
    
    public MixinTargetAlreadyLoadedException(final IMixinInfo a1, final String a2, final String a3) {
        super(a1, a2);
        this.target = a3;
    }
    
    public MixinTargetAlreadyLoadedException(final IMixinInfo a1, final String a2, final String a3, final Throwable a4) {
        super(a1, a2, a4);
        this.target = a3;
    }
    
    public String getTarget() {
        return this.target;
    }
}
