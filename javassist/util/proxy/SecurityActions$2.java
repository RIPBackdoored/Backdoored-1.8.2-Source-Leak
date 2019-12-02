package javassist.util.proxy;

import java.security.*;

static final class SecurityActions$2 implements PrivilegedAction {
    final /* synthetic */ Class val$clazz;
    
    SecurityActions$2(final Class val$clazz) {
        this.val$clazz = val$clazz;
        super();
    }
    
    @Override
    public Object run() {
        return this.val$clazz.getDeclaredConstructors();
    }
}