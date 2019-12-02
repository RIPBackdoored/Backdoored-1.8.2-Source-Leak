package com.google.api.client.repackaged.com.google.common.base;

import java.io.*;
import javax.annotation.*;

private static final class StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable
{
    private final Class<T> enumClass;
    private static final long serialVersionUID = 0L;
    
    StringConverter(final Class<T> a1) {
        super();
        this.enumClass = Preconditions.checkNotNull(a1);
    }
    
    @Override
    protected T doForward(final String a1) {
        return Enum.valueOf(this.enumClass, a1);
    }
    
    @Override
    protected String doBackward(final T a1) {
        return a1.name();
    }
    
    @Override
    public boolean equals(@Nullable final Object v2) {
        if (v2 instanceof StringConverter) {
            final StringConverter<?> a1 = (StringConverter<?>)v2;
            return this.enumClass.equals(a1.enumClass);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.enumClass.hashCode();
    }
    
    @Override
    public String toString() {
        return "Enums.stringConverter(" + this.enumClass.getName() + ".class)";
    }
    
    @Override
    protected /* bridge */ Object doBackward(final Object a1) {
        return this.doBackward((T)a1);
    }
    
    @Override
    protected /* bridge */ Object doForward(final Object a1) {
        return this.doForward((String)a1);
    }
}
