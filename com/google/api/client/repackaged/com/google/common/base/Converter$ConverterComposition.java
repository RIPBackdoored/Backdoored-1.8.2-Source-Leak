package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import javax.annotation.*;

private static final class ConverterComposition<A, B, C> extends Converter<A, C> implements Serializable
{
    final Converter<A, B> first;
    final Converter<B, C> second;
    private static final long serialVersionUID = 0L;
    
    ConverterComposition(final Converter<A, B> a1, final Converter<B, C> a2) {
        super();
        this.first = a1;
        this.second = a2;
    }
    
    @Override
    protected C doForward(final A a1) {
        throw new AssertionError();
    }
    
    @Override
    protected A doBackward(final C a1) {
        throw new AssertionError();
    }
    
    @Nullable
    @Override
    C correctedDoForward(@Nullable final A a1) {
        return this.second.correctedDoForward(this.first.correctedDoForward(a1));
    }
    
    @Nullable
    @Override
    A correctedDoBackward(@Nullable final C a1) {
        return this.first.correctedDoBackward(this.second.correctedDoBackward(a1));
    }
    
    @Override
    public boolean equals(@Nullable final Object v2) {
        if (v2 instanceof ConverterComposition) {
            final ConverterComposition<?, ?, ?> a1 = (ConverterComposition<?, ?, ?>)v2;
            return this.first.equals(a1.first) && this.second.equals(a1.second);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return 31 * this.first.hashCode() + this.second.hashCode();
    }
    
    @Override
    public String toString() {
        return this.first + ".andThen(" + this.second + ")";
    }
}
