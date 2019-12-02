package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

private static class RangesMatcher extends CharMatcher
{
    private final String description;
    private final char[] rangeStarts;
    private final char[] rangeEnds;
    
    RangesMatcher(final String a3, final char[] v1, final char[] v2) {
        super();
        this.description = a3;
        this.rangeStarts = v1;
        this.rangeEnds = v2;
        Preconditions.checkArgument(v1.length == v2.length);
        for (int a4 = 0; a4 < v1.length; ++a4) {
            Preconditions.checkArgument(v1[a4] <= v2[a4]);
            if (a4 + 1 < v1.length) {
                Preconditions.checkArgument(v2[a4] < v1[a4 + 1]);
            }
        }
    }
    
    @Override
    public boolean matches(final char a1) {
        int v1 = Arrays.binarySearch(this.rangeStarts, a1);
        if (v1 >= 0) {
            return true;
        }
        v1 = ~v1 - 1;
        return v1 >= 0 && a1 <= this.rangeEnds[v1];
    }
    
    @Override
    public String toString() {
        return this.description;
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
}
