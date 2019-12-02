package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.refmap.*;

public class InvalidAccessorException extends InvalidMixinException
{
    private static final long serialVersionUID = 2L;
    private final AccessorInfo info;
    
    public InvalidAccessorException(final IMixinContext a1, final String a2) {
        super(a1, a2);
        this.info = null;
    }
    
    public InvalidAccessorException(final AccessorInfo a1, final String a2) {
        super(a1.getContext(), a2);
        this.info = a1;
    }
    
    public InvalidAccessorException(final IMixinContext a1, final Throwable a2) {
        super(a1, a2);
        this.info = null;
    }
    
    public InvalidAccessorException(final AccessorInfo a1, final Throwable a2) {
        super(a1.getContext(), a2);
        this.info = a1;
    }
    
    public InvalidAccessorException(final IMixinContext a1, final String a2, final Throwable a3) {
        super(a1, a2, a3);
        this.info = null;
    }
    
    public InvalidAccessorException(final AccessorInfo a1, final String a2, final Throwable a3) {
        super(a1.getContext(), a2, a3);
        this.info = a1;
    }
    
    public AccessorInfo getAccessorInfo() {
        return this.info;
    }
}
