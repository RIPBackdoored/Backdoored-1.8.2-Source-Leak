package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static class Negated extends CharMatcher
{
    final CharMatcher original;
    
    Negated(final CharMatcher a1) {
        super();
        this.original = Preconditions.checkNotNull(a1);
    }
    
    @Override
    public boolean matches(final char a1) {
        return !this.original.matches(a1);
    }
    
    @Override
    public boolean matchesAllOf(final CharSequence a1) {
        return this.original.matchesNoneOf(a1);
    }
    
    @Override
    public boolean matchesNoneOf(final CharSequence a1) {
        return this.original.matchesAllOf(a1);
    }
    
    @Override
    public int countIn(final CharSequence a1) {
        return a1.length() - this.original.countIn(a1);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        final BitSet v1 = new BitSet();
        this.original.setBits(v1);
        v1.flip(0, 65536);
        a1.or(v1);
    }
    
    @Override
    public CharMatcher negate() {
        return this.original;
    }
    
    @Override
    public String toString() {
        return this.original + ".negate()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
}
