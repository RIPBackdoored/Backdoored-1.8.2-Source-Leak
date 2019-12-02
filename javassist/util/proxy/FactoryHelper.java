package javassist.util.proxy;

import javassist.bytecode.*;
import java.security.*;
import javassist.*;
import java.lang.reflect.*;
import java.io.*;

public class FactoryHelper
{
    private static Method defineClass1;
    private static Method defineClass2;
    public static final Class[] primitiveTypes;
    public static final String[] wrapperTypes;
    public static final String[] wrapperDesc;
    public static final String[] unwarpMethods;
    public static final String[] unwrapDesc;
    public static final int[] dataSize;
    
    public FactoryHelper() {
        super();
    }
    
    public static final int typeIndex(final Class v1) {
        final Class[] v2 = FactoryHelper.primitiveTypes;
        for (int v3 = v2.length, a1 = 0; a1 < v3; ++a1) {
            if (v2[a1] == v1) {
                return a1;
            }
        }
        throw new RuntimeException("bad type:" + v1.getName());
    }
    
    public static Class toClass(final ClassFile a1, final ClassLoader a2) throws CannotCompileException {
        return toClass(a1, a2, null);
    }
    
    public static Class toClass(final ClassFile v-3, final ClassLoader v-2, final ProtectionDomain v-1) throws CannotCompileException {
        try {
            final byte[] a3 = toBytecode(v-3);
            Method v1 = null;
            Object[] v2 = null;
            if (v-1 == null) {
                final Method a4 = FactoryHelper.defineClass1;
                final Object[] a5 = { v-3.getName(), a3, new Integer(0), new Integer(a3.length) };
            }
            else {
                v1 = FactoryHelper.defineClass2;
                v2 = new Object[] { v-3.getName(), a3, new Integer(0), new Integer(a3.length), v-1 };
            }
            return toClass2(v1, v-2, v2);
        }
        catch (RuntimeException v3) {
            throw v3;
        }
        catch (InvocationTargetException v4) {
            throw new CannotCompileException(v4.getTargetException());
        }
        catch (Exception v5) {
            throw new CannotCompileException(v5);
        }
    }
    
    private static synchronized Class toClass2(final Method a1, final ClassLoader a2, final Object[] a3) throws Exception {
        SecurityActions.setAccessible(a1, true);
        final Class v1 = (Class)a1.invoke(a2, a3);
        SecurityActions.setAccessible(a1, false);
        return v1;
    }
    
    private static byte[] toBytecode(final ClassFile a1) throws IOException {
        final ByteArrayOutputStream v1 = new ByteArrayOutputStream();
        final DataOutputStream v2 = new DataOutputStream(v1);
        try {
            a1.write(v2);
        }
        finally {
            v2.close();
        }
        return v1.toByteArray();
    }
    
    public static void writeFile(final ClassFile a2, final String v1) throws CannotCompileException {
        try {
            writeFile0(a2, v1);
        }
        catch (IOException a3) {
            throw new CannotCompileException(a3);
        }
    }
    
    private static void writeFile0(final ClassFile v1, final String v2) throws CannotCompileException, IOException {
        final String v3 = v1.getName();
        final String v4 = v2 + File.separatorChar + v3.replace('.', File.separatorChar) + ".class";
        final int v5 = v4.lastIndexOf(File.separatorChar);
        if (v5 > 0) {
            final String a1 = v4.substring(0, v5);
            if (!a1.equals(".")) {
                new File(a1).mkdirs();
            }
        }
        final DataOutputStream v6 = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(v4)));
        try {
            v1.write(v6);
        }
        catch (IOException a2) {
            throw a2;
        }
        finally {
            v6.close();
        }
    }
    
    static {
        try {
            final Class v1 = Class.forName("java.lang.ClassLoader");
            FactoryHelper.defineClass1 = SecurityActions.getDeclaredMethod(v1, "defineClass", new Class[] { String.class, byte[].class, Integer.TYPE, Integer.TYPE });
            FactoryHelper.defineClass2 = SecurityActions.getDeclaredMethod(v1, "defineClass", new Class[] { String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class });
        }
        catch (Exception v2) {
            throw new RuntimeException("cannot initialize");
        }
        primitiveTypes = new Class[] { Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE };
        wrapperTypes = new String[] { "java.lang.Boolean", "java.lang.Byte", "java.lang.Character", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Void" };
        wrapperDesc = new String[] { "(Z)V", "(B)V", "(C)V", "(S)V", "(I)V", "(J)V", "(F)V", "(D)V" };
        unwarpMethods = new String[] { "booleanValue", "byteValue", "charValue", "shortValue", "intValue", "longValue", "floatValue", "doubleValue" };
        unwrapDesc = new String[] { "()Z", "()B", "()C", "()S", "()I", "()J", "()F", "()D" };
        dataSize = new int[] { 1, 1, 1, 1, 1, 2, 1, 2 };
    }
}
