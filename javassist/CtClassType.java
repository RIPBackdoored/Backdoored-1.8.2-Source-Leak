package javassist;

import java.lang.ref.*;
import java.io.*;
import java.net.*;
import javassist.bytecode.annotation.*;
import javassist.expr.*;
import javassist.bytecode.*;
import javassist.compiler.*;
import java.util.*;

class CtClassType extends CtClass
{
    ClassPool classPool;
    boolean wasChanged;
    private boolean wasFrozen;
    boolean wasPruned;
    boolean gcConstPool;
    ClassFile classfile;
    byte[] rawClassfile;
    private WeakReference memberCache;
    private AccessorMaker accessors;
    private FieldInitLink fieldInitializers;
    private Hashtable hiddenMethods;
    private int uniqueNumberSeed;
    private boolean doPruning;
    private int getCount;
    private static final int GET_THRESHOLD = 2;
    
    CtClassType(final String a1, final ClassPool a2) {
        super(a1);
        this.doPruning = ClassPool.doPruning;
        this.classPool = a2;
        final boolean b = false;
        this.gcConstPool = b;
        this.wasPruned = b;
        this.wasFrozen = b;
        this.wasChanged = b;
        this.classfile = null;
        this.rawClassfile = null;
        this.memberCache = null;
        this.accessors = null;
        this.fieldInitializers = null;
        this.hiddenMethods = null;
        this.uniqueNumberSeed = 0;
        this.getCount = 0;
    }
    
    CtClassType(final InputStream a1, final ClassPool a2) throws IOException {
        this((String)null, a2);
        this.classfile = new ClassFile(new DataInputStream(a1));
        this.qualifiedName = this.classfile.getName();
    }
    
    CtClassType(final ClassFile a1, final ClassPool a2) {
        this((String)null, a2);
        this.classfile = a1;
        this.qualifiedName = this.classfile.getName();
    }
    
    @Override
    protected void extendToString(final StringBuffer v0) {
        if (this.wasChanged) {
            v0.append("changed ");
        }
        if (this.wasFrozen) {
            v0.append("frozen ");
        }
        if (this.wasPruned) {
            v0.append("pruned ");
        }
        v0.append(Modifier.toString(this.getModifiers()));
        v0.append(" class ");
        v0.append(this.getName());
        try {
            final CtClass v = this.getSuperclass();
            if (v != null) {
                final String a1 = v.getName();
                if (!a1.equals("java.lang.Object")) {
                    v0.append(" extends " + v.getName());
                }
            }
        }
        catch (NotFoundException v5) {
            v0.append(" extends ??");
        }
        try {
            final CtClass[] v2 = this.getInterfaces();
            if (v2.length > 0) {
                v0.append(" implements ");
            }
            for (int v3 = 0; v3 < v2.length; ++v3) {
                v0.append(v2[v3].getName());
                v0.append(", ");
            }
        }
        catch (NotFoundException v5) {
            v0.append(" extends ??");
        }
        final CtMember.Cache v4 = this.getMembers();
        this.exToString(v0, " fields=", v4.fieldHead(), v4.lastField());
        this.exToString(v0, " constructors=", v4.consHead(), v4.lastCons());
        this.exToString(v0, " methods=", v4.methodHead(), v4.lastMethod());
    }
    
    private void exToString(final StringBuffer a1, final String a2, CtMember a3, final CtMember a4) {
        a1.append(a2);
        while (a3 != a4) {
            a3 = a3.next();
            a1.append(a3);
            a1.append(", ");
        }
    }
    
    @Override
    public AccessorMaker getAccessorMaker() {
        if (this.accessors == null) {
            this.accessors = new AccessorMaker(this);
        }
        return this.accessors;
    }
    
    @Override
    public ClassFile getClassFile2() {
        return this.getClassFile3(true);
    }
    
