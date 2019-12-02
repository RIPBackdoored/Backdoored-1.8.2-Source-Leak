package javassist.util.proxy;

import java.lang.ref.*;
import javassist.*;
import java.security.*;
import java.lang.reflect.*;
import java.util.*;
import javassist.bytecode.*;

public class ProxyFactory
{
    private Class superClass;
    private Class[] interfaces;
    private MethodFilter methodFilter;
    private MethodHandler handler;
    private List signatureMethods;
    private boolean hasGetHandler;
    private byte[] signature;
    private String classname;
    private String basename;
    private String superName;
    private Class thisClass;
    private boolean factoryUseCache;
    private boolean factoryWriteReplace;
    public String writeDirectory;
    private static final Class OBJECT_TYPE;
    private static final String HOLDER = "_methods_";
    private static final String HOLDER_TYPE = "[Ljava/lang/reflect/Method;";
    private static final String FILTER_SIGNATURE_FIELD = "_filter_signature";
    private static final String FILTER_SIGNATURE_TYPE = "[B";
    private static final String HANDLER = "handler";
    private static final String NULL_INTERCEPTOR_HOLDER = "javassist.util.proxy.RuntimeSupport";
    private static final String DEFAULT_INTERCEPTOR = "default_interceptor";
    private static final String HANDLER_TYPE;
    private static final String HANDLER_SETTER = "setHandler";
    private static final String HANDLER_SETTER_TYPE;
    private static final String HANDLER_GETTER = "getHandler";
    private static final String HANDLER_GETTER_TYPE;
    private static final String SERIAL_VERSION_UID_FIELD = "serialVersionUID";
    private static final String SERIAL_VERSION_UID_TYPE = "J";
    private static final long SERIAL_VERSION_UID_VALUE = -1L;
    public static volatile boolean useCache;
    public static volatile boolean useWriteReplace;
    private static WeakHashMap proxyCache;
    private static char[] hexDigits;
    public static ClassLoaderProvider classLoaderProvider;
    public static UniqueName nameGenerator;
    private static Comparator sorter;
    private static final String HANDLER_GETTER_KEY = "getHandler:()";
    
    public boolean isUseCache() {
        return this.factoryUseCache;
    }
    
    public void setUseCache(final boolean a1) {
        if (this.handler != null && a1) {
            throw new RuntimeException("caching cannot be enabled if the factory default interceptor has been set");
        }
        this.factoryUseCache = a1;
    }
    
    public boolean isUseWriteReplace() {
        return this.factoryWriteReplace;
    }
    
    public void setUseWriteReplace(final boolean a1) {
        this.factoryWriteReplace = a1;
    }
    
    public static boolean isProxyClass(final Class a1) {
        return Proxy.class.isAssignableFrom(a1);
    }
    
    public ProxyFactory() {
        super();
        this.superClass = null;
        this.interfaces = null;
        this.methodFilter = null;
        this.handler = null;
        this.signature = null;
        this.signatureMethods = null;
        this.hasGetHandler = false;
        this.thisClass = null;
        this.writeDirectory = null;
        this.factoryUseCache = ProxyFactory.useCache;
        this.factoryWriteReplace = ProxyFactory.useWriteReplace;
    }
    
    public void setSuperclass(final Class a1) {
        this.superClass = a1;
        this.signature = null;
    }
    
    public Class getSuperclass() {
        return this.superClass;
    }
    
    public void setInterfaces(final Class[] a1) {
        this.interfaces = a1;
        this.signature = null;
    }
    
    public Class[] getInterfaces() {
        return this.interfaces;
    }
    
    public void setFilter(final MethodFilter a1) {
        this.methodFilter = a1;
        this.signature = null;
    }
    
    public Class createClass() {
        if (this.signature == null) {
            this.computeSignature(this.methodFilter);
        }
        return this.createClass1();
    }
    
    public Class createClass(final MethodFilter a1) {
        this.computeSignature(a1);
        return this.createClass1();
    }
    
    Class createClass(final byte[] a1) {
        this.installSignature(a1);
        return this.createClass1();
    }
    
    private Class createClass1() {
        Class v0 = this.thisClass;
        if (v0 == null) {
            final ClassLoader v2 = this.getClassLoader();
            synchronized (ProxyFactory.proxyCache) {
                if (this.factoryUseCache) {
                    this.createClass2(v2);
                }
                else {
                    this.createClass3(v2);
                }
                v0 = this.thisClass;
                this.thisClass = null;
            }
        }
        return v0;
    }
    
