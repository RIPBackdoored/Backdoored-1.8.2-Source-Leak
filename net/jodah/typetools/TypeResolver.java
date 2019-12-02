package net.jodah.typetools;

import java.lang.ref.*;
import java.util.*;
import sun.misc.*;
import java.security.*;
import java.lang.reflect.*;

public final class TypeResolver
{
    private static final Map<Class<?>, Reference<Map<TypeVariable<?>, Type>>> TYPE_VARIABLE_CACHE;
    private static volatile boolean CACHE_ENABLED;
    private static boolean RESOLVES_LAMBDAS;
    private static Method GET_CONSTANT_POOL;
    private static Method GET_CONSTANT_POOL_SIZE;
    private static Method GET_CONSTANT_POOL_METHOD_AT;
    private static final Map<String, Method> OBJECT_METHODS;
    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPERS;
    private static final Double JAVA_VERSION;
    
    private TypeResolver() {
        super();
    }
    
    public static void enableCache() {
        TypeResolver.CACHE_ENABLED = true;
    }
    
    public static void disableCache() {
        TypeResolver.TYPE_VARIABLE_CACHE.clear();
        TypeResolver.CACHE_ENABLED = false;
    }
    
    public static <T, S extends T> Class<?> resolveRawArgument(final Class<T> a1, final Class<S> a2) {
        return resolveRawArgument(resolveGenericType(a1, a2), a2);
    }
    
    public static Class<?> resolveRawArgument(final Type a1, final Class<?> a2) {
        final Class<?>[] v1 = resolveRawArguments(a1, a2);
        if (v1 == null) {
            return Unknown.class;
        }
        if (v1.length != 1) {
            throw new IllegalArgumentException("Expected 1 argument for generic type " + a1 + " but found " + v1.length);
        }
        return v1[0];
    }
    
    public static <T, S extends T> Class<?>[] resolveRawArguments(final Class<T> a1, final Class<S> a2) {
        return resolveRawArguments(resolveGenericType(a1, a2), a2);
    }
    
    public static Class<?>[] resolveRawArguments(final Type v-3, final Class<?> v-2) {
        Class<?>[] array = null;
        Class<?> v0 = null;
        if (TypeResolver.RESOLVES_LAMBDAS && v-2.isSynthetic()) {
            final Class<?> a1 = (Class<?>)((v-3 instanceof ParameterizedType && ((ParameterizedType)v-3).getRawType() instanceof Class) ? ((ParameterizedType)v-3).getRawType() : ((v-3 instanceof Class) ? ((Class)v-3) : null));
            if (a1 != null && a1.isInterface()) {
                v0 = a1;
            }
        }
        if (v-3 instanceof ParameterizedType) {
            final ParameterizedType v2 = (ParameterizedType)v-3;
            final Type[] v3 = v2.getActualTypeArguments();
            array = (Class<?>[])new Class[v3.length];
            for (int a2 = 0; a2 < v3.length; ++a2) {
                array[a2] = resolveRawClass(v3[a2], v-2, v0);
            }
        }
        else if (v-3 instanceof TypeVariable) {
            array = (Class<?>[])new Class[] { resolveRawClass(v-3, v-2, v0) };
        }
        else if (v-3 instanceof Class) {
            final TypeVariable<?>[] v4 = (TypeVariable<?>[])((Class)v-3).getTypeParameters();
            array = (Class<?>[])new Class[v4.length];
            for (int v5 = 0; v5 < v4.length; ++v5) {
                array[v5] = resolveRawClass(v4[v5], v-2, v0);
            }
        }
        return array;
    }
    
