package com.google.api.client.repackaged.com.google.common.base;

private static final class None extends NamedFastMatcher
{
    static final None INSTANCE;
    
    private None() {
        super("CharMatcher.none()");
    }
    
    @Override
    public boolean matches(final char a1) {
        return false;
    }
    
    @Override
    public int indexIn(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return -1;
    }
    
    @Override
    public int indexIn(final CharSequence a1, final int a2) {
        final int v1 = a1.length();
        Preconditions.checkPositionIndex(a2, v1);
        return -1;
    }
    
    @Override
    public int lastIndexIn(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return -1;
    }
    
    @Override
    public boolean matchesAllOf(final CharSequence a1) {
        return a1.length() == 0;
    }
    
    @Override
    public boolean matchesNoneOf(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return true;
    }
    
    @Override
    public String removeFrom(final CharSequence a1) {
        return a1.toString();
    }
    
    @Override
    public String replaceFrom(final CharSequence a1, final char a2) {
        return a1.toString();
    }
    
    @Override
    public String replaceFrom(final CharSequence a1, final CharSequence a2) {
        Preconditions.checkNotNull(a2);
        return a1.toString();
    }
    
    @Override
    public String collapseFrom(final CharSequence a1, final char a2) {
        return a1.toString();
    }
    
    @Override
    public String trimFrom(final CharSequence a1) {
        return a1.toString();
    }
    
    @Override
    public String trimLeadingFrom(final CharSequence a1) {
        return a1.toString();
    }
    
    @Override
    public String trimTrailingFrom(final CharSequence a1) {
        return a1.toString();
    }
    
    @Override
    public int countIn(final CharSequence a1) {
        Preconditions.checkNotNull(a1);
        return 0;
    }
    
    @Override
    public CharMatcher and(final CharMatcher a1) {
        Preconditions.checkNotNull(a1);
        return this;
    }
    
    @Override
    public CharMatcher or(final CharMatcher a1) {
        return Preconditions.checkNotNull(a1);
    }
    
    @Override
    public CharMatcher negate() {
        return CharMatcher.any();
    }
    
    static {
        INSTANCE = new None();
    }
}
