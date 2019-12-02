package com.sun.jna;

import java.lang.reflect.*;

public class MethodResultContext extends FunctionResultContext
{
    private final Method method;
    
    MethodResultContext(final Class<?> a1, final Function a2, final Object[] a3, final Method a4) {
        super(a1, a2, a3);
        this.method = a4;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
