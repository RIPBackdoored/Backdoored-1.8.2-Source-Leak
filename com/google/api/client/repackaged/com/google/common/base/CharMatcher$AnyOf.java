package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class AnyOf extends CharMatcher
{
    private final char[] chars;
    
    public AnyOf(final CharSequence a1) {
        super();
        Arrays.sort(this.chars = a1.toString().toCharArray());
    }
    
    @Override
    public boolean matches(final char a1) {
        return Arrays.binarySearch(this.chars, a1) >= 0;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet v0) {
        for (final char a1 : this.chars) {
            v0.set(a1);
        }
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CharMatcher.anyOf(\"");
        for (final char v2 : this.chars) {
            sb.append(CharMatcher.access$100(v2));
        }
        sb.append("\")");
        return sb.toString();
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
}
