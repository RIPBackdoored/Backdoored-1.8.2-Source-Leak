package org.spongepowered.asm.lib;

public class ByteVector
{
    byte[] data;
    int length;
    
    public ByteVector() {
        super();
        this.data = new byte[64];
    }
    
    public ByteVector(final int a1) {
        super();
        this.data = new byte[a1];
    }
    
    public ByteVector putByte(final int a1) {
        int v1 = this.length;
        if (v1 + 1 > this.data.length) {
            this.enlarge(1);
        }
        this.data[v1++] = (byte)a1;
        this.length = v1;
        return this;
    }
    
    ByteVector put11(final int a1, final int a2) {
        int v1 = this.length;
        if (v1 + 2 > this.data.length) {
            this.enlarge(2);
        }
        final byte[] v2 = this.data;
        v2[v1++] = (byte)a1;
        v2[v1++] = (byte)a2;
        this.length = v1;
        return this;
    }
    
    public ByteVector putShort(final int a1) {
        int v1 = this.length;
        if (v1 + 2 > this.data.length) {
            this.enlarge(2);
        }
        final byte[] v2 = this.data;
        v2[v1++] = (byte)(a1 >>> 8);
        v2[v1++] = (byte)a1;
        this.length = v1;
        return this;
    }
    
    ByteVector put12(final int a1, final int a2) {
        int v1 = this.length;
        if (v1 + 3 > this.data.length) {
            this.enlarge(3);
        }
        final byte[] v2 = this.data;
        v2[v1++] = (byte)a1;
        v2[v1++] = (byte)(a2 >>> 8);
        v2[v1++] = (byte)a2;
        this.length = v1;
        return this;
    }
    
    public ByteVector putInt(final int a1) {
        int v1 = this.length;
        if (v1 + 4 > this.data.length) {
            this.enlarge(4);
        }
        final byte[] v2 = this.data;
        v2[v1++] = (byte)(a1 >>> 24);
        v2[v1++] = (byte)(a1 >>> 16);
        v2[v1++] = (byte)(a1 >>> 8);
        v2[v1++] = (byte)a1;
        this.length = v1;
        return this;
    }
    
    public ByteVector putLong(final long a1) {
        int v1 = this.length;
        if (v1 + 8 > this.data.length) {
            this.enlarge(8);
        }
        final byte[] v2 = this.data;
        int v3 = (int)(a1 >>> 32);
        v2[v1++] = (byte)(v3 >>> 24);
        v2[v1++] = (byte)(v3 >>> 16);
        v2[v1++] = (byte)(v3 >>> 8);
        v2[v1++] = (byte)v3;
        v3 = (int)a1;
        v2[v1++] = (byte)(v3 >>> 24);
        v2[v1++] = (byte)(v3 >>> 16);
        v2[v1++] = (byte)(v3 >>> 8);
        v2[v1++] = (byte)v3;
        this.length = v1;
        return this;
    }
    
    public ByteVector putUTF8(final String v-3) {
        final int length = v-3.length();
        if (length > 65535) {
            throw new IllegalArgumentException();
        }
        int length2 = this.length;
        if (length2 + 2 + length > this.data.length) {
            this.enlarge(2 + length);
        }
        final byte[] v0 = this.data;
        v0[length2++] = (byte)(length >>> 8);
        v0[length2++] = (byte)length;
        for (int v2 = 0; v2 < length; ++v2) {
            final char a1 = v-3.charAt(v2);
            if (a1 < '\u0001' || a1 > '\u007f') {
                this.length = length2;
                return this.encodeUTF8(v-3, v2, 65535);
            }
            v0[length2++] = (byte)a1;
        }
        this.length = length2;
        return this;
    }
    
    ByteVector encodeUTF8(final String v-7, final int v-6, final int v-5) {
        final int length = v-7.length();
        int n = v-6;
        for (int a2 = v-6; a2 < length; ++a2) {
            final char a3 = v-7.charAt(a2);
            if (a3 >= '\u0001' && a3 <= '\u007f') {
                ++n;
            }
            else if (a3 > '\u07ff') {
                n += 3;
            }
            else {
                n += 2;
            }
        }
        if (n > v-5) {
            throw new IllegalArgumentException();
        }
        final int n2 = this.length - v-6 - 2;
        if (n2 >= 0) {
            this.data[n2] = (byte)(n >>> 8);
            this.data[n2 + 1] = (byte)n;
        }
        if (this.length + n - v-6 > this.data.length) {
            this.enlarge(n - v-6);
        }
        int v0 = this.length;
        for (int v2 = v-6; v2 < length; ++v2) {
            final char a4 = v-7.charAt(v2);
            if (a4 >= '\u0001' && a4 <= '\u007f') {
                this.data[v0++] = (byte)a4;
            }
            else if (a4 > '\u07ff') {
                this.data[v0++] = (byte)(0xE0 | (a4 >> 12 & 0xF));
                this.data[v0++] = (byte)(0x80 | (a4 >> 6 & 0x3F));
                this.data[v0++] = (byte)(0x80 | (a4 & '?'));
            }
            else {
                this.data[v0++] = (byte)(0xC0 | (a4 >> 6 & 0x1F));
                this.data[v0++] = (byte)(0x80 | (a4 & '?'));
            }
        }
        this.length = v0;
        return this;
    }
    
    public ByteVector putByteArray(final byte[] a1, final int a2, final int a3) {
        if (this.length + a3 > this.data.length) {
            this.enlarge(a3);
        }
        if (a1 != null) {
            System.arraycopy(a1, a2, this.data, this.length, a3);
        }
        this.length += a3;
        return this;
    }
    
    private void enlarge(final int a1) {
        final int v1 = 2 * this.data.length;
        final int v2 = this.length + a1;
        final byte[] v3 = new byte[(v1 > v2) ? v1 : v2];
        System.arraycopy(this.data, 0, v3, 0, this.length);
        this.data = v3;
    }
}
