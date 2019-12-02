package javassist.util.proxy;

import java.security.*;
import java.lang.reflect.*;

static final class SecurityActions$5 implements PrivilegedAction {
    final /* synthetic */ AccessibleObject val$ao;
    final /* synthetic */ boolean val$accessible;
    
    SecurityActions$5(final AccessibleObject val$ao, final boolean val$accessible) {
        this.val$ao = val$ao;
        this.val$accessible = val$accessible;
        super();
    }
    
    @Override
    public Object run() {
        this.val$ao.setAccessible(this.val$accessible);
        return null;
    }
}