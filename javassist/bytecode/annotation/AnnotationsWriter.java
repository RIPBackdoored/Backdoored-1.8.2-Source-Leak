package javassist.bytecode.annotation;

import java.io.*;
import javassist.bytecode.*;

public class AnnotationsWriter
{
    protected OutputStream output;
    private ConstPool pool;
    
    public AnnotationsWriter(final OutputStream a1, final ConstPool a2) {
        super();
        this.output = a1;
        this.pool = a2;
    }
    
    public ConstPool getConstPool() {
        return this.pool;
    }
    
    public void close() throws IOException {
        this.output.close();
    }
    
    public void numParameters(final int a1) throws IOException {
        this.output.write(a1);
    }
    
    public void numAnnotations(final int a1) throws IOException {
        this.write16bit(a1);
    }
    
    public void annotation(final String a1, final int a2) throws IOException {
        this.annotation(this.pool.addUtf8Info(a1), a2);
    }
    
    public void annotation(final int a1, final int a2) throws IOException {
        this.write16bit(a1);
        this.write16bit(a2);
    }
    
    public void memberValuePair(final String a1) throws IOException {
        this.memberValuePair(this.pool.addUtf8Info(a1));
    }
    
    public void memberValuePair(final int a1) throws IOException {
        this.write16bit(a1);
    }
    
    public void constValueIndex(final boolean a1) throws IOException {
        this.constValueIndex(90, this.pool.addIntegerInfo((int)(a1 ? 1 : 0)));
    }
    
    public void constValueIndex(final byte a1) throws IOException {
        this.constValueIndex(66, this.pool.addIntegerInfo(a1));
    }
    
    public void constValueIndex(final char a1) throws IOException {
        this.constValueIndex(67, this.pool.addIntegerInfo(a1));
    }
    
    public void constValueIndex(final short a1) throws IOException {
        this.constValueIndex(83, this.pool.addIntegerInfo(a1));
    }
    
    public void constValueIndex(final int a1) throws IOException {
        this.constValueIndex(73, this.pool.addIntegerInfo(a1));
    }
    
    public void constValueIndex(final long a1) throws IOException {
        this.constValueIndex(74, this.pool.addLongInfo(a1));
    }
    
    public void constValueIndex(final float a1) throws IOException {
        this.constValueIndex(70, this.pool.addFloatInfo(a1));
    }
    
    public void constValueIndex(final double a1) throws IOException {
        this.constValueIndex(68, this.pool.addDoubleInfo(a1));
    }
    
    public void constValueIndex(final String a1) throws IOException {
        this.constValueIndex(115, this.pool.addUtf8Info(a1));
    }
    
    public void constValueIndex(final int a1, final int a2) throws IOException {
        this.output.write(a1);
        this.write16bit(a2);
    }
    
    public void enumConstValue(final String a1, final String a2) throws IOException {
        this.enumConstValue(this.pool.addUtf8Info(a1), this.pool.addUtf8Info(a2));
    }
    
    public void enumConstValue(final int a1, final int a2) throws IOException {
        this.output.write(101);
        this.write16bit(a1);
        this.write16bit(a2);
    }
    
    public void classInfoIndex(final String a1) throws IOException {
        this.classInfoIndex(this.pool.addUtf8Info(a1));
    }
    
    public void classInfoIndex(final int a1) throws IOException {
        this.output.write(99);
        this.write16bit(a1);
    }
    
    public void annotationValue() throws IOException {
        this.output.write(64);
    }
    
    public void arrayValue(final int a1) throws IOException {
        this.output.write(91);
        this.write16bit(a1);
    }
    
    protected void write16bit(final int a1) throws IOException {
        final byte[] v1 = new byte[2];
        ByteArray.write16bit(a1, v1, 0);
        this.output.write(v1);
    }
}
