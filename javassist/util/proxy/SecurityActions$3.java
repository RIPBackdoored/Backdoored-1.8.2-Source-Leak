package javassist.util.proxy;

import java.security.*;

static final class SecurityActions$3 implements PrivilegedExceptionAction {
    final /* synthetic */ Class val$clazz;
    final /* synthetic */ String val$name;
    final /* synthetic */ Class[] val$types;
    
    SecurityActions$3(final Class val$clazz, final String val$name, final Class[] val$types) {
        this.val$clazz = val$clazz;
        this.val$name = val$name;
        this.val$types = val$types;
        super();
    }
    
    @Override
    public Object run() throws Exception {
        return this.val$clazz.getDeclaredMethod(this.val$name, (Class[])this.val$types);
    }
}