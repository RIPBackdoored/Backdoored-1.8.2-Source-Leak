package javassist.util.proxy;

import java.security.*;

static final class SecurityActions$4 implements PrivilegedExceptionAction {
    final /* synthetic */ Class val$clazz;
    final /* synthetic */ Class[] val$types;
    
    SecurityActions$4(final Class val$clazz, final Class[] val$types) {
        this.val$clazz = val$clazz;
        this.val$types = val$types;
        super();
    }
    
    @Override
    public Object run() throws Exception {
        return this.val$clazz.getDeclaredConstructor((Class[])this.val$types);
    }
}