    public String getKey(final Class v-4, final Class[] v-3, final byte[] v-2, final boolean v-1) {
        final StringBuffer v0 = new StringBuffer();
        if (v-4 != null) {
            v0.append(v-4.getName());
        }
        v0.append(":");
        for (int a1 = 0; a1 < v-3.length; ++a1) {
            v0.append(v-3[a1].getName());
            v0.append(":");
        }
        for (int v2 = 0; v2 < v-2.length; ++v2) {
            final byte a2 = v-2[v2];
            final int a3 = a2 & 0xF;
            final int a4 = a2 >> 4 & 0xF;
            v0.append(ProxyFactory.hexDigits[a3]);
            v0.append(ProxyFactory.hexDigits[a4]);
        }
        if (v-1) {
            v0.append(":w");
        }
        return v0.toString();
    }
    
    private void createClass2(final ClassLoader v2) {
        final String v3 = this.getKey(this.superClass, this.interfaces, this.signature, this.factoryWriteReplace);
        HashMap v4 = ProxyFactory.proxyCache.get(v2);
        if (v4 == null) {
            v4 = new HashMap();
            ProxyFactory.proxyCache.put(v2, v4);
        }
        ProxyDetails v5 = v4.get(v3);
        if (v5 != null) {
            final WeakReference a1 = v5.proxyClass;
            this.thisClass = (Class)a1.get();
            if (this.thisClass != null) {
                return;
            }
        }
        this.createClass3(v2);
        v5 = new ProxyDetails(this.signature, this.thisClass, this.factoryWriteReplace);
        v4.put(v3, v5);
    }
    
    private void createClass3(final ClassLoader v0) {
        this.allocateClassName();
        try {
            final ClassFile a1 = this.make();
            if (this.writeDirectory != null) {
                FactoryHelper.writeFile(a1, this.writeDirectory);
            }
            this.thisClass = FactoryHelper.toClass(a1, v0, this.getDomain());
            this.setField("_filter_signature", this.signature);
            if (!this.factoryUseCache) {
                this.setField("default_interceptor", this.handler);
            }
        }
        catch (CannotCompileException v) {
            throw new RuntimeException(v.getMessage(), v);
        }
    }
    
    private void setField(final String v2, final Object v3) {
        if (this.thisClass != null && v3 != null) {
            try {
                final Field a1 = this.thisClass.getField(v2);
                SecurityActions.setAccessible(a1, true);
                a1.set(null, v3);
                SecurityActions.setAccessible(a1, false);
            }
            catch (Exception a2) {
                throw new RuntimeException(a2);
            }
        }
    }
    
    static byte[] getFilterSignature(final Class a1) {
        return (byte[])getField(a1, "_filter_signature");
    }
    
    private static Object getField(final Class v-1, final String v0) {
        try {
            final Field a1 = v-1.getField(v0);
            a1.setAccessible(true);
            final Object a2 = a1.get(null);
            a1.setAccessible(false);
            return a2;
        }
        catch (Exception v) {
            throw new RuntimeException(v);
        }
    }
    
    public static MethodHandler getHandler(final Proxy v-1) {
        try {
            final Field a1 = v-1.getClass().getDeclaredField("handler");
            a1.setAccessible(true);
            final Object v1 = a1.get(v-1);
            a1.setAccessible(false);
            return (MethodHandler)v1;
        }
        catch (Exception v2) {
            throw new RuntimeException(v2);
        }
    }
    
    protected ClassLoader getClassLoader() {
        return ProxyFactory.classLoaderProvider.get(this);
    }
    
    protected ClassLoader getClassLoader0() {
        ClassLoader v1 = null;
        if (this.superClass != null && !this.superClass.getName().equals("java.lang.Object")) {
            v1 = this.superClass.getClassLoader();
        }
        else if (this.interfaces != null && this.interfaces.length > 0) {
            v1 = this.interfaces[0].getClassLoader();
        }
        if (v1 == null) {
            v1 = this.getClass().getClassLoader();
            if (v1 == null) {
                v1 = Thread.currentThread().getContextClassLoader();
                if (v1 == null) {
                    v1 = ClassLoader.getSystemClassLoader();
                }
            }
        }
        return v1;
    }
    
    protected ProtectionDomain getDomain() {
        Class v1;
        if (this.superClass != null && !this.superClass.getName().equals("java.lang.Object")) {
            v1 = this.superClass;
        }
        else if (this.interfaces != null && this.interfaces.length > 0) {
            v1 = this.interfaces[0];
        }
        else {
            v1 = this.getClass();
        }
        return v1.getProtectionDomain();
    }
    
