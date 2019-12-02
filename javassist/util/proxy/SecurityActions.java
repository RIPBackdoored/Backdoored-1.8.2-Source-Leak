package javassist.util.proxy;

import java.security.*;
import java.lang.reflect.*;

class SecurityActions
{
    SecurityActions() {
        super();
    }
    
    static Method[] getDeclaredMethods(final Class a1) {
        if (System.getSecurityManager() == null) {
            return a1.getDeclaredMethods();
        }
        return AccessController.doPrivileged((PrivilegedAction<Method[]>)new PrivilegedAction() {
            final /* synthetic */ Class val$clazz;
            
            SecurityActions$1() {
                super();
            }
            
            @Override
            public Object run() {
                return a1.getDeclaredMethods();
            }
        });
    }
    
    static Constructor[] getDeclaredConstructors(final Class a1) {
        if (System.getSecurityManager() == null) {
            return a1.getDeclaredConstructors();
        }
        return AccessController.doPrivileged((PrivilegedAction<Constructor[]>)new PrivilegedAction() {
            final /* synthetic */ Class val$clazz;
            
            SecurityActions$2() {
                super();
            }
            
            @Override
            public Object run() {
                return a1.getDeclaredConstructors();
            }
        });
    }
    
    static Method getDeclaredMethod(final Class a2, final String a3, final Class[] v1) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return a2.getDeclaredMethod(a3, (Class[])v1);
        }
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction() {
                final /* synthetic */ Class val$clazz;
                final /* synthetic */ String val$name;
                final /* synthetic */ Class[] val$types;
                
                SecurityActions$3() {
                    super();
                }
                
                @Override
                public Object run() throws Exception {
                    return a2.getDeclaredMethod(a3, (Class[])v1);
                }
            });
        }
        catch (PrivilegedActionException a4) {
            if (a4.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)a4.getCause();
            }
            throw new RuntimeException(a4.getCause());
        }
    }
    
    static Constructor getDeclaredConstructor(final Class a2, final Class[] v1) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return a2.getDeclaredConstructor((Class[])v1);
        }
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Constructor>)new PrivilegedExceptionAction() {
                final /* synthetic */ Class val$clazz;
                final /* synthetic */ Class[] val$types;
                
                SecurityActions$4() {
                    super();
                }
                
                @Override
                public Object run() throws Exception {
                    return a2.getDeclaredConstructor((Class[])v1);
                }
            });
        }
        catch (PrivilegedActionException a3) {
            if (a3.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)a3.getCause();
            }
            throw new RuntimeException(a3.getCause());
        }
    }
    
    static void setAccessible(final AccessibleObject a1, final boolean a2) {
        if (System.getSecurityManager() == null) {
            a1.setAccessible(a2);
        }
        else {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                final /* synthetic */ AccessibleObject val$ao;
                final /* synthetic */ boolean val$accessible;
                
                SecurityActions$5() {
                    super();
                }
                
                @Override
                public Object run() {
                    a1.setAccessible(a2);
                    return null;
                }
            });
        }
    }
    
    static void set(final Field a2, final Object a3, final Object v1) throws IllegalAccessException {
        if (System.getSecurityManager() == null) {
            a2.set(a3, v1);
        }
        else {
            try {
                AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction() {
                    final /* synthetic */ Field val$fld;
                    final /* synthetic */ Object val$target;
                    final /* synthetic */ Object val$value;
                    
                    SecurityActions$6() {
                        super();
                    }
                    
                    @Override
                    public Object run() throws Exception {
                        a2.set(a3, v1);
                        return null;
                    }
                });
            }
            catch (PrivilegedActionException a4) {
                if (a4.getCause() instanceof NoSuchMethodException) {
                    throw (IllegalAccessException)a4.getCause();
                }
                throw new RuntimeException(a4.getCause());
            }
        }
    }
}
