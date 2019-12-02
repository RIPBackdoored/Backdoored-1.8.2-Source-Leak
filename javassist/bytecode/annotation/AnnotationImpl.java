package javassist.bytecode.annotation;

import java.lang.reflect.*;
import javassist.*;
import javassist.bytecode.*;

public class AnnotationImpl implements InvocationHandler
{
    private static final String JDK_ANNOTATION_CLASS_NAME = "java.lang.annotation.Annotation";
    private static Method JDK_ANNOTATION_TYPE_METHOD;
    private Annotation annotation;
    private ClassPool pool;
    private ClassLoader classLoader;
    private transient Class annotationType;
    private transient int cachedHashCode;
    
    public static Object make(final ClassLoader a1, final Class a2, final ClassPool a3, final Annotation a4) {
        final AnnotationImpl v1 = new AnnotationImpl(a4, a3, a1);
        return Proxy.newProxyInstance(a1, new Class[] { a2 }, v1);
    }
    
    private AnnotationImpl(final Annotation a1, final ClassPool a2, final ClassLoader a3) {
        super();
        this.cachedHashCode = Integer.MIN_VALUE;
        this.annotation = a1;
        this.pool = a2;
        this.classLoader = a3;
    }
    
    public String getTypeName() {
        return this.annotation.getTypeName();
    }
    
    private Class getAnnotationType() {
        if (this.annotationType == null) {
            final String typeName = this.annotation.getTypeName();
            try {
                this.annotationType = this.classLoader.loadClass(typeName);
            }
            catch (ClassNotFoundException v2) {
                final NoClassDefFoundError v1 = new NoClassDefFoundError("Error loading annotation class: " + typeName);
                v1.setStackTrace(v2.getStackTrace());
                throw v1;
            }
        }
        return this.annotationType;
    }
    
    public Annotation getAnnotation() {
        return this.annotation;
    }
    
    @Override
    public Object invoke(final Object a3, final Method v1, final Object[] v2) throws Throwable {
        final String v3 = v1.getName();
        if (Object.class == v1.getDeclaringClass()) {
            if ("equals".equals(v3)) {
                final Object a4 = v2[0];
                return new Boolean(this.checkEquals(a4));
            }
            if ("toString".equals(v3)) {
                return this.annotation.toString();
            }
            if ("hashCode".equals(v3)) {
                return new Integer(this.hashCode());
            }
        }
        else if ("annotationType".equals(v3) && v1.getParameterTypes().length == 0) {
            return this.getAnnotationType();
        }
        final MemberValue v4 = this.annotation.getMemberValue(v3);
        if (v4 == null) {
            return this.getDefault(v3, v1);
        }
        return v4.getValue(this.classLoader, this.pool, v1);
    }
    
    private Object getDefault(final String v-2, final Method v-1) throws ClassNotFoundException, RuntimeException {
        final String v0 = this.annotation.getTypeName();
        if (this.pool != null) {
            try {
                final CtClass v2 = this.pool.get(v0);
                final ClassFile v3 = v2.getClassFile2();
                final MethodInfo v4 = v3.getMethod(v-2);
                if (v4 != null) {
                    final AnnotationDefaultAttribute a2 = (AnnotationDefaultAttribute)v4.getAttribute("AnnotationDefault");
                    if (a2 != null) {
                        final MemberValue a3 = a2.getDefaultValue();
                        return a3.getValue(this.classLoader, this.pool, v-1);
                    }
                }
            }
            catch (NotFoundException v5) {
                throw new RuntimeException("cannot find a class file: " + v0);
            }
        }
        throw new RuntimeException("no default value: " + v0 + "." + v-2 + "()");
    }
    
    @Override
    public int hashCode() {
        if (this.cachedHashCode == Integer.MIN_VALUE) {
            int cachedHashCode = 0;
            this.getAnnotationType();
            final Method[] declaredMethods = this.annotationType.getDeclaredMethods();
            for (int i = 0; i < declaredMethods.length; ++i) {
                final String name = declaredMethods[i].getName();
                int n = 0;
                final MemberValue memberValue = this.annotation.getMemberValue(name);
                Object v0 = null;
                try {
                    if (memberValue != null) {
                        v0 = memberValue.getValue(this.classLoader, this.pool, declaredMethods[i]);
                    }
                    if (v0 == null) {
                        v0 = this.getDefault(name, declaredMethods[i]);
                    }
                }
                catch (RuntimeException v2) {
                    throw v2;
                }
                catch (Exception v3) {
                    throw new RuntimeException("Error retrieving value " + name + " for annotation " + this.annotation.getTypeName(), v3);
                }
                if (v0 != null) {
                    if (v0.getClass().isArray()) {
                        n = arrayHashCode(v0);
                    }
                    else {
                        n = v0.hashCode();
                    }
                }
                cachedHashCode += (127 * name.hashCode() ^ n);
            }
            this.cachedHashCode = cachedHashCode;
        }
        return this.cachedHashCode;
    }
    
    private boolean checkEquals(final Object v0) throws Exception {
        if (v0 == null) {
            return false;
        }
        if (v0 instanceof Proxy) {
            final InvocationHandler v = Proxy.getInvocationHandler(v0);
            if (v instanceof AnnotationImpl) {
                final AnnotationImpl a1 = (AnnotationImpl)v;
                return this.annotation.equals(a1.annotation);
            }
        }
        final Class v2 = (Class)AnnotationImpl.JDK_ANNOTATION_TYPE_METHOD.invoke(v0, (Object[])null);
        if (!this.getAnnotationType().equals(v2)) {
            return false;
        }
        final Method[] v3 = this.annotationType.getDeclaredMethods();
        for (int v4 = 0; v4 < v3.length; ++v4) {
            final String v5 = v3[v4].getName();
            final MemberValue v6 = this.annotation.getMemberValue(v5);
            Object v7 = null;
            Object v8 = null;
            try {
                if (v6 != null) {
                    v7 = v6.getValue(this.classLoader, this.pool, v3[v4]);
                }
                if (v7 == null) {
                    v7 = this.getDefault(v5, v3[v4]);
                }
                v8 = v3[v4].invoke(v0, (Object[])null);
            }
            catch (RuntimeException v9) {
                throw v9;
            }
            catch (Exception v10) {
                throw new RuntimeException("Error retrieving value " + v5 + " for annotation " + this.annotation.getTypeName(), v10);
            }
            if (v7 == null && v8 != null) {
                return false;
            }
            if (v7 != null && !v7.equals(v8)) {
                return false;
            }
        }
        return true;
    }
    
    private static int arrayHashCode(final Object v-2) {
        if (v-2 == null) {
            return 0;
        }
        int n = 1;
        final Object[] v0 = (Object[])v-2;
        for (int v2 = 0; v2 < v0.length; ++v2) {
            int a1 = 0;
            if (v0[v2] != null) {
                a1 = v0[v2].hashCode();
            }
            n = 31 * n + a1;
        }
        return n;
    }
    
    static {
        AnnotationImpl.JDK_ANNOTATION_TYPE_METHOD = null;
        try {
            final Class v1 = Class.forName("java.lang.annotation.Annotation");
            AnnotationImpl.JDK_ANNOTATION_TYPE_METHOD = v1.getMethod("annotationType", (Class[])null);
        }
        catch (Exception ex) {}
    }
}
