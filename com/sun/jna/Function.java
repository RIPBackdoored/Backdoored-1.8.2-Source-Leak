package com.sun.jna;

import java.util.*;
import java.lang.reflect.*;

public class Function extends Pointer
{
    public static final int MAX_NARGS = 256;
    public static final int C_CONVENTION = 0;
    public static final int ALT_CONVENTION = 63;
    private static final int MASK_CC = 63;
    public static final int THROW_LAST_ERROR = 64;
    public static final int USE_VARARGS = 384;
    static final Integer INTEGER_TRUE;
    static final Integer INTEGER_FALSE;
    private NativeLibrary library;
    private final String functionName;
    final String encoding;
    final int callFlags;
    final Map<String, ?> options;
    static final String OPTION_INVOKING_METHOD = "invoking-method";
    private static final VarArgsChecker IS_VARARGS;
    
    public static Function getFunction(final String a1, final String a2) {
        return NativeLibrary.getInstance(a1).getFunction(a2);
    }
    
    public static Function getFunction(final String a1, final String a2, final int a3) {
        return NativeLibrary.getInstance(a1).getFunction(a2, a3, null);
    }
    
    public static Function getFunction(final String a1, final String a2, final int a3, final String a4) {
        return NativeLibrary.getInstance(a1).getFunction(a2, a3, a4);
    }
    
    public static Function getFunction(final Pointer a1) {
        return getFunction(a1, 0, null);
    }
    
    public static Function getFunction(final Pointer a1, final int a2) {
        return getFunction(a1, a2, null);
    }
    
    public static Function getFunction(final Pointer a1, final int a2, final String a3) {
        return new Function(a1, a2, a3);
    }
    
    Function(final NativeLibrary a3, final String a4, final int v1, final String v2) {
        super();
        this.checkCallingConvention(v1 & 0x3F);
        if (a4 == null) {
            throw new NullPointerException("Function name must not be null");
        }
        this.library = a3;
        this.functionName = a4;
        this.callFlags = v1;
        this.options = a3.options;
        this.encoding = ((v2 != null) ? v2 : Native.getDefaultStringEncoding());
        try {
            this.peer = a3.getSymbolAddress(a4);
        }
        catch (UnsatisfiedLinkError a5) {
            throw new UnsatisfiedLinkError("Error looking up function '" + a4 + "': " + a5.getMessage());
        }
    }
    
    Function(final Pointer a1, final int a2, final String a3) {
        super();
        this.checkCallingConvention(a2 & 0x3F);
        if (a1 == null || a1.peer == 0L) {
            throw new NullPointerException("Function address may not be null");
        }
        this.functionName = a1.toString();
        this.callFlags = a2;
        this.peer = a1.peer;
        this.options = (Map<String, ?>)Collections.EMPTY_MAP;
        this.encoding = ((a3 != null) ? a3 : Native.getDefaultStringEncoding());
    }
    
    private void checkCallingConvention(final int a1) throws IllegalArgumentException {
        if ((a1 & 0x3F) != a1) {
            throw new IllegalArgumentException("Unrecognized calling convention: " + a1);
        }
    }
    
    public String getName() {
        return this.functionName;
    }
    
    public int getCallingConvention() {
        return this.callFlags & 0x3F;
    }
    
    public Object invoke(final Class<?> a1, final Object[] a2) {
        return this.invoke(a1, a2, this.options);
    }
    
    public Object invoke(final Class<?> a1, final Object[] a2, final Map<String, ?> a3) {
        final Method v1 = (Method)a3.get("invoking-method");
        final Class<?>[] v2 = (Class<?>[])((v1 != null) ? v1.getParameterTypes() : null);
        return this.invoke(v1, v2, a1, a2, a3);
    }
    
