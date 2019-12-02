package org.spongepowered.asm.mixin.throwables;

public class ClassAlreadyLoadedException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public ClassAlreadyLoadedException(final String a1) {
        super(a1);
    }
    
    public ClassAlreadyLoadedException(final Throwable a1) {
        super(a1);
    }
    
    public ClassAlreadyLoadedException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
