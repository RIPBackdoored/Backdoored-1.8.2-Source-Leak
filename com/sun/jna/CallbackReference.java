package com.sun.jna;

import java.lang.ref.*;
import com.sun.jna.win32.*;
import java.util.*;
import java.lang.reflect.*;

public class CallbackReference extends WeakReference<Callback>
{
    static final Map<Callback, CallbackReference> callbackMap;
    static final Map<Callback, CallbackReference> directCallbackMap;
    static final Map<Pointer, Reference<Callback>> pointerCallbackMap;
    static final Map<Object, Object> allocations;
    private static final Map<CallbackReference, Reference<CallbackReference>> allocatedMemory;
    private static final Method PROXY_CALLBACK_METHOD;
    private static final Map<Callback, CallbackThreadInitializer> initializers;
    Pointer cbstruct;
    Pointer trampoline;
    CallbackProxy proxy;
    Method method;
    int callingConvention;
    
    static CallbackThreadInitializer setCallbackThreadInitializer(final Callback a1, final CallbackThreadInitializer a2) {
        synchronized (CallbackReference.initializers) {
            if (a2 != null) {
                return CallbackReference.initializers.put(a1, a2);
            }
            return CallbackReference.initializers.remove(a1);
        }
    }
    
    private static ThreadGroup initializeThread(Callback a1, final AttachOptions a2) {
        CallbackThreadInitializer v1 = null;
        if (a1 instanceof DefaultCallbackProxy) {
            a1 = ((DefaultCallbackProxy)a1).getCallback();
        }
        synchronized (CallbackReference.initializers) {
            v1 = CallbackReference.initializers.get(a1);
        }
        ThreadGroup v2 = null;
        if (v1 != null) {
            v2 = v1.getThreadGroup(a1);
            a2.name = v1.getName(a1);
            a2.daemon = v1.isDaemon(a1);
            a2.detach = v1.detach(a1);
            a2.write();
        }
        return v2;
    }
    
    public static Callback getCallback(final Class<?> a1, final Pointer a2) {
        return getCallback(a1, a2, false);
    }
    
    private static Callback getCallback(final Class<?> v-7, final Pointer v-6, final boolean v-5) {
        if (v-6 == null) {
            return null;
        }
        if (!v-7.isInterface()) {
            throw new IllegalArgumentException("Callback type must be an interface");
        }
        final Map<Callback, CallbackReference> map = v-5 ? CallbackReference.directCallbackMap : CallbackReference.callbackMap;
        synchronized (CallbackReference.pointerCallbackMap) {
            Callback a1 = null;
            final Reference<Callback> a2 = CallbackReference.pointerCallbackMap.get(v-6);
            if (a2 == null) {
                final int a3 = AltCallingConvention.class.isAssignableFrom(v-7) ? 63 : 0;
                final Map<String, Object> v1 = new HashMap<String, Object>(Native.getLibraryOptions(v-7));
                v1.put("invoking-method", getCallbackMethod(v-7));
                final NativeFunctionHandler v2 = new NativeFunctionHandler(v-6, a3, v1);
                a1 = (Callback)Proxy.newProxyInstance(v-7.getClassLoader(), new Class[] { v-7 }, v2);
                map.remove(a1);
                CallbackReference.pointerCallbackMap.put(v-6, new WeakReference<Callback>(a1));
                return a1;
            }
            a1 = a2.get();
            if (a1 != null && !v-7.isAssignableFrom(a1.getClass())) {
                throw new IllegalStateException("Pointer " + v-6 + " already mapped to " + a1 + ".\nNative code may be re-using a default function pointer, in which case you may need to use a common Callback class wherever the function pointer is reused.");
            }
            return a1;
        }
    }
    
