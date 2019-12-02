package javassist.bytecode;

import java.util.*;
import java.io.*;

class LongInfo extends ConstInfo
{
    static final int tag = 5;
    long value;
    
    public LongInfo(final long a1, final int a2) {
        super(a2);
        this.value = a1;
    }
    
    public LongInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.value = a1.readLong();
    }
    
    @Override
    public int hashCode() {
        return (int)(this.value ^ this.value >>> 32);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof LongInfo && ((LongInfo)a1).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 5;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addLongInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(5);
        a1.writeLong(this.value);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("Long ");
        a1.println(this.value);
    }
}
