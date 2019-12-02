package com.sun.jna;

public class FunctionParameterContext extends ToNativeContext
{
    private Function function;
    private Object[] args;
    private int index;
    
    FunctionParameterContext(final Function a1, final Object[] a2, final int a3) {
        super();
        this.function = a1;
        this.args = a2;
        this.index = a3;
    }
    
    public Function getFunction() {
        return this.function;
    }
    
    public Object[] getParameters() {
        return this.args;
    }
    
    public int getParameterIndex() {
        return this.index;
    }
}
