package javassist.bytecode;

import java.util.*;
import javassist.*;
import javassist.bytecode.stackmap.*;
import java.io.*;

public class MethodInfo
{
    ConstPool constPool;
    int accessFlags;
    int name;
    String cachedName;
    int descriptor;
    ArrayList attribute;
    public static boolean doPreverify;
    public static final String nameInit = "<init>";
    public static final String nameClinit = "<clinit>";
    
    private MethodInfo(final ConstPool a1) {
        super();
        this.constPool = a1;
        this.attribute = null;
    }
    
    public MethodInfo(final ConstPool a1, final String a2, final String a3) {
        this(a1);
        this.accessFlags = 0;
        this.name = a1.addUtf8Info(a2);
        this.cachedName = a2;
        this.descriptor = this.constPool.addUtf8Info(a3);
    }
    
    MethodInfo(final ConstPool a1, final DataInputStream a2) throws IOException {
        this(a1);
        this.read(a2);
    }
    
    public MethodInfo(final ConstPool a1, final String a2, final MethodInfo a3, final Map a4) throws BadBytecode {
        this(a1);
        this.read(a3, a2, a4);
    }
    
    @Override
    public String toString() {
        return this.getName() + " " + this.getDescriptor();
    }
    
    void compact(final ConstPool a1) {
        this.name = a1.addUtf8Info(this.getName());
        this.descriptor = a1.addUtf8Info(this.getDescriptor());
        this.attribute = AttributeInfo.copyAll(this.attribute, a1);
        this.constPool = a1;
    }
    
    void prune(final ConstPool a1) {
        final ArrayList v1 = new ArrayList();
        AttributeInfo v2 = this.getAttribute("RuntimeInvisibleAnnotations");
        if (v2 != null) {
            v2 = v2.copy(a1, null);
            v1.add(v2);
        }
        AttributeInfo v3 = this.getAttribute("RuntimeVisibleAnnotations");
        if (v3 != null) {
            v3 = v3.copy(a1, null);
            v1.add(v3);
        }
        AttributeInfo v4 = this.getAttribute("RuntimeInvisibleParameterAnnotations");
        if (v4 != null) {
            v4 = v4.copy(a1, null);
            v1.add(v4);
        }
        AttributeInfo v5 = this.getAttribute("RuntimeVisibleParameterAnnotations");
        if (v5 != null) {
            v5 = v5.copy(a1, null);
            v1.add(v5);
        }
        final AnnotationDefaultAttribute v6 = (AnnotationDefaultAttribute)this.getAttribute("AnnotationDefault");
        if (v6 != null) {
            v1.add(v6);
        }
        final ExceptionsAttribute v7 = this.getExceptionsAttribute();
        if (v7 != null) {
            v1.add(v7);
        }
        AttributeInfo v8 = this.getAttribute("Signature");
        if (v8 != null) {
            v8 = v8.copy(a1, null);
            v1.add(v8);
        }
        this.attribute = v1;
        this.name = a1.addUtf8Info(this.getName());
        this.descriptor = a1.addUtf8Info(this.getDescriptor());
        this.constPool = a1;
    }
    
    public String getName() {
        if (this.cachedName == null) {
            this.cachedName = this.constPool.getUtf8Info(this.name);
        }
        return this.cachedName;
    }
    
    public void setName(final String a1) {
        this.name = this.constPool.addUtf8Info(a1);
        this.cachedName = a1;
    }
    
