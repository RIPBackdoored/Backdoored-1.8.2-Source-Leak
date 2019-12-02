package com.google.api.client.repackaged.com.google.common.base;

private static final class ForPredicate extends CharMatcher
{
    private final Predicate<? super Character> predicate;
    
    ForPredicate(final Predicate<? super Character> a1) {
        super();
        this.predicate = Preconditions.checkNotNull(a1);
    }
    
    @Override
    public boolean matches(final char a1) {
        return this.predicate.apply(a1);
    }
    
    @Override
    public boolean apply(final Character a1) {
        return this.predicate.apply(Preconditions.checkNotNull(a1));
    }
    
    @Override
    public String toString() {
        return "CharMatcher.forPredicate(" + this.predicate + ")";
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return this.apply((Character)a1);
    }
}
