package com.sun.jna.win32;

import com.sun.jna.*;

class W32APITypeMapper$2 implements TypeConverter {
    final /* synthetic */ W32APITypeMapper this$0;
    
    W32APITypeMapper$2(final W32APITypeMapper a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public Object toNative(final Object a1, final ToNativeContext a2) {
        return Boolean.TRUE.equals(a1) ? 1 : 0;
    }
    
    @Override
    public Object fromNative(final Object a1, final FromNativeContext a2) {
        return ((int)a1 != 0) ? Boolean.TRUE : Boolean.FALSE;
    }
    
    @Override
    public Class<?> nativeType() {
        return Integer.class;
    }
}