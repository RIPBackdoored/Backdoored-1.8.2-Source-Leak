package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public class FieldInfo
{
    private static final Map<Field, FieldInfo> CACHE;
    private final boolean isPrimitive;
    private final Field field;
    private final String name;
    
    public static FieldInfo of(final Enum<?> v0) {
        try {
            final FieldInfo a1 = of(v0.getClass().getField(v0.name()));
            Preconditions.checkArgument(a1 != null, "enum constant missing @Value or @NullValue annotation: %s", v0);
            return a1;
        }
        catch (NoSuchFieldException v) {
            throw new RuntimeException(v);
        }
    }
    
    public static FieldInfo of(final Field v-4) {
        if (v-4 == null) {
            return null;
        }
        synchronized (FieldInfo.CACHE) {
            FieldInfo fieldInfo = FieldInfo.CACHE.get(v-4);
            final boolean enumConstant = v-4.isEnumConstant();
            if (fieldInfo == null && (enumConstant || !Modifier.isStatic(v-4.getModifiers()))) {
                String v3 = null;
                if (enumConstant) {
                    final Value v0 = v-4.getAnnotation(Value.class);
                    if (v0 != null) {
                        final String a1 = v0.value();
                    }
                    else {
                        final NullValue v2 = v-4.getAnnotation(NullValue.class);
                        if (v2 == null) {
                            return null;
                        }
                        v3 = null;
                    }
                }
                else {
                    final Key v4 = v-4.getAnnotation(Key.class);
                    if (v4 == null) {
                        return null;
                    }
                    v3 = v4.value();
                    v-4.setAccessible(true);
                }
                if ("##default".equals(v3)) {
                    v3 = v-4.getName();
                }
                fieldInfo = new FieldInfo(v-4, v3);
                FieldInfo.CACHE.put(v-4, fieldInfo);
            }
            return fieldInfo;
        }
    }
    
    FieldInfo(final Field a1, final String a2) {
        super();
        this.field = a1;
        this.name = ((a2 == null) ? null : a2.intern());
        this.isPrimitive = Data.isPrimitive(this.getType());
    }
    
    public Field getField() {
        return this.field;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Class<?> getType() {
        return this.field.getType();
    }
    
    public Type getGenericType() {
        return this.field.getGenericType();
    }
    
    public boolean isFinal() {
        return Modifier.isFinal(this.field.getModifiers());
    }
    
    public boolean isPrimitive() {
        return this.isPrimitive;
    }
    
    public Object getValue(final Object a1) {
        return getFieldValue(this.field, a1);
    }
    
    public void setValue(final Object a1, final Object a2) {
        setFieldValue(this.field, a1, a2);
    }
    
    public ClassInfo getClassInfo() {
        return ClassInfo.of(this.field.getDeclaringClass());
    }
    
    public <T extends Enum<T>> T enumValue() {
        return Enum.valueOf(this.field.getDeclaringClass(), this.field.getName());
    }
    
    public static Object getFieldValue(final Field a2, final Object v1) {
        try {
            return a2.get(v1);
        }
        catch (IllegalAccessException a3) {
            throw new IllegalArgumentException(a3);
        }
    }
    
    public static void setFieldValue(final Field v1, final Object v2, final Object v3) {
        if (Modifier.isFinal(v1.getModifiers())) {
            final Object a1 = getFieldValue(v1, v2);
            if (v3 == null) {
                if (a1 == null) {
                    return;
                }
            }
            else if (v3.equals(a1)) {
                return;
            }
            throw new IllegalArgumentException("expected final value <" + a1 + "> but was <" + v3 + "> on " + v1.getName() + " field in " + v2.getClass().getName());
        }
        try {
            v1.set(v2, v3);
        }
        catch (SecurityException a2) {
            throw new IllegalArgumentException(a2);
        }
        catch (IllegalAccessException a3) {
            throw new IllegalArgumentException(a3);
        }
    }
    
    static {
        CACHE = new WeakHashMap<Field, FieldInfo>();
    }
}
