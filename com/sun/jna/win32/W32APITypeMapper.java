package com.sun.jna.win32;

import com.sun.jna.*;

public class W32APITypeMapper extends DefaultTypeMapper
{
    public static final TypeMapper UNICODE;
    public static final TypeMapper ASCII;
    public static final TypeMapper DEFAULT;
    
    protected W32APITypeMapper(final boolean v2) {
        super();
        if (v2) {
            final TypeConverter a1 = new TypeConverter() {
                final /* synthetic */ W32APITypeMapper this$0;
                
                W32APITypeMapper$1() {
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
            };
            this.addTypeConverter(String.class, a1);
            this.addToNativeConverter(String[].class, a1);
        }
        final TypeConverter v3 = new TypeConverter() {
            final /* synthetic */ W32APITypeMapper this$0;
            
            W32APITypeMapper$2() {
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
        };
        this.addTypeConverter(Boolean.class, v3);
    }
    
    static {
        UNICODE = new W32APITypeMapper(true);
        ASCII = new W32APITypeMapper(false);
        DEFAULT = (Boolean.getBoolean("w32.ascii") ? W32APITypeMapper.ASCII : W32APITypeMapper.UNICODE);
    }
}
