package javassist.bytecode;

import java.util.*;
import javassist.*;
import java.io.*;

public class StackMapTable extends AttributeInfo
{
    public static final String tag = "StackMapTable";
    public static final int TOP = 0;
    public static final int INTEGER = 1;
    public static final int FLOAT = 2;
    public static final int DOUBLE = 3;
    public static final int LONG = 4;
    public static final int NULL = 5;
    public static final int THIS = 6;
    public static final int OBJECT = 7;
    public static final int UNINIT = 8;
    
    StackMapTable(final ConstPool a1, final byte[] a2) {
        super(a1, "StackMapTable", a2);
    }
    
    StackMapTable(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) throws RuntimeCopyException {
        try {
            return new StackMapTable(v1, new Copier(this.constPool, this.info, v1, v2).doit());
        }
        catch (BadBytecode a1) {
            throw new RuntimeCopyException("bad bytecode. fatal?");
        }
    }
    
    @Override
    void write(final DataOutputStream a1) throws IOException {
        super.write(a1);
    }
    
    public void insertLocal(final int a1, final int a2, final int a3) throws BadBytecode {
        final byte[] v1 = new InsertLocal(this.get(), a1, a2, a3).doit();
        this.set(v1);
    }
    
    public static int typeTagOf(final char a1) {
        switch (a1) {
            case 'D': {
                return 3;
            }
            case 'F': {
                return 2;
            }
            case 'J': {
                return 4;
            }
            case 'L':
            case '[': {
                return 7;
            }
            default: {
                return 1;
            }
        }
    }
    
    public void println(final PrintWriter a1) {
        Printer.print(this, a1);
    }
    
    public void println(final PrintStream a1) {
        Printer.print(this, new PrintWriter(a1, true));
    }
    
    void shiftPc(final int a1, final int a2, final boolean a3) throws BadBytecode {
        new OffsetShifter(this, a1, a2).parse();
        new Shifter(this, a1, a2, a3).doit();
    }
    
    void shiftForSwitch(final int a1, final int a2) throws BadBytecode {
        new SwitchShifter(this, a1, a2).doit();
    }
    
    public void removeNew(final int v0) throws CannotCompileException {
        try {
            final byte[] a1 = new NewRemover(this.get(), v0).doit();
            this.set(a1);
        }
        catch (BadBytecode v) {
            throw new CannotCompileException("bad stack map table", v);
        }
    }
    
    public static class RuntimeCopyException extends RuntimeException
    {
        public RuntimeCopyException(final String a1) {
            super(a1);
        }
    }
    
    public static class Walker
    {
        byte[] info;
        int numOfEntries;
        
        public Walker(final StackMapTable a1) {
            this(a1.get());
        }
        
        public Walker(final byte[] a1) {
            super();
            this.info = a1;
            this.numOfEntries = ByteArray.readU16bit(a1, 0);
        }
        
        public final int size() {
            return this.numOfEntries;
        }
        
        public void parse() throws BadBytecode {
            final int numOfEntries = this.numOfEntries;
            int v0 = 2;
            for (int v2 = 0; v2 < numOfEntries; ++v2) {
                v0 = this.stackMapFrames(v0, v2);
            }
        }
        
        int stackMapFrames(int v2, final int v3) throws BadBytecode {
            final int v4 = this.info[v2] & 0xFF;
            if (v4 < 64) {
                this.sameFrame(v2, v4);
                ++v2;
            }
            else if (v4 < 128) {
                v2 = this.sameLocals(v2, v4);
            }
            else {
                if (v4 < 247) {
                    throw new BadBytecode("bad frame_type in StackMapTable");
                }
                if (v4 == 247) {
                    v2 = this.sameLocals(v2, v4);
                }
                else if (v4 < 251) {
                    final int a1 = ByteArray.readU16bit(this.info, v2 + 1);
                    this.chopFrame(v2, a1, 251 - v4);
                    v2 += 3;
                }
                else if (v4 == 251) {
                    final int a2 = ByteArray.readU16bit(this.info, v2 + 1);
                    this.sameFrame(v2, a2);
                    v2 += 3;
                }
                else if (v4 < 255) {
                    v2 = this.appendFrame(v2, v4);
                }
                else {
                    v2 = this.fullFrame(v2);
                }
            }
            return v2;
        }
        
