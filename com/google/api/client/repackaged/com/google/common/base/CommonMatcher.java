package com.google.api.client.repackaged.com.google.common.base;

import com.google.api.client.repackaged.com.google.common.annotations.*;

@GwtCompatible
abstract class CommonMatcher
{
    CommonMatcher() {
        super();
    }
    
    abstract boolean matches();
    
    abstract boolean find();
    
    abstract boolean find(final int p0);
    
    abstract String replaceAll(final String p0);
    
    abstract int end();
    
    abstract int start();
}