    private CallbackReference(final Callback v-9, final int v-8, boolean v-7) {
        super(v-9);
        final TypeMapper typeMapper = Native.getTypeMapper(v-9.getClass());
        this.callingConvention = v-8;
        final boolean ppc = Platform.isPPC();
        if (v-7) {
            final Method a2 = getCallbackMethod(v-9);
            final Class<?>[] a3 = a2.getParameterTypes();
            for (int a4 = 0; a4 < a3.length; ++a4) {
                if (ppc && (a3[a4] == Float.TYPE || a3[a4] == Double.TYPE)) {
                    v-7 = false;
                    break;
                }
                if (typeMapper != null && typeMapper.getFromNativeConverter(a3[a4]) != null) {
                    v-7 = false;
                    break;
                }
            }
            if (typeMapper != null && typeMapper.getToNativeConverter(a2.getReturnType()) != null) {
                v-7 = false;
            }
        }
        final String stringEncoding = Native.getStringEncoding(v-9.getClass());
        long a5 = 0L;
        if (v-7) {
            this.method = getCallbackMethod(v-9);
            final Class<?>[] array = this.method.getParameterTypes();
            final Class<?> clazz = this.method.getReturnType();
            int v1 = 1;
            if (v-9 instanceof DLLCallback) {
                v1 |= 0x2;
            }
            a5 = Native.createNativeCallback(v-9, this.method, array, clazz, v-8, v1, stringEncoding);
        }
        else {
            if (v-9 instanceof CallbackProxy) {
                this.proxy = (CallbackProxy)v-9;
            }
            else {
                this.proxy = new DefaultCallbackProxy(getCallbackMethod(v-9), typeMapper, stringEncoding);
            }
            final Class<?>[] array = this.proxy.getParameterTypes();
            Class<?> clazz = this.proxy.getReturnType();
            if (typeMapper != null) {
                for (int v1 = 0; v1 < array.length; ++v1) {
                    final FromNativeConverter v2 = typeMapper.getFromNativeConverter(array[v1]);
                    if (v2 != null) {
                        array[v1] = v2.nativeType();
                    }
                }
                final ToNativeConverter v3 = typeMapper.getToNativeConverter(clazz);
                if (v3 != null) {
                    clazz = v3.nativeType();
                }
            }
            for (int v1 = 0; v1 < array.length; ++v1) {
                array[v1] = this.getNativeType(array[v1]);
                if (!isAllowableNativeType(array[v1])) {
                    final String v4 = "Callback argument " + array[v1] + " requires custom type conversion";
                    throw new IllegalArgumentException(v4);
                }
            }
            clazz = this.getNativeType(clazz);
            if (!isAllowableNativeType(clazz)) {
                final String v5 = "Callback return type " + clazz + " requires custom type conversion";
                throw new IllegalArgumentException(v5);
            }
            int v1 = (v-9 instanceof DLLCallback) ? 2 : 0;
            a5 = Native.createNativeCallback(this.proxy, CallbackReference.PROXY_CALLBACK_METHOD, array, clazz, v-8, v1, stringEncoding);
        }
        this.cbstruct = ((a5 != 0L) ? new Pointer(a5) : null);
        CallbackReference.allocatedMemory.put(this, new WeakReference<CallbackReference>(this));
    }
    
    private Class<?> getNativeType(final Class<?> a1) {
        if (Structure.class.isAssignableFrom(a1)) {
            Structure.validate(a1);
            if (!Structure.ByValue.class.isAssignableFrom(a1)) {
                return Pointer.class;
            }
        }
        else {
            if (NativeMapped.class.isAssignableFrom(a1)) {
                return NativeMappedConverter.getInstance(a1).nativeType();
            }
            if (a1 == String.class || a1 == WString.class || a1 == String[].class || a1 == WString[].class || Callback.class.isAssignableFrom(a1)) {
                return Pointer.class;
            }
        }
        return a1;
    }
    
    private static Method checkMethod(final Method v1) {
        if (v1.getParameterTypes().length > 256) {
            final String a1 = "Method signature exceeds the maximum parameter count: " + v1;
            throw new UnsupportedOperationException(a1);
        }
        return v1;
    }
    
    static Class<?> findCallbackClass(final Class<?> v-1) {
        if (!Callback.class.isAssignableFrom(v-1)) {
            throw new IllegalArgumentException(v-1.getName() + " is not derived from com.sun.jna.Callback");
        }
        if (v-1.isInterface()) {
            return v-1;
        }
        final Class<?>[] v0 = v-1.getInterfaces();
        for (int v2 = 0; v2 < v0.length; ++v2) {
            if (Callback.class.isAssignableFrom(v0[v2])) {
                try {
                    getCallbackMethod(v0[v2]);
                    return v0[v2];
                }
                catch (IllegalArgumentException a1) {
                    break;
                }
            }
        }
        if (Callback.class.isAssignableFrom(v-1.getSuperclass())) {
            return findCallbackClass(v-1.getSuperclass());
        }
        return v-1;
    }
    
