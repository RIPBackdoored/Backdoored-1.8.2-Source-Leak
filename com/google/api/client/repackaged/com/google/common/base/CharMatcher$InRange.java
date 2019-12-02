package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class InRange extends FastMatcher
{
    private final char startInclusive;
    private final char endInclusive;
    
    InRange(final char a1, final char a2) {
        super();
        Preconditions.checkArgument(a2 >= a1);
        this.startInclusive = a1;
        this.endInclusive = a2;
    }
    
    @Override
    public boolean matches(final char a1) {
        return this.startInclusive <= a1 && a1 <= this.endInclusive;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        a1.set(this.startInclusive, this.endInclusive + '\u0001');
    }
    
    @Override
    public String toString() {
        return "CharMatcher.inRange('" + CharMatcher.access$100(this.startInclusive) + "', '" + CharMatcher.access$100(this.endInclusive) + "')";
    }
}
