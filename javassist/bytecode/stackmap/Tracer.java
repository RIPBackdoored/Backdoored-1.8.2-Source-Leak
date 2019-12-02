package javassist.bytecode.stackmap;

import javassist.*;
import javassist.bytecode.*;

public abstract class Tracer implements TypeTag
{
    protected ClassPool classPool;
    protected ConstPool cpool;
    protected String returnType;
    protected int stackTop;
    protected TypeData[] stackTypes;
    protected TypeData[] localsTypes;
    
    public Tracer(final ClassPool a1, final ConstPool a2, final int a3, final int a4, final String a5) {
        super();
        this.classPool = a1;
        this.cpool = a2;
        this.returnType = a5;
        this.stackTop = 0;
        this.stackTypes = TypeData.make(a3);
        this.localsTypes = TypeData.make(a4);
    }
    
    public Tracer(final Tracer a1) {
        super();
        this.classPool = a1.classPool;
        this.cpool = a1.cpool;
        this.returnType = a1.returnType;
        this.stackTop = a1.stackTop;
        this.stackTypes = TypeData.make(a1.stackTypes.length);
        this.localsTypes = TypeData.make(a1.localsTypes.length);
    }
    
    protected int doOpcode(final int v2, final byte[] v3) throws BadBytecode {
        try {
            final int a1 = v3[v2] & 0xFF;
            if (a1 < 96) {
                if (a1 < 54) {
                    return this.doOpcode0_53(v2, v3, a1);
                }
                return this.doOpcode54_95(v2, v3, a1);
            }
            else {
                if (a1 < 148) {
                    return this.doOpcode96_147(v2, v3, a1);
                }
                return this.doOpcode148_201(v2, v3, a1);
            }
        }
        catch (ArrayIndexOutOfBoundsException a2) {
            throw new BadBytecode("inconsistent stack height " + a2.getMessage(), a2);
        }
    }
    
    protected void visitBranch(final int a1, final byte[] a2, final int a3) throws BadBytecode {
    }
    
    protected void visitGoto(final int a1, final byte[] a2, final int a3) throws BadBytecode {
    }
    
    protected void visitReturn(final int a1, final byte[] a2) throws BadBytecode {
    }
    
    protected void visitThrow(final int a1, final byte[] a2) throws BadBytecode {
    }
    
    protected void visitTableSwitch(final int a1, final byte[] a2, final int a3, final int a4, final int a5) throws BadBytecode {
    }
    
    protected void visitLookupSwitch(final int a1, final byte[] a2, final int a3, final int a4, final int a5) throws BadBytecode {
    }
    
    protected void visitJSR(final int a1, final byte[] a2) throws BadBytecode {
    }
    
    protected void visitRET(final int a1, final byte[] a2) throws BadBytecode {
    }
    