    private static Method getCallbackMethod(final Callback a1) {
        return getCallbackMethod(findCallbackClass(a1.getClass()));
    }
    
    private static Method getCallbackMethod(final Class<?> v-3) {
        final Method[] declaredMethods = v-3.getDeclaredMethods();
        final Method[] methods = v-3.getMethods();
        final Set<Method> v0 = new HashSet<Method>(Arrays.asList(declaredMethods));
        v0.retainAll(Arrays.asList(methods));
        final Iterator<Method> v2 = v0.iterator();
        while (v2.hasNext()) {
            final Method a1 = v2.next();
            if (Callback.FORBIDDEN_NAMES.contains(a1.getName())) {
                v2.remove();
            }
        }
        final Method[] v3 = v0.toArray(new Method[v0.size()]);
        if (v3.length == 1) {
            return checkMethod(v3[0]);
        }
        for (int v4 = 0; v4 < v3.length; ++v4) {
            final Method v5 = v3[v4];
            if ("callback".equals(v5.getName())) {
                return checkMethod(v5);
            }
        }
        final String v6 = "Callback must implement a single public method, or one public method named 'callback'";
        throw new IllegalArgumentException(v6);
    }
    
    private void setCallbackOptions(final int a1) {
        this.cbstruct.setInt(Pointer.SIZE, a1);
    }
    
    public Pointer getTrampoline() {
        if (this.trampoline == null) {
            this.trampoline = this.cbstruct.getPointer(0L);
        }
        return this.trampoline;
    }
    
    @Override
    protected void finalize() {
        this.dispose();
    }
    
    protected synchronized void dispose() {
        if (this.cbstruct != null) {
            try {
                Native.freeNativeCallback(this.cbstruct.peer);
            }
            finally {
                this.cbstruct.peer = 0L;
                this.cbstruct = null;
                CallbackReference.allocatedMemory.remove(this);
            }
        }
    }
    
    static void disposeAll() {
        final Collection<CallbackReference> collection = new LinkedList<CallbackReference>(CallbackReference.allocatedMemory.keySet());
        for (final CallbackReference v1 : collection) {
            v1.dispose();
        }
    }
    
    private Callback getCallback() {
        return this.get();
    }
    
    private static Pointer getNativeFunctionPointer(final Callback v1) {
        if (Proxy.isProxyClass(v1.getClass())) {
            final Object a1 = Proxy.getInvocationHandler(v1);
            if (a1 instanceof NativeFunctionHandler) {
                return ((NativeFunctionHandler)a1).getPointer();
            }
        }
        return null;
    }
    
    public static Pointer getFunctionPointer(final Callback a1) {
        return getFunctionPointer(a1, false);
    }
    
    private static Pointer getFunctionPointer(final Callback a2, final boolean v1) {
        Pointer v2 = null;
        if (a2 == null) {
            return null;
        }
        if ((v2 = getNativeFunctionPointer(a2)) != null) {
            return v2;
        }
        final Map<String, ?> v3 = Native.getLibraryOptions(a2.getClass());
        final int v4 = (int)((a2 instanceof AltCallingConvention) ? 63 : ((v3 != null && v3.containsKey("calling-convention")) ? v3.get("calling-convention") : 0));
        final Map<Callback, CallbackReference> v5 = v1 ? CallbackReference.directCallbackMap : CallbackReference.callbackMap;
        synchronized (CallbackReference.pointerCallbackMap) {
            CallbackReference a3 = v5.get(a2);
            if (a3 == null) {
                a3 = new CallbackReference(a2, v4, v1);
                v5.put(a2, a3);
                CallbackReference.pointerCallbackMap.put(a3.getTrampoline(), new WeakReference<Callback>(a2));
                if (CallbackReference.initializers.containsKey(a2)) {
                    a3.setCallbackOptions(1);
                }
            }
            return a3.getTrampoline();
        }
    }
    
