package com.fasterxml.jackson.core.sym;

static final class Bucket
{
    public final String symbol;
    public final Bucket next;
    public final int length;
    
    public Bucket(final String a1, final Bucket a2) {
        super();
        this.symbol = a1;
        this.next = a2;
        this.length = ((a2 == null) ? 1 : (a2.length + 1));
    }
    
    public String has(final char[] a1, final int a2, final int a3) {
        if (this.symbol.length() != a3) {
            return null;
        }
        int v1 = 0;
        while (this.symbol.charAt(v1) == a1[a2 + v1]) {
            if (++v1 >= a3) {
                return this.symbol;
            }
        }
        return null;
    }
}
