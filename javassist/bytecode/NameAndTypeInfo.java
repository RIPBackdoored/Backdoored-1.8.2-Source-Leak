package javassist.bytecode;

import java.util.*;
import java.io.*;

class NameAndTypeInfo extends ConstInfo
{
    static final int tag = 12;
    int memberName;
    int typeDescriptor;
    
    public NameAndTypeInfo(final int a1, final int a2, final int a3) {
        super(a3);
        this.memberName = a1;
        this.typeDescriptor = a2;
    }
    
    public NameAndTypeInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.memberName = a1.readUnsignedShort();
        this.typeDescriptor = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.memberName << 16 ^ this.typeDescriptor;
    }
    
    @Override
    public boolean equals(final Object v2) {
        if (v2 instanceof NameAndTypeInfo) {
            final NameAndTypeInfo a1 = (NameAndTypeInfo)v2;
            return a1.memberName == this.memberName && a1.typeDescriptor == this.typeDescriptor;
        }
        return false;
    }
    
    @Override
    public int getTag() {
        return 12;
    }
    
    @Override
    public void renameClass(final ConstPool a1, final String a2, final String a3, final HashMap a4) {
        final String v1 = a1.getUtf8Info(this.typeDescriptor);
        final String v2 = Descriptor.rename(v1, a2, a3);
        if (v1 != v2) {
            if (a4 == null) {
                this.typeDescriptor = a1.addUtf8Info(v2);
            }
            else {
                a4.remove(this);
                this.typeDescriptor = a1.addUtf8Info(v2);
                a4.put(this, this);
            }
        }
    }
    
    @Override
    public void renameClass(final ConstPool a1, final Map a2, final HashMap a3) {
        final String v1 = a1.getUtf8Info(this.typeDescriptor);
        final String v2 = Descriptor.rename(v1, a2);
        if (v1 != v2) {
            if (a3 == null) {
                this.typeDescriptor = a1.addUtf8Info(v2);
            }
            else {
                a3.remove(this);
                this.typeDescriptor = a1.addUtf8Info(v2);
                a3.put(this, this);
            }
        }
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        final String v1 = a1.getUtf8Info(this.memberName);
        String v2 = a1.getUtf8Info(this.typeDescriptor);
        v2 = Descriptor.rename(v2, a3);
        return a2.addNameAndTypeInfo(a2.addUtf8Info(v1), a2.addUtf8Info(v2));
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(12);
        a1.writeShort(this.memberName);
        a1.writeShort(this.typeDescriptor);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("NameAndType #");
        a1.print(this.memberName);
        a1.print(", type #");
        a1.println(this.typeDescriptor);
    }
}
