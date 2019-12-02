package javassist.bytecode.analysis;

import javassist.bytecode.*;
import javassist.*;

public class Executor implements Opcode
{
    private final ConstPool constPool;
    private final ClassPool classPool;
    private final Type STRING_TYPE;
    private final Type CLASS_TYPE;
    private final Type THROWABLE_TYPE;
    private int lastPos;
    
    public Executor(final ClassPool v1, final ConstPool v2) {
        super();
        this.constPool = v2;
        this.classPool = v1;
        try {
            this.STRING_TYPE = this.getType("java.lang.String");
            this.CLASS_TYPE = this.getType("java.lang.Class");
            this.THROWABLE_TYPE = this.getType("java.lang.Throwable");
        }
        catch (Exception a1) {
            throw new RuntimeException(a1);
        }
    }
    
    public void execute(final MethodInfo v-6, final int v-5, final CodeIterator v-4, final Frame v-3, final Subroutine v-2) throws BadBytecode {
        this.lastPos = v-5;
        final int byte1 = v-4.byteAt(v-5);
        switch (byte1) {
            case 1: {
                v-3.push(Type.UNINIT);
                break;
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                v-3.push(Type.INTEGER);
                break;
            }
            case 9:
            case 10: {
                v-3.push(Type.LONG);
                v-3.push(Type.TOP);
                break;
            }
            case 11:
            case 12:
            case 13: {
                v-3.push(Type.FLOAT);
                break;
            }
            case 14:
            case 15: {
                v-3.push(Type.DOUBLE);
                v-3.push(Type.TOP);
                break;
            }
            case 16:
            case 17: {
                v-3.push(Type.INTEGER);
                break;
            }
            case 18: {
                this.evalLDC(v-4.byteAt(v-5 + 1), v-3);
                break;
            }
            case 19:
            case 20: {
                this.evalLDC(v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 21: {
                this.evalLoad(Type.INTEGER, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 22: {
                this.evalLoad(Type.LONG, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 23: {
                this.evalLoad(Type.FLOAT, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 24: {
                this.evalLoad(Type.DOUBLE, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 25: {
                this.evalLoad(Type.OBJECT, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 26:
            case 27:
            case 28:
            case 29: {
                this.evalLoad(Type.INTEGER, byte1 - 26, v-3, v-2);
                break;
            }
            case 30:
            case 31:
            case 32:
            case 33: {
                this.evalLoad(Type.LONG, byte1 - 30, v-3, v-2);
                break;
            }
            case 34:
            case 35:
            case 36:
            case 37: {
                this.evalLoad(Type.FLOAT, byte1 - 34, v-3, v-2);
                break;
            }
            case 38:
            case 39:
            case 40:
            case 41: {
                this.evalLoad(Type.DOUBLE, byte1 - 38, v-3, v-2);
                break;
            }
            case 42:
            case 43:
            case 44:
            case 45: {
                this.evalLoad(Type.OBJECT, byte1 - 42, v-3, v-2);
                break;
            }
            case 46: {
                this.evalArrayLoad(Type.INTEGER, v-3);
                break;
            }
            case 47: {
                this.evalArrayLoad(Type.LONG, v-3);
                break;
            }
            case 48: {
                this.evalArrayLoad(Type.FLOAT, v-3);
                break;
            }
            case 49: {
                this.evalArrayLoad(Type.DOUBLE, v-3);
                break;
            }
            case 50: {
                this.evalArrayLoad(Type.OBJECT, v-3);
                break;
            }
            case 51:
            case 52:
            case 53: {
                this.evalArrayLoad(Type.INTEGER, v-3);
                break;
            }
            case 54: {
                this.evalStore(Type.INTEGER, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 55: {
                this.evalStore(Type.LONG, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 56: {
                this.evalStore(Type.FLOAT, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 57: {
                this.evalStore(Type.DOUBLE, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 58: {
                this.evalStore(Type.OBJECT, v-4.byteAt(v-5 + 1), v-3, v-2);
                break;
            }
            case 59:
            case 60:
            case 61:
            case 62: {
                this.evalStore(Type.INTEGER, byte1 - 59, v-3, v-2);
                break;
            }
            case 63:
            case 64:
            case 65:
            case 66: {
                this.evalStore(Type.LONG, byte1 - 63, v-3, v-2);
                break;
            }
            case 67:
            case 68:
            case 69:
            case 70: {
                this.evalStore(Type.FLOAT, byte1 - 67, v-3, v-2);
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74: {
                this.evalStore(Type.DOUBLE, byte1 - 71, v-3, v-2);
                break;
            }
            case 75:
            case 76:
            case 77:
            case 78: {
                this.evalStore(Type.OBJECT, byte1 - 75, v-3, v-2);
                break;
            }
            case 79: {
                this.evalArrayStore(Type.INTEGER, v-3);
                break;
            }
            case 80: {
                this.evalArrayStore(Type.LONG, v-3);
                break;
            }
            case 81: {
                this.evalArrayStore(Type.FLOAT, v-3);
                break;
            }
            case 82: {
                this.evalArrayStore(Type.DOUBLE, v-3);
                break;
            }
            case 83: {
                this.evalArrayStore(Type.OBJECT, v-3);
                break;
            }
            case 84:
            case 85:
            case 86: {
                this.evalArrayStore(Type.INTEGER, v-3);
                break;
            }
            case 87: {
                if (v-3.pop() == Type.TOP) {
                    throw new BadBytecode("POP can not be used with a category 2 value, pos = " + v-5);
                }
                break;
            }
            case 88: {
                v-3.pop();
                v-3.pop();
                break;
            }
            case 89: {
                final Type a1 = v-3.peek();
                if (a1 == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + v-5);
                }
                v-3.push(v-3.peek());
                break;
            }
            case 90:
            case 91: {
                final Type a2 = v-3.peek();
                if (a2 == Type.TOP) {
                    throw new BadBytecode("DUP can not be used with a category 2 value, pos = " + v-5);
                }
                int a3 = v-3.getTopIndex();
                final int a4 = a3 - (byte1 - 90) - 1;
                v-3.push(a2);
                while (a3 > a4) {
                    v-3.setStack(a3, v-3.getStack(a3 - 1));
                    --a3;
                }
                v-3.setStack(a4, a2);
                break;
            }
            case 92: {
                v-3.push(v-3.getStack(v-3.getTopIndex() - 1));
                v-3.push(v-3.getStack(v-3.getTopIndex() - 1));
                break;
            }
            case 93:
            case 94: {
                int a5 = v-3.getTopIndex();
                final int v1 = a5 - (byte1 - 93) - 1;
                final Type v2 = v-3.getStack(v-3.getTopIndex() - 1);
                final Type v3 = v-3.peek();
                v-3.push(v2);
                v-3.push(v3);
                while (a5 > v1) {
                    v-3.setStack(a5, v-3.getStack(a5 - 2));
                    --a5;
                }
                v-3.setStack(v1, v3);
                v-3.setStack(v1 - 1, v2);
                break;
            }
            case 95: {
                final Type v4 = v-3.pop();
                final Type v5 = v-3.pop();
                if (v4.getSize() == 2 || v5.getSize() == 2) {
                    throw new BadBytecode("Swap can not be used with category 2 values, pos = " + v-5);
                }
                v-3.push(v4);
                v-3.push(v5);
                break;
            }
            case 96: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 97: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 98: {
                this.evalBinaryMath(Type.FLOAT, v-3);
                break;
            }
            case 99: {
                this.evalBinaryMath(Type.DOUBLE, v-3);
                break;
            }
            case 100: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 101: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 102: {
                this.evalBinaryMath(Type.FLOAT, v-3);
                break;
            }
            case 103: {
                this.evalBinaryMath(Type.DOUBLE, v-3);
                break;
            }
            case 104: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 105: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 106: {
                this.evalBinaryMath(Type.FLOAT, v-3);
                break;
            }
            case 107: {
                this.evalBinaryMath(Type.DOUBLE, v-3);
                break;
            }
            case 108: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 109: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 110: {
                this.evalBinaryMath(Type.FLOAT, v-3);
                break;
            }
            case 111: {
                this.evalBinaryMath(Type.DOUBLE, v-3);
                break;
            }
            case 112: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 113: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 114: {
                this.evalBinaryMath(Type.FLOAT, v-3);
                break;
            }
            case 115: {
                this.evalBinaryMath(Type.DOUBLE, v-3);
                break;
            }
            case 116: {
                this.verifyAssignable(Type.INTEGER, this.simplePeek(v-3));
                break;
            }
            case 117: {
                this.verifyAssignable(Type.LONG, this.simplePeek(v-3));
                break;
            }
            case 118: {
                this.verifyAssignable(Type.FLOAT, this.simplePeek(v-3));
                break;
            }
            case 119: {
                this.verifyAssignable(Type.DOUBLE, this.simplePeek(v-3));
                break;
            }
            case 120: {
                this.evalShift(Type.INTEGER, v-3);
                break;
            }
            case 121: {
                this.evalShift(Type.LONG, v-3);
                break;
            }
            case 122: {
                this.evalShift(Type.INTEGER, v-3);
                break;
            }
            case 123: {
                this.evalShift(Type.LONG, v-3);
                break;
            }
            case 124: {
                this.evalShift(Type.INTEGER, v-3);
                break;
            }
            case 125: {
                this.evalShift(Type.LONG, v-3);
                break;
            }
            case 126: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 127: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 128: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 129: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 130: {
                this.evalBinaryMath(Type.INTEGER, v-3);
                break;
            }
            case 131: {
                this.evalBinaryMath(Type.LONG, v-3);
                break;
            }
            case 132: {
                final int v6 = v-4.byteAt(v-5 + 1);
                this.verifyAssignable(Type.INTEGER, v-3.getLocal(v6));
                this.access(v6, Type.INTEGER, v-2);
                break;
            }
            case 133: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                this.simplePush(Type.LONG, v-3);
                break;
            }
            case 134: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                this.simplePush(Type.FLOAT, v-3);
                break;
            }
            case 135: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                this.simplePush(Type.DOUBLE, v-3);
                break;
            }
            case 136: {
                this.verifyAssignable(Type.LONG, this.simplePop(v-3));
                this.simplePush(Type.INTEGER, v-3);
                break;
            }
            case 137: {
                this.verifyAssignable(Type.LONG, this.simplePop(v-3));
                this.simplePush(Type.FLOAT, v-3);
                break;
            }
            case 138: {
                this.verifyAssignable(Type.LONG, this.simplePop(v-3));
                this.simplePush(Type.DOUBLE, v-3);
                break;
            }
            case 139: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(v-3));
                this.simplePush(Type.INTEGER, v-3);
                break;
            }
            case 140: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(v-3));
                this.simplePush(Type.LONG, v-3);
                break;
            }
            case 141: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(v-3));
                this.simplePush(Type.DOUBLE, v-3);
                break;
            }
            case 142: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(v-3));
                this.simplePush(Type.INTEGER, v-3);
                break;
            }
            case 143: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(v-3));
                this.simplePush(Type.LONG, v-3);
                break;
            }
            case 144: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(v-3));
                this.simplePush(Type.FLOAT, v-3);
                break;
            }
            case 145:
            case 146:
            case 147: {
                this.verifyAssignable(Type.INTEGER, v-3.peek());
                break;
            }
            case 148: {
                this.verifyAssignable(Type.LONG, this.simplePop(v-3));
                this.verifyAssignable(Type.LONG, this.simplePop(v-3));
                v-3.push(Type.INTEGER);
                break;
            }
            case 149:
            case 150: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(v-3));
                this.verifyAssignable(Type.FLOAT, this.simplePop(v-3));
                v-3.push(Type.INTEGER);
                break;
            }
            case 151:
            case 152: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(v-3));
                this.verifyAssignable(Type.DOUBLE, this.simplePop(v-3));
                v-3.push(Type.INTEGER);
                break;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                break;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                break;
            }
            case 165:
            case 166: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(v-3));
                this.verifyAssignable(Type.OBJECT, this.simplePop(v-3));
            }
            case 168: {
                v-3.push(Type.RETURN_ADDRESS);
                break;
            }
            case 169: {
                this.verifyAssignable(Type.RETURN_ADDRESS, v-3.getLocal(v-4.byteAt(v-5 + 1)));
                break;
            }
            case 170:
            case 171:
            case 172: {
                this.verifyAssignable(Type.INTEGER, this.simplePop(v-3));
                break;
            }
            case 173: {
                this.verifyAssignable(Type.LONG, this.simplePop(v-3));
                break;
            }
            case 174: {
                this.verifyAssignable(Type.FLOAT, this.simplePop(v-3));
                break;
            }
            case 175: {
                this.verifyAssignable(Type.DOUBLE, this.simplePop(v-3));
                break;
            }
            case 176: {
                try {
                    final CtClass v7 = Descriptor.getReturnType(v-6.getDescriptor(), this.classPool);
                    this.verifyAssignable(Type.get(v7), this.simplePop(v-3));
                }
                catch (NotFoundException v8) {
                    throw new RuntimeException(v8);
                }
            }
            case 178: {
                this.evalGetField(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 179: {
                this.evalPutField(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 180: {
                this.evalGetField(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 181: {
                this.evalPutField(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 182:
            case 183:
            case 184: {
                this.evalInvokeMethod(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 185: {
                this.evalInvokeIntfMethod(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 186: {
                this.evalInvokeDynamic(byte1, v-4.u16bitAt(v-5 + 1), v-3);
                break;
            }
            case 187: {
                v-3.push(this.resolveClassInfo(this.constPool.getClassInfo(v-4.u16bitAt(v-5 + 1))));
                break;
            }
            case 188: {
                this.evalNewArray(v-5, v-4, v-3);
                break;
            }
            case 189: {
                this.evalNewObjectArray(v-5, v-4, v-3);
                break;
            }
            case 190: {
                final Type v4 = this.simplePop(v-3);
                if (!v4.isArray() && v4 != Type.UNINIT) {
                    throw new BadBytecode("Array length passed a non-array [pos = " + v-5 + "]: " + v4);
                }
                v-3.push(Type.INTEGER);
                break;
            }
            case 191: {
                this.verifyAssignable(this.THROWABLE_TYPE, this.simplePop(v-3));
                break;
            }
            case 192: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(v-3));
                v-3.push(this.typeFromDesc(this.constPool.getClassInfoByDescriptor(v-4.u16bitAt(v-5 + 1))));
                break;
            }
            case 193: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(v-3));
                v-3.push(Type.INTEGER);
                break;
            }
            case 194:
            case 195: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(v-3));
                break;
            }
            case 196: {
                this.evalWide(v-5, v-4, v-3, v-2);
                break;
            }
            case 197: {
                this.evalNewObjectArray(v-5, v-4, v-3);
                break;
            }
            case 198:
            case 199: {
                this.verifyAssignable(Type.OBJECT, this.simplePop(v-3));
            }
            case 201: {
                v-3.push(Type.RETURN_ADDRESS);
                break;
            }
        }
    }
    
    private Type zeroExtend(final Type a1) {
        if (a1 == Type.SHORT || a1 == Type.BYTE || a1 == Type.CHAR || a1 == Type.BOOLEAN) {
            return Type.INTEGER;
        }
        return a1;
    }
    
    private void evalArrayLoad(final Type a1, final Frame a2) throws BadBytecode {
        final Type v1 = a2.pop();
        final Type v2 = a2.pop();
        if (v2 == Type.UNINIT) {
            this.verifyAssignable(Type.INTEGER, v1);
            if (a1 == Type.OBJECT) {
                this.simplePush(Type.UNINIT, a2);
            }
            else {
                this.simplePush(a1, a2);
            }
            return;
        }
        Type v3 = v2.getComponent();
        if (v3 == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + v3);
        }
        v3 = this.zeroExtend(v3);
        this.verifyAssignable(a1, v3);
        this.verifyAssignable(Type.INTEGER, v1);
        this.simplePush(v3, a2);
    }
    
    private void evalArrayStore(final Type a1, final Frame a2) throws BadBytecode {
        final Type v1 = this.simplePop(a2);
        final Type v2 = a2.pop();
        final Type v3 = a2.pop();
        if (v3 == Type.UNINIT) {
            this.verifyAssignable(Type.INTEGER, v2);
            return;
        }
        Type v4 = v3.getComponent();
        if (v4 == null) {
            throw new BadBytecode("Not an array! [pos = " + this.lastPos + "]: " + v4);
        }
        v4 = this.zeroExtend(v4);
        this.verifyAssignable(a1, v4);
        this.verifyAssignable(Type.INTEGER, v2);
        if (a1 == Type.OBJECT) {
            this.verifyAssignable(a1, v1);
        }
        else {
            this.verifyAssignable(v4, v1);
        }
    }
    
    private void evalBinaryMath(final Type a1, final Frame a2) throws BadBytecode {
        final Type v1 = this.simplePop(a2);
        final Type v2 = this.simplePop(a2);
        this.verifyAssignable(a1, v1);
        this.verifyAssignable(a1, v2);
        this.simplePush(v2, a2);
    }
    
    private void evalGetField(final int a3, final int v1, final Frame v2) throws BadBytecode {
        final String v3 = this.constPool.getFieldrefType(v1);
        final Type v4 = this.zeroExtend(this.typeFromDesc(v3));
        if (a3 == 180) {
            final Type a4 = this.resolveClassInfo(this.constPool.getFieldrefClassName(v1));
            this.verifyAssignable(a4, this.simplePop(v2));
        }
        this.simplePush(v4, v2);
    }
    
    private void evalInvokeIntfMethod(final int a1, final int a2, final Frame a3) throws BadBytecode {
        final String v1 = this.constPool.getInterfaceMethodrefType(a2);
        final Type[] v2 = this.paramTypesFromDesc(v1);
        int v3 = v2.length;
        while (v3 > 0) {
            this.verifyAssignable(this.zeroExtend(v2[--v3]), this.simplePop(a3));
        }
        final String v4 = this.constPool.getInterfaceMethodrefClassName(a2);
        final Type v5 = this.resolveClassInfo(v4);
        this.verifyAssignable(v5, this.simplePop(a3));
        final Type v6 = this.returnTypeFromDesc(v1);
        if (v6 != Type.VOID) {
            this.simplePush(this.zeroExtend(v6), a3);
        }
    }
    
    private void evalInvokeMethod(final int a3, final int v1, final Frame v2) throws BadBytecode {
        final String v3 = this.constPool.getMethodrefType(v1);
        final Type[] v4 = this.paramTypesFromDesc(v3);
        int v5 = v4.length;
        while (v5 > 0) {
            this.verifyAssignable(this.zeroExtend(v4[--v5]), this.simplePop(v2));
        }
        if (a3 != 184) {
            final Type a4 = this.resolveClassInfo(this.constPool.getMethodrefClassName(v1));
            this.verifyAssignable(a4, this.simplePop(v2));
        }
        final Type v6 = this.returnTypeFromDesc(v3);
        if (v6 != Type.VOID) {
            this.simplePush(this.zeroExtend(v6), v2);
        }
    }
    
    private void evalInvokeDynamic(final int a1, final int a2, final Frame a3) throws BadBytecode {
        final String v1 = this.constPool.getInvokeDynamicType(a2);
        final Type[] v2 = this.paramTypesFromDesc(v1);
        int v3 = v2.length;
        while (v3 > 0) {
            this.verifyAssignable(this.zeroExtend(v2[--v3]), this.simplePop(a3));
        }
        final Type v4 = this.returnTypeFromDesc(v1);
        if (v4 != Type.VOID) {
            this.simplePush(this.zeroExtend(v4), a3);
        }
    }
    
    private void evalLDC(final int v-2, final Frame v-1) throws BadBytecode {
        final int v0 = this.constPool.getTag(v-2);
        Type v2 = null;
        switch (v0) {
            case 8: {
                final Type a1 = this.STRING_TYPE;
                break;
            }
            case 3: {
                final Type a2 = Type.INTEGER;
                break;
            }
            case 4: {
                v2 = Type.FLOAT;
                break;
            }
            case 5: {
                v2 = Type.LONG;
                break;
            }
            case 6: {
                v2 = Type.DOUBLE;
                break;
            }
            case 7: {
                v2 = this.CLASS_TYPE;
                break;
            }
            default: {
                throw new BadBytecode("bad LDC [pos = " + this.lastPos + "]: " + v0);
            }
        }
        this.simplePush(v2, v-1);
    }
    
    private void evalLoad(final Type a1, final int a2, final Frame a3, final Subroutine a4) throws BadBytecode {
        final Type v1 = a3.getLocal(a2);
        this.verifyAssignable(a1, v1);
        this.simplePush(v1, a3);
        this.access(a2, v1, a4);
    }
    
    private void evalNewArray(final int a1, final CodeIterator a2, final Frame a3) throws BadBytecode {
        this.verifyAssignable(Type.INTEGER, this.simplePop(a3));
        Type v1 = null;
        final int v2 = a2.byteAt(a1 + 1);
        switch (v2) {
            case 4: {
                v1 = this.getType("boolean[]");
                break;
            }
            case 5: {
                v1 = this.getType("char[]");
                break;
            }
            case 8: {
                v1 = this.getType("byte[]");
                break;
            }
            case 9: {
                v1 = this.getType("short[]");
                break;
            }
            case 10: {
                v1 = this.getType("int[]");
                break;
            }
            case 11: {
                v1 = this.getType("long[]");
                break;
            }
            case 6: {
                v1 = this.getType("float[]");
                break;
            }
            case 7: {
                v1 = this.getType("double[]");
                break;
            }
            default: {
                throw new BadBytecode("Invalid array type [pos = " + a1 + "]: " + v2);
            }
        }
        a3.push(v1);
    }
    
    private void evalNewObjectArray(final int a3, final CodeIterator v1, final Frame v2) throws BadBytecode {
        final Type v3 = this.resolveClassInfo(this.constPool.getClassInfo(v1.u16bitAt(a3 + 1)));
        String v4 = v3.getCtClass().getName();
        final int v5 = v1.byteAt(a3);
        int v6 = 0;
        if (v5 == 197) {
            final int a4 = v1.byteAt(a3 + 3);
        }
        else {
            v4 += "[]";
            v6 = 1;
        }
        while (v6-- > 0) {
            this.verifyAssignable(Type.INTEGER, this.simplePop(v2));
        }
        this.simplePush(this.getType(v4), v2);
    }
    
    private void evalPutField(final int a3, final int v1, final Frame v2) throws BadBytecode {
        final String v3 = this.constPool.getFieldrefType(v1);
        final Type v4 = this.zeroExtend(this.typeFromDesc(v3));
        this.verifyAssignable(v4, this.simplePop(v2));
        if (a3 == 181) {
            final Type a4 = this.resolveClassInfo(this.constPool.getFieldrefClassName(v1));
            this.verifyAssignable(a4, this.simplePop(v2));
        }
    }
    
    private void evalShift(final Type a1, final Frame a2) throws BadBytecode {
        final Type v1 = this.simplePop(a2);
        final Type v2 = this.simplePop(a2);
        this.verifyAssignable(Type.INTEGER, v1);
        this.verifyAssignable(a1, v2);
        this.simplePush(v2, a2);
    }
    
    private void evalStore(final Type a1, final int a2, final Frame a3, final Subroutine a4) throws BadBytecode {
        final Type v1 = this.simplePop(a3);
        if (a1 != Type.OBJECT || v1 != Type.RETURN_ADDRESS) {
            this.verifyAssignable(a1, v1);
        }
        this.simpleSetLocal(a2, v1, a3);
        this.access(a2, v1, a4);
    }
    
    private void evalWide(final int a1, final CodeIterator a2, final Frame a3, final Subroutine a4) throws BadBytecode {
        final int v1 = a2.byteAt(a1 + 1);
        final int v2 = a2.u16bitAt(a1 + 2);
        switch (v1) {
            case 21: {
                this.evalLoad(Type.INTEGER, v2, a3, a4);
                break;
            }
            case 22: {
                this.evalLoad(Type.LONG, v2, a3, a4);
                break;
            }
            case 23: {
                this.evalLoad(Type.FLOAT, v2, a3, a4);
                break;
            }
            case 24: {
                this.evalLoad(Type.DOUBLE, v2, a3, a4);
                break;
            }
            case 25: {
                this.evalLoad(Type.OBJECT, v2, a3, a4);
                break;
            }
            case 54: {
                this.evalStore(Type.INTEGER, v2, a3, a4);
                break;
            }
            case 55: {
                this.evalStore(Type.LONG, v2, a3, a4);
                break;
            }
            case 56: {
                this.evalStore(Type.FLOAT, v2, a3, a4);
                break;
            }
            case 57: {
                this.evalStore(Type.DOUBLE, v2, a3, a4);
                break;
            }
            case 58: {
                this.evalStore(Type.OBJECT, v2, a3, a4);
                break;
            }
            case 132: {
                this.verifyAssignable(Type.INTEGER, a3.getLocal(v2));
                break;
            }
            case 169: {
                this.verifyAssignable(Type.RETURN_ADDRESS, a3.getLocal(v2));
                break;
            }
            default: {
                throw new BadBytecode("Invalid WIDE operand [pos = " + a1 + "]: " + v1);
            }
        }
    }
    
    private Type getType(final String v2) throws BadBytecode {
        try {
            return Type.get(this.classPool.get(v2));
        }
        catch (NotFoundException a1) {
            throw new BadBytecode("Could not find class [pos = " + this.lastPos + "]: " + v2);
        }
    }
    
    private Type[] paramTypesFromDesc(final String v-2) throws BadBytecode {
        CtClass[] parameterTypes = null;
        try {
            parameterTypes = Descriptor.getParameterTypes(v-2, this.classPool);
        }
        catch (NotFoundException a1) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + a1.getMessage());
        }
        if (parameterTypes == null) {
            throw new BadBytecode("Could not obtain parameters for descriptor [pos = " + this.lastPos + "]: " + v-2);
        }
        final Type[] v0 = new Type[parameterTypes.length];
        for (int v2 = 0; v2 < v0.length; ++v2) {
            v0[v2] = Type.get(parameterTypes[v2]);
        }
        return v0;
    }
    
    private Type returnTypeFromDesc(final String v2) throws BadBytecode {
        CtClass v3 = null;
        try {
            v3 = Descriptor.getReturnType(v2, this.classPool);
        }
        catch (NotFoundException a1) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + a1.getMessage());
        }
        if (v3 == null) {
            throw new BadBytecode("Could not obtain return type for descriptor [pos = " + this.lastPos + "]: " + v2);
        }
        return Type.get(v3);
    }
    
