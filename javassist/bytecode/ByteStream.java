package javassist.bytecode;

import java.io.*;

final class ByteStream extends OutputStream
{
    private byte[] buf;
    private int count;
    
    public ByteStream() {
        this(32);
    }
    
    public ByteStream(final int a1) {
        super();
        this.buf = new byte[a1];
        this.count = 0;
    }
    
    public int getPos() {
        return this.count;
    }
    
    public int size() {
        return this.count;
    }
    
    public void writeBlank(final int a1) {
        this.enlarge(a1);
        this.count += a1;
    }
    
    @Override
    public void write(final byte[] a1) {
        this.write(a1, 0, a1.length);
    }
    
    @Override
    public void write(final byte[] a1, final int a2, final int a3) {
        this.enlarge(a3);
        System.arraycopy(a1, a2, this.buf, this.count, a3);
        this.count += a3;
    }
    
    @Override
    public void write(final int a1) {
        this.enlarge(1);
        final int v1 = this.count;
        this.buf[v1] = (byte)a1;
        this.count = v1 + 1;
    }
    
    public void writeShort(final int a1) {
        this.enlarge(2);
        final int v1 = this.count;
        this.buf[v1] = (byte)(a1 >>> 8);
        this.buf[v1 + 1] = (byte)a1;
        this.count = v1 + 2;
    }
    
    public void writeInt(final int a1) {
        this.enlarge(4);
        final int v1 = this.count;
        this.buf[v1] = (byte)(a1 >>> 24);
        this.buf[v1 + 1] = (byte)(a1 >>> 16);
        this.buf[v1 + 2] = (byte)(a1 >>> 8);
        this.buf[v1 + 3] = (byte)a1;
        this.count = v1 + 4;
    }
    
    public void writeLong(final long a1) {
        this.enlarge(8);
        final int v1 = this.count;
        this.buf[v1] = (byte)(a1 >>> 56);
        this.buf[v1 + 1] = (byte)(a1 >>> 48);
        this.buf[v1 + 2] = (byte)(a1 >>> 40);
        this.buf[v1 + 3] = (byte)(a1 >>> 32);
        this.buf[v1 + 4] = (byte)(a1 >>> 24);
        this.buf[v1 + 5] = (byte)(a1 >>> 16);
        this.buf[v1 + 6] = (byte)(a1 >>> 8);
        this.buf[v1 + 7] = (byte)a1;
        this.count = v1 + 8;
    }
    
    public void writeFloat(final float a1) {
        this.writeInt(Float.floatToIntBits(a1));
    }
    
    public void writeDouble(final double a1) {
        this.writeLong(Double.doubleToLongBits(a1));
    }
    
    public void writeUTF(final String v-3) {
        final int length = v-3.length();
        int count = this.count;
        this.enlarge(length + 2);
        final byte[] v0 = this.buf;
        v0[count++] = (byte)(length >>> 8);
        v0[count++] = (byte)length;
        for (int v2 = 0; v2 < length; ++v2) {
            final char a1 = v-3.charAt(v2);
            if ('\u0001' > a1 || a1 > '\u007f') {
                this.writeUTF2(v-3, length, v2);
                return;
            }
            v0[count++] = (byte)a1;
        }
        this.count = count;
    }
    
    private void writeUTF2(final String v-5, final int v-4, final int v-3) {
        int n = v-4;
        for (int a2 = v-3; a2 < v-4; ++a2) {
            final int a3 = v-5.charAt(a2);
            if (a3 > 2047) {
                n += 2;
            }
            else if (a3 == 0 || a3 > 127) {
                ++n;
            }
        }
        if (n > 65535) {
            throw new RuntimeException("encoded string too long: " + v-4 + n + " bytes");
        }
        this.enlarge(n + 2);
        int count = this.count;
        final byte[] v0 = this.buf;
        v0[count] = (byte)(n >>> 8);
        v0[count + 1] = (byte)n;
        count += 2 + v-3;
        for (int v2 = v-3; v2 < v-4; ++v2) {
            final int a4 = v-5.charAt(v2);
            if (1 <= a4 && a4 <= 127) {
                v0[count++] = (byte)a4;
            }
            else if (a4 > 2047) {
                v0[count] = (byte)(0xE0 | (a4 >> 12 & 0xF));
                v0[count + 1] = (byte)(0x80 | (a4 >> 6 & 0x3F));
                v0[count + 2] = (byte)(0x80 | (a4 & 0x3F));
                count += 3;
            }
            else {
                v0[count] = (byte)(0xC0 | (a4 >> 6 & 0x1F));
                v0[count + 1] = (byte)(0x80 | (a4 & 0x3F));
                count += 2;
            }
        }
        this.count = count;
    }
    
    public void write(final int a1, final int a2) {
        this.buf[a1] = (byte)a2;
    }
    
    public void writeShort(final int a1, final int a2) {
        this.buf[a1] = (byte)(a2 >>> 8);
        this.buf[a1 + 1] = (byte)a2;
    }
    
    public void writeInt(final int a1, final int a2) {
        this.buf[a1] = (byte)(a2 >>> 24);
        this.buf[a1 + 1] = (byte)(a2 >>> 16);
        this.buf[a1 + 2] = (byte)(a2 >>> 8);
        this.buf[a1 + 3] = (byte)a2;
    }
    
    public byte[] toByteArray() {
        final byte[] v1 = new byte[this.count];
        System.arraycopy(this.buf, 0, v1, 0, this.count);
        return v1;
    }
    
    public void writeTo(final OutputStream a1) throws IOException {
        a1.write(this.buf, 0, this.count);
    }
    
    public void enlarge(final int v-2) {
        final int n = this.count + v-2;
        if (n > this.buf.length) {
            final int a1 = this.buf.length << 1;
            final byte[] v1 = new byte[(a1 > n) ? a1 : n];
            System.arraycopy(this.buf, 0, v1, 0, this.count);
            this.buf = v1;
        }
    }
}
