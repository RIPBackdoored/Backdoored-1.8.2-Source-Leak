package com.sun.jna;

import java.lang.reflect.*;

private class DefaultCallbackProxy implements CallbackProxy
{
    private final Method callbackMethod;
    private ToNativeConverter toNative;
    private final FromNativeConverter[] fromNative;
    private final String encoding;
    final /* synthetic */ CallbackReference this$0;
    
    public DefaultCallbackProxy(final CallbackReference this$0, final Method a4, final TypeMapper v1, final String v2) {
        this.this$0 = this$0;
        super();
        this.callbackMethod = a4;
        this.encoding = v2;
        final Class<?>[] v3 = a4.getParameterTypes();
        final Class<?> v4 = a4.getReturnType();
        this.fromNative = new FromNativeConverter[v3.length];
        if (NativeMapped.class.isAssignableFrom(v4)) {
            this.toNative = NativeMappedConverter.getInstance(v4);
        }
        else if (v1 != null) {
            this.toNative = v1.getToNativeConverter(v4);
        }
        for (int a5 = 0; a5 < this.fromNative.length; ++a5) {
            if (NativeMapped.class.isAssignableFrom(v3[a5])) {
                this.fromNative[a5] = new NativeMappedConverter(v3[a5]);
            }
            else if (v1 != null) {
                this.fromNative[a5] = v1.getFromNativeConverter(v3[a5]);
            }
        }
        if (!a4.isAccessible()) {
            try {
                a4.setAccessible(true);
            }
            catch (SecurityException a6) {
                throw new IllegalArgumentException("Callback method is inaccessible, make sure the interface is public: " + a4);
            }
        }
    }
    
    public Callback getCallback() {
        return CallbackReference.access$000(this.this$0);
    }
    
    private Object invokeCallback(final Object[] v-3) {
        final Class<?>[] parameterTypes = this.callbackMethod.getParameterTypes();
        final Object[] array = new Object[v-3.length];
        for (int v0 = 0; v0 < v-3.length; ++v0) {
            final Class<?> v2 = parameterTypes[v0];
            final Object v3 = v-3[v0];
            if (this.fromNative[v0] != null) {
                final FromNativeContext a1 = new CallbackParameterContext(v2, this.callbackMethod, v-3, v0);
                array[v0] = this.fromNative[v0].fromNative(v3, a1);
            }
            else {
                array[v0] = this.convertArgument(v3, v2);
            }
        }
        Object v4 = null;
        final Callback v5 = this.getCallback();
        if (v5 != null) {
            try {
                v4 = this.convertResult(this.callbackMethod.invoke(v5, array));
            }
            catch (IllegalArgumentException v6) {
                Native.getCallbackExceptionHandler().uncaughtException(v5, v6);
            }
            catch (IllegalAccessException v7) {
                Native.getCallbackExceptionHandler().uncaughtException(v5, v7);
            }
            catch (InvocationTargetException v8) {
                Native.getCallbackExceptionHandler().uncaughtException(v5, v8.getTargetException());
            }
        }
        for (int v9 = 0; v9 < array.length; ++v9) {
            if (array[v9] instanceof Structure && !(array[v9] instanceof Structure.ByValue)) {
                ((Structure)array[v9]).autoWrite();
            }
        }
        return v4;
    }
    
    @Override
    public Object callback(final Object[] v2) {
        try {
            return this.invokeCallback(v2);
        }
        catch (Throwable a1) {
            Native.getCallbackExceptionHandler().uncaughtException(this.getCallback(), a1);
            return null;
        }
    }
    
    private Object convertArgument(Object v-1, final Class<?> v0) {
        if (v-1 instanceof Pointer) {
            if (v0 == String.class) {
                v-1 = ((Pointer)v-1).getString(0L, this.encoding);
            }
            else if (v0 == WString.class) {
                v-1 = new WString(((Pointer)v-1).getWideString(0L));
            }
            else if (v0 == String[].class) {
                v-1 = ((Pointer)v-1).getStringArray(0L, this.encoding);
            }
            else if (v0 == WString[].class) {
                v-1 = ((Pointer)v-1).getWideStringArray(0L);
            }
            else if (Callback.class.isAssignableFrom(v0)) {
                v-1 = CallbackReference.getCallback(v0, (Pointer)v-1);
            }
            else if (Structure.class.isAssignableFrom(v0)) {
                if (Structure.ByValue.class.isAssignableFrom(v0)) {
                    final Structure a1 = Structure.newInstance(v0);
                    final byte[] a2 = new byte[a1.size()];
                    ((Pointer)v-1).read(0L, a2, 0, a2.length);
                    a1.getPointer().write(0L, a2, 0, a2.length);
                    a1.read();
                    v-1 = a1;
                }
                else {
                    final Structure v = Structure.newInstance(v0, (Pointer)v-1);
                    v.conditionalAutoRead();
                    v-1 = v;
                }
            }
        }
        else if ((Boolean.TYPE == v0 || Boolean.class == v0) && v-1 instanceof Number) {
            v-1 = Function.valueOf(((Number)v-1).intValue() != 0);
        }
        return v-1;
    }
    
    private Object convertResult(Object v2) {
        if (this.toNative != null) {
            v2 = this.toNative.toNative(v2, new CallbackResultContext(this.callbackMethod));
        }
        if (v2 == null) {
            return null;
        }
        final Class<?> v3 = v2.getClass();
        if (Structure.class.isAssignableFrom(v3)) {
            if (Structure.ByValue.class.isAssignableFrom(v3)) {
                return v2;
            }
            return ((Structure)v2).getPointer();
        }
        else {
            if (v3 == Boolean.TYPE || v3 == Boolean.class) {
                return Boolean.TRUE.equals(v2) ? Function.INTEGER_TRUE : Function.INTEGER_FALSE;
            }
            if (v3 == String.class || v3 == WString.class) {
                return CallbackReference.access$100(v2, v3 == WString.class);
            }
            if (v3 == String[].class || v3 == WString.class) {
                final StringArray a1 = (v3 == String[].class) ? new StringArray((String[])v2, this.encoding) : new StringArray((WString[])v2);
                CallbackReference.allocations.put(v2, a1);
                return a1;
            }
            if (Callback.class.isAssignableFrom(v3)) {
                return CallbackReference.getFunctionPointer((Callback)v2);
            }
            return v2;
        }
    }
    
    @Override
    public Class<?>[] getParameterTypes() {
        return this.callbackMethod.getParameterTypes();
    }
    
    @Override
    public Class<?> getReturnType() {
        return this.callbackMethod.getReturnType();
    }
}
