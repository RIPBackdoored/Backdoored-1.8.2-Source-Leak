package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class BasicInterpreter extends Interpreter<BasicValue> implements Opcodes
{
    public BasicInterpreter() {
        super(327680);
    }
    
    protected BasicInterpreter(final int a1) {
        super(a1);
    }
    
    @Override
    public BasicValue newValue(final Type a1) {
        if (a1 == null) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        switch (a1.getSort()) {
            case 0: {
                return null;
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5: {
                return BasicValue.INT_VALUE;
            }
            case 6: {
                return BasicValue.FLOAT_VALUE;
            }
            case 7: {
                return BasicValue.LONG_VALUE;
            }
            case 8: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 9:
            case 10: {
                return BasicValue.REFERENCE_VALUE;
            }
            default: {
                throw new Error("Internal error");
            }
        }
    }
    
    @Override
    public BasicValue newOperation(final AbstractInsnNode v0) throws AnalyzerException {
        switch (v0.getOpcode()) {
            case 1: {
                return this.newValue(Type.getObjectType("null"));
            }
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                return BasicValue.INT_VALUE;
            }
            case 9:
            case 10: {
                return BasicValue.LONG_VALUE;
            }
            case 11:
            case 12:
            case 13: {
                return BasicValue.FLOAT_VALUE;
            }
            case 14:
            case 15: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 16:
            case 17: {
                return BasicValue.INT_VALUE;
            }
            case 18: {
                final Object v = ((LdcInsnNode)v0).cst;
                if (v instanceof Integer) {
                    return BasicValue.INT_VALUE;
                }
                if (v instanceof Float) {
                    return BasicValue.FLOAT_VALUE;
                }
                if (v instanceof Long) {
                    return BasicValue.LONG_VALUE;
                }
                if (v instanceof Double) {
                    return BasicValue.DOUBLE_VALUE;
                }
                if (v instanceof String) {
                    return this.newValue(Type.getObjectType("java/lang/String"));
                }
                if (v instanceof Type) {
                    final int a1 = ((Type)v).getSort();
                    if (a1 == 10 || a1 == 9) {
                        return this.newValue(Type.getObjectType("java/lang/Class"));
                    }
                    if (a1 == 11) {
                        return this.newValue(Type.getObjectType("java/lang/invoke/MethodType"));
                    }
                    throw new IllegalArgumentException("Illegal LDC constant " + v);
                }
                else {
                    if (v instanceof Handle) {
                        return this.newValue(Type.getObjectType("java/lang/invoke/MethodHandle"));
                    }
                    throw new IllegalArgumentException("Illegal LDC constant " + v);
                }
                break;
            }
            case 168: {
                return BasicValue.RETURNADDRESS_VALUE;
            }
            case 178: {
                return this.newValue(Type.getType(((FieldInsnNode)v0).desc));
            }
            case 187: {
                return this.newValue(Type.getObjectType(((TypeInsnNode)v0).desc));
            }
            default: {
                throw new Error("Internal error.");
            }
        }
    }
    
    @Override
    public BasicValue copyOperation(final AbstractInsnNode a1, final BasicValue a2) throws AnalyzerException {
        return a2;
    }
    
    @Override
    public BasicValue unaryOperation(final AbstractInsnNode v2, final BasicValue v3) throws AnalyzerException {
        switch (v2.getOpcode()) {
            case 116:
            case 132:
            case 136:
            case 139:
            case 142:
            case 145:
            case 146:
            case 147: {
                return BasicValue.INT_VALUE;
            }
            case 118:
            case 134:
            case 137:
            case 144: {
                return BasicValue.FLOAT_VALUE;
            }
            case 117:
            case 133:
            case 140:
            case 143: {
                return BasicValue.LONG_VALUE;
            }
            case 119:
            case 135:
            case 138:
            case 141: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 179: {
                return null;
            }
            case 180: {
                return this.newValue(Type.getType(((FieldInsnNode)v2).desc));
            }
            case 188: {
                switch (((IntInsnNode)v2).operand) {
                    case 4: {
                        return this.newValue(Type.getType("[Z"));
                    }
                    case 5: {
                        return this.newValue(Type.getType("[C"));
                    }
                    case 8: {
                        return this.newValue(Type.getType("[B"));
                    }
                    case 9: {
                        return this.newValue(Type.getType("[S"));
                    }
                    case 10: {
                        return this.newValue(Type.getType("[I"));
                    }
                    case 6: {
                        return this.newValue(Type.getType("[F"));
                    }
                    case 7: {
                        return this.newValue(Type.getType("[D"));
                    }
                    case 11: {
                        return this.newValue(Type.getType("[J"));
                    }
                    default: {
                        throw new AnalyzerException(v2, "Invalid array type");
                    }
                }
                break;
            }
            case 189: {
                final String a1 = ((TypeInsnNode)v2).desc;
                return this.newValue(Type.getType("[" + Type.getObjectType(a1)));
            }
            case 190: {
                return BasicValue.INT_VALUE;
            }
            case 191: {
                return null;
            }
            case 192: {
                final String a2 = ((TypeInsnNode)v2).desc;
                return this.newValue(Type.getObjectType(a2));
            }
            case 193: {
                return BasicValue.INT_VALUE;
            }
            case 194:
            case 195:
            case 198:
            case 199: {
                return null;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
    }
    
    @Override
    public BasicValue binaryOperation(final AbstractInsnNode a1, final BasicValue a2, final BasicValue a3) throws AnalyzerException {
        switch (a1.getOpcode()) {
            case 46:
            case 51:
            case 52:
            case 53:
            case 96:
            case 100:
            case 104:
            case 108:
            case 112:
            case 120:
            case 122:
            case 124:
            case 126:
            case 128:
            case 130: {
                return BasicValue.INT_VALUE;
            }
            case 48:
            case 98:
            case 102:
            case 106:
            case 110:
            case 114: {
                return BasicValue.FLOAT_VALUE;
            }
            case 47:
            case 97:
            case 101:
            case 105:
            case 109:
            case 113:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131: {
                return BasicValue.LONG_VALUE;
            }
            case 49:
            case 99:
            case 103:
            case 107:
            case 111:
            case 115: {
                return BasicValue.DOUBLE_VALUE;
            }
            case 50: {
                return BasicValue.REFERENCE_VALUE;
            }
            case 148:
            case 149:
            case 150:
            case 151:
            case 152: {
                return BasicValue.INT_VALUE;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 181: {
                return null;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
    }
    
    @Override
    public BasicValue ternaryOperation(final AbstractInsnNode a1, final BasicValue a2, final BasicValue a3, final BasicValue a4) throws AnalyzerException {
        return null;
    }
    
    @Override
    public BasicValue naryOperation(final AbstractInsnNode a1, final List<? extends BasicValue> a2) throws AnalyzerException {
        final int v1 = a1.getOpcode();
        if (v1 == 197) {
            return this.newValue(Type.getType(((MultiANewArrayInsnNode)a1).desc));
        }
        if (v1 == 186) {
            return this.newValue(Type.getReturnType(((InvokeDynamicInsnNode)a1).desc));
        }
        return this.newValue(Type.getReturnType(((MethodInsnNode)a1).desc));
    }
    
    @Override
    public void returnOperation(final AbstractInsnNode a1, final BasicValue a2, final BasicValue a3) throws AnalyzerException {
    }
    
    @Override
    public BasicValue merge(final BasicValue a1, final BasicValue a2) {
        if (!a1.equals(a2)) {
            return BasicValue.UNINITIALIZED_VALUE;
        }
        return a1;
    }
    
    public /* bridge */ Value merge(final Value value, final Value value2) {
        return this.merge((BasicValue)value, (BasicValue)value2);
    }
    
    public /* bridge */ void returnOperation(final AbstractInsnNode a1, final Value value, final Value value2) throws AnalyzerException {
        this.returnOperation(a1, (BasicValue)value, (BasicValue)value2);
    }
    
    public /* bridge */ Value naryOperation(final AbstractInsnNode a1, final List a2) throws AnalyzerException {
        return this.naryOperation(a1, a2);
    }
    
    public /* bridge */ Value ternaryOperation(final AbstractInsnNode a1, final Value value, final Value value2, final Value value3) throws AnalyzerException {
        return this.ternaryOperation(a1, (BasicValue)value, (BasicValue)value2, (BasicValue)value3);
    }
    
    public /* bridge */ Value binaryOperation(final AbstractInsnNode a1, final Value value, final Value value2) throws AnalyzerException {
        return this.binaryOperation(a1, (BasicValue)value, (BasicValue)value2);
    }
    
    public /* bridge */ Value unaryOperation(final AbstractInsnNode v2, final Value value) throws AnalyzerException {
        return this.unaryOperation(v2, (BasicValue)value);
    }
    
    public /* bridge */ Value copyOperation(final AbstractInsnNode a1, final Value value) throws AnalyzerException {
        return this.copyOperation(a1, (BasicValue)value);
    }
    
    public /* bridge */ Value newOperation(final AbstractInsnNode v0) throws AnalyzerException {
        return this.newOperation(v0);
    }
    
    public /* bridge */ Value newValue(final Type a1) {
        return this.newValue(a1);
    }
}
