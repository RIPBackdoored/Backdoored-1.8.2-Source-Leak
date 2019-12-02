package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.*;

public class InvalidImplicitDiscriminatorException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidImplicitDiscriminatorException(final String a1) {
        super(a1);
    }
    
    public InvalidImplicitDiscriminatorException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
