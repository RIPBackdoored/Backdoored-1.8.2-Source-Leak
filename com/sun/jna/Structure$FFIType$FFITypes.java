package com.sun.jna;

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