    public boolean isMethod() {
        final String v1 = this.getName();
        return !v1.equals("<init>") && !v1.equals("<clinit>");
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public boolean isConstructor() {
        return this.getName().equals("<init>");
    }
    
    public boolean isStaticInitializer() {
        return this.getName().equals("<clinit>");
    }
    
    public int getAccessFlags() {
        return this.accessFlags;
    }
    
    public void setAccessFlags(final int a1) {
        this.accessFlags = a1;
    }
    
    public String getDescriptor() {
        return this.constPool.getUtf8Info(this.descriptor);
    }
    
    public void setDescriptor(final String a1) {
        if (!a1.equals(this.getDescriptor())) {
            this.descriptor = this.constPool.addUtf8Info(a1);
        }
    }
    
    public List getAttributes() {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        return this.attribute;
    }
    
    public AttributeInfo getAttribute(final String a1) {
        return AttributeInfo.lookup(this.attribute, a1);
    }
    
    public AttributeInfo removeAttribute(final String a1) {
        return AttributeInfo.remove(this.attribute, a1);
    }
    
    public void addAttribute(final AttributeInfo a1) {
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        AttributeInfo.remove(this.attribute, a1.getName());
        this.attribute.add(a1);
    }
    
    public ExceptionsAttribute getExceptionsAttribute() {
        final AttributeInfo v1 = AttributeInfo.lookup(this.attribute, "Exceptions");
        return (ExceptionsAttribute)v1;
    }
    
    public CodeAttribute getCodeAttribute() {
        final AttributeInfo v1 = AttributeInfo.lookup(this.attribute, "Code");
        return (CodeAttribute)v1;
    }
    
    public void removeExceptionsAttribute() {
        AttributeInfo.remove(this.attribute, "Exceptions");
    }
    
    public void setExceptionsAttribute(final ExceptionsAttribute a1) {
        this.removeExceptionsAttribute();
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        this.attribute.add(a1);
    }
    
    public void removeCodeAttribute() {
        AttributeInfo.remove(this.attribute, "Code");
    }
    
    public void setCodeAttribute(final CodeAttribute a1) {
        this.removeCodeAttribute();
        if (this.attribute == null) {
            this.attribute = new ArrayList();
        }
        this.attribute.add(a1);
    }
    
    public void rebuildStackMapIf6(final ClassPool a1, final ClassFile a2) throws BadBytecode {
        if (a2.getMajorVersion() >= 50) {
            this.rebuildStackMap(a1);
        }
        if (MethodInfo.doPreverify) {
            this.rebuildStackMapForME(a1);
        }
    }
    
    public void rebuildStackMap(final ClassPool v2) throws BadBytecode {
        final CodeAttribute v3 = this.getCodeAttribute();
        if (v3 != null) {
            final StackMapTable a1 = MapMaker.make(v2, this);
            v3.setAttribute(a1);
        }
    }
    
    public void rebuildStackMapForME(final ClassPool v2) throws BadBytecode {
        final CodeAttribute v3 = this.getCodeAttribute();
        if (v3 != null) {
            final StackMap a1 = MapMaker.make2(v2, this);
            v3.setAttribute(a1);
        }
    }
    
    public int getLineNumber(final int a1) {
        final CodeAttribute v1 = this.getCodeAttribute();
        if (v1 == null) {
            return -1;
        }
        final LineNumberAttribute v2 = (LineNumberAttribute)v1.getAttribute("LineNumberTable");
        if (v2 == null) {
            return -1;
        }
        return v2.toLineNumber(a1);
    }
    
    public void setSuperclass(final String v-5) throws BadBytecode {
        if (!this.isConstructor()) {
            return;
        }
        final CodeAttribute codeAttribute = this.getCodeAttribute();
        final byte[] code = codeAttribute.getCode();
        final CodeIterator iterator = codeAttribute.iterator();
        final int skipSuperConstructor = iterator.skipSuperConstructor();
        if (skipSuperConstructor >= 0) {
            final ConstPool a1 = this.constPool;
            final int v1 = ByteArray.readU16bit(code, skipSuperConstructor + 1);
            final int v2 = a1.getMethodrefNameAndType(v1);
            final int v3 = a1.addClassInfo(v-5);
            final int v4 = a1.addMethodrefInfo(v3, v2);
            ByteArray.write16bit(v4, code, skipSuperConstructor + 1);
        }
    }
    
    private void read(final MethodInfo a1, final String a2, final Map a3) throws BadBytecode {
        final ConstPool v1 = this.constPool;
        this.accessFlags = a1.accessFlags;
        this.name = v1.addUtf8Info(a2);
        this.cachedName = a2;
        final ConstPool v2 = a1.constPool;
        final String v3 = v2.getUtf8Info(a1.descriptor);
        final String v4 = Descriptor.rename(v3, a3);
        this.descriptor = v1.addUtf8Info(v4);
        this.attribute = new ArrayList();
        final ExceptionsAttribute v5 = a1.getExceptionsAttribute();
        if (v5 != null) {
            this.attribute.add(v5.copy(v1, a3));
        }
        final CodeAttribute v6 = a1.getCodeAttribute();
        if (v6 != null) {
            this.attribute.add(v6.copy(v1, a3));
        }
    }
    
    private void read(final DataInputStream v2) throws IOException {
        this.accessFlags = v2.readUnsignedShort();
        this.name = v2.readUnsignedShort();
        this.descriptor = v2.readUnsignedShort();
        final int v3 = v2.readUnsignedShort();
        this.attribute = new ArrayList();
        for (int a1 = 0; a1 < v3; ++a1) {
            this.attribute.add(AttributeInfo.read(this.constPool, v2));
        }
    }
    
    void write(final DataOutputStream a1) throws IOException {
        a1.writeShort(this.accessFlags);
        a1.writeShort(this.name);
        a1.writeShort(this.descriptor);
        if (this.attribute == null) {
            a1.writeShort(0);
        }
        else {
            a1.writeShort(this.attribute.size());
            AttributeInfo.writeAll(this.attribute, a1);
        }
    }
    
    static {
        MethodInfo.doPreverify = false;
    }
}
