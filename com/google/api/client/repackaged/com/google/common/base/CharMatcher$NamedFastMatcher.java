package com.google.api.client.repackaged.com.google.common.base;

abstract static class NamedFastMatcher extends FastMatcher
{
    private final String description;
    
    NamedFastMatcher(final String a1) {
        super();
        this.description = Preconditions.checkNotNull(a1);
    }
    
    @Override
    public final String toString() {
        return this.description;
    }
}
