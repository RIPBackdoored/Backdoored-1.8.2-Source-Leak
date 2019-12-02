package javassist.bytecode;

import javassist.*;
import java.util.*;
import java.io.*;

public final class ClassFile
{
    int major;
    int minor;
    ConstPool constPool;
    int thisClass;
    int accessFlags;
    int superClass;
    int[] interfaces;
    ArrayList fields;
    ArrayList methods;
    ArrayList attributes;
    String thisclassname;
    String[] cachedInterfaces;
    String cachedSuperclass;
    public static final int JAVA_1 = 45;
    public static final int JAVA_2 = 46;
    public static final int JAVA_3 = 47;
    public static final int JAVA_4 = 48;
    public static final int JAVA_5 = 49;
    public static final int JAVA_6 = 50;
    public static final int JAVA_7 = 51;
    public static final int JAVA_8 = 52;
    public static final int MAJOR_VERSION;
    
    public ClassFile(final DataInputStream a1) throws IOException {
        super();
        this.read(a1);
    }
    
    public ClassFile(final boolean a1, final String a2, final String a3) {
        super();
        this.major = ClassFile.MAJOR_VERSION;
        this.minor = 0;
        this.constPool = new ConstPool(a2);
        this.thisClass = this.constPool.getThisClassInfo();
        if (a1) {
            this.accessFlags = 1536;
        }
        else {
            this.accessFlags = 32;
        }
        this.initSuperclass(a3);
        this.interfaces = null;
        this.fields = new ArrayList();
        this.methods = new ArrayList();
        this.thisclassname = a2;
        (this.attributes = new ArrayList()).add(new SourceFileAttribute(this.constPool, getSourcefileName(this.thisclassname)));
    }
    
    private void initSuperclass(final String a1) {
        if (a1 != null) {
            this.superClass = this.constPool.addClassInfo(a1);
            this.cachedSuperclass = a1;
        }
        else {
            this.superClass = this.constPool.addClassInfo("java.lang.Object");
            this.cachedSuperclass = "java.lang.Object";
        }
    }
    
    private static String getSourcefileName(String a1) {
        final int v1 = a1.lastIndexOf(46);
        if (v1 >= 0) {
            a1 = a1.substring(v1 + 1);
        }
        return a1 + ".java";
    }
    
    public void compact() {
        final ConstPool compact0 = this.compact0();
        ArrayList list = this.methods;
        for (int n = list.size(), v0 = 0; v0 < n; ++v0) {
            final MethodInfo v2 = list.get(v0);
            v2.compact(compact0);
        }
        list = this.fields;
        for (int n = list.size(), v0 = 0; v0 < n; ++v0) {
            final FieldInfo v3 = list.get(v0);
            v3.compact(compact0);
        }
        this.attributes = AttributeInfo.copyAll(this.attributes, compact0);
        this.constPool = compact0;
    }
    
    private ConstPool compact0() {
        final ConstPool constPool = new ConstPool(this.thisclassname);
        this.thisClass = constPool.getThisClassInfo();
        final String superclass = this.getSuperclass();
        if (superclass != null) {
            this.superClass = constPool.addClassInfo(this.getSuperclass());
        }
        if (this.interfaces != null) {
            for (int v0 = this.interfaces.length, v2 = 0; v2 < v0; ++v2) {
                this.interfaces[v2] = constPool.addClassInfo(this.constPool.getClassInfo(this.interfaces[v2]));
            }
        }
        return constPool;
    }
    
