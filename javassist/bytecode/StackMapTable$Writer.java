package javassist.bytecode;

import java.io.*;

public static class Writer
{
    ByteArrayOutputStream output;
    int numOfEntries;
    
    public Writer(final int a1) {
        super();
        this.output = new ByteArrayOutputStream(a1);
        this.numOfEntries = 0;
        this.output.write(0);
        this.output.write(0);
    }
    
    public byte[] toByteArray() {
        final byte[] v1 = this.output.toByteArray();
        ByteArray.write16bit(this.numOfEntries, v1, 0);
        return v1;
    }
    
    public StackMapTable toStackMapTable(final ConstPool a1) {
        return new StackMapTable(a1, this.toByteArray());
    }
    
    public void sameFrame(final int a1) {
        ++this.numOfEntries;
        if (a1 < 64) {
            this.output.write(a1);
        }
        else {
            this.output.write(251);
            this.write16(a1);
        }
    }
    
    public void sameLocals(final int a1, final int a2, final int a3) {
        ++this.numOfEntries;
        if (a1 < 64) {
            this.output.write(a1 + 64);
        }
        else {
            this.output.write(247);
            this.write16(a1);
        }
        this.writeTypeInfo(a2, a3);
    }
    
    public void chopFrame(final int a1, final int a2) {
        ++this.numOfEntries;
        this.output.write(251 - a2);
        this.write16(a1);
    }
    
    public void appendFrame(final int a3, final int[] v1, final int[] v2) {
        ++this.numOfEntries;
        final int v3 = v1.length;
        this.output.write(v3 + 251);
        this.write16(a3);
        for (int a4 = 0; a4 < v3; ++a4) {
            this.writeTypeInfo(v1[a4], v2[a4]);
        }
    }
    
    public void fullFrame(final int a4, final int[] a5, final int[] v1, final int[] v2, final int[] v3) {
        ++this.numOfEntries;
        this.output.write(255);
        this.write16(a4);
        int v4 = a5.length;
        this.write16(v4);
        for (int a6 = 0; a6 < v4; ++a6) {
            this.writeTypeInfo(a5[a6], v1[a6]);
        }
        v4 = v2.length;
        this.write16(v4);
        for (int a7 = 0; a7 < v4; ++a7) {
            this.writeTypeInfo(v2[a7], v3[a7]);
        }
    }
    
    private void writeTypeInfo(final int a1, final int a2) {
        this.output.write(a1);
        if (a1 == 7 || a1 == 8) {
            this.write16(a2);
        }
    }
    
    private void write16(final int a1) {
        this.output.write(a1 >>> 8 & 0xFF);
        this.output.write(a1 & 0xFF);
    }
}
