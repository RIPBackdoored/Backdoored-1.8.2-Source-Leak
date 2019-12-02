package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.refmap.*;

public class InvalidInjectionException extends InvalidMixinException
{
    private static final long serialVersionUID = 2L;
    private final InjectionInfo info;
    
    public InvalidInjectionException(final IMixinContext a1, final String a2) {
        super(a1, a2);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo a1, final String a2) {
        super(a1.getContext(), a2);
        this.info = a1;
    }
    
    public InvalidInjectionException(final IMixinContext a1, final Throwable a2) {
        super(a1, a2);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo a1, final Throwable a2) {
        super(a1.getContext(), a2);
        this.info = a1;
    }
    
    public InvalidInjectionException(final IMixinContext a1, final String a2, final Throwable a3) {
        super(a1, a2, a3);
        this.info = null;
    }
    
    public InvalidInjectionException(final InjectionInfo a1, final String a2, final Throwable a3) {
        super(a1.getContext(), a2, a3);
        this.info = a1;
    }
    
    public InjectionInfo getInjectionInfo() {
        return this.info;
    }
}