    public void prune() {
        final ConstPool compact0 = this.compact0();
        final ArrayList attributes = new ArrayList();
        AttributeInfo attributeInfo = this.getAttribute("RuntimeInvisibleAnnotations");
        if (attributeInfo != null) {
            attributeInfo = attributeInfo.copy(compact0, null);
            attributes.add(attributeInfo);
        }
        AttributeInfo attributeInfo2 = this.getAttribute("RuntimeVisibleAnnotations");
        if (attributeInfo2 != null) {
            attributeInfo2 = attributeInfo2.copy(compact0, null);
            attributes.add(attributeInfo2);
        }
        AttributeInfo attributeInfo3 = this.getAttribute("Signature");
        if (attributeInfo3 != null) {
            attributeInfo3 = attributeInfo3.copy(compact0, null);
            attributes.add(attributeInfo3);
        }
        ArrayList list = this.methods;
        for (int n = list.size(), v0 = 0; v0 < n; ++v0) {
            final MethodInfo v2 = list.get(v0);
            v2.prune(compact0);
        }
        list = this.fields;
        for (int n = list.size(), v0 = 0; v0 < n; ++v0) {
            final FieldInfo v3 = list.get(v0);
            v3.prune(compact0);
        }
        this.attributes = attributes;
        this.constPool = compact0;
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public boolean isInterface() {
        return (this.accessFlags & 0x200) != 0x0;
    }
    
    public boolean isFinal() {
        return (this.accessFlags & 0x10) != 0x0;
    }
    
    public boolean isAbstract() {
        return (this.accessFlags & 0x400) != 0x0;
    }
    
    public int getAccessFlags() {
        return this.accessFlags;
    }
    
    public void setAccessFlags(int a1) {
        if ((a1 & 0x200) == 0x0) {
            a1 |= 0x20;
        }
        this.accessFlags = a1;
    }
    
    public int getInnerAccessFlags() {
        final InnerClassesAttribute innerClassesAttribute = (InnerClassesAttribute)this.getAttribute("InnerClasses");
        if (innerClassesAttribute == null) {
            return -1;
        }
        final String name = this.getName();
        for (int v0 = innerClassesAttribute.tableLength(), v2 = 0; v2 < v0; ++v2) {
            if (name.equals(innerClassesAttribute.innerClass(v2))) {
                return innerClassesAttribute.accessFlags(v2);
            }
        }
        return -1;
    }
    
    public String getName() {
        return this.thisclassname;
    }
    
    public void setName(final String a1) {
        this.renameClass(this.thisclassname, a1);
    }
    
    public String getSuperclass() {
        if (this.cachedSuperclass == null) {
            this.cachedSuperclass = this.constPool.getClassInfo(this.superClass);
        }
        return this.cachedSuperclass;
    }
    
    public int getSuperclassId() {
        return this.superClass;
    }
    
    public void setSuperclass(String v-2) throws CannotCompileException {
        if (v-2 == null) {
            v-2 = "java.lang.Object";
        }
        try {
            this.superClass = this.constPool.addClassInfo(v-2);
            final ArrayList methods = this.methods;
            for (int v0 = methods.size(), v2 = 0; v2 < v0; ++v2) {
                final MethodInfo a1 = methods.get(v2);
                a1.setSuperclass(v-2);
            }
        }
        catch (BadBytecode a2) {
            throw new CannotCompileException(a2);
        }
        this.cachedSuperclass = v-2;
    }
    
    public final void renameClass(String v-3, String v-2) {
        if (v-3.equals(v-2)) {
            return;
        }
        if (v-3.equals(this.thisclassname)) {
            this.thisclassname = v-2;
        }
        v-3 = Descriptor.toJvmName(v-3);
        v-2 = Descriptor.toJvmName(v-2);
        this.constPool.renameClass(v-3, v-2);
        AttributeInfo.renameClass(this.attributes, v-3, v-2);
        ArrayList list = this.methods;
        for (int v0 = list.size(), v2 = 0; v2 < v0; ++v2) {
            final MethodInfo a1 = list.get(v2);
            final String a2 = a1.getDescriptor();
            a1.setDescriptor(Descriptor.rename(a2, v-3, v-2));
            AttributeInfo.renameClass(a1.getAttributes(), v-3, v-2);
        }
        list = this.fields;
        for (int v0 = list.size(), v2 = 0; v2 < v0; ++v2) {
            final FieldInfo v3 = list.get(v2);
            final String v4 = v3.getDescriptor();
            v3.setDescriptor(Descriptor.rename(v4, v-3, v-2));
            AttributeInfo.renameClass(v3.getAttributes(), v-3, v-2);
        }
    }
    
    public final void renameClass(final Map v-5) {
        final String a2 = v-5.get(Descriptor.toJvmName(this.thisclassname));
        if (a2 != null) {
            this.thisclassname = Descriptor.toJavaName(a2);
        }
        this.constPool.renameClass(v-5);
        AttributeInfo.renameClass(this.attributes, v-5);
        ArrayList list = this.methods;
        for (int n = list.size(), i = 0; i < n; ++i) {
            final MethodInfo a1 = list.get(i);
            final String v1 = a1.getDescriptor();
            a1.setDescriptor(Descriptor.rename(v1, v-5));
            AttributeInfo.renameClass(a1.getAttributes(), v-5);
        }
        list = this.fields;
        for (int n = list.size(), i = 0; i < n; ++i) {
            final FieldInfo v2 = list.get(i);
            final String v1 = v2.getDescriptor();
            v2.setDescriptor(Descriptor.rename(v1, v-5));
            AttributeInfo.renameClass(v2.getAttributes(), v-5);
        }
    }
    
    public final void getRefClasses(final Map v-4) {
        this.constPool.renameClass(v-4);
        AttributeInfo.getRefClasses(this.attributes, v-4);
        ArrayList list = this.methods;
        for (int n = list.size(), i = 0; i < n; ++i) {
            final MethodInfo a1 = list.get(i);
            final String v1 = a1.getDescriptor();
            Descriptor.rename(v1, v-4);
            AttributeInfo.getRefClasses(a1.getAttributes(), v-4);
        }
        list = this.fields;
        for (int n = list.size(), i = 0; i < n; ++i) {
            final FieldInfo v2 = list.get(i);
            final String v1 = v2.getDescriptor();
            Descriptor.rename(v1, v-4);
            AttributeInfo.getRefClasses(v2.getAttributes(), v-4);
        }
    }
    
    public String[] getInterfaces() {
        if (this.cachedInterfaces != null) {
            return this.cachedInterfaces;
        }
        String[] cachedInterfaces = null;
        if (this.interfaces == null) {
            cachedInterfaces = new String[0];
        }
        else {
            final int length = this.interfaces.length;
            final String[] v0 = new String[length];
            for (int v2 = 0; v2 < length; ++v2) {
                v0[v2] = this.constPool.getClassInfo(this.interfaces[v2]);
            }
            cachedInterfaces = v0;
        }
        return this.cachedInterfaces = cachedInterfaces;
    }
    
    public void setInterfaces(final String[] v0) {
        this.cachedInterfaces = null;
        if (v0 != null) {
            final int v = v0.length;
            this.interfaces = new int[v];
            for (int a1 = 0; a1 < v; ++a1) {
                this.interfaces[a1] = this.constPool.addClassInfo(v0[a1]);
            }
        }
    }
    
    public void addInterface(final String v-2) {
        this.cachedInterfaces = null;
        final int addClassInfo = this.constPool.addClassInfo(v-2);
        if (this.interfaces == null) {
            (this.interfaces = new int[1])[0] = addClassInfo;
        }
        else {
            final int a1 = this.interfaces.length;
            final int[] v1 = new int[a1 + 1];
            System.arraycopy(this.interfaces, 0, v1, 0, a1);
            v1[a1] = addClassInfo;
            this.interfaces = v1;
        }
    }
    
    public List getFields() {
        return this.fields;
    }
    
    public void addField(final FieldInfo a1) throws DuplicateMemberException {
        this.testExistingField(a1.getName(), a1.getDescriptor());
        this.fields.add(a1);
    }
    
    public final void addField2(final FieldInfo a1) {
        this.fields.add(a1);
    }
    
    private void testExistingField(final String v1, final String v2) throws DuplicateMemberException {
        final ListIterator v3 = this.fields.listIterator(0);
        while (v3.hasNext()) {
            final FieldInfo a1 = v3.next();
            if (a1.getName().equals(v1)) {
                throw new DuplicateMemberException("duplicate field: " + v1);
            }
        }
    }
    
    public List getMethods() {
        return this.methods;
    }
    
    public MethodInfo getMethod(final String v-2) {
        final ArrayList methods = this.methods;
        for (int v0 = methods.size(), v2 = 0; v2 < v0; ++v2) {
            final MethodInfo a1 = methods.get(v2);
            if (a1.getName().equals(v-2)) {
                return a1;
            }
        }
        return null;
    }
    
    public MethodInfo getStaticInitializer() {
        return this.getMethod("<clinit>");
    }
    
    public void addMethod(final MethodInfo a1) throws DuplicateMemberException {
        this.testExistingMethod(a1);
        this.methods.add(a1);
    }
    
    public final void addMethod2(final MethodInfo a1) {
        this.methods.add(a1);
    }
    
    private void testExistingMethod(final MethodInfo a1) throws DuplicateMemberException {
        final String v1 = a1.getName();
        final String v2 = a1.getDescriptor();
        final ListIterator v3 = this.methods.listIterator(0);
        while (v3.hasNext()) {
            if (isDuplicated(a1, v1, v2, v3.next(), v3)) {
                throw new DuplicateMemberException("duplicate method: " + v1 + " in " + this.getName());
            }
        }
    }
    
    private static boolean isDuplicated(final MethodInfo a1, final String a2, final String a3, final MethodInfo a4, final ListIterator a5) {
        if (!a4.getName().equals(a2)) {
            return false;
        }
        final String v1 = a4.getDescriptor();
        if (!Descriptor.eqParamTypes(v1, a3)) {
            return false;
        }
        if (!v1.equals(a3)) {
            return false;
        }
        if (notBridgeMethod(a4)) {
            return true;
        }
        a5.remove();
        return false;
    }
    
    private static boolean notBridgeMethod(final MethodInfo a1) {
        return (a1.getAccessFlags() & 0x40) == 0x0;
    }
    
    public List getAttributes() {
        return this.attributes;
    }
    
    public AttributeInfo getAttribute(final String v-2) {
        final ArrayList attributes = this.attributes;
        for (int v0 = attributes.size(), v2 = 0; v2 < v0; ++v2) {
            final AttributeInfo a1 = attributes.get(v2);
            if (a1.getName().equals(v-2)) {
                return a1;
            }
        }
        return null;
    }
    
    public AttributeInfo removeAttribute(final String a1) {
        return AttributeInfo.remove(this.attributes, a1);
    }
    
    public void addAttribute(final AttributeInfo a1) {
        AttributeInfo.remove(this.attributes, a1.getName());
        this.attributes.add(a1);
    }
    
    public String getSourceFile() {
        final SourceFileAttribute v1 = (SourceFileAttribute)this.getAttribute("SourceFile");
        if (v1 == null) {
            return null;
        }
        return v1.getFileName();
    }
    
    private void read(final DataInputStream v2) throws IOException {
        final int v3 = v2.readInt();
        if (v3 != -889275714) {
            throw new IOException("bad magic number: " + Integer.toHexString(v3));
        }
        this.minor = v2.readUnsignedShort();
        this.major = v2.readUnsignedShort();
        this.constPool = new ConstPool(v2);
        this.accessFlags = v2.readUnsignedShort();
        this.thisClass = v2.readUnsignedShort();
        this.constPool.setThisClassInfo(this.thisClass);
        this.superClass = v2.readUnsignedShort();
        int v4 = v2.readUnsignedShort();
        if (v4 == 0) {
            this.interfaces = null;
        }
        else {
            this.interfaces = new int[v4];
            for (int a1 = 0; a1 < v4; ++a1) {
                this.interfaces[a1] = v2.readUnsignedShort();
            }
        }
        final ConstPool v5 = this.constPool;
        v4 = v2.readUnsignedShort();
        this.fields = new ArrayList();
        for (int v6 = 0; v6 < v4; ++v6) {
            this.addField2(new FieldInfo(v5, v2));
        }
        v4 = v2.readUnsignedShort();
        this.methods = new ArrayList();
        for (int v6 = 0; v6 < v4; ++v6) {
            this.addMethod2(new MethodInfo(v5, v2));
        }
        this.attributes = new ArrayList();
        v4 = v2.readUnsignedShort();
        for (int v6 = 0; v6 < v4; ++v6) {
            this.addAttribute(AttributeInfo.read(v5, v2));
        }
        this.thisclassname = this.constPool.getClassInfo(this.thisClass);
    }
    
    public void write(final DataOutputStream v-3) throws IOException {
        v-3.writeInt(-889275714);
        v-3.writeShort(this.minor);
        v-3.writeShort(this.major);
        this.constPool.write(v-3);
        v-3.writeShort(this.accessFlags);
        v-3.writeShort(this.thisClass);
        v-3.writeShort(this.superClass);
        int n = 0;
        if (this.interfaces == null) {
            final int a1 = 0;
        }
        else {
            n = this.interfaces.length;
        }
        v-3.writeShort(n);
        for (int i = 0; i < n; ++i) {
            v-3.writeShort(this.interfaces[i]);
        }
        ArrayList v0 = this.fields;
        n = v0.size();
        v-3.writeShort(n);
        for (int i = 0; i < n; ++i) {
            final FieldInfo v2 = v0.get(i);
            v2.write(v-3);
        }
        v0 = this.methods;
        n = v0.size();
        v-3.writeShort(n);
        for (int i = 0; i < n; ++i) {
            final MethodInfo v3 = v0.get(i);
            v3.write(v-3);
        }
        v-3.writeShort(this.attributes.size());
        AttributeInfo.writeAll(this.attributes, v-3);
    }
    
    public int getMajorVersion() {
        return this.major;
    }
    
    public void setMajorVersion(final int a1) {
        this.major = a1;
    }
    
    public int getMinorVersion() {
        return this.minor;
    }
    
    public void setMinorVersion(final int a1) {
        this.minor = a1;
    }
    
    public void setVersionToJava5() {
        this.major = 49;
        this.minor = 0;
    }
    
    static {
        int v1 = 47;
        try {
            Class.forName("java.lang.StringBuilder");
            v1 = 49;
            Class.forName("java.util.zip.DeflaterInputStream");
            v1 = 50;
            Class.forName("java.lang.invoke.CallSite");
            v1 = 51;
            Class.forName("java.util.function.Function");
            v1 = 52;
        }
        catch (Throwable t) {}
        MAJOR_VERSION = v1;
    }
}
