package org.spongepowered.asm.mixin.throwables;

public class MixinApplyError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public MixinApplyError(final String a1) {
        super(a1);
    }
    
    public MixinApplyError(final Throwable a1) {
        super(a1);
    }
    
    public MixinApplyError(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
