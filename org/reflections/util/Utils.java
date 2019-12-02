package org.reflections.util;

import com.google.common.collect.*;
import java.lang.reflect.*;
import org.reflections.*;
import java.io.*;
import org.slf4j.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

public abstract class Utils
{
    public Utils() {
        super();
    }
    
    public static String repeat(final String a2, final int v1) {
        final StringBuilder v2 = new StringBuilder();
        for (int a3 = 0; a3 < v1; ++a3) {
            v2.append(a2);
        }
        return v2.toString();
    }
    
    public static boolean isEmpty(final String a1) {
        return a1 == null || a1.length() == 0;
    }
    
    public static boolean isEmpty(final Object[] a1) {
        return a1 == null || a1.length == 0;
    }
    
    public static File prepareFile(final String a1) {
        final File v1 = new File(a1);
        final File v2 = v1.getAbsoluteFile().getParentFile();
        if (!v2.exists()) {
            v2.mkdirs();
        }
        return v1;
    }
    
    public static Member getMemberFromDescriptor(final String v-9, final ClassLoader... v-8) throws ReflectionsException {
        final int lastIndex = v-9.lastIndexOf(40);
        final String s = (lastIndex != -1) ? v-9.substring(0, lastIndex) : v-9;
        final String a4 = (lastIndex != -1) ? v-9.substring(lastIndex + 1, v-9.lastIndexOf(41)) : "";
        final int max = Math.max(s.lastIndexOf(46), s.lastIndexOf("$"));
        final String substring = s.substring(s.lastIndexOf(32) + 1, max);
        final String substring2 = s.substring(max + 1);
        Class<?>[] array = null;
        if (!isEmpty(a4)) {
            final String[] a2 = a4.split(",");
            final List<Class<?>> v1 = new ArrayList<Class<?>>(a2.length);
            for (final String a3 : a2) {
                v1.add(ReflectionUtils.forName(a3.trim(), v-8));
            }
            array = v1.toArray(new Class[v1.size()]);
        }
        Class<?> v2 = ReflectionUtils.forName(substring, v-8);
        while (v2 != null) {
            try {
                if (!v-9.contains("(")) {
                    return v2.isInterface() ? v2.getField(substring2) : v2.getDeclaredField(substring2);
                }
                if (isConstructor(v-9)) {
                    return v2.isInterface() ? v2.getConstructor(array) : v2.getDeclaredConstructor(array);
                }
                return v2.isInterface() ? v2.getMethod(substring2, array) : v2.getDeclaredMethod(substring2, array);
            }
            catch (Exception v3) {
                v2 = v2.getSuperclass();
                continue;
            }
            break;
        }
        throw new ReflectionsException("Can't resolve member named " + substring2 + " for class " + substring);
    }
    
    public static Set<Method> getMethodsFromDescriptors(final Iterable<String> v1, final ClassLoader... v2) {
        final Set<Method> v3 = (Set<Method>)Sets.newHashSet();
        for (final String a2 : v1) {
            if (!isConstructor(a2)) {
                final Method a3 = (Method)getMemberFromDescriptor(a2, v2);
                if (a3 == null) {
                    continue;
                }
                v3.add(a3);
            }
        }
        return v3;
    }
    
    public static Set<Constructor> getConstructorsFromDescriptors(final Iterable<String> v1, final ClassLoader... v2) {
        final Set<Constructor> v3 = (Set<Constructor>)Sets.newHashSet();
        for (final String a2 : v1) {
            if (isConstructor(a2)) {
                final Constructor a3 = (Constructor)getMemberFromDescriptor(a2, v2);
                if (a3 == null) {
                    continue;
                }
                v3.add(a3);
            }
        }
        return v3;
    }
    
    public static Set<Member> getMembersFromDescriptors(final Iterable<String> v1, final ClassLoader... v2) {
        final Set<Member> v3 = (Set<Member>)Sets.newHashSet();
        for (final String a2 : v1) {
            try {
                v3.add(getMemberFromDescriptor(a2, v2));
            }
            catch (ReflectionsException a3) {
                throw new ReflectionsException("Can't resolve member named " + a2, a3);
            }
        }
        return v3;
    }
    
    public static Field getFieldFromString(final String a2, final ClassLoader... v1) {
        final String v2 = a2.substring(0, a2.lastIndexOf(46));
        final String v3 = a2.substring(a2.lastIndexOf(46) + 1);
        try {
            return ReflectionUtils.forName(v2, v1).getDeclaredField(v3);
        }
        catch (NoSuchFieldException a3) {
            throw new ReflectionsException("Can't resolve field named " + v3, a3);
        }
    }
    
    public static void close(final InputStream v1) {
        try {
            if (v1 != null) {
                v1.close();
            }
        }
        catch (IOException a1) {
            if (Reflections.log != null) {
                Reflections.log.warn("Could not close InputStream", a1);
            }
        }
    }
    
    @Nullable
    public static Logger findLogger(final Class<?> v1) {
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            return LoggerFactory.getLogger(v1);
        }
        catch (Throwable a1) {
            return null;
        }
    }
    
    public static boolean isConstructor(final String a1) {
        return a1.contains("init>");
    }
    
    public static String name(Class v1) {
        if (!v1.isArray()) {
            return v1.getName();
        }
        int a1 = 0;
        while (v1.isArray()) {
            ++a1;
            v1 = v1.getComponentType();
        }
        return v1.getName() + repeat("[]", a1);
    }
    
    public static List<String> names(final Iterable<Class<?>> v1) {
        final List<String> v2 = new ArrayList<String>();
        for (final Class<?> a1 : v1) {
            v2.add(name(a1));
        }
        return v2;
    }
    
    public static List<String> names(final Class<?>... a1) {
        return names(Arrays.asList(a1));
    }
    
    public static String name(final Constructor a1) {
        return a1.getName() + ".<init>(" + Joiner.on(", ").join(names((Class<?>[])a1.getParameterTypes())) + ")";
    }
    
    public static String name(final Method a1) {
        return a1.getDeclaringClass().getName() + "." + a1.getName() + "(" + Joiner.on(", ").join(names(a1.getParameterTypes())) + ")";
    }
    
    public static String name(final Field a1) {
        return a1.getDeclaringClass().getName() + "." + a1.getName();
    }
}
