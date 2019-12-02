package com.google.api.client.repackaged.com.google.common.base;

import java.lang.ref.*;
import com.google.api.client.repackaged.com.google.common.annotations.*;
import java.lang.reflect.*;
import java.util.*;
import java.io.*;
import javax.annotation.*;

@GwtCompatible(emulated = true)
public final class Enums
{
    @GwtIncompatible
    private static final Map<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>> enumConstantCache;
    
    private Enums() {
        super();
    }
    
    @GwtIncompatible
    public static Field getField(final Enum<?> v1) {
        final Class<?> v2 = v1.getDeclaringClass();
        try {
            return v2.getDeclaredField(v1.name());
        }
        catch (NoSuchFieldException a1) {
            throw new AssertionError((Object)a1);
        }
    }
    
    public static <T extends Enum<T>> Optional<T> getIfPresent(final Class<T> a1, final String a2) {
        Preconditions.checkNotNull(a1);
        Preconditions.checkNotNull(a2);
        return Platform.getEnumIfPresent(a1, a2);
    }
    
    @GwtIncompatible
    private static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> populateCache(final Class<T> v-1) {
        final Map<String, WeakReference<? extends Enum<?>>> v0 = new HashMap<String, WeakReference<? extends Enum<?>>>();
        for (final T a1 : EnumSet.allOf(v-1)) {
            v0.put(a1.name(), new WeakReference<Enum<?>>(a1));
        }
        Enums.enumConstantCache.put(v-1, v0);
        return v0;
    }
    
    @GwtIncompatible
    static <T extends Enum<T>> Map<String, WeakReference<? extends Enum<?>>> getEnumConstants(final Class<T> v1) {
        synchronized (Enums.enumConstantCache) {
            Map<String, WeakReference<? extends Enum<?>>> a1 = Enums.enumConstantCache.get(v1);
            if (a1 == null) {
                a1 = populateCache((Class<Enum>)v1);
            }
            return a1;
        }
    }
    
    public static <T extends Enum<T>> Converter<String, T> stringConverter(final Class<T> a1) {
        return (Converter<String, T>)new StringConverter((Class<Enum>)a1);
    }
    
    static {
        enumConstantCache = new WeakHashMap<Class<? extends Enum<?>>, Map<String, WeakReference<? extends Enum<?>>>>();
    }
    
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
}
