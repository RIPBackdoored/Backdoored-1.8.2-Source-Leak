package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;

private static final class IdentityConverter<T> extends Converter<T, T> implements Serializable
{
    static final IdentityConverter INSTANCE;
    private static final long serialVersionUID = 0L;
    
    private IdentityConverter() {
        super();
    }
    
    @Override
    protected T doForward(final T a1) {
        return a1;
    }
    
    @Override
    protected T doBackward(final T a1) {
        return a1;
    }
    
    @Override
    public IdentityConverter<T> reverse() {
        return this;
    }
    
    @Override
     <S> Converter<T, S> doAndThen(final Converter<T, S> a1) {
        return Preconditions.checkNotNull(a1, (Object)"otherConverter");
    }
    
    @Override
    public String toString() {
        return "Converter.identity()";
    }
    
    private Object readResolve() {
        return IdentityConverter.INSTANCE;
    }
    
    @Override
    public /* bridge */ Converter reverse() {
        return this.reverse();
    }
    
    static {
        INSTANCE = new IdentityConverter();
    }
}
