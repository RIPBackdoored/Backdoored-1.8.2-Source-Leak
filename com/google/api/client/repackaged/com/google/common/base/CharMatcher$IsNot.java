package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class IsNot extends FastMatcher
{
    private final char match;
    
    IsNot(final char a1) {
        super();
        this.match = a1;
    }
    
    @Override
    public boolean matches(final char a1) {
        return a1 != this.match;
    }
    
    @Override
    public CharMatcher and(final CharMatcher a1) {
        return a1.matches(this.match) ? super.and(a1) : a1;
    }
    
    @Override
    public CharMatcher or(final CharMatcher a1) {
        return a1.matches(this.match) ? CharMatcher.any() : this;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        a1.set(0, this.match);
        a1.set(this.match + '\u0001', 65536);
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.is(this.match);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.isNot('" + CharMatcher.access$100(this.match) + "')";
    }
}
