package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;

@GwtCompatible
abstract class CommonPattern
{
    CommonPattern() {
        super();
    }
    
    abstract CommonMatcher matcher(final CharSequence p0);
    
    abstract String pattern();
    
    abstract int flags();
    
    @Override
    public abstract String toString();
    
    @Override
    public abstract int hashCode();
    
    @Override
    public abstract boolean equals(final Object p0);
}
