package org.reflections;

import java.lang.annotation.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.lang.reflect.*;
import java.util.regex.*;
import com.google.common.collect.*;
import org.reflections.util.*;
import java.util.*;

public abstract class ReflectionUtils
{
    public static boolean includeObject;
    private static List<String> primitiveNames;
    private static List<Class> primitiveTypes;
    private static List<String> primitiveDescriptors;
    
    public ReflectionUtils() {
        super();
    }
    
    public static Set<Class<?>> getAllSuperTypes(final Class<?> a2, final Predicate<? super Class<?>>... v1) {
        final Set<Class<?>> v2 = (Set<Class<?>>)Sets.newLinkedHashSet();
        if (a2 != null && (ReflectionUtils.includeObject || !a2.equals(Object.class))) {
            v2.add(a2);
            for (final Class<?> a3 : getSuperTypes(a2)) {
                v2.addAll(getAllSuperTypes(a3, (Predicate<? super Class<?>>[])new Predicate[0]));
            }
        }
        return filter(v2, v1);
    }
    
    public static Set<Class<?>> getSuperTypes(final Class<?> a1) {
        final Set<Class<?>> v1 = new LinkedHashSet<Class<?>>();
        final Class<?> v2 = a1.getSuperclass();
        final Class<?>[] v3 = a1.getInterfaces();
        if (v2 != null && (ReflectionUtils.includeObject || !v2.equals(Object.class))) {
            v1.add(v2);
        }
        if (v3 != null && v3.length > 0) {
            v1.addAll(Arrays.asList(v3));
        }
        return v1;
    }
    
    public static Set<Method> getAllMethods(final Class<?> a2, final Predicate<? super Method>... v1) {
        final Set<Method> v2 = (Set<Method>)Sets.newHashSet();
        for (final Class<?> a3 : getAllSuperTypes(a2, (Predicate<? super Class<?>>[])new Predicate[0])) {
            v2.addAll(getMethods(a3, v1));
        }
        return v2;
    }
    
    public static Set<Method> getMethods(final Class<?> a1, final Predicate<? super Method>... a2) {
        return filter(a1.isInterface() ? a1.getMethods() : a1.getDeclaredMethods(), a2);
    }
    
    public static Set<Constructor> getAllConstructors(final Class<?> a2, final Predicate<? super Constructor>... v1) {
        final Set<Constructor> v2 = (Set<Constructor>)Sets.newHashSet();
        for (final Class<?> a3 : getAllSuperTypes(a2, (Predicate<? super Class<?>>[])new Predicate[0])) {
            v2.addAll(getConstructors(a3, v1));
        }
        return v2;
    }
    
    public static Set<Constructor> getConstructors(final Class<?> a1, final Predicate<? super Constructor>... a2) {
        return (Set<Constructor>)filter(a1.getDeclaredConstructors(), (Predicate<? super Constructor<?>>[])a2);
    }
    
    public static Set<Field> getAllFields(final Class<?> a2, final Predicate<? super Field>... v1) {
        final Set<Field> v2 = (Set<Field>)Sets.newHashSet();
        for (final Class<?> a3 : getAllSuperTypes(a2, (Predicate<? super Class<?>>[])new Predicate[0])) {
            v2.addAll(getFields(a3, v1));
        }
        return v2;
    }
    
    public static Set<Field> getFields(final Class<?> a1, final Predicate<? super Field>... a2) {
        return filter(a1.getDeclaredFields(), a2);
    }
    
    public static <T extends java.lang.Object> Set<Annotation> getAllAnnotations(final T a2, final Predicate<Annotation>... v1) {
        final Set<Annotation> v2 = (Set<Annotation>)Sets.newHashSet();
        if (a2 instanceof Class) {
            for (final Class<?> a3 : getAllSuperTypes((Class<?>)a2, (Predicate<? super Class<?>>[])new Predicate[0])) {
                v2.addAll(getAnnotations((AnnotatedElement)a3, (Predicate[])v1));
            }
        }
        else {
            v2.addAll(getAnnotations((AnnotatedElement)a2, (Predicate[])v1));
        }
        return v2;
    }
    
    public static <T extends java.lang.Object> Set<Annotation> getAnnotations(final T a1, final Predicate<Annotation>... a2) {
        return filter(((AnnotatedElement)a1).getDeclaredAnnotations(), (Predicate<? super Annotation>[])a2);
    }
    