        public void sameFrame(final int a1, final int a2) throws BadBytecode {
        }
        
        private int sameLocals(int v1, final int v2) throws BadBytecode {
            final int v3 = v1;
            int v4 = 0;
            if (v2 < 128) {
                final int a1 = v2 - 64;
            }
            else {
                v4 = ByteArray.readU16bit(this.info, v1 + 1);
                v1 += 2;
            }
            final int v5 = this.info[v1 + 1] & 0xFF;
            int v6 = 0;
            if (v5 == 7 || v5 == 8) {
                v6 = ByteArray.readU16bit(this.info, v1 + 2);
                this.objectOrUninitialized(v5, v6, v1 + 2);
                v1 += 2;
            }
            this.sameLocals(v3, v4, v5, v6);
            return v1 + 2;
        }
        
        public void sameLocals(final int a1, final int a2, final int a3, final int a4) throws BadBytecode {
        }
        
        public void chopFrame(final int a1, final int a2, final int a3) throws BadBytecode {
        }
        
        private int appendFrame(final int v2, final int v3) throws BadBytecode {
            final int v4 = v3 - 251;
            final int v5 = ByteArray.readU16bit(this.info, v2 + 1);
            final int[] v6 = new int[v4];
            final int[] v7 = new int[v4];
            int v8 = v2 + 3;
            for (int a2 = 0; a2 < v4; ++a2) {
                final int a3 = this.info[v8] & 0xFF;
                v6[a2] = a3;
                if (a3 == 7 || a3 == 8) {
                    this.objectOrUninitialized(a3, v7[a2] = ByteArray.readU16bit(this.info, v8 + 1), v8 + 1);
                    v8 += 3;
                }
                else {
                    v7[a2] = 0;
                    ++v8;
                }
            }
            this.appendFrame(v2, v5, v6, v7);
            return v8;
        }
        
        public void appendFrame(final int a1, final int a2, final int[] a3, final int[] a4) throws BadBytecode {
        }
        
        private int fullFrame(final int a1) throws BadBytecode {
            final int v1 = ByteArray.readU16bit(this.info, a1 + 1);
            final int v2 = ByteArray.readU16bit(this.info, a1 + 3);
            final int[] v3 = new int[v2];
            final int[] v4 = new int[v2];
            int v5 = this.verifyTypeInfo(a1 + 5, v2, v3, v4);
            final int v6 = ByteArray.readU16bit(this.info, v5);
            final int[] v7 = new int[v6];
            final int[] v8 = new int[v6];
            v5 = this.verifyTypeInfo(v5 + 2, v6, v7, v8);
            this.fullFrame(a1, v1, v3, v4, v7, v8);
            return v5;
        }
        
        public void fullFrame(final int a1, final int a2, final int[] a3, final int[] a4, final int[] a5, final int[] a6) throws BadBytecode {
        }
        
        private int verifyTypeInfo(int a4, final int v1, final int[] v2, final int[] v3) {
            for (int a5 = 0; a5 < v1; ++a5) {
                final int a6 = this.info[a4++] & 0xFF;
                v2[a5] = a6;
                if (a6 == 7 || a6 == 8) {
                    this.objectOrUninitialized(a6, v3[a5] = ByteArray.readU16bit(this.info, a4), a4);
                    a4 += 2;
                }
            }
            return a4;
        }
        
        public void objectOrUninitialized(final int a1, final int a2, final int a3) {
        }
    }
    
    static class SimpleCopy extends Walker
    {
        private Writer writer;
        
        public SimpleCopy(final byte[] a1) {
            super(a1);
            this.writer = new Writer(a1.length);
        }
        
        public byte[] doit() throws BadBytecode {
            this.parse();
            return this.writer.toByteArray();
        }
        
        @Override
        public void sameFrame(final int a1, final int a2) {
            this.writer.sameFrame(a2);
        }
        
        @Override
        public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
            this.writer.sameLocals(a2, a3, this.copyData(a3, a4));
        }
        
        @Override
        public void chopFrame(final int a1, final int a2, final int a3) {
            this.writer.chopFrame(a2, a3);
        }
        
        @Override
        public void appendFrame(final int a1, final int a2, final int[] a3, final int[] a4) {
            this.writer.appendFrame(a2, a3, this.copyData(a3, a4));
        }
        
