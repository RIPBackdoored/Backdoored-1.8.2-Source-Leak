package com.google.api.client.repackaged.com.google.common.base;

abstract static class FastMatcher extends CharMatcher
{
    FastMatcher() {
        super();
    }
    
    @Override
    public final CharMatcher precomputed() {
        return this;
    }
    
    @Override
    public CharMatcher negate() {
        return new NegatedFastMatcher(this);
    }
    
    @Override
    public /* bridge */ boolean apply(final Object a1) {
        return super.apply((Character)a1);
    }
}
