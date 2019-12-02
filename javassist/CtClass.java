package javassist;

import javassist.compiler.*;
import java.net.*;
import java.util.*;
import javassist.bytecode.*;
import javassist.expr.*;
import java.security.*;
import java.io.*;

public abstract class CtClass
{
    protected String qualifiedName;
    public static String debugDump;
    public static final String version = "3.21.0-GA";
    static final String javaLangObject = "java.lang.Object";
    public static CtClass booleanType;
    public static CtClass charType;
    public static CtClass byteType;
    public static CtClass shortType;
    public static CtClass intType;
    public static CtClass longType;
    public static CtClass floatType;
    public static CtClass doubleType;
    public static CtClass voidType;
    static CtClass[] primitiveTypes;
    
    public static void main(final String[] a1) {
        System.out.println("Javassist version 3.21.0-GA");
        System.out.println("Copyright (C) 1999-2016 Shigeru Chiba. All Rights Reserved.");
    }
    
    protected CtClass(final String a1) {
        super();
        this.qualifiedName = a1;
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer(this.getClass().getName());
        v1.append("@");
        v1.append(Integer.toHexString(this.hashCode()));
        v1.append("[");
        this.extendToString(v1);
        v1.append("]");
        return v1.toString();
    }
    
    protected void extendToString(final StringBuffer a1) {
        a1.append(this.getName());
    }
    
    public ClassPool getClassPool() {
        return null;
    }
    
    public ClassFile getClassFile() {
        this.checkModify();
        return this.getClassFile2();
    }
    
    public ClassFile getClassFile2() {
        return null;
    }
    
    public AccessorMaker getAccessorMaker() {
        return null;
    }
    
    public URL getURL() throws NotFoundException {
        throw new NotFoundException(this.getName());
    }
    
    public boolean isModified() {
        return false;
    }
    
    public boolean isFrozen() {
        return true;
    }
    
    public void freeze() {
    }
    
    void checkModify() throws RuntimeException {
        if (this.isFrozen()) {
            throw new RuntimeException(this.getName() + " class is frozen");
        }
    }
    
    public void defrost() {
        throw new RuntimeException("cannot defrost " + this.getName());
    }
    
    public boolean isPrimitive() {
        return false;
    }
    
    public boolean isArray() {
        return false;
    }
    
    public CtClass getComponentType() throws NotFoundException {
        return null;
    }
    
    public boolean subtypeOf(final CtClass a1) throws NotFoundException {
        return this == a1 || this.getName().equals(a1.getName());
    }
    
    public String getName() {
        return this.qualifiedName;
    }
    
    public final String getSimpleName() {
        final String v1 = this.qualifiedName;
        final int v2 = v1.lastIndexOf(46);
        if (v2 < 0) {
            return v1;
        }
        return v1.substring(v2 + 1);
    }
    
    public final String getPackageName() {
        final String v1 = this.qualifiedName;
        final int v2 = v1.lastIndexOf(46);
        if (v2 < 0) {
            return null;
        }
        return v1.substring(0, v2);
    }
    
    public void setName(final String a1) {
        this.checkModify();
        if (a1 != null) {
            this.qualifiedName = a1;
        }
    }
    
    public String getGenericSignature() {
        return null;
    }
    
    public void setGenericSignature(final String a1) {
        this.checkModify();
    }
    
    public void replaceClassName(final String a1, final String a2) {
        this.checkModify();
    }
    
    public void replaceClassName(final ClassMap a1) {
        this.checkModify();
    }
    
    public synchronized Collection getRefClasses() {
        final ClassFile v0 = this.getClassFile2();
        if (v0 != null) {
            final ClassMap v2 = new ClassMap() {
                final /* synthetic */ CtClass this$0;
                
                CtClass$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public void put(final String a1, final String a2) {
                    this.put0(a1, a2);
                }
                
                @Override
                public Object get(final Object a1) {
                    final String v1 = ClassMap.toJavaName((String)a1);
                    this.put0(v1, v1);
                    return null;
                }
                
                @Override
                public void fix(final String a1) {
                }
            };
            v0.getRefClasses(v2);
            return v2.values();
        }
        return null;
    }
    
    public boolean isInterface() {
        return false;
    }
    
    public boolean isAnnotation() {
        return false;
    }
    
    public boolean isEnum() {
        return false;
    }
    
    public int getModifiers() {
        return 0;
    }
    
    public boolean hasAnnotation(final Class a1) {
        return this.hasAnnotation(a1.getName());
    }
    
    public boolean hasAnnotation(final String a1) {
        return false;
    }
    
    public Object getAnnotation(final Class a1) throws ClassNotFoundException {
        return null;
    }
    
    public Object[] getAnnotations() throws ClassNotFoundException {
        return new Object[0];
    }
    
    public Object[] getAvailableAnnotations() {
        return new Object[0];
    }
    
    public CtClass[] getDeclaredClasses() throws NotFoundException {
        return this.getNestedClasses();
    }
    
    public CtClass[] getNestedClasses() throws NotFoundException {
        return new CtClass[0];
    }
    
