package org.spongepowered.asm.lib;

final class FieldWriter extends FieldVisitor
{
    private final ClassWriter cw;
    private final int access;
    private final int name;
    private final int desc;
    private int signature;
    private int value;
    private AnnotationWriter anns;
    private AnnotationWriter ianns;
    private AnnotationWriter tanns;
    private AnnotationWriter itanns;
    private Attribute attrs;
    
    FieldWriter(final ClassWriter a1, final int a2, final String a3, final String a4, final String a5, final Object a6) {
        super(327680);
        if (a1.firstField == null) {
            a1.firstField = this;
        }
        else {
            a1.lastField.fv = this;
        }
        a1.lastField = this;
        this.cw = a1;
        this.access = a2;
        this.name = a1.newUTF8(a3);
        this.desc = a1.newUTF8(a4);
        if (a5 != null) {
            this.signature = a1.newUTF8(a5);
        }
        if (a6 != null) {
            this.value = a1.newConstItem(a6).index;
        }
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final ByteVector v1 = new ByteVector();
        v1.putShort(this.cw.newUTF8(a1)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, 2);
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
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final ByteVector v1 = new ByteVector();
        AnnotationWriter.putTarget(a1, a2, v1);
        v1.putShort(this.cw.newUTF8(a3)).putShort(0);
        final AnnotationWriter v2 = new AnnotationWriter(this.cw, true, v1, v1, v1.length - 2);
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
    public void visitAttribute(final Attribute a1) {
        a1.next = this.attrs;
        this.attrs = a1;
    }
    
    @Override
    public void visitEnd() {
    }
    
    int getSize() {
        int v1 = 8;
        if (this.value != 0) {
            this.cw.newUTF8("ConstantValue");
            v1 += 8;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            this.cw.newUTF8("Synthetic");
            v1 += 6;
        }
        if ((this.access & 0x20000) != 0x0) {
            this.cw.newUTF8("Deprecated");
            v1 += 6;
        }
        if (this.signature != 0) {
            this.cw.newUTF8("Signature");
            v1 += 8;
        }
        if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            v1 += 8 + this.anns.getSize();
        }
        if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            v1 += 8 + this.ianns.getSize();
        }
        if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            v1 += 8 + this.tanns.getSize();
        }
        if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            v1 += 8 + this.itanns.getSize();
        }
        if (this.attrs != null) {
            v1 += this.attrs.getSize(this.cw, null, 0, -1, -1);
        }
        return v1;
    }
    
    void put(final ByteVector a1) {
        final int v1 = 64;
        final int v2 = 0x60000 | (this.access & 0x40000) / 64;
        a1.putShort(this.access & ~v2).putShort(this.name).putShort(this.desc);
        int v3 = 0;
        if (this.value != 0) {
            ++v3;
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            ++v3;
        }
        if ((this.access & 0x20000) != 0x0) {
            ++v3;
        }
        if (this.signature != 0) {
            ++v3;
        }
        if (this.anns != null) {
            ++v3;
        }
        if (this.ianns != null) {
            ++v3;
        }
        if (this.tanns != null) {
            ++v3;
        }
        if (this.itanns != null) {
            ++v3;
        }
        if (this.attrs != null) {
            v3 += this.attrs.getCount();
        }
        a1.putShort(v3);
        if (this.value != 0) {
            a1.putShort(this.cw.newUTF8("ConstantValue"));
            a1.putInt(2).putShort(this.value);
        }
        if ((this.access & 0x1000) != 0x0 && ((this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0x0)) {
            a1.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
        }
        if ((this.access & 0x20000) != 0x0) {
            a1.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
        }
        if (this.signature != 0) {
            a1.putShort(this.cw.newUTF8("Signature"));
            a1.putInt(2).putShort(this.signature);
        }
        if (this.anns != null) {
            a1.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(a1);
        }
        if (this.ianns != null) {
            a1.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(a1);
        }
        if (this.tanns != null) {
            a1.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(a1);
        }
        if (this.itanns != null) {
            a1.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(a1);
        }
        if (this.attrs != null) {
            this.attrs.put(this.cw, null, 0, -1, -1, a1);
        }
    }
}
