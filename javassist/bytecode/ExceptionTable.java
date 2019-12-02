package javassist.bytecode;

import java.util.*;
import java.io.*;

public class ExceptionTable implements Cloneable
{
    private ConstPool constPool;
    private ArrayList entries;
    
    public ExceptionTable(final ConstPool a1) {
        super();
        this.constPool = a1;
        this.entries = new ArrayList();
    }
    
    ExceptionTable(final ConstPool v-6, final DataInputStream v-5) throws IOException {
        super();
        this.constPool = v-6;
        final int unsignedShort = v-5.readUnsignedShort();
        final ArrayList entries = new ArrayList(unsignedShort);
        for (int i = 0; i < unsignedShort; ++i) {
            final int a1 = v-5.readUnsignedShort();
            final int a2 = v-5.readUnsignedShort();
            final int v1 = v-5.readUnsignedShort();
            final int v2 = v-5.readUnsignedShort();
            entries.add(new ExceptionTableEntry(a1, a2, v1, v2));
        }
        this.entries = entries;
    }
    
    public Object clone() throws CloneNotSupportedException {
        final ExceptionTable v1 = (ExceptionTable)super.clone();
        v1.entries = new ArrayList(this.entries);
        return v1;
    }
    
    public int size() {
        return this.entries.size();
    }
    
    public int startPc(final int a1) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        return v1.startPc;
    }
    
    public void setStartPc(final int a1, final int a2) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        v1.startPc = a2;
    }
    
    public int endPc(final int a1) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        return v1.endPc;
    }
    
    public void setEndPc(final int a1, final int a2) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        v1.endPc = a2;
    }
    
    public int handlerPc(final int a1) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        return v1.handlerPc;
    }
    
    public void setHandlerPc(final int a1, final int a2) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        v1.handlerPc = a2;
    }
    
    public int catchType(final int a1) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        return v1.catchType;
    }
    
    public void setCatchType(final int a1, final int a2) {
        final ExceptionTableEntry v1 = this.entries.get(a1);
        v1.catchType = a2;
    }
    
    public void add(final int a3, final ExceptionTable v1, final int v2) {
        int v3 = v1.size();
        while (--v3 >= 0) {
            final ExceptionTableEntry a4 = v1.entries.get(v3);
            this.add(a3, a4.startPc + v2, a4.endPc + v2, a4.handlerPc + v2, a4.catchType);
        }
    }
    
    public void add(final int a1, final int a2, final int a3, final int a4, final int a5) {
        if (a2 < a3) {
            this.entries.add(a1, new ExceptionTableEntry(a2, a3, a4, a5));
        }
    }
    
    public void add(final int a1, final int a2, final int a3, final int a4) {
        if (a1 < a2) {
            this.entries.add(new ExceptionTableEntry(a1, a2, a3, a4));
        }
    }
    
    public void remove(final int a1) {
        this.entries.remove(a1);
    }
    
    public ExceptionTable copy(final ConstPool v-4, final Map v-3) {
        final ExceptionTable exceptionTable = new ExceptionTable(v-4);
        final ConstPool constPool = this.constPool;
        for (int v0 = this.size(), v2 = 0; v2 < v0; ++v2) {
            final ExceptionTableEntry a1 = this.entries.get(v2);
            final int a2 = constPool.copy(a1.catchType, v-4, v-3);
            exceptionTable.add(a1.startPc, a1.endPc, a1.handlerPc, a2);
        }
        return exceptionTable;
    }
    
    void shiftPc(final int v1, final int v2, final boolean v3) {
        for (int v4 = this.size(), a2 = 0; a2 < v4; ++a2) {
            final ExceptionTableEntry a3 = this.entries.get(a2);
            a3.startPc = shiftPc(a3.startPc, v1, v2, v3);
            a3.endPc = shiftPc(a3.endPc, v1, v2, v3);
            a3.handlerPc = shiftPc(a3.handlerPc, v1, v2, v3);
        }
    }
    
    private static int shiftPc(int a1, final int a2, final int a3, final boolean a4) {
        if (a1 > a2 || (a4 && a1 == a2)) {
            a1 += a3;
        }
        return a1;
    }
    
    void write(final DataOutputStream v-1) throws IOException {
        final int v0 = this.size();
        v-1.writeShort(v0);
        for (int v2 = 0; v2 < v0; ++v2) {
            final ExceptionTableEntry a1 = this.entries.get(v2);
            v-1.writeShort(a1.startPc);
            v-1.writeShort(a1.endPc);
            v-1.writeShort(a1.handlerPc);
            v-1.writeShort(a1.catchType);
        }
    }
}
