package javassist.bytecode;

import java.util.*;
import java.io.*;

class MethodHandleInfo extends ConstInfo
{
    static final int tag = 15;
    int refKind;
    int refIndex;
    
    public MethodHandleInfo(final int a1, final int a2, final int a3) {
        super(a3);
        this.refKind = a1;
        this.refIndex = a2;
    }
    
    public MethodHandleInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.refKind = a1.readUnsignedByte();
        this.refIndex = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.refKind << 16 ^ this.refIndex;
    }
    
    @Override
    public boolean equals(final Object v2) {
        if (v2 instanceof MethodHandleInfo) {
            final MethodHandleInfo a1 = (MethodHandleInfo)v2;
            return a1.refKind == this.refKind && a1.refIndex == this.refIndex;
        }
        return false;
    }
    
    @Override
    public int getTag() {
        return 15;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addMethodHandleInfo(this.refKind, a1.getItem(this.refIndex).copy(a1, a2, a3));
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(15);
        a1.writeByte(this.refKind);
        a1.writeShort(this.refIndex);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("MethodHandle #");
        a1.print(this.refKind);
        a1.print(", index #");
        a1.println(this.refIndex);
    }
}
