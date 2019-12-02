package javassist.bytecode;

import java.io.*;
import java.util.*;

public class LineNumberAttribute extends AttributeInfo
{
    public static final String tag = "LineNumberTable";
    
    LineNumberAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    private LineNumberAttribute(final ConstPool a1, final byte[] a2) {
        super(a1, "LineNumberTable", a2);
    }
    
    public int tableLength() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    public int startPc(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 4 + 2);
    }
    
    public int lineNumber(final int a1) {
        return ByteArray.readU16bit(this.info, a1 * 4 + 4);
    }
    
    public int toLineNumber(final int a1) {
        final int v1 = this.tableLength();
        int v2 = 0;
        while (v2 < v1) {
            if (a1 < this.startPc(v2)) {
                if (v2 == 0) {
                    return this.lineNumber(0);
                }
                break;
            }
            else {
                ++v2;
            }
        }
        return this.lineNumber(v2 - 1);
    }
    
    public int toStartPc(final int v2) {
        for (int v3 = this.tableLength(), a1 = 0; a1 < v3; ++a1) {
            if (v2 == this.lineNumber(a1)) {
                return this.startPc(a1);
            }
        }
        return -1;
    }
    
    public Pc toNearPc(final int v-3) {
        final int tableLength = this.tableLength();
        int index = 0;
        int v0 = 0;
        if (tableLength > 0) {
            v0 = this.lineNumber(0) - v-3;
            index = this.startPc(0);
        }
        for (int v2 = 1; v2 < tableLength; ++v2) {
            final int a1 = this.lineNumber(v2) - v-3;
            if ((a1 < 0 && a1 > v0) || (a1 >= 0 && (a1 < v0 || v0 < 0))) {
                v0 = a1;
                index = this.startPc(v2);
            }
        }
        final Pc v3 = new Pc();
        v3.index = index;
        v3.line = v-3 + v0;
        return v3;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) {
        final byte[] v3 = this.info;
        final int v4 = v3.length;
        final byte[] v5 = new byte[v4];
        for (int a1 = 0; a1 < v4; ++a1) {
            v5[a1] = v3[a1];
        }
        final LineNumberAttribute v6 = new LineNumberAttribute(v1, v5);
        return v6;
    }
    
    void shiftPc(final int v2, final int v3, final boolean v4) {
        for (int v5 = this.tableLength(), a3 = 0; a3 < v5; ++a3) {
            final int a4 = a3 * 4 + 2;
            final int a5 = ByteArray.readU16bit(this.info, a4);
            if (a5 > v2 || (v4 && a5 == v2)) {
                ByteArray.write16bit(a5 + v3, this.info, a4);
            }
        }
    }
    
    public static class Pc
    {
        public int index;
        public int line;
        
        public Pc() {
            super();
        }
    }
}
