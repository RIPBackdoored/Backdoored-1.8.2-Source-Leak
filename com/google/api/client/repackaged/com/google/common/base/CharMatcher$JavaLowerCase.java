package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaLowerCase extends CharMatcher
{
    static final JavaLowerCase INSTANCE;
    
    private JavaLowerCase() {
        super();
    }
    
    @Override
    public boolean matches(final char a1) {
        return Character.isLowerCase(a1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaLowerCase()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
    
    static {
        INSTANCE = new JavaLowerCase();
    }
}
