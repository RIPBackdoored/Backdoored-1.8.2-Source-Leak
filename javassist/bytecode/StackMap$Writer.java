package javassist.bytecode;

import java.io.*;

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