    private Type simplePeek(final Frame a1) {
        final Type v1 = a1.peek();
        return (v1 == Type.TOP) ? a1.getStack(a1.getTopIndex() - 1) : v1;
    }
    
    private Type simplePop(final Frame a1) {
        final Type v1 = a1.pop();
        return (v1 == Type.TOP) ? a1.pop() : v1;
    }
    
    private void simplePush(final Type a1, final Frame a2) {
        a2.push(a1);
        if (a1.getSize() == 2) {
            a2.push(Type.TOP);
        }
    }
    
    private void access(final int a1, final Type a2, final Subroutine a3) {
        if (a3 == null) {
            return;
        }
        a3.access(a1);
        if (a2.getSize() == 2) {
            a3.access(a1 + 1);
        }
    }
    
    private void simpleSetLocal(final int a1, final Type a2, final Frame a3) {
        a3.setLocal(a1, a2);
        if (a2.getSize() == 2) {
            a3.setLocal(a1 + 1, Type.TOP);
        }
    }
    
    private Type resolveClassInfo(final String v2) throws BadBytecode {
        CtClass v3 = null;
        try {
            if (v2.charAt(0) == '[') {
                v3 = Descriptor.toCtClass(v2, this.classPool);
            }
            else {
                v3 = this.classPool.get(v2);
            }
        }
        catch (NotFoundException a1) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + a1.getMessage());
        }
        if (v3 == null) {
            throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + v2);
        }
        return Type.get(v3);
    }
    
    private Type typeFromDesc(final String v2) throws BadBytecode {
        CtClass v3 = null;
        try {
            v3 = Descriptor.toCtClass(v2, this.classPool);
        }
        catch (NotFoundException a1) {
            throw new BadBytecode("Could not find class in descriptor [pos = " + this.lastPos + "]: " + a1.getMessage());
        }
        if (v3 == null) {
            throw new BadBytecode("Could not obtain type for descriptor [pos = " + this.lastPos + "]: " + v2);
        }
        return Type.get(v3);
    }
    
    private void verifyAssignable(final Type a1, final Type a2) throws BadBytecode {
        if (!a1.isAssignableFrom(a2)) {
            throw new BadBytecode("Expected type: " + a1 + " Got: " + a2 + " [pos = " + this.lastPos + "]");
        }
    }
}
