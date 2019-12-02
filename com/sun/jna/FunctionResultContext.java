package com.sun.jna;

public class FunctionResultContext extends FromNativeContext
{
    private Function function;
    private Object[] args;
    
    FunctionResultContext(final Class<?> a1, final Function a2, final Object[] a3) {
        super(a1);
        this.function = a2;
        this.args = a3;
    }
    
    public Function getFunction() {
        return this.function;
    }
    
    public Object[] getArguments() {
        return this.args;
    }
}
