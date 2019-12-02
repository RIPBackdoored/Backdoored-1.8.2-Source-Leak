package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.struct.*;

public class InvalidInjectionPointException extends InvalidInjectionException
{
    private static final long serialVersionUID = 2L;
    
    public InvalidInjectionPointException(final IMixinContext a1, final String a2, final Object... a3) {
        super(a1, String.format(a2, a3));
    }
    
    public InvalidInjectionPointException(final InjectionInfo a1, final String a2, final Object... a3) {
        super(a1, String.format(a2, a3));
    }
    
    public InvalidInjectionPointException(final IMixinContext a1, final Throwable a2, final String a3, final Object... a4) {
        super(a1, String.format(a3, a4), a2);
    }
    
    public InvalidInjectionPointException(final InjectionInfo a1, final Throwable a2, final String a3, final Object... a4) {
        super(a1, String.format(a3, a4), a2);
    }
}
