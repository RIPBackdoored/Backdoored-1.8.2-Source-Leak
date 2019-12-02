package javassist;

import java.util.*;
import java.net.*;
import javassist.bytecode.*;
import java.io.*;
import java.lang.reflect.*;
import java.security.*;

public class ClassPool
{
    private static Method defineClass1;
    private static Method defineClass2;
    private static Method definePackage;
    public boolean childFirstLookup;
    public static boolean doPruning;
    private int compressCount;
    private static final int COMPRESS_THRESHOLD = 100;
    public static boolean releaseUnmodifiedClassFile;
    protected ClassPoolTail source;
    protected ClassPool parent;
    protected Hashtable classes;
    private Hashtable cflow;
    private static final int INIT_HASH_SIZE = 191;
    private ArrayList importedPackages;
    private static ClassPool defaultPool;
    
    public ClassPool() {
        this(null);
    }
    
    public ClassPool(final boolean a1) {
        this(null);
        if (a1) {
            this.appendSystemPath();
        }
    }
    
    public ClassPool(final ClassPool v0) {
        super();
        this.childFirstLookup = false;
        this.cflow = null;
        this.classes = new Hashtable(191);
        this.source = new ClassPoolTail();
        this.parent = v0;
        if (v0 == null) {
            final CtClass[] v = CtClass.primitiveTypes;
            for (int a1 = 0; a1 < v.length; ++a1) {
                this.classes.put(v[a1].getName(), v[a1]);
            }
        }
        this.cflow = null;
        this.compressCount = 0;
        this.clearImportedPackages();
    }
    
    public static synchronized ClassPool getDefault() {
        if (ClassPool.defaultPool == null) {
            (ClassPool.defaultPool = new ClassPool(null)).appendSystemPath();
        }
        return ClassPool.defaultPool;
    }
    
    protected CtClass getCached(final String a1) {
        return this.classes.get(a1);
    }
    
    protected void cacheCtClass(final String a1, final CtClass a2, final boolean a3) {
        this.classes.put(a1, a2);
    }
    
    protected CtClass removeCached(final String a1) {
        return this.classes.remove(a1);
    }
    
    @Override
    public String toString() {
        return this.source.toString();
    }
    
    void compress() {
        if (this.compressCount++ > 100) {
            this.compressCount = 0;
            final Enumeration v1 = this.classes.elements();
            while (v1.hasMoreElements()) {
                v1.nextElement().compress();
            }
        }
    }
    
    public void importPackage(final String a1) {
        this.importedPackages.add(a1);
    }
    
    public void clearImportedPackages() {
        (this.importedPackages = new ArrayList()).add("java.lang");
    }
    
    public Iterator getImportedPackages() {
        return this.importedPackages.iterator();
    }
    
    @Deprecated
    public void recordInvalidClassName(final String a1) {
    }
    
