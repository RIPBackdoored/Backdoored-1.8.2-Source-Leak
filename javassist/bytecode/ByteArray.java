package javassist.bytecode;

public class ByteArray
{
    public ByteArray() {
        super();
    }
    
    public static int readU16bit(final byte[] a1, final int a2) {
        return (a1[a2] & 0xFF) << 8 | (a1[a2 + 1] & 0xFF);
    }
    
    public static int readS16bit(final byte[] a1, final int a2) {
        return a1[a2] << 8 | (a1[a2 + 1] & 0xFF);
    }
    
    public static void write16bit(final int a1, final byte[] a2, final int a3) {
        a2[a3] = (byte)(a1 >>> 8);
        a2[a3 + 1] = (byte)a1;
    }
    
    public static int read32bit(final byte[] a1, final int a2) {
        return a1[a2] << 24 | (a1[a2 + 1] & 0xFF) << 16 | (a1[a2 + 2] & 0xFF) << 8 | (a1[a2 + 3] & 0xFF);
    }
    
    public static void write32bit(final int a1, final byte[] a2, final int a3) {
        a2[a3] = (byte)(a1 >>> 24);
        a2[a3 + 1] = (byte)(a1 >>> 16);
        a2[a3 + 2] = (byte)(a1 >>> 8);
        a2[a3 + 3] = (byte)a1;
    }
    
    static void copy32bit(final byte[] a1, final int a2, final byte[] a3, final int a4) {
        a3[a4] = a1[a2];
        a3[a4 + 1] = a1[a2 + 1];
        a3[a4 + 2] = a1[a2 + 2];
        a3[a4 + 3] = a1[a2 + 3];
    }
}
