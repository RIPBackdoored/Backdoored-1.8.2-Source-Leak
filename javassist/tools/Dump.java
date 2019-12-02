package javassist.tools;

import java.io.*;
import javassist.bytecode.*;

public class Dump
{
    private Dump() {
        super();
    }
    
    public static void main(final String[] a1) throws Exception {
        if (a1.length != 1) {
            System.err.println("Usage: java Dump <class file name>");
            return;
        }
        final DataInputStream v1 = new DataInputStream(new FileInputStream(a1[0]));
        final ClassFile v2 = new ClassFile(v1);
        final PrintWriter v3 = new PrintWriter(System.out, true);
        v3.println("*** constant pool ***");
        v2.getConstPool().print(v3);
        v3.println();
        v3.println("*** members ***");
        ClassFilePrinter.print(v2, v3);
    }
}
