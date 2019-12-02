package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.*;

public class LVTGeneratorException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public LVTGeneratorException(final String a1) {
        super(a1);
    }
    
    public LVTGeneratorException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
