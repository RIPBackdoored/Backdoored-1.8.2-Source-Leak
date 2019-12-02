package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class Is extends FastMatcher
{
    private final char match;
    
    Is(final char a1) {
        super();
        this.match = a1;
    }
    
    @Override
    public boolean matches(final char a1) {
        return a1 == this.match;
    }
    
    @Override
    public String replaceFrom(final CharSequence a1, final char a2) {
        return a1.toString().replace(this.match, a2);
    }
    
    @Override
    public CharMatcher and(final CharMatcher a1) {
        return a1.matches(this.match) ? this : CharMatcher.none();
    }
    
    @Override
    public CharMatcher or(final CharMatcher a1) {
        return a1.matches(this.match) ? a1 : super.or(a1);
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.isNot(this.match);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        a1.set(this.match);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.is('" + CharMatcher.access$100(this.match) + "')";
    }
}
