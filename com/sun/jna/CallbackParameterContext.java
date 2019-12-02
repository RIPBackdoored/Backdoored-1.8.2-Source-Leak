package com.sun.jna;

import java.lang.reflect.*;

public class CallbackParameterContext extends FromNativeContext
{
    private Method method;
    private Object[] args;
    private int index;
    
    CallbackParameterContext(final Class<?> a1, final Method a2, final Object[] a3, final int a4) {
        super(a1);
        this.method = a2;
        this.args = a3;
        this.index = a4;
    }
    
    public Method getMethod() {
        return this.method;
    }
    
    public Object[] getArguments() {
        return this.args;
    }
    
    public int getIndex() {
        return this.index;
    }
}
