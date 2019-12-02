package javassist.bytecode;

import java.io.*;
import java.util.*;

public class LocalVariableAttribute extends AttributeInfo
{
    public static final String tag = "LocalVariableTable";
    public static final String typeTag = "LocalVariableTypeTable";
    
    public LocalVariableAttribute(final ConstPool a1) {
        super(a1, "LocalVariableTable", new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }
    
    @Deprecated
    public LocalVariableAttribute(final ConstPool a1, final String a2) {
        super(a1, a2, new byte[2]);
        ByteArray.write16bit(0, this.info, 0);
    }
    
    LocalVariableAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    LocalVariableAttribute(final ConstPool a1, final String a2, final byte[] a3) {
        super(a1, a2, a3);
    }
    
    public void addEntry(final int a3, final int a4, final int a5, final int v1, final int v2) {
        final int v3 = this.info.length;
        final byte[] v4 = new byte[v3 + 10];
        ByteArray.write16bit(this.tableLength() + 1, v4, 0);
        for (int a6 = 2; a6 < v3; ++a6) {
            v4[a6] = this.info[a6];
        }
        ByteArray.write16bit(a3, v4, v3);
        ByteArray.write16bit(a4, v4, v3 + 2);
        ByteArray.write16bit(a5, v4, v3 + 4);
        ByteArray.write16bit(v1, v4, v3 + 6);
        ByteArray.write16bit(v2, v4, v3 + 8);
        this.info = v4;
    }
    
    @Override
    void renameClass(final String v-5, final String v-4) {
        final ConstPool constPool = this.getConstPool();
        for (int tableLength = this.tableLength(), i = 0; i < tableLength; ++i) {
            final int a2 = i * 10 + 2;
            final int v1 = ByteArray.readU16bit(this.info, a2 + 6);
            if (v1 != 0) {
                String a3 = constPool.getUtf8Info(v1);
                a3 = this.renameEntry(a3, v-5, v-4);
                ByteArray.write16bit(constPool.addUtf8Info(a3), this.info, a2 + 6);
            }
        }
    }
    
    String renameEntry(final String a1, final String a2, final String a3) {
        return Descriptor.rename(a1, a2, a3);
    }
    
    @Override
    void renameClass(final Map v-3) {
        final ConstPool constPool = this.getConstPool();
        for (int tableLength = this.tableLength(), v0 = 0; v0 < tableLength; ++v0) {
            final int v2 = v0 * 10 + 2;
            final int v3 = ByteArray.readU16bit(this.info, v2 + 6);
            if (v3 != 0) {
                String a1 = constPool.getUtf8Info(v3);
                a1 = this.renameEntry(a1, v-3);
                ByteArray.write16bit(constPool.addUtf8Info(a1), this.info, v2 + 6);
            }
        }
    }
    
    String renameEntry(final String a1, final Map a2) {
        return Descriptor.rename(a1, a2);
    }
    
    public void shiftIndex(final int v2, final int v3) {
        for (int v4 = this.info.length, a2 = 2; a2 < v4; a2 += 10) {
            final int a3 = ByteArray.readU16bit(this.info, a2 + 8);
            if (a3 >= v2) {
                ByteArray.write16bit(a3 + v3, this.info, a2 + 8);
            }
        }
    }
    
    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    public int startPc(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 10 + 2);
    }
    
    public int codeLength(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 10 + 4);
    }
    
    void shiftPc(final int v-3, final int v-2, final boolean v-1) {
        for (int v0 = this.tableLength(), v2 = 0; v2 < v0; ++v2) {
            final int a1 = v2 * 10 + 2;
            final int a2 = ByteArray.readU16bit(this.info, a1);
            final int a3 = ByteArray.readU16bit(this.info, a1 + 2);
            if (a2 > v-3 || (v-1 && a2 == v-3 && a2 != 0)) {
                ByteArray.write16bit(a2 + v-2, this.info, a1);
            }
            else if (a2 + a3 > v-3 || (v-1 && a2 + a3 == v-3)) {
                ByteArray.write16bit(a3 + v-2, this.info, a1 + 2);
            }
        }
    }
    
    public int nameIndex(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 10 + 6);
    }
    
    public String variableName(final int a1) {
        return this.getConstPool().getUtf8Info(this.nameIndex(a1));
    }
    
    public int descriptorIndex(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 10 + 8);
    }
    
    public int signatureIndex(final int a1) {
        return this.descriptorIndex(a1);
    }
    
    public String descriptor(final int a1) {
        return this.getConstPool().getUtf8Info(this.descriptorIndex(a1));
    }
    
    public String signature(final int a1) {
        return this.descriptor(a1);
    }
    
    public int index(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 10 + 10);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v-9, final Map v-8) {
        final byte[] value = this.get();
        final byte[] a4 = new byte[value.length];
        final ConstPool constPool = this.getConstPool();
        final LocalVariableAttribute thisAttr = this.makeThisAttr(v-9, a4);
        final int u16bit = ByteArray.readU16bit(value, 0);
        ByteArray.write16bit(u16bit, a4, 0);
        int n = 2;
        for (int i = 0; i < u16bit; ++i) {
            final int a2 = ByteArray.readU16bit(value, n);
            final int v1 = ByteArray.readU16bit(value, n + 2);
            int v2 = ByteArray.readU16bit(value, n + 4);
            int v3 = ByteArray.readU16bit(value, n + 6);
            final int v4 = ByteArray.readU16bit(value, n + 8);
            ByteArray.write16bit(a2, a4, n);
            ByteArray.write16bit(v1, a4, n + 2);
            if (v2 != 0) {
                v2 = constPool.copy(v2, v-9, null);
            }
            ByteArray.write16bit(v2, a4, n + 4);
            if (v3 != 0) {
                String a3 = constPool.getUtf8Info(v3);
                a3 = Descriptor.rename(a3, v-8);
                v3 = v-9.addUtf8Info(a3);
            }
            ByteArray.write16bit(v3, a4, n + 6);
            ByteArray.write16bit(v4, a4, n + 8);
            n += 10;
        }
        return thisAttr;
    }
    
    LocalVariableAttribute makeThisAttr(final ConstPool a1, final byte[] a2) {
        return new LocalVariableAttribute(a1, "LocalVariableTable", a2);
    }
}
