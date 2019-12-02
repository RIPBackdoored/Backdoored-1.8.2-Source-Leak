package com.sun.jna;

import java.lang.reflect.*;
import java.util.*;

class NativeLibrary$1 extends Function {
    final /* synthetic */ NativeLibrary this$0;
    
    NativeLibrary$1(final NativeLibrary a1, final NativeLibrary a2, final String a3, final int a4, final String a5) {
        this.this$0 = a1;
        super(a2, a3, a4, a5);
    }
    
    @Override
    Object invoke(final Object[] a1, final Class<?> a2, final boolean a3, final int a4) {
        return Native.getLastError();
    }
    
    @Override
    Object invoke(final Method a1, final Class<?>[] a2, final Class<?> a3, final Object[] a4, final Map<String, ?> a5) {
        return Native.getLastError();
    }
}