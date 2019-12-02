package javassist.bytecode;

import java.util.*;
import java.io.*;

class Utf8Info extends ConstInfo
{
    static final int tag = 1;
    String string;
    
    public Utf8Info(final String a1, final int a2) {
        super(a2);
        this.string = a1;
    }
    
    public Utf8Info(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.string = a1.readUTF();
    }
    
    @Override
    public int hashCode() {
        return this.string.hashCode();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Utf8Info && ((Utf8Info)a1).string.equals(this.string);
    }
    
    @Override
    public int getTag() {
        return 1;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addUtf8Info(this.string);
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(1);
        a1.writeUTF(this.string);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("UTF8 \"");
        a1.print(this.string);
        a1.println("\"");
    }
}