    Object invoke(final Method v-18, final Class<?>[] v-17, final Class<?> v-16, final Object[] v-15, final Map<String, ?> v-14) {
        Object[] array = new Object[0];
        if (v-15 != null) {
            if (v-15.length > 256) {
                throw new UnsupportedOperationException("Maximum argument count is 256");
            }
            array = new Object[v-15.length];
            System.arraycopy(v-15, 0, array, 0, array.length);
        }
        final TypeMapper v-19 = (TypeMapper)v-14.get("type-mapper");
        final boolean equals = Boolean.TRUE.equals(v-14.get("allow-objects"));
        final boolean b = array.length > 0 && v-18 != null && isVarArgs(v-18);
        final int v-20 = (array.length > 0 && v-18 != null) ? fixedArgs(v-18) : 0;
        for (int a2 = 0; a2 < array.length; ++a2) {
            final Class<?> a3 = (v-18 != null) ? ((b && a2 >= v-17.length - 1) ? v-17[v-17.length - 1].getComponentType() : v-17[a2]) : null;
            array[a2] = this.convertArgument(array, a2, v-18, v-19, equals, a3);
        }
        Class<?> v-21 = v-16;
        FromNativeConverter fromNativeConverter = null;
        if (NativeMapped.class.isAssignableFrom(v-16)) {
            final NativeMappedConverter a4 = (NativeMappedConverter)(fromNativeConverter = NativeMappedConverter.getInstance(v-16));
            v-21 = a4.nativeType();
        }
        else if (v-19 != null) {
            fromNativeConverter = v-19.getFromNativeConverter(v-16);
            if (fromNativeConverter != null) {
                v-21 = fromNativeConverter.nativeType();
            }
        }
        Object o = this.invoke(array, v-21, equals, v-20);
        if (fromNativeConverter != null) {
            FromNativeContext a6 = null;
            if (v-18 != null) {
                final FromNativeContext a5 = new MethodResultContext(v-16, this, v-15, v-18);
            }
            else {
                a6 = new FunctionResultContext(v-16, this, v-15);
            }
            o = fromNativeConverter.fromNative(o, a6);
        }
        if (v-15 != null) {
            for (int i = 0; i < v-15.length; ++i) {
                final Object o2 = v-15[i];
                if (o2 != null) {
                    if (o2 instanceof Structure) {
                        if (!(o2 instanceof Structure.ByValue)) {
                            ((Structure)o2).autoRead();
                        }
                    }
                    else if (array[i] instanceof PostCallRead) {
                        ((PostCallRead)array[i]).read();
                        if (array[i] instanceof PointerArray) {
                            final PointerArray pointerArray = (PointerArray)array[i];
                            if (Structure.ByReference[].class.isAssignableFrom(o2.getClass())) {
                                final Class<?> componentType = o2.getClass().getComponentType();
                                final Structure[] array2 = (Structure[])o2;
                                for (int v0 = 0; v0 < array2.length; ++v0) {
                                    final Pointer v2 = pointerArray.getPointer(Pointer.SIZE * v0);
                                    array2[v0] = Structure.updateStructureByReference(componentType, array2[v0], v2);
                                }
                            }
                        }
                    }
                    else if (Structure[].class.isAssignableFrom(o2.getClass())) {
                        Structure.autoRead((Structure[])o2);
                    }
                }
            }
        }
        return o;
    }
    
    Object invoke(final Object[] a1, final Class<?> a2, final boolean a3) {
        return this.invoke(a1, a2, a3, 0);
    }
    
