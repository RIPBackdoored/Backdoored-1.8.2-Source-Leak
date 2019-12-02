package javassist.tools.reflect;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

public class ClassMetaobject implements Serializable
{
    static final String methodPrefix = "_m_";
    static final int methodPrefixLen = 3;
    private Class javaClass;
    private Constructor[] constructors;
    private Method[] methods;
    public static boolean useContextClassLoader;
    
    public ClassMetaobject(final String[] v2) {
        super();
        try {
            this.javaClass = this.getClassObject(v2[0]);
        }
        catch (ClassNotFoundException a1) {
            throw new RuntimeException("not found: " + v2[0] + ", useContextClassLoader: " + Boolean.toString(ClassMetaobject.useContextClassLoader), a1);
        }
        this.constructors = this.javaClass.getConstructors();
        this.methods = null;
    }
    
    private void writeObject(final ObjectOutputStream a1) throws IOException {
        a1.writeUTF(this.javaClass.getName());
    }
    
    private void readObject(final ObjectInputStream a1) throws IOException, ClassNotFoundException {
        this.javaClass = this.getClassObject(a1.readUTF());
        this.constructors = this.javaClass.getConstructors();
        this.methods = null;
    }
    
    private Class getClassObject(final String a1) throws ClassNotFoundException {
        if (ClassMetaobject.useContextClassLoader) {
            return Thread.currentThread().getContextClassLoader().loadClass(a1);
        }
        return Class.forName(a1);
    }
    
    public final Class getJavaClass() {
        return this.javaClass;
    }
    
    public final String getName() {
        return this.javaClass.getName();
    }
    
    public final boolean isInstance(final Object a1) {
        return this.javaClass.isInstance(a1);
    }
    
    public final Object newInstance(final Object[] v-2) throws CannotCreateException {
        for (int length = this.constructors.length, v0 = 0; v0 < length; ++v0) {
            try {
                return this.constructors[v0].newInstance(v-2);
            }
            catch (IllegalArgumentException ex) {}
            catch (InstantiationException a1) {
                throw new CannotCreateException(a1);
            }
            catch (IllegalAccessException v2) {
                throw new CannotCreateException(v2);
            }
            catch (InvocationTargetException v3) {
                throw new CannotCreateException(v3);
            }
        }
        throw new CannotCreateException("no constructor matches");
    }
    
    public Object trapFieldRead(final String v-1) {
        final Class v0 = this.getJavaClass();
        try {
            return v0.getField(v-1).get(null);
        }
        catch (NoSuchFieldException a1) {
            throw new RuntimeException(a1.toString());
        }
        catch (IllegalAccessException v2) {
            throw new RuntimeException(v2.toString());
        }
    }
    
    public void trapFieldWrite(final String v2, final Object v3) {
        final Class v4 = this.getJavaClass();
        try {
            v4.getField(v2).set(null, v3);
        }
        catch (NoSuchFieldException a1) {
            throw new RuntimeException(a1.toString());
        }
        catch (IllegalAccessException a2) {
            throw new RuntimeException(a2.toString());
        }
    }
    
    public static Object invoke(final Object v1, final int v2, final Object[] v3) throws Throwable {
        final Method[] v4 = v1.getClass().getMethods();
        final int v5 = v4.length;
        final String v6 = "_m_" + v2;
        for (int a3 = 0; a3 < v5; ++a3) {
            if (v4[a3].getName().startsWith(v6)) {
                try {
                    return v4[a3].invoke(v1, v3);
                }
                catch (InvocationTargetException a4) {
                    throw a4.getTargetException();
                }
                catch (IllegalAccessException a5) {
                    throw new CannotInvokeException(a5);
                }
            }
        }
        throw new CannotInvokeException("cannot find a method");
    }
    
    public Object trapMethodcall(final int v-1, final Object[] v0) throws Throwable {
        try {
            final Method[] a1 = this.getReflectiveMethods();
            return a1[v-1].invoke(null, v0);
        }
        catch (InvocationTargetException a2) {
            throw a2.getTargetException();
        }
        catch (IllegalAccessException v) {
            throw new CannotInvokeException(v);
        }
    }
    
    public final Method[] getReflectiveMethods() {
        if (this.methods != null) {
            return this.methods;
        }
        final Class javaClass = this.getJavaClass();
        final Method[] declaredMethods = javaClass.getDeclaredMethods();
        final int length = declaredMethods.length;
        final int[] array = new int[length];
        int n = 0;
        for (int i = 0; i < length; ++i) {
            final Method method = declaredMethods[i];
            final String name = method.getName();
            if (name.startsWith("_m_")) {
                int n2 = 0;
                int v0 = 3;
                while (true) {
                    final char v2 = name.charAt(v0);
                    if ('0' > v2 || v2 > '9') {
                        break;
                    }
                    n2 = n2 * 10 + v2 - 48;
                    ++v0;
                }
                array[i] = ++n2;
                if (n2 > n) {
                    n = n2;
                }
            }
        }
        this.methods = new Method[n];
        for (int i = 0; i < length; ++i) {
            if (array[i] > 0) {
                this.methods[array[i] - 1] = declaredMethods[i];
            }
        }
        return this.methods;
    }
    
    public final Method getMethod(final int a1) {
        return this.getReflectiveMethods()[a1];
    }
    
    public final String getMethodName(final int v2) {
        final String v3 = this.getReflectiveMethods()[v2].getName();
        int v4 = 3;
        char a1;
        do {
            a1 = v3.charAt(v4++);
        } while (a1 >= '0' && '9' >= a1);
        return v3.substring(v4);
    }
    
    public final Class[] getParameterTypes(final int a1) {
        return this.getReflectiveMethods()[a1].getParameterTypes();
    }
    
    public final Class getReturnType(final int a1) {
        return this.getReflectiveMethods()[a1].getReturnType();
    }
    
    public final int getMethodIndex(final String v1, final Class[] v2) throws NoSuchMethodException {
        final Method[] v3 = this.getReflectiveMethods();
        for (int a1 = 0; a1 < v3.length; ++a1) {
            if (v3[a1] != null) {
                if (this.getMethodName(a1).equals(v1) && Arrays.equals(v2, v3[a1].getParameterTypes())) {
                    return a1;
                }
            }
        }
        throw new NoSuchMethodException("Method " + v1 + " not found");
    }
    
    static {
        ClassMetaobject.useContextClassLoader = false;
    }
}
