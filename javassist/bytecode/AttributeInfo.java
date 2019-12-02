package javassist.bytecode;

import java.io.*;
import java.util.*;

public class AttributeInfo
{
    protected ConstPool constPool;
    int name;
    byte[] info;
    
    protected AttributeInfo(final ConstPool a1, final int a2, final byte[] a3) {
        super();
        this.constPool = a1;
        this.name = a2;
        this.info = a3;
    }
    
    protected AttributeInfo(final ConstPool a1, final String a2) {
        this(a1, a2, null);
    }
    
    public AttributeInfo(final ConstPool a1, final String a2, final byte[] a3) {
        this(a1, a1.addUtf8Info(a2), a3);
    }
    
    protected AttributeInfo(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super();
        this.constPool = a1;
        this.name = a2;
        final int v1 = a3.readInt();
        this.info = new byte[v1];
        if (v1 > 0) {
            a3.readFully(this.info);
        }
    }
    
    static AttributeInfo read(final ConstPool a1, final DataInputStream a2) throws IOException {
        final int v1 = a2.readUnsignedShort();
        final String v2 = a1.getUtf8Info(v1);
        final char v3 = v2.charAt(0);
        if (v3 < 'M') {
            if (v3 < 'E') {
                if (v2.equals("AnnotationDefault")) {
                    return new AnnotationDefaultAttribute(a1, v1, a2);
                }
                if (v2.equals("BootstrapMethods")) {
                    return new BootstrapMethodsAttribute(a1, v1, a2);
                }
                if (v2.equals("Code")) {
                    return new CodeAttribute(a1, v1, a2);
                }
                if (v2.equals("ConstantValue")) {
                    return new ConstantAttribute(a1, v1, a2);
                }
                if (v2.equals("Deprecated")) {
                    return new DeprecatedAttribute(a1, v1, a2);
                }
            }
            else {
                if (v2.equals("EnclosingMethod")) {
                    return new EnclosingMethodAttribute(a1, v1, a2);
                }
                if (v2.equals("Exceptions")) {
                    return new ExceptionsAttribute(a1, v1, a2);
                }
                if (v2.equals("InnerClasses")) {
                    return new InnerClassesAttribute(a1, v1, a2);
                }
                if (v2.equals("LineNumberTable")) {
                    return new LineNumberAttribute(a1, v1, a2);
                }
                if (v2.equals("LocalVariableTable")) {
                    return new LocalVariableAttribute(a1, v1, a2);
                }
                if (v2.equals("LocalVariableTypeTable")) {
                    return new LocalVariableTypeAttribute(a1, v1, a2);
                }
            }
        }
        else if (v3 < 'S') {
            if (v2.equals("MethodParameters")) {
                return new MethodParametersAttribute(a1, v1, a2);
            }
            if (v2.equals("RuntimeVisibleAnnotations") || v2.equals("RuntimeInvisibleAnnotations")) {
                return new AnnotationsAttribute(a1, v1, a2);
            }
            if (v2.equals("RuntimeVisibleParameterAnnotations") || v2.equals("RuntimeInvisibleParameterAnnotations")) {
                return new ParameterAnnotationsAttribute(a1, v1, a2);
            }
            if (v2.equals("RuntimeVisibleTypeAnnotations") || v2.equals("RuntimeInvisibleTypeAnnotations")) {
                return new TypeAnnotationsAttribute(a1, v1, a2);
            }
        }
        else {
            if (v2.equals("Signature")) {
                return new SignatureAttribute(a1, v1, a2);
            }
            if (v2.equals("SourceFile")) {
                return new SourceFileAttribute(a1, v1, a2);
            }
            if (v2.equals("Synthetic")) {
                return new SyntheticAttribute(a1, v1, a2);
            }
            if (v2.equals("StackMap")) {
                return new StackMap(a1, v1, a2);
            }
            if (v2.equals("StackMapTable")) {
                return new StackMapTable(a1, v1, a2);
            }
        }
        return new AttributeInfo(a1, v1, a2);
    }
    
    public String getName() {
        return this.constPool.getUtf8Info(this.name);
    }
    
    public ConstPool getConstPool() {
        return this.constPool;
    }
    
    public int length() {
        return this.info.length + 6;
    }
    
    public byte[] get() {
        return this.info;
    }
    
    public void set(final byte[] a1) {
        this.info = a1;
    }
    
    public AttributeInfo copy(final ConstPool v1, final Map v2) {
        final int v3 = this.info.length;
        final byte[] v4 = this.info;
        final byte[] v5 = new byte[v3];
        for (int a1 = 0; a1 < v3; ++a1) {
            v5[a1] = v4[a1];
        }
        return new AttributeInfo(v1, this.getName(), v5);
    }
    
    void write(final DataOutputStream a1) throws IOException {
        a1.writeShort(this.name);
        a1.writeInt(this.info.length);
        if (this.info.length > 0) {
            a1.write(this.info);
        }
    }
    
    static int getLength(final ArrayList v-2) {
        int n = 0;
        for (int v0 = v-2.size(), v2 = 0; v2 < v0; ++v2) {
            final AttributeInfo a1 = v-2.get(v2);
            n += a1.length();
        }
        return n;
    }
    
    static AttributeInfo lookup(final ArrayList a2, final String v1) {
        if (a2 == null) {
            return null;
        }
        final ListIterator v2 = a2.listIterator();
        while (v2.hasNext()) {
            final AttributeInfo a3 = v2.next();
            if (a3.getName().equals(v1)) {
                return a3;
            }
        }
        return null;
    }
    
    static synchronized AttributeInfo remove(final ArrayList a2, final String v1) {
        if (a2 == null) {
            return null;
        }
        AttributeInfo v2 = null;
        final ListIterator v3 = a2.listIterator();
        while (v3.hasNext()) {
            final AttributeInfo a3 = v3.next();
            if (a3.getName().equals(v1)) {
                v3.remove();
                v2 = a3;
            }
        }
        return v2;
    }
    
    static void writeAll(final ArrayList v1, final DataOutputStream v2) throws IOException {
        if (v1 == null) {
            return;
        }
        for (int v3 = v1.size(), a2 = 0; a2 < v3; ++a2) {
            final AttributeInfo a3 = v1.get(a2);
            a3.write(v2);
        }
    }
    
    static ArrayList copyAll(final ArrayList v1, final ConstPool v2) {
        if (v1 == null) {
            return null;
        }
        final ArrayList v3 = new ArrayList();
        for (int v4 = v1.size(), a2 = 0; a2 < v4; ++a2) {
            final AttributeInfo a3 = v1.get(a2);
            v3.add(a3.copy(v2, null));
        }
        return v3;
    }
    
    void renameClass(final String a1, final String a2) {
    }
    
    void renameClass(final Map a1) {
    }
    
    static void renameClass(final List a2, final String a3, final String v1) {
        for (final AttributeInfo a4 : a2) {
            a4.renameClass(a3, v1);
        }
    }
    
    static void renameClass(final List a2, final Map v1) {
        for (final AttributeInfo a3 : a2) {
            a3.renameClass(v1);
        }
    }
    
    void getRefClasses(final Map a1) {
    }
    
    static void getRefClasses(final List a2, final Map v1) {
        for (final AttributeInfo a3 : a2) {
            a3.getRefClasses(v1);
        }
    }
}
