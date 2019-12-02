package org.spongepowered.asm.mixin.transformer.throwables;

public class MixinTransformerError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public MixinTransformerError(final String a1) {
        super(a1);
    }
    
    public MixinTransformerError(final Throwable a1) {
        super(a1);
    }
    
    public MixinTransformerError(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
