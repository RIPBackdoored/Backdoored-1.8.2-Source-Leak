package javassist.util.proxy;

import java.lang.reflect.*;
import java.io.*;

public class RuntimeSupport
{
    public static MethodHandler default_interceptor;
    
    public RuntimeSupport() {
        super();
    }
    
    public static void find2Methods(final Class a1, final String a2, final String a3, final int a4, final String a5, final Method[] a6) {
        a6[a4 + 1] = ((a3 == null) ? null : findMethod(a1, a3, a5));
        a6[a4] = findSuperClassMethod(a1, a2, a5);
    }
    
    @Deprecated
    public static void find2Methods(final Object a1, final String a2, final String a3, final int a4, final String a5, final Method[] a6) {
        a6[a4 + 1] = ((a3 == null) ? null : findMethod(a1, a3, a5));
        a6[a4] = findSuperMethod(a1, a2, a5);
    }
    
    @Deprecated
    public static Method findMethod(final Object a1, final String a2, final String a3) {
        final Method v1 = findMethod2(a1.getClass(), a2, a3);
        if (v1 == null) {
            error(a1.getClass(), a2, a3);
        }
        return v1;
    }
    
    public static Method findMethod(final Class a1, final String a2, final String a3) {
        final Method v1 = findMethod2(a1, a2, a3);
        if (v1 == null) {
            error(a1, a2, a3);
        }
        return v1;
    }
    
    public static Method findSuperMethod(final Object a1, final String a2, final String a3) {
        final Class v1 = a1.getClass();
        return findSuperClassMethod(v1, a2, a3);
    }
    
    public static Method findSuperClassMethod(final Class a1, final String a2, final String a3) {
        Method v1 = findSuperMethod2(a1.getSuperclass(), a2, a3);
        if (v1 == null) {
            v1 = searchInterfaces(a1, a2, a3);
        }
        if (v1 == null) {
            error(a1, a2, a3);
        }
        return v1;
    }
    
    private static void error(final Class a1, final String a2, final String a3) {
        throw new RuntimeException("not found " + a2 + ":" + a3 + " in " + a1.getName());
    }
    
    private static Method findSuperMethod2(final Class a1, final String a2, final String a3) {
        Method v1 = findMethod2(a1, a2, a3);
        if (v1 != null) {
            return v1;
        }
        final Class v2 = a1.getSuperclass();
        if (v2 != null) {
            v1 = findSuperMethod2(v2, a2, a3);
            if (v1 != null) {
                return v1;
            }
        }
        return searchInterfaces(a1, a2, a3);
    }
    
    private static Method searchInterfaces(final Class a2, final String a3, final String v1) {
        Method v2 = null;
        final Class[] v3 = a2.getInterfaces();
        for (int a4 = 0; a4 < v3.length; ++a4) {
            v2 = findSuperMethod2(v3[a4], a3, v1);
            if (v2 != null) {
                return v2;
            }
        }
        return v2;
    }
    
    private static Method findMethod2(final Class a2, final String a3, final String v1) {
        final Method[] v2 = SecurityActions.getDeclaredMethods(a2);
        for (int v3 = v2.length, a4 = 0; a4 < v3; ++a4) {
            if (v2[a4].getName().equals(a3) && makeDescriptor(v2[a4]).equals(v1)) {
                return v2[a4];
            }
        }
        return null;
    }
    
    public static String makeDescriptor(final Method a1) {
        final Class[] v1 = a1.getParameterTypes();
        return makeDescriptor(v1, a1.getReturnType());
    }
    
    public static String makeDescriptor(final Class[] a2, final Class v1) {
        final StringBuffer v2 = new StringBuffer();
        v2.append('(');
        for (int a3 = 0; a3 < a2.length; ++a3) {
            makeDesc(v2, a2[a3]);
        }
        v2.append(')');
        if (v1 != null) {
            makeDesc(v2, v1);
        }
        return v2.toString();
    }
    
    public static String makeDescriptor(final String a1, final Class a2) {
        final StringBuffer v1 = new StringBuffer(a1);
        makeDesc(v1, a2);
        return v1.toString();
    }
    
    private static void makeDesc(final StringBuffer a1, final Class a2) {
        if (a2.isArray()) {
            a1.append('[');
            makeDesc(a1, a2.getComponentType());
        }
        else if (a2.isPrimitive()) {
            if (a2 == Void.TYPE) {
                a1.append('V');
            }
            else if (a2 == Integer.TYPE) {
                a1.append('I');
            }
            else if (a2 == Byte.TYPE) {
                a1.append('B');
            }
            else if (a2 == Long.TYPE) {
                a1.append('J');
            }
            else if (a2 == Double.TYPE) {
                a1.append('D');
            }
            else if (a2 == Float.TYPE) {
                a1.append('F');
            }
            else if (a2 == Character.TYPE) {
                a1.append('C');
            }
            else if (a2 == Short.TYPE) {
                a1.append('S');
            }
            else {
                if (a2 != Boolean.TYPE) {
                    throw new RuntimeException("bad type: " + a2.getName());
                }
                a1.append('Z');
            }
        }
        else {
            a1.append('L').append(a2.getName().replace('.', '/')).append(';');
        }
    }
    
    public static SerializedProxy makeSerializedProxy(final Object a1) throws InvalidClassException {
        final Class v1 = a1.getClass();
        MethodHandler v2 = null;
        if (a1 instanceof ProxyObject) {
            v2 = ((ProxyObject)a1).getHandler();
        }
        else if (a1 instanceof Proxy) {
            v2 = ProxyFactory.getHandler((Proxy)a1);
        }
        return new SerializedProxy(v1, ProxyFactory.getFilterSignature(v1), v2);
    }
    
    static {
        RuntimeSupport.default_interceptor = new DefaultMethodHandler();
    }
    
    static class DefaultMethodHandler implements MethodHandler, Serializable
    {
        DefaultMethodHandler() {
            super();
        }
        
        @Override
        public Object invoke(final Object a1, final Method a2, final Method a3, final Object[] a4) throws Exception {
            return a3.invoke(a1, a4);
        }
    }
}
