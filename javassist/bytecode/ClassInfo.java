package javassist.bytecode;

import java.util.*;
import java.io.*;

class ClassInfo extends ConstInfo
{
    static final int tag = 7;
    int name;
    
    public ClassInfo(final int a1, final int a2) {
        super(a2);
        this.name = a1;
    }
    
    public ClassInfo(final DataInputStream a1, final int a2) throws IOException {
        super(a2);
        this.name = a1.readUnsignedShort();
    }
    
    @Override
    public int hashCode() {
        return this.name;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof ClassInfo && ((ClassInfo)a1).name == this.name;
    }
    
    @Override
    public int getTag() {
        return 7;
    }
    
    @Override
    public String getClassName(final ConstPool a1) {
        return a1.getUtf8Info(this.name);
    }
    
    @Override
    public void renameClass(final ConstPool a3, final String a4, final String v1, final HashMap v2) {
        final String v3 = a3.getUtf8Info(this.name);
        String v4 = null;
        if (v3.equals(a4)) {
            v4 = v1;
        }
        else if (v3.charAt(0) == '[') {
            final String a5 = Descriptor.rename(v3, a4, v1);
            if (v3 != a5) {
                v4 = a5;
            }
        }
        if (v4 != null) {
            if (v2 == null) {
                this.name = a3.addUtf8Info(v4);
            }
            else {
                v2.remove(this);
                this.name = a3.addUtf8Info(v4);
                v2.put(this, this);
            }
        }
    }
    
    @Override
    public void renameClass(final ConstPool v1, final Map v2, final HashMap v3) {
        final String v4 = v1.getUtf8Info(this.name);
        String v5 = null;
        if (v4.charAt(0) == '[') {
            final String a1 = Descriptor.rename(v4, v2);
            if (v4 != a1) {
                v5 = a1;
            }
        }
        else {
            final String a2 = v2.get(v4);
            if (a2 != null && !a2.equals(v4)) {
                v5 = a2;
            }
        }
        if (v5 != null) {
            if (v3 == null) {
                this.name = v1.addUtf8Info(v5);
            }
            else {
                v3.remove(this);
                this.name = v1.addUtf8Info(v5);
                v3.put(this, this);
            }
        }
    }
    
    @Override
    public int copy(final ConstPool a3, final ConstPool v1, final Map v2) {
        String v3 = a3.getUtf8Info(this.name);
        if (v2 != null) {
            final String a4 = v2.get(v3);
            if (a4 != null) {
                v3 = a4;
            }
        }
        return v1.addClassInfo(v3);
    }
    
    @Override
    public void write(final DataOutputStream a1) throws IOException {
        a1.writeByte(7);
        a1.writeShort(this.name);
    }
    
    @Override
    public void print(final PrintWriter a1) {
        a1.print("Class #");
        a1.println(this.name);
    }
}
