package javassist.bytecode;

import java.io.*;

public class ClassFileWriter
{
    private ByteStream output;
    private ConstPoolWriter constPool;
    private FieldWriter fields;
    private MethodWriter methods;
    int thisClass;
    int superClass;
    
    public ClassFileWriter(final int a1, final int a2) {
        super();
        (this.output = new ByteStream(512)).writeInt(-889275714);
        this.output.writeShort(a2);
        this.output.writeShort(a1);
        this.constPool = new ConstPoolWriter(this.output);
        this.fields = new FieldWriter(this.constPool);
        this.methods = new MethodWriter(this.constPool);
    }
    
    public ConstPoolWriter getConstPool() {
        return this.constPool;
    }
    
    public FieldWriter getFieldWriter() {
        return this.fields;
    }
    
    public MethodWriter getMethodWriter() {
        return this.methods;
    }
    
    public byte[] end(final int a4, final int a5, final int v1, final int[] v2, final AttributeWriter v3) {
        this.constPool.end();
        this.output.writeShort(a4);
        this.output.writeShort(a5);
        this.output.writeShort(v1);
        if (v2 == null) {
            this.output.writeShort(0);
        }
        else {
            final int a6 = v2.length;
            this.output.writeShort(a6);
            for (int a7 = 0; a7 < a6; ++a7) {
                this.output.writeShort(v2[a7]);
            }
        }
        this.output.enlarge(this.fields.dataSize() + this.methods.dataSize() + 6);
        try {
            this.output.writeShort(this.fields.size());
            this.fields.write(this.output);
            this.output.writeShort(this.methods.numOfMethods());
            this.methods.write(this.output);
        }
        catch (IOException ex) {}
        writeAttribute(this.output, v3, 0);
        return this.output.toByteArray();
    }
    
    public void end(final DataOutputStream a4, final int a5, final int a6, final int v1, final int[] v2, final AttributeWriter v3) throws IOException {
        this.constPool.end();
        this.output.writeTo(a4);
        a4.writeShort(a5);
        a4.writeShort(a6);
        a4.writeShort(v1);
        if (v2 == null) {
            a4.writeShort(0);
        }
        else {
            final int a7 = v2.length;
            a4.writeShort(a7);
            for (int a8 = 0; a8 < a7; ++a8) {
                a4.writeShort(v2[a8]);
            }
        }
        a4.writeShort(this.fields.size());
        this.fields.write(a4);
        a4.writeShort(this.methods.numOfMethods());
        this.methods.write(a4);
        if (v3 == null) {
            a4.writeShort(0);
        }
        else {
            a4.writeShort(v3.size());
            v3.write(a4);
        }
    }
    
    static void writeAttribute(final ByteStream a1, final AttributeWriter a2, final int a3) {
        if (a2 == null) {
            a1.writeShort(a3);
            return;
        }
        a1.writeShort(a2.size() + a3);
        final DataOutputStream v1 = new DataOutputStream(a1);
        try {
            a2.write(v1);
            v1.flush();
        }
        catch (IOException ex) {}
    }
    
    public static final class FieldWriter
    {
        protected ByteStream output;
        protected ConstPoolWriter constPool;
        private int fieldCount;
        
        FieldWriter(final ConstPoolWriter a1) {
            super();
            this.output = new ByteStream(128);
            this.constPool = a1;
            this.fieldCount = 0;
        }
        
        public void add(final int a1, final String a2, final String a3, final AttributeWriter a4) {
            final int v1 = this.constPool.addUtf8Info(a2);
            final int v2 = this.constPool.addUtf8Info(a3);
            this.add(a1, v1, v2, a4);
        }
        
        public void add(final int a1, final int a2, final int a3, final AttributeWriter a4) {
            ++this.fieldCount;
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            this.output.writeShort(a3);
            ClassFileWriter.writeAttribute(this.output, a4, 0);
        }
        
        int size() {
            return this.fieldCount;
        }
        
        int dataSize() {
            return this.output.size();
        }
        
