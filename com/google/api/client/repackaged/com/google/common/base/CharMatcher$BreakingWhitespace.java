package com.google.api.client.repackaged.com.google.common.base;

private static final class BreakingWhitespace extends CharMatcher
{
    static final CharMatcher INSTANCE;
    
    private BreakingWhitespace() {
        super();
    }
    
    @Override
    public boolean matches(final char a1) {
        switch (a1) {
            case '\t':
            case '\n':
            case '\u000b':
            case '\f':
            case '\r':
            case ' ':
            case '\u0085':
            case '\u1680':
            case '\u2028':
            case '\u2029':
            case '\u205f':
            case '\u3000': {
                return true;
            }
            case '\u2007': {
                return false;
            }
            default: {
                return a1 >= '\u2000' && a1 <= '\u200a';
            }
        }
    }
    
    @Override
    public String toString() {
        return "CharMatcher.breakingWhitespace()";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
    
    static {
        INSTANCE = new BreakingWhitespace();
    }
}
