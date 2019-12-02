package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@GwtIncompatible
final class SmallCharMatcher extends NamedFastMatcher
{
    static final int MAX_SIZE = 1023;
    private final char[] table;
    private final boolean containsZero;
    private final long filter;
    private static final int C1 = -862048943;
    private static final int C2 = 461845907;
    private static final double DESIRED_LOAD_FACTOR = 0.5;
    
    private SmallCharMatcher(final char[] a1, final long a2, final boolean a3, final String a4) {
        super(a4);
        this.table = a1;
        this.filter = a2;
        this.containsZero = a3;
    }
    
    static int smear(final int a1) {
        return 461845907 * Integer.rotateLeft(a1 * -862048943, 15);
    }
    
    private boolean checkFilter(final int a1) {
        return 0x1L == (0x1L & this.filter >> a1);
    }
    
    @VisibleForTesting
    static int chooseTableSize(final int a1) {
        if (a1 == 1) {
            return 2;
        }
        int v1;
        for (v1 = Integer.highestOneBit(a1 - 1) << 1; v1 * 0.5 < a1; v1 <<= 1) {}
        return v1;
    }
    
    static CharMatcher from(final BitSet v1, final String v2) {
        long v3 = 0L;
        final int v4 = v1.cardinality();
        final boolean v5 = v1.get(0);
        final char[] v6 = new char[chooseTableSize(v4)];
        final int v7 = v6.length - 1;
        for (int a2 = v1.nextSetBit(0); a2 != -1; a2 = v1.nextSetBit(a2 + 1)) {
            v3 |= 1L << a2;
            int a3;
            for (a3 = (smear(a2) & v7); v6[a3] != '\0'; a3 = (a3 + 1 & v7)) {}
            v6[a3] = (char)a2;
        }
        return new SmallCharMatcher(v6, v3, v5, v2);
    }
    
    @Override
    public boolean matches(final char a1) {
        if (a1 == '\0') {
            return this.containsZero;
        }
        if (!this.checkFilter(a1)) {
            return false;
        }
        final int v1 = this.table.length - 1;
        int v3;
        final int v2 = v3 = (smear(a1) & v1);
        while (this.table[v3] != '\0') {
            if (this.table[v3] == a1) {
                return true;
            }
            v3 = (v3 + 1 & v1);
            if (v3 == v2) {
                return false;
            }
        }
        return false;
    }
    
    @Override
    void setBits(final BitSet v0) {
        if (this.containsZero) {
            v0.set(0);
        }
        for (final char a1 : this.table) {
            if (a1 != '\0') {
                v0.set(a1);
            }
        }
    }
}
