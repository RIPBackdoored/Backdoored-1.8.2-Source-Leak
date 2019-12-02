package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.*;

@GwtIncompatible
private static final class BitSetMatcher extends NamedFastMatcher
{
    private final BitSet table;
    
    private BitSetMatcher(BitSet a1, final String a2) {
        super(a2);
        if (a1.length() + 64 < a1.size()) {
            a1 = (BitSet)a1.clone();
        }
        this.table = a1;
    }
    
    @Override
    public boolean matches(final char a1) {
        return this.table.get(a1);
    }
    
    @Override
    void setBits(final BitSet a1) {
        a1.or(this.table);
    }
    
    BitSetMatcher(final BitSet a1, final String a2, final CharMatcher$1 a3) {
        this(a1, a2);
    }
}