    public static Type resolveGenericType(final Class<?> v-6, final Type v-5) {
        Class<?> clazz = null;
        if (v-5 instanceof ParameterizedType) {
            final Class<?> a1 = (Class<?>)((ParameterizedType)v-5).getRawType();
        }
        else {
            clazz = (Class<?>)v-5;
        }
        if (v-6.equals(clazz)) {
            return v-5;
        }
        if (v-6.isInterface()) {
            for (final Type v1 : clazz.getGenericInterfaces()) {
                final Type a2;
                if (v1 != null && !v1.equals(Object.class) && (a2 = resolveGenericType(v-6, v1)) != null) {
                    return a2;
                }
            }
        }
        final Type genericSuperclass = clazz.getGenericSuperclass();
        final Type resolveGenericType;
        if (genericSuperclass != null && !genericSuperclass.equals(Object.class) && (resolveGenericType = resolveGenericType(v-6, genericSuperclass)) != null) {
            return resolveGenericType;
        }
        return null;
    }
    
    public static Class<?> resolveRawClass(final Type a1, final Class<?> a2) {
        return resolveRawClass(a1, a2, null);
    }
    
    private static Class<?> resolveRawClass(Type v1, final Class<?> v2, final Class<?> v3) {
        if (v1 instanceof Class) {
            return (Class<?>)v1;
        }
        if (v1 instanceof ParameterizedType) {
            return resolveRawClass(((ParameterizedType)v1).getRawType(), v2, v3);
        }
        if (v1 instanceof GenericArrayType) {
            final GenericArrayType a1 = (GenericArrayType)v1;
            final Class<?> a2 = resolveRawClass(a1.getGenericComponentType(), v2, v3);
            return Array.newInstance(a2, 0).getClass();
        }
        if (v1 instanceof TypeVariable) {
            final TypeVariable<?> a3 = (TypeVariable<?>)v1;
            v1 = getTypeVariableMap(v2, v3).get(a3);
            v1 = ((v1 == null) ? resolveBound(a3) : resolveRawClass(v1, v2, v3));
        }
        return (Class<?>)((v1 instanceof Class) ? ((Class)v1) : Unknown.class);
    }
    
    private static Map<TypeVariable<?>, Type> getTypeVariableMap(final Class<?> v1, final Class<?> v2) {
        final Reference<Map<TypeVariable<?>, Type>> v3 = TypeResolver.TYPE_VARIABLE_CACHE.get(v1);
        Map<TypeVariable<?>, Type> v4 = (v3 != null) ? v3.get() : null;
        if (v4 == null) {
            v4 = new HashMap<TypeVariable<?>, Type>();
            if (v2 != null) {
                populateLambdaArgs(v2, v1, v4);
            }
            populateSuperTypeArgs(v1.getGenericInterfaces(), v4, v2 != null);
            Type a1 = v1.getGenericSuperclass();
            for (Class<?> a2 = v1.getSuperclass(); a2 != null && !Object.class.equals(a2); a2 = a2.getSuperclass()) {
                if (a1 instanceof ParameterizedType) {
                    populateTypeArgs((ParameterizedType)a1, v4, false);
                }
                populateSuperTypeArgs(a2.getGenericInterfaces(), v4, false);
                a1 = a2.getGenericSuperclass();
            }
            for (Class<?> a2 = v1; a2.isMemberClass(); a2 = a2.getEnclosingClass()) {
                a1 = a2.getGenericSuperclass();
                if (a1 instanceof ParameterizedType) {
                    populateTypeArgs((ParameterizedType)a1, v4, v2 != null);
                }
            }
            if (TypeResolver.CACHE_ENABLED) {
                TypeResolver.TYPE_VARIABLE_CACHE.put(v1, new WeakReference<Map<TypeVariable<?>, Type>>(v4));
            }
        }
        return v4;
    }
    
    private static void populateSuperTypeArgs(final Type[] v1, final Map<TypeVariable<?>, Type> v2, final boolean v3) {
        for (final Type a3 : v1) {
            if (a3 instanceof ParameterizedType) {
                final ParameterizedType a4 = (ParameterizedType)a3;
                if (!v3) {
                    populateTypeArgs(a4, v2, v3);
                }
                final Type a5 = a4.getRawType();
                if (a5 instanceof Class) {
                    populateSuperTypeArgs(((Class)a5).getGenericInterfaces(), v2, v3);
                }
                if (v3) {
                    populateTypeArgs(a4, v2, v3);
                }
            }
            else if (a3 instanceof Class) {
                populateSuperTypeArgs(((Class)a3).getGenericInterfaces(), v2, v3);
            }
        }
    }
    
