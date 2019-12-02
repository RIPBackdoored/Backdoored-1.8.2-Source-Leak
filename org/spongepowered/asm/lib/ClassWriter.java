package org.spongepowered.asm.lib;

public class ClassWriter extends ClassVisitor
{
    public static final int COMPUTE_MAXS = 1;
    public static final int COMPUTE_FRAMES = 2;
    static final int ACC_SYNTHETIC_ATTRIBUTE = 262144;
    static final int TO_ACC_SYNTHETIC = 64;
    static final int NOARG_INSN = 0;
    static final int SBYTE_INSN = 1;
    static final int SHORT_INSN = 2;
    static final int VAR_INSN = 3;
    static final int IMPLVAR_INSN = 4;
    static final int TYPE_INSN = 5;
    static final int FIELDORMETH_INSN = 6;
    static final int ITFMETH_INSN = 7;
    static final int INDYMETH_INSN = 8;
    static final int LABEL_INSN = 9;
    static final int LABELW_INSN = 10;
    static final int LDC_INSN = 11;
    static final int LDCW_INSN = 12;
    static final int IINC_INSN = 13;
    static final int TABL_INSN = 14;
    static final int LOOK_INSN = 15;
    static final int MANA_INSN = 16;
    static final int WIDE_INSN = 17;
    static final int ASM_LABEL_INSN = 18;
    static final int F_INSERT = 256;
    static final byte[] TYPE;
    static final int CLASS = 7;
    static final int FIELD = 9;
    static final int METH = 10;
    static final int IMETH = 11;
    static final int STR = 8;
    static final int INT = 3;
    static final int FLOAT = 4;
    static final int LONG = 5;
    static final int DOUBLE = 6;
    static final int NAME_TYPE = 12;
    static final int UTF8 = 1;
    static final int MTYPE = 16;
    static final int HANDLE = 15;
    static final int INDY = 18;
    static final int HANDLE_BASE = 20;
    static final int TYPE_NORMAL = 30;
    static final int TYPE_UNINIT = 31;
    static final int TYPE_MERGED = 32;
    static final int BSM = 33;
    ClassReader cr;
    int version;
    int index;
    final ByteVector pool;
    Item[] items;
    int threshold;
    final Item key;
    final Item key2;
    final Item key3;
    final Item key4;
    Item[] typeTable;
    private short typeCount;
    private int access;
    private int name;
    String thisName;
    private int signature;
    private int superName;
    private int interfaceCount;
    private int[] interfaces;
    private int sourceFile;
    private ByteVector sourceDebug;
    private int enclosingMethodOwner;
    private int enclosingMethod;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private Attribute attrs;
    private int innerClassesCount;
    private ByteVector innerClasses;
    int bootstrapMethodsCount;
    ByteVector bootstrapMethods;
    FieldWriter firstField;
    FieldWriter lastField;
    MethodWriter firstMethod;
    MethodWriter lastMethod;
    private int compute;
    boolean hasAsmInsns;
    
    public ClassWriter(final int a1) {
        super(327680);
        this.index = 1;
        this.pool = new ByteVector();
        this.items = new Item[256];
        this.threshold = (int)(0.75 * this.items.length);
        this.key = new Item();
        this.key2 = new Item();
        this.key3 = new Item();
        this.key4 = new Item();
        this.compute = (((a1 & 0x2) != 0x0) ? 0 : (((a1 & 0x1) != 0x0) ? 2 : 3));
    }
    
    public ClassWriter(final ClassReader a1, final int a2) {
        this(a2);
        a1.copyPool(this);
        this.cr = a1;
    }
    
    @Override
    public final void visit(final int a3, final int a4, final String a5, final String a6, final String v1, final String[] v2) {
        this.version = a3;
        this.access = a4;
        this.name = this.newClass(a5);
        this.thisName = a5;
        if (a6 != null) {
            this.signature = this.newUTF8(a6);
        }
        this.superName = ((v1 == null) ? 0 : this.newClass(v1));
        if (v2 != null && v2.length > 0) {
            this.interfaceCount = v2.length;
            this.interfaces = new int[this.interfaceCount];
            for (int a7 = 0; a7 < this.interfaceCount; ++a7) {
                this.interfaces[a7] = this.newClass(v2[a7]);
            }
        }
    }
    
