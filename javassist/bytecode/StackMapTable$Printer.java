package javassist.bytecode;

import java.io.*;

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