    public Object create(final Class[] a1, final Object[] a2, final MethodHandler a3) throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final Object v1 = this.create(a1, a2);
        ((Proxy)v1).setHandler(a3);
        return v1;
    }
    
    public Object create(final Class[] a1, final Object[] a2) throws NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final Class v1 = this.createClass();
        final Constructor v2 = v1.getConstructor((Class[])a1);
        return v2.newInstance(a2);
    }
    
    @Deprecated
    public void setHandler(final MethodHandler a1) {
        if (this.factoryUseCache && a1 != null) {
            this.factoryUseCache = false;
            this.thisClass = null;
        }
        this.setField("default_interceptor", this.handler = a1);
    }
    
    private static String makeProxyName(final String a1) {
        synchronized (ProxyFactory.nameGenerator) {
            return ProxyFactory.nameGenerator.get(a1);
        }
    }
    
    private ClassFile make() throws CannotCompileException {
        final ClassFile classFile = new ClassFile(false, this.classname, this.superName);
        classFile.setAccessFlags(1);
        setInterfaces(classFile, this.interfaces, (Class)(this.hasGetHandler ? Proxy.class : ProxyObject.class));
        final ConstPool v0 = classFile.getConstPool();
        if (!this.factoryUseCache) {
            final FieldInfo v2 = new FieldInfo(v0, "default_interceptor", ProxyFactory.HANDLER_TYPE);
            v2.setAccessFlags(9);
            classFile.addField(v2);
        }
        final FieldInfo v2 = new FieldInfo(v0, "handler", ProxyFactory.HANDLER_TYPE);
        v2.setAccessFlags(2);
        classFile.addField(v2);
        final FieldInfo v3 = new FieldInfo(v0, "_filter_signature", "[B");
        v3.setAccessFlags(9);
        classFile.addField(v3);
        final FieldInfo v4 = new FieldInfo(v0, "serialVersionUID", "J");
        v4.setAccessFlags(25);
        classFile.addField(v4);
        this.makeConstructors(this.classname, classFile, v0, this.classname);
        final ArrayList v5 = new ArrayList();
        final int v6 = this.overrideMethods(classFile, v0, this.classname, v5);
        addClassInitializer(classFile, v0, this.classname, v6, v5);
        addSetter(this.classname, classFile, v0);
        if (!this.hasGetHandler) {
            addGetter(this.classname, classFile, v0);
        }
        if (this.factoryWriteReplace) {
            try {
                classFile.addMethod(makeWriteReplace(v0));
            }
            catch (DuplicateMemberException ex) {}
        }
        this.thisClass = null;
        return classFile;
    }
    
    private void checkClassAndSuperName() {
        if (this.interfaces == null) {
            this.interfaces = new Class[0];
        }
        if (this.superClass == null) {
            this.superClass = ProxyFactory.OBJECT_TYPE;
            this.superName = this.superClass.getName();
            this.basename = ((this.interfaces.length == 0) ? this.superName : this.interfaces[0].getName());
        }
        else {
            this.superName = this.superClass.getName();
            this.basename = this.superName;
        }
        if (Modifier.isFinal(this.superClass.getModifiers())) {
            throw new RuntimeException(this.superName + " is final");
        }
        if (this.basename.startsWith("java.")) {
            this.basename = "org.javassist.tmp." + this.basename;
        }
    }
    
    private void allocateClassName() {
        this.classname = makeProxyName(this.basename);
    }
    
    private void makeSortedMethodList() {
        this.checkClassAndSuperName();
        this.hasGetHandler = false;
        final HashMap v1 = this.getMethods(this.superClass, this.interfaces);
        Collections.sort((List<Object>)(this.signatureMethods = new ArrayList(v1.entrySet())), ProxyFactory.sorter);
    }
    
    private void computeSignature(final MethodFilter v-4) {
        this.makeSortedMethodList();
        final int size = this.signatureMethods.size();
        final int n = size + 7 >> 3;
        this.signature = new byte[n];
        for (int i = 0; i < size; ++i) {
            final Map.Entry a1 = this.signatureMethods.get(i);
            final Method v1 = a1.getValue();
            final int v2 = v1.getModifiers();
            if (!Modifier.isFinal(v2) && !Modifier.isStatic(v2) && isVisible(v2, this.basename, v1) && (v-4 == null || v-4.isHandled(v1))) {
                this.setBit(this.signature, i);
            }
        }
    }
    
    private void installSignature(final byte[] a1) {
        this.makeSortedMethodList();
        final int v1 = this.signatureMethods.size();
        final int v2 = v1 + 7 >> 3;
        if (a1.length != v2) {
            throw new RuntimeException("invalid filter signature length for deserialized proxy class");
        }
        this.signature = a1;
    }
    
    private boolean testBit(final byte[] v-4, final int v-3) {
        final int n = v-3 >> 3;
        if (n > v-4.length) {
            return false;
        }
        final int a1 = v-3 & 0x7;
        final int a2 = 1 << a1;
        final int v1 = v-4[n];
        return (v1 & a2) != 0x0;
    }
    
    private void setBit(final byte[] v-4, final int v-3) {
        final int n = v-3 >> 3;
        if (n < v-4.length) {
            final int a1 = v-3 & 0x7;
            final int a2 = 1 << a1;
            final int v1 = v-4[n];
            v-4[n] = (byte)(v1 | a2);
        }
    }
    
    private static void setInterfaces(final ClassFile a3, final Class[] v1, final Class v2) {
        final String v3 = v2.getName();
        String[] v4 = null;
        if (v1 == null || v1.length == 0) {
            final String[] a4 = { v3 };
        }
        else {
            v4 = new String[v1.length + 1];
            for (int a5 = 0; a5 < v1.length; ++a5) {
                v4[a5] = v1[a5].getName();
            }
            v4[v1.length] = v3;
        }
        a3.setInterfaces(v4);
    }
    
    private static void addClassInitializer(final ClassFile a2, final ConstPool a3, final String a4, final int a5, final ArrayList v1) throws CannotCompileException {
        final FieldInfo v2 = new FieldInfo(a3, "_methods_", "[Ljava/lang/reflect/Method;");
        v2.setAccessFlags(10);
        a2.addField(v2);
        final MethodInfo v3 = new MethodInfo(a3, "<clinit>", "()V");
        v3.setAccessFlags(8);
        setThrows(v3, a3, new Class[] { ClassNotFoundException.class });
        final Bytecode v4 = new Bytecode(a3, 0, 2);
        v4.addIconst(a5 * 2);
        v4.addAnewarray("java.lang.reflect.Method");
        final int v5 = 0;
        v4.addAstore(0);
        v4.addLdc(a4);
        v4.addInvokestatic("java.lang.Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
        final int v6 = 1;
        v4.addAstore(1);
        for (final Find2MethodsArgs a6 : v1) {
            callFind2Methods(v4, a6.methodName, a6.delegatorName, a6.origIndex, a6.descriptor, 1, 0);
        }
        v4.addAload(0);
        v4.addPutstatic(a4, "_methods_", "[Ljava/lang/reflect/Method;");
        v4.addLconst(-1L);
        v4.addPutstatic(a4, "serialVersionUID", "J");
        v4.addOpcode(177);
        v3.setCodeAttribute(v4.toCodeAttribute());
        a2.addMethod(v3);
    }
    
    private static void callFind2Methods(final Bytecode a1, final String a2, final String a3, final int a4, final String a5, final int a6, final int a7) {
        final String v1 = RuntimeSupport.class.getName();
        final String v2 = "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;[Ljava/lang/reflect/Method;)V";
        a1.addAload(a6);
        a1.addLdc(a2);
        if (a3 == null) {
            a1.addOpcode(1);
        }
        else {
            a1.addLdc(a3);
        }
        a1.addIconst(a4);
        a1.addLdc(a5);
        a1.addAload(a7);
        a1.addInvokestatic(v1, "find2Methods", v2);
    }
    
    private static void addSetter(final String a1, final ClassFile a2, final ConstPool a3) throws CannotCompileException {
        final MethodInfo v1 = new MethodInfo(a3, "setHandler", ProxyFactory.HANDLER_SETTER_TYPE);
        v1.setAccessFlags(1);
        final Bytecode v2 = new Bytecode(a3, 2, 2);
        v2.addAload(0);
        v2.addAload(1);
        v2.addPutfield(a1, "handler", ProxyFactory.HANDLER_TYPE);
        v2.addOpcode(177);
        v1.setCodeAttribute(v2.toCodeAttribute());
        a2.addMethod(v1);
    }
    
    private static void addGetter(final String a1, final ClassFile a2, final ConstPool a3) throws CannotCompileException {
        final MethodInfo v1 = new MethodInfo(a3, "getHandler", ProxyFactory.HANDLER_GETTER_TYPE);
        v1.setAccessFlags(1);
        final Bytecode v2 = new Bytecode(a3, 1, 1);
        v2.addAload(0);
        v2.addGetfield(a1, "handler", ProxyFactory.HANDLER_TYPE);
        v2.addOpcode(176);
        v1.setCodeAttribute(v2.toCodeAttribute());
        a2.addMethod(v1);
    }
    
    private int overrideMethods(final ClassFile v1, final ConstPool v2, final String v3, final ArrayList v4) throws CannotCompileException {
        final String v5 = makeUniqueName("_d", this.signatureMethods);
        final Iterator v6 = this.signatureMethods.iterator();
        int v7 = 0;
        while (v6.hasNext()) {
            final Map.Entry a1 = v6.next();
            final String a2 = a1.getKey();
            final Method a3 = a1.getValue();
            if ((ClassFile.MAJOR_VERSION < 49 || !isBridge(a3)) && this.testBit(this.signature, v7)) {
                this.override(v3, a3, v5, v7, keyToDesc(a2, a3), v1, v2, v4);
            }
            ++v7;
        }
        return v7;
    }
    
    private static boolean isBridge(final Method a1) {
        return a1.isBridge();
    }
    
    private void override(final String a3, final Method a4, final String a5, final int a6, final String a7, final ClassFile a8, final ConstPool v1, final ArrayList v2) throws CannotCompileException {
        final Class v3 = a4.getDeclaringClass();
        String v4 = a5 + a6 + a4.getName();
        if (Modifier.isAbstract(a4.getModifiers())) {
            v4 = null;
        }
        else {
            final MethodInfo a9 = this.makeDelegator(a4, a7, v1, v3, v4);
            a9.setAccessFlags(a9.getAccessFlags() & 0xFFFFFFBF);
            a8.addMethod(a9);
        }
        final MethodInfo v5 = makeForwarder(a3, a4, a7, v1, v3, v4, a6, v2);
        a8.addMethod(v5);
    }
    
    private void makeConstructors(final String v2, final ClassFile v3, final ConstPool v4, final String v5) throws CannotCompileException {
        final Constructor[] v6 = SecurityActions.getDeclaredConstructors(this.superClass);
        final boolean v7 = !this.factoryUseCache;
        for (int a4 = 0; a4 < v6.length; ++a4) {
            final Constructor a5 = v6[a4];
            final int a6 = a5.getModifiers();
            if (!Modifier.isFinal(a6) && !Modifier.isPrivate(a6) && isVisible(a6, this.basename, a5)) {
                final MethodInfo a7 = makeConstructor(v2, a5, v4, this.superClass, v7);
                v3.addMethod(a7);
            }
        }
    }
    
    private static String makeUniqueName(final String v1, final List v2) {
        if (makeUniqueName0(v1, v2.iterator())) {
            return v1;
        }
        for (int a2 = 100; a2 < 999; ++a2) {
            final String a3 = v1 + a2;
            if (makeUniqueName0(a3, v2.iterator())) {
                return a3;
            }
        }
        throw new RuntimeException("cannot make a unique method name");
    }
    
    private static boolean makeUniqueName0(final String v1, final Iterator v2) {
        while (v2.hasNext()) {
            final Map.Entry a1 = v2.next();
            final String a2 = a1.getKey();
            if (a2.startsWith(v1)) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isVisible(final int a3, final String v1, final Member v2) {
        if ((a3 & 0x2) != 0x0) {
            return false;
        }
        if ((a3 & 0x5) != 0x0) {
            return true;
        }
        final String a4 = getPackageName(v1);
        final String a5 = getPackageName(v2.getDeclaringClass().getName());
        if (a4 == null) {
            return a5 == null;
        }
        return a4.equals(a5);
    }
    
    private static String getPackageName(final String a1) {
        final int v1 = a1.lastIndexOf(46);
        if (v1 < 0) {
            return null;
        }
        return a1.substring(0, v1);
    }
    
    private HashMap getMethods(final Class v1, final Class[] v2) {
        final HashMap v3 = new HashMap();
        final HashSet v4 = new HashSet();
        for (int a1 = 0; a1 < v2.length; ++a1) {
            this.getMethods(v3, v2[a1], v4);
        }
        this.getMethods(v3, v1, v4);
        return v3;
    }
    
    private void getMethods(final HashMap v-8, final Class v-7, final Set v-6) {
        if (!v-6.add(v-7)) {
            return;
        }
        final Class[] interfaces = v-7.getInterfaces();
        for (int a1 = 0; a1 < interfaces.length; ++a1) {
            this.getMethods(v-8, interfaces[a1], v-6);
        }
        final Class superclass = v-7.getSuperclass();
        if (superclass != null) {
            this.getMethods(v-8, superclass, v-6);
        }
        final Method[] declaredMethods = SecurityActions.getDeclaredMethods(v-7);
        for (int i = 0; i < declaredMethods.length; ++i) {
            if (!Modifier.isPrivate(declaredMethods[i].getModifiers())) {
                final Method a2 = declaredMethods[i];
                final String a3 = a2.getName() + ':' + RuntimeSupport.makeDescriptor(a2);
                if (a3.startsWith("getHandler:()")) {
                    this.hasGetHandler = true;
                }
                final Method v1 = v-8.put(a3, a2);
                if (null != v1 && isBridge(a2) && !Modifier.isPublic(v1.getDeclaringClass().getModifiers()) && !Modifier.isAbstract(v1.getModifiers()) && !isOverloaded(i, declaredMethods)) {
                    v-8.put(a3, v1);
                }
                if (null != v1 && Modifier.isPublic(v1.getModifiers()) && !Modifier.isPublic(a2.getModifiers())) {
                    v-8.put(a3, v1);
                }
            }
        }
    }
    
    private static boolean isOverloaded(final int a2, final Method[] v1) {
        final String v2 = v1[a2].getName();
        for (int a3 = 0; a3 < v1.length; ++a3) {
            if (a3 != a2 && v2.equals(v1[a3].getName())) {
                return true;
            }
        }
        return false;
    }
    
    private static String keyToDesc(final String a1, final Method a2) {
        return a1.substring(a1.indexOf(58) + 1);
    }
    
    private static MethodInfo makeConstructor(final String a1, final Constructor a2, final ConstPool a3, final Class a4, final boolean a5) {
        final String v1 = RuntimeSupport.makeDescriptor(a2.getParameterTypes(), Void.TYPE);
        final MethodInfo v2 = new MethodInfo(a3, "<init>", v1);
        v2.setAccessFlags(1);
        setThrows(v2, a3, a2.getExceptionTypes());
        final Bytecode v3 = new Bytecode(a3, 0, 0);
        if (a5) {
            v3.addAload(0);
            v3.addGetstatic(a1, "default_interceptor", ProxyFactory.HANDLER_TYPE);
            v3.addPutfield(a1, "handler", ProxyFactory.HANDLER_TYPE);
            v3.addGetstatic(a1, "default_interceptor", ProxyFactory.HANDLER_TYPE);
            v3.addOpcode(199);
            v3.addIndex(10);
        }
        v3.addAload(0);
        v3.addGetstatic("javassist.util.proxy.RuntimeSupport", "default_interceptor", ProxyFactory.HANDLER_TYPE);
        v3.addPutfield(a1, "handler", ProxyFactory.HANDLER_TYPE);
        final int v4 = v3.currentPc();
        v3.addAload(0);
        final int v5 = addLoadParameters(v3, a2.getParameterTypes(), 1);
        v3.addInvokespecial(a4.getName(), "<init>", v1);
        v3.addOpcode(177);
        v3.setMaxLocals(v5 + 1);
        final CodeAttribute v6 = v3.toCodeAttribute();
        v2.setCodeAttribute(v6);
        final StackMapTable.Writer v7 = new StackMapTable.Writer(32);
        v7.sameFrame(v4);
        v6.setAttribute(v7.toStackMapTable(a3));
        return v2;
    }
    
    private MethodInfo makeDelegator(final Method a1, final String a2, final ConstPool a3, final Class a4, final String a5) {
        final MethodInfo v1 = new MethodInfo(a3, a5, a2);
        v1.setAccessFlags(0x11 | (a1.getModifiers() & 0xFFFFFAD9));
        setThrows(v1, a3, a1);
        final Bytecode v2 = new Bytecode(a3, 0, 0);
        v2.addAload(0);
        int v3 = addLoadParameters(v2, a1.getParameterTypes(), 1);
        final Class v4 = this.invokespecialTarget(a4);
        v2.addInvokespecial(v4.isInterface(), a3.addClassInfo(v4.getName()), a1.getName(), a2);
        addReturn(v2, a1.getReturnType());
        v2.setMaxLocals(++v3);
        v1.setCodeAttribute(v2.toCodeAttribute());
        return v1;
    }
    
    private Class invokespecialTarget(final Class v2) {
        if (v2.isInterface()) {
            for (final Class a1 : this.interfaces) {
                if (v2.isAssignableFrom(a1)) {
                    return a1;
                }
            }
        }
        return this.superClass;
    }
    
    private static MethodInfo makeForwarder(final String a1, final Method a2, final String a3, final ConstPool a4, final Class a5, final String a6, final int a7, final ArrayList a8) {
        final MethodInfo v1 = new MethodInfo(a4, a2.getName(), a3);
        v1.setAccessFlags(0x10 | (a2.getModifiers() & 0xFFFFFADF));
        setThrows(v1, a4, a2);
        final int v2 = Descriptor.paramSize(a3);
        final Bytecode v3 = new Bytecode(a4, 0, v2 + 2);
        final int v4 = a7 * 2;
        final int v5 = a7 * 2 + 1;
        final int v6 = v2 + 1;
        v3.addGetstatic(a1, "_methods_", "[Ljava/lang/reflect/Method;");
        v3.addAstore(v6);
        a8.add(new Find2MethodsArgs(a2.getName(), a6, a3, v4));
        v3.addAload(0);
        v3.addGetfield(a1, "handler", ProxyFactory.HANDLER_TYPE);
        v3.addAload(0);
        v3.addAload(v6);
        v3.addIconst(v4);
        v3.addOpcode(50);
        v3.addAload(v6);
        v3.addIconst(v5);
        v3.addOpcode(50);
        makeParameterList(v3, a2.getParameterTypes());
        v3.addInvokeinterface(MethodHandler.class.getName(), "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", 5);
        final Class v7 = a2.getReturnType();
        addUnwrapper(v3, v7);
        addReturn(v3, v7);
        final CodeAttribute v8 = v3.toCodeAttribute();
        v1.setCodeAttribute(v8);
        return v1;
    }
    
    private static void setThrows(final MethodInfo a1, final ConstPool a2, final Method a3) {
        final Class[] v1 = a3.getExceptionTypes();
        setThrows(a1, a2, v1);
    }
    
    private static void setThrows(final MethodInfo a2, final ConstPool a3, final Class[] v1) {
        if (v1.length == 0) {
            return;
        }
        final String[] v2 = new String[v1.length];
        for (int a4 = 0; a4 < v1.length; ++a4) {
            v2[a4] = v1[a4].getName();
        }
        final ExceptionsAttribute v3 = new ExceptionsAttribute(a3);
        v3.setExceptions(v2);
        a2.setExceptionsAttribute(v3);
    }
    
    private static int addLoadParameters(final Bytecode a2, final Class[] a3, final int v1) {
        int v2 = 0;
        for (int v3 = a3.length, a4 = 0; a4 < v3; ++a4) {
            v2 += addLoad(a2, v2 + v1, a3[a4]);
        }
        return v2;
    }
    
    private static int addLoad(final Bytecode a1, final int a2, final Class a3) {
        if (a3.isPrimitive()) {
            if (a3 == Long.TYPE) {
                a1.addLload(a2);
                return 2;
            }
            if (a3 == Float.TYPE) {
                a1.addFload(a2);
            }
            else {
                if (a3 == Double.TYPE) {
                    a1.addDload(a2);
                    return 2;
                }
                a1.addIload(a2);
            }
        }
        else {
            a1.addAload(a2);
        }
        return 1;
    }
    
    private static int addReturn(final Bytecode a1, final Class a2) {
        if (a2.isPrimitive()) {
            if (a2 == Long.TYPE) {
                a1.addOpcode(173);
                return 2;
            }
            if (a2 == Float.TYPE) {
                a1.addOpcode(174);
            }
            else {
                if (a2 == Double.TYPE) {
                    a1.addOpcode(175);
                    return 2;
                }
                if (a2 == Void.TYPE) {
                    a1.addOpcode(177);
                    return 0;
                }
                a1.addOpcode(172);
            }
        }
        else {
            a1.addOpcode(176);
        }
        return 1;
    }
    
    private static void makeParameterList(final Bytecode v1, final Class[] v2) {
        int v3 = 1;
        final int v4 = v2.length;
        v1.addIconst(v4);
        v1.addAnewarray("java/lang/Object");
        for (int a2 = 0; a2 < v4; ++a2) {
            v1.addOpcode(89);
            v1.addIconst(a2);
            final Class a3 = v2[a2];
            if (a3.isPrimitive()) {
                v3 = makeWrapper(v1, a3, v3);
            }
            else {
                v1.addAload(v3);
                ++v3;
            }
            v1.addOpcode(83);
        }
    }
    
    private static int makeWrapper(final Bytecode a1, final Class a2, final int a3) {
        final int v1 = FactoryHelper.typeIndex(a2);
        final String v2 = FactoryHelper.wrapperTypes[v1];
        a1.addNew(v2);
        a1.addOpcode(89);
        addLoad(a1, a3, a2);
        a1.addInvokespecial(v2, "<init>", FactoryHelper.wrapperDesc[v1]);
        return a3 + FactoryHelper.dataSize[v1];
    }
    
    private static void addUnwrapper(final Bytecode v1, final Class v2) {
        if (v2.isPrimitive()) {
            if (v2 == Void.TYPE) {
                v1.addOpcode(87);
            }
            else {
                final int a1 = FactoryHelper.typeIndex(v2);
                final String a2 = FactoryHelper.wrapperTypes[a1];
                v1.addCheckcast(a2);
                v1.addInvokevirtual(a2, FactoryHelper.unwarpMethods[a1], FactoryHelper.unwrapDesc[a1]);
            }
        }
        else {
            v1.addCheckcast(v2.getName());
        }
    }
    
    private static MethodInfo makeWriteReplace(final ConstPool a1) {
        final MethodInfo v1 = new MethodInfo(a1, "writeReplace", "()Ljava/lang/Object;");
        final String[] v2 = { "java.io.ObjectStreamException" };
        final ExceptionsAttribute v3 = new ExceptionsAttribute(a1);
        v3.setExceptions(v2);
        v1.setExceptionsAttribute(v3);
        final Bytecode v4 = new Bytecode(a1, 0, 1);
        v4.addAload(0);
        v4.addInvokestatic("javassist.util.proxy.RuntimeSupport", "makeSerializedProxy", "(Ljava/lang/Object;)Ljavassist/util/proxy/SerializedProxy;");
        v4.addOpcode(176);
        v1.setCodeAttribute(v4.toCodeAttribute());
        return v1;
    }
    
    static {
        OBJECT_TYPE = Object.class;
        HANDLER_TYPE = 'L' + MethodHandler.class.getName().replace('.', '/') + ';';
        HANDLER_SETTER_TYPE = "(" + ProxyFactory.HANDLER_TYPE + ")V";
        HANDLER_GETTER_TYPE = "()" + ProxyFactory.HANDLER_TYPE;
        ProxyFactory.useCache = true;
        ProxyFactory.useWriteReplace = true;
        ProxyFactory.proxyCache = new WeakHashMap();
        ProxyFactory.hexDigits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        ProxyFactory.classLoaderProvider = new ClassLoaderProvider() {
            ProxyFactory$1() {
                super();
            }
            
            @Override
            public ClassLoader get(final ProxyFactory a1) {
                return a1.getClassLoader0();
            }
        };
        ProxyFactory.nameGenerator = new UniqueName() {
            private final String sep = "_$$_jvst" + Integer.toHexString(this.hashCode() & 0xFFF) + "_";
            private int counter = 0;
            
            ProxyFactory$2() {
                super();
            }
            
            @Override
            public String get(final String a1) {
                return a1 + this.sep + Integer.toHexString(this.counter++);
            }
        };
        ProxyFactory.sorter = new Comparator() {
            ProxyFactory$3() {
                super();
            }
            
            @Override
            public int compare(final Object a1, final Object a2) {
                final Map.Entry v1 = (Map.Entry)a1;
                final Map.Entry v2 = (Map.Entry)a2;
                final String v3 = v1.getKey();
                final String v4 = v2.getKey();
                return v3.compareTo(v4);
            }
        };
    }
    
    static class ProxyDetails
    {
        byte[] signature;
        WeakReference proxyClass;
        boolean isUseWriteReplace;
        
        ProxyDetails(final byte[] a1, final Class a2, final boolean a3) {
            super();
            this.signature = a1;
            this.proxyClass = new WeakReference((T)a2);
            this.isUseWriteReplace = a3;
        }
    }
    
    static class Find2MethodsArgs
    {
        String methodName;
        String delegatorName;
        String descriptor;
        int origIndex;
        
        Find2MethodsArgs(final String a1, final String a2, final String a3, final int a4) {
            super();
            this.methodName = a1;
            this.delegatorName = a2;
            this.descriptor = a3;
            this.origIndex = a4;
        }
    }
    
    public interface UniqueName
    {
        String get(final String p0);
    }
    
    public interface ClassLoaderProvider
    {
        ClassLoader get(final ProxyFactory p0);
    }
}
