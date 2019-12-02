package javassist;

import java.security.*;
import java.net.*;

static final class ClassPool$1 implements PrivilegedExceptionAction {
    ClassPool$1() {
        super();
    }
    
    @Override
    public Object run() throws Exception {
        final Class v1 = Class.forName("java.lang.ClassLoader");
        ClassPool.access$002(v1.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE));
        ClassPool.access$102(v1.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class));
        ClassPool.access$202(v1.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class));
        return null;
    }
}