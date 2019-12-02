package javassist.bytecode;

import java.util.*;
import java.io.*;

class IntegerInfo extends ConstInfo
{
    static final int tag = 3;
    int value;
    
    public IntegerInfo(final int a1, final int a2) {
        super(a2);
        this.value = a1;
    }
    
    public IntegerInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.value = a1.readInt();
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof IntegerInfo && ((IntegerInfo)a1).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 3;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addIntegerInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(3);
        a1.writeInt(this.value);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("Integer ");
        a1.println(this.value);
    }
}
