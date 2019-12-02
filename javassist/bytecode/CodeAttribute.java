package javassist.bytecode;

import java.util.*;
import java.io.*;

public class CodeAttribute extends AttributeInfo implements Opcode
{
    public static final String tag = "Code";
    private int maxStack;
    private int maxLocals;
    private ExceptionTable exceptions;
    private ArrayList attributes;
    
    public CodeAttribute(final ConstPool a1, final int a2, final int a3, final byte[] a4, final ExceptionTable a5) {
        super(a1, "Code");
        this.maxStack = a2;
        this.maxLocals = a3;
        this.info = a4;
        this.exceptions = a5;
        this.attributes = new ArrayList();
    }
    
    private CodeAttribute(final ConstPool v1, final CodeAttribute v2, final Map v3) throws BadBytecode {
        super(v1, "Code");
        this.maxStack = v2.getMaxStack();
        this.maxLocals = v2.getMaxLocals();
        this.exceptions = v2.getExceptionTable().copy(v1, v3);
        this.attributes = new ArrayList();
        final List v4 = v2.getAttributes();
        for (int v5 = v4.size(), a2 = 0; a2 < v5; ++a2) {
            final AttributeInfo a3 = v4.get(a2);
            this.attributes.add(a3.copy(v1, v3));
        }
        this.info = v2.copyCode(v1, v3, this.exceptions, this);
    }
    
    CodeAttribute(final ConstPool a3, final int v1, final DataInputStream v2) throws IOException {
        super(a3, v1, (byte[])null);
        final int v3 = v2.readInt();
        this.maxStack = v2.readUnsignedShort();
        this.maxLocals = v2.readUnsignedShort();
        final int v4 = v2.readInt();
        v2.readFully(this.info = new byte[v4]);
        this.exceptions = new ExceptionTable(a3, v2);
        this.attributes = new ArrayList();
        for (int v5 = v2.readUnsignedShort(), a4 = 0; a4 < v5; ++a4) {
            this.attributes.add(AttributeInfo.read(a3, v2));
        }
    }
    
    @Override
    public AttributeInfo copy(final ConstPool v1, final Map v2) throws RuntimeCopyException {
        try {
            return new CodeAttribute(v1, this, v2);
        }
        catch (BadBytecode a1) {
            throw new RuntimeCopyException("bad bytecode. fatal?");
        }
    }
    
    @Override
    public int length() {
        return 18 + this.info.length + this.exceptions.size() * 8 + AttributeInfo.getLength(this.attributes);
    }
    
    @Override
    void write(final DataOutputStream a1) throws IOException {
        a1.writeShort(this.name);
        a1.writeInt(this.length() - 6);
        a1.writeShort(this.maxStack);
        a1.writeShort(this.maxLocals);
        a1.writeInt(this.info.length);
        a1.write(this.info);
        this.exceptions.write(a1);
        a1.writeShort(this.attributes.size());
        AttributeInfo.writeAll(this.attributes, a1);
    }
    
    @Override
    public byte[] get() {
        throw new UnsupportedOperationException("CodeAttribute.get()");
    }
    
    @Override
    public void set(final byte[] a1) {
        throw new UnsupportedOperationException("CodeAttribute.set()");
    }
    
    @Override
    void renameClass(final String a1, final String a2) {
        AttributeInfo.renameClass(this.attributes, a1, a2);
    }
    
    @Override
    void renameClass(final Map a1) {
        AttributeInfo.renameClass(this.attributes, a1);
    }
    
    @Override
    void getRefClasses(final Map a1) {
        AttributeInfo.getRefClasses(this.attributes, a1);
    }
    