    private static void populateTypeArgs(final ParameterizedType v-8, final Map<TypeVariable<?>, Type> v-7, final boolean v-6) {
        if (v-8.getRawType() instanceof Class) {
            final TypeVariable<?>[] typeParameters = (TypeVariable<?>[])((Class)v-8.getRawType()).getTypeParameters();
            final Type[] actualTypeArguments = v-8.getActualTypeArguments();
            if (v-8.getOwnerType() != null) {
                final Type a1 = v-8.getOwnerType();
                if (a1 instanceof ParameterizedType) {
                    populateTypeArgs((ParameterizedType)a1, v-7, v-6);
                }
            }
            for (int i = 0; i < actualTypeArguments.length; ++i) {
                final TypeVariable<?> typeVariable = typeParameters[i];
                final Type type = actualTypeArguments[i];
                if (type instanceof Class) {
                    v-7.put(typeVariable, type);
                }
                else if (type instanceof GenericArrayType) {
                    v-7.put(typeVariable, type);
                }
                else if (type instanceof ParameterizedType) {
                    v-7.put(typeVariable, type);
                }
                else if (type instanceof TypeVariable) {
                    final TypeVariable<?> a2 = (TypeVariable<?>)type;
                    if (v-6) {
                        final Type a3 = v-7.get(typeVariable);
                        if (a3 != null) {
                            v-7.put(a2, a3);
                            continue;
                        }
                    }
                    Type v1 = v-7.get(a2);
                    if (v1 == null) {
                        v1 = resolveBound(a2);
                    }
                    v-7.put(typeVariable, v1);
                }
            }
        }
    }
    
    public static Type resolveBound(final TypeVariable<?> a1) {
        final Type[] v1 = a1.getBounds();
        if (v1.length == 0) {
            return Unknown.class;
        }
        Type v2 = v1[0];
        if (v2 instanceof TypeVariable) {
            v2 = resolveBound((TypeVariable<?>)v2);
        }
        return (v2 == Object.class) ? Unknown.class : v2;
    }
    
    private static void populateLambdaArgs(final Class<?> v-6, final Class<?> v-5, final Map<TypeVariable<?>, Type> v-4) {
        if (TypeResolver.RESOLVES_LAMBDAS) {
            for (final Method v0 : v-6.getMethods()) {
                if (!isDefaultMethod(v0) && !Modifier.isStatic(v0.getModifiers()) && !v0.isBridge()) {
                    final Method v2 = TypeResolver.OBJECT_METHODS.get(v0.getName());
                    if (v2 == null || !Arrays.equals(v0.getTypeParameters(), v2.getTypeParameters())) {
                        final Type v3 = v0.getGenericReturnType();
                        final Type[] v4 = v0.getGenericParameterTypes();
                        final Member v5 = getMemberRef(v-5);
                        if (v5 == null) {
                            return;
                        }
                        if (v3 instanceof TypeVariable) {
                            Class<?> a1 = (v5 instanceof Method) ? ((Method)v5).getReturnType() : ((Constructor)v5).getDeclaringClass();
                            a1 = wrapPrimitives(a1);
                            if (!a1.equals(Void.class)) {
                                v-4.put((TypeVariable<?>)v3, a1);
                            }
                        }
                        final Class<?>[] v6 = (v5 instanceof Method) ? ((Method)v5).getParameterTypes() : ((Constructor)v5).getParameterTypes();
                        int v7 = 0;
                        if (v4.length > 0 && v4[0] instanceof TypeVariable && v4.length == v6.length + 1) {
                            final Class<?> a2 = v5.getDeclaringClass();
                            v-4.put((TypeVariable<?>)v4[0], a2);
                            v7 = 1;
                        }
                        int v8 = 0;
                        if (v4.length < v6.length) {
                            v8 = v6.length - v4.length;
                        }
                        for (int a3 = 0; a3 + v8 < v6.length; ++a3) {
                            if (v4[a3] instanceof TypeVariable) {
                                v-4.put((TypeVariable<?>)v4[a3 + v7], wrapPrimitives(v6[a3 + v8]));
                            }
                        }
                        return;
                    }
                }
            }
        }
    }
    
