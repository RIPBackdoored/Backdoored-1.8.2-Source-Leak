package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import javax.annotation.*;

private static final class ReverseConverter<A, B> extends Converter<B, A> implements Serializable
{
    final Converter<A, B> original;
    private static final long serialVersionUID = 0L;
    
    ReverseConverter(final Converter<A, B> a1) {
        super();
        this.original = a1;
    }
    
    @Override
    protected A doForward(final B a1) {
        throw new AssertionError();
    }
    
    @Override
    protected B doBackward(final A a1) {
        throw new AssertionError();
    }
    
    @Nullable
    @Override
    A correctedDoForward(@Nullable final B a1) {
        return this.original.correctedDoBackward(a1);
    }
    
    @Nullable
    @Override
    B correctedDoBackward(@Nullable final A a1) {
        return this.original.correctedDoForward(a1);
    }
    
    @Override
    public Converter<A, B> reverse() {
        return this.original;
    }
    
    @Override
    public boolean equals(@Nullable final Object v2) {
        if (v2 instanceof ReverseConverter) {
            final ReverseConverter<?, ?> a1 = (ReverseConverter<?, ?>)v2;
            return this.original.equals(a1.original);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return ~this.original.hashCode();
    }
    
    @Override
    public String toString() {
        return this.original + ".reverse()";
    }
}
