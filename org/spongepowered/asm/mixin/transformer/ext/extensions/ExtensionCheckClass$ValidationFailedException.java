package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.throwables.*;

public static class ValidationFailedException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public ValidationFailedException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
    
    public ValidationFailedException(final String a1) {
        super(a1);
    }
    
    public ValidationFailedException(final Throwable a1) {
        super(a1);
    }
}
