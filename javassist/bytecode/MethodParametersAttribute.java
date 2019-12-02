package javassist.bytecode;

import java.io.*;
import java.util.*;

public class MethodParametersAttribute extends AttributeInfo
{
    public static final String tag = "MethodParameters";
    
    MethodParametersAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public MethodParametersAttribute(final ConstPool a3, final String[] v1, final int[] v2) {
        super(a3, "MethodParameters");
        final byte[] v3 = new byte[v1.length * 4 + 1];
        v3[0] = (byte)v1.length;
        for (int a4 = 0; a4 < v1.length; ++a4) {
            ByteArray.write16bit(a3.addUtf8Info(v1[a4]), v3, a4 * 4 + 1);
            ByteArray.write16bit(v2[a4], v3, a4 * 4 + 3);
        }
        this.set(v3);
    }
    
    public int size() {
        return this.info[0] & 0xFF;
    }
    
    public int name(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 4 + 1);
    }
    
    public int accessFlags(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 4 + 3);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) {
        final int v3 = this.size();
        final ConstPool v4 = this.getConstPool();
        final String[] v5 = new String[v3];
        final int[] v6 = new int[v3];
        for (int a1 = 0; a1 < v3; ++a1) {
            v5[a1] = v4.getUtf8Info(this.name(a1));
            v6[a1] = this.accessFlags(a1);
        }
        return new MethodParametersAttribute(v1, v5, v6);
    }
}
