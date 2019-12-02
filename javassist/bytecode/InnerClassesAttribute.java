package javassist.bytecode;

import java.io.*;
import java.util.*;

public class InnerClassesAttribute extends AttributeInfo
{
    public static final String tag = "InnerClasses";
    
    InnerClassesAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    private InnerClassesAttribute(final ConstPool a1, final byte[] a2) {
        super(a1, "InnerClasses", a2);
    }
    
    public InnerClassesAttribute(final ConstPool a1) {
        super(a1, "InnerClasses", new byte[2]);
        ByteArray.write16bit(0, this.get(), 0);
    }
    
    public int tableLength() {
        return ByteArray.readU16bit(this.get(), 0);
    }
    
    public int innerClassIndex(final int a1) {
        return ByteArray.readU16bit(this.get(), a1 * 8 + 2);
    }
    
    public String innerClass(final int a1) {
        final int v1 = this.innerClassIndex(a1);
        if (v1 == 0) {
            return null;
        }
        return this.constPool.getClassInfo(v1);
    }
    
    public void setInnerClassIndex(final int a1, final int a2) {
        ByteArray.write16bit(a2, this.get(), a1 * 8 + 2);
    }
    
    public int outerClassIndex(final int a1) {
        return ByteArray.readU16bit(this.get(), a1 * 8 + 4);
    }
    
    public String outerClass(final int a1) {
        final int v1 = this.outerClassIndex(a1);
        if (v1 == 0) {
            return null;
        }
        return this.constPool.getClassInfo(v1);
    }
    
    public void setOuterClassIndex(final int a1, final int a2) {
        ByteArray.write16bit(a2, this.get(), a1 * 8 + 4);
    }
    
    public int innerNameIndex(final int a1) {
        return ByteArray.readU16bit(this.get(), a1 * 8 + 6);
    }
    
    public String innerName(final int a1) {
        final int v1 = this.innerNameIndex(a1);
        if (v1 == 0) {
            return null;
        }
        return this.constPool.getUtf8Info(v1);
    }
    
    public void setInnerNameIndex(final int a1, final int a2) {
        ByteArray.write16bit(a2, this.get(), a1 * 8 + 6);
    }
    
    public int accessFlags(final int a1) {
        return ByteArray.readU16bit(this.get(), a1 * 8 + 8);
    }
    
    public void setAccessFlags(final int a1, final int a2) {
        ByteArray.write16bit(a2, this.get(), a1 * 8 + 8);
    }
    
    public void append(final String a1, final String a2, final String a3, final int a4) {
        final int v1 = this.constPool.addClassInfo(a1);
        final int v2 = this.constPool.addClassInfo(a2);
        final int v3 = this.constPool.addUtf8Info(a3);
        this.append(v1, v2, v3, a4);
    }
    
    public void append(final int a3, final int a4, final int v1, final int v2) {
        final byte[] v3 = this.get();
        final int v4 = v3.length;
        final byte[] v5 = new byte[v4 + 8];
        for (int a5 = 2; a5 < v4; ++a5) {
            v5[a5] = v3[a5];
        }
        final int v6 = ByteArray.readU16bit(v3, 0);
        ByteArray.write16bit(v6 + 1, v5, 0);
        ByteArray.write16bit(a3, v5, v4);
        ByteArray.write16bit(a4, v5, v4 + 2);
        ByteArray.write16bit(v1, v5, v4 + 4);
        ByteArray.write16bit(v2, v5, v4 + 6);
        this.set(v5);
    }
    
    public int remove(final int a1) {
        final byte[] v1 = this.get();
        final int v2 = v1.length;
        if (v2 < 10) {
            return 0;
        }
        final int v3 = ByteArray.readU16bit(v1, 0);
        final int v4 = 2 + a1 * 8;
        if (v3 <= a1) {
            return v3;
        }
        final byte[] v5 = new byte[v2 - 8];
        ByteArray.write16bit(v3 - 1, v5, 0);
        int v6 = 2;
        int v7 = 2;
        while (v6 < v2) {
            if (v6 == v4) {
                v6 += 8;
            }
            else {
                v5[v7++] = v1[v6++];
            }
        }
        this.set(v5);
        return v3 - 1;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v-10, final Map v-9) {
        final byte[] value = this.get();
        final byte[] array = new byte[value.length];
        final ConstPool constPool = this.getConstPool();
        final InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute(v-10, array);
        final int u16bit = ByteArray.readU16bit(value, 0);
        ByteArray.write16bit(u16bit, array, 0);
        int n = 2;
        for (int i = 0; i < u16bit; ++i) {
            int a1 = ByteArray.readU16bit(value, n);
            int a2 = ByteArray.readU16bit(value, n + 2);
            int v1 = ByteArray.readU16bit(value, n + 4);
            final int v2 = ByteArray.readU16bit(value, n + 6);
            if (a1 != 0) {
                a1 = constPool.copy(a1, v-10, v-9);
            }
            ByteArray.write16bit(a1, array, n);
            if (a2 != 0) {
                a2 = constPool.copy(a2, v-10, v-9);
            }
            ByteArray.write16bit(a2, array, n + 2);
            if (v1 != 0) {
                v1 = constPool.copy(v1, v-10, v-9);
            }
            ByteArray.write16bit(v1, array, n + 4);
            ByteArray.write16bit(v2, array, n + 6);
            n += 8;
        }
        return innerClassesAttribute;
    }
}
