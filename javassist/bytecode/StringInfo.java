package javassist.bytecode;

import java.util.*;
import java.io.*;

class StringInfo extends ConstInfo
{
    static final int tag = 8;
    int string;
    
    public StringInfo(final int a1, final int a2) {
        super(a2);
        this.string = a1;
    }
    
    public StringInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.string = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.string;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof StringInfo && ((StringInfo)a1).string == this.string;
    }
    
    @Override
    public int getTag() {
        return 8;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addStringInfo(a1.getUtf8Info(this.string));
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(8);
        a1.writeShort(this.string);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("String #");
        a1.println(this.string);
    }
}
