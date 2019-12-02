package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaUpperCase extends CharMatcher
{
    static final JavaUpperCase INSTANCE;
    
    private JavaUpperCase() {
        super();
    }
    
    @Override
    public boolean matches(final char a1) {
        return Character.isUpperCase(a1);
    }
    
    @Override
    public String toString() {
        return "CharMatcher.javaUpperCase()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
    
    static {
        INSTANCE = new JavaUpperCase();
    }
}