    private static boolean isAllowableNativeType(final Class<?> a1) {
        return a1 == Void.TYPE || a1 == Void.class || a1 == Boolean.TYPE || a1 == Boolean.class || a1 == Byte.TYPE || a1 == Byte.class || a1 == Short.TYPE || a1 == Short.class || a1 == Character.TYPE || a1 == Character.class || a1 == Integer.TYPE || a1 == Integer.class || a1 == Long.TYPE || a1 == Long.class || a1 == Float.TYPE || a1 == Float.class || a1 == Double.TYPE || a1 == Double.class || (Structure.ByValue.class.isAssignableFrom(a1) && Structure.class.isAssignableFrom(a1)) || Pointer.class.isAssignableFrom(a1);
    }
    
    private static Pointer getNativeString(final Object a2, final boolean v1) {
        if (a2 != null) {
            final NativeString a3 = new NativeString(a2.toString(), v1);
            CallbackReference.allocations.put(a2, a3);
            return a3.getPointer();
        }
        return null;
    }
    
    static /* bridge */ Callback access$000(final CallbackReference a1) {
        return a1.getCallback();
    }
    
    static /* bridge */ Pointer access$100(final Object a1, final boolean a2) {
        return getNativeString(a1, a2);
    }
    
    static {
        callbackMap = new WeakHashMap<Callback, CallbackReference>();
        directCallbackMap = new WeakHashMap<Callback, CallbackReference>();
        pointerCallbackMap = new WeakHashMap<Pointer, Reference<Callback>>();
        allocations = new WeakHashMap<Object, Object>();
        allocatedMemory = Collections.synchronizedMap(new WeakHashMap<CallbackReference, Reference<CallbackReference>>());
        try {
            PROXY_CALLBACK_METHOD = CallbackProxy.class.getMethod("callback", Object[].class);
        }
        catch (Exception v1) {
            throw new Error("Error looking up CallbackProxy.callback() method");
        }
        initializers = new WeakHashMap<Callback, CallbackThreadInitializer>();
    }
    
    static class AttachOptions extends Structure
    {
        public static final List<String> FIELDS;
        public boolean daemon;
        public boolean detach;
        public String name;
        
        AttachOptions() {
            super();
            this.setStringEncoding("utf8");
        }
        
        @Override
        protected List<String> getFieldOrder() {
            return AttachOptions.FIELDS;
        }
        
        static {
            FIELDS = Structure.createFieldsOrder("daemon", "detach", "name");
        }
    }
    
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
            return CallbackReference.this.getCallback();
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
                    return getNativeString(v2, v3 == WString.class);
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
    
    private static class NativeFunctionHandler implements InvocationHandler
    {
        private final Function function;
        private final Map<String, ?> options;
        
        public NativeFunctionHandler(final Pointer a1, final int a2, final Map<String, ?> a3) {
            super();
            this.options = a3;
            this.function = new Function(a1, a2, (String)a3.get("string-encoding"));
        }
        
        @Override
        public Object invoke(final Object v-2, final Method v-1, Object[] v0) throws Throwable {
            if (Library.Handler.OBJECT_TOSTRING.equals(v-1)) {
                String a1 = "Proxy interface to " + this.function;
                final Method a2 = (Method)this.options.get("invoking-method");
                final Class<?> a3 = CallbackReference.findCallbackClass(a2.getDeclaringClass());
                a1 = a1 + " (" + a3.getName() + ")";
                return a1;
            }
            if (Library.Handler.OBJECT_HASHCODE.equals(v-1)) {
                return this.hashCode();
            }
            if (!Library.Handler.OBJECT_EQUALS.equals(v-1)) {
                if (Function.isVarArgs(v-1)) {
                    v0 = Function.concatenateVarArgs(v0);
                }
                return this.function.invoke(v-1.getReturnType(), v0, this.options);
            }
            final Object v = v0[0];
            if (v != null && Proxy.isProxyClass(v.getClass())) {
                return Function.valueOf(Proxy.getInvocationHandler(v) == this);
            }
            return Boolean.FALSE;
        }
        
        public Pointer getPointer() {
            return this.function;
        }
    }
}
