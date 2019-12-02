package javassist.bytecode;

import java.util.*;
import java.io.*;

class MethodTypeInfo extends ConstInfo
{
    static final int tag = 16;
    int descriptor;
    
    public MethodTypeInfo(final int a1, final int a2) {
        super(a2);
        this.descriptor = a1;
    }
    
    public MethodTypeInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.descriptor = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.descriptor;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof MethodTypeInfo && ((MethodTypeInfo)a1).descriptor == this.descriptor;
    }
    
    @Override
    public int getTag() {
        return 16;
    }
    
    @Override
    public void renameClass(final ConstPool a1, final String a2, final String a3, final HashMap a4) {
        final String v1 = a1.getUtf8Info(this.descriptor);
        final String v2 = Descriptor.rename(v1, a2, a3);
        if (v1 != v2) {
            if (a4 == null) {
                this.descriptor = a1.addUtf8Info(v2);
            }
            else {
                a4.remove(this);
                this.descriptor = a1.addUtf8Info(v2);
                a4.put(this, this);
            }
        }
    }
    
    @Override
    public void renameClass(final ConstPool a1, final Map a2, final HashMap a3) {
        final String v1 = a1.getUtf8Info(this.descriptor);
        final String v2 = Descriptor.rename(v1, a2);
        if (v1 != v2) {
            if (a3 == null) {
                this.descriptor = a1.addUtf8Info(v2);
            }
            else {
                a3.remove(this);
                this.descriptor = a1.addUtf8Info(v2);
                a3.put(this, this);
            }
        }
    }
    
    @Override
    public int copy(final ConstPool a1, final ConstPool a2, final Map a3) {
        String v1 = a1.getUtf8Info(this.descriptor);
        v1 = Descriptor.rename(v1, a3);
        return a2.addMethodTypeInfo(a2.addUtf8Info(v1));
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(16);
        a1.writeShort(this.descriptor);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("MethodType #");
        a1.println(this.descriptor);
    }
}
