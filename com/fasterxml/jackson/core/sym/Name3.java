package com.fasterxml.jackson.core.sym;

public final class Name3 extends Name
{
    private final int q1;
    private final int q2;
    private final int q3;
    
    Name3(final String a1, final int a2, final int a3, final int a4, final int a5) {
        super(a1, a2);
        this.q1 = a3;
        this.q2 = a4;
        this.q3 = a5;
    }
    
    @Override
    public boolean equals(final int a1) {
        return false;
    }
    
    @Override
    public boolean equals(final int a1, final int a2) {
        return false;
    }
    
    @Override
    public boolean equals(final int a1, final int a2, final int a3) {
        return this.q1 == a1 && this.q2 == a2 && this.q3 == a3;
    }
    
    @Override
    public boolean equals(final int[] a1, final int a2) {
        return a2 == 3 && a1[0] == this.q1 && a1[1] == this.q2 && a1[2] == this.q3;
    }
}
