package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaLetterOrDigit extends CharMatcher
{
    static final JavaLetterOrDigit INSTANCE;
    
    private JavaLetterOrDigit() {
        super();
    }
    
    @Override
    public boolean matches(final char a1) {
        return Character.isLetterOrDigit(a1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaLetterOrDigit()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
    
    static {
        INSTANCE = new JavaLetterOrDigit();
    }
}
