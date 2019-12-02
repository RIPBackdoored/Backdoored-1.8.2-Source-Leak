package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.throwables.*;

public class InvalidMemberDescriptorException extends MixinException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidMemberDescriptorException(final String a1) {
        super(a1);
    }
    
    public InvalidMemberDescriptorException(final Throwable a1) {
        super(a1);
    }
    
    public InvalidMemberDescriptorException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
