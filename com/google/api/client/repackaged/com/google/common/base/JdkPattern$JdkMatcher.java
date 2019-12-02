package com.google.api.client.repackaged.com.google.common.base;

import java.util.regex.*;

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
