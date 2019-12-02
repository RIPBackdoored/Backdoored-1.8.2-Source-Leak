package org.spongepowered.asm.mixin.injection.throwables;

public class InjectionError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public InjectionError() {
        super();
    }
    
    public InjectionError(final String a1) {
        super(a1);
    }
    
    public InjectionError(final Throwable a1) {
        super(a1);
    }
    
    public InjectionError(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
