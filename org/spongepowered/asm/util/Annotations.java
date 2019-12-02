package org.spongepowered.asm.util;

import java.lang.annotation.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.*;

public final class Annotations
{
    private Annotations() {
        super();
    }
    
    public static void setVisible(final FieldNode a1, final Class<? extends Annotation> a2, final Object... a3) {
        final AnnotationNode v1 = createNode(Type.getDescriptor(a2), a3);
        a1.visibleAnnotations = add(a1.visibleAnnotations, v1);
    }
    
    public static void setInvisible(final FieldNode a1, final Class<? extends Annotation> a2, final Object... a3) {
        final AnnotationNode v1 = createNode(Type.getDescriptor(a2), a3);
        a1.invisibleAnnotations = add(a1.invisibleAnnotations, v1);
    }
    
    public static void setVisible(final MethodNode a1, final Class<? extends Annotation> a2, final Object... a3) {
        final AnnotationNode v1 = createNode(Type.getDescriptor(a2), a3);
        a1.visibleAnnotations = add(a1.visibleAnnotations, v1);
    }
    
    public static void setInvisible(final MethodNode a1, final Class<? extends Annotation> a2, final Object... a3) {
        final AnnotationNode v1 = createNode(Type.getDescriptor(a2), a3);
        a1.invisibleAnnotations = add(a1.invisibleAnnotations, v1);
    }
    
    private static AnnotationNode createNode(final String a2, final Object... v1) {
        final AnnotationNode v2 = new AnnotationNode(a2);
        for (int a3 = 0; a3 < v1.length - 1; a3 += 2) {
            if (!(v1[a3] instanceof String)) {
                throw new IllegalArgumentException("Annotation keys must be strings, found " + v1[a3].getClass().getSimpleName() + " with " + v1[a3].toString() + " at index " + a3 + " creating " + a2);
            }
            v2.visit((String)v1[a3], v1[a3 + 1]);
        }
        return v2;
    }
    
    private static List<AnnotationNode> add(List<AnnotationNode> a1, final AnnotationNode a2) {
        if (a1 == null) {
            a1 = new ArrayList<AnnotationNode>(1);
        }
        else {
            a1.remove(get(a1, a2.desc));
        }
        a1.add(a2);
        return a1;
    }
    
    public static AnnotationNode getVisible(final FieldNode a1, final Class<? extends Annotation> a2) {
        return get(a1.visibleAnnotations, Type.getDescriptor(a2));
    }
    
    public static AnnotationNode getInvisible(final FieldNode a1, final Class<? extends Annotation> a2) {
        return get(a1.invisibleAnnotations, Type.getDescriptor(a2));
    }
    
    public static AnnotationNode getVisible(final MethodNode a1, final Class<? extends Annotation> a2) {
        return get(a1.visibleAnnotations, Type.getDescriptor(a2));
    }
    
    public static AnnotationNode getInvisible(final MethodNode a1, final Class<? extends Annotation> a2) {
        return get(a1.invisibleAnnotations, Type.getDescriptor(a2));
    }
    
    public static AnnotationNode getSingleVisible(final MethodNode a1, final Class<? extends Annotation>... a2) {
        return getSingle(a1.visibleAnnotations, a2);
    }
    
    public static AnnotationNode getSingleInvisible(final MethodNode a1, final Class<? extends Annotation>... a2) {
        return getSingle(a1.invisibleAnnotations, a2);
    }
    
    public static AnnotationNode getVisible(final ClassNode a1, final Class<? extends Annotation> a2) {
        return get(a1.visibleAnnotations, Type.getDescriptor(a2));
    }
    
    public static AnnotationNode getInvisible(final ClassNode a1, final Class<? extends Annotation> a2) {
        return get(a1.invisibleAnnotations, Type.getDescriptor(a2));
    }
    
    public static AnnotationNode getVisibleParameter(final MethodNode a1, final Class<? extends Annotation> a2, final int a3) {
        return getParameter(a1.visibleParameterAnnotations, Type.getDescriptor(a2), a3);
    }
    
    public static AnnotationNode getInvisibleParameter(final MethodNode a1, final Class<? extends Annotation> a2, final int a3) {
        return getParameter(a1.invisibleParameterAnnotations, Type.getDescriptor(a2), a3);
    }
    