        void write(final OutputStream a1) throws IOException {
            this.output.writeTo(a1);
        }
    }
    
    public static final class MethodWriter
    {
        protected ByteStream output;
        protected ConstPoolWriter constPool;
        private int methodCount;
        protected int codeIndex;
        protected int throwsIndex;
        protected int stackIndex;
        private int startPos;
        private boolean isAbstract;
        private int catchPos;
        private int catchCount;
        
        MethodWriter(final ConstPoolWriter a1) {
            super();
            this.output = new ByteStream(256);
            this.constPool = a1;
            this.methodCount = 0;
            this.codeIndex = 0;
            this.throwsIndex = 0;
            this.stackIndex = 0;
        }
        
        public void begin(final int a3, final String a4, final String a5, final String[] v1, final AttributeWriter v2) {
            final int v3 = this.constPool.addUtf8Info(a4);
            final int v4 = this.constPool.addUtf8Info(a5);
            int[] v5 = null;
            if (v1 == null) {
                final int[] a6 = null;
            }
            else {
                v5 = this.constPool.addClassInfo(v1);
            }
            this.begin(a3, v3, v4, v5, v2);
        }
        
        public void begin(final int a1, final int a2, final int a3, final int[] a4, final AttributeWriter a5) {
            ++this.methodCount;
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            this.output.writeShort(a3);
            this.isAbstract = ((a1 & 0x400) != 0x0);
            int v1 = this.isAbstract ? 0 : 1;
            if (a4 != null) {
                ++v1;
            }
            ClassFileWriter.writeAttribute(this.output, a5, v1);
            if (a4 != null) {
                this.writeThrows(a4);
            }
            if (!this.isAbstract) {
                if (this.codeIndex == 0) {
                    this.codeIndex = this.constPool.addUtf8Info("Code");
                }
                this.startPos = this.output.getPos();
                this.output.writeShort(this.codeIndex);
                this.output.writeBlank(12);
            }
            this.catchPos = -1;
            this.catchCount = 0;
        }
        
        private void writeThrows(final int[] v2) {
            if (this.throwsIndex == 0) {
                this.throwsIndex = this.constPool.addUtf8Info("Exceptions");
            }
            this.output.writeShort(this.throwsIndex);
            this.output.writeInt(v2.length * 2 + 2);
            this.output.writeShort(v2.length);
            for (int a1 = 0; a1 < v2.length; ++a1) {
                this.output.writeShort(v2[a1]);
            }
        }
        
        public void add(final int a1) {
            this.output.write(a1);
        }
        
        public void add16(final int a1) {
            this.output.writeShort(a1);
        }
        
        public void add32(final int a1) {
            this.output.writeInt(a1);
        }
        
        public void addInvoke(final int a1, final String a2, final String a3, final String a4) {
            final int v1 = this.constPool.addClassInfo(a2);
            final int v2 = this.constPool.addNameAndTypeInfo(a3, a4);
            final int v3 = this.constPool.addMethodrefInfo(v1, v2);
            this.add(a1);
            this.add16(v3);
        }
        
        public void codeEnd(final int a1, final int a2) {
            if (!this.isAbstract) {
                this.output.writeShort(this.startPos + 6, a1);
                this.output.writeShort(this.startPos + 8, a2);
                this.output.writeInt(this.startPos + 10, this.output.getPos() - this.startPos - 14);
                this.catchPos = this.output.getPos();
                this.catchCount = 0;
                this.output.writeShort(0);
            }
        }
        
        public void addCatch(final int a1, final int a2, final int a3, final int a4) {
            ++this.catchCount;
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            this.output.writeShort(a3);
            this.output.writeShort(a4);
        }
        
        public void end(final StackMapTable.Writer v1, final AttributeWriter v2) {
            if (this.isAbstract) {
                return;
            }
            this.output.writeShort(this.catchPos, this.catchCount);
            final int v3 = (v1 != null) ? 1 : 0;
            ClassFileWriter.writeAttribute(this.output, v2, v3);
            if (v1 != null) {
                if (this.stackIndex == 0) {
                    this.stackIndex = this.constPool.addUtf8Info("StackMapTable");
                }
                this.output.writeShort(this.stackIndex);
                final byte[] a1 = v1.toByteArray();
                this.output.writeInt(a1.length);
                this.output.write(a1);
            }
            this.output.writeInt(this.startPos + 2, this.output.getPos() - this.startPos - 6);
        }
        
