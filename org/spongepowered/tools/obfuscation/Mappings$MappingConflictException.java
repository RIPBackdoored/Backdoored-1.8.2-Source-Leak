package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.obfuscation.mapping.*;

public static class MappingConflictException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private final IMapping<?> oldMapping;
    private final IMapping<?> newMapping;
    
    public MappingConflictException(final IMapping<?> a1, final IMapping<?> a2) {
        super();
        this.oldMapping = a1;
        this.newMapping = a2;
    }
    
    public IMapping<?> getOld() {
        return this.oldMapping;
    }
    
    public IMapping<?> getNew() {
        return this.newMapping;
    }
}
