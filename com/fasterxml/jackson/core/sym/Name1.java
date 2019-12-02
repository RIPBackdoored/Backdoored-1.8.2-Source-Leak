package com.fasterxml.jackson.core.sym;

public final class Name1 extends Name
{
    private static final Name1 EMPTY;
    private final int q;
    
    Name1(final String a1, final int a2, final int a3) {
        super(a1, a2);
        this.q = a3;
    }
    
    public static Name1 getEmptyName() {
        return Name1.EMPTY;
    }
    
    @Override
    public boolean equals(final int a1) {
        return a1 == this.q;
    }
    
    @Override
    public boolean equals(final int a1, final int a2) {
        return a1 == this.q && a2 == 0;
    }
    
    @Override
    public boolean equals(final int a1, final int a2, final int a3) {
        return false;
    }
    
    @Override
    public boolean equals(final int[] a1, final int a2) {
        return a2 == 1 && a1[0] == this.q;
    }
    
    static {
        EMPTY = new Name1("", 0, 0);
    }
}
