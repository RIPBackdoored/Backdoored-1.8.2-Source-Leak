package org.spongepowered.asm.util;

import java.util.regex.*;
import org.spongepowered.asm.util.throwables.*;

public static class Constraint
{
    public static final Constraint NONE;
    private static final Pattern pattern;
    private final String expr;
    private String token;
    private String[] constraint;
    private int min;
    private int max;
    private Constraint next;
    
    Constraint(final String a1) {
        super();
        this.min = Integer.MIN_VALUE;
        this.max = 0;
        this.expr = a1;
        final Matcher v1 = Constraint.pattern.matcher(a1);
        if (!v1.matches()) {
            throw new InvalidConstraintException("Constraint syntax was invalid parsing: " + this.expr);
        }
        this.token = v1.group(1);
        this.constraint = new String[] { v1.group(2), v1.group(3), v1.group(4), v1.group(5), v1.group(6), v1.group(7), v1.group(8) };
        this.parse();
    }
    
    private Constraint() {
        super();
        this.min = Integer.MIN_VALUE;
        this.max = 0;
        this.expr = null;
        this.token = "*";
        this.constraint = new String[0];
    }
    
    private void parse() {
        if (!this.has(1)) {
            return;
        }
        final int val = this.val(1);
        this.min = val;
        this.max = val;
        final boolean v0 = this.has(0);
        if (this.has(4)) {
            if (v0) {
                throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
            }
            this.max = this.val(4);
            if (this.max < this.min) {
                throw new InvalidConstraintException("Invalid range specified '" + this.max + "' is less than " + this.min + " in " + this.expr);
            }
        }
        else {
            if (!this.has(6)) {
                if (v0) {
                    if (this.has(3)) {
                        throw new InvalidConstraintException("Unexpected trailing modifier '" + this.elem(3) + "' in " + this.expr);
                    }
                    final String v2 = this.elem(0);
                    if (">".equals(v2)) {
                        ++this.min;
                        this.max = 0;
                    }
                    else if (">=".equals(v2)) {
                        this.max = 0;
                    }
                    else if ("<".equals(v2)) {
                        final int n = this.min - 1;
                        this.min = n;
                        this.max = n;
                        this.min = Integer.MIN_VALUE;
                    }
                    else if ("<=".equals(v2)) {
                        this.max = this.min;
                        this.min = Integer.MIN_VALUE;
                    }
                }
                else if (this.has(2)) {
                    final String v2 = this.elem(2);
                    if ("<".equals(v2)) {
                        this.max = this.min;
                        this.min = Integer.MIN_VALUE;
                    }
                    else {
                        this.max = 0;
                    }
                }
                return;
            }
            if (v0) {
                throw new InvalidConstraintException("Unexpected modifier '" + this.elem(0) + "' in " + this.expr + " parsing range");
            }
            this.max = this.min + this.val(6);
        }
    }
    
    private boolean has(final int a1) {
        return this.constraint[a1] != null;
    }
    
    private String elem(final int a1) {
        return this.constraint[a1];
    }
    
    private int val(final int a1) {
        return (this.constraint[a1] != null) ? Integer.parseInt(this.constraint[a1]) : 0;
    }
    
    void append(final Constraint a1) {
        if (this.next != null) {
            this.next.append(a1);
            return;
        }
        this.next = a1;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public int getMin() {
        return this.min;
    }
    
    public int getMax() {
        return this.max;
    }
    
    public void check(final ITokenProvider v2) throws ConstraintViolationException {
        if (this != Constraint.NONE) {
            final Integer a1 = v2.getToken(this.token);
            if (a1 == null) {
                throw new ConstraintViolationException("The token '" + this.token + "' could not be resolved in " + v2, this);
            }
            if (a1 < this.min) {
                throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + a1 + ") which is less than the minimum value " + this.min + " in " + v2, this, a1);
            }
            if (a1 > this.max) {
                throw new ConstraintViolationException("Token '" + this.token + "' has a value (" + a1 + ") which is greater than the maximum value " + this.max + " in " + v2, this, a1);
            }
        }
        if (this.next != null) {
            this.next.check(v2);
        }
    }
    
    public String getRangeHumanReadable() {
        if (this.min == Integer.MIN_VALUE && this.max == 0) {
            return "ANY VALUE";
        }
        if (this.min == Integer.MIN_VALUE) {
            return String.format("less than or equal to %d", this.max);
        }
        if (this.max == 0) {
            return String.format("greater than or equal to %d", this.min);
        }
        if (this.min == this.max) {
            return String.format("%d", this.min);
        }
        return String.format("between %d and %d", this.min, this.max);
    }
    
    @Override
    public String toString() {
        return String.format("Constraint(%s [%d-%d])", this.token, this.min, this.max);
    }
    
    static {
        NONE = new Constraint();
        pattern = Pattern.compile("^([A-Z0-9\\-_\\.]+)\\((?:(<|<=|>|>=|=)?([0-9]+)(<|(-)([0-9]+)?|>|(\\+)([0-9]+)?)?)?\\)$");
    }
}