    public String getDeclaringClass() {
        final ConstPool v1 = this.getConstPool();
        return v1.getClassName();
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    public void setMaxStack(final int a1) {
        this.maxStack = a1;
    }
    
    public int computeMaxStack() throws BadBytecode {
        return this.maxStack = new CodeAnalyzer(this).computeMaxStack();
    }
    
    public int getMaxLocals() {
        return this.maxLocals;
    }
    
    public void setMaxLocals(final int a1) {
        this.maxLocals = a1;
    }
    
    public int getCodeLength() {
        return this.info.length;
    }
    
    public byte[] getCode() {
        return this.info;
    }
    
    void setCode(final byte[] a1) {
        super.set(a1);
    }
    
    public CodeIterator iterator() {
        return new CodeIterator(this);
    }
    
    public ExceptionTable getExceptionTable() {
        return this.exceptions;
    }
    
    public List getAttributes() {
        return this.attributes;
    }
    
    public AttributeInfo getAttribute(final String a1) {
        return AttributeInfo.lookup(this.attributes, a1);
    }
    
    public void setAttribute(final StackMapTable a1) {
        AttributeInfo.remove(this.attributes, "StackMapTable");
        if (a1 != null) {
            this.attributes.add(a1);
        }
    }
    
    public void setAttribute(final StackMap a1) {
        AttributeInfo.remove(this.attributes, "StackMap");
        if (a1 != null) {
            this.attributes.add(a1);
        }
    }
    
    private byte[] copyCode(final ConstPool a1, final Map a2, final ExceptionTable a3, final CodeAttribute a4) throws BadBytecode {
        final int v1 = this.getCodeLength();
        final byte[] v2 = new byte[v1];
        a4.info = v2;
        final LdcEntry v3 = copyCode(this.info, 0, v1, this.getConstPool(), v2, a1, a2);
        return LdcEntry.doit(v2, v3, a3, a4);
    }
    
    private static LdcEntry copyCode(final byte[] a6, final int a7, final int v1, final ConstPool v2, final byte[] v3, final ConstPool v4, final Map v5) throws BadBytecode {
        LdcEntry v6 = null;
        int a9;
        for (int a8 = a7; a8 < v1; a8 = a9) {
            a9 = CodeIterator.nextOpcode(a6, a8);
            final byte a10 = a6[a8];
            switch ((v3[a8] = a10) & 0xFF) {
                case 19:
                case 20:
                case 178:
                case 179:
                case 180:
                case 181:
                case 182:
                case 183:
                case 184:
                case 187:
                case 189:
                case 192:
                case 193: {
                    copyConstPoolInfo(a8 + 1, a6, v2, v3, v4, v5);
                    break;
                }
                case 18: {
                    int a11 = a6[a8 + 1] & 0xFF;
                    a11 = v2.copy(a11, v4, v5);
                    if (a11 < 256) {
                        v3[a8 + 1] = (byte)a11;
                        break;
                    }
                    v3[a8 + 1] = (v3[a8] = 0);
                    final LdcEntry a12 = new LdcEntry();
                    a12.where = a8;
                    a12.index = a11;
                    a12.next = v6;
                    v6 = a12;
                    break;
                }
                case 185: {
                    copyConstPoolInfo(a8 + 1, a6, v2, v3, v4, v5);
                    v3[a8 + 3] = a6[a8 + 3];
                    v3[a8 + 4] = a6[a8 + 4];
                    break;
                }
                case 186: {
                    copyConstPoolInfo(a8 + 1, a6, v2, v3, v4, v5);
                    v3[a8 + 4] = (v3[a8 + 3] = 0);
                    break;
                }
                case 197: {
                    copyConstPoolInfo(a8 + 1, a6, v2, v3, v4, v5);
                    v3[a8 + 3] = a6[a8 + 3];
                    break;
                }
                default: {
                    while (++a8 < a9) {
                        v3[a8] = a6[a8];
                    }
                    break;
                }
            }
        }
        return v6;
    }
    
    private static void copyConstPoolInfo(final int a1, final byte[] a2, final ConstPool a3, final byte[] a4, final ConstPool a5, final Map a6) {
        int v1 = (a2[a1] & 0xFF) << 8 | (a2[a1 + 1] & 0xFF);
        v1 = a3.copy(v1, a5, a6);
        a4[a1] = (byte)(v1 >> 8);
        a4[a1 + 1] = (byte)v1;
    }
    
    public void insertLocalVar(final int a1, final int a2) throws BadBytecode {
        final CodeIterator v1 = this.iterator();
        while (v1.hasNext()) {
            shiftIndex(v1, a1, a2);
        }
        this.setMaxLocals(this.getMaxLocals() + a2);
    }
    
    private static void shiftIndex(final CodeIterator v-4, final int v-3, final int v-2) throws BadBytecode {
        final int next = v-4.next();
        final int v0 = v-4.byteAt(next);
        if (v0 < 21) {
            return;
        }
        if (v0 < 79) {
            if (v0 < 26) {
                shiftIndex8(v-4, next, v0, v-3, v-2);
            }
            else if (v0 < 46) {
                shiftIndex0(v-4, next, v0, v-3, v-2, 26, 21);
            }
            else {
                if (v0 < 54) {
                    return;
                }
                if (v0 < 59) {
                    shiftIndex8(v-4, next, v0, v-3, v-2);
                }
                else {
                    shiftIndex0(v-4, next, v0, v-3, v-2, 59, 54);
                }
            }
        }
        else if (v0 == 132) {
            int a3 = v-4.byteAt(next + 1);
            if (a3 < v-3) {
                return;
            }
            a3 += v-2;
            if (a3 < 256) {
                v-4.writeByte(a3, next + 1);
            }
            else {
                final int a4 = (byte)v-4.byteAt(next + 2);
                final int a5 = v-4.insertExGap(3);
                v-4.writeByte(196, a5 - 3);
                v-4.writeByte(132, a5 - 2);
                v-4.write16bit(a3, a5 - 1);
                v-4.write16bit(a4, a5 + 1);
            }
        }
        else if (v0 == 169) {
            shiftIndex8(v-4, next, v0, v-3, v-2);
        }
        else if (v0 == 196) {
            int v2 = v-4.u16bitAt(next + 2);
            if (v2 < v-3) {
                return;
            }
            v2 += v-2;
            v-4.write16bit(v2, next + 2);
        }
    }
    
    private static void shiftIndex8(final CodeIterator a2, final int a3, final int a4, final int a5, final int v1) throws BadBytecode {
        int v2 = a2.byteAt(a3 + 1);
        if (v2 < a5) {
            return;
        }
        v2 += v1;
        if (v2 < 256) {
            a2.writeByte(v2, a3 + 1);
        }
        else {
            final int a6 = a2.insertExGap(2);
            a2.writeByte(196, a6 - 2);
            a2.writeByte(a4, a6 - 1);
            a2.write16bit(v2, a6);
        }
    }
    
    private static void shiftIndex0(final CodeIterator a3, final int a4, int a5, final int a6, final int a7, final int v1, final int v2) throws BadBytecode {
        int v3 = (a5 - v1) % 4;
        if (v3 < a6) {
            return;
        }
        v3 += a7;
        if (v3 < 4) {
            a3.writeByte(a5 + a7, a4);
        }
        else {
            a5 = (a5 - v1) / 4 + v2;
            if (v3 < 256) {
                final int a8 = a3.insertExGap(1);
                a3.writeByte(a5, a8 - 1);
                a3.writeByte(v3, a8);
            }
            else {
                final int a9 = a3.insertExGap(3);
                a3.writeByte(196, a9 - 1);
                a3.writeByte(a5, a9);
                a3.write16bit(v3, a9 + 1);
            }
        }
    }
    
    public static class RuntimeCopyException extends RuntimeException
    {
        public RuntimeCopyException(final String a1) {
            super(a1);
        }
    }
    
    static class LdcEntry
    {
        LdcEntry next;
        int where;
        int index;
        
        LdcEntry() {
            super();
        }
        
        static byte[] doit(byte[] a1, final LdcEntry a2, final ExceptionTable a3, final CodeAttribute a4) throws BadBytecode {
            if (a2 != null) {
                a1 = CodeIterator.changeLdcToLdcW(a1, a3, a4, a2);
            }
            return a1;
        }
    }
}
