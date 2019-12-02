package com.sun.jna;

import java.util.*;
import java.lang.reflect.*;

private static class NativeFunctionHandler implements InvocationHandler
{
    private final Function function;
    private final Map<String, ?> options;
    
    public NativeFunctionHandler(final Pointer a1, final int a2, final Map<String, ?> a3) {
        super();
        this.options = a3;
        this.function = new Function(a1, a2, (String)a3.get("string-encoding"));
    }
    
    @Override
    public Object invoke(final Object v-2, final Method v-1, Object[] v0) throws Throwable {
        if (Library.Handler.OBJECT_TOSTRING.equals(v-1)) {
            String a1 = "Proxy interface to " + this.function;
            final Method a2 = (Method)this.options.get("invoking-method");
            final Class<?> a3 = CallbackReference.findCallbackClass(a2.getDeclaringClass());
            a1 = a1 + " (" + a3.getName() + ")";
            return a1;
        }
        if (Library.Handler.OBJECT_HASHCODE.equals(v-1)) {
            return this.hashCode();
        }
        if (!Library.Handler.OBJECT_EQUALS.equals(v-1)) {
            if (Function.isVarArgs(v-1)) {
                v0 = Function.concatenateVarArgs(v0);
            }
            return this.function.invoke(v-1.getReturnType(), v0, this.options);
        }
        final Object v = v0[0];
        if (v != null && Proxy.isProxyClass(v.getClass())) {
            return Function.valueOf(Proxy.getInvocationHandler(v) == this);
        }
        return Boolean.FALSE;
    }
    
    public Pointer getPointer() {
        return this.function;
    }
}
