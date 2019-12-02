package com.fasterxml.jackson.core.type;

import java.lang.reflect.*;

public abstract class TypeReference<T> implements Comparable<TypeReference<T>>
{
    protected final Type _type;
    
    protected TypeReference() {
        super();
        final Type v1 = this.getClass().getGenericSuperclass();
        if (v1 instanceof Class) {
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        this._type = ((ParameterizedType)v1).getActualTypeArguments()[0];
    }
    
    public Type getType() {
        return this._type;
    }
    
    @Override
    public int compareTo(final TypeReference<T> a1) {
        return 0;
    }
    
    @Override
    public /* bridge */ int compareTo(final Object a1) {
        return this.compareTo((TypeReference)a1);
    }
}
