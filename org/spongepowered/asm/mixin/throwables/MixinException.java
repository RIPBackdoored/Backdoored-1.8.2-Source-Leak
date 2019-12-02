package org.spongepowered.asm.mixin.throwables;

public class MixinException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public MixinException() {
        super();
    }
    
    public MixinException(final String a1) {
        super(a1);
    }
    
    public MixinException(final Throwable a1) {
        super(a1);
    }
    
    public MixinException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
