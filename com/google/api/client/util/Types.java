package com.google.api.client.util;

import java.lang.reflect.*;
import java.util.*;

public class Types
{
    public static ParameterizedType getSuperParameterizedType(Type v-6, final Class<?> v-5) {
        if (v-6 instanceof Class || v-6 instanceof ParameterizedType) {
        Label_0014:
            while (v-6 != null && v-6 != Object.class) {
                Class<?> rawClass = null;
                if (v-6 instanceof Class) {
                    final Class<?> a1 = (Class<?>)v-6;
                }
                else {
                    final ParameterizedType a3 = (ParameterizedType)v-6;
                    rawClass = getRawClass(a3);
                    if (rawClass == v-5) {
                        return a3;
                    }
                    if (v-5.isInterface()) {
                        for (final Type v1 : rawClass.getGenericInterfaces()) {
                            final Class<?> a2 = (Class<?>)((v1 instanceof Class) ? ((Class)v1) : getRawClass((ParameterizedType)v1));
                            if (v-5.isAssignableFrom(a2)) {
                                v-6 = v1;
                                continue Label_0014;
                            }
                        }
                    }
                }
                v-6 = rawClass.getGenericSuperclass();
            }
        }
        return null;
    }
    
    public static boolean isAssignableToOrFrom(final Class<?> a1, final Class<?> a2) {
        return a1.isAssignableFrom(a2) || a2.isAssignableFrom(a1);
    }
    
    public static <T> T newInstance(final Class<T> v0) {
        try {
            return v0.newInstance();
        }
        catch (IllegalAccessException a1) {
            throw handleExceptionForNewInstance(a1, v0);
        }
        catch (InstantiationException v) {
            throw handleExceptionForNewInstance(v, v0);
        }
    }
    
    private static IllegalArgumentException handleExceptionForNewInstance(final Exception v1, final Class<?> v2) {
        final StringBuilder v3 = new StringBuilder("unable to create new instance of class ").append(v2.getName());
        final ArrayList<String> v4 = new ArrayList<String>();
        if (v2.isArray()) {
            v4.add("because it is an array");
        }
        else if (v2.isPrimitive()) {
            v4.add("because it is primitive");
        }
        else if (v2 == Void.class) {
            v4.add("because it is void");
        }
        else {
            if (Modifier.isInterface(v2.getModifiers())) {
                v4.add("because it is an interface");
            }
            else if (Modifier.isAbstract(v2.getModifiers())) {
                v4.add("because it is abstract");
            }
            if (v2.getEnclosingClass() != null && !Modifier.isStatic(v2.getModifiers())) {
                v4.add("because it is not static");
            }
            if (!Modifier.isPublic(v2.getModifiers())) {
                v4.add("possibly because it is not public");
            }
            else {
                try {
                    v2.getConstructor((Class<?>[])new Class[0]);
                }
                catch (NoSuchMethodException a3) {
                    v4.add("because it has no accessible default constructor");
                }
            }
        }
        boolean v5 = false;
        for (final String a2 : v4) {
            if (v5) {
                v3.append(" and");
            }
            else {
                v5 = true;
            }
            v3.append(" ").append(a2);
        }
        return new IllegalArgumentException(v3.toString(), v1);
    }
    
    public static boolean isArray(final Type a1) {
        return a1 instanceof GenericArrayType || (a1 instanceof Class && ((Class)a1).isArray());
    }
    
    public static Type getArrayComponentType(final Type a1) {
        return (a1 instanceof GenericArrayType) ? ((GenericArrayType)a1).getGenericComponentType() : ((Class)a1).getComponentType();
    }
    
    public static Class<?> getRawClass(final ParameterizedType a1) {
        return (Class<?>)a1.getRawType();
    }
    
    public static Type getBound(final WildcardType a1) {
        final Type[] v1 = a1.getLowerBounds();
        if (v1.length != 0) {
            return v1[0];
        }
        return a1.getUpperBounds()[0];
    }
    
