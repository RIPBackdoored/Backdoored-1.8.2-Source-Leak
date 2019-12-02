package com.sun.jna;

import java.lang.ref.*;
import java.util.*;

public class NativeMappedConverter implements TypeConverter
{
    private static final Map<Class<?>, Reference<NativeMappedConverter>> converters;
    private final Class<?> type;
    private final Class<?> nativeType;
    private final NativeMapped instance;
    
    public static NativeMappedConverter getInstance(final Class<?> v-2) {
        synchronized (NativeMappedConverter.converters) {
            final Reference<NativeMappedConverter> a1 = NativeMappedConverter.converters.get(v-2);
            NativeMappedConverter v1 = (a1 != null) ? a1.get() : null;
            if (v1 == null) {
                v1 = new NativeMappedConverter(v-2);
                NativeMappedConverter.converters.put(v-2, new SoftReference<NativeMappedConverter>(v1));
            }
            return v1;
        }
    }
    
    public NativeMappedConverter(final Class<?> a1) {
        super();
        if (!NativeMapped.class.isAssignableFrom(a1)) {
            throw new IllegalArgumentException("Type must derive from " + NativeMapped.class);
        }
        this.type = a1;
        this.instance = this.defaultValue();
        this.nativeType = this.instance.nativeType();
    }
    
    public NativeMapped defaultValue() {
        try {
            return (NativeMapped)this.type.newInstance();
        }
        catch (InstantiationException v2) {
            final String v1 = "Can't create an instance of " + this.type + ", requires a no-arg constructor: " + v2;
            throw new IllegalArgumentException(v1);
        }
        catch (IllegalAccessException v3) {
            final String v1 = "Not allowed to create an instance of " + this.type + ", requires a public, no-arg constructor: " + v3;
            throw new IllegalArgumentException(v1);
        }
    }
    
    @Override
    public Object fromNative(final Object a1, final FromNativeContext a2) {
        return this.instance.fromNative(a1, a2);
    }
    
    @Override
    public Class<?> nativeType() {
        return this.nativeType;
    }
    
    @Override
    public Object toNative(Object a1, final ToNativeContext a2) {
        if (a1 == null) {
            if (Pointer.class.isAssignableFrom(this.nativeType)) {
                return null;
            }
            a1 = this.defaultValue();
        }
        return ((NativeMapped)a1).toNative();
    }
    
    static {
        converters = new WeakHashMap<Class<?>, Reference<NativeMappedConverter>>();
    }
}
