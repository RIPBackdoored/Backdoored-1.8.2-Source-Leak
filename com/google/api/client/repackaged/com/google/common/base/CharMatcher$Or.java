package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class Or extends CharMatcher
{
    final CharMatcher first;
    final CharMatcher second;
    
    Or(final CharMatcher a1, final CharMatcher a2) {
        super();
        this.first = Preconditions.checkNotNull(a1);
        this.second = Preconditions.checkNotNull(a2);
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        this.first.setBits(a1);
        this.second.setBits(a1);
    }
    
    @Override
    public boolean matches(final char a1) {
        return this.first.matches(a1) || this.second.matches(a1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.or(" + this.first + ", " + this.second + ")";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
}
