package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class And extends CharMatcher
{
    final CharMatcher first;
    final CharMatcher second;
    
    And(final CharMatcher a1, final CharMatcher a2) {
        super();
        this.first = Preconditions.checkNotNull(a1);
        this.second = Preconditions.checkNotNull(a2);
    }
    
    @Override
    public boolean matches(final char a1) {
        return this.first.matches(a1) && this.second.matches(a1);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        final BitSet v1 = new BitSet();
        this.first.setBits(v1);
        final BitSet v2 = new BitSet();
        this.second.setBits(v2);
        v1.and(v2);
        a1.or(v1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.and(" + this.first + ", " + this.second + ")";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
}
