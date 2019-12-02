package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaDigit extends CharMatcher
{
    static final JavaDigit INSTANCE;
    
    private JavaDigit() {
        super();
    }
    
    @Override
    public boolean matches(final char a1) {
        return Character.isDigit(a1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaDigit()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
    
    static {
        INSTANCE = new JavaDigit();
    }
}