    void recordCflow(final String a1, final String a2, final String a3) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        this.cflow.put(a1, new Object[] { a2, a3 });
    }
    
    public Object[] lookupCflow(final String a1) {
        if (this.cflow == null) {
            this.cflow = new Hashtable();
        }
        return this.cflow.get(a1);
    }
    
    public CtClass getAndRename(final String a1, final String a2) throws NotFoundException {
        final CtClass v1 = this.get0(a1, false);
        if (v1 == null) {
            throw new NotFoundException(a1);
        }
        if (v1 instanceof CtClassType) {
            ((CtClassType)v1).setClassPool(this);
        }
        v1.setName(a2);
        return v1;
    }
    
    synchronized void classNameChanged(final String a1, final CtClass a2) {
        final CtClass v1 = this.getCached(a1);
        if (v1 == a2) {
            this.removeCached(a1);
        }
        final String v2 = a2.getName();
        this.checkNotFrozen(v2);
        this.cacheCtClass(v2, a2, false);
    }
    
    public CtClass get(final String v2) throws NotFoundException {
        CtClass v3 = null;
        if (v2 == null) {
            final CtClass a1 = null;
        }
        else {
            v3 = this.get0(v2, true);
        }
        if (v3 == null) {
            throw new NotFoundException(v2);
        }
        v3.incGetCounter();
        return v3;
    }
    
    public CtClass getOrNull(final String a1) {
        CtClass v1 = null;
        if (a1 == null) {
            v1 = null;
        }
        else {
            try {
                v1 = this.get0(a1, true);
            }
            catch (NotFoundException ex) {}
        }
        if (v1 != null) {
            v1.incGetCounter();
        }
        return v1;
    }
    
    public CtClass getCtClass(final String a1) throws NotFoundException {
        if (a1.charAt(0) == '[') {
            return Descriptor.toCtClass(a1, this);
        }
        return this.get(a1);
    }
    
    protected synchronized CtClass get0(final String a1, final boolean a2) throws NotFoundException {
        CtClass v1 = null;
        if (a2) {
            v1 = this.getCached(a1);
            if (v1 != null) {
                return v1;
            }
        }
        if (!this.childFirstLookup && this.parent != null) {
            v1 = this.parent.get0(a1, a2);
            if (v1 != null) {
                return v1;
            }
        }
        v1 = this.createCtClass(a1, a2);
        if (v1 != null) {
            if (a2) {
                this.cacheCtClass(v1.getName(), v1, false);
            }
            return v1;
        }
        if (this.childFirstLookup && this.parent != null) {
            v1 = this.parent.get0(a1, a2);
        }
        return v1;
    }
    
    protected CtClass createCtClass(String v1, final boolean v2) {
        if (v1.charAt(0) == '[') {
            v1 = Descriptor.toClassName(v1);
        }
        if (v1.endsWith("[]")) {
            final String a1 = v1.substring(0, v1.indexOf(91));
            if ((!v2 || this.getCached(a1) == null) && this.find(a1) == null) {
                return null;
            }
            return new CtArray(v1, this);
        }
        else {
            if (this.find(v1) == null) {
                return null;
            }
            return new CtClassType(v1, this);
        }
    }
    
    public URL find(final String a1) {
        return this.source.find(a1);
    }
    
    void checkNotFrozen(final String a1) throws RuntimeException {
        CtClass v1 = this.getCached(a1);
        if (v1 == null) {
            if (!this.childFirstLookup && this.parent != null) {
                try {
                    v1 = this.parent.get0(a1, true);
                }
                catch (NotFoundException ex) {}
                if (v1 != null) {
                    throw new RuntimeException(a1 + " is in a parent ClassPool.  Use the parent.");
                }
            }
        }
        else if (v1.isFrozen()) {
            throw new RuntimeException(a1 + ": frozen class (cannot edit)");
        }
    }
    
    CtClass checkNotExists(final String a1) {
        CtClass v1 = this.getCached(a1);
        if (v1 == null && !this.childFirstLookup && this.parent != null) {
            try {
                v1 = this.parent.get0(a1, true);
            }
            catch (NotFoundException ex) {}
        }
        return v1;
    }
    
    InputStream openClassfile(final String a1) throws NotFoundException {
        return this.source.openClassfile(a1);
    }
    
    void writeClassfile(final String a1, final OutputStream a2) throws NotFoundException, IOException, CannotCompileException {
        this.source.writeClassfile(a1, a2);
    }
    
    public CtClass[] get(final String[] v2) throws NotFoundException {
        if (v2 == null) {
            return new CtClass[0];
        }
        final int v3 = v2.length;
        final CtClass[] v4 = new CtClass[v3];
        for (int a1 = 0; a1 < v3; ++a1) {
            v4[a1] = this.get(v2[a1]);
        }
        return v4;
    }
    
    public CtMethod getMethod(final String a1, final String a2) throws NotFoundException {
        final CtClass v1 = this.get(a1);
        return v1.getDeclaredMethod(a2);
    }
    
    public CtClass makeClass(final InputStream a1) throws IOException, RuntimeException {
        return this.makeClass(a1, true);
    }
    
    public CtClass makeClass(InputStream a1, final boolean a2) throws IOException, RuntimeException {
        this.compress();
        a1 = new BufferedInputStream(a1);
        final CtClass v1 = new CtClassType(a1, this);
        v1.checkModify();
        final String v2 = v1.getName();
        if (a2) {
            this.checkNotFrozen(v2);
        }
        this.cacheCtClass(v2, v1, true);
        return v1;
    }
    
    public CtClass makeClass(final ClassFile a1) throws RuntimeException {
        return this.makeClass(a1, true);
    }
    
    public CtClass makeClass(final ClassFile a1, final boolean a2) throws RuntimeException {
        this.compress();
        final CtClass v1 = new CtClassType(a1, this);
        v1.checkModify();
        final String v2 = v1.getName();
        if (a2) {
            this.checkNotFrozen(v2);
        }
        this.cacheCtClass(v2, v1, true);
        return v1;
    }
    
    public CtClass makeClassIfNew(InputStream a1) throws IOException, RuntimeException {
        this.compress();
        a1 = new BufferedInputStream(a1);
        final CtClass v1 = new CtClassType(a1, this);
        v1.checkModify();
        final String v2 = v1.getName();
        final CtClass v3 = this.checkNotExists(v2);
        if (v3 != null) {
            return v3;
        }
        this.cacheCtClass(v2, v1, true);
        return v1;
    }
    
    public CtClass makeClass(final String a1) throws RuntimeException {
        return this.makeClass(a1, null);
    }
    
    public synchronized CtClass makeClass(final String a1, final CtClass a2) throws RuntimeException {
        this.checkNotFrozen(a1);
        final CtClass v1 = new CtNewClass(a1, this, false, a2);
        this.cacheCtClass(a1, v1, true);
        return v1;
    }
    
    synchronized CtClass makeNestedClass(final String a1) {
        this.checkNotFrozen(a1);
        final CtClass v1 = new CtNewNestedClass(a1, this, false, null);
        this.cacheCtClass(a1, v1, true);
        return v1;
    }
    
    public CtClass makeInterface(final String a1) throws RuntimeException {
        return this.makeInterface(a1, null);
    }
    
    public synchronized CtClass makeInterface(final String a1, final CtClass a2) throws RuntimeException {
        this.checkNotFrozen(a1);
        final CtClass v1 = new CtNewClass(a1, this, true, a2);
        this.cacheCtClass(a1, v1, true);
        return v1;
    }
    
    public CtClass makeAnnotation(final String v0) throws RuntimeException {
        try {
            final CtClass a1 = this.makeInterface(v0, this.get("java.lang.annotation.Annotation"));
            a1.setModifiers(a1.getModifiers() | 0x2000);
            return a1;
        }
        catch (NotFoundException v) {
            throw new RuntimeException(v.getMessage(), v);
        }
    }
    
    public ClassPath appendSystemPath() {
        return this.source.appendSystemPath();
    }
    
    public ClassPath insertClassPath(final ClassPath a1) {
        return this.source.insertClassPath(a1);
    }
    
    public ClassPath appendClassPath(final ClassPath a1) {
        return this.source.appendClassPath(a1);
    }
    
    public ClassPath insertClassPath(final String a1) throws NotFoundException {
        return this.source.insertClassPath(a1);
    }
    
    public ClassPath appendClassPath(final String a1) throws NotFoundException {
        return this.source.appendClassPath(a1);
    }
    
    public void removeClassPath(final ClassPath a1) {
        this.source.removeClassPath(a1);
    }
    
    public void appendPathList(final String v2) throws NotFoundException {
        final char v3 = File.pathSeparatorChar;
        int v4 = 0;
        while (true) {
            final int a1 = v2.indexOf(v3, v4);
            if (a1 < 0) {
                break;
            }
            this.appendClassPath(v2.substring(v4, a1));
            v4 = a1 + 1;
        }
        this.appendClassPath(v2.substring(v4));
    }
    
    public Class toClass(final CtClass a1) throws CannotCompileException {
        return this.toClass(a1, this.getClassLoader());
    }
    
    public ClassLoader getClassLoader() {
        return getContextClassLoader();
    }
    
    static ClassLoader getContextClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    
    @Deprecated
    public Class toClass(final CtClass a1, final ClassLoader a2) throws CannotCompileException {
        return this.toClass(a1, a2, null);
    }
    
    public Class toClass(final CtClass v-3, final ClassLoader v-2, final ProtectionDomain v-1) throws CannotCompileException {
        try {
            final byte[] a3 = v-3.toBytecode();
            Method v1 = null;
            Object[] v2 = null;
            if (v-1 == null) {
                final Method a4 = ClassPool.defineClass1;
                final Object[] a5 = { v-3.getName(), a3, new Integer(0), new Integer(a3.length) };
            }
            else {
                v1 = ClassPool.defineClass2;
                v2 = new Object[] { v-3.getName(), a3, new Integer(0), new Integer(a3.length), v-1 };
            }
            return (Class)toClass2(v1, v-2, v2);
        }
        catch (RuntimeException v3) {
            throw v3;
        }
        catch (InvocationTargetException v4) {
            throw new CannotCompileException(v4.getTargetException());
        }
        catch (Exception v5) {
            throw new CannotCompileException(v5);
        }
    }
    
    private static synchronized Object toClass2(final Method a1, final ClassLoader a2, final Object[] a3) throws Exception {
        a1.setAccessible(true);
        try {
            return a1.invoke(a2, a3);
        }
        finally {
            a1.setAccessible(false);
        }
    }
    
    public void makePackage(final ClassLoader v-3, final String v-2) throws CannotCompileException {
        final Object[] a4 = { v-2, null, null, null, null, null, null, null };
        Throwable v0 = null;
        try {
            toClass2(ClassPool.definePackage, v-3, a4);
            return;
        }
        catch (InvocationTargetException a3) {
            Throwable a2 = a3.getTargetException();
            if (a2 == null) {
                a2 = a3;
            }
            else if (a2 instanceof IllegalArgumentException) {
                return;
            }
        }
        catch (Exception v2) {
            v0 = v2;
        }
        throw new CannotCompileException(v0);
    }
    
    static /* synthetic */ Method access$002(final Method a1) {
        return ClassPool.defineClass1 = a1;
    }
    
    static /* synthetic */ Method access$102(final Method a1) {
        return ClassPool.defineClass2 = a1;
    }
    
    static /* synthetic */ Method access$202(final Method a1) {
        return ClassPool.definePackage = a1;
    }
    
    static {
        try {
            AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction() {
                ClassPool$1() {
                    super();
                }
                
                @Override
                public Object run() throws Exception {
                    final Class v1 = Class.forName("java.lang.ClassLoader");
                    ClassPool.defineClass1 = v1.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE);
                    ClassPool.defineClass2 = v1.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                    ClassPool.definePackage = v1.getDeclaredMethod("definePackage", String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class);
                    return null;
                }
            });
        }
        catch (PrivilegedActionException v1) {
            throw new RuntimeException("cannot initialize ClassPool", v1.getException());
        }
        ClassPool.doPruning = false;
        ClassPool.releaseUnmodifiedClassFile = true;
        ClassPool.defaultPool = null;
    }
}