    @Override
    public final void visitSource(final String a1, final String a2) {
        if (a1 != null) {
            this.sourceFile = this.newUTF8(a1);
        }
        if (a2 != null) {
            this.sourceDebug = new ByteVector().encodeUTF8(a2, 0, 0);
        }
    }
    
    @Override
    public final void visitOuterClass(final String a1, final String a2, final String a3) {
        this.enclosingMethodOwner = this.newClass(a1);
        if (a2 != null && a3 != null) {
            this.enclosingMethod = this.newNameType(a2, a3);
        }
    }
    
    @Override
    public final AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final ByteVector v1 = new ByteVector();
        v1.putShort(this.newUTF8(a1)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this, true, v1, v1, 2);
        if (a2) {
            v2.next = this.anns;
            this.anns = v2;
        }
        else {
            v2.next = this.ianns;
            this.ianns = v2;
        }
        return v2;
    }
    
    @Override
    public final AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final ByteVector v1 = new ByteVector();
        AnnotationWriter.putTarget(a1, a2, v1);
        v1.putShort(this.newUTF8(a3)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this, true, v1, v1, v1.length - 2);
        if (a4) {
            v2.next = this.tanns;
            this.tanns = v2;
        }
        else {
            v2.next = this.itanns;
            this.itanns = v2;
        }
        return v2;
    }
    
    @Override
    public final void visitAttribute(final Attribute a1) {
        a1.next = this.attrs;
        this.attrs = a1;
    }
    
    @Override
    public final void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        if (this.innerClasses == null) {
            this.innerClasses = new ByteVector();
        }
        final Item v1 = this.newClassItem(a1);
        if (v1.intVal == 0) {
            ++this.innerClassesCount;
            this.innerClasses.putShort(v1.index);
            this.innerClasses.putShort((a2 == null) ? 0 : this.newClass(a2));
            this.innerClasses.putShort((a3 == null) ? 0 : this.newUTF8(a3));
            this.innerClasses.putShort(a4);
            v1.intVal = this.innerClassesCount;
        }
    }
    
    @Override
    public final FieldVisitor visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        return new FieldWriter(this, a1, a2, a3, a4, a5);
    }
    
    @Override
    public final MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        return new MethodWriter(this, a1, a2, a3, a4, a5, this.compute);
    }
    
    @Override
    public final void visitEnd() {
    }
    
    public byte[] toByteArray() {
        if (this.index > 65535) {
            throw new RuntimeException("Class file too large!");
        }
        int a1 = 24 + 2 * this.interfaceCount;
        int a2 = 0;
        for (FieldWriter fieldWriter = this.firstField; fieldWriter != null; fieldWriter = (FieldWriter)fieldWriter.fv) {
            ++a2;
            a1 += fieldWriter.getSize();
        }
        int a3 = 0;
        for (MethodWriter methodWriter = this.firstMethod; methodWriter != null; methodWriter = (MethodWriter)methodWriter.mv) {
            ++a3;
            a1 += methodWriter.getSize();
        }
        int a4 = 0;
        if (this.bootstrapMethods != null) {
            ++a4;
            a1 += 8 + this.bootstrapMethods.length;
            this.newUTF8("BootstrapMethods");
        }
        if (this.signature != 0) {
            ++a4;
            a1 += 8;
            this.newUTF8("Signature");
        }
        if (this.sourceFile != 0) {
            ++a4;
            a1 += 8;
            this.newUTF8("SourceFile");
        }
        if (this.sourceDebug != null) {
            ++a4;
            a1 += this.sourceDebug.length + 6;
            this.newUTF8("SourceDebugExtension");
        }
        if (this.enclosingMethodOwner != 0) {
            ++a4;
            a1 += 10;
            this.newUTF8("EnclosingMethod");
        }
        if ((this.access & 0x20000) != 0x0) {
            ++a4;
            a1 += 6;
            this.newUTF8("Deprecated");
        }
        if ((this.access & 0x1000) != 0x0 && ((this.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            ++a4;
            a1 += 6;
            this.newUTF8("Synthetic");
        }
        if (this.innerClasses != null) {
            ++a4;
            a1 += 8 + this.innerClasses.length;
            this.newUTF8("InnerClasses");
        }
        if (this.anns != null) {
            ++a4;
            a1 += 8 + this.anns.getSize();
            this.newUTF8("RuntimeVisibleAnnotations");
        }
        if (this.ianns != null) {
            ++a4;
            a1 += 8 + this.ianns.getSize();
            this.newUTF8("RuntimeInvisibleAnnotations");
        }
        if (this.tanns != null) {
            ++a4;
            a1 += 8 + this.tanns.getSize();
            this.newUTF8("RuntimeVisibleTypeAnnotations");
        }
        if (this.itanns != null) {
            ++a4;
            a1 += 8 + this.itanns.getSize();
            this.newUTF8("RuntimeInvisibleTypeAnnotations");
        }
        if (this.attrs != null) {
            a4 += this.attrs.getCount();
            a1 += this.attrs.getSize(this, null, 0, -1, -1);
        }
        a1 += this.pool.length;
        final ByteVector v3 = new ByteVector(a1);
        v3.putInt(-889275714).putInt(this.version);
        v3.putShort(this.index).putByteArray(this.pool.data, 0, this.pool.length);
        final int v0 = 0x60000 | (this.access & 0x40000) / 64;
        v3.putShort(this.access & ~v0).putShort(this.name).putShort(this.superName);
        v3.putShort(this.interfaceCount);
        for (int v2 = 0; v2 < this.interfaceCount; ++v2) {
            v3.putShort(this.interfaces[v2]);
        }
        v3.putShort(a2);
        for (FieldWriter fieldWriter = this.firstField; fieldWriter != null; fieldWriter = (FieldWriter)fieldWriter.fv) {
            fieldWriter.put(v3);
        }
        v3.putShort(a3);
        for (MethodWriter methodWriter = this.firstMethod; methodWriter != null; methodWriter = (MethodWriter)methodWriter.mv) {
            methodWriter.put(v3);
        }
        v3.putShort(a4);
        if (this.bootstrapMethods != null) {
            v3.putShort(this.newUTF8("BootstrapMethods"));
            v3.putInt(this.bootstrapMethods.length + 2).putShort(this.bootstrapMethodsCount);
            v3.putByteArray(this.bootstrapMethods.data, 0, this.bootstrapMethods.length);
        }
        if (this.signature != 0) {
            v3.putShort(this.newUTF8("Signature")).putInt(2).putShort(this.signature);
        }
        if (this.sourceFile != 0) {
            v3.putShort(this.newUTF8("SourceFile")).putInt(2).putShort(this.sourceFile);
        }
        if (this.sourceDebug != null) {
            final int v2 = this.sourceDebug.length;
            v3.putShort(this.newUTF8("SourceDebugExtension")).putInt(v2);
            v3.putByteArray(this.sourceDebug.data, 0, v2);
        }
        if (this.enclosingMethodOwner != 0) {
            v3.putShort(this.newUTF8("EnclosingMethod")).putInt(4);
            v3.putShort(this.enclosingMethodOwner).putShort(this.enclosingMethod);
        }
        if ((this.access & 0x20000) != 0x0) {
            v3.putShort(this.newUTF8("Deprecated")).putInt(0);
        }
        if ((this.access & 0x1000) != 0x0 && ((this.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            v3.putShort(this.newUTF8("Synthetic")).putInt(0);
        }
        if (this.innerClasses != null) {
            v3.putShort(this.newUTF8("InnerClasses"));
            v3.putInt(this.innerClasses.length + 2).putShort(this.innerClassesCount);
            v3.putByteArray(this.innerClasses.data, 0, this.innerClasses.length);
        }
        if (this.anns != null) {
            v3.putShort(this.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(v3);
        }
        if (this.ianns != null) {
            v3.putShort(this.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(v3);
        }
        if (this.tanns != null) {
            v3.putShort(this.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(v3);
        }
        if (this.itanns != null) {
            v3.putShort(this.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(v3);
        }
        if (this.attrs != null) {
            this.attrs.put(this, null, 0, -1, -1, v3);
        }
        if (this.hasAsmInsns) {
            this.anns = null;
            this.ianns = null;
            this.attrs = null;
            this.innerClassesCount = 0;
            this.innerClasses = null;
            this.firstField = null;
            this.lastField = null;
            this.firstMethod = null;
            this.lastMethod = null;
            this.compute = 1;
            this.hasAsmInsns = false;
            new ClassReader(v3.data).accept(this, 264);
            return this.toByteArray();
        }
        return v3.data;
    }
    
    Item newConstItem(final Object v0) {
        if (v0 instanceof Integer) {
            final int a1 = (int)v0;
            return this.newInteger(a1);
        }
        if (v0 instanceof Byte) {
            final int v = (int)v0;
            return this.newInteger(v);
        }
        if (v0 instanceof Character) {
            final int v = (char)v0;
            return this.newInteger(v);
        }
        if (v0 instanceof Short) {
            final int v = (int)v0;
            return this.newInteger(v);
        }
        if (v0 instanceof Boolean) {
            final int v = ((boolean)v0) ? 1 : 0;
            return this.newInteger(v);
        }
        if (v0 instanceof Float) {
            final float v2 = (float)v0;
            return this.newFloat(v2);
        }
        if (v0 instanceof Long) {
            final long v3 = (long)v0;
            return this.newLong(v3);
        }
        if (v0 instanceof Double) {
            final double v4 = (double)v0;
            return this.newDouble(v4);
        }
        if (v0 instanceof String) {
            return this.newString((String)v0);
        }
        if (v0 instanceof Type) {
            final Type v5 = (Type)v0;
            final int v6 = v5.getSort();
            if (v6 == 10) {
                return this.newClassItem(v5.getInternalName());
            }
            if (v6 == 11) {
                return this.newMethodTypeItem(v5.getDescriptor());
            }
            return this.newClassItem(v5.getDescriptor());
        }
        else {
            if (v0 instanceof Handle) {
                final Handle v7 = (Handle)v0;
                return this.newHandleItem(v7.tag, v7.owner, v7.name, v7.desc, v7.itf);
            }
            throw new IllegalArgumentException("value " + v0);
        }
    }
    
    public int newConst(final Object a1) {
        return this.newConstItem(a1).index;
    }
    
    public int newUTF8(final String a1) {
        this.key.set(1, a1, null, null);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            this.pool.putByte(1).putUTF8(a1);
            v1 = new Item(this.index++, this.key);
            this.put(v1);
        }
        return v1.index;
    }
    
    Item newClassItem(final String a1) {
        this.key2.set(7, a1, null, null);
        Item v1 = this.get(this.key2);
        if (v1 == null) {
            this.pool.put12(7, this.newUTF8(a1));
            v1 = new Item(this.index++, this.key2);
            this.put(v1);
        }
        return v1;
    }
    
    public int newClass(final String a1) {
        return this.newClassItem(a1).index;
    }
    
    Item newMethodTypeItem(final String a1) {
        this.key2.set(16, a1, null, null);
        Item v1 = this.get(this.key2);
        if (v1 == null) {
            this.pool.put12(16, this.newUTF8(a1));
            v1 = new Item(this.index++, this.key2);
            this.put(v1);
        }
        return v1;
    }
    
    public int newMethodType(final String a1) {
        return this.newMethodTypeItem(a1).index;
    }
    
    Item newHandleItem(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        this.key4.set(20 + a1, a2, a3, a4);
        Item v1 = this.get(this.key4);
        if (v1 == null) {
            if (a1 <= 4) {
                this.put112(15, a1, this.newField(a2, a3, a4));
            }
            else {
                this.put112(15, a1, this.newMethod(a2, a3, a4, a5));
            }
            v1 = new Item(this.index++, this.key4);
            this.put(v1);
        }
        return v1;
    }
    
    @Deprecated
    public int newHandle(final int a1, final String a2, final String a3, final String a4) {
        return this.newHandle(a1, a2, a3, a4, a1 == 9);
    }
    
    public int newHandle(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        return this.newHandleItem(a1, a2, a3, a4, a5).index;
    }
    
    Item newInvokeDynamicItem(final String v-10, final String v-9, final Handle v-8, final Object... v-7) {
        ByteVector bootstrapMethods = this.bootstrapMethods;
        if (bootstrapMethods == null) {
            final ByteVector bootstrapMethods2 = new ByteVector();
            this.bootstrapMethods = bootstrapMethods2;
            bootstrapMethods = bootstrapMethods2;
        }
        final int length = bootstrapMethods.length;
        int hashCode = v-8.hashCode();
        bootstrapMethods.putShort(this.newHandle(v-8.tag, v-8.owner, v-8.name, v-8.desc, v-8.isInterface()));
        final int length2 = v-7.length;
        bootstrapMethods.putShort(length2);
        for (final Object a3 : v-7) {
            hashCode ^= a3.hashCode();
            bootstrapMethods.putShort(this.newConst(a3));
        }
        final byte[] data = bootstrapMethods.data;
        final int n = 2 + length2 << 1;
        hashCode &= 0x0;
        Item v0 = this.items[hashCode % this.items.length];
    Label_0163:
        while (v0 != null) {
            if (v0.type == 33 && v0.hashCode == hashCode) {
                final int a4 = v0.intVal;
                for (int a5 = 0; a5 < n; ++a5) {
                    if (data[length + a5] != data[a4 + a5]) {
                        v0 = v0.next;
                        continue Label_0163;
                    }
                }
                break;
            }
            v0 = v0.next;
        }
        int v2;
        if (v0 != null) {
            v2 = v0.index;
            bootstrapMethods.length = length;
        }
        else {
            v2 = this.bootstrapMethodsCount++;
            v0 = new Item(v2);
            v0.set(length, hashCode);
            this.put(v0);
        }
        this.key3.set(v-10, v-9, v2);
        v0 = this.get(this.key3);
        if (v0 == null) {
            this.put122(18, v2, this.newNameType(v-10, v-9));
            v0 = new Item(this.index++, this.key3);
            this.put(v0);
        }
        return v0;
    }
    
    public int newInvokeDynamic(final String a1, final String a2, final Handle a3, final Object... a4) {
        return this.newInvokeDynamicItem(a1, a2, a3, a4).index;
    }
    
    Item newFieldItem(final String a1, final String a2, final String a3) {
        this.key3.set(9, a1, a2, a3);
        Item v1 = this.get(this.key3);
        if (v1 == null) {
            this.put122(9, this.newClass(a1), this.newNameType(a2, a3));
            v1 = new Item(this.index++, this.key3);
            this.put(v1);
        }
        return v1;
    }
    
    public int newField(final String a1, final String a2, final String a3) {
        return this.newFieldItem(a1, a2, a3).index;
    }
    
    Item newMethodItem(final String a1, final String a2, final String a3, final boolean a4) {
        final int v1 = a4 ? 11 : 10;
        this.key3.set(v1, a1, a2, a3);
        Item v2 = this.get(this.key3);
        if (v2 == null) {
            this.put122(v1, this.newClass(a1), this.newNameType(a2, a3));
            v2 = new Item(this.index++, this.key3);
            this.put(v2);
        }
        return v2;
    }
    
    public int newMethod(final String a1, final String a2, final String a3, final boolean a4) {
        return this.newMethodItem(a1, a2, a3, a4).index;
    }
    
    Item newInteger(final int a1) {
        this.key.set(a1);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            this.pool.putByte(3).putInt(a1);
            v1 = new Item(this.index++, this.key);
            this.put(v1);
        }
        return v1;
    }
    
    Item newFloat(final float a1) {
        this.key.set(a1);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            this.pool.putByte(4).putInt(this.key.intVal);
            v1 = new Item(this.index++, this.key);
            this.put(v1);
        }
        return v1;
    }
    
    Item newLong(final long a1) {
        this.key.set(a1);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            this.pool.putByte(5).putLong(a1);
            v1 = new Item(this.index, this.key);
            this.index += 2;
            this.put(v1);
        }
        return v1;
    }
    
    Item newDouble(final double a1) {
        this.key.set(a1);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            this.pool.putByte(6).putLong(this.key.longVal);
            v1 = new Item(this.index, this.key);
            this.index += 2;
            this.put(v1);
        }
        return v1;
    }
    
    private Item newString(final String a1) {
        this.key2.set(8, a1, null, null);
        Item v1 = this.get(this.key2);
        if (v1 == null) {
            this.pool.put12(8, this.newUTF8(a1));
            v1 = new Item(this.index++, this.key2);
            this.put(v1);
        }
        return v1;
    }
    
    public int newNameType(final String a1, final String a2) {
        return this.newNameTypeItem(a1, a2).index;
    }
    
    Item newNameTypeItem(final String a1, final String a2) {
        this.key2.set(12, a1, a2, null);
        Item v1 = this.get(this.key2);
        if (v1 == null) {
            this.put122(12, this.newUTF8(a1), this.newUTF8(a2));
            v1 = new Item(this.index++, this.key2);
            this.put(v1);
        }
        return v1;
    }
    
    int addType(final String a1) {
        this.key.set(30, a1, null, null);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            v1 = this.addType(this.key);
        }
        return v1.index;
    }
    
    int addUninitializedType(final String a1, final int a2) {
        this.key.type = 31;
        this.key.intVal = a2;
        this.key.strVal1 = a1;
        this.key.hashCode = (0x0 & 31 + a1.hashCode() + a2);
        Item v1 = this.get(this.key);
        if (v1 == null) {
            v1 = this.addType(this.key);
        }
        return v1.index;
    }
    
    private Item addType(final Item v2) {
        ++this.typeCount;
        final Item v3 = new Item(this.typeCount, this.key);
        this.put(v3);
        if (this.typeTable == null) {
            this.typeTable = new Item[16];
        }
        if (this.typeCount == this.typeTable.length) {
            final Item[] a1 = new Item[2 * this.typeTable.length];
            System.arraycopy(this.typeTable, 0, a1, 0, this.typeTable.length);
            this.typeTable = a1;
        }
        return this.typeTable[this.typeCount] = v3;
    }
    
    int getMergedType(final int v2, final int v3) {
        this.key2.type = 32;
        this.key2.longVal = ((long)v2 | (long)v3 << 32);
        this.key2.hashCode = (0x0 & 32 + v2 + v3);
        Item v4 = this.get(this.key2);
        if (v4 == null) {
            final String a1 = this.typeTable[v2].strVal1;
            final String a2 = this.typeTable[v3].strVal1;
            this.key2.intVal = this.addType(this.getCommonSuperClass(a1, a2));
            v4 = new Item(0, this.key2);
            this.put(v4);
        }
        return v4.intVal;
    }
    
    protected String getCommonSuperClass(final String v-4, final String v-3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //     4: invokevirtual   java/lang/Class.getClassLoader:()Ljava/lang/ClassLoader;
        //     7: astore_3        /* v-2 */
        //     8: aload_1         /* v-4 */
        //     9: bipush          47
        //    11: bipush          46
        //    13: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //    16: iconst_0       
        //    17: aload_3         /* v-2 */
        //    18: invokestatic    java/lang/Class.forName:(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
        //    21: astore          a1
        //    23: aload_2         /* v-3 */
        //    24: bipush          47
        //    26: bipush          46
        //    28: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //    31: iconst_0       
        //    32: aload_3         /* v-2 */
        //    33: invokestatic    java/lang/Class.forName:(Ljava/lang/String;ZLjava/lang/ClassLoader;)Ljava/lang/Class;
        //    36: astore          a2
        //    38: goto            56
        //    41: astore          v1
        //    43: new             Ljava/lang/RuntimeException;
        //    46: dup            
        //    47: aload           v1
        //    49: invokevirtual   java/lang/Exception.toString:()Ljava/lang/String;
        //    52: invokespecial   java/lang/RuntimeException.<init>:(Ljava/lang/String;)V
        //    55: athrow         
        //    56: aload           v-1
        //    58: aload           v0
        //    60: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //    63: ifeq            68
        //    66: aload_1         /* v-4 */
        //    67: areturn        
        //    68: aload           v0
        //    70: aload           v-1
        //    72: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //    75: ifeq            80
        //    78: aload_2         /* v-3 */
        //    79: areturn        
        //    80: aload           v-1
        //    82: invokevirtual   java/lang/Class.isInterface:()Z
        //    85: ifne            96
        //    88: aload           v0
        //    90: invokevirtual   java/lang/Class.isInterface:()Z
        //    93: ifeq            100
        //    96: ldc_w           "java/lang/Object"
        //    99: areturn        
        //   100: aload           v-1
        //   102: invokevirtual   java/lang/Class.getSuperclass:()Ljava/lang/Class;
        //   105: astore          v-1
        //   107: aload           v-1
        //   109: aload           v0
        //   111: invokevirtual   java/lang/Class.isAssignableFrom:(Ljava/lang/Class;)Z
        //   114: ifeq            100
        //   117: aload           v-1
        //   119: invokevirtual   java/lang/Class.getName:()Ljava/lang/String;
        //   122: bipush          46
        //   124: bipush          47
        //   126: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //   129: areturn        
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  8      38     41     56     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private Item get(final Item a1) {
        Item v1;
        for (v1 = this.items[a1.hashCode % this.items.length]; v1 != null && (v1.type != a1.type || !a1.isEqualTo(v1)); v1 = v1.next) {}
        return v1;
    }
    
    private void put(final Item v-6) {
        if (this.index + this.typeCount > this.threshold) {
            final int length = this.items.length;
            final int n = length * 2 + 1;
            final Item[] items = new Item[n];
            for (int i = length - 1; i >= 0; --i) {
                Item v1;
                for (Item item = this.items[i]; item != null; item = v1) {
                    final int a1 = item.hashCode % items.length;
                    v1 = item.next;
                    item.next = items[a1];
                    items[a1] = item;
                }
            }
            this.items = items;
            this.threshold = (int)(n * 0.75);
        }
        final int length = v-6.hashCode % this.items.length;
        v-6.next = this.items[length];
        this.items[length] = v-6;
    }
    
    private void put122(final int a1, final int a2, final int a3) {
        this.pool.put12(a1, a2).putShort(a3);
    }
    
    private void put112(final int a1, final int a2, final int a3) {
        this.pool.put11(a1, a2).putShort(a3);
    }
    
    static {
        final byte[] type = new byte[220];
        final String v0 = "AAAAAAAAAAAAAAAABCLMMDDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAADDDDDEEEEEEEEEEEEEEEEEEEEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANAAAAAAAAAAAAAAAAAAAAJJJJJJJJJJJJJJJJDOPAAAAAAGGGGGGGHIFBFAAFFAARQJJKKSSSSSSSSSSSSSSSSSS";
        for (int v2 = 0; v2 < type.length; ++v2) {
            type[v2] = (byte)(v0.charAt(v2) - 'A');
        }
        TYPE = type;
    }
}
