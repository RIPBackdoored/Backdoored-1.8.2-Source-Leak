package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import javax.annotation.*;

private static final class FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable
{
    private final Function<? super A, ? extends B> forwardFunction;
    private final Function<? super B, ? extends A> backwardFunction;
    
    private FunctionBasedConverter(final Function<? super A, ? extends B> a1, final Function<? super B, ? extends A> a2) {
        super();
        this.forwardFunction = Preconditions.checkNotNull(a1);
        this.backwardFunction = Preconditions.checkNotNull(a2);
    }
    
    @Override
    protected B doForward(final A a1) {
        return (B)this.forwardFunction.apply((Object)a1);
    }
    
    @Override
    protected A doBackward(final B a1) {
        return (A)this.backwardFunction.apply((Object)a1);
    }
    
    @Override
    public boolean equals(@Nullable final Object v2) {
        if (v2 instanceof FunctionBasedConverter) {
            final FunctionBasedConverter<?, ?> a1 = (FunctionBasedConverter<?, ?>)v2;
            return this.forwardFunction.equals(a1.forwardFunction) && this.backwardFunction.equals(a1.backwardFunction);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.forwardFunction.hashCode() * 31 + this.backwardFunction.hashCode();
    }
    
    @Override
    public String toString() {
        return "Converter.from(" + this.forwardFunction + ", " + this.backwardFunction + ")";
    }
    
    FunctionBasedConverter(final Function a1, final Function a2, final Converter$1 a3) {
        this(a1, a2);
    }
}
