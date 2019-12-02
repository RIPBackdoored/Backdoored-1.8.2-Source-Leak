package javassist.bytecode;

import java.io.*;

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
