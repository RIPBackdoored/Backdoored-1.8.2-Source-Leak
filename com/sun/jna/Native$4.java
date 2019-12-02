package com.sun.jna;

import java.security.*;
import java.lang.reflect.*;

static final class Native$4 implements PrivilegedAction<Method> {
    Native$4() {
        super();
    }
    
    @Override
    public Method run() {
        try {
            final Method v1 = ClassLoader.class.getDeclaredMethod("findLibrary", String.class);
            v1.setAccessible(true);
            return v1;
        }
        catch (Exception v2) {
            return null;
        }
    }
    
    @Override
    public /* bridge */ Object run() {
        return this.run();
    }
}