        @Override
        public void fullFrame(final int a1, final int a2, final int[] a3, final int[] a4, final int[] a5, final int[] a6) {
            this.writer.fullFrame(a2, a3, this.copyData(a3, a4), a5, this.copyData(a5, a6));
        }
        
        protected int copyData(final int a1, final int a2) {
            return a2;
        }
        
        protected int[] copyData(final int[] a1, final int[] a2) {
            return a2;
        }
    }
    
    static class Copier extends SimpleCopy
    {
        private ConstPool srcPool;
        private ConstPool destPool;
        private Map classnames;
        
        public Copier(final ConstPool a1, final byte[] a2, final ConstPool a3, final Map a4) {
            super(a2);
            this.srcPool = a1;
            this.destPool = a3;
            this.classnames = a4;
        }
        
        @Override
        protected int copyData(final int a1, final int a2) {
            if (a1 == 7) {
                return this.srcPool.copy(a2, this.destPool, this.classnames);
            }
            return a2;
        }
        
        @Override
        protected int[] copyData(final int[] v1, final int[] v2) {
            final int[] v3 = new int[v2.length];
            for (int a1 = 0; a1 < v2.length; ++a1) {
                if (v1[a1] == 7) {
                    v3[a1] = this.srcPool.copy(v2[a1], this.destPool, this.classnames);
                }
                else {
                    v3[a1] = v2[a1];
                }
            }
            return v3;
        }
    }
    
    static class InsertLocal extends SimpleCopy
    {
        private int varIndex;
        private int varTag;
        private int varData;
        
        public InsertLocal(final byte[] a1, final int a2, final int a3, final int a4) {
            super(a1);
            this.varIndex = a2;
            this.varTag = a3;
            this.varData = a4;
        }
        
        @Override
        public void fullFrame(final int a3, final int a4, final int[] a5, final int[] a6, final int[] v1, final int[] v2) {
            final int v3 = a5.length;
            if (v3 < this.varIndex) {
                super.fullFrame(a3, a4, a5, a6, v1, v2);
                return;
            }
            final int v4 = (this.varTag == 4 || this.varTag == 3) ? 2 : 1;
            final int[] v5 = new int[v3 + v4];
            final int[] v6 = new int[v3 + v4];
            final int v7 = this.varIndex;
            int v8 = 0;
            for (int a7 = 0; a7 < v3; ++a7) {
                if (v8 == v7) {
                    v8 += v4;
                }
                v5[v8] = a5[a7];
                v6[v8++] = a6[a7];
            }
            v5[v7] = this.varTag;
            v6[v7] = this.varData;
            if (v4 > 1) {
                v6[v7 + 1] = (v5[v7 + 1] = 0);
            }
            super.fullFrame(a3, a4, v5, v6, v1, v2);
        }
    }
    
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
    
    static class Printer extends Walker
    {
        private PrintWriter writer;
        private int offset;
        
        public static void print(final StackMapTable a2, final PrintWriter v1) {
            try {
                new Printer(a2.get(), v1).parse();
            }
            catch (BadBytecode a3) {
                v1.println(a3.getMessage());
            }
        }
        
        Printer(final byte[] a1, final PrintWriter a2) {
            super(a1);
            this.writer = a2;
            this.offset = -1;
        }
        
        @Override
        public void sameFrame(final int a1, final int a2) {
            this.offset += a2 + 1;
            this.writer.println(this.offset + " same frame: " + a2);
        }
        
        @Override
        public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
            this.offset += a2 + 1;
            this.writer.println(this.offset + " same locals: " + a2);
            this.printTypeInfo(a3, a4);
        }
        
        @Override
        public void chopFrame(final int a1, final int a2, final int a3) {
            this.offset += a2 + 1;
            this.writer.println(this.offset + " chop frame: " + a2 + ",    " + a3 + " last locals");
        }
        
        @Override
        public void appendFrame(final int a3, final int a4, final int[] v1, final int[] v2) {
            this.offset += a4 + 1;
            this.writer.println(this.offset + " append frame: " + a4);
            for (int a5 = 0; a5 < v1.length; ++a5) {
                this.printTypeInfo(v1[a5], v2[a5]);
            }
        }
        
        @Override
        public void fullFrame(final int a4, final int a5, final int[] a6, final int[] v1, final int[] v2, final int[] v3) {
            this.offset += a5 + 1;
            this.writer.println(this.offset + " full frame: " + a5);
            this.writer.println("[locals]");
            for (int a7 = 0; a7 < a6.length; ++a7) {
                this.printTypeInfo(a6[a7], v1[a7]);
            }
            this.writer.println("[stack]");
            for (int a8 = 0; a8 < v2.length; ++a8) {
                this.printTypeInfo(v2[a8], v3[a8]);
            }
        }
        
        private void printTypeInfo(final int a1, final int a2) {
            String v1 = null;
            switch (a1) {
                case 0: {
                    v1 = "top";
                    break;
                }
                case 1: {
                    v1 = "integer";
                    break;
                }
                case 2: {
                    v1 = "float";
                    break;
                }
                case 3: {
                    v1 = "double";
                    break;
                }
                case 4: {
                    v1 = "long";
                    break;
                }
                case 5: {
                    v1 = "null";
                    break;
                }
                case 6: {
                    v1 = "this";
                    break;
                }
                case 7: {
                    v1 = "object (cpool_index " + a2 + ")";
                    break;
                }
                case 8: {
                    v1 = "uninitialized (offset " + a2 + ")";
                    break;
                }
            }
            this.writer.print("    ");
            this.writer.println(v1);
        }
    }
    
    static class OffsetShifter extends Walker
    {
        int where;
        int gap;
        
        public OffsetShifter(final StackMapTable a1, final int a2, final int a3) {
            super(a1);
            this.where = a2;
            this.gap = a3;
        }
        
        @Override
        public void objectOrUninitialized(final int a1, final int a2, final int a3) {
            if (a1 == 8 && this.where <= a2) {
                ByteArray.write16bit(a2 + this.gap, this.info, a3);
            }
        }
    }
    
    static class Shifter extends Walker
    {
        private StackMapTable stackMap;
        int where;
        int gap;
        int position;
        byte[] updatedInfo;
        boolean exclusive;
        
        public Shifter(final StackMapTable a1, final int a2, final int a3, final boolean a4) {
            super(a1);
            this.stackMap = a1;
            this.where = a2;
            this.gap = a3;
            this.position = 0;
            this.updatedInfo = null;
            this.exclusive = a4;
        }
        
        public void doit() throws BadBytecode {
            this.parse();
            if (this.updatedInfo != null) {
                this.stackMap.set(this.updatedInfo);
            }
        }
        
        @Override
        public void sameFrame(final int a1, final int a2) {
            this.update(a1, a2, 0, 251);
        }
        
        @Override
        public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
            this.update(a1, a2, 64, 247);
        }
        
        void update(final int v1, final int v2, final int v3, final int v4) {
            final int v5 = this.position;
            this.position = v5 + v2 + ((v5 != 0) ? 1 : 0);
            boolean v6 = false;
            if (this.exclusive) {
                final boolean a1 = v5 < this.where && this.where <= this.position;
            }
            else {
                v6 = (v5 <= this.where && this.where < this.position);
            }
            if (v6) {
                final int a2 = v2 + this.gap;
                this.position += this.gap;
                if (a2 < 64) {
                    this.info[v1] = (byte)(a2 + v3);
                }
                else if (v2 < 64) {
                    final byte[] a3 = insertGap(this.info, v1, 2);
                    a3[v1] = (byte)v4;
                    ByteArray.write16bit(a2, a3, v1 + 1);
                    this.updatedInfo = a3;
                }
                else {
                    ByteArray.write16bit(a2, this.info, v1 + 1);
                }
            }
        }
        
        static byte[] insertGap(final byte[] a2, final int a3, final int v1) {
            final int v2 = a2.length;
            final byte[] v3 = new byte[v2 + v1];
            for (int a4 = 0; a4 < v2; ++a4) {
                v3[a4 + ((a4 < a3) ? 0 : v1)] = a2[a4];
            }
            return v3;
        }
        
        @Override
        public void chopFrame(final int a1, final int a2, final int a3) {
            this.update(a1, a2);
        }
        
        @Override
        public void appendFrame(final int a1, final int a2, final int[] a3, final int[] a4) {
            this.update(a1, a2);
        }
        
        @Override
        public void fullFrame(final int a1, final int a2, final int[] a3, final int[] a4, final int[] a5, final int[] a6) {
            this.update(a1, a2);
        }
        
        void update(final int v2, final int v3) {
            final int v4 = this.position;
            this.position = v4 + v3 + ((v4 != 0) ? 1 : 0);
            boolean v5 = false;
            if (this.exclusive) {
                final boolean a1 = v4 < this.where && this.where <= this.position;
            }
            else {
                v5 = (v4 <= this.where && this.where < this.position);
            }
            if (v5) {
                final int a2 = v3 + this.gap;
                ByteArray.write16bit(a2, this.info, v2 + 1);
                this.position += this.gap;
            }
        }
    }
    
    static class SwitchShifter extends Shifter
    {
        SwitchShifter(final StackMapTable a1, final int a2, final int a3) {
            super(a1, a2, a3, false);
        }
        
        @Override
        void update(final int a4, final int v1, final int v2, final int v3) {
            final int v4 = this.position;
            this.position = v4 + v1 + ((v4 != 0) ? 1 : 0);
            int v5 = v1;
            if (this.where == this.position) {
                v5 = v1 - this.gap;
            }
            else {
                if (this.where != v4) {
                    return;
                }
                v5 = v1 + this.gap;
            }
            if (v1 < 64) {
                if (v5 < 64) {
                    this.info[a4] = (byte)(v5 + v2);
                }
                else {
                    final byte[] a5 = Shifter.insertGap(this.info, a4, 2);
                    a5[a4] = (byte)v3;
                    ByteArray.write16bit(v5, a5, a4 + 1);
                    this.updatedInfo = a5;
                }
            }
            else if (v5 < 64) {
                final byte[] a6 = deleteGap(this.info, a4, 2);
                a6[a4] = (byte)(v5 + v2);
                this.updatedInfo = a6;
            }
            else {
                ByteArray.write16bit(v5, this.info, a4 + 1);
            }
        }
        
        static byte[] deleteGap(final byte[] a2, int a3, final int v1) {
            a3 += v1;
            final int v2 = a2.length;
            final byte[] v3 = new byte[v2 - v1];
            for (int a4 = 0; a4 < v2; ++a4) {
                v3[a4 - ((a4 < a3) ? 0 : v1)] = a2[a4];
            }
            return v3;
        }
        
        @Override
        void update(final int a1, final int a2) {
            final int v1 = this.position;
            this.position = v1 + a2 + ((v1 != 0) ? 1 : 0);
            int v2 = a2;
            if (this.where == this.position) {
                v2 = a2 - this.gap;
            }
            else {
                if (this.where != v1) {
                    return;
                }
                v2 = a2 + this.gap;
            }
            ByteArray.write16bit(v2, this.info, a1 + 1);
        }
    }
    
    static class NewRemover extends SimpleCopy
    {
        int posOfNew;
        
        public NewRemover(final byte[] a1, final int a2) {
            super(a1);
            this.posOfNew = a2;
        }
        
        @Override
        public void sameLocals(final int a1, final int a2, final int a3, final int a4) {
            if (a3 == 8 && a4 == this.posOfNew) {
                super.sameFrame(a1, a2);
            }
            else {
                super.sameLocals(a1, a2, a3, a4);
            }
        }
        
        @Override
        public void fullFrame(final int v1, final int v2, final int[] v3, final int[] v4, int[] v5, int[] v6) {
            for (int v7 = v5.length - 1, a5 = 0; a5 < v7; ++a5) {
                if (v5[a5] == 8 && v6[a5] == this.posOfNew && v5[a5 + 1] == 8 && v6[a5 + 1] == this.posOfNew) {
                    final int[] a6 = new int[++v7 - 2];
                    final int[] a7 = new int[v7 - 2];
                    int a8 = 0;
                    for (int a9 = 0; a9 < v7; ++a9) {
                        if (a9 == a5) {
                            ++a9;
                        }
                        else {
                            a6[a8] = v5[a9];
                            a7[a8++] = v6[a9];
                        }
                    }
                    v5 = a6;
                    v6 = a7;
                    break;
                }
            }
            super.fullFrame(v1, v2, v3, v4, v5, v6);
        }
    }
}
