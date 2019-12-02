package com.google.api.client.util;

import java.util.*;

static class ArrayValue
{
    final Class<?> componentType;
    final ArrayList<Object> values;
    
    ArrayValue(final Class<?> a1) {
        super();
        this.values = new ArrayList<Object>();
        this.componentType = a1;
    }
    
    Object toArray() {
        return Types.toArray(this.values, this.componentType);
    }
    
    void addValue(final Class<?> a1, final Object a2) {
        Preconditions.checkArgument(a1 == this.componentType);
        this.values.add(a2);
    }
}
