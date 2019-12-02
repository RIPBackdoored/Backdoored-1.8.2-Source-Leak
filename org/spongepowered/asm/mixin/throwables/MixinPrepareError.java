package org.spongepowered.asm.mixin.throwables;

public class MixinPrepareError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public MixinPrepareError(final String a1) {
        super(a1);
    }
    
    public MixinPrepareError(final Throwable a1) {
        super(a1);
    }
    
    public MixinPrepareError(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
