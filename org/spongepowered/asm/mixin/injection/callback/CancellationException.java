package org.spongepowered.asm.mixin.injection.callback;

public class CancellationException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public CancellationException() {
        super();
    }
    
    public CancellationException(final String a1) {
        super(a1);
    }
    
    public CancellationException(final Throwable a1) {
        super(a1);
    }
    
    public CancellationException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
