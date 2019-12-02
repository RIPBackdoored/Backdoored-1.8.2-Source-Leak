package com.sun.jna;

import java.lang.reflect.*;
import java.util.*;

private static final class FunctionInfo
{
    final InvocationHandler handler;
    final Function function;
    final boolean isVarArgs;
    final Map<String, ?> options;
    final Class<?>[] parameterTypes;
    
    FunctionInfo(final InvocationHandler a1, final Function a2, final Class<?>[] a3, final boolean a4, final Map<String, ?> a5) {
        super();
        this.handler = a1;
        this.function = a2;
        this.isVarArgs = a4;
        this.options = a5;
        this.parameterTypes = a3;
    }
}
