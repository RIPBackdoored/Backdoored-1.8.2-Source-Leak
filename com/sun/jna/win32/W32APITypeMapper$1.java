package com.sun.jna.win32;

import com.sun.jna.*;

class W32APITypeMapper$1 implements TypeConverter {
    final /* synthetic */ W32APITypeMapper this$0;
    
    W32APITypeMapper$1(final W32APITypeMapper a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public Object toNative(final Object a1, final ToNativeContext a2) {
        if (a1 == null) {
            return null;
        }
        if (a1 instanceof String[]) {
            return new StringArray((String[])a1, true);
        }
        return new WString(a1.toString());
    }
    
    @Override
    public Object fromNative(final Object a1, final FromNativeContext a2) {
        if (a1 == null) {
            return null;
        }
        return a1.toString();
    }
    
    @Override
    public Class<?> nativeType() {
        return WString.class;
    }
}