    public static <T extends java.lang.Object> Set<T> getAll(final Set<T> a1, final Predicate<? super T>... a2) {
        return (Set<T>)(Utils.isEmpty(a2) ? a1 : Sets.newHashSet((Iterable<?>)Iterables.filter((Iterable<? extends E>)a1, Predicates.and((Predicate[])a2))));
    }
    
    public static <T extends java.lang.Object> Predicate<T> withName(final String a1) {
        return new Predicate<T>() {
            final /* synthetic */ String val$name;
            
            ReflectionUtils$1() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return a1 != null && ((Member)a1).getName().equals(a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withPrefix(final String a1) {
        return new Predicate<T>() {
            final /* synthetic */ String val$prefix;
            
            ReflectionUtils$2() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return a1 != null && ((Member)a1).getName().startsWith(a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withPattern(final String a1) {
        return new Predicate<T>() {
            final /* synthetic */ String val$regex;
            
            ReflectionUtils$3() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return Pattern.matches(a1, a1.toString());
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withAnnotation(final Class<? extends Annotation> a1) {
        return new Predicate<T>() {
            final /* synthetic */ Class val$annotation;
            
            ReflectionUtils$4() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return a1 != null && ((AnnotatedElement)a1).isAnnotationPresent(a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withAnnotations(final Class<? extends Annotation>... a1) {
        return new Predicate<T>() {
            final /* synthetic */ Class[] val$annotations;
            
            ReflectionUtils$5() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return a1 != null && Arrays.equals(a1, annotationTypes(((AnnotatedElement)a1).getAnnotations()));
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withAnnotation(final Annotation a1) {
        return new Predicate<T>() {
            final /* synthetic */ Annotation val$annotation;
            
            ReflectionUtils$6() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return a1 != null && ((AnnotatedElement)a1).isAnnotationPresent(a1.annotationType()) && areAnnotationMembersMatching(((AnnotatedElement)a1).getAnnotation((Class<Annotation>)a1.annotationType()), a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withAnnotations(final Annotation... a1) {
        return new Predicate<T>() {
            final /* synthetic */ Annotation[] val$annotations;
            
            ReflectionUtils$7() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T v0) {
                if (v0 != null) {
                    final Annotation[] v = ((AnnotatedElement)v0).getAnnotations();
                    if (v.length == a1.length) {
                        for (int a1 = 0; a1 < v.length; ++a1) {
                            if (!areAnnotationMembersMatching(v[a1], a1[a1])) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((AnnotatedElement)o);
            }
        };
    }
    
    public static Predicate<Member> withParameters(final Class<?>... a1) {
        return new Predicate<Member>() {
            final /* synthetic */ Class[] val$types;
            
            ReflectionUtils$8() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member a1) {
                return Arrays.equals(parameterTypes(a1), a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withParametersAssignableTo(final Class... a1) {
        return new Predicate<Member>() {
            final /* synthetic */ Class[] val$types;
            
            ReflectionUtils$9() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member v0) {
                if (v0 != null) {
                    final Class<?>[] v = (Class<?>[])parameterTypes(v0);
                    if (v.length == a1.length) {
                        for (int a1 = 0; a1 < v.length; ++a1) {
                            if (!v[a1].isAssignableFrom(a1[a1]) || (v[a1] == Object.class && a1[a1] != Object.class)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withParametersCount(final int a1) {
        return new Predicate<Member>() {
            final /* synthetic */ int val$count;
            
            ReflectionUtils$10() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member a1) {
                return a1 != null && parameterTypes(a1).length == a1;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withAnyParameterAnnotation(final Class<? extends Annotation> a1) {
        return new Predicate<Member>() {
            final /* synthetic */ Class val$annotationClass;
            
            ReflectionUtils$11() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member a1) {
                return a1 != null && Iterables.any((Iterable<Object>)annotationTypes(parameterAnnotations(a1)), (Predicate<? super Object>)new Predicate<Class<? extends Annotation>>() {
                    final /* synthetic */ ReflectionUtils$11 this$0;
                    
                    ReflectionUtils$11$1() {
                        this.this$0 = a1;
                        super();
                    }
                    
                    @Override
                    public boolean apply(@Nullable final Class<? extends Annotation> a1) {
                        return a1.equals(a1);
                    }
                    
                    @Override
                    public /* bridge */ boolean apply(@Nullable final Object o) {
                        return this.apply((Class<? extends Annotation>)o);
                    }
                });
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Member> withAnyParameterAnnotation(final Annotation a1) {
        return new Predicate<Member>() {
            final /* synthetic */ Annotation val$annotation;
            
            ReflectionUtils$12() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Member a1) {
                return a1 != null && Iterables.any((Iterable<Object>)parameterAnnotations(a1), (Predicate<? super Object>)new Predicate<Annotation>() {
                    final /* synthetic */ ReflectionUtils$12 this$0;
                    
                    ReflectionUtils$12$1() {
                        this.this$0 = a1;
                        super();
                    }
                    
                    @Override
                    public boolean apply(@Nullable final Annotation a1) {
                        return areAnnotationMembersMatching(a1, a1);
                    }
                    
                    @Override
                    public /* bridge */ boolean apply(@Nullable final Object o) {
                        return this.apply((Annotation)o);
                    }
                });
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static <T> Predicate<Field> withType(final Class<T> a1) {
        return new Predicate<Field>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$13() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Field a1) {
                return a1 != null && a1.getType().equals(a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Field)o);
            }
        };
    }
    
    public static <T> Predicate<Field> withTypeAssignableTo(final Class<T> a1) {
        return new Predicate<Field>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$14() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Field a1) {
                return a1 != null && a1.isAssignableFrom(a1.getType());
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Field)o);
            }
        };
    }
    
    public static <T> Predicate<Method> withReturnType(final Class<T> a1) {
        return new Predicate<Method>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$15() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Method a1) {
                return a1 != null && a1.getReturnType().equals(a1);
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Method)o);
            }
        };
    }
    
    public static <T> Predicate<Method> withReturnTypeAssignableTo(final Class<T> a1) {
        return new Predicate<Method>() {
            final /* synthetic */ Class val$type;
            
            ReflectionUtils$16() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Method a1) {
                return a1 != null && a1.isAssignableFrom(a1.getReturnType());
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Method)o);
            }
        };
    }
    
    public static <T extends java.lang.Object> Predicate<T> withModifier(final int a1) {
        return new Predicate<T>() {
            final /* synthetic */ int val$mod;
            
            ReflectionUtils$17() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final T a1) {
                return a1 != null && (((Member)a1).getModifiers() & a1) != 0x0;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Member)o);
            }
        };
    }
    
    public static Predicate<Class<?>> withClassModifier(final int a1) {
        return new Predicate<Class<?>>() {
            final /* synthetic */ int val$mod;
            
            ReflectionUtils$18() {
                super();
            }
            
            @Override
            public boolean apply(@Nullable final Class<?> a1) {
                return a1 != null && (a1.getModifiers() & a1) != 0x0;
            }
            
            @Override
            public /* bridge */ boolean apply(@Nullable final Object o) {
                return this.apply((Class<?>)o);
            }
        };
    }
    
    public static Class<?> forName(final String v-1, final ClassLoader... v0) {
        if (getPrimitiveNames().contains(v-1)) {
            return getPrimitiveTypes().get(getPrimitiveNames().indexOf(v-1));
        }
        String v;
        if (v-1.contains("[")) {
            final int a1 = v-1.indexOf("[");
            v = v-1.substring(0, a1);
            final String a2 = v-1.substring(a1).replace("]", "");
            if (getPrimitiveNames().contains(v)) {
                v = getPrimitiveDescriptors().get(getPrimitiveNames().indexOf(v));
            }
            else {
                v = "L" + v + ";";
            }
            v = a2 + v;
        }
        else {
            v = v-1;
        }
        final List<ReflectionsException> v2 = (List<ReflectionsException>)Lists.newArrayList();
        final ClassLoader[] classLoaders = ClasspathHelper.classLoaders(v0);
        final int length = classLoaders.length;
        int i = 0;
        while (i < length) {
            final ClassLoader v3 = classLoaders[i];
            if (v.contains("[")) {
                try {
                    return Class.forName(v, false, v3);
                }
                catch (Throwable v4) {
                    v2.add(new ReflectionsException("could not get type for name " + v-1, v4));
                }
            }
            try {
                return v3.loadClass(v);
            }
            catch (Throwable v4) {
                v2.add(new ReflectionsException("could not get type for name " + v-1, v4));
                ++i;
                continue;
            }
            break;
        }
        if (Reflections.log != null) {
            for (final ReflectionsException v5 : v2) {
                Reflections.log.warn("could not get type for name " + v-1 + " from any class loader", v5);
            }
        }
        return null;
    }
    
    public static <T> List<Class<? extends T>> forNames(final Iterable<String> v1, final ClassLoader... v2) {
        final List<Class<? extends T>> v3 = new ArrayList<Class<? extends T>>();
        for (final String a2 : v1) {
            final Class<?> a3 = forName(a2, v2);
            if (a3 != null) {
                v3.add((Class<? extends T>)a3);
            }
        }
        return v3;
    }
    
    private static Class[] parameterTypes(final Member a1) {
        return (Class[])((a1 != null) ? ((a1.getClass() == Method.class) ? ((Method)a1).getParameterTypes() : ((a1.getClass() == Constructor.class) ? ((Constructor)a1).getParameterTypes() : null)) : null);
    }
    
    private static Set<Annotation> parameterAnnotations(final Member v1) {
        final Set<Annotation> v2 = (Set<Annotation>)Sets.newHashSet();
        final Annotation[][] array;
        final Annotation[][] v3 = array = ((v1 instanceof Method) ? ((Method)v1).getParameterAnnotations() : ((v1 instanceof Constructor) ? ((Constructor)v1).getParameterAnnotations() : null));
        for (final Annotation[] a1 : array) {
            Collections.addAll(v2, a1);
        }
        return v2;
    }
    
    private static Set<Class<? extends Annotation>> annotationTypes(final Iterable<Annotation> v1) {
        final Set<Class<? extends Annotation>> v2 = (Set<Class<? extends Annotation>>)Sets.newHashSet();
        for (final Annotation a1 : v1) {
            v2.add(a1.annotationType());
        }
        return v2;
    }
    
    private static Class<? extends Annotation>[] annotationTypes(final Annotation[] v1) {
        final Class<? extends Annotation>[] v2 = (Class<? extends Annotation>[])new Class[v1.length];
        for (int a1 = 0; a1 < v1.length; ++a1) {
            v2[a1] = v1[a1].annotationType();
        }
        return v2;
    }
    
    private static void initPrimitives() {
        if (ReflectionUtils.primitiveNames == null) {
            ReflectionUtils.primitiveNames = Lists.newArrayList("boolean", "char", "byte", "short", "int", "long", "float", "double", "void");
            ReflectionUtils.primitiveTypes = (List<Class>)Lists.newArrayList(Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE);
            ReflectionUtils.primitiveDescriptors = Lists.newArrayList("Z", "C", "B", "S", "I", "J", "F", "D", "V");
        }
    }
    
    private static List<String> getPrimitiveNames() {
        initPrimitives();
        return ReflectionUtils.primitiveNames;
    }
    
    private static List<Class> getPrimitiveTypes() {
        initPrimitives();
        return ReflectionUtils.primitiveTypes;
    }
    
    private static List<String> getPrimitiveDescriptors() {
        initPrimitives();
        return ReflectionUtils.primitiveDescriptors;
    }
    
    static <T> Set<T> filter(final T[] a1, final Predicate<? super T>... a2) {
        return (Set<T>)(Utils.isEmpty(a2) ? Sets.newHashSet((Object[])a1) : Sets.newHashSet((Iterable<?>)Iterables.filter((Iterable<? extends E>)Arrays.asList(a1), Predicates.and((Predicate[])a2))));
    }
    
    static <T> Set<T> filter(final Iterable<T> a1, final Predicate<? super T>... a2) {
        return (Set<T>)(Utils.isEmpty(a2) ? Sets.newHashSet((Iterable<?>)a1) : Sets.newHashSet((Iterable<?>)Iterables.filter((Iterable<? extends E>)a1, Predicates.and((Predicate[])a2))));
    }
    
    private static boolean areAnnotationMembersMatching(final Annotation v1, final Annotation v2) {
        if (v2 != null && v1.annotationType() == v2.annotationType()) {
            for (final Method a2 : v1.annotationType().getDeclaredMethods()) {
                try {
                    if (!a2.invoke(v1, new Object[0]).equals(a2.invoke(v2, new Object[0]))) {
                        return false;
                    }
                }
                catch (Exception a3) {
                    throw new ReflectionsException(String.format("could not invoke method %s on annotation %s", a2.getName(), v1.annotationType()), a3);
                }
            }
            return true;
        }
        return false;
    }
    
    static /* bridge */ Class[] access$000(final Annotation[] a1) {
        return annotationTypes(a1);
    }
    
    static /* bridge */ boolean access$100(final Annotation a1, final Annotation a2) {
        return areAnnotationMembersMatching(a1, a2);
    }
    
    static /* bridge */ Class[] access$200(final Member a1) {
        return parameterTypes(a1);
    }
    
    static /* bridge */ Set access$300(final Member a1) {
        return parameterAnnotations(a1);
    }
    
    static /* bridge */ Set access$400(final Iterable a1) {
        return annotationTypes(a1);
    }
    
    static {
        ReflectionUtils.includeObject = false;
    }
}
