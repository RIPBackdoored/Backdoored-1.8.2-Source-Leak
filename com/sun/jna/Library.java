package com.sun.jna;

import java.util.*;
import java.lang.reflect.*;

public interface Library
{
    public static final String OPTION_TYPE_MAPPER = "type-mapper";
    public static final String OPTION_FUNCTION_MAPPER = "function-mapper";
    public static final String OPTION_INVOCATION_MAPPER = "invocation-mapper";
    public static final String OPTION_STRUCTURE_ALIGNMENT = "structure-alignment";
    public static final String OPTION_STRING_ENCODING = "string-encoding";
    public static final String OPTION_ALLOW_OBJECTS = "allow-objects";
    public static final String OPTION_CALLING_CONVENTION = "calling-convention";
    public static final String OPTION_OPEN_FLAGS = "open-flags";
    public static final String OPTION_CLASSLOADER = "classloader";
    
    public static class Handler implements InvocationHandler
    {
        static final Method OBJECT_TOSTRING;
        static final Method OBJECT_HASHCODE;
        static final Method OBJECT_EQUALS;
        private final NativeLibrary nativeLibrary;
        private final Class<?> interfaceClass;
        private final Map<String, Object> options;
        private final InvocationMapper invocationMapper;
        private final Map<Method, FunctionInfo> functions;
        
        public Handler(final String a1, final Class<?> a2, final Map<String, ?> a3) {
            super();
            this.functions = new WeakHashMap<Method, FunctionInfo>();
            if (a1 != null && "".equals(a1.trim())) {
                throw new IllegalArgumentException("Invalid library name \"" + a1 + "\"");
            }
            if (!a2.isInterface()) {
                throw new IllegalArgumentException(a1 + " does not implement an interface: " + a2.getName());
            }
            this.interfaceClass = a2;
            this.options = new HashMap<String, Object>(a3);
            final int v1 = AltCallingConvention.class.isAssignableFrom(a2) ? 63 : 0;
            if (this.options.get("calling-convention") == null) {
                this.options.put("calling-convention", v1);
            }
            if (this.options.get("classloader") == null) {
                this.options.put("classloader", a2.getClassLoader());
            }
            this.nativeLibrary = NativeLibrary.getInstance(a1, this.options);
            this.invocationMapper = this.options.get("invocation-mapper");
        }
        
        public NativeLibrary getNativeLibrary() {
            return this.nativeLibrary;
        }
        
        public String getLibraryName() {
            return this.nativeLibrary.getName();
        }
        
        public Class<?> getInterfaceClass() {
            return this.interfaceClass;
        }
        
        @Override
        public Object invoke(final Object v-6, final Method v-5, Object[] v-4) throws Throwable {
            if (Handler.OBJECT_TOSTRING.equals(v-5)) {
                return "Proxy interface to " + this.nativeLibrary;
            }
            if (Handler.OBJECT_HASHCODE.equals(v-5)) {
                return this.hashCode();
            }
            if (Handler.OBJECT_EQUALS.equals(v-5)) {
                final Object a1 = v-4[0];
                if (a1 != null && Proxy.isProxyClass(a1.getClass())) {
                    return Function.valueOf(Proxy.getInvocationHandler(a1) == this);
                }
                return Boolean.FALSE;
            }
            else {
                FunctionInfo functionInfo = this.functions.get(v-5);
                if (functionInfo == null) {
                    synchronized (this.functions) {
                        functionInfo = this.functions.get(v-5);
                        if (functionInfo == null) {
                            final boolean a2 = Function.isVarArgs(v-5);
                            InvocationHandler a3 = null;
                            if (this.invocationMapper != null) {
                                a3 = this.invocationMapper.getInvocationHandler(this.nativeLibrary, v-5);
                            }
                            Function v1 = null;
                            Class<?>[] v2 = null;
                            Map<String, Object> v3 = null;
                            if (a3 == null) {
                                v1 = this.nativeLibrary.getFunction(v-5.getName(), v-5);
                                v2 = v-5.getParameterTypes();
                                v3 = new HashMap<String, Object>(this.options);
                                v3.put("invoking-method", v-5);
                            }
                            functionInfo = new FunctionInfo(a3, v1, v2, a2, v3);
                            this.functions.put(v-5, functionInfo);
                        }
                    }
                }
                if (functionInfo.isVarArgs) {
                    v-4 = Function.concatenateVarArgs(v-4);
                }
                if (functionInfo.handler != null) {
                    return functionInfo.handler.invoke(v-6, v-5, v-4);
                }
                return functionInfo.function.invoke(v-5, functionInfo.parameterTypes, v-5.getReturnType(), v-4, functionInfo.options);
            }
        }
        
        static {
            try {
                OBJECT_TOSTRING = Object.class.getMethod("toString", (Class<?>[])new Class[0]);
                OBJECT_HASHCODE = Object.class.getMethod("hashCode", (Class<?>[])new Class[0]);
                OBJECT_EQUALS = Object.class.getMethod("equals", Object.class);
            }
            catch (Exception v1) {
                throw new Error("Error retrieving Object.toString() method");
            }
        }
        
        private static final class FunctionInfo
        {
            final InvocationHandler handler;
            final Function function;
            final boolean isVarArgs;
            final Map<String, ?> options;
            final Class<?>[] parameterTypes;
            
            FunctionInfo(final InvocationHandler a1, final Function a2, final Class<?>[] a3, final boolean a4, final Map<String, ?> a5) {
                super();
                this.handler = a1;
                this.function = a2;
                this.isVarArgs = a4;
                this.options = a5;
                this.parameterTypes = a3;
            }
        }
    }
}
