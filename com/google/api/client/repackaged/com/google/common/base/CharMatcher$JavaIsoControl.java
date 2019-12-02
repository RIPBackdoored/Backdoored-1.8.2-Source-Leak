package com.google.api.client.repackaged.com.google.common.base;

private static final class JavaIsoControl extends NamedFastMatcher
{
    static final JavaIsoControl INSTANCE;
    
    private JavaIsoControl() {
        super("CharMatcher.javaIsoControl()");
    }
    
    @Override
    public boolean matches(final char a1) {
        return a1 <= '\u001f' || (a1 >= '\u007f' && a1 <= '\u009f');
    }
    
    static {
        INSTANCE = new JavaIsoControl();
    }
}
