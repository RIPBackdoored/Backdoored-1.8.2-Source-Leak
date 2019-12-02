package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.*;

public class InjectionValidationException extends Exception
{
    private static final long serialVersionUID = 1L;
    private final InjectorGroupInfo group;
    
    public InjectionValidationException(final InjectorGroupInfo a1, final String a2) {
        super(a2);
        this.group = a1;
    }
    
    public InjectorGroupInfo getGroup() {
        return this.group;
    }
}
