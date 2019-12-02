package com.sun.jna;

public class FromNativeContext
{
    private Class<?> type;
    
    FromNativeContext(final Class<?> a1) {
        super();
        this.type = a1;
    }
    
    public Class<?> getTargetType() {
        return this.type;
    }
}