    public static Type resolveTypeVariable(final List<Type> v-5, final TypeVariable<?> v-4) {
        final GenericDeclaration genericDeclaration = (GenericDeclaration)v-4.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            Class<?> v-6;
            int size;
            ParameterizedType v0;
            for (v-6 = (Class<?>)genericDeclaration, size = v-5.size(), v0 = null; v0 == null && --size >= 0; v0 = getSuperParameterizedType(v-5.get(size), v-6)) {}
            if (v0 != null) {
                TypeVariable<?>[] v2;
                int v3;
                TypeVariable<?> a1;
                for (v2 = genericDeclaration.getTypeParameters(), v3 = 0; v3 < v2.length; ++v3) {
                    a1 = v2[v3];
                    if (a1.equals(v-4)) {
                        break;
                    }
                }
                final Type v4 = v0.getActualTypeArguments()[v3];
                if (v4 instanceof TypeVariable) {
                    final Type a2 = resolveTypeVariable(v-5, (TypeVariable<?>)v4);
                    if (a2 != null) {
                        return a2;
                    }
                }
                return v4;
            }
        }
        return null;
    }
    
    public static Class<?> getRawArrayComponentType(final List<Type> a2, Type v1) {
        if (v1 instanceof TypeVariable) {
            v1 = resolveTypeVariable(a2, (TypeVariable<?>)v1);
        }
        if (v1 instanceof GenericArrayType) {
            final Class<?> a3 = getRawArrayComponentType(a2, getArrayComponentType(v1));
            return Array.newInstance(a3, 0).getClass();
        }
        if (v1 instanceof Class) {
            return (Class<?>)v1;
        }
        if (v1 instanceof ParameterizedType) {
            return getRawClass((ParameterizedType)v1);
        }
        Preconditions.checkArgument(v1 == null, "wildcard type is not supported: %s", v1);
        return Object.class;
    }
    
    public static Type getIterableParameter(final Type a1) {
        return getActualParameterAtPosition(a1, Iterable.class, 0);
    }
    
    public static Type getMapValueParameter(final Type a1) {
        return getActualParameterAtPosition(a1, Map.class, 1);
    }
    
    private static Type getActualParameterAtPosition(final Type a2, final Class<?> a3, final int v1) {
        final ParameterizedType v2 = getSuperParameterizedType(a2, a3);
        if (v2 == null) {
            return null;
        }
        final Type v3 = v2.getActualTypeArguments()[v1];
        if (v3 instanceof TypeVariable) {
            final Type a4 = resolveTypeVariable(Arrays.asList(a2), (TypeVariable<?>)v3);
            if (a4 != null) {
                return a4;
            }
        }
        return v3;
    }
    
    public static <T> Iterable<T> iterableOf(final Object a1) {
        if (a1 instanceof Iterable) {
            return (Iterable<T>)a1;
        }
        final Class<?> v1 = a1.getClass();
        Preconditions.checkArgument(v1.isArray(), "not an array or Iterable: %s", v1);
        final Class<?> v2 = v1.getComponentType();
        if (!v2.isPrimitive()) {
            return (Iterable<T>)Arrays.asList((Object[])a1);
        }
        return new Iterable<T>() {
            final /* synthetic */ Object val$value;
            
            Types$1() {
                super();
            }
            
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    final int length = Array.getLength(a1);
                    int index = 0;
                    final /* synthetic */ Types$1 this$0;
                    
                    Types$1$1() {
                        this.this$0 = a1;
                        super();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.index < this.length;
                    }
                    
                    @Override
                    public T next() {
                        if (!this.hasNext()) {
                            throw new NoSuchElementException();
                        }
                        return (T)Array.get(a1, this.index++);
                    }
                    
                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }
    
    public static Object toArray(final Collection<?> v-2, final Class<?> v-1) {
        if (v-1.isPrimitive()) {
            final Object a2 = Array.newInstance(v-1, v-2.size());
            int v1 = 0;
            for (final Object a3 : v-2) {
                Array.set(a2, v1++, a3);
            }
            return a2;
        }
        return v-2.toArray((Object[])Array.newInstance(v-1, v-2.size()));
    }
    
    private Types() {
        super();
    }
}