    private static boolean isDefaultMethod(final Method a1) {
        return TypeResolver.JAVA_VERSION >= 1.8 && a1.isDefault();
    }
    
    private static Member getMemberRef(final Class<?> v-1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0         /* v-1 */
        //     4: iconst_0       
        //     5: anewarray       Ljava/lang/Object;
        //     8: invokevirtual   java/lang/reflect/Method.invoke:(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
        //    11: astore_1        /* a1 */
        //    12: goto            18
        //    15: astore_2        /* v1 */
        //    16: aconst_null    
        //    17: areturn        
        //    18: aconst_null    
        //    19: astore_2        /* v1 */
        //    20: aload_1         /* v0 */
        //    21: invokestatic    net/jodah/typetools/TypeResolver.getConstantPoolSize:(Ljava/lang/Object;)I
        //    24: iconst_1       
        //    25: isub           
        //    26: istore_3        /* v2 */
        //    27: iload_3         /* v2 */
        //    28: iflt            118
        //    31: aload_1         /* v0 */
        //    32: iload_3         /* v2 */
        //    33: invokestatic    net/jodah/typetools/TypeResolver.getConstantPoolMethodAt:(Ljava/lang/Object;I)Ljava/lang/reflect/Member;
        //    36: astore          v3
        //    38: aload           v3
        //    40: ifnull          112
        //    43: aload           v3
        //    45: instanceof      Ljava/lang/reflect/Constructor;
        //    48: ifeq            70
        //    51: aload           v3
        //    53: invokeinterface java/lang/reflect/Member.getDeclaringClass:()Ljava/lang/Class;
        //    58: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //    61: ldc_w           "java.lang.invoke.SerializedLambda"
        //    64: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    67: ifne            112
        //    70: aload           v3
        //    72: invokeinterface java/lang/reflect/Member.getDeclaringClass:()Ljava/lang/Class;
        //    77: aload_0         /* v-1 */
        //    78: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //    81: ifeq            87
        //    84: goto            112
        //    87: aload           v3
        //    89: astore_2        /* v1 */
        //    90: aload           v3
        //    92: instanceof      Ljava/lang/reflect/Method;
        //    95: ifeq            118
        //    98: aload           v3
        //   100: checkcast       Ljava/lang/reflect/Method;
        //   103: invokestatic    net/jodah/typetools/TypeResolver.isAutoBoxingMethod:(Ljava/lang/reflect/Method;)Z
        //   106: ifne            112
        //   109: goto            118
        //   112: iinc            v2, -1
        //   115: goto            27
        //   118: aload_2         /* v1 */
        //   119: areturn        
        //    Signature:
        //  (Ljava/lang/Class<*>;)Ljava/lang/reflect/Member;
        //    StackMapTable: 00 07 4F 07 01 4D FC 00 02 07 00 04 FD 00 08 07 01 25 01 FC 00 2A 07 01 25 10 FA 00 18 FA 00 05
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  0      12     15     18     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static boolean isAutoBoxingMethod(final Method a1) {
        final Class<?>[] v1 = a1.getParameterTypes();
        return a1.getName().equals("valueOf") && v1.length == 1 && v1[0].isPrimitive() && wrapPrimitives(v1[0]).equals(a1.getDeclaringClass());
    }
    
    private static Class<?> wrapPrimitives(final Class<?> a1) {
        return a1.isPrimitive() ? TypeResolver.PRIMITIVE_WRAPPERS.get(a1) : a1;
    }
    
    private static int getConstantPoolSize(final Object v1) {
        try {
            return (int)TypeResolver.GET_CONSTANT_POOL_SIZE.invoke(v1, new Object[0]);
        }
        catch (Exception a1) {
            return 0;
        }
    }
    
    private static Member getConstantPoolMethodAt(final Object a2, final int v1) {
        try {
            return (Member)TypeResolver.GET_CONSTANT_POOL_METHOD_AT.invoke(a2, v1);
        }
        catch (Exception a3) {
            return null;
        }
    }
    
    static {
        TYPE_VARIABLE_CACHE = Collections.synchronizedMap(new WeakHashMap<Class<?>, Reference<Map<TypeVariable<?>, Type>>>());
        TypeResolver.CACHE_ENABLED = true;
        OBJECT_METHODS = new HashMap<String, Method>();
        JAVA_VERSION = Double.parseDouble(System.getProperty("java.specification.version", "0"));
        try {
            final Unsafe unsafe = AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>)new PrivilegedExceptionAction<Unsafe>() {
                TypeResolver$1() {
                    super();
                }
                
                @Override
                public Unsafe run() throws Exception {
                    final Field v1 = Unsafe.class.getDeclaredField("theUnsafe");
                    v1.setAccessible(true);
                    return (Unsafe)v1.get(null);
                }
                
                @Override
                public /* bridge */ Object run() throws Exception {
                    return this.run();
                }
            });
            TypeResolver.GET_CONSTANT_POOL = Class.class.getDeclaredMethod("getConstantPool", (Class<?>[])new Class[0]);
            final String s = (TypeResolver.JAVA_VERSION < 9.0) ? "sun.reflect.ConstantPool" : "jdk.internal.reflect.ConstantPool";
            final Class<?> forName = Class.forName(s);
            TypeResolver.GET_CONSTANT_POOL_SIZE = forName.getDeclaredMethod("getSize", (Class<?>[])new Class[0]);
            TypeResolver.GET_CONSTANT_POOL_METHOD_AT = forName.getDeclaredMethod("getMethodAt", Integer.TYPE);
            final Field declaredField = AccessibleObject.class.getDeclaredField("override");
            final long objectFieldOffset = unsafe.objectFieldOffset(declaredField);
            unsafe.putBoolean(TypeResolver.GET_CONSTANT_POOL, objectFieldOffset, true);
            unsafe.putBoolean(TypeResolver.GET_CONSTANT_POOL_SIZE, objectFieldOffset, true);
            unsafe.putBoolean(TypeResolver.GET_CONSTANT_POOL_METHOD_AT, objectFieldOffset, true);
            final Object invoke = TypeResolver.GET_CONSTANT_POOL.invoke(Object.class, new Object[0]);
            TypeResolver.GET_CONSTANT_POOL_SIZE.invoke(invoke, new Object[0]);
            for (final Method v1 : Object.class.getDeclaredMethods()) {
                TypeResolver.OBJECT_METHODS.put(v1.getName(), v1);
            }
            TypeResolver.RESOLVES_LAMBDAS = true;
        }
        catch (Exception ex) {}
        final Map<Class<?>, Class<?>> map = new HashMap<Class<?>, Class<?>>();
        map.put(Boolean.TYPE, Boolean.class);
        map.put(Byte.TYPE, Byte.class);
        map.put(Character.TYPE, Character.class);
        map.put(Double.TYPE, Double.class);
        map.put(Float.TYPE, Float.class);
        map.put(Integer.TYPE, Integer.class);
        map.put(Long.TYPE, Long.class);
        map.put(Short.TYPE, Short.class);
        map.put(Void.TYPE, Void.class);
        PRIMITIVE_WRAPPERS = Collections.unmodifiableMap((Map<? extends Class<?>, ? extends Class<?>>)map);
    }
    
    public static final class Unknown
    {
        private Unknown() {
            super();
        }
    }
}
