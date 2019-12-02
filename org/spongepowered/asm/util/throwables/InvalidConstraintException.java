package org.spongepowered.asm.util.throwables;

public class InvalidConstraintException extends IllegalArgumentException
{
    private static final long serialVersionUID = 1L;
    
    public InvalidConstraintException() {
        super();
    }
    
    public InvalidConstraintException(final String a1) {
        super(a1);
    }
    
    public InvalidConstraintException(final Throwable a1) {
        super(a1);
    }
    
    public InvalidConstraintException(final String a1, final Throwable a2) {
        super(a1, a2);
    }
}
