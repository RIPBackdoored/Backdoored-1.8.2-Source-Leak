package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.code.*;

public class InvalidSliceException extends InvalidInjectionException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidSliceException(final IMixinContext a1, final String a2) {
        super(a1, a2);
    }
    
    public InvalidSliceException(final ISliceContext a1, final String a2) {
        super(a1.getContext(), a2);
    }
    
    public InvalidSliceException(final IMixinContext a1, final Throwable a2) {
        super(a1, a2);
    }
    
    public InvalidSliceException(final ISliceContext a1, final Throwable a2) {
        super(a1.getContext(), a2);
    }
    
    public InvalidSliceException(final IMixinContext a1, final String a2, final Throwable a3) {
        super(a1, a2, a3);
    }
    
    public InvalidSliceException(final ISliceContext a1, final String a2, final Throwable a3) {
        super(a1.getContext(), a2, a3);
    }
}
