package com.sun.jna;

import java.lang.reflect.*;
import java.nio.*;
import java.util.*;

static class FFIType extends Structure
{
    private static final Map<Object, Object> typeInfoMap;
    private static final int FFI_TYPE_STRUCT = 13;
    public size_t size;
    public short alignment;
    public short type;
    public Pointer elements;
    
    private FFIType(final Structure v0) {
        super();
        this.type = 13;
        Structure.access$1900(v0, true);
        Pointer[] v;
        if (v0 instanceof Union) {
            final StructField a1 = ((Union)v0).typeInfoField();
            v = new Pointer[] { get(v0.getFieldValue(a1.field), a1.type), null };
        }
        else {
            v = new Pointer[v0.fields().size() + 1];
            int v2 = 0;
            for (final StructField v3 : v0.fields().values()) {
                v[v2++] = v0.getFieldTypeInfo(v3);
            }
        }
        this.init(v);
    }
    
    private FFIType(final Object v1, final Class<?> v2) {
        super();
        this.type = 13;
        final int v3 = Array.getLength(v1);
        final Pointer[] v4 = new Pointer[v3 + 1];
        final Pointer v5 = get(null, v2.getComponentType());
        for (int a1 = 0; a1 < v3; ++a1) {
            v4[a1] = v5;
        }
        this.init(v4);
    }
    
    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList("size", "alignment", "type", "elements");
    }
    
    private void init(final Pointer[] a1) {
        (this.elements = new Memory(Pointer.SIZE * a1.length)).write(0L, a1, 0, a1.length);
        this.write();
    }
    
    static Pointer get(final Object a1) {
        if (a1 == null) {
            return FFITypes.ffi_type_pointer;
        }
        if (a1 instanceof Class) {
            return get(null, (Class<?>)a1);
        }
        return get(a1, a1.getClass());
    }
    
    private static Pointer get(Object v-4, Class<?> v-3) {
        final TypeMapper typeMapper = Native.getTypeMapper(v-3);
        if (typeMapper != null) {
            final ToNativeConverter a1 = typeMapper.getToNativeConverter(v-3);
            if (a1 != null) {
                v-3 = a1.nativeType();
            }
        }
        synchronized (FFIType.typeInfoMap) {
            final Object v0 = FFIType.typeInfoMap.get(v-3);
            if (v0 instanceof Pointer) {
                return (Pointer)v0;
            }
            if (v0 instanceof FFIType) {
                return ((FFIType)v0).getPointer();
            }
            if ((Platform.HAS_BUFFERS && Buffer.class.isAssignableFrom(v-3)) || Callback.class.isAssignableFrom(v-3)) {
                FFIType.typeInfoMap.put(v-3, FFITypes.ffi_type_pointer);
                return FFITypes.ffi_type_pointer;
            }
            if (Structure.class.isAssignableFrom(v-3)) {
                if (v-4 == null) {
                    v-4 = Structure.newInstance(v-3, Structure.access$2000());
                }
                if (ByReference.class.isAssignableFrom(v-3)) {
                    FFIType.typeInfoMap.put(v-3, FFITypes.ffi_type_pointer);
                    return FFITypes.ffi_type_pointer;
                }
                final FFIType a2 = new FFIType((Structure)v-4);
                FFIType.typeInfoMap.put(v-3, a2);
                return a2.getPointer();
            }
            else {
                if (NativeMapped.class.isAssignableFrom(v-3)) {
                    final NativeMappedConverter v2 = NativeMappedConverter.getInstance(v-3);
                    return get(v2.toNative(v-4, new ToNativeContext()), v2.nativeType());
                }
                if (v-3.isArray()) {
                    final FFIType v3 = new FFIType(v-4, v-3);
                    FFIType.typeInfoMap.put(v-4, v3);
                    return v3.getPointer();
                }
                throw new IllegalArgumentException("Unsupported type " + v-3);
            }
        }
    }
    
    static /* bridge */ Pointer access$800(final Object a1, final Class a2) {
        return get(a1, a2);
    }
    
    static {
        typeInfoMap = new WeakHashMap<Object, Object>();
        if (Native.POINTER_SIZE == 0) {
            throw new Error("Native library not initialized");
        }
        if (FFITypes.ffi_type_void == null) {
            throw new Error("FFI types not initialized");
        }
        FFIType.typeInfoMap.put(Void.TYPE, FFITypes.ffi_type_void);
        FFIType.typeInfoMap.put(Void.class, FFITypes.ffi_type_void);
        FFIType.typeInfoMap.put(Float.TYPE, FFITypes.ffi_type_float);
        FFIType.typeInfoMap.put(Float.class, FFITypes.ffi_type_float);
        FFIType.typeInfoMap.put(Double.TYPE, FFITypes.ffi_type_double);
        FFIType.typeInfoMap.put(Double.class, FFITypes.ffi_type_double);
        FFIType.typeInfoMap.put(Long.TYPE, FFITypes.ffi_type_sint64);
        FFIType.typeInfoMap.put(Long.class, FFITypes.ffi_type_sint64);
        FFIType.typeInfoMap.put(Integer.TYPE, FFITypes.ffi_type_sint32);
        FFIType.typeInfoMap.put(Integer.class, FFITypes.ffi_type_sint32);
        FFIType.typeInfoMap.put(Short.TYPE, FFITypes.ffi_type_sint16);
        FFIType.typeInfoMap.put(Short.class, FFITypes.ffi_type_sint16);
        final Pointer v1 = (Native.WCHAR_SIZE == 2) ? FFITypes.ffi_type_uint16 : FFITypes.ffi_type_uint32;
        FFIType.typeInfoMap.put(Character.TYPE, v1);
        FFIType.typeInfoMap.put(Character.class, v1);
        FFIType.typeInfoMap.put(Byte.TYPE, FFITypes.ffi_type_sint8);
        FFIType.typeInfoMap.put(Byte.class, FFITypes.ffi_type_sint8);
        FFIType.typeInfoMap.put(Pointer.class, FFITypes.ffi_type_pointer);
        FFIType.typeInfoMap.put(String.class, FFITypes.ffi_type_pointer);
        FFIType.typeInfoMap.put(WString.class, FFITypes.ffi_type_pointer);
        FFIType.typeInfoMap.put(Boolean.TYPE, FFITypes.ffi_type_uint32);
        FFIType.typeInfoMap.put(Boolean.class, FFITypes.ffi_type_uint32);
    }
    
    public static class size_t extends IntegerType
    {
        private static final long serialVersionUID = 1L;
        
        public size_t() {
            this(0L);
        }
        
        public size_t(final long a1) {
            super(Native.SIZE_T_SIZE, a1);
        }
    }
    
    private static class FFITypes
    {
        private static Pointer ffi_type_void;
        private static Pointer ffi_type_float;
        private static Pointer ffi_type_double;
        private static Pointer ffi_type_longdouble;
        private static Pointer ffi_type_uint8;
        private static Pointer ffi_type_sint8;
        private static Pointer ffi_type_uint16;
        private static Pointer ffi_type_sint16;
        private static Pointer ffi_type_uint32;
        private static Pointer ffi_type_sint32;
        private static Pointer ffi_type_uint64;
        private static Pointer ffi_type_sint64;
        private static Pointer ffi_type_pointer;
        
        private FFITypes() {
            super();
        }
        
        static /* synthetic */ Pointer access$900() {
            return FFITypes.ffi_type_void;
        }
        
        static /* synthetic */ Pointer access$1000() {
            return FFITypes.ffi_type_float;
        }
        
        static /* synthetic */ Pointer access$1100() {
            return FFITypes.ffi_type_double;
        }
        
        static /* synthetic */ Pointer access$1200() {
            return FFITypes.ffi_type_sint64;
        }
        
        static /* synthetic */ Pointer access$1300() {
            return FFITypes.ffi_type_sint32;
        }
        
        static /* synthetic */ Pointer access$1400() {
            return FFITypes.ffi_type_sint16;
        }
        
        static /* synthetic */ Pointer access$1500() {
            return FFITypes.ffi_type_uint16;
        }
        
        static /* synthetic */ Pointer access$1600() {
            return FFITypes.ffi_type_uint32;
        }
        
        static /* synthetic */ Pointer access$1700() {
            return FFITypes.ffi_type_sint8;
        }
        
        static /* synthetic */ Pointer access$1800() {
            return FFITypes.ffi_type_pointer;
        }
    }
}
