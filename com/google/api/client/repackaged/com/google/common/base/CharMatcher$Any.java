package com.google.api.client.repackaged.com.google.common.base;

import java.util.*;

private static final class Any extends NamedFastMatcher
{
    static final Any INSTANCE;
    
    private Any() {
        super("CharMatcher.any()");
    }
    
    @Override
    public boolean matches(final char a1) {
        return true;
    }
    
    @Override
    public int indexIn(final CharSequence a1) {
        return (a1.length() == 0) ? -1 : 0;
    }
    
    @Override
    public int indexIn(final CharSequence a1, final int a2) {
        final int v1 = a1.length();
        Preconditions.checkPositionIndex(a2, v1);
        return (a2 == v1) ? -1 : a2;
    }
    
    @Override
    public int lastIndexIn(final CharSequence a1) {
        return a1.length() - 1;
    }
    
    @Override
    public boolean matchesAllOf(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return true;
    }
    
    @Override
    public boolean matchesNoneOf(final CharSequence a1) {
        return a1.length() == 0;
    }
    
    @Override
    public String removeFrom(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return "";
    }
    
    @Override
    public String replaceFrom(final CharSequence a1, final char a2) {
        final char[] v1 = new char[a1.length()];
        Arrays.fill(v1, a2);
        return new String(v1);
    }
    
    @Override
    public String replaceFrom(final CharSequence v1, final CharSequence v2) {
        final StringBuilder v3 = new StringBuilder(v1.length() * v2.length());
        for (int a1 = 0; a1 < v1.length(); ++a1) {
            v3.append(v2);
        }
        return v3.toString();
    }
    
    @Override
    public String collapseFrom(final CharSequence a1, final char a2) {
        return (a1.length() == 0) ? "" : String.valueOf(a2);
    }
    
    @Override
    public String trimFrom(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return "";
    }
    
    @Override
    public int countIn(final CharSequence a1) {
        return a1.length();
    }
    
    @Override
    public CharMatcher and(final CharMatcher a1) {
        return Preconditions.checkNotNull(a1);
    }
    
    @Override
    public CharMatcher or(final CharMatcher a1) {
        Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.none();
    }
    
    static {
        INSTANCE = new Any();
    }
}
