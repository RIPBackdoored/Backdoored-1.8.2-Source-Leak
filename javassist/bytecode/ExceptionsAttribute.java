package javassist.bytecode;

import java.io.*;
import java.util.*;

public class ExceptionsAttribute extends AttributeInfo
{
    public static final String tag = "Exceptions";
    
    ExceptionsAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    private ExceptionsAttribute(final ConstPool a1, final ExceptionsAttribute a2, final Map a3) {
        super(a1, "Exceptions");
        this.copyFrom(a2, a3);
    }
    
    public ExceptionsAttribute(final ConstPool a1) {
        super(a1, "Exceptions");
        final byte[] v1 = new byte[2];
        v1[0] = (v1[1] = 0);
        this.info = v1;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool a1, final Map a2) {
        return new ExceptionsAttribute(a1, this, a2);
    }
    
    private void copyFrom(final ExceptionsAttribute v2, final Map v3) {
        final ConstPool v4 = v2.constPool;
        final ConstPool v5 = this.constPool;
        final byte[] v6 = v2.info;
        final int v7 = v6.length;
        final byte[] v8 = new byte[v7];
        v8[0] = v6[0];
        v8[1] = v6[1];
        for (int a2 = 2; a2 < v7; a2 += 2) {
            final int a3 = ByteArray.readU16bit(v6, a2);
            ByteArray.write16bit(v4.copy(a3, v5, v3), v8, a2);
        }
        this.info = v8;
    }
    
    public int[] getExceptionIndexes() {
        final byte[] info = this.info;
        final int length = info.length;
        if (length <= 2) {
            return null;
        }
        final int[] array = new int[length / 2 - 1];
        int v0 = 0;
        for (int v2 = 2; v2 < length; v2 += 2) {
            array[v0++] = ((info[v2] & 0xFF) << 8 | (info[v2 + 1] & 0xFF));
        }
        return array;
    }
    
    public String[] getExceptions() {
        final byte[] info = this.info;
        final int length = info.length;
        if (length <= 2) {
            return null;
        }
        final String[] array = new String[length / 2 - 1];
        int n = 0;
        for (int v0 = 2; v0 < length; v0 += 2) {
            final int v2 = (info[v0] & 0xFF) << 8 | (info[v0 + 1] & 0xFF);
            array[n++] = this.constPool.getClassInfo(v2);
        }
        return array;
    }
    
    public void setExceptionIndexes(final int[] v2) {
        final int v3 = v2.length;
        final byte[] v4 = new byte[v3 * 2 + 2];
        ByteArray.write16bit(v3, v4, 0);
        for (int a1 = 0; a1 < v3; ++a1) {
            ByteArray.write16bit(v2[a1], v4, a1 * 2 + 2);
        }
        this.info = v4;
    }
    
    public void setExceptions(final String[] v2) {
        final int v3 = v2.length;
        final byte[] v4 = new byte[v3 * 2 + 2];
        ByteArray.write16bit(v3, v4, 0);
        for (int a1 = 0; a1 < v3; ++a1) {
            ByteArray.write16bit(this.constPool.addClassInfo(v2[a1]), v4, a1 * 2 + 2);
        }
        this.info = v4;
    }
    
    public int tableLength() {
        return this.info.length / 2 - 1;
    }
    
    public int getException(final int a1) {
        final int v1 = a1 * 2 + 2;
        return (this.info[v1] & 0xFF) << 8 | (this.info[v1 + 1] & 0xFF);
    }
}