    private int doOpcode0_53(final int v2, final byte[] v3, final int v4) throws BadBytecode {
        final TypeData[] v5 = this.stackTypes;
        switch (v4) {
            case 0: {
                break;
            }
            case 1: {
                v5[this.stackTop++] = new TypeData.NullType();
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                v5[this.stackTop++] = Tracer.INTEGER;
                break;
            }
            case 9:
            case 10: {
                v5[this.stackTop++] = Tracer.LONG;
                v5[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 11:
            case 12:
            case 13: {
                v5[this.stackTop++] = Tracer.FLOAT;
                break;
            }
            case 14:
            case 15: {
                v5[this.stackTop++] = Tracer.DOUBLE;
                v5[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 16:
            case 17: {
                v5[this.stackTop++] = Tracer.INTEGER;
                return (v4 == 17) ? 3 : 2;
            }
            case 18: {
                this.doLDC(v3[v2 + 1] & 0xFF);
                return 2;
            }
            case 19:
            case 20: {
                this.doLDC(ByteArray.readU16bit(v3, v2 + 1));
                return 3;
            }
            case 21: {
                return this.doXLOAD(Tracer.INTEGER, v3, v2);
            }
            case 22: {
                return this.doXLOAD(Tracer.LONG, v3, v2);
            }
            case 23: {
                return this.doXLOAD(Tracer.FLOAT, v3, v2);
            }
            case 24: {
                return this.doXLOAD(Tracer.DOUBLE, v3, v2);
            }
            case 25: {
                return this.doALOAD(v3[v2 + 1] & 0xFF);
            }
            case 26:
            case 27:
            case 28:
            case 29: {
                v5[this.stackTop++] = Tracer.INTEGER;
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33: {
                v5[this.stackTop++] = Tracer.LONG;
                v5[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 34:
            case 35:
            case 36:
            case 37: {
                v5[this.stackTop++] = Tracer.FLOAT;
                break;
            }
            case 38:
            case 39:
            case 40:
            case 41: {
                v5[this.stackTop++] = Tracer.DOUBLE;
                v5[this.stackTop++] = Tracer.TOP;
                break;
            }
            case 42:
            case 43:
            case 44:
            case 45: {
                final int a1 = v4 - 42;
                v5[this.stackTop++] = this.localsTypes[a1];
                break;
            }
            case 46: {
                final TypeData[] array = v5;
                final int stackTop = this.stackTop - 1;
                this.stackTop = stackTop;
                array[stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 47: {
                v5[this.stackTop - 2] = Tracer.LONG;
                v5[this.stackTop - 1] = Tracer.TOP;
                break;
            }
            case 48: {
                final TypeData[] array2 = v5;
                final int stackTop2 = this.stackTop - 1;
                this.stackTop = stackTop2;
                array2[stackTop2 - 1] = Tracer.FLOAT;
                break;
            }
            case 49: {
                v5[this.stackTop - 2] = Tracer.DOUBLE;
                v5[this.stackTop - 1] = Tracer.TOP;
                break;
            }
            case 50: {
                final int stackTop3 = this.stackTop - 1;
                this.stackTop = stackTop3;
                final int a2 = stackTop3 - 1;
                final TypeData a3 = v5[a2];
                v5[a2] = TypeData.ArrayElement.make(a3);
                break;
            }
            case 51:
            case 52:
            case 53: {
                final TypeData[] array3 = v5;
                final int stackTop4 = this.stackTop - 1;
                this.stackTop = stackTop4;
                array3[stackTop4 - 1] = Tracer.INTEGER;
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return 1;
    }
    
    private void doLDC(final int a1) {
        final TypeData[] v1 = this.stackTypes;
        final int v2 = this.cpool.getTag(a1);
        if (v2 == 8) {
            v1[this.stackTop++] = new TypeData.ClassName("java.lang.String");
        }
        else if (v2 == 3) {
            v1[this.stackTop++] = Tracer.INTEGER;
        }
        else if (v2 == 4) {
            v1[this.stackTop++] = Tracer.FLOAT;
        }
        else if (v2 == 5) {
            v1[this.stackTop++] = Tracer.LONG;
            v1[this.stackTop++] = Tracer.TOP;
        }
        else if (v2 == 6) {
            v1[this.stackTop++] = Tracer.DOUBLE;
            v1[this.stackTop++] = Tracer.TOP;
        }
        else {
            if (v2 != 7) {
                throw new RuntimeException("bad LDC: " + v2);
            }
            v1[this.stackTop++] = new TypeData.ClassName("java.lang.Class");
        }
    }
    
    private int doXLOAD(final TypeData a1, final byte[] a2, final int a3) {
        final int v1 = a2[a3 + 1] & 0xFF;
        return this.doXLOAD(v1, a1);
    }
    
    private int doXLOAD(final int a1, final TypeData a2) {
        this.stackTypes[this.stackTop++] = a2;
        if (a2.is2WordType()) {
            this.stackTypes[this.stackTop++] = Tracer.TOP;
        }
        return 2;
    }
    
    private int doALOAD(final int a1) {
        this.stackTypes[this.stackTop++] = this.localsTypes[a1];
        return 2;
    }
    
    private int doOpcode54_95(final int v-2, final byte[] v-1, final int v0) throws BadBytecode {
        switch (v0) {
            case 54: {
                return this.doXSTORE(v-2, v-1, Tracer.INTEGER);
            }
            case 55: {
                return this.doXSTORE(v-2, v-1, Tracer.LONG);
            }
            case 56: {
                return this.doXSTORE(v-2, v-1, Tracer.FLOAT);
            }
            case 57: {
                return this.doXSTORE(v-2, v-1, Tracer.DOUBLE);
            }
            case 58: {
                return this.doASTORE(v-1[v-2 + 1] & 0xFF);
            }
            case 59:
            case 60:
            case 61:
            case 62: {
                final int a1 = v0 - 59;
                this.localsTypes[a1] = Tracer.INTEGER;
                --this.stackTop;
                break;
            }
            case 63:
            case 64:
            case 65:
            case 66: {
                final int a2 = v0 - 63;
                this.localsTypes[a2] = Tracer.LONG;
                this.localsTypes[a2 + 1] = Tracer.TOP;
                this.stackTop -= 2;
                break;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
                final int a3 = v0 - 67;
                this.localsTypes[a3] = Tracer.FLOAT;
                --this.stackTop;
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74: {
                final int v = v0 - 71;
                this.localsTypes[v] = Tracer.DOUBLE;
                this.localsTypes[v + 1] = Tracer.TOP;
                this.stackTop -= 2;
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78: {
                final int v = v0 - 75;
                this.doASTORE(v);
                break;
            }
            case 79:
            case 80:
            case 81:
            case 82: {
                this.stackTop -= ((v0 == 80 || v0 == 82) ? 4 : 3);
                break;
            }
            case 83: {
                TypeData.aastore(this.stackTypes[this.stackTop - 3], this.stackTypes[this.stackTop - 1], this.classPool);
                this.stackTop -= 3;
                break;
            }
            case 84:
            case 85:
            case 86: {
                this.stackTop -= 3;
                break;
            }
            case 87: {
                --this.stackTop;
                break;
            }
            case 88: {
                this.stackTop -= 2;
                break;
            }
            case 89: {
                final int v = this.stackTop;
                this.stackTypes[v] = this.stackTypes[v - 1];
                this.stackTop = v + 1;
                break;
            }
            case 90:
            case 91: {
                final int v = v0 - 90 + 2;
                this.doDUP_XX(1, v);
                final int v2 = this.stackTop;
                this.stackTypes[v2 - v] = this.stackTypes[v2];
                this.stackTop = v2 + 1;
                break;
            }
            case 92: {
                this.doDUP_XX(2, 2);
                this.stackTop += 2;
                break;
            }
            case 93:
            case 94: {
                final int v = v0 - 93 + 3;
                this.doDUP_XX(2, v);
                final int v2 = this.stackTop;
                this.stackTypes[v2 - v] = this.stackTypes[v2];
                this.stackTypes[v2 - v + 1] = this.stackTypes[v2 + 1];
                this.stackTop = v2 + 2;
                break;
            }
            case 95: {
                final int v = this.stackTop - 1;
                final TypeData v3 = this.stackTypes[v];
                this.stackTypes[v] = this.stackTypes[v - 1];
                this.stackTypes[v - 1] = v3;
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return 1;
    }
    
    private int doXSTORE(final int a1, final byte[] a2, final TypeData a3) {
        final int v1 = a2[a1 + 1] & 0xFF;
        return this.doXSTORE(v1, a3);
    }
    
    private int doXSTORE(final int a1, final TypeData a2) {
        --this.stackTop;
        this.localsTypes[a1] = a2;
        if (a2.is2WordType()) {
            --this.stackTop;
            this.localsTypes[a1 + 1] = Tracer.TOP;
        }
        return 2;
    }
    
    private int doASTORE(final int a1) {
        --this.stackTop;
        this.localsTypes[a1] = this.stackTypes[this.stackTop];
        return 2;
    }
    
    private void doDUP_XX(final int a1, final int a2) {
        final TypeData[] v1 = this.stackTypes;
        for (int v2 = this.stackTop - 1, v3 = v2 - a2; v2 > v3; --v2) {
            v1[v2 + a1] = v1[v2];
        }
    }
    
    private int doOpcode96_147(final int a1, final byte[] a2, final int a3) {
        if (a3 <= 131) {
            this.stackTop += Opcode.STACK_GROW[a3];
            return 1;
        }
        switch (a3) {
            case 132: {
                return 3;
            }
            case 133: {
                this.stackTypes[this.stackTop - 1] = Tracer.LONG;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 134: {
                this.stackTypes[this.stackTop - 1] = Tracer.FLOAT;
                break;
            }
            case 135: {
                this.stackTypes[this.stackTop - 1] = Tracer.DOUBLE;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 136: {
                final TypeData[] stackTypes = this.stackTypes;
                final int stackTop = this.stackTop - 1;
                this.stackTop = stackTop;
                stackTypes[stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 137: {
                final TypeData[] stackTypes2 = this.stackTypes;
                final int stackTop2 = this.stackTop - 1;
                this.stackTop = stackTop2;
                stackTypes2[stackTop2 - 1] = Tracer.FLOAT;
                break;
            }
            case 138: {
                this.stackTypes[this.stackTop - 2] = Tracer.DOUBLE;
                break;
            }
            case 139: {
                this.stackTypes[this.stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 140: {
                this.stackTypes[this.stackTop - 1] = Tracer.LONG;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 141: {
                this.stackTypes[this.stackTop - 1] = Tracer.DOUBLE;
                this.stackTypes[this.stackTop] = Tracer.TOP;
                ++this.stackTop;
                break;
            }
            case 142: {
                final TypeData[] stackTypes3 = this.stackTypes;
                final int stackTop3 = this.stackTop - 1;
                this.stackTop = stackTop3;
                stackTypes3[stackTop3 - 1] = Tracer.INTEGER;
                break;
            }
            case 143: {
                this.stackTypes[this.stackTop - 2] = Tracer.LONG;
                break;
            }
            case 144: {
                final TypeData[] stackTypes4 = this.stackTypes;
                final int stackTop4 = this.stackTop - 1;
                this.stackTop = stackTop4;
                stackTypes4[stackTop4 - 1] = Tracer.FLOAT;
                break;
            }
            case 145:
            case 146:
            case 147: {
                break;
            }
            default: {
                throw new RuntimeException("fatal");
            }
        }
        return 1;
    }
    
    private int doOpcode148_201(final int v-5, final byte[] v-4, final int v-3) throws BadBytecode {
        switch (v-3) {
            case 148: {
                this.stackTypes[this.stackTop - 4] = Tracer.INTEGER;
                this.stackTop -= 3;
                break;
            }
            case 149:
            case 150: {
                final TypeData[] stackTypes = this.stackTypes;
                final int stackTop = this.stackTop - 1;
                this.stackTop = stackTop;
                stackTypes[stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 151:
            case 152: {
                this.stackTypes[this.stackTop - 4] = Tracer.INTEGER;
                this.stackTop -= 3;
                break;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158: {
                --this.stackTop;
                this.visitBranch(v-5, v-4, ByteArray.readS16bit(v-4, v-5 + 1));
                return 3;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166: {
                this.stackTop -= 2;
                this.visitBranch(v-5, v-4, ByteArray.readS16bit(v-4, v-5 + 1));
                return 3;
            }
            case 167: {
                this.visitGoto(v-5, v-4, ByteArray.readS16bit(v-4, v-5 + 1));
                return 3;
            }
            case 168: {
                this.visitJSR(v-5, v-4);
                return 3;
            }
            case 169: {
                this.visitRET(v-5, v-4);
                return 2;
            }
            case 170: {
                --this.stackTop;
                final int a1 = (v-5 & 0xFFFFFFFC) + 8;
                final int a2 = ByteArray.read32bit(v-4, a1);
                final int a3 = ByteArray.read32bit(v-4, a1 + 4);
                final int v1 = a3 - a2 + 1;
                this.visitTableSwitch(v-5, v-4, v1, a1 + 8, ByteArray.read32bit(v-4, a1 - 4));
                return v1 * 4 + 16 - (v-5 & 0x3);
            }
            case 171: {
                --this.stackTop;
                final int n = (v-5 & 0xFFFFFFFC) + 8;
                final int read32bit = ByteArray.read32bit(v-4, n);
                this.visitLookupSwitch(v-5, v-4, read32bit, n + 4, ByteArray.read32bit(v-4, n - 4));
                return read32bit * 8 + 12 - (v-5 & 0x3);
            }
            case 172: {
                --this.stackTop;
                this.visitReturn(v-5, v-4);
                break;
            }
            case 173: {
                this.stackTop -= 2;
                this.visitReturn(v-5, v-4);
                break;
            }
            case 174: {
                --this.stackTop;
                this.visitReturn(v-5, v-4);
                break;
            }
            case 175: {
                this.stackTop -= 2;
                this.visitReturn(v-5, v-4);
                break;
            }
            case 176: {
                final TypeData[] stackTypes2 = this.stackTypes;
                final int stackTop2 = this.stackTop - 1;
                this.stackTop = stackTop2;
                stackTypes2[stackTop2].setType(this.returnType, this.classPool);
                this.visitReturn(v-5, v-4);
                break;
            }
            case 177: {
                this.visitReturn(v-5, v-4);
                break;
            }
            case 178: {
                return this.doGetField(v-5, v-4, false);
            }
            case 179: {
                return this.doPutField(v-5, v-4, false);
            }
            case 180: {
                return this.doGetField(v-5, v-4, true);
            }
            case 181: {
                return this.doPutField(v-5, v-4, true);
            }
            case 182:
            case 183: {
                return this.doInvokeMethod(v-5, v-4, true);
            }
            case 184: {
                return this.doInvokeMethod(v-5, v-4, false);
            }
            case 185: {
                return this.doInvokeIntfMethod(v-5, v-4);
            }
            case 186: {
                return this.doInvokeDynamic(v-5, v-4);
            }
            case 187: {
                final int n = ByteArray.readU16bit(v-4, v-5 + 1);
                this.stackTypes[this.stackTop++] = new TypeData.UninitData(v-5, this.cpool.getClassInfo(n));
                return 3;
            }
            case 188: {
                return this.doNEWARRAY(v-5, v-4);
            }
            case 189: {
                final int n = ByteArray.readU16bit(v-4, v-5 + 1);
                String s = this.cpool.getClassInfo(n).replace('.', '/');
                if (s.charAt(0) == '[') {
                    s = "[" + s;
                }
                else {
                    s = "[L" + s + ";";
                }
                this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(s);
                return 3;
            }
            case 190: {
                this.stackTypes[this.stackTop - 1].setType("[Ljava.lang.Object;", this.classPool);
                this.stackTypes[this.stackTop - 1] = Tracer.INTEGER;
                break;
            }
            case 191: {
                final TypeData[] stackTypes3 = this.stackTypes;
                final int stackTop3 = this.stackTop - 1;
                this.stackTop = stackTop3;
                stackTypes3[stackTop3].setType("java.lang.Throwable", this.classPool);
                this.visitThrow(v-5, v-4);
                break;
            }
            case 192: {
                final int n = ByteArray.readU16bit(v-4, v-5 + 1);
                String s = this.cpool.getClassInfo(n);
                if (s.charAt(0) == '[') {
                    s = s.replace('.', '/');
                }
                this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(s);
                return 3;
            }
            case 193: {
                this.stackTypes[this.stackTop - 1] = Tracer.INTEGER;
                return 3;
            }
            case 194:
            case 195: {
                --this.stackTop;
                break;
            }
            case 196: {
                return this.doWIDE(v-5, v-4);
            }
            case 197: {
                return this.doMultiANewArray(v-5, v-4);
            }
            case 198:
            case 199: {
                --this.stackTop;
                this.visitBranch(v-5, v-4, ByteArray.readS16bit(v-4, v-5 + 1));
                return 3;
            }
            case 200: {
                this.visitGoto(v-5, v-4, ByteArray.read32bit(v-4, v-5 + 1));
                return 5;
            }
            case 201: {
                this.visitJSR(v-5, v-4);
                return 5;
            }
        }
        return 1;
    }
    
    private int doWIDE(final int v2, final byte[] v3) throws BadBytecode {
        final int v4 = v3[v2 + 1] & 0xFF;
        switch (v4) {
            case 21: {
                this.doWIDE_XLOAD(v2, v3, Tracer.INTEGER);
                break;
            }
            case 22: {
                this.doWIDE_XLOAD(v2, v3, Tracer.LONG);
                break;
            }
            case 23: {
                this.doWIDE_XLOAD(v2, v3, Tracer.FLOAT);
                break;
            }
            case 24: {
                this.doWIDE_XLOAD(v2, v3, Tracer.DOUBLE);
                break;
            }
            case 25: {
                final int a1 = ByteArray.readU16bit(v3, v2 + 2);
                this.doALOAD(a1);
                break;
            }
            case 54: {
                this.doWIDE_STORE(v2, v3, Tracer.INTEGER);
                break;
            }
            case 55: {
                this.doWIDE_STORE(v2, v3, Tracer.LONG);
                break;
            }
            case 56: {
                this.doWIDE_STORE(v2, v3, Tracer.FLOAT);
                break;
            }
            case 57: {
                this.doWIDE_STORE(v2, v3, Tracer.DOUBLE);
                break;
            }
            case 58: {
                final int a2 = ByteArray.readU16bit(v3, v2 + 2);
                this.doASTORE(a2);
                break;
            }
            case 132: {
                return 6;
            }
            case 169: {
                this.visitRET(v2, v3);
                break;
            }
            default: {
                throw new RuntimeException("bad WIDE instruction: " + v4);
            }
        }
        return 4;
    }
    
    private void doWIDE_XLOAD(final int a1, final byte[] a2, final TypeData a3) {
        final int v1 = ByteArray.readU16bit(a2, a1 + 2);
        this.doXLOAD(v1, a3);
    }
    
    private void doWIDE_STORE(final int a1, final byte[] a2, final TypeData a3) {
        final int v1 = ByteArray.readU16bit(a2, a1 + 2);
        this.doXSTORE(v1, a3);
    }
    
    private int doPutField(final int a1, final byte[] a2, final boolean a3) throws BadBytecode {
        final int v1 = ByteArray.readU16bit(a2, a1 + 1);
        final String v2 = this.cpool.getFieldrefType(v1);
        this.stackTop -= Descriptor.dataSize(v2);
        final char v3 = v2.charAt(0);
        if (v3 == 'L') {
            this.stackTypes[this.stackTop].setType(getFieldClassName(v2, 0), this.classPool);
        }
        else if (v3 == '[') {
            this.stackTypes[this.stackTop].setType(v2, this.classPool);
        }
        this.setFieldTarget(a3, v1);
        return 3;
    }
    
    private int doGetField(final int a1, final byte[] a2, final boolean a3) throws BadBytecode {
        final int v1 = ByteArray.readU16bit(a2, a1 + 1);
        this.setFieldTarget(a3, v1);
        final String v2 = this.cpool.getFieldrefType(v1);
        this.pushMemberType(v2);
        return 3;
    }
    
    private void setFieldTarget(final boolean v1, final int v2) throws BadBytecode {
        if (v1) {
            final String a1 = this.cpool.getFieldrefClassName(v2);
            final TypeData[] stackTypes = this.stackTypes;
            final int stackTop = this.stackTop - 1;
            this.stackTop = stackTop;
            stackTypes[stackTop].setType(a1, this.classPool);
        }
    }
    
    private int doNEWARRAY(final int v-2, final byte[] v-1) {
        final int v0 = this.stackTop - 1;
        String v2 = null;
        switch (v-1[v-2 + 1] & 0xFF) {
            case 4: {
                final String a1 = "[Z";
                break;
            }
            case 5: {
                final String a2 = "[C";
                break;
            }
            case 6: {
                v2 = "[F";
                break;
            }
            case 7: {
                v2 = "[D";
                break;
            }
            case 8: {
                v2 = "[B";
                break;
            }
            case 9: {
                v2 = "[S";
                break;
            }
            case 10: {
                v2 = "[I";
                break;
            }
            case 11: {
                v2 = "[J";
                break;
            }
            default: {
                throw new RuntimeException("bad newarray");
            }
        }
        this.stackTypes[v0] = new TypeData.ClassName(v2);
        return 2;
    }
    
    private int doMultiANewArray(final int a1, final byte[] a2) {
        final int v1 = ByteArray.readU16bit(a2, a1 + 1);
        final int v2 = a2[a1 + 3] & 0xFF;
        this.stackTop -= v2 - 1;
        final String v3 = this.cpool.getClassInfo(v1).replace('.', '/');
        this.stackTypes[this.stackTop - 1] = new TypeData.ClassName(v3);
        return 4;
    }
    
    private int doInvokeMethod(final int v1, final byte[] v2, final boolean v3) throws BadBytecode {
        final int v4 = ByteArray.readU16bit(v2, v1 + 1);
        final String v5 = this.cpool.getMethodrefType(v4);
        this.checkParamTypes(v5, 1);
        if (v3) {
            final String a1 = this.cpool.getMethodrefClassName(v4);
            final TypeData[] stackTypes = this.stackTypes;
            final int stackTop = this.stackTop - 1;
            this.stackTop = stackTop;
            final TypeData a2 = stackTypes[stackTop];
            if (a2 instanceof TypeData.UninitTypeVar && a2.isUninit()) {
                this.constructorCalled(a2, ((TypeData.UninitTypeVar)a2).offset());
            }
            else if (a2 instanceof TypeData.UninitData) {
                this.constructorCalled(a2, ((TypeData.UninitData)a2).offset());
            }
            a2.setType(a1, this.classPool);
        }
        this.pushMemberType(v5);
        return 3;
    }
    
    private void constructorCalled(final TypeData v2, final int v3) {
        v2.constructorCalled(v3);
        for (int a1 = 0; a1 < this.stackTop; ++a1) {
            this.stackTypes[a1].constructorCalled(v3);
        }
        for (int a2 = 0; a2 < this.localsTypes.length; ++a2) {
            this.localsTypes[a2].constructorCalled(v3);
        }
    }
    
    private int doInvokeIntfMethod(final int a1, final byte[] a2) throws BadBytecode {
        final int v1 = ByteArray.readU16bit(a2, a1 + 1);
        final String v2 = this.cpool.getInterfaceMethodrefType(v1);
        this.checkParamTypes(v2, 1);
        final String v3 = this.cpool.getInterfaceMethodrefClassName(v1);
        final TypeData[] stackTypes = this.stackTypes;
        final int stackTop = this.stackTop - 1;
        this.stackTop = stackTop;
        stackTypes[stackTop].setType(v3, this.classPool);
        this.pushMemberType(v2);
        return 5;
    }
    
    private int doInvokeDynamic(final int a1, final byte[] a2) throws BadBytecode {
        final int v1 = ByteArray.readU16bit(a2, a1 + 1);
        final String v2 = this.cpool.getInvokeDynamicType(v1);
        this.checkParamTypes(v2, 1);
        this.pushMemberType(v2);
        return 5;
    }
    
    private void pushMemberType(final String a1) {
        int v1 = 0;
        if (a1.charAt(0) == '(') {
            v1 = a1.indexOf(41) + 1;
            if (v1 < 1) {
                throw new IndexOutOfBoundsException("bad descriptor: " + a1);
            }
        }
        final TypeData[] v2 = this.stackTypes;
        final int v3 = this.stackTop;
        switch (a1.charAt(v1)) {
            case '[': {
                v2[v3] = new TypeData.ClassName(a1.substring(v1));
                break;
            }
            case 'L': {
                v2[v3] = new TypeData.ClassName(getFieldClassName(a1, v1));
                break;
            }
            case 'J': {
                v2[v3] = Tracer.LONG;
                v2[v3 + 1] = Tracer.TOP;
                this.stackTop += 2;
                return;
            }
            case 'F': {
                v2[v3] = Tracer.FLOAT;
                break;
            }
            case 'D': {
                v2[v3] = Tracer.DOUBLE;
                v2[v3 + 1] = Tracer.TOP;
                this.stackTop += 2;
                return;
            }
            case 'V': {
                return;
            }
            default: {
                v2[v3] = Tracer.INTEGER;
                break;
            }
        }
        ++this.stackTop;
    }
    
    private static String getFieldClassName(final String a1, final int a2) {
        return a1.substring(a2 + 1, a1.length() - 1).replace('/', '.');
    }
    
    private void checkParamTypes(final String a1, final int a2) throws BadBytecode {
        char v1 = a1.charAt(a2);
        if (v1 == ')') {
            return;
        }
        int v2 = a2;
        boolean v3 = false;
        while (v1 == '[') {
            v3 = true;
            v1 = a1.charAt(++v2);
        }
        if (v1 == 'L') {
            v2 = a1.indexOf(59, v2) + 1;
            if (v2 <= 0) {
                throw new IndexOutOfBoundsException("bad descriptor");
            }
        }
        else {
            ++v2;
        }
        this.checkParamTypes(a1, v2);
        if (!v3 && (v1 == 'J' || v1 == 'D')) {
            this.stackTop -= 2;
        }
        else {
            --this.stackTop;
        }
        if (v3) {
            this.stackTypes[this.stackTop].setType(a1.substring(a2, v2), this.classPool);
        }
        else if (v1 == 'L') {
            this.stackTypes[this.stackTop].setType(a1.substring(a2 + 1, v2 - 1).replace('/', '.'), this.classPool);
        }
    }
}
