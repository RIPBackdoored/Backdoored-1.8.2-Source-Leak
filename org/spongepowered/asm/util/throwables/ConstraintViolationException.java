package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.*;

public class ConstraintViolationException extends Exception
{
    private static final String MISSING_VALUE = "UNRESOLVED";
    private static final long serialVersionUID = 1L;
    private final ConstraintParser.Constraint constraint;
    private final String badValue;
    
    public ConstraintViolationException(final ConstraintParser.Constraint a1) {
        super();
        this.constraint = a1;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final ConstraintParser.Constraint a1, final int a2) {
        super();
        this.constraint = a1;
        this.badValue = String.valueOf(a2);
    }
    
    public ConstraintViolationException(final String a1, final ConstraintParser.Constraint a2) {
        super(a1);
        this.constraint = a2;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final String a1, final ConstraintParser.Constraint a2, final int a3) {
        super(a1);
        this.constraint = a2;
        this.badValue = String.valueOf(a3);
    }
    
    public ConstraintViolationException(final Throwable a1, final ConstraintParser.Constraint a2) {
        super(a1);
        this.constraint = a2;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final Throwable a1, final ConstraintParser.Constraint a2, final int a3) {
        super(a1);
        this.constraint = a2;
        this.badValue = String.valueOf(a3);
    }
    
    public ConstraintViolationException(final String a1, final Throwable a2, final ConstraintParser.Constraint a3) {
        super(a1, a2);
        this.constraint = a3;
        this.badValue = "UNRESOLVED";
    }
    
    public ConstraintViolationException(final String a1, final Throwable a2, final ConstraintParser.Constraint a3, final int a4) {
        super(a1, a2);
        this.constraint = a3;
        this.badValue = String.valueOf(a4);
    }
    
    public ConstraintParser.Constraint getConstraint() {
        return this.constraint;
    }
    
    public String getBadValue() {
        return this.badValue;
    }
}
