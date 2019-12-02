package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.util.regex.*;

@GwtIncompatible
final class JdkPattern extends CommonPattern implements Serializable
{
    private final Pattern pattern;
    private static final long serialVersionUID = 0L;
    
    JdkPattern(final Pattern a1) {
        super();
        this.pattern = Preconditions.checkNotNull(a1);
    }
    
    @Override
    CommonMatcher matcher(final CharSequence a1) {
        return new JdkMatcher(this.pattern.matcher(a1));
    }
    
    @Override
    String pattern() {
        return this.pattern.pattern();
    }
    
    @Override
    int flags() {
        return this.pattern.flags();
    }
    
    @Override
    public String toString() {
        return this.pattern.toString();
    }
    
    @Override
    public int hashCode() {
        return this.pattern.hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof JdkPattern && this.pattern.equals(((JdkPattern)a1).pattern);
    }
    
    private static final class JdkMatcher extends CommonMatcher
    {
        final Matcher matcher;
        
        JdkMatcher(final Matcher a1) {
            super();
            this.matcher = Preconditions.checkNotNull(a1);
        }
        
        @Override
        boolean matches() {
            return this.matcher.matches();
        }
        
        @Override
        boolean find() {
            return this.matcher.find();
        }
        
        @Override
        boolean find(final int a1) {
            return this.matcher.find(a1);
        }
        
        @Override
        String replaceAll(final String a1) {
            return this.matcher.replaceAll(a1);
        }
        
        @Override
        int end() {
            return this.matcher.end();
        }
        
        @Override
        int start() {
            return this.matcher.start();
        }
    }
}