    Object invoke(final Object[] v-8, final Class<?> v-7, final boolean v-6, final int v-5) {
        Object o = null;
        final int a5 = this.callFlags | (v-5 & 0x3) << 7;
        if (v-7 == null || v-7 == Void.TYPE || v-7 == Void.class) {
            Native.invokeVoid(this, this.peer, a5, v-8);
            o = null;
        }
        else if (v-7 == Boolean.TYPE || v-7 == Boolean.class) {
            o = valueOf(Native.invokeInt(this, this.peer, a5, v-8) != 0);
        }
        else if (v-7 == Byte.TYPE || v-7 == Byte.class) {
            o = (byte)Native.invokeInt(this, this.peer, a5, v-8);
        }
        else if (v-7 == Short.TYPE || v-7 == Short.class) {
            o = (short)Native.invokeInt(this, this.peer, a5, v-8);
        }
        else if (v-7 == Character.TYPE || v-7 == Character.class) {
            o = (char)Native.invokeInt(this, this.peer, a5, v-8);
        }
        else if (v-7 == Integer.TYPE || v-7 == Integer.class) {
            o = Native.invokeInt(this, this.peer, a5, v-8);
        }
        else if (v-7 == Long.TYPE || v-7 == Long.class) {
            o = Native.invokeLong(this, this.peer, a5, v-8);
        }
        else if (v-7 == Float.TYPE || v-7 == Float.class) {
            o = Native.invokeFloat(this, this.peer, a5, v-8);
        }
        else if (v-7 == Double.TYPE || v-7 == Double.class) {
            o = Native.invokeDouble(this, this.peer, a5, v-8);
        }
        else if (v-7 == String.class) {
            o = this.invokeString(a5, v-8, false);
        }
        else if (v-7 == WString.class) {
            final String a1 = this.invokeString(a5, v-8, true);
            if (a1 != null) {
                o = new WString(a1);
            }
        }
        else {
            if (Pointer.class.isAssignableFrom(v-7)) {
                return this.invokePointer(a5, v-8);
            }
            if (Structure.class.isAssignableFrom(v-7)) {
                if (Structure.ByValue.class.isAssignableFrom(v-7)) {
                    final Structure a2 = Native.invokeStructure(this, this.peer, a5, v-8, Structure.newInstance(v-7));
                    a2.autoRead();
                    o = a2;
                }
                else {
                    o = this.invokePointer(a5, v-8);
                    if (o != null) {
                        final Structure a3 = Structure.newInstance(v-7, (Pointer)o);
                        a3.conditionalAutoRead();
                        o = a3;
                    }
                }
            }
            else if (Callback.class.isAssignableFrom(v-7)) {
                o = this.invokePointer(a5, v-8);
                if (o != null) {
                    o = CallbackReference.getCallback(v-7, (Pointer)o);
                }
            }
            else if (v-7 == String[].class) {
                final Pointer a4 = this.invokePointer(a5, v-8);
                if (a4 != null) {
                    o = a4.getStringArray(0L, this.encoding);
                }
            }
            else if (v-7 == WString[].class) {
                final Pointer pointer = this.invokePointer(a5, v-8);
                if (pointer != null) {
                    final String[] wideStringArray = pointer.getWideStringArray(0L);
                    final WString[] v0 = new WString[wideStringArray.length];
                    for (int v2 = 0; v2 < wideStringArray.length; ++v2) {
                        v0[v2] = new WString(wideStringArray[v2]);
                    }
                    o = v0;
                }
            }
            else if (v-7 == Pointer[].class) {
                final Pointer pointer = this.invokePointer(a5, v-8);
                if (pointer != null) {
                    o = pointer.getPointerArray(0L);
                }
            }
            else {
                if (!v-6) {
                    throw new IllegalArgumentException("Unsupported return type " + v-7 + " in function " + this.getName());
                }
                o = Native.invokeObject(this, this.peer, a5, v-8);
                if (o != null && !v-7.isAssignableFrom(o.getClass())) {
                    throw new ClassCastException("Return type " + v-7 + " does not match result " + o.getClass());
                }
            }
        }
        return o;
    }
    
    private Pointer invokePointer(final int a1, final Object[] a2) {
        final long v1 = Native.invokePointer(this, this.peer, a1, a2);
        return (v1 == 0L) ? null : new Pointer(v1);
    }
    
