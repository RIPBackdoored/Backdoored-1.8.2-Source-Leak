package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;

private static final class IsEither extends FastMatcher
{
    private final char match1;
    private final char match2;
    
    IsEither(final char a1, final char a2) {
        super();
        this.match1 = a1;
        this.match2 = a2;
    }
    
    @Override
    public boolean matches(final char a1) {
        return a1 == this.match1 || a1 == this.match2;
    }
    
    @GwtIncompatible
    @Override
    void setBits(final BitSet a1) {
        a1.set(this.match1);
        a1.set(this.match2);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.anyOf(\"" + CharMatcher.access$100(this.match1) + CharMatcher.access$100(this.match2) + "\")";
    }
}
