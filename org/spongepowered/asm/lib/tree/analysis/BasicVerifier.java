package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class BasicVerifier extends BasicInterpreter
{
    public BasicVerifier() {
        super(327680);
    }
    
    protected BasicVerifier(final int a1) {
        super(a1);
    }
    
    @Override
    public BasicValue copyOperation(final AbstractInsnNode v-1, final BasicValue v0) throws AnalyzerException {
        Value v = null;
        switch (v-1.getOpcode()) {
            case 21:
            case 54: {
                final Value a1 = BasicValue.INT_VALUE;
                break;
            }
            case 23:
            case 56: {
                final Value a2 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 22:
            case 55: {
                v = BasicValue.LONG_VALUE;
                break;
            }
            case 24:
            case 57: {
                v = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 25: {
                if (!v0.isReference()) {
                    throw new AnalyzerException(v-1, null, "an object reference", v0);
                }
                return v0;
            }
            case 58: {
                if (!v0.isReference() && !BasicValue.RETURNADDRESS_VALUE.equals(v0)) {
                    throw new AnalyzerException(v-1, null, "an object reference or a return address", v0);
                }
                return v0;
            }
            default: {
                return v0;
            }
        }
        if (!v.equals(v0)) {
            throw new AnalyzerException(v-1, null, v, v0);
        }
        return v0;
    }
    
    @Override
    public BasicValue unaryOperation(final AbstractInsnNode v-1, final BasicValue v0) throws AnalyzerException {
        BasicValue v = null;
        switch (v-1.getOpcode()) {
            case 116:
            case 132:
            case 133:
            case 134:
            case 135:
            case 145:
            case 146:
            case 147:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 188:
            case 189: {
                final BasicValue a1 = BasicValue.INT_VALUE;
                break;
            }
            case 118:
            case 139:
            case 140:
            case 141:
            case 174: {
                final BasicValue a2 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 117:
            case 136:
            case 137:
            case 138:
            case 173: {
                v = BasicValue.LONG_VALUE;
                break;
            }
            case 119:
            case 142:
            case 143:
            case 144:
            case 175: {
                v = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 180: {
                v = this.newValue(Type.getObjectType(((FieldInsnNode)v-1).owner));
                break;
            }
            case 192: {
                if (!v0.isReference()) {
                    throw new AnalyzerException(v-1, null, "an object reference", v0);
                }
                return super.unaryOperation(v-1, v0);
            }
            case 190: {
                if (!this.isArrayValue(v0)) {
                    throw new AnalyzerException(v-1, null, "an array reference", v0);
                }
                return super.unaryOperation(v-1, v0);
            }
            case 176:
            case 191:
            case 193:
            case 194:
            case 195:
            case 198:
            case 199: {
                if (!v0.isReference()) {
                    throw new AnalyzerException(v-1, null, "an object reference", v0);
                }
                return super.unaryOperation(v-1, v0);
            }
            case 179: {
                v = this.newValue(Type.getType(((FieldInsnNode)v-1).desc));
                break;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
        if (!this.isSubTypeOf(v0, v)) {
            throw new AnalyzerException(v-1, null, v, v0);
        }
        return super.unaryOperation(v-1, v0);
    }
    
    @Override
    public BasicValue binaryOperation(final AbstractInsnNode v-2, final BasicValue v-1, final BasicValue v0) throws AnalyzerException {
        BasicValue v = null;
        BasicValue v2 = null;
        switch (v-2.getOpcode()) {
            case 46: {
                final BasicValue a1 = this.newValue(Type.getType("[I"));
                final BasicValue a2 = BasicValue.INT_VALUE;
                break;
            }
            case 51: {
                if (this.isSubTypeOf(v-1, this.newValue(Type.getType("[Z")))) {
                    final BasicValue a3 = this.newValue(Type.getType("[Z"));
                }
                else {
                    v = this.newValue(Type.getType("[B"));
                }
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 52: {
                v = this.newValue(Type.getType("[C"));
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 53: {
                v = this.newValue(Type.getType("[S"));
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 47: {
                v = this.newValue(Type.getType("[J"));
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 48: {
                v = this.newValue(Type.getType("[F"));
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 49: {
                v = this.newValue(Type.getType("[D"));
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 50: {
                v = this.newValue(Type.getType("[Ljava/lang/Object;"));
                v2 = BasicValue.INT_VALUE;
                break;
            }
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
            case 130:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164: {
                v = BasicValue.INT_VALUE;
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 98:
            case 102:
            case 106:
            case 110:
            case 114:
            case 149:
            case 150: {
                v = BasicValue.FLOAT_VALUE;
                v2 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 97:
            case 101:
            case 105:
            case 109:
            case 113:
            case 127:
            case 129:
            case 131:
            case 148: {
                v = BasicValue.LONG_VALUE;
                v2 = BasicValue.LONG_VALUE;
                break;
            }
            case 121:
            case 123:
            case 125: {
                v = BasicValue.LONG_VALUE;
                v2 = BasicValue.INT_VALUE;
                break;
            }
            case 99:
            case 103:
            case 107:
            case 111:
            case 115:
            case 151:
            case 152: {
                v = BasicValue.DOUBLE_VALUE;
                v2 = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 165:
            case 166: {
                v = BasicValue.REFERENCE_VALUE;
                v2 = BasicValue.REFERENCE_VALUE;
                break;
            }
            case 181: {
                final FieldInsnNode v3 = (FieldInsnNode)v-2;
                v = this.newValue(Type.getObjectType(v3.owner));
                v2 = this.newValue(Type.getType(v3.desc));
                break;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
        if (!this.isSubTypeOf(v-1, v)) {
            throw new AnalyzerException(v-2, "First argument", v, v-1);
        }
        if (!this.isSubTypeOf(v0, v2)) {
            throw new AnalyzerException(v-2, "Second argument", v2, v0);
        }
        if (v-2.getOpcode() == 50) {
            return this.getElementValue(v-1);
        }
        return super.binaryOperation(v-2, v-1, v0);
    }
    
    @Override
    public BasicValue ternaryOperation(final AbstractInsnNode v-4, final BasicValue v-3, final BasicValue v-2, final BasicValue v-1) throws AnalyzerException {
        BasicValue v1 = null;
        BasicValue v2 = null;
        switch (v-4.getOpcode()) {
            case 79: {
                final BasicValue a1 = this.newValue(Type.getType("[I"));
                final BasicValue a2 = BasicValue.INT_VALUE;
                break;
            }
            case 84: {
                if (this.isSubTypeOf(v-3, this.newValue(Type.getType("[Z")))) {
                    final BasicValue a3 = this.newValue(Type.getType("[Z"));
                }
                else {
                    final BasicValue a4 = this.newValue(Type.getType("[B"));
                }
                v1 = BasicValue.INT_VALUE;
                break;
            }
            case 85: {
                v2 = this.newValue(Type.getType("[C"));
                v1 = BasicValue.INT_VALUE;
                break;
            }
            case 86: {
                v2 = this.newValue(Type.getType("[S"));
                v1 = BasicValue.INT_VALUE;
                break;
            }
            case 80: {
                v2 = this.newValue(Type.getType("[J"));
                v1 = BasicValue.LONG_VALUE;
                break;
            }
            case 81: {
                v2 = this.newValue(Type.getType("[F"));
                v1 = BasicValue.FLOAT_VALUE;
                break;
            }
            case 82: {
                v2 = this.newValue(Type.getType("[D"));
                v1 = BasicValue.DOUBLE_VALUE;
                break;
            }
            case 83: {
                v2 = v-3;
                v1 = BasicValue.REFERENCE_VALUE;
                break;
            }
            default: {
                throw new Error("Internal error.");
            }
        }
        if (!this.isSubTypeOf(v-3, v2)) {
            throw new AnalyzerException(v-4, "First argument", "a " + v2 + " array reference", v-3);
        }
        if (!BasicValue.INT_VALUE.equals(v-2)) {
            throw new AnalyzerException(v-4, "Second argument", BasicValue.INT_VALUE, v-2);
        }
        if (!this.isSubTypeOf(v-1, v1)) {
            throw new AnalyzerException(v-4, "Third argument", v1, v-1);
        }
        return null;
    }
    
    @Override
    public BasicValue naryOperation(final AbstractInsnNode v-6, final List<? extends BasicValue> v-5) throws AnalyzerException {
        final int opcode = v-6.getOpcode();
        if (opcode == 197) {
            for (int a1 = 0; a1 < v-5.size(); ++a1) {
                if (!BasicValue.INT_VALUE.equals(v-5.get(a1))) {
                    throw new AnalyzerException(v-6, null, BasicValue.INT_VALUE, (Value)v-5.get(a1));
                }
            }
        }
        else {
            int i = 0;
            int n = 0;
            if (opcode != 184 && opcode != 186) {
                final Type a2 = Type.getObjectType(((MethodInsnNode)v-6).owner);
                if (!this.isSubTypeOf((BasicValue)v-5.get(i++), this.newValue(a2))) {
                    throw new AnalyzerException(v-6, "Method owner", this.newValue(a2), (Value)v-5.get(0));
                }
            }
            final String v4 = (opcode == 186) ? ((InvokeDynamicInsnNode)v-6).desc : ((MethodInsnNode)v-6).desc;
            final Type[] v0 = Type.getArgumentTypes(v4);
            while (i < v-5.size()) {
                final BasicValue v2 = this.newValue(v0[n++]);
                final BasicValue v3 = (BasicValue)v-5.get(i++);
                if (!this.isSubTypeOf(v3, v2)) {
                    throw new AnalyzerException(v-6, "Argument " + n, v2, v3);
                }
            }
        }
        return super.naryOperation(v-6, v-5);
    }
    
    @Override
    public void returnOperation(final AbstractInsnNode a1, final BasicValue a2, final BasicValue a3) throws AnalyzerException {
        if (!this.isSubTypeOf(a2, a3)) {
            throw new AnalyzerException(a1, "Incompatible return type", a3, a2);
        }
    }
    
    protected boolean isArrayValue(final BasicValue a1) {
        return a1.isReference();
    }
    
    protected BasicValue getElementValue(final BasicValue a1) throws AnalyzerException {
        return BasicValue.REFERENCE_VALUE;
    }
    
    protected boolean isSubTypeOf(final BasicValue a1, final BasicValue a2) {
        return a1.equals(a2);
    }
    
    public /* bridge */ void returnOperation(final AbstractInsnNode a1, final Value value, final Value value2) throws AnalyzerException {
        this.returnOperation(a1, (BasicValue)value, (BasicValue)value2);
    }
    
    public /* bridge */ Value naryOperation(final AbstractInsnNode v-6, final List v-7) throws AnalyzerException {
        return this.naryOperation(v-6, v-7);
    }
    
    public /* bridge */ Value ternaryOperation(final AbstractInsnNode v-4, final Value value, final Value value2, final Value value3) throws AnalyzerException {
        return this.ternaryOperation(v-4, (BasicValue)value, (BasicValue)value2, (BasicValue)value3);
    }
    
    public /* bridge */ Value binaryOperation(final AbstractInsnNode v-2, final Value value, final Value value2) throws AnalyzerException {
        return this.binaryOperation(v-2, (BasicValue)value, (BasicValue)value2);
    }
    
    public /* bridge */ Value unaryOperation(final AbstractInsnNode v-1, final Value value) throws AnalyzerException {
        return this.unaryOperation(v-1, (BasicValue)value);
    }
    
    public /* bridge */ Value copyOperation(final AbstractInsnNode v-1, final Value value) throws AnalyzerException {
        return this.copyOperation(v-1, (BasicValue)value);
    }
}
