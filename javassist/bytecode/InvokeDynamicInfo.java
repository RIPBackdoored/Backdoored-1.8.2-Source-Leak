package javassist.bytecode;

import java.util.*;
import java.io.*;

class InvokeDynamicInfo extends ConstInfo
{
    static final int tag = 18;
    int bootstrap;
    int nameAndType;
    
    public InvokeDynamicInfo(final int a1, final int a2, final int a3) {
        super(a3);
        this.bootstrap = a1;
        this.nameAndType = a2;
    }
    
    public InvokeDynamicInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.bootstrap = a1.readUnsignedShort();
        this.nameAndType = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.bootstrap << 16 ^ this.nameAndType;
    }
    
    @Override
    public boolean equals(final Object v2) {
        if (v2 instanceof InvokeDynamicInfo) {
            final InvokeDynamicInfo a1 = (InvokeDynamicInfo)v2;
            return a1.bootstrap == this.bootstrap && a1.nameAndType == this.nameAndType;
        }
        return false;
    }
    
    @Override
    public int getTag() {
        return 18;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        return a2.addInvokeDynamicInfo(this.bootstrap, a1.getItem(this.nameAndType).copy(a1, a2, a3));
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(18);
        a1.writeShort(this.bootstrap);
        a1.writeShort(this.nameAndType);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("InvokeDynamic #");
        a1.print(this.bootstrap);
        a1.print(", name&type #");
        a1.println(this.nameAndType);
    }
}
