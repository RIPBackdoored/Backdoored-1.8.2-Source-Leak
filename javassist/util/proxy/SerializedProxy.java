package javassist.util.proxy;

import java.security.*;
import java.io.*;

class SerializedProxy implements Serializable
{
    private String superClass;
    private String[] interfaces;
    private byte[] filterSignature;
    private MethodHandler handler;
    
    SerializedProxy(final Class v1, final byte[] v2, final MethodHandler v3) {
        super();
        this.filterSignature = v2;
        this.handler = v3;
        this.superClass = v1.getSuperclass().getName();
        final Class[] v4 = v1.getInterfaces();
        final int v5 = v4.length;
        this.interfaces = new String[v5 - 1];
        final String v6 = ProxyObject.class.getName();
        final String v7 = Proxy.class.getName();
        for (int a2 = 0; a2 < v5; ++a2) {
            final String a3 = v4[a2].getName();
            if (!a3.equals(v6) && !a3.equals(v7)) {
                this.interfaces[a2] = a3;
            }
        }
    }
    
    protected Class loadClass(final String v2) throws ClassNotFoundException {
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Class>)new PrivilegedExceptionAction() {
                final /* synthetic */ String val$className;
                final /* synthetic */ SerializedProxy this$0;
                
                SerializedProxy$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public Object run() throws Exception {
                    final ClassLoader v1 = Thread.currentThread().getContextClassLoader();
                    return Class.forName(v2, true, v1);
                }
            });
        }
        catch (PrivilegedActionException a1) {
            throw new RuntimeException("cannot load the class: " + v2, a1.getException());
        }
    }
    
    Object readResolve() throws ObjectStreamException {
        try {
            final int length = this.interfaces.length;
            final Class[] v0 = new Class[length];
            for (int v2 = 0; v2 < length; ++v2) {
                v0[v2] = this.loadClass(this.interfaces[v2]);
            }
            final ProxyFactory v3 = new ProxyFactory();
            v3.setSuperclass(this.loadClass(this.superClass));
            v3.setInterfaces(v0);
            final Proxy v4 = v3.createClass(this.filterSignature).newInstance();
            v4.setHandler(this.handler);
            return v4;
        }
        catch (ClassNotFoundException ex) {
            throw new InvalidClassException(ex.getMessage());
        }
        catch (InstantiationException ex2) {
            throw new InvalidObjectException(ex2.getMessage());
        }
        catch (IllegalAccessException ex3) {
            throw new InvalidClassException(ex3.getMessage());
        }
    }
}
