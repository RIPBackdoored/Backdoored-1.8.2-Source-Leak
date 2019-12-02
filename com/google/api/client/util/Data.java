package com.google.api.client.util;

import java.math.*;
import java.util.concurrent.*;
import java.util.*;
import java.lang.reflect.*;

public class Data
{
    public static final Boolean NULL_BOOLEAN;
    public static final String NULL_STRING;
    public static final Character NULL_CHARACTER;
    public static final Byte NULL_BYTE;
    public static final Short NULL_SHORT;
    public static final Integer NULL_INTEGER;
    public static final Float NULL_FLOAT;
    public static final Long NULL_LONG;
    public static final Double NULL_DOUBLE;
    public static final BigInteger NULL_BIG_INTEGER;
    public static final BigDecimal NULL_BIG_DECIMAL;
    public static final DateTime NULL_DATE_TIME;
    private static final ConcurrentHashMap<Class<?>, Object> NULL_CACHE;
    
    public Data() {
        super();
    }
    
    public static <T> T nullOf(final Class<?> v-3) {
        Object o = Data.NULL_CACHE.get(v-3);
        if (o == null) {
            synchronized (Data.NULL_CACHE) {
                o = Data.NULL_CACHE.get(v-3);
                if (o == null) {
                    if (v-3.isArray()) {
                        int a1 = 0;
                        Class<?> v1 = v-3;
                        do {
                            v1 = v1.getComponentType();
                            ++a1;
                        } while (v1.isArray());
                        o = Array.newInstance(v1, new int[a1]);
                    }
                    else if (v-3.isEnum()) {
                        final FieldInfo v2 = ClassInfo.of(v-3).getFieldInfo(null);
                        Preconditions.checkNotNull(v2, "enum missing constant with @NullValue annotation: %s", v-3);
                        final Enum v3 = (Enum)(o = v2.enumValue());
                    }
                    else {
                        o = Types.newInstance(v-3);
                    }
                    Data.NULL_CACHE.put(v-3, o);
                }
            }
        }
        final T t = (T)o;
        return t;
    }
    
    public static boolean isNull(final Object a1) {
        return a1 != null && a1 == Data.NULL_CACHE.get(a1.getClass());
    }
    
    public static Map<String, Object> mapOf(final Object v1) {
        if (v1 == null || isNull(v1)) {
            return Collections.emptyMap();
        }
        if (v1 instanceof Map) {
            final Map<String, Object> a1 = (Map<String, Object>)v1;
            return a1;
        }
        final Map<String, Object> v2 = new DataMap(v1, false);
        return v2;
    }
    
    public static <T> T clone(final T v-1) {
        if (v-1 == null || isPrimitive(v-1.getClass())) {
            return v-1;
        }
        if (v-1 instanceof GenericData) {
            return (T)((GenericData)v-1).clone();
        }
        final Class<?> v0 = v-1.getClass();
        T v2 = null;
        if (v0.isArray()) {
            final T a1 = (T)Array.newInstance(v0.getComponentType(), Array.getLength(v-1));
        }
        else if (v-1 instanceof ArrayMap) {
            v2 = (T)((ArrayMap)v-1).clone();
        }
        else {
            if ("java.util.Arrays$ArrayList".equals(v0.getName())) {
                final Object[] v3 = ((List)v-1).toArray();
                deepCopy(v3, v3);
                v2 = (T)Arrays.asList(v3);
                return v2;
            }
            v2 = Types.newInstance(v0);
        }
        deepCopy(v-1, v2);
        return v2;
    }
    
