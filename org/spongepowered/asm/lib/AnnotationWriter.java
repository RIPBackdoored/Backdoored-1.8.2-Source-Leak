package org.spongepowered.asm.lib;

final class AnnotationWriter extends AnnotationVisitor
{
    private final ClassWriter cw;
    private int size;
    private final boolean named;
    private final ByteVector bv;
    private final ByteVector parent;
    private final int offset;
    AnnotationWriter next;
    AnnotationWriter prev;
    
    AnnotationWriter(final ClassWriter a1, final boolean a2, final ByteVector a3, final ByteVector a4, final int a5) {
        super(327680);
        this.cw = a1;
        this.named = a2;
        this.bv = a3;
        this.parent = a4;
        this.offset = a5;
    }
    
    @Override
    public void visit(final String v-1, final Object v0) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(v-1));
        }
        if (v0 instanceof String) {
            this.bv.put12(115, this.cw.newUTF8((String)v0));
        }
        else if (v0 instanceof Byte) {
            this.bv.put12(66, this.cw.newInteger((byte)v0).index);
        }
        else if (v0 instanceof Boolean) {
            final int a1 = ((boolean)v0) ? 1 : 0;
            this.bv.put12(90, this.cw.newInteger(a1).index);
        }
        else if (v0 instanceof Character) {
            this.bv.put12(67, this.cw.newInteger((char)v0).index);
        }
        else if (v0 instanceof Short) {
            this.bv.put12(83, this.cw.newInteger((short)v0).index);
        }
        else if (v0 instanceof Type) {
            this.bv.put12(99, this.cw.newUTF8(((Type)v0).getDescriptor()));
        }
        else if (v0 instanceof byte[]) {
            final byte[] v = (byte[])v0;
            this.bv.put12(91, v.length);
            for (int a2 = 0; a2 < v.length; ++a2) {
                this.bv.put12(66, this.cw.newInteger(v[a2]).index);
            }
        }
        else if (v0 instanceof boolean[]) {
            final boolean[] v2 = (boolean[])v0;
            this.bv.put12(91, v2.length);
            for (int v3 = 0; v3 < v2.length; ++v3) {
                this.bv.put12(90, this.cw.newInteger(v2[v3] ? 1 : 0).index);
            }
        }
        else if (v0 instanceof short[]) {
            final short[] v4 = (short[])v0;
            this.bv.put12(91, v4.length);
            for (int v3 = 0; v3 < v4.length; ++v3) {
                this.bv.put12(83, this.cw.newInteger(v4[v3]).index);
            }
        }
        else if (v0 instanceof char[]) {
            final char[] v5 = (char[])v0;
            this.bv.put12(91, v5.length);
            for (int v3 = 0; v3 < v5.length; ++v3) {
                this.bv.put12(67, this.cw.newInteger(v5[v3]).index);
            }
        }
        else if (v0 instanceof int[]) {
            final int[] v6 = (int[])v0;
            this.bv.put12(91, v6.length);
            for (int v3 = 0; v3 < v6.length; ++v3) {
                this.bv.put12(73, this.cw.newInteger(v6[v3]).index);
            }
        }
        else if (v0 instanceof long[]) {
            final long[] v7 = (long[])v0;
            this.bv.put12(91, v7.length);
            for (int v3 = 0; v3 < v7.length; ++v3) {
                this.bv.put12(74, this.cw.newLong(v7[v3]).index);
            }
        }
        else if (v0 instanceof float[]) {
            final float[] v8 = (float[])v0;
            this.bv.put12(91, v8.length);
            for (int v3 = 0; v3 < v8.length; ++v3) {
                this.bv.put12(70, this.cw.newFloat(v8[v3]).index);
            }
        }
        else if (v0 instanceof double[]) {
            final double[] v9 = (double[])v0;
            this.bv.put12(91, v9.length);
            for (int v3 = 0; v3 < v9.length; ++v3) {
                this.bv.put12(68, this.cw.newDouble(v9[v3]).index);
            }
        }
        else {
            final Item v10 = this.cw.newConstItem(v0);
            this.bv.put12(".s.IFJDCS".charAt(v10.type), v10.index);
        }
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(a1));
        }
        this.bv.put12(101, this.cw.newUTF8(a2)).putShort(this.cw.newUTF8(a3));
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final String a2) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(a1));
        }
        this.bv.put12(64, this.cw.newUTF8(a2)).putShort(0);
        return new AnnotationWriter(this.cw, true, this.bv, this.bv, this.bv.length - 2);
    }
    
    @Override
    public AnnotationVisitor visitArray(final String a1) {
        ++this.size;
        if (this.named) {
            this.bv.putShort(this.cw.newUTF8(a1));
        }
        this.bv.put12(91, 0);
        return new AnnotationWriter(this.cw, false, this.bv, this.bv, this.bv.length - 2);
    }
    
    @Override
    public void visitEnd() {
        if (this.parent != null) {
            final byte[] v1 = this.parent.data;
            v1[this.offset] = (byte)(this.size >>> 8);
            v1[this.offset + 1] = (byte)this.size;
        }
    }
    
    int getSize() {
        int v1 = 0;
        for (AnnotationWriter v2 = this; v2 != null; v2 = v2.next) {
            v1 += v2.bv.length;
        }
        return v1;
    }
    
    void put(final ByteVector a1) {
        int v1 = 0;
        int v2 = 2;
        AnnotationWriter v3 = this;
        AnnotationWriter v4 = null;
        while (v3 != null) {
            ++v1;
            v2 += v3.bv.length;
            v3.visitEnd();
            v3.prev = v4;
            v4 = v3;
            v3 = v3.next;
        }
        a1.putInt(v2);
        a1.putShort(v1);
        for (v3 = v4; v3 != null; v3 = v3.prev) {
            a1.putByteArray(v3.bv.data, 0, v3.bv.length);
        }
    }
    
    static void put(final AnnotationWriter[] v-6, final int v-5, final ByteVector v-4) {
        int a4 = 1 + 2 * (v-6.length - v-5);
        for (int a1 = v-5; a1 < v-6.length; ++a1) {
            a4 += ((v-6[a1] == null) ? 0 : v-6[a1].getSize());
        }
        v-4.putInt(a4).putByte(v-6.length - v-5);
        for (int i = v-5; i < v-6.length; ++i) {
            AnnotationWriter a2 = v-6[i];
            AnnotationWriter a3 = null;
            int v1 = 0;
            while (a2 != null) {
                ++v1;
                a2.visitEnd();
                a2.prev = a3;
                a3 = a2;
                a2 = a2.next;
            }
            v-4.putShort(v1);
            for (a2 = a3; a2 != null; a2 = a2.prev) {
                v-4.putByteArray(a2.bv.data, 0, a2.bv.length);
            }
        }
    }
    
    static void putTarget(final int a2, final TypePath a3, final ByteVector v1) {
        switch (a2 >>> 24) {
            case 0:
            case 1:
            case 22: {
                v1.putShort(a2 >>> 16);
                break;
            }
            case 19:
            case 20:
            case 21: {
                v1.putByte(a2 >>> 24);
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                v1.putInt(a2);
                break;
            }
            default: {
                v1.put12(a2 >>> 24, (a2 & 0xFFFF00) >> 8);
                break;
            }
        }
        if (a3 == null) {
            v1.putByte(0);
        }
        else {
            final int a4 = a3.b[a3.offset] * 2 + 1;
            v1.putByteArray(a3.b, a3.offset, a4);
        }
    }
}
