package org.spongepowered.asm.launch;

public class MixinInitialisationError extends Error
{
    private static final long serialVersionUID = 1L;
    
    public MixinInitialisationError() {
        super();
    }
    
    public MixinInitialisationError(final String a1) {
        super(a1);
    }
    
    public MixinInitialisationError(final Throwable a1) {
        super(a1);
    }
    
    public MixinInitialisationError(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
