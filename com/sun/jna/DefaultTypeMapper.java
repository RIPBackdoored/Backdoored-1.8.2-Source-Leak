package com.sun.jna;

import java.util.*;

public class DefaultTypeMapper implements TypeMapper
{
    private List<Entry> toNativeConverters;
    private List<Entry> fromNativeConverters;
    
    public DefaultTypeMapper() {
        super();
        this.toNativeConverters = new ArrayList<Entry>();
        this.fromNativeConverters = new ArrayList<Entry>();
    }
    
    private Class<?> getAltClass(final Class<?> a1) {
        if (a1 == Boolean.class) {
            return Boolean.TYPE;
        }
        if (a1 == Boolean.TYPE) {
            return Boolean.class;
        }
        if (a1 == Byte.class) {
            return Byte.TYPE;
        }
        if (a1 == Byte.TYPE) {
            return Byte.class;
        }
        if (a1 == Character.class) {
            return Character.TYPE;
        }
        if (a1 == Character.TYPE) {
            return Character.class;
        }
        if (a1 == Short.class) {
            return Short.TYPE;
        }
        if (a1 == Short.TYPE) {
            return Short.class;
        }
        if (a1 == Integer.class) {
            return Integer.TYPE;
        }
        if (a1 == Integer.TYPE) {
            return Integer.class;
        }
        if (a1 == Long.class) {
            return Long.TYPE;
        }
        if (a1 == Long.TYPE) {
            return Long.class;
        }
        if (a1 == Float.class) {
            return Float.TYPE;
        }
        if (a1 == Float.TYPE) {
            return Float.class;
        }
        if (a1 == Double.class) {
            return Double.TYPE;
        }
        if (a1 == Double.TYPE) {
            return Double.class;
        }
        return null;
    }
    
    public void addToNativeConverter(final Class<?> a1, final ToNativeConverter a2) {
        this.toNativeConverters.add(new Entry(a1, a2));
        final Class<?> v1 = this.getAltClass(a1);
        if (v1 != null) {
            this.toNativeConverters.add(new Entry(v1, a2));
        }
    }
    
    public void addFromNativeConverter(final Class<?> a1, final FromNativeConverter a2) {
        this.fromNativeConverters.add(new Entry(a1, a2));
        final Class<?> v1 = this.getAltClass(a1);
        if (v1 != null) {
            this.fromNativeConverters.add(new Entry(v1, a2));
        }
    }
    
    public void addTypeConverter(final Class<?> a1, final TypeConverter a2) {
        this.addFromNativeConverter(a1, a2);
        this.addToNativeConverter(a1, a2);
    }
    
    private Object lookupConverter(final Class<?> v1, final Collection<? extends Entry> v2) {
        for (final Entry a1 : v2) {
            if (a1.type.isAssignableFrom(v1)) {
                return a1.converter;
            }
        }
        return null;
    }
    
    @Override
    public FromNativeConverter getFromNativeConverter(final Class<?> a1) {
        return (FromNativeConverter)this.lookupConverter(a1, this.fromNativeConverters);
    }
    
    @Override
    public ToNativeConverter getToNativeConverter(final Class<?> a1) {
        return (ToNativeConverter)this.lookupConverter(a1, this.toNativeConverters);
    }
    
    private static class Entry
    {
        public Class<?> type;
        public Object converter;
        
        public Entry(final Class<?> a1, final Object a2) {
            super();
            this.type = a1;
            this.converter = a2;
        }
    }
}