    public static AnnotationNode getParameter(final List<AnnotationNode>[] a1, final String a2, final int a3) {
        if (a1 == null || a3 < 0 || a3 >= a1.length) {
            return null;
        }
        return get(a1[a3], a2);
    }
    
    public static AnnotationNode get(final List<AnnotationNode> a2, final String v1) {
        if (a2 == null) {
            return null;
        }
        for (final AnnotationNode a3 : a2) {
            if (v1.equals(a3.desc)) {
                return a3;
            }
        }
        return null;
    }
    
    private static AnnotationNode getSingle(final List<AnnotationNode> v1, final Class<? extends Annotation>[] v2) {
        final List<AnnotationNode> v3 = new ArrayList<AnnotationNode>();
        for (final Class<? extends Annotation> a2 : v2) {
            final AnnotationNode a3 = get(v1, Type.getDescriptor(a2));
            if (a3 != null) {
                v3.add(a3);
            }
        }
        final int v4 = v3.size();
        if (v4 > 1) {
            throw new IllegalArgumentException("Conflicting annotations found: " + Lists.transform(v3, (Function<? super AnnotationNode, ?>)new Function<AnnotationNode, String>() {
                Annotations$1() {
                    super();
                }
                
                @Override
                public String apply(final AnnotationNode a1) {
                    return a1.desc;
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((AnnotationNode)o);
                }
            }));
        }
        return (v4 == 0) ? null : v3.get(0);
    }
    
    public static <T> T getValue(final AnnotationNode a1) {
        return getValue(a1, "value");
    }
    
    public static <T> T getValue(final AnnotationNode a1, final String a2, final T a3) {
        final T v1 = getValue(a1, a2);
        return (v1 != null) ? v1 : a3;
    }
    
    public static <T> T getValue(final AnnotationNode a1, final String a2, final Class<?> a3) {
        Preconditions.checkNotNull(a3, "annotationClass cannot be null");
        T v1 = getValue(a1, a2);
        if (v1 == null) {
            try {
                v1 = (T)a3.getDeclaredMethod(a2, (Class<?>[])new Class[0]).getDefaultValue();
            }
            catch (NoSuchMethodException ex) {}
        }
        return v1;
    }
    
    public static <T> T getValue(final AnnotationNode a2, final String v1) {
        boolean v2 = false;
        if (a2 == null || a2.values == null) {
            return null;
        }
        for (final Object a3 : a2.values) {
            if (v2) {
                return (T)a3;
            }
            if (!a3.equals(v1)) {
                continue;
            }
            v2 = true;
        }
        return null;
    }
    
    public static <T extends Enum<T>> T getValue(final AnnotationNode a1, final String a2, final Class<T> a3, final T a4) {
        final String[] v1 = getValue(a1, a2);
        if (v1 == null) {
            return a4;
        }
        return toEnumValue(a3, v1);
    }
    
    public static <T> List<T> getValue(final AnnotationNode a2, final String a3, final boolean v1) {
        final Object v2 = getValue(a2, a3);
        if (v2 instanceof List) {
            return (List<T>)v2;
        }
        if (v2 != null) {
            final List<T> a4 = new ArrayList<T>();
            a4.add((T)v2);
            return a4;
        }
        return Collections.emptyList();
    }
    
    public static <T extends Enum<T>> List<T> getValue(final AnnotationNode a3, final String a4, final boolean v1, final Class<T> v2) {
        final Object v3 = getValue(a3, a4);
        if (v3 instanceof List) {
            final ListIterator<Object> a5 = ((List)v3).listIterator();
            while (a5.hasNext()) {
                a5.set(toEnumValue(v2, a5.next()));
            }
            return (List<T>)v3;
        }
        if (v3 instanceof String[]) {
            final List<T> a6 = new ArrayList<T>();
            a6.add(toEnumValue(v2, (String[])v3));
            return a6;
        }
        return Collections.emptyList();
    }
    
    private static <T extends Enum<T>> T toEnumValue(final Class<T> a1, final String[] a2) {
        if (!a1.getName().equals(Type.getType(a2[0]).getClassName())) {
            throw new IllegalArgumentException("The supplied enum class does not match the stored enum value");
        }
        return Enum.valueOf(a1, a2[1]);
    }
}