    public void setModifiers(final int a1) {
        this.checkModify();
    }
    
    public boolean subclassOf(final CtClass a1) {
        return false;
    }
    
    public CtClass getSuperclass() throws NotFoundException {
        return null;
    }
    
    public void setSuperclass(final CtClass a1) throws CannotCompileException {
        this.checkModify();
    }
    
    public CtClass[] getInterfaces() throws NotFoundException {
        return new CtClass[0];
    }
    
    public void setInterfaces(final CtClass[] a1) {
        this.checkModify();
    }
    
    public void addInterface(final CtClass a1) {
        this.checkModify();
    }
    
    public CtClass getDeclaringClass() throws NotFoundException {
        return null;
    }
    
    @Deprecated
    public final CtMethod getEnclosingMethod() throws NotFoundException {
        final CtBehavior v1 = this.getEnclosingBehavior();
        if (v1 == null) {
            return null;
        }
        if (v1 instanceof CtMethod) {
            return (CtMethod)v1;
        }
        throw new NotFoundException(v1.getLongName() + " is enclosing " + this.getName());
    }
    
    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        return null;
    }
    
    public CtClass makeNestedClass(final String a1, final boolean a2) {
        throw new RuntimeException(this.getName() + " is not a class");
    }
    
    public CtField[] getFields() {
        return new CtField[0];
    }
    
    public CtField getField(final String a1) throws NotFoundException {
        return this.getField(a1, null);
    }
    
    public CtField getField(final String a1, final String a2) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    CtField getField2(final String a1, final String a2) {
        return null;
    }
    
    public CtField[] getDeclaredFields() {
        return new CtField[0];
    }
    
    public CtField getDeclaredField(final String a1) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    public CtField getDeclaredField(final String a1, final String a2) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    public CtBehavior[] getDeclaredBehaviors() {
        return new CtBehavior[0];
    }
    
    public CtConstructor[] getConstructors() {
        return new CtConstructor[0];
    }
    
    public CtConstructor getConstructor(final String a1) throws NotFoundException {
        throw new NotFoundException("no such constructor");
    }
    
    public CtConstructor[] getDeclaredConstructors() {
        return new CtConstructor[0];
    }
    
    public CtConstructor getDeclaredConstructor(final CtClass[] a1) throws NotFoundException {
        final String v1 = Descriptor.ofConstructor(a1);
        return this.getConstructor(v1);
    }
    
    public CtConstructor getClassInitializer() {
        return null;
    }
    
    public CtMethod[] getMethods() {
        return new CtMethod[0];
    }
    
    public CtMethod getMethod(final String a1, final String a2) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    public CtMethod[] getDeclaredMethods() {
        return new CtMethod[0];
    }
    
    public CtMethod getDeclaredMethod(final String a1, final CtClass[] a2) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    public CtMethod[] getDeclaredMethods(final String a1) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    public CtMethod getDeclaredMethod(final String a1) throws NotFoundException {
        throw new NotFoundException(a1);
    }
    
    public CtConstructor makeClassInitializer() throws CannotCompileException {
        throw new CannotCompileException("not a class");
    }
    
    public void addConstructor(final CtConstructor a1) throws CannotCompileException {
        this.checkModify();
    }
    
    public void removeConstructor(final CtConstructor a1) throws NotFoundException {
        this.checkModify();
    }
    
    public void addMethod(final CtMethod a1) throws CannotCompileException {
        this.checkModify();
    }
    
    public void removeMethod(final CtMethod a1) throws NotFoundException {
        this.checkModify();
    }
    
    public void addField(final CtField a1) throws CannotCompileException {
        this.addField(a1, (CtField.Initializer)null);
    }
    
    public void addField(final CtField a1, final String a2) throws CannotCompileException {
        this.checkModify();
    }
    
    public void addField(final CtField a1, final CtField.Initializer a2) throws CannotCompileException {
        this.checkModify();
    }
    
    public void removeField(final CtField a1) throws NotFoundException {
        this.checkModify();
    }
    
    public byte[] getAttribute(final String a1) {
        return null;
    }
    
    public void setAttribute(final String a1, final byte[] a2) {
        this.checkModify();
    }
    
    public void instrument(final CodeConverter a1) throws CannotCompileException {
        this.checkModify();
    }
    
    public void instrument(final ExprEditor a1) throws CannotCompileException {
        this.checkModify();
    }
    
    public Class toClass() throws CannotCompileException {
        return this.getClassPool().toClass(this);
    }
    
    public Class toClass(ClassLoader a1, final ProtectionDomain a2) throws CannotCompileException {
        final ClassPool v1 = this.getClassPool();
        if (a1 == null) {
            a1 = v1.getClassLoader();
        }
        return v1.toClass(this, a1, a2);
    }
    
    @Deprecated
    public final Class toClass(final ClassLoader a1) throws CannotCompileException {
        return this.getClassPool().toClass(this, a1);
    }
    
    public void detach() {
        final ClassPool v1 = this.getClassPool();
        final CtClass v2 = v1.removeCached(this.getName());
        if (v2 != this) {
            v1.cacheCtClass(this.getName(), v2, false);
        }
    }
    
    public boolean stopPruning(final boolean a1) {
        return true;
    }
    
    public void prune() {
    }
    
    void incGetCounter() {
    }
    
    public void rebuildClassFile() {
    }
    
    public byte[] toBytecode() throws IOException, CannotCompileException {
        final ByteArrayOutputStream v1 = new ByteArrayOutputStream();
        final DataOutputStream v2 = new DataOutputStream(v1);
        try {
            this.toBytecode(v2);
        }
        finally {
            v2.close();
        }
        return v1.toByteArray();
    }
    
    public void writeFile() throws NotFoundException, IOException, CannotCompileException {
        this.writeFile(".");
    }
    
    public void writeFile(final String a1) throws CannotCompileException, IOException {
        final DataOutputStream v1 = this.makeFileOutput(a1);
        try {
            this.toBytecode(v1);
        }
        finally {
            v1.close();
        }
    }
    
    protected DataOutputStream makeFileOutput(final String v2) {
        final String v3 = this.getName();
        final String v4 = v2 + File.separatorChar + v3.replace('.', File.separatorChar) + ".class";
        final int v5 = v4.lastIndexOf(File.separatorChar);
        if (v5 > 0) {
            final String a1 = v4.substring(0, v5);
            if (!a1.equals(".")) {
                new File(a1).mkdirs();
            }
        }
        return new DataOutputStream(new BufferedOutputStream(new DelayedFileOutputStream(v4)));
    }
    
    public void debugWriteFile() {
        this.debugWriteFile(".");
    }
    
    public void debugWriteFile(final String v0) {
        try {
            final boolean a1 = this.stopPruning(true);
            this.writeFile(v0);
            this.defrost();
            this.stopPruning(a1);
        }
        catch (Exception v) {
            throw new RuntimeException(v);
        }
    }
    
    public void toBytecode(final DataOutputStream a1) throws CannotCompileException, IOException {
        throw new CannotCompileException("not a class");
    }
    
    public String makeUniqueName(final String a1) {
        throw new RuntimeException("not available in " + this.getName());
    }
    
    void compress() {
    }
    
    static {
        CtClass.debugDump = null;
        CtClass.primitiveTypes = new CtClass[9];
        CtClass.booleanType = new CtPrimitiveType("boolean", 'Z', "java.lang.Boolean", "booleanValue", "()Z", 172, 4, 1);
        CtClass.primitiveTypes[0] = CtClass.booleanType;
        CtClass.charType = new CtPrimitiveType("char", 'C', "java.lang.Character", "charValue", "()C", 172, 5, 1);
        CtClass.primitiveTypes[1] = CtClass.charType;
        CtClass.byteType = new CtPrimitiveType("byte", 'B', "java.lang.Byte", "byteValue", "()B", 172, 8, 1);
        CtClass.primitiveTypes[2] = CtClass.byteType;
        CtClass.shortType = new CtPrimitiveType("short", 'S', "java.lang.Short", "shortValue", "()S", 172, 9, 1);
        CtClass.primitiveTypes[3] = CtClass.shortType;
        CtClass.intType = new CtPrimitiveType("int", 'I', "java.lang.Integer", "intValue", "()I", 172, 10, 1);
        CtClass.primitiveTypes[4] = CtClass.intType;
        CtClass.longType = new CtPrimitiveType("long", 'J', "java.lang.Long", "longValue", "()J", 173, 11, 2);
        CtClass.primitiveTypes[5] = CtClass.longType;
        CtClass.floatType = new CtPrimitiveType("float", 'F', "java.lang.Float", "floatValue", "()F", 174, 6, 1);
        CtClass.primitiveTypes[6] = CtClass.floatType;
        CtClass.doubleType = new CtPrimitiveType("double", 'D', "java.lang.Double", "doubleValue", "()D", 175, 7, 2);
        CtClass.primitiveTypes[7] = CtClass.doubleType;
        CtClass.voidType = new CtPrimitiveType("void", 'V', "java.lang.Void", null, null, 177, 0, 0);
        CtClass.primitiveTypes[8] = CtClass.voidType;
    }
    
    static class DelayedFileOutputStream extends OutputStream
    {
        private FileOutputStream file;
        private String filename;
        
        DelayedFileOutputStream(final String a1) {
            super();
            this.file = null;
            this.filename = a1;
        }
        
        private void init() throws IOException {
            if (this.file == null) {
                this.file = new FileOutputStream(this.filename);
            }
        }
        
        @Override
        public void write(final int a1) throws IOException {
            this.init();
            this.file.write(a1);
        }
        
        @Override
        public void write(final byte[] a1) throws IOException {
            this.init();
            this.file.write(a1);
        }
        
        @Override
        public void write(final byte[] a1, final int a2, final int a3) throws IOException {
            this.init();
            this.file.write(a1, a2, a3);
        }
        
        @Override
        public void flush() throws IOException {
            this.init();
            this.file.flush();
        }
        
        @Override
        public void close() throws IOException {
            this.init();
            this.file.close();
        }
    }
}