    public static void deepCopy(final Object v-3, final Object v-2) {
        final Class<?> class1 = v-3.getClass();
        Preconditions.checkArgument(class1 == v-2.getClass());
        if (class1.isArray()) {
            Preconditions.checkArgument(Array.getLength(v-3) == Array.getLength(v-2));
            int a2 = 0;
            for (final Object a3 : Types.iterableOf(v-3)) {
                Array.set(v-2, a2++, clone(a3));
            }
        }
        else if (Collection.class.isAssignableFrom(class1)) {
            final Collection<Object> v0 = (Collection<Object>)v-3;
            if (ArrayList.class.isAssignableFrom(class1)) {
                final ArrayList<Object> v2 = (ArrayList<Object>)v-2;
                v2.ensureCapacity(v0.size());
            }
            final Collection<Object> v3 = (Collection<Object>)v-2;
            for (final Object v4 : v0) {
                v3.add(clone(v4));
            }
        }
        else {
            final boolean v5 = GenericData.class.isAssignableFrom(class1);
            if (v5 || !Map.class.isAssignableFrom(class1)) {
                final ClassInfo v6 = v5 ? ((GenericData)v-3).classInfo : ClassInfo.of(class1);
                for (final String v7 : v6.names) {
                    final FieldInfo v8 = v6.getFieldInfo(v7);
                    if (!v8.isFinal() && (!v5 || !v8.isPrimitive())) {
                        final Object v9 = v8.getValue(v-3);
                        if (v9 == null) {
                            continue;
                        }
                        v8.setValue(v-2, clone(v9));
                    }
                }
            }
            else if (ArrayMap.class.isAssignableFrom(class1)) {
                final ArrayMap<Object, Object> v10 = (ArrayMap<Object, Object>)v-2;
                final ArrayMap<Object, Object> v11 = (ArrayMap<Object, Object>)v-3;
                for (int v12 = v11.size(), v13 = 0; v13 < v12; ++v13) {
                    final Object v9 = v11.getValue(v13);
                    v10.set(v13, clone(v9));
                }
            }
            else {
                final Map<String, Object> v14 = (Map<String, Object>)v-2;
                final Map<String, Object> v15 = (Map<String, Object>)v-3;
                for (final Map.Entry<String, Object> v16 : v15.entrySet()) {
                    v14.put(v16.getKey(), clone(v16.getValue()));
                }
            }
        }
    }
    
    public static boolean isPrimitive(Type a1) {
        if (a1 instanceof WildcardType) {
            a1 = Types.getBound((WildcardType)a1);
        }
        if (!(a1 instanceof Class)) {
            return false;
        }
        final Class<?> v1 = (Class<?>)a1;
        return v1.isPrimitive() || v1 == Character.class || v1 == String.class || v1 == Integer.class || v1 == Long.class || v1 == Short.class || v1 == Byte.class || v1 == Float.class || v1 == Double.class || v1 == BigInteger.class || v1 == BigDecimal.class || v1 == DateTime.class || v1 == Boolean.class;
    }
    
    public static boolean isValueOfPrimitiveType(final Object a1) {
        return a1 == null || isPrimitive(a1.getClass());
    }
    
    public static Object parsePrimitiveValue(final Type a2, final String v1) {
        final Class<?> v2 = (Class<?>)((a2 instanceof Class) ? ((Class)a2) : null);
        if (a2 == null || v2 != null) {
            if (v2 == Void.class) {
                return null;
            }
            if (v1 == null || v2 == null || v2.isAssignableFrom(String.class)) {
                return v1;
            }
            if (v2 == Character.class || v2 == Character.TYPE) {
                if (v1.length() != 1) {
                    throw new IllegalArgumentException("expected type Character/char but got " + v2);
                }
                return v1.charAt(0);
            }
            else {
                if (v2 == Boolean.class || v2 == Boolean.TYPE) {
                    return Boolean.valueOf(v1);
                }
                if (v2 == Byte.class || v2 == Byte.TYPE) {
                    return Byte.valueOf(v1);
                }
                if (v2 == Short.class || v2 == Short.TYPE) {
                    return Short.valueOf(v1);
                }
                if (v2 == Integer.class || v2 == Integer.TYPE) {
                    return Integer.valueOf(v1);
                }
                if (v2 == Long.class || v2 == Long.TYPE) {
                    return Long.valueOf(v1);
                }
                if (v2 == Float.class || v2 == Float.TYPE) {
                    return Float.valueOf(v1);
                }
                if (v2 == Double.class || v2 == Double.TYPE) {
                    return Double.valueOf(v1);
                }
                if (v2 == DateTime.class) {
                    return DateTime.parseRfc3339(v1);
                }
                if (v2 == BigInteger.class) {
                    return new BigInteger(v1);
                }
                if (v2 == BigDecimal.class) {
                    return new BigDecimal(v1);
                }
                if (v2.isEnum()) {
                    final Enum a3 = ClassInfo.of(v2).getFieldInfo(v1).enumValue();
                    return a3;
                }
            }
        }
        throw new IllegalArgumentException("expected primitive class, but got: " + a2);
    }
    
