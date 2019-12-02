package com.sun.jna;

import java.lang.reflect.*;

abstract class VarArgsChecker
{
    private VarArgsChecker() {
        super();
    }
    
    static VarArgsChecker create() {
        try {
            final Method v1 = Method.class.getMethod("isVarArgs", (Class<?>[])new Class[0]);
            if (v1 != null) {
                return new RealVarArgsChecker();
            }
            return new NoVarArgsChecker();
        }
        catch (NoSuchMethodException v2) {
            return new NoVarArgsChecker();
        }
        catch (SecurityException v3) {
            return new NoVarArgsChecker();
        }
    }
    
    abstract boolean isVarArgs(final Method p0);
    
    abstract int fixedArgs(final Method p0);
    
    VarArgsChecker(final VarArgsChecker$1 a1) {
        this();
    }
    
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
}
