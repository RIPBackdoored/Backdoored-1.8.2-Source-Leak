package com.sun.jna;

import java.lang.reflect.*;

private static final class RealVarArgsChecker extends VarArgsChecker
{
    private RealVarArgsChecker() {
        super(null);
    }
    
    @Override
    boolean isVarArgs(final Method a1) {
        return a1.isVarArgs();
    }
    
    @Override
    int fixedArgs(final Method a1) {
        return a1.isVarArgs() ? (a1.getParameterTypes().length - 1) : 0;
    }
    
    RealVarArgsChecker(final VarArgsChecker$1 a1) {
        this();
    }
}
