package com.sun.jna.win32;

import java.lang.reflect.*;
import com.sun.jna.*;

public class StdCallFunctionMapper implements FunctionMapper
{
    public StdCallFunctionMapper() {
        super();
    }
    
    protected int getArgumentNativeStackSize(Class<?> v2) {
        if (NativeMapped.class.isAssignableFrom(v2)) {
            v2 = NativeMappedConverter.getInstance(v2).nativeType();
        }
        if (v2.isArray()) {
            return Pointer.SIZE;
        }
        try {
            return Native.getNativeSize(v2);
        }
        catch (IllegalArgumentException a1) {
            throw new IllegalArgumentException("Unknown native stack allocation size for " + v2);
        }
    }
    
    @Override
    public String getFunctionName(final NativeLibrary v-7, final Method v-6) {
        String s = v-6.getName();
        int n = 0;
        final Class<?>[] parameterTypes;
        final Class<?>[] array = parameterTypes = v-6.getParameterTypes();
        for (final Class<?> a1 : parameterTypes) {
            n += this.getArgumentNativeStackSize(a1);
        }
        final String string = s + "@" + n;
        final int n2 = 63;
        try {
            final Function a2 = v-7.getFunction(string, n2);
            s = a2.getName();
        }
        catch (UnsatisfiedLinkError v2) {
            try {
                final Function v1 = v-7.getFunction("_" + string, n2);
                s = v1.getName();
            }
            catch (UnsatisfiedLinkError unsatisfiedLinkError) {}
        }
        return s;
    }
}
