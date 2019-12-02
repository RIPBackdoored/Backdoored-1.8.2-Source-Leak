package com.google.api.client.util;

public final class Joiner
{
    private final com.google.api.client.repackaged.com.google.common.base.Joiner wrapped;
    
    public static Joiner on(final char a1) {
        return new Joiner(com.google.api.client.repackaged.com.google.common.base.Joiner.on(a1));
    }
    
    private Joiner(final com.google.api.client.repackaged.com.google.common.base.Joiner a1) {
        super();
        this.wrapped = a1;
    }
    
    public final String join(final Iterable<?> a1) {
        return this.wrapped.join(a1);
    }
}
