package com.google.api.client.repackaged.com.google.common.base;

static class NegatedFastMatcher extends Negated
{
    NegatedFastMatcher(final CharMatcher a1) {
        super(a1);
    }
    
    @Override
    public final CharMatcher precomputed() {
        return this;
    }
}
