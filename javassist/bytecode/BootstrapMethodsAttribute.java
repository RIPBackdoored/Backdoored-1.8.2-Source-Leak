package javassist.bytecode;

import java.io.*;
import java.util.*;

public class BootstrapMethodsAttribute extends AttributeInfo
{
    public static final String tag = "BootstrapMethods";
    
    BootstrapMethodsAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public BootstrapMethodsAttribute(final ConstPool v-5, final BootstrapMethod[] v-4) {
        super(v-5, "BootstrapMethods");
        int n = 2;
        for (int a1 = 0; a1 < v-4.length; ++a1) {
            n += 4 + v-4[a1].arguments.length * 2;
        }
        final byte[] a3 = new byte[n];
        ByteArray.write16bit(v-4.length, a3, 0);
        int n2 = 2;
        for (int v0 = 0; v0 < v-4.length; ++v0) {
            ByteArray.write16bit(v-4[v0].methodRef, a3, n2);
            ByteArray.write16bit(v-4[v0].arguments.length, a3, n2 + 2);
            final int[] v2 = v-4[v0].arguments;
            n2 += 4;
            for (int a2 = 0; a2 < v2.length; ++a2) {
                ByteArray.write16bit(v2[a2], a3, n2);
                n2 += 2;
            }
        }
        this.set(a3);
    }
    
    public BootstrapMethod[] getMethods() {
        final byte[] value = this.get();
        final int u16bit = ByteArray.readU16bit(value, 0);
        final BootstrapMethod[] array = new BootstrapMethod[u16bit];
        int n = 2;
        for (int i = 0; i < u16bit; ++i) {
            final int u16bit2 = ByteArray.readU16bit(value, n);
            final int u16bit3 = ByteArray.readU16bit(value, n + 2);
            final int[] v0 = new int[u16bit3];
            n += 4;
            for (int v2 = 0; v2 < u16bit3; ++v2) {
                v0[v2] = ByteArray.readU16bit(value, n);
                n += 2;
            }
            array[i] = new BootstrapMethod(u16bit2, v0);
        }
        return array;
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v-3, final Map v-2) {
        final BootstrapMethod[] methods = this.getMethods();
        final ConstPool v0 = this.getConstPool();
        for (int v2 = 0; v2 < methods.length; ++v2) {
            final BootstrapMethod a2 = methods[v2];
            a2.methodRef = v0.copy(a2.methodRef, v-3, v-2);
            for (int a3 = 0; a3 < a2.arguments.length; ++a3) {
                a2.arguments[a3] = v0.copy(a2.arguments[a3], v-3, v-2);
            }
        }
        return new BootstrapMethodsAttribute(v-3, methods);
    }
    
    public static class BootstrapMethod
    {
        public int methodRef;
        public int[] arguments;
        
        public BootstrapMethod(final int a1, final int[] a2) {
            super();
            this.methodRef = a1;
            this.arguments = a2;
        }
    }
}
