package javassist.bytecode;

import java.io.*;

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