    public ClassFile getClassFile3(final boolean v-1) {
        final ClassFile v0 = this.classfile;
        if (v0 != null) {
            return v0;
        }
        if (v-1) {
            this.classPool.compress();
        }
        if (this.rawClassfile != null) {
            try {
                final ClassFile a1 = new ClassFile(new DataInputStream(new ByteArrayInputStream(this.rawClassfile)));
                this.rawClassfile = null;
                this.getCount = 2;
                return this.setClassFile(a1);
            }
            catch (IOException v2) {
                throw new RuntimeException(v2.toString(), v2);
            }
        }
        InputStream v3 = null;
        try {
            v3 = this.classPool.openClassfile(this.getName());
            if (v3 == null) {
                throw new NotFoundException(this.getName());
            }
            v3 = new BufferedInputStream(v3);
            final ClassFile v4 = new ClassFile(new DataInputStream(v3));
            if (!v4.getName().equals(this.qualifiedName)) {
                throw new RuntimeException("cannot find " + this.qualifiedName + ": " + v4.getName() + " found in " + this.qualifiedName.replace('.', '/') + ".class");
            }
            return this.setClassFile(v4);
        }
        catch (NotFoundException v5) {
            throw new RuntimeException(v5.toString(), v5);
        }
        catch (IOException v6) {
            throw new RuntimeException(v6.toString(), v6);
        }
        finally {
            if (v3 != null) {
                try {
                    v3.close();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    @Override
    final void incGetCounter() {
        ++this.getCount;
    }
    
    @Override
    void compress() {
        if (this.getCount < 2) {
            if (!this.isModified() && ClassPool.releaseUnmodifiedClassFile) {
                this.removeClassFile();
            }
            else if (this.isFrozen() && !this.wasPruned) {
                this.saveClassFile();
            }
        }
        this.getCount = 0;
    }
    
    private synchronized void saveClassFile() {
        if (this.classfile == null || this.hasMemberCache() != null) {
            return;
        }
        final ByteArrayOutputStream v1 = new ByteArrayOutputStream();
        final DataOutputStream v2 = new DataOutputStream(v1);
        try {
            this.classfile.write(v2);
            v1.close();
            this.rawClassfile = v1.toByteArray();
            this.classfile = null;
        }
        catch (IOException ex) {}
    }
    
    private synchronized void removeClassFile() {
        if (this.classfile != null && !this.isModified() && this.hasMemberCache() == null) {
            this.classfile = null;
        }
    }
    
    private synchronized ClassFile setClassFile(final ClassFile a1) {
        if (this.classfile == null) {
            this.classfile = a1;
        }
        return this.classfile;
    }
    
    @Override
    public ClassPool getClassPool() {
        return this.classPool;
    }
    
    void setClassPool(final ClassPool a1) {
        this.classPool = a1;
    }
    
    @Override
    public URL getURL() throws NotFoundException {
        final URL v1 = this.classPool.find(this.getName());
        if (v1 == null) {
            throw new NotFoundException(this.getName());
        }
        return v1;
    }
    
    @Override
    public boolean isModified() {
        return this.wasChanged;
    }
    
    @Override
    public boolean isFrozen() {
        return this.wasFrozen;
    }
    
    @Override
    public void freeze() {
        this.wasFrozen = true;
    }
    
    @Override
    void checkModify() throws RuntimeException {
        if (this.isFrozen()) {
            String v1 = this.getName() + " class is frozen";
            if (this.wasPruned) {
                v1 += " and pruned";
            }
            throw new RuntimeException(v1);
        }
        this.wasChanged = true;
    }
    
    @Override
    public void defrost() {
        this.checkPruned("defrost");
        this.wasFrozen = false;
    }
    
    @Override
    public boolean subtypeOf(final CtClass a1) throws NotFoundException {
        final String v2 = a1.getName();
        if (this == a1 || this.getName().equals(v2)) {
            return true;
        }
        final ClassFile v3 = this.getClassFile2();
        final String v4 = v3.getSuperclass();
        if (v4 != null && v4.equals(v2)) {
            return true;
        }
        final String[] v5 = v3.getInterfaces();
        final int v6 = v5.length;
        for (int v7 = 0; v7 < v6; ++v7) {
            if (v5[v7].equals(v2)) {
                return true;
            }
        }
        if (v4 != null && this.classPool.get(v4).subtypeOf(a1)) {
            return true;
        }
        for (int v7 = 0; v7 < v6; ++v7) {
            if (this.classPool.get(v5[v7]).subtypeOf(a1)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void setName(final String a1) throws RuntimeException {
        final String v1 = this.getName();
        if (a1.equals(v1)) {
            return;
        }
        this.classPool.checkNotFrozen(a1);
        final ClassFile v2 = this.getClassFile2();
        super.setName(a1);
        v2.setName(a1);
        this.nameReplaced();
        this.classPool.classNameChanged(v1, this);
    }
    
    @Override
    public String getGenericSignature() {
        final SignatureAttribute v1 = (SignatureAttribute)this.getClassFile2().getAttribute("Signature");
        return (v1 == null) ? null : v1.getSignature();
    }
    
    @Override
    public void setGenericSignature(final String a1) {
        final ClassFile v1 = this.getClassFile();
        final SignatureAttribute v2 = new SignatureAttribute(v1.getConstPool(), a1);
        v1.addAttribute(v2);
    }
    
    @Override
    public void replaceClassName(final ClassMap a1) throws RuntimeException {
        final String v1 = this.getName();
        String v2 = (String)a1.get(Descriptor.toJvmName(v1));
        if (v2 != null) {
            v2 = Descriptor.toJavaName(v2);
            this.classPool.checkNotFrozen(v2);
        }
        super.replaceClassName(a1);
        final ClassFile v3 = this.getClassFile2();
        v3.renameClass(a1);
        this.nameReplaced();
        if (v2 != null) {
            super.setName(v2);
            this.classPool.classNameChanged(v1, this);
        }
    }
    
    @Override
    public void replaceClassName(final String a1, final String a2) throws RuntimeException {
        final String v1 = this.getName();
        if (v1.equals(a1)) {
            this.setName(a2);
        }
        else {
            super.replaceClassName(a1, a2);
            this.getClassFile2().renameClass(a1, a2);
            this.nameReplaced();
        }
    }
    
    @Override
    public boolean isInterface() {
        return Modifier.isInterface(this.getModifiers());
    }
    
    @Override
    public boolean isAnnotation() {
        return Modifier.isAnnotation(this.getModifiers());
    }
    
    @Override
    public boolean isEnum() {
        return Modifier.isEnum(this.getModifiers());
    }
    
    @Override
    public int getModifiers() {
        final ClassFile v1 = this.getClassFile2();
        int v2 = v1.getAccessFlags();
        v2 = AccessFlag.clear(v2, 32);
        final int v3 = v1.getInnerAccessFlags();
        if (v3 != -1 && (v3 & 0x8) != 0x0) {
            v2 |= 0x8;
        }
        return AccessFlag.toModifier(v2);
    }
    
    @Override
    public CtClass[] getNestedClasses() throws NotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)classFile2.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return new CtClass[0];
        }
        final String string = classFile2.getName() + "$";
        final int tableLength = innerClassesAttribute.tableLength();
        final ArrayList list = new ArrayList(tableLength);
        for (int v0 = 0; v0 < tableLength; ++v0) {
            final String v2 = innerClassesAttribute.innerClass(v0);
            if (v2 != null && v2.startsWith(string) && v2.lastIndexOf(36) < string.length()) {
                list.add(this.classPool.get(v2));
            }
        }
        return list.toArray(new CtClass[list.size()]);
    }
    
    @Override
    public void setModifiers(int v2) {
        final ClassFile v3 = this.getClassFile2();
        if (Modifier.isStatic(v2)) {
            final int a1 = v3.getInnerAccessFlags();
            if (a1 == -1 || (a1 & 0x8) == 0x0) {
                throw new RuntimeException("cannot change " + this.getName() + " into a static class");
            }
            v2 &= 0xFFFFFFF7;
        }
        this.checkModify();
        v3.setAccessFlags(AccessFlag.of(v2));
    }
    
    @Override
    public boolean hasAnnotation(final String a1) {
        final ClassFile v1 = this.getClassFile2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return hasAnnotationType(a1, this.getClassPool(), v2, v3);
    }
    
    @Deprecated
    static boolean hasAnnotationType(final Class a1, final ClassPool a2, final AnnotationsAttribute a3, final AnnotationsAttribute a4) {
        return hasAnnotationType(a1.getName(), a2, a3, a4);
    }
    
    static boolean hasAnnotationType(final String v1, final ClassPool v2, final AnnotationsAttribute v3, final AnnotationsAttribute v4) {
        Annotation[] v5 = null;
        if (v3 == null) {
            final Annotation[] a1 = null;
        }
        else {
            v5 = v3.getAnnotations();
        }
        Annotation[] v6 = null;
        if (v4 == null) {
            final Annotation[] a2 = null;
        }
        else {
            v6 = v4.getAnnotations();
        }
        if (v5 != null) {
            for (int a3 = 0; a3 < v5.length; ++a3) {
                if (v5[a3].getTypeName().equals(v1)) {
                    return true;
                }
            }
        }
        if (v6 != null) {
            for (int a4 = 0; a4 < v6.length; ++a4) {
                if (v6[a4].getTypeName().equals(v1)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public Object getAnnotation(final Class a1) throws ClassNotFoundException {
        final ClassFile v1 = this.getClassFile2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return getAnnotationType(a1, this.getClassPool(), v2, v3);
    }
    
    static Object getAnnotationType(final Class v1, final ClassPool v2, final AnnotationsAttribute v3, final AnnotationsAttribute v4) throws ClassNotFoundException {
        Annotation[] v5 = null;
        if (v3 == null) {
            final Annotation[] a1 = null;
        }
        else {
            v5 = v3.getAnnotations();
        }
        Annotation[] v6 = null;
        if (v4 == null) {
            final Annotation[] a2 = null;
        }
        else {
            v6 = v4.getAnnotations();
        }
        final String v7 = v1.getName();
        if (v5 != null) {
            for (int a3 = 0; a3 < v5.length; ++a3) {
                if (v5[a3].getTypeName().equals(v7)) {
                    return toAnnoType(v5[a3], v2);
                }
            }
        }
        if (v6 != null) {
            for (int a4 = 0; a4 < v6.length; ++a4) {
                if (v6[a4].getTypeName().equals(v7)) {
                    return toAnnoType(v6[a4], v2);
                }
            }
        }
        return null;
    }
    
    @Override
    public Object[] getAnnotations() throws ClassNotFoundException {
        return this.getAnnotations(false);
    }
    
    @Override
    public Object[] getAvailableAnnotations() {
        try {
            return this.getAnnotations(true);
        }
        catch (ClassNotFoundException v1) {
            throw new RuntimeException("Unexpected exception ", v1);
        }
    }
    
    private Object[] getAnnotations(final boolean a1) throws ClassNotFoundException {
        final ClassFile v1 = this.getClassFile2();
        final AnnotationsAttribute v2 = (AnnotationsAttribute)v1.getAttribute("RuntimeInvisibleAnnotations");
        final AnnotationsAttribute v3 = (AnnotationsAttribute)v1.getAttribute("RuntimeVisibleAnnotations");
        return toAnnotationType(a1, this.getClassPool(), v2, v3);
    }
    
    static Object[] toAnnotationType(final boolean v-8, final ClassPool v-7, final AnnotationsAttribute v-6, final AnnotationsAttribute v-5) throws ClassNotFoundException {
        Annotation[] annotations = null;
        int length = 0;
        if (v-6 == null) {
            final Annotation[] a1 = null;
            final int a2 = 0;
        }
        else {
            annotations = v-6.getAnnotations();
            length = annotations.length;
        }
        Annotation[] annotations2 = null;
        int length2 = 0;
        if (v-5 == null) {
            final Annotation[] a3 = null;
            final int a4 = 0;
        }
        else {
            annotations2 = v-5.getAnnotations();
            length2 = annotations2.length;
        }
        if (!v-8) {
            final Object[] v0 = new Object[length + length2];
            for (int v2 = 0; v2 < length; ++v2) {
                v0[v2] = toAnnoType(annotations[v2], v-7);
            }
            for (int v2 = 0; v2 < length2; ++v2) {
                v0[v2 + length] = toAnnoType(annotations2[v2], v-7);
            }
            return v0;
        }
        final ArrayList v3 = new ArrayList();
        for (int v2 = 0; v2 < length; ++v2) {
            try {
                v3.add(toAnnoType(annotations[v2], v-7));
            }
            catch (ClassNotFoundException ex) {}
        }
        for (int v2 = 0; v2 < length2; ++v2) {
            try {
                v3.add(toAnnoType(annotations2[v2], v-7));
            }
            catch (ClassNotFoundException ex2) {}
        }
        return v3.toArray();
    }
    
    static Object[][] toAnnotationType(final boolean v-11, final ClassPool v-10, final ParameterAnnotationsAttribute v-9, final ParameterAnnotationsAttribute v-8, final MethodInfo v-7) throws ClassNotFoundException {
        int n = 0;
        if (v-9 != null) {
            n = v-9.numParameters();
        }
        else if (v-8 != null) {
            n = v-8.numParameters();
        }
        else {
            n = Descriptor.numOfParameters(v-7.getDescriptor());
        }
        final Object[][] array = new Object[n][];
        for (int i = 0; i < n; ++i) {
            Annotation[] array2 = null;
            int length = 0;
            if (v-9 == null) {
                final Annotation[] a1 = null;
                final int a2 = 0;
            }
            else {
                array2 = v-9.getAnnotations()[i];
                length = array2.length;
            }
            Annotation[] array3 = null;
            int v0 = 0;
            if (v-8 == null) {
                final Annotation[] a3 = null;
                final int a4 = 0;
            }
            else {
                array3 = v-8.getAnnotations()[i];
                v0 = array3.length;
            }
            if (!v-11) {
                array[i] = new Object[length + v0];
                for (int a5 = 0; a5 < length; ++a5) {
                    array[i][a5] = toAnnoType(array2[a5], v-10);
                }
                for (int v2 = 0; v2 < v0; ++v2) {
                    array[i][v2 + length] = toAnnoType(array3[v2], v-10);
                }
            }
            else {
                final ArrayList v3 = new ArrayList();
                for (int v4 = 0; v4 < length; ++v4) {
                    try {
                        v3.add(toAnnoType(array2[v4], v-10));
                    }
                    catch (ClassNotFoundException ex) {}
                }
                for (int v4 = 0; v4 < v0; ++v4) {
                    try {
                        v3.add(toAnnoType(array3[v4], v-10));
                    }
                    catch (ClassNotFoundException ex2) {}
                }
                array[i] = v3.toArray();
            }
        }
        return array;
    }
    
    private static Object toAnnoType(final Annotation v-4, final ClassPool v-3) throws ClassNotFoundException {
        try {
            final ClassLoader a1 = v-3.getClassLoader();
            return v-4.toAnnotationType(a1, v-3);
        }
        catch (ClassNotFoundException ex) {
            final ClassLoader classLoader = v-3.getClass().getClassLoader();
            try {
                return v-4.toAnnotationType(classLoader, v-3);
            }
            catch (ClassNotFoundException v0) {
                try {
                    final Class a2 = v-3.get(v-4.getTypeName()).toClass();
                    return AnnotationImpl.make(a2.getClassLoader(), a2, v-3, v-4);
                }
                catch (Throwable v2) {
                    throw new ClassNotFoundException(v-4.getTypeName());
                }
            }
        }
    }
    
    @Override
    public boolean subclassOf(final CtClass a1) {
        if (a1 == null) {
            return false;
        }
        final String v1 = a1.getName();
        CtClass v2 = this;
        try {
            while (v2 != null) {
                if (v2.getName().equals(v1)) {
                    return true;
                }
                v2 = v2.getSuperclass();
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    @Override
    public CtClass getSuperclass() throws NotFoundException {
        final String v1 = this.getClassFile2().getSuperclass();
        if (v1 == null) {
            return null;
        }
        return this.classPool.get(v1);
    }
    
    @Override
    public void setSuperclass(final CtClass a1) throws CannotCompileException {
        this.checkModify();
        if (this.isInterface()) {
            this.addInterface(a1);
        }
        else {
            this.getClassFile2().setSuperclass(a1.getName());
        }
    }
    
    @Override
    public CtClass[] getInterfaces() throws NotFoundException {
        final String[] interfaces = this.getClassFile2().getInterfaces();
        final int length = interfaces.length;
        final CtClass[] v0 = new CtClass[length];
        for (int v2 = 0; v2 < length; ++v2) {
            v0[v2] = this.classPool.get(interfaces[v2]);
        }
        return v0;
    }
    
    @Override
    public void setInterfaces(final CtClass[] v-2) {
        this.checkModify();
        String[] interfaces = null;
        if (v-2 == null) {
            final String[] a1 = new String[0];
        }
        else {
            final int v0 = v-2.length;
            interfaces = new String[v0];
            for (int v2 = 0; v2 < v0; ++v2) {
                interfaces[v2] = v-2[v2].getName();
            }
        }
        this.getClassFile2().setInterfaces(interfaces);
    }
    
    @Override
    public void addInterface(final CtClass a1) {
        this.checkModify();
        if (a1 != null) {
            this.getClassFile2().addInterface(a1.getName());
        }
    }
    
    @Override
    public CtClass getDeclaringClass() throws NotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)classFile2.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return null;
        }
        final String name = this.getName();
        for (int tableLength = innerClassesAttribute.tableLength(), i = 0; i < tableLength; ++i) {
            if (name.equals(innerClassesAttribute.innerClass(i))) {
                final String v0 = innerClassesAttribute.outerClass(i);
                if (v0 != null) {
                    return this.classPool.get(v0);
                }
                final EnclosingMethodAttribute v2 = (EnclosingMethodAttribute)classFile2.getAttribute("EnclosingMethod");
                if (v2 != null) {
                    return this.classPool.get(v2.className());
                }
            }
        }
        return null;
    }
    
    @Override
    public CtBehavior getEnclosingBehavior() throws NotFoundException {
        final ClassFile classFile2 = this.getClassFile2();
        final EnclosingMethodAttribute v0 = (EnclosingMethodAttribute)classFile2.getAttribute("EnclosingMethod");
        if (v0 == null) {
            return null;
        }
        final CtClass v2 = this.classPool.get(v0.className());
        final String v3 = v0.methodName();
        if ("<init>".equals(v3)) {
            return v2.getConstructor(v0.methodDescriptor());
        }
        if ("<clinit>".equals(v3)) {
            return v2.getClassInitializer();
        }
        return v2.getMethod(v3, v0.methodDescriptor());
    }
    
    @Override
    public CtClass makeNestedClass(final String a1, final boolean a2) {
        if (!a2) {
            throw new RuntimeException("sorry, only nested static class is supported");
        }
        this.checkModify();
        final CtClass v1 = this.classPool.makeNestedClass(this.getName() + "$" + a1);
        final ClassFile v2 = this.getClassFile2();
        final ClassFile v3 = v1.getClassFile2();
        InnerClassesAttribute v4 = (InnerClassesAttribute)v2.getAttribute("InnerClasses");
        if (v4 == null) {
            v4 = new InnerClassesAttribute(v2.getConstPool());
            v2.addAttribute(v4);
        }
        v4.append(v1.getName(), this.getName(), a1, (v3.getAccessFlags() & 0xFFFFFFDF) | 0x8);
        v3.addAttribute(v4.copy(v3.getConstPool(), null));
        return v1;
    }
    
    private void nameReplaced() {
        final CtMember.Cache v0 = this.hasMemberCache();
        if (v0 != null) {
            CtMember v2 = v0.methodHead();
            final CtMember v3 = v0.lastMethod();
            while (v2 != v3) {
                v2 = v2.next();
                v2.nameReplaced();
            }
        }
    }
    
    protected CtMember.Cache hasMemberCache() {
        final WeakReference v1 = this.memberCache;
        if (v1 != null) {
            return (CtMember.Cache)v1.get();
        }
        return null;
    }
    
    protected synchronized CtMember.Cache getMembers() {
        CtMember.Cache v1 = null;
        if (this.memberCache == null || (v1 = (CtMember.Cache)this.memberCache.get()) == null) {
            v1 = new CtMember.Cache(this);
            this.makeFieldCache(v1);
            this.makeBehaviorCache(v1);
            this.memberCache = new WeakReference((T)v1);
        }
        return v1;
    }
    
    private void makeFieldCache(final CtMember.Cache v-4) {
        final List fields = this.getClassFile3(false).getFields();
        for (int size = fields.size(), i = 0; i < size; ++i) {
            final FieldInfo a1 = fields.get(i);
            final CtField v1 = new CtField(a1, this);
            v-4.addField(v1);
        }
    }
    
    private void makeBehaviorCache(final CtMember.Cache v-4) {
        final List methods = this.getClassFile3(false).getMethods();
        for (int size = methods.size(), i = 0; i < size; ++i) {
            final MethodInfo v0 = methods.get(i);
            if (v0.isMethod()) {
                final CtMethod a1 = new CtMethod(v0, this);
                v-4.addMethod(a1);
            }
            else {
                final CtConstructor v2 = new CtConstructor(v0, this);
                v-4.addConstructor(v2);
            }
        }
    }
    
    @Override
    public CtField[] getFields() {
        final ArrayList v1 = new ArrayList();
        getFields(v1, this);
        return v1.toArray(new CtField[v1.size()]);
    }
    
    private static void getFields(final ArrayList v-2, final CtClass v-1) {
        if (v-1 == null) {
            return;
        }
        try {
            getFields(v-2, v-1.getSuperclass());
        }
        catch (NotFoundException ex) {}
        try {
            final CtClass[] a1 = v-1.getInterfaces();
            for (int v1 = a1.length, a2 = 0; a2 < v1; ++a2) {
                getFields(v-2, a1[a2]);
            }
        }
        catch (NotFoundException ex2) {}
        final CtMember.Cache v2 = ((CtClassType)v-1).getMembers();
        CtMember v3 = v2.fieldHead();
        final CtMember v4 = v2.lastField();
        while (v3 != v4) {
            v3 = v3.next();
            if (!Modifier.isPrivate(v3.getModifiers())) {
                v-2.add(v3);
            }
        }
    }
    
    @Override
    public CtField getField(final String a1, final String a2) throws NotFoundException {
        final CtField v1 = this.getField2(a1, a2);
        return this.checkGetField(v1, a1, a2);
    }
    
    private CtField checkGetField(final CtField a3, final String v1, final String v2) throws NotFoundException {
        if (a3 == null) {
            String a4 = "field: " + v1;
            if (v2 != null) {
                a4 = a4 + " type " + v2;
            }
            throw new NotFoundException(a4 + " in " + this.getName());
        }
        return a3;
    }
    
    @Override
    CtField getField2(final String v-2, final String v-1) {
        final CtField v0 = this.getDeclaredField2(v-2, v-1);
        if (v0 != null) {
            return v0;
        }
        try {
            final CtClass[] v2 = this.getInterfaces();
            for (int v3 = v2.length, a2 = 0; a2 < v3; ++a2) {
                final CtField a3 = v2[a2].getField2(v-2, v-1);
                if (a3 != null) {
                    return a3;
                }
            }
            final CtClass v4 = this.getSuperclass();
            if (v4 != null) {
                return v4.getField2(v-2, v-1);
            }
        }
        catch (NotFoundException ex) {}
        return null;
    }
    
    @Override
    public CtField[] getDeclaredFields() {
        final CtMember.Cache v1 = this.getMembers();
        CtMember v2 = v1.fieldHead();
        final CtMember v3 = v1.lastField();
        final int v4 = CtMember.Cache.count(v2, v3);
        final CtField[] v5 = new CtField[v4];
        for (int v6 = 0; v2 != v3; v2 = v2.next(), v5[v6++] = (CtField)v2) {}
        return v5;
    }
    
    @Override
    public CtField getDeclaredField(final String a1) throws NotFoundException {
        return this.getDeclaredField(a1, null);
    }
    
    @Override
    public CtField getDeclaredField(final String a1, final String a2) throws NotFoundException {
        final CtField v1 = this.getDeclaredField2(a1, a2);
        return this.checkGetField(v1, a1, a2);
    }
    
    private CtField getDeclaredField2(final String a1, final String a2) {
        final CtMember.Cache v1 = this.getMembers();
        CtMember v2 = v1.fieldHead();
        final CtMember v3 = v1.lastField();
        while (v2 != v3) {
            v2 = v2.next();
            if (v2.getName().equals(a1) && (a2 == null || a2.equals(v2.getSignature()))) {
                return (CtField)v2;
            }
        }
        return null;
    }
    
    @Override
    public CtBehavior[] getDeclaredBehaviors() {
        final CtMember.Cache v1 = this.getMembers();
        CtMember v2 = v1.consHead();
        final CtMember v3 = v1.lastCons();
        final int v4 = CtMember.Cache.count(v2, v3);
        CtMember v5 = v1.methodHead();
        final CtMember v6 = v1.lastMethod();
        final int v7 = CtMember.Cache.count(v5, v6);
        CtBehavior[] v8;
        int v9;
        for (v8 = new CtBehavior[v4 + v7], v9 = 0; v2 != v3; v2 = v2.next(), v8[v9++] = (CtBehavior)v2) {}
        while (v5 != v6) {
            v5 = v5.next();
            v8[v9++] = (CtBehavior)v5;
        }
        return v8;
    }
    
    @Override
    public CtConstructor[] getConstructors() {
        final CtMember.Cache members = this.getMembers();
        final CtMember consHead = members.consHead();
        final CtMember lastCons = members.lastCons();
        int n = 0;
        CtMember ctMember = consHead;
        while (ctMember != lastCons) {
            ctMember = ctMember.next();
            if (isPubCons((CtConstructor)ctMember)) {
                ++n;
            }
        }
        final CtConstructor[] array = new CtConstructor[n];
        int v0 = 0;
        ctMember = consHead;
        while (ctMember != lastCons) {
            ctMember = ctMember.next();
            final CtConstructor v2 = (CtConstructor)ctMember;
            if (isPubCons(v2)) {
                array[v0++] = v2;
            }
        }
        return array;
    }
    
    private static boolean isPubCons(final CtConstructor a1) {
        return !Modifier.isPrivate(a1.getModifiers()) && a1.isConstructor();
    }
    
    @Override
    public CtConstructor getConstructor(final String v2) throws NotFoundException {
        final CtMember.Cache v3 = this.getMembers();
        CtMember v4 = v3.consHead();
        final CtMember v5 = v3.lastCons();
        while (v4 != v5) {
            v4 = v4.next();
            final CtConstructor a1 = (CtConstructor)v4;
            if (a1.getMethodInfo2().getDescriptor().equals(v2) && a1.isConstructor()) {
                return a1;
            }
        }
        return super.getConstructor(v2);
    }
    
    @Override
    public CtConstructor[] getDeclaredConstructors() {
        final CtMember.Cache members = this.getMembers();
        final CtMember consHead = members.consHead();
        final CtMember lastCons = members.lastCons();
        int n = 0;
        CtMember v0 = consHead;
        while (v0 != lastCons) {
            v0 = v0.next();
            final CtConstructor v2 = (CtConstructor)v0;
            if (v2.isConstructor()) {
                ++n;
            }
        }
        final CtConstructor[] v3 = new CtConstructor[n];
        int v4 = 0;
        v0 = consHead;
        while (v0 != lastCons) {
            v0 = v0.next();
            final CtConstructor v5 = (CtConstructor)v0;
            if (v5.isConstructor()) {
                v3[v4++] = v5;
            }
        }
        return v3;
    }
    
    @Override
    public CtConstructor getClassInitializer() {
        final CtMember.Cache members = this.getMembers();
        CtMember ctMember = members.consHead();
        final CtMember v0 = members.lastCons();
        while (ctMember != v0) {
            ctMember = ctMember.next();
            final CtConstructor v2 = (CtConstructor)ctMember;
            if (v2.isClassInitializer()) {
                return v2;
            }
        }
        return null;
    }
    
    @Override
    public CtMethod[] getMethods() {
        final HashMap v1 = new HashMap();
        getMethods0(v1, this);
        return (CtMethod[])v1.values().toArray(new CtMethod[v1.size()]);
    }
    
    private static void getMethods0(final HashMap v-2, final CtClass v-1) {
        try {
            final CtClass[] a2 = v-1.getInterfaces();
            for (int v1 = a2.length, a3 = 0; a3 < v1; ++a3) {
                getMethods0(v-2, a2[a3]);
            }
        }
        catch (NotFoundException ex) {}
        try {
            final CtClass v2 = v-1.getSuperclass();
            if (v2 != null) {
                getMethods0(v-2, v2);
            }
        }
        catch (NotFoundException ex2) {}
        if (v-1 instanceof CtClassType) {
            final CtMember.Cache v3 = ((CtClassType)v-1).getMembers();
            CtMember v4 = v3.methodHead();
            final CtMember v5 = v3.lastMethod();
            while (v4 != v5) {
                v4 = v4.next();
                if (!Modifier.isPrivate(v4.getModifiers())) {
                    v-2.put(((CtMethod)v4).getStringRep(), v4);
                }
            }
        }
    }
    
    @Override
    public CtMethod getMethod(final String a1, final String a2) throws NotFoundException {
        final CtMethod v1 = getMethod0(this, a1, a2);
        if (v1 != null) {
            return v1;
        }
        throw new NotFoundException(a1 + "(..) is not found in " + this.getName());
    }
    
    private static CtMethod getMethod0(final CtClass v-3, final String v-2, final String v-1) {
        if (v-3 instanceof CtClassType) {
            final CtMember.Cache a1 = ((CtClassType)v-3).getMembers();
            CtMember a2 = a1.methodHead();
            final CtMember a3 = a1.lastMethod();
            while (a2 != a3) {
                a2 = a2.next();
                if (a2.getName().equals(v-2) && ((CtMethod)a2).getMethodInfo2().getDescriptor().equals(v-1)) {
                    return (CtMethod)a2;
                }
            }
        }
        try {
            final CtClass v0 = v-3.getSuperclass();
            if (v0 != null) {
                final CtMethod v2 = getMethod0(v0, v-2, v-1);
                if (v2 != null) {
                    return v2;
                }
            }
        }
        catch (NotFoundException ex) {}
        try {
            final CtClass[] v3 = v-3.getInterfaces();
            for (int v4 = v3.length, v5 = 0; v5 < v4; ++v5) {
                final CtMethod v6 = getMethod0(v3[v5], v-2, v-1);
                if (v6 != null) {
                    return v6;
                }
            }
        }
        catch (NotFoundException ex2) {}
        return null;
    }
    
    @Override
    public CtMethod[] getDeclaredMethods() {
        final CtMember.Cache v1 = this.getMembers();
        CtMember v2 = v1.methodHead();
        final CtMember v3 = v1.lastMethod();
        final int v4 = CtMember.Cache.count(v2, v3);
        final CtMethod[] v5 = new CtMethod[v4];
        for (int v6 = 0; v2 != v3; v2 = v2.next(), v5[v6++] = (CtMethod)v2) {}
        return v5;
    }
    
    @Override
    public CtMethod[] getDeclaredMethods(final String a1) throws NotFoundException {
        final CtMember.Cache v1 = this.getMembers();
        CtMember v2 = v1.methodHead();
        final CtMember v3 = v1.lastMethod();
        final ArrayList v4 = new ArrayList();
        while (v2 != v3) {
            v2 = v2.next();
            if (v2.getName().equals(a1)) {
                v4.add(v2);
            }
        }
        return v4.toArray(new CtMethod[v4.size()]);
    }
    
    @Override
    public CtMethod getDeclaredMethod(final String a1) throws NotFoundException {
        final CtMember.Cache v1 = this.getMembers();
        CtMember v2 = v1.methodHead();
        final CtMember v3 = v1.lastMethod();
        while (v2 != v3) {
            v2 = v2.next();
            if (v2.getName().equals(a1)) {
                return (CtMethod)v2;
            }
        }
        throw new NotFoundException(a1 + "(..) is not found in " + this.getName());
    }
    
    @Override
    public CtMethod getDeclaredMethod(final String a1, final CtClass[] a2) throws NotFoundException {
        final String v1 = Descriptor.ofParameters(a2);
        final CtMember.Cache v2 = this.getMembers();
        CtMember v3 = v2.methodHead();
        final CtMember v4 = v2.lastMethod();
        while (v3 != v4) {
            v3 = v3.next();
            if (v3.getName().equals(a1) && ((CtMethod)v3).getMethodInfo2().getDescriptor().startsWith(v1)) {
                return (CtMethod)v3;
            }
        }
        throw new NotFoundException(a1 + "(..) is not found in " + this.getName());
    }
    
    @Override
    public void addField(final CtField a1, final String a2) throws CannotCompileException {
        this.addField(a1, CtField.Initializer.byExpr(a2));
    }
    
    @Override
    public void addField(final CtField v-1, CtField.Initializer v0) throws CannotCompileException {
        this.checkModify();
        if (v-1.getDeclaringClass() != this) {
            throw new CannotCompileException("cannot add");
        }
        if (v0 == null) {
            v0 = v-1.getInit();
        }
        if (v0 != null) {
            v0.check(v-1.getSignature());
            final int v = v-1.getModifiers();
            if (Modifier.isStatic(v) && Modifier.isFinal(v)) {
                try {
                    final ConstPool a1 = this.getClassFile2().getConstPool();
                    final int a2 = v0.getConstantValue(a1, v-1.getType());
                    if (a2 != 0) {
                        v-1.getFieldInfo2().addAttribute(new ConstantAttribute(a1, a2));
                        v0 = null;
                    }
                }
                catch (NotFoundException ex) {}
            }
        }
        this.getMembers().addField(v-1);
        this.getClassFile2().addField(v-1.getFieldInfo2());
        if (v0 != null) {
            final FieldInitLink v2 = new FieldInitLink(v-1, v0);
            FieldInitLink v3 = this.fieldInitializers;
            if (v3 == null) {
                this.fieldInitializers = v2;
            }
            else {
                while (v3.next != null) {
                    v3 = v3.next;
                }
                v3.next = v2;
            }
        }
    }
    
    @Override
    public void removeField(final CtField a1) throws NotFoundException {
        this.checkModify();
        final FieldInfo v1 = a1.getFieldInfo2();
        final ClassFile v2 = this.getClassFile2();
        if (v2.getFields().remove(v1)) {
            this.getMembers().remove(a1);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(a1.toString());
    }
    
    @Override
    public CtConstructor makeClassInitializer() throws CannotCompileException {
        final CtConstructor v1 = this.getClassInitializer();
        if (v1 != null) {
            return v1;
        }
        this.checkModify();
        final ClassFile v2 = this.getClassFile2();
        final Bytecode v3 = new Bytecode(v2.getConstPool(), 0, 0);
        this.modifyClassConstructor(v2, v3, 0, 0);
        return this.getClassInitializer();
    }
    
    @Override
    public void addConstructor(final CtConstructor a1) throws CannotCompileException {
        this.checkModify();
        if (a1.getDeclaringClass() != this) {
            throw new CannotCompileException("cannot add");
        }
        this.getMembers().addConstructor(a1);
        this.getClassFile2().addMethod(a1.getMethodInfo2());
    }
    
    @Override
    public void removeConstructor(final CtConstructor a1) throws NotFoundException {
        this.checkModify();
        final MethodInfo v1 = a1.getMethodInfo2();
        final ClassFile v2 = this.getClassFile2();
        if (v2.getMethods().remove(v1)) {
            this.getMembers().remove(a1);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(a1.toString());
    }
    
    @Override
    public void addMethod(final CtMethod a1) throws CannotCompileException {
        this.checkModify();
        if (a1.getDeclaringClass() != this) {
            throw new CannotCompileException("bad declaring class");
        }
        final int v1 = a1.getModifiers();
        if ((this.getModifiers() & 0x200) != 0x0) {
            if (Modifier.isProtected(v1) || Modifier.isPrivate(v1)) {
                throw new CannotCompileException("an interface method must be public: " + a1.toString());
            }
            a1.setModifiers(v1 | 0x1);
        }
        this.getMembers().addMethod(a1);
        this.getClassFile2().addMethod(a1.getMethodInfo2());
        if ((v1 & 0x400) != 0x0) {
            this.setModifiers(this.getModifiers() | 0x400);
        }
    }
    
    @Override
    public void removeMethod(final CtMethod a1) throws NotFoundException {
        this.checkModify();
        final MethodInfo v1 = a1.getMethodInfo2();
        final ClassFile v2 = this.getClassFile2();
        if (v2.getMethods().remove(v1)) {
            this.getMembers().remove(a1);
            this.gcConstPool = true;
            return;
        }
        throw new NotFoundException(a1.toString());
    }
    
    @Override
    public byte[] getAttribute(final String a1) {
        final AttributeInfo v1 = this.getClassFile2().getAttribute(a1);
        if (v1 == null) {
            return null;
        }
        return v1.get();
    }
    
    @Override
    public void setAttribute(final String a1, final byte[] a2) {
        this.checkModify();
        final ClassFile v1 = this.getClassFile2();
        v1.addAttribute(new AttributeInfo(v1.getConstPool(), a1, a2));
    }
    
    @Override
    public void instrument(final CodeConverter v-4) throws CannotCompileException {
        this.checkModify();
        final ClassFile classFile2 = this.getClassFile2();
        final ConstPool constPool = classFile2.getConstPool();
        final List methods = classFile2.getMethods();
        for (int v0 = methods.size(), v2 = 0; v2 < v0; ++v2) {
            final MethodInfo a1 = methods.get(v2);
            v-4.doit(this, a1, constPool);
        }
    }
    
    @Override
    public void instrument(final ExprEditor v-3) throws CannotCompileException {
        this.checkModify();
        final ClassFile classFile2 = this.getClassFile2();
        final List methods = classFile2.getMethods();
        for (int v0 = methods.size(), v2 = 0; v2 < v0; ++v2) {
            final MethodInfo a1 = methods.get(v2);
            v-3.doit(this, a1);
        }
    }
    
    @Override
    public void prune() {
        if (this.wasPruned) {
            return;
        }
        final boolean b = true;
        this.wasFrozen = b;
        this.wasPruned = b;
        this.getClassFile2().prune();
    }
    
    @Override
    public void rebuildClassFile() {
        this.gcConstPool = true;
    }
    
    @Override
    public void toBytecode(final DataOutputStream v0) throws CannotCompileException, IOException {
        try {
            if (this.isModified()) {
                this.checkPruned("toBytecode");
                final ClassFile a1 = this.getClassFile2();
                if (this.gcConstPool) {
                    a1.compact();
                    this.gcConstPool = false;
                }
                this.modifyClassConstructor(a1);
                this.modifyConstructors(a1);
                if (CtClassType.debugDump != null) {
                    this.dumpClassFile(a1);
                }
                a1.write(v0);
                v0.flush();
                this.fieldInitializers = null;
                if (this.doPruning) {
                    a1.prune();
                    this.wasPruned = true;
                }
            }
            else {
                this.classPool.writeClassfile(this.getName(), v0);
            }
            this.getCount = 0;
            this.wasFrozen = true;
        }
        catch (NotFoundException v) {
            throw new CannotCompileException(v);
        }
        catch (IOException v2) {
            throw new CannotCompileException(v2);
        }
    }
    
    private void dumpClassFile(final ClassFile a1) throws IOException {
        final DataOutputStream v1 = this.makeFileOutput(CtClassType.debugDump);
        try {
            a1.write(v1);
        }
        finally {
            v1.close();
        }
    }
    
    private void checkPruned(final String a1) {
        if (this.wasPruned) {
            throw new RuntimeException(a1 + "(): " + this.getName() + " was pruned.");
        }
    }
    
    @Override
    public boolean stopPruning(final boolean a1) {
        final boolean v1 = !this.doPruning;
        this.doPruning = !a1;
        return v1;
    }
    
    private void modifyClassConstructor(final ClassFile v-5) throws CannotCompileException, NotFoundException {
        if (this.fieldInitializers == null) {
            return;
        }
        final Bytecode bytecode = new Bytecode(v-5.getConstPool(), 0, 0);
        final Javac javac = new Javac(bytecode, this);
        int v-6 = 0;
        boolean b = false;
        for (FieldInitLink v0 = this.fieldInitializers; v0 != null; v0 = v0.next) {
            final CtField v2 = v0.field;
            if (Modifier.isStatic(v2.getModifiers())) {
                b = true;
                final int a1 = v0.init.compileIfStatic(v2.getType(), v2.getName(), bytecode, javac);
                if (v-6 < a1) {
                    v-6 = a1;
                }
            }
        }
        if (b) {
            this.modifyClassConstructor(v-5, bytecode, v-6, 0);
        }
    }
    
    private void modifyClassConstructor(final ClassFile v-8, final Bytecode v-7, final int v-6, final int v-5) throws CannotCompileException {
        MethodInfo staticInitializer = v-8.getStaticInitializer();
        if (staticInitializer == null) {
            v-7.add(177);
            v-7.setMaxStack(v-6);
            v-7.setMaxLocals(v-5);
            staticInitializer = new MethodInfo(v-8.getConstPool(), "<clinit>", "()V");
            staticInitializer.setAccessFlags(8);
            staticInitializer.setCodeAttribute(v-7.toCodeAttribute());
            v-8.addMethod(staticInitializer);
            final CtMember.Cache a1 = this.hasMemberCache();
            if (a1 != null) {
                a1.addConstructor(new CtConstructor(staticInitializer, this));
            }
        }
        else {
            final CodeAttribute codeAttribute = staticInitializer.getCodeAttribute();
            if (codeAttribute == null) {
                throw new CannotCompileException("empty <clinit>");
            }
            try {
                final CodeIterator a2 = codeAttribute.iterator();
                final int a3 = a2.insertEx(v-7.get());
                a2.insert(v-7.getExceptionTable(), a3);
                final int a4 = codeAttribute.getMaxStack();
                if (a4 < v-6) {
                    codeAttribute.setMaxStack(v-6);
                }
                final int v1 = codeAttribute.getMaxLocals();
                if (v1 < v-5) {
                    codeAttribute.setMaxLocals(v-5);
                }
            }
            catch (BadBytecode a5) {
                throw new CannotCompileException(a5);
            }
        }
        try {
            staticInitializer.rebuildStackMapIf6(this.classPool, v-8);
        }
        catch (BadBytecode a6) {
            throw new CannotCompileException(a6);
        }
    }
    
    private void modifyConstructors(final ClassFile v-7) throws CannotCompileException, NotFoundException {
        if (this.fieldInitializers == null) {
            return;
        }
        final ConstPool constPool = v-7.getConstPool();
        final List methods = v-7.getMethods();
        for (int size = methods.size(), i = 0; i < size; ++i) {
            final MethodInfo methodInfo = methods.get(i);
            if (methodInfo.isConstructor()) {
                final CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
                if (codeAttribute != null) {
                    try {
                        final Bytecode a1 = new Bytecode(constPool, 0, codeAttribute.getMaxLocals());
                        final CtClass[] v1 = Descriptor.getParameterTypes(methodInfo.getDescriptor(), this.classPool);
                        final int v2 = this.makeFieldInitializer(a1, v1);
                        insertAuxInitializer(codeAttribute, a1, v2);
                        methodInfo.rebuildStackMapIf6(this.classPool, v-7);
                    }
                    catch (BadBytecode v3) {
                        throw new CannotCompileException(v3);
                    }
                }
            }
        }
    }
    
    private static void insertAuxInitializer(final CodeAttribute a1, final Bytecode a2, final int a3) throws BadBytecode {
        final CodeIterator v1 = a1.iterator();
        int v2 = v1.skipSuperConstructor();
        if (v2 < 0) {
            v2 = v1.skipThisConstructor();
            if (v2 >= 0) {
                return;
            }
        }
        final int v3 = v1.insertEx(a2.get());
        v1.insert(a2.getExceptionTable(), v3);
        final int v4 = a1.getMaxStack();
        if (v4 < a3) {
            a1.setMaxStack(a3);
        }
    }
    
    private int makeFieldInitializer(final Bytecode v-4, final CtClass[] v-3) throws CannotCompileException, NotFoundException {
        int n = 0;
        final Javac javac = new Javac(v-4, this);
        try {
            javac.recordParams(v-3, false);
        }
        catch (CompileError a1) {
            throw new CannotCompileException(a1);
        }
        for (FieldInitLink v0 = this.fieldInitializers; v0 != null; v0 = v0.next) {
            final CtField v2 = v0.field;
            if (!Modifier.isStatic(v2.getModifiers())) {
                final int a2 = v0.init.compile(v2.getType(), v2.getName(), v-4, v-3, javac);
                if (n < a2) {
                    n = a2;
                }
            }
        }
        return n;
    }
    
    Hashtable getHiddenMethods() {
        if (this.hiddenMethods == null) {
            this.hiddenMethods = new Hashtable();
        }
        return this.hiddenMethods;
    }
    
    int getUniqueNumber() {
        return this.uniqueNumberSeed++;
    }
    
    @Override
    public String makeUniqueName(final String a1) {
        final HashMap v1 = new HashMap();
        this.makeMemberList(v1);
        final Set v2 = v1.keySet();
        final String[] v3 = new String[v2.size()];
        v2.toArray(v3);
        if (notFindInArray(a1, v3)) {
            return a1;
        }
        int v4 = 100;
        while (v4 <= 999) {
            final String v5 = a1 + v4++;
            if (notFindInArray(v5, v3)) {
                return v5;
            }
        }
        throw new RuntimeException("too many unique name");
    }
    
    private static boolean notFindInArray(final String a2, final String[] v1) {
        for (int v2 = v1.length, a3 = 0; a3 < v2; ++a3) {
            if (v1[a3].startsWith(a2)) {
                return false;
            }
        }
        return true;
    }
    
    private void makeMemberList(final HashMap v-3) {
        final int modifiers = this.getModifiers();
        Label_0076: {
            if (!Modifier.isAbstract(modifiers)) {
                if (!Modifier.isInterface(modifiers)) {
                    break Label_0076;
                }
            }
            try {
                for (final CtClass a1 : this.getInterfaces()) {
                    if (a1 != null && a1 instanceof CtClassType) {
                        ((CtClassType)a1).makeMemberList(v-3);
                    }
                }
            }
            catch (NotFoundException ex) {}
            try {
                final CtClass superclass = this.getSuperclass();
                if (superclass != null && superclass instanceof CtClassType) {
                    ((CtClassType)superclass).makeMemberList(v-3);
                }
            }
            catch (NotFoundException ex2) {}
        }
        List list = this.getClassFile2().getMethods();
        for (int v0 = list.size(), v2 = 0; v2 < v0; ++v2) {
            final MethodInfo v3 = list.get(v2);
            v-3.put(v3.getName(), this);
        }
        list = this.getClassFile2().getFields();
        for (int v0 = list.size(), v2 = 0; v2 < v0; ++v2) {
            final FieldInfo v4 = list.get(v2);
            v-3.put(v4.getName(), this);
        }
    }
}