    private Object convertArgument(final Object[] v-8, final int v-7, final Method v-6, final TypeMapper v-5, final boolean v-4, final Class<?> v-3) {
        Object native1 = v-8[v-7];
        if (native1 != null) {
            final Class<?> a3 = native1.getClass();
            ToNativeConverter a4 = null;
            if (NativeMapped.class.isAssignableFrom(a3)) {
                a4 = NativeMappedConverter.getInstance(a3);
            }
            else if (v-5 != null) {
                a4 = v-5.getToNativeConverter(a3);
            }
            if (a4 != null) {
                ToNativeContext a6 = null;
                if (v-6 != null) {
                    final ToNativeContext a5 = new MethodParameterContext(this, v-8, v-7, v-6);
                }
                else {
                    a6 = new FunctionParameterContext(this, v-8, v-7);
                }
                native1 = a4.toNative(native1, a6);
            }
        }
        if (native1 == null || this.isPrimitiveArray(native1.getClass())) {
            return native1;
        }
        final Class<?> class1 = native1.getClass();
        if (native1 instanceof Structure) {
            final Structure v0 = (Structure)native1;
            v0.autoWrite();
            if (v0 instanceof Structure.ByValue) {
                Class<?> v2 = v0.getClass();
                if (v-6 != null) {
                    final Class<?>[] a7 = v-6.getParameterTypes();
                    if (Function.IS_VARARGS.isVarArgs(v-6)) {
                        if (v-7 < a7.length - 1) {
                            v2 = a7[v-7];
                        }
                        else {
                            final Class<?> a8 = a7[a7.length - 1].getComponentType();
                            if (a8 != Object.class) {
                                v2 = a8;
                            }
                        }
                    }
                    else {
                        v2 = a7[v-7];
                    }
                }
                if (Structure.ByValue.class.isAssignableFrom(v2)) {
                    return v0;
                }
            }
            return v0.getPointer();
        }
        if (native1 instanceof Callback) {
            return CallbackReference.getFunctionPointer((Callback)native1);
        }
        if (native1 instanceof String) {
            return new NativeString((String)native1, false).getPointer();
        }
        if (native1 instanceof WString) {
            return new NativeString(native1.toString(), true).getPointer();
        }
        if (native1 instanceof Boolean) {
            return Boolean.TRUE.equals(native1) ? Function.INTEGER_TRUE : Function.INTEGER_FALSE;
        }
        if (String[].class == class1) {
            return new StringArray((String[])native1, this.encoding);
        }
        if (WString[].class == class1) {
            return new StringArray((WString[])native1);
        }
        if (Pointer[].class == class1) {
            return new PointerArray((Pointer[])native1);
        }
        if (NativeMapped[].class.isAssignableFrom(class1)) {
            return new NativeMappedArray((NativeMapped[])native1);
        }
        if (Structure[].class.isAssignableFrom(class1)) {
            final Structure[] v3 = (Structure[])native1;
            final Class<?> v2 = class1.getComponentType();
            final boolean v4 = Structure.ByReference.class.isAssignableFrom(v2);
            if (v-3 != null && !Structure.ByReference[].class.isAssignableFrom(v-3)) {
                if (v4) {
                    throw new IllegalArgumentException("Function " + this.getName() + " declared Structure[] at parameter " + v-7 + " but array of " + v2 + " was passed");
                }
                for (int v5 = 0; v5 < v3.length; ++v5) {
                    if (v3[v5] instanceof Structure.ByReference) {
                        throw new IllegalArgumentException("Function " + this.getName() + " declared Structure[] at parameter " + v-7 + " but element " + v5 + " is of Structure.ByReference type");
                    }
                }
            }
            if (v4) {
                Structure.autoWrite(v3);
                final Pointer[] v6 = new Pointer[v3.length + 1];
                for (int v7 = 0; v7 < v3.length; ++v7) {
                    v6[v7] = ((v3[v7] != null) ? v3[v7].getPointer() : null);
                }
                return new PointerArray(v6);
            }
            if (v3.length == 0) {
                throw new IllegalArgumentException("Structure array must have non-zero length");
            }
            if (v3[0] == null) {
                Structure.newInstance(v2).toArray(v3);
                return v3[0].getPointer();
            }
            Structure.autoWrite(v3);
            return v3[0].getPointer();
        }
        else {
            if (class1.isArray()) {
                throw new IllegalArgumentException("Unsupported array argument type: " + class1.getComponentType());
            }
            if (v-4) {
                return native1;
            }
            if (!Native.isSupportedNativeType(native1.getClass())) {
                throw new IllegalArgumentException("Unsupported argument type " + native1.getClass().getName() + " at parameter " + v-7 + " of function " + this.getName());
            }
            return native1;
        }
    }
    
    private boolean isPrimitiveArray(final Class<?> a1) {
        return a1.isArray() && a1.getComponentType().isPrimitive();
    }
    
    public void invoke(final Object[] a1) {
        this.invoke(Void.class, a1);
    }
    
    private String invokeString(final int a1, final Object[] a2, final boolean a3) {
        final Pointer v1 = this.invokePointer(a1, a2);
        String v2 = null;
        if (v1 != null) {
            if (a3) {
                v2 = v1.getWideString(0L);
            }
            else {
                v2 = v1.getString(0L, this.encoding);
            }
        }
        return v2;
    }
    
