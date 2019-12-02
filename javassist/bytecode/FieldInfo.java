package javassist.bytecode;

import java.util.*;
import java.io.*;

public final class FieldInfo
{
    ConstPool constPool;
    int accessFlags;
    int name;
    String cachedName;
    String cachedType;
    int descriptor;
    ArrayList attribute;
    
    private FieldInfo(final ConstPool a1) {
        super();
        this.constPool = a1;
        this.accessFlags = 0;
        this.attribute = null;
    }
    
    public FieldInfo(final ConstPool a1, final String a2, final String a3) {
        this(a1);
        this.name = a1.addUtf8Info(a2);
        this.cachedName = a2;
        this.descriptor = a1.addUtf8Info(a3);
    }
    
    FieldInfo(final ConstPool a1, final DataInputStream a2) throws IOException {
        this(a1);
        this.read(a2);
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
        AttributeInfo v4 = this.getAttribute("Signature");
        if (v4 != null) {
            v4 = v4.copy(a1, null);
            v1.add(v4);
        }
        int v5 = this.getConstantValue();
        if (v5 != 0) {
            v5 = this.constPool.copy(v5, a1, null);
            v1.add(new ConstantAttribute(a1, v5));
        }
        this.attribute = v1;
        this.name = a1.addUtf8Info(this.getName());
        this.descriptor = a1.addUtf8Info(this.getDescriptor());
        this.constPool = a1;
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
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
    
    public int getConstantValue() {
        if ((this.accessFlags & 0x8) == 0x0) {
            return 0;
        }
        final ConstantAttribute v1 = (ConstantAttribute)this.getAttribute("ConstantValue");
        if (v1 == null) {
            return 0;
        }
        return v1.getConstantValue();
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
}
