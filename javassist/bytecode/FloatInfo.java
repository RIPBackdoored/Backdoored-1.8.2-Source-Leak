package javassist.bytecode;

import java.util.*;
import java.io.*;

class FloatInfo extends ConstInfo
{
    static final int tag = 4;
    float value;
    
    public FloatInfo(final float a1, final int a2) {
        super(a2);
        this.value = a1;
    }
    
    public FloatInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.value = a1.readFloat();
    }
    
    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.value);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof FloatInfo && ((FloatInfo)a1).value == this.value;
    }
    
    @Override
    public int getTag() {
        return 4;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addFloatInfo(this.value);
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(4);
        a1.writeFloat(this.value);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("Float ");
        a1.println(this.value);
    }
}
