package org.spongepowered.asm.lib;

public class Attribute
{
    public final String type;
    byte[] value;
    Attribute next;
    
    protected Attribute(final String a1) {
        super();
        this.type = a1;
    }
    
    public boolean isUnknown() {
        return true;
    }
    
    public boolean isCodeAttribute() {
        return false;
    }
    
    protected Label[] getLabels() {
        return null;
    }
    
    protected Attribute read(final ClassReader a1, final int a2, final int a3, final char[] a4, final int a5, final Label[] a6) {
        final Attribute v1 = new Attribute(this.type);
        v1.value = new byte[a3];
        System.arraycopy(a1.b, a2, v1.value, 0, a3);
        return v1;
    }
    
    protected ByteVector write(final ClassWriter a1, final byte[] a2, final int a3, final int a4, final int a5) {
        final ByteVector v1 = new ByteVector();
        v1.data = this.value;
        v1.length = this.value.length;
        return v1;
    }
    
    final int getCount() {
        int v1 = 0;
        for (Attribute v2 = this; v2 != null; v2 = v2.next) {
            ++v1;
        }
        return v1;
    }
    
    final int getSize(final ClassWriter a1, final byte[] a2, final int a3, final int a4, final int a5) {
        Attribute v1 = this;
        int v2 = 0;
        while (v1 != null) {
            a1.newUTF8(v1.type);
            v2 += v1.write(a1, a2, a3, a4, a5).length + 6;
            v1 = v1.next;
        }
        return v2;
    }
    
    final void put(final ClassWriter a3, final byte[] a4, final int a5, final int a6, final int v1, final ByteVector v2) {
        for (Attribute v3 = this; v3 != null; v3 = v3.next) {
            final ByteVector a7 = v3.write(a3, a4, a5, a6, v1);
            v2.putShort(a3.newUTF8(v3.type)).putInt(a7.length);
            v2.putByteArray(a7.data, 0, a7.length);
        }
    }
}