    @Override
    public String toString() {
        if (this.library != null) {
            return "native function " + this.functionName + "(" + this.library.getName() + ")@0x" + Long.toHexString(this.peer);
        }
        return "native function@0x" + Long.toHexString(this.peer);
    }
    
    public Object invokeObject(final Object[] a1) {
        return this.invoke(Object.class, a1);
    }
    
    public Pointer invokePointer(final Object[] a1) {
        return (Pointer)this.invoke(Pointer.class, a1);
    }
    
    public String invokeString(final Object[] a1, final boolean a2) {
        final Object v1 = this.invoke((Class<?>)(a2 ? WString.class : String.class), a1);
        return (v1 != null) ? v1.toString() : null;
    }
    
    public int invokeInt(final Object[] a1) {
        return (int)this.invoke(Integer.class, a1);
    }
    
    public long invokeLong(final Object[] a1) {
        return (long)this.invoke(Long.class, a1);
    }
    
    public float invokeFloat(final Object[] a1) {
        return (float)this.invoke(Float.class, a1);
    }
    
    public double invokeDouble(final Object[] a1) {
        return (double)this.invoke(Double.class, a1);
    }
    
    public void invokeVoid(final Object[] a1) {
        this.invoke(Void.class, a1);
    }
    
    @Override
    public boolean equals(final Object v2) {
        if (v2 == this) {
            return true;
        }
        if (v2 == null) {
            return false;
        }
        if (v2.getClass() == this.getClass()) {
            final Function a1 = (Function)v2;
            return a1.callFlags == this.callFlags && a1.options.equals(this.options) && a1.peer == this.peer;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.callFlags + this.options.hashCode() + super.hashCode();
    }
    
    static Object[] concatenateVarArgs(Object[] v-2) {
        if (v-2 != null && v-2.length > 0) {
            final Object o = v-2[v-2.length - 1];
            final Class<?> v0 = (o != null) ? o.getClass() : null;
            if (v0 != null && v0.isArray()) {
                final Object[] v2 = (Object[])o;
                for (int a1 = 0; a1 < v2.length; ++a1) {
                    if (v2[a1] instanceof Float) {
                        v2[a1] = v2[a1];
                    }
                }
                final Object[] v3 = new Object[v-2.length + v2.length];
                System.arraycopy(v-2, 0, v3, 0, v-2.length - 1);
                System.arraycopy(v2, 0, v3, v-2.length - 1, v2.length);
                v3[v3.length - 1] = null;
                v-2 = v3;
            }
        }
        return v-2;
    }
    
    static boolean isVarArgs(final Method a1) {
        return Function.IS_VARARGS.isVarArgs(a1);
    }
    
    static int fixedArgs(final Method a1) {
        return Function.IS_VARARGS.fixedArgs(a1);
    }
    
    static Boolean valueOf(final boolean a1) {
        return a1 ? Boolean.TRUE : Boolean.FALSE;
    }
    
    static {
        INTEGER_TRUE = -1;
        INTEGER_FALSE = 0;
        IS_VARARGS = VarArgsChecker.create();
    }
    
    private static class NativeMappedArray extends Memory implements PostCallRead
    {
        private final NativeMapped[] original;
        
        public NativeMappedArray(final NativeMapped[] a1) {
            super(Native.getNativeSize(a1.getClass(), a1));
            this.setValue(0L, this.original = a1, this.original.getClass());
        }
        
        @Override
        public void read() {
            this.getValue(0L, this.original.getClass(), this.original);
        }
    }
    
    private static class PointerArray extends Memory implements PostCallRead
    {
        private final Pointer[] original;
        
        public PointerArray(final Pointer[] v2) {
            super(Pointer.SIZE * (v2.length + 1));
            this.original = v2;
            for (int a1 = 0; a1 < v2.length; ++a1) {
                this.setPointer(a1 * Pointer.SIZE, v2[a1]);
            }
            this.setPointer(Pointer.SIZE * v2.length, null);
        }
        
        @Override
        public void read() {
            this.read(0L, this.original, 0, this.original.length);
        }
    }
    
    public interface PostCallRead
    {
        void read();
    }
}