        public int size() {
            return this.output.getPos() - this.startPos - 14;
        }
        
        int numOfMethods() {
            return this.methodCount;
        }
        
        int dataSize() {
            return this.output.size();
        }
        
        void write(final OutputStream a1) throws IOException {
            this.output.writeTo(a1);
        }
    }
    
    public static final class ConstPoolWriter
    {
        ByteStream output;
        protected int startPos;
        protected int num;
        
        ConstPoolWriter(final ByteStream a1) {
            super();
            this.output = a1;
            this.startPos = a1.getPos();
            this.num = 1;
            this.output.writeShort(1);
        }
        
        public int[] addClassInfo(final String[] v2) {
            final int v3 = v2.length;
            final int[] v4 = new int[v3];
            for (int a1 = 0; a1 < v3; ++a1) {
                v4[a1] = this.addClassInfo(v2[a1]);
            }
            return v4;
        }
        
        public int addClassInfo(final String a1) {
            final int v1 = this.addUtf8Info(a1);
            this.output.write(7);
            this.output.writeShort(v1);
            return this.num++;
        }
        
        public int addClassInfo(final int a1) {
            this.output.write(7);
            this.output.writeShort(a1);
            return this.num++;
        }
        
        public int addNameAndTypeInfo(final String a1, final String a2) {
            return this.addNameAndTypeInfo(this.addUtf8Info(a1), this.addUtf8Info(a2));
        }
        
        public int addNameAndTypeInfo(final int a1, final int a2) {
            this.output.write(12);
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            return this.num++;
        }
        
        public int addFieldrefInfo(final int a1, final int a2) {
            this.output.write(9);
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            return this.num++;
        }
        
        public int addMethodrefInfo(final int a1, final int a2) {
            this.output.write(10);
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            return this.num++;
        }
        
        public int addInterfaceMethodrefInfo(final int a1, final int a2) {
            this.output.write(11);
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            return this.num++;
        }
        
        public int addMethodHandleInfo(final int a1, final int a2) {
            this.output.write(15);
            this.output.write(a1);
            this.output.writeShort(a2);
            return this.num++;
        }
        
        public int addMethodTypeInfo(final int a1) {
            this.output.write(16);
            this.output.writeShort(a1);
            return this.num++;
        }
        
        public int addInvokeDynamicInfo(final int a1, final int a2) {
            this.output.write(18);
            this.output.writeShort(a1);
            this.output.writeShort(a2);
            return this.num++;
        }
        
        public int addStringInfo(final String a1) {
            final int v1 = this.addUtf8Info(a1);
            this.output.write(8);
            this.output.writeShort(v1);
            return this.num++;
        }
        
        public int addIntegerInfo(final int a1) {
            this.output.write(3);
            this.output.writeInt(a1);
            return this.num++;
        }
        
        public int addFloatInfo(final float a1) {
            this.output.write(4);
            this.output.writeFloat(a1);
            return this.num++;
        }
        
        public int addLongInfo(final long a1) {
            this.output.write(5);
            this.output.writeLong(a1);
            final int v1 = this.num;
            this.num += 2;
            return v1;
        }
        
        public int addDoubleInfo(final double a1) {
            this.output.write(6);
            this.output.writeDouble(a1);
            final int v1 = this.num;
            this.num += 2;
            return v1;
        }
        
        public int addUtf8Info(final String a1) {
            this.output.write(1);
            this.output.writeUTF(a1);
            return this.num++;
        }
        
        void end() {
            this.output.writeShort(this.startPos, this.num);
        }
    }
    
    public interface AttributeWriter
    {
        int size();
        
        void write(final DataOutputStream p0) throws IOException;
    }
}
