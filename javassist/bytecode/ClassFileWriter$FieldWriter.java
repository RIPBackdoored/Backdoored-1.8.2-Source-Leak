package javassist.bytecode;

import java.io.*;

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
