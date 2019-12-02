package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class SourceInterpreter extends Interpreter<SourceValue> implements Opcodes
{
    public SourceInterpreter() {
        super(327680);
    }
    
    protected SourceInterpreter(final int a1) {
        super(a1);
    }
    
    @Override
    public SourceValue newValue(final Type a1) {
        if (a1 == Type.VOID_TYPE) {
            return null;
        }
        return new SourceValue((a1 == null) ? 1 : a1.getSize());
    }
    
    @Override
    public SourceValue newOperation(final AbstractInsnNode v0) {
        int v2 = 0;
        switch (v0.getOpcode()) {
            case 9:
            case 10:
            case 14:
            case 15: {
                final int a1 = 2;
                break;
            }
            case 18: {
                final Object v = ((LdcInsnNode)v0).cst;
                v2 = ((v instanceof Long || v instanceof Double) ? 2 : 1);
                break;
            }
            case 178: {
                v2 = Type.getType(((FieldInsnNode)v0).desc).getSize();
                break;
            }
            default: {
                v2 = 1;
                break;
            }
        }
        return new SourceValue(v2, v0);
    }
    
    @Override
    public SourceValue copyOperation(final AbstractInsnNode a1, final SourceValue a2) {
        return new SourceValue(a2.getSize(), a1);
    }
    
    @Override
    public SourceValue unaryOperation(final AbstractInsnNode v2, final SourceValue v3) {
        final int v4;
        switch (v2.getOpcode()) {
            case 117:
            case 119:
            case 133:
            case 135:
            case 138:
            case 140:
            case 141:
            case 143: {
                final int a1 = 2;
                break;
            }
            case 180: {
                final int a2 = Type.getType(((FieldInsnNode)v2).desc).getSize();
                break;
            }
            default: {
                v4 = 1;
                break;
            }
        }
        return new SourceValue(v4, v2);
    }
    
    @Override
    public SourceValue binaryOperation(final AbstractInsnNode a3, final SourceValue v1, final SourceValue v2) {
        final int v3;
        switch (a3.getOpcode()) {
            case 47:
            case 49:
            case 97:
            case 99:
            case 101:
            case 103:
            case 105:
            case 107:
            case 109:
            case 111:
            case 113:
            case 115:
            case 121:
            case 123:
            case 125:
            case 127:
            case 129:
            case 131: {
                final int a4 = 2;
                break;
            }
            default: {
                v3 = 1;
                break;
            }
        }
        return new SourceValue(v3, a3);
    }
    
    @Override
    public SourceValue ternaryOperation(final AbstractInsnNode a1, final SourceValue a2, final SourceValue a3, final SourceValue a4) {
        return new SourceValue(1, a1);
    }
    
    @Override
    public SourceValue naryOperation(final AbstractInsnNode v2, final List<? extends SourceValue> v3) {
        final int v4 = v2.getOpcode();
        int v5 = 0;
        if (v4 == 197) {
            final int a1 = 1;
        }
        else {
            final String a2 = (v4 == 186) ? ((InvokeDynamicInsnNode)v2).desc : ((MethodInsnNode)v2).desc;
            v5 = Type.getReturnType(a2).getSize();
        }
        return new SourceValue(v5, v2);
    }
    
    @Override
    public void returnOperation(final AbstractInsnNode a1, final SourceValue a2, final SourceValue a3) {
    }
    
    @Override
    public SourceValue merge(final SourceValue v2, final SourceValue v3) {
        if (v2.insns instanceof SmallSet && v3.insns instanceof SmallSet) {
            final Set<AbstractInsnNode> a1 = ((SmallSet)v2.insns).union((SmallSet)v3.insns);
            if (a1 == v2.insns && v2.size == v3.size) {
                return v2;
            }
            return new SourceValue(Math.min(v2.size, v3.size), a1);
        }
        else {
            if (v2.size != v3.size || !v2.insns.containsAll(v3.insns)) {
                final HashSet<AbstractInsnNode> a2 = new HashSet<AbstractInsnNode>();
                a2.addAll((Collection<?>)v2.insns);
                a2.addAll((Collection<?>)v3.insns);
                return new SourceValue(Math.min(v2.size, v3.size), a2);
            }
            return v2;
        }
    }
    
    public /* bridge */ Value merge(final Value value, final Value value2) {
        return this.merge((SourceValue)value, (SourceValue)value2);
    }
    
    public /* bridge */ void returnOperation(final AbstractInsnNode a1, final Value value, final Value value2) throws AnalyzerException {
        this.returnOperation(a1, (SourceValue)value, (SourceValue)value2);
    }
    
    public /* bridge */ Value naryOperation(final AbstractInsnNode v2, final List v3) throws AnalyzerException {
        return this.naryOperation(v2, v3);
    }
    
    public /* bridge */ Value ternaryOperation(final AbstractInsnNode a1, final Value value, final Value value2, final Value value3) throws AnalyzerException {
        return this.ternaryOperation(a1, (SourceValue)value, (SourceValue)value2, (SourceValue)value3);
    }
    
    public /* bridge */ Value binaryOperation(final AbstractInsnNode a3, final Value value, final Value value2) throws AnalyzerException {
        return this.binaryOperation(a3, (SourceValue)value, (SourceValue)value2);
    }
    
    public /* bridge */ Value unaryOperation(final AbstractInsnNode v2, final Value value) throws AnalyzerException {
        return this.unaryOperation(v2, (SourceValue)value);
    }
    
    public /* bridge */ Value copyOperation(final AbstractInsnNode a1, final Value value) throws AnalyzerException {
        return this.copyOperation(a1, (SourceValue)value);
    }
    
    public /* bridge */ Value newOperation(final AbstractInsnNode v0) throws AnalyzerException {
        return this.newOperation(v0);
    }
    
    public /* bridge */ Value newValue(final Type a1) {
        return this.newValue(a1);
    }
}
