package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public final class ArrayValueMap
{
    private final Map<String, ArrayValue> keyMap;
    private final Map<Field, ArrayValue> fieldMap;
    private final Object destination;
    
    public ArrayValueMap(final Object a1) {
        super();
        this.keyMap = (Map<String, ArrayValue>)ArrayMap.create();
        this.fieldMap = (Map<Field, ArrayValue>)ArrayMap.create();
        this.destination = a1;
    }
    
    public void setValues() {
        for (final Map.Entry<String, ArrayValue> v0 : this.keyMap.entrySet()) {
            final Map<String, Object> v2 = (Map<String, Object>)this.destination;
            v2.put(v0.getKey(), v0.getValue().toArray());
        }
        for (final Map.Entry<Field, ArrayValue> v3 : this.fieldMap.entrySet()) {
            FieldInfo.setFieldValue(v3.getKey(), this.destination, v3.getValue().toArray());
        }
    }
    
    public void put(final Field a1, final Class<?> a2, final Object a3) {
        ArrayValue v1 = this.fieldMap.get(a1);
        if (v1 == null) {
            v1 = new ArrayValue(a2);
            this.fieldMap.put(a1, v1);
        }
        v1.addValue(a2, a3);
    }
    
    public void put(final String a1, final Class<?> a2, final Object a3) {
        ArrayValue v1 = this.keyMap.get(a1);
        if (v1 == null) {
            v1 = new ArrayValue(a2);
            this.keyMap.put(a1, v1);
        }
        v1.addValue(a2, a3);
    }
    
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
}
