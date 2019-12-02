package javassist.bytecode;

import java.io.*;
import java.util.*;

public class ConstantAttribute extends AttributeInfo
{
    public static final String tag = "ConstantValue";
    
    ConstantAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public ConstantAttribute(final ConstPool a1, final int a2) {
        super(a1, "ConstantValue");
        final byte[] v1 = { (byte)(a2 >>> 8), (byte)a2 };
        this.set(v1);
    }
    
    public int getConstantValue() {
        return ByteArray.readU16bit(this.get(), 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool a1, final Map a2) {
        final int v1 = this.getConstPool().copy(this.getConstantValue(), a1, a2);
        return new ConstantAttribute(a1, v1);
    }
}
