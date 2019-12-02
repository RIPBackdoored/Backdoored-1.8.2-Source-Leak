package net.jodah.typetools;

import java.security.*;
import sun.misc.*;
import java.lang.reflect.*;

static final class TypeResolver$1 implements PrivilegedExceptionAction<Unsafe> {
    TypeResolver$1() {
        super();
    }
    
    @Override
    public Unsafe run() throws Exception {
        final Field v1 = Unsafe.class.getDeclaredField("theUnsafe");
        v1.setAccessible(true);
        return (Unsafe)v1.get(null);
    }
    
    @Override
    public /* bridge */ Object run() throws Exception {
        return this.run();
    }
}