package javassist.bytecode;

import java.util.*;
import java.io.*;

class DoubleInfo extends ConstInfo
{
    static final int tag = 6;
    double value;
    
    public DoubleInfo(final double a1, final int a2) {
        super(a2);
        this.value = a1;
    }
    
    public DoubleInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.value = a1.readDouble();
    }
    
    @Override
    public int hashCode() {
        final long v1 = Double.doubleToLongBits(this.value);
        return (int)(v1 ^ v1 >>> 32);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof DoubleInfo && ((DoubleInfo)a1).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 6;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addDoubleInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(6);
        a1.writeDouble(this.value);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("Double ");
        a1.println(this.value);
    }
}
