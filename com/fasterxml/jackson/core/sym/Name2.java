package com.fasterxml.jackson.core.sym;

public final class Name2 extends Name
{
    private final int q1;
    private final int q2;
    
    Name2(final String a1, final int a2, final int a3, final int a4) {
        super(a1, a2);
        this.q1 = a3;
        this.q2 = a4;
    }
    
    @Override
    public boolean equals(final int a1) {
        return false;
    }
    
    @Override
    public boolean equals(final int a1, final int a2) {
        return a1 == this.q1 && a2 == this.q2;
    }
    
    @Override
    public boolean equals(final int a1, final int a2, final int a3) {
        return false;
    }
    
    @Override
    public boolean equals(final int[] a1, final int a2) {
        return a2 == 2 && a1[0] == this.q1 && a1[1] == this.q2;
    }
}