    public static Collection<Object> newCollectionInstance(Type a1) {
        if (a1 instanceof WildcardType) {
            a1 = Types.getBound((WildcardType)a1);
        }
        if (a1 instanceof ParameterizedType) {
            a1 = ((ParameterizedType)a1).getRawType();
        }
        final Class<?> v1 = (Class<?>)((a1 instanceof Class) ? ((Class)a1) : null);
        if (a1 == null || a1 instanceof GenericArrayType || (v1 != null && (v1.isArray() || v1.isAssignableFrom(ArrayList.class)))) {
            return new ArrayList<Object>();
        }
        if (v1 == null) {
            throw new IllegalArgumentException("unable to create new instance of type: " + a1);
        }
        if (v1.isAssignableFrom(HashSet.class)) {
            return new HashSet<Object>();
        }
        if (v1.isAssignableFrom(TreeSet.class)) {
            return new TreeSet<Object>();
        }
        final Collection<Object> v2 = Types.newInstance(v1);
        return v2;
    }
    
    public static Map<String, Object> newMapInstance(final Class<?> a1) {
        if (a1 == null || a1.isAssignableFrom(ArrayMap.class)) {
            return (Map<String, Object>)ArrayMap.create();
        }
        if (a1.isAssignableFrom(TreeMap.class)) {
            return new TreeMap<String, Object>();
        }
        final Map<String, Object> v1 = Types.newInstance(a1);
        return v1;
    }
    
    public static Type resolveWildcardTypeOrTypeVariable(final List<Type> a2, Type v1) {
        if (v1 instanceof WildcardType) {
            v1 = Types.getBound((WildcardType)v1);
        }
        while (v1 instanceof TypeVariable) {
            final Type a3 = Types.resolveTypeVariable(a2, (TypeVariable<?>)v1);
            if (a3 != null) {
                v1 = a3;
            }
            if (v1 instanceof TypeVariable) {
                v1 = ((TypeVariable)v1).getBounds()[0];
            }
        }
        return v1;
    }
    
    static {
        NULL_BOOLEAN = new Boolean(true);
        NULL_STRING = new String();
        NULL_CHARACTER = new Character('\0');
        NULL_BYTE = new Byte((byte)0);
        NULL_SHORT = new Short((short)0);
        NULL_INTEGER = new Integer(0);
        NULL_FLOAT = new Float(0.0f);
        NULL_LONG = new Long(0L);
        NULL_DOUBLE = new Double(0.0);
        NULL_BIG_INTEGER = new BigInteger("0");
        NULL_BIG_DECIMAL = new BigDecimal("0");
        NULL_DATE_TIME = new DateTime(0L);
        (NULL_CACHE = new ConcurrentHashMap<Class<?>, Object>()).put(Boolean.class, Data.NULL_BOOLEAN);
        Data.NULL_CACHE.put(String.class, Data.NULL_STRING);
        Data.NULL_CACHE.put(Character.class, Data.NULL_CHARACTER);
        Data.NULL_CACHE.put(Byte.class, Data.NULL_BYTE);
        Data.NULL_CACHE.put(Short.class, Data.NULL_SHORT);
        Data.NULL_CACHE.put(Integer.class, Data.NULL_INTEGER);
        Data.NULL_CACHE.put(Float.class, Data.NULL_FLOAT);
        Data.NULL_CACHE.put(Long.class, Data.NULL_LONG);
        Data.NULL_CACHE.put(Double.class, Data.NULL_DOUBLE);
        Data.NULL_CACHE.put(BigInteger.class, Data.NULL_BIG_INTEGER);
        Data.NULL_CACHE.put(BigDecimal.class, Data.NULL_BIG_DECIMAL);
        Data.NULL_CACHE.put(DateTime.class, Data.NULL_DATE_TIME);
    }
}
