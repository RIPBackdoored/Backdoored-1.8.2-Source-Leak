package com.sun.jna;

import java.lang.reflect.*;

private static final class NoVarArgsChecker extends VarArgsChecker
{
    private NoVarArgsChecker() {
        super(null);
    }
    
    @Override
    boolean isVarArgs(final Method a1) {
        return false;
    }
    
    @Override
    int fixedArgs(final Method a1) {
        return 0;
    }
    
    NoVarArgsChecker(final VarArgsChecker$1 a1) {
        this();
    }
}
