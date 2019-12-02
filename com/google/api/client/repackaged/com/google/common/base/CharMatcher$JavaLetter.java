package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaLetter extends CharMatcher
{
    static final JavaLetter INSTANCE;
    
    private JavaLetter() {
        super();
    }
    
    @Override
    public boolean matches(final char a1) {
        return Character.isLetter(a1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaLetter()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
    
    static {
        INSTANCE = new JavaLetter();
    }
}
