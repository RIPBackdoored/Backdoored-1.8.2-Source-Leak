package javassist.bytecode;

import java.util.*;
import java.io.*;

abstract class MemberrefInfo extends ConstInfo
{
    int classIndex;
    int nameAndTypeIndex;
    
    public MemberrefInfo(final int a1, final int a2, final int a3) {
        super(a3);
        this.classIndex = a1;
        this.nameAndTypeIndex = a2;
    }
    
    public MemberrefInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.classIndex = a1.readUnsignedShort();
        this.nameAndTypeIndex = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.classIndex << 16 ^ this.nameAndTypeIndex;
    }
    
    @Override
    public boolean equals(final Object v2) {
        if (v2 instanceof MemberrefInfo) {
            final MemberrefInfo a1 = (MemberrefInfo)v2;
            return a1.classIndex == this.classIndex && a1.nameAndTypeIndex == this.nameAndTypeIndex && a1.getClass() == this.getClass();
        }
        return false;
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        final int v1 = a1.getItem(this.classIndex).copy(a1, a2, a3);
        final int v2 = a1.getItem(this.nameAndTypeIndex).copy(a1, a2, a3);
        return this.copy2(a2, v1, v2);
    }
    
    protected abstract int copy2(final ConstPool p0, final int p1, final int p2);
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(this.getTag());
        a1.writeShort(this.classIndex);
        a1.writeShort(this.nameAndTypeIndex);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print(this.getTagName() + " #");
        a1.print(this.classIndex);
        a1.print(", name&type #");
        a1.println(this.nameAndTypeIndex);
    }
    
    public abstract String getTagName();
}
