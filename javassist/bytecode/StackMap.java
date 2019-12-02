package javassist.bytecode;

import java.util.*;
import javassist.*;
import java.io.*;

public class StackMap extends AttributeInfo
{
    public static final String tag = "StackMap";
    public static final int TOP = 0;
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int DOUBLE = 3;
    public static final int LONG = 4;
    public static final int NULL = 5;
    public static final int THIS = 6;
    public static final int OBJECT = 7;
    public static final int UNINIT = 8;
    
    StackMap(final ConstPool a1, final byte[] a2) {
        super(a1, "StackMap", a2);
    }
    
    StackMap(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public int numOfEntries() {
        return ByteArray.readU16bit(this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool a1, final Map a2) {
        final Copier v1 = new Copier(this, a1, a2);
        v1.visit();
        return v1.getStackMap();
    }
    
    public void insertLocal(final int a1, final int a2, final int a3) throws BadBytecode {
        final byte[] v1 = new InsertLocal(this, a1, a2, a3).doit();
        this.set(v1);
    }
    
    void shiftPc(final int a1, final int a2, final boolean a3) throws BadBytecode {
        new Shifter(this, a1, a2, a3).visit();
    }
    
    void shiftForSwitch(final int a1, final int a2) throws BadBytecode {
        new SwitchShifter(this, a1, a2).visit();
    }
    
    public void removeNew(final int a1) throws CannotCompileException {
        final byte[] v1 = new NewRemover(this, a1).doit();
        this.set(v1);
    }
    
    public void print(final PrintWriter a1) {
        new Printer(this, a1).print();
    }
    
    public static class Walker
    {
        byte[] info;
        
        public Walker(final StackMap a1) {
            super();
            this.info = a1.get();
        }
        
        public void visit() {
            final int u16bit = ByteArray.readU16bit(this.info, 0);
            int n = 2;
            for (int v0 = 0; v0 < u16bit; ++v0) {
                final int v2 = ByteArray.readU16bit(this.info, n);
                final int v3 = ByteArray.readU16bit(this.info, n + 2);
                n = this.locals(n + 4, v2, v3);
                final int v4 = ByteArray.readU16bit(this.info, n);
                n = this.stack(n + 2, v2, v4);
            }
        }
        
        public int locals(final int a1, final int a2, final int a3) {
            return this.typeInfoArray(a1, a2, a3, true);
        }
        
        public int stack(final int a1, final int a2, final int a3) {
            return this.typeInfoArray(a1, a2, a3, false);
        }
        
        public int typeInfoArray(int a3, final int a4, final int v1, final boolean v2) {
            for (int a5 = 0; a5 < v1; ++a5) {
                a3 = this.typeInfoArray2(a5, a3);
            }
            return a3;
        }
        
        int typeInfoArray2(final int v2, int v3) {
            final byte v4 = this.info[v3];
            if (v4 == 7) {
                final int a1 = ByteArray.readU16bit(this.info, v3 + 1);
                this.objectVariable(v3, a1);
                v3 += 3;
            }
            else if (v4 == 8) {
                final int a2 = ByteArray.readU16bit(this.info, v3 + 1);
                this.uninitialized(v3, a2);
                v3 += 3;
            }
            else {
                this.typeInfo(v3, v4);
                ++v3;
            }
            return v3;
        }
        
        public void typeInfo(final int a1, final byte a2) {
        }
        
        public void objectVariable(final int a1, final int a2) {
        }
        
        public void uninitialized(final int a1, final int a2) {
        }
    }
    
    static class Copier extends Walker
    {
        byte[] dest;
        ConstPool srcCp;
        ConstPool destCp;
        Map classnames;
        
        Copier(final StackMap a1, final ConstPool a2, final Map a3) {
            super(a1);
            this.srcCp = a1.getConstPool();
            this.dest = new byte[this.info.length];
            this.destCp = a2;
            this.classnames = a3;
        }
        
        @Override
        public void visit() {
            final int v1 = ByteArray.readU16bit(this.info, 0);
            ByteArray.write16bit(v1, this.dest, 0);
            super.visit();
        }
        
        @Override
        public int locals(final int a1, final int a2, final int a3) {
            ByteArray.write16bit(a2, this.dest, a1 - 4);
            return super.locals(a1, a2, a3);
        }
        
        @Override
        public int typeInfoArray(final int a1, final int a2, final int a3, final boolean a4) {
            ByteArray.write16bit(a3, this.dest, a1 - 2);
            return super.typeInfoArray(a1, a2, a3, a4);
        }
        
        @Override
        public void typeInfo(final int a1, final byte a2) {
            this.dest[a1] = a2;
        }
        
        @Override
        public void objectVariable(final int a1, final int a2) {
            this.dest[a1] = 7;
            final int v1 = this.srcCp.copy(a2, this.destCp, this.classnames);
            ByteArray.write16bit(v1, this.dest, a1 + 1);
        }
        
        @Override
        public void uninitialized(final int a1, final int a2) {
            this.dest[a1] = 8;
            ByteArray.write16bit(a2, this.dest, a1 + 1);
        }
        
        public StackMap getStackMap() {
            return new StackMap(this.destCp, this.dest);
        }
    }
    
    static class SimpleCopy extends Walker
    {
        Writer writer;
        
        SimpleCopy(final StackMap a1) {
            super(a1);
            this.writer = new Writer();
        }
        
        byte[] doit() {
            this.visit();
            return this.writer.toByteArray();
        }
        
        @Override
        public void visit() {
            final int v1 = ByteArray.readU16bit(this.info, 0);
            this.writer.write16bit(v1);
            super.visit();
        }
        
        @Override
        public int locals(final int a1, final int a2, final int a3) {
            this.writer.write16bit(a2);
            return super.locals(a1, a2, a3);
        }
        
        @Override
        public int typeInfoArray(final int a1, final int a2, final int a3, final boolean a4) {
            this.writer.write16bit(a3);
            return super.typeInfoArray(a1, a2, a3, a4);
        }
        
        @Override
        public void typeInfo(final int a1, final byte a2) {
            this.writer.writeVerifyTypeInfo(a2, 0);
        }
        
        @Override
        public void objectVariable(final int a1, final int a2) {
            this.writer.writeVerifyTypeInfo(7, a2);
        }
        
        @Override
        public void uninitialized(final int a1, final int a2) {
            this.writer.writeVerifyTypeInfo(8, a2);
        }
    }
    
    static class InsertLocal extends SimpleCopy
    {
        private int varIndex;
        private int varTag;
        private int varData;
        
        InsertLocal(final StackMap a1, final int a2, final int a3, final int a4) {
            super(a1);
            this.varIndex = a2;
            this.varTag = a3;
            this.varData = a4;
        }
        
        @Override
        public int typeInfoArray(int a3, final int a4, final int v1, final boolean v2) {
            if (!v2 || v1 < this.varIndex) {
                return super.typeInfoArray(a3, a4, v1, v2);
            }
            this.writer.write16bit(v1 + 1);
            for (int a5 = 0; a5 < v1; ++a5) {
                if (a5 == this.varIndex) {
                    this.writeVarTypeInfo();
                }
                a3 = this.typeInfoArray2(a5, a3);
            }
            if (v1 == this.varIndex) {
                this.writeVarTypeInfo();
            }
            return a3;
        }
        
        private void writeVarTypeInfo() {
            if (this.varTag == 7) {
                this.writer.writeVerifyTypeInfo(7, this.varData);
            }
            else if (this.varTag == 8) {
                this.writer.writeVerifyTypeInfo(8, this.varData);
            }
            else {
                this.writer.writeVerifyTypeInfo(this.varTag, 0);
            }
        }
    }
    
    static class Shifter extends Walker
    {
        private int where;
        private int gap;
        private boolean exclusive;
        
        public Shifter(final StackMap a1, final int a2, final int a3, final boolean a4) {
            super(a1);
            this.where = a2;
            this.gap = a3;
            this.exclusive = a4;
        }
        
        @Override
        public int locals(final int a1, final int a2, final int a3) {
            if (this.exclusive) {
                if (this.where > a2) {
                    return super.locals(a1, a2, a3);
                }
            }
            else if (this.where >= a2) {
                return super.locals(a1, a2, a3);
            }
            ByteArray.write16bit(a2 + this.gap, this.info, a1 - 4);
            return super.locals(a1, a2, a3);
        }
        
        @Override
        public void uninitialized(final int a1, final int a2) {
            if (this.where <= a2) {
                ByteArray.write16bit(a2 + this.gap, this.info, a1 + 1);
            }
        }
    }
    
    static class SwitchShifter extends Walker
    {
        private int where;
        private int gap;
        
        public SwitchShifter(final StackMap a1, final int a2, final int a3) {
            super(a1);
            this.where = a2;
            this.gap = a3;
        }
        
        @Override
        public int locals(final int a1, final int a2, final int a3) {
            if (this.where == a1 + a2) {
                ByteArray.write16bit(a2 - this.gap, this.info, a1 - 4);
            }
            else if (this.where == a1) {
                ByteArray.write16bit(a2 + this.gap, this.info, a1 - 4);
            }
            return super.locals(a1, a2, a3);
        }
    }
    
    static class NewRemover extends SimpleCopy
    {
        int posOfNew;
        
        NewRemover(final StackMap a1, final int a2) {
            super(a1);
            this.posOfNew = a2;
        }
        
        @Override
        public int stack(final int a1, final int a2, final int a3) {
            return this.stackTypeInfoArray(a1, a2, a3);
        }
        
        private int stackTypeInfoArray(int v-6, final int v-5, final int v-4) {
            int n = v-6;
            int n2 = 0;
            for (int a3 = 0; a3 < v-4; ++a3) {
                final byte a4 = this.info[n];
                if (a4 == 7) {
                    n += 3;
                }
                else if (a4 == 8) {
                    final int a5 = ByteArray.readU16bit(this.info, n + 1);
                    if (a5 == this.posOfNew) {
                        ++n2;
                    }
                    n += 3;
                }
                else {
                    ++n;
                }
            }
            this.writer.write16bit(v-4 - n2);
            for (int i = 0; i < v-4; ++i) {
                final byte v0 = this.info[v-6];
                if (v0 == 7) {
                    final int v2 = ByteArray.readU16bit(this.info, v-6 + 1);
                    this.objectVariable(v-6, v2);
                    v-6 += 3;
                }
                else if (v0 == 8) {
                    final int v2 = ByteArray.readU16bit(this.info, v-6 + 1);
                    if (v2 != this.posOfNew) {
                        this.uninitialized(v-6, v2);
                    }
                    v-6 += 3;
                }
                else {
                    this.typeInfo(v-6, v0);
                    ++v-6;
                }
            }
            return v-6;
        }
    }
    
    static class Printer extends Walker
    {
        private PrintWriter writer;
        
        public Printer(final StackMap a1, final PrintWriter a2) {
            super(a1);
            this.writer = a2;
        }
        
        public void print() {
            final int v1 = ByteArray.readU16bit(this.info, 0);
            this.writer.println(v1 + " entries");
            this.visit();
        }
        
        @Override
        public int locals(final int a1, final int a2, final int a3) {
            this.writer.println("  * offset " + a2);
            return super.locals(a1, a2, a3);
        }
    }
    
    public static class Writer
    {
        private ByteArrayOutputStream output;
        
        public Writer() {
            super();
            this.output = new ByteArrayOutputStream();
        }
        
        public byte[] toByteArray() {
            return this.output.toByteArray();
        }
        
        public StackMap toStackMap(final ConstPool a1) {
            return new StackMap(a1, this.output.toByteArray());
        }
        
        public void writeVerifyTypeInfo(final int a1, final int a2) {
            this.output.write(a1);
            if (a1 == 7 || a1 == 8) {
                this.write16bit(a2);
            }
        }
        
        public void write16bit(final int a1) {
            this.output.write(a1 >>> 8 & 0xFF);
            this.output.write(a1 & 0xFF);
        }
    }
}
