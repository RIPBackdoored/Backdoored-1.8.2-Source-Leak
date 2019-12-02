package org.spongepowered.tools.obfuscation;

public static class ReferenceConflictException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    private final String oldReference;
    private final String newReference;
    
    public ReferenceConflictException(final String a1, final String a2) {
        super();
        this.oldReference = a1;
        this.newReference = a2;
    }
    
    public String getOld() {
        return this.oldReference;
    }
    
    public String getNew() {
        return this.newReference;
    }
}
