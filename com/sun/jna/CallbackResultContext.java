package com.sun.jna;

import java.lang.reflect.*;

public class CallbackResultContext extends ToNativeContext
{
    private Method method;
    
    CallbackResultContext(final Method a1) {
        super();
        this.method = a1;
    }
    
    public Method getMethod() {
        return this.method;
    }
}
