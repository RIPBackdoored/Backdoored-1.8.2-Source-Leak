package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class Frame<V extends java.lang.Object>
{
    private V returnValue;
    private V[] values;
    private int locals;
    private int top;
    
    public Frame(final int a1, final int a2) {
        super();
        this.values = new Value[a1 + a2];
        this.locals = a1;
    }
    
    public Frame(final Frame<? extends V> a1) {
        this(a1.locals, a1.values.length - a1.locals);
        this.init(a1);
    }
    
    public Frame<V> init(final Frame<? extends V> a1) {
        this.returnValue = a1.returnValue;
        System.arraycopy(a1.values, 0, this.values, 0, this.values.length);
        this.top = a1.top;
        return this;
    }
    
    public void setReturn(final V a1) {
        this.returnValue = (Value)a1;
    }
    
    public int getLocals() {
        return this.locals;
    }
    
    public int getMaxStackSize() {
        return this.values.length - this.locals;
    }
    
    public V getLocal(final int a1) throws IndexOutOfBoundsException {
        if (a1 >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable");
        }
        return (V)this.values[a1];
    }
    
    public void setLocal(final int a1, final V a2) throws IndexOutOfBoundsException {
        if (a1 >= this.locals) {
            throw new IndexOutOfBoundsException("Trying to access an inexistant local variable " + a1);
        }
        this.values[a1] = (Value)a2;
    }
    
    public int getStackSize() {
        return this.top;
    }
    
    public V getStack(final int a1) throws IndexOutOfBoundsException {
        return (V)this.values[a1 + this.locals];
    }
    
    public void clearStack() {
        this.top = 0;
    }
    
    public V pop() throws IndexOutOfBoundsException {
        if (this.top == 0) {
            throw new IndexOutOfBoundsException("Cannot pop operand off an empty stack.");
        }
        final Value[] values = this.values;
        final int top = this.top - 1;
        this.top = top;
        return (V)values[top + this.locals];
    }
    
    public void push(final V a1) throws IndexOutOfBoundsException {
        if (this.top + this.locals >= this.values.length) {
            throw new IndexOutOfBoundsException("Insufficient maximum stack size.");
        }
        this.values[this.top++ + this.locals] = (Value)a1;
    }
    
    public void execute(final AbstractInsnNode v-4, final Interpreter<V> v-3) throws AnalyzerException {
        switch (v-4.getOpcode()) {
            case 0: {
                break;
            }
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18: {
                this.push(v-3.newOperation(v-4));
                break;
            }
            case 21:
            case 22:
            case 23:
            case 24:
            case 25: {
                this.push(v-3.copyOperation(v-4, this.getLocal(((VarInsnNode)v-4).var)));
                break;
            }
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53: {
                final V a2 = (V)this.pop();
                final V a3 = (V)this.pop();
                this.push(v-3.binaryOperation(v-4, (Value)a3, (Value)a2));
                break;
            }
            case 54:
            case 55:
            case 56:
            case 57:
            case 58: {
                final V v9 = (V)v-3.copyOperation(v-4, this.pop());
                final int v0 = ((VarInsnNode)v-4).var;
                this.setLocal(v0, (Value)v9);
                if (((Value)v9).getSize() == 2) {
                    this.setLocal(v0 + 1, v-3.newValue((Type)null));
                }
                if (v0 > 0) {
                    final Value v2 = this.getLocal(v0 - 1);
                    if (v2 != null && v2.getSize() == 2) {
                        this.setLocal(v0 - 1, v-3.newValue((Type)null));
                    }
                    break;
                }
                break;
            }
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86: {
                final V v3 = (V)this.pop();
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                v-3.ternaryOperation(v-4, (Value)v9, (Value)v10, (Value)v3);
                break;
            }
            case 87: {
                if (this.pop().getSize() == 2) {
                    throw new AnalyzerException(v-4, "Illegal use of POP");
                }
                break;
            }
            case 88: {
                if (this.pop().getSize() == 1 && this.pop().getSize() != 1) {
                    throw new AnalyzerException(v-4, "Illegal use of POP2");
                }
                break;
            }
            case 89: {
                final V v9 = (V)this.pop();
                if (((Value)v9).getSize() != 1) {
                    throw new AnalyzerException(v-4, "Illegal use of DUP");
                }
                this.push((Value)v9);
                this.push(v-3.copyOperation(v-4, (Value)v9));
                break;
            }
            case 90: {
                final V v9 = (V)this.pop();
                final V v10 = (V)this.pop();
                if (((Value)v9).getSize() != 1 || ((Value)v10).getSize() != 1) {
                    throw new AnalyzerException(v-4, "Illegal use of DUP_X1");
                }
                this.push(v-3.copyOperation(v-4, (Value)v9));
                this.push((Value)v10);
                this.push((Value)v9);
                break;
            }
            case 91: {
                final V v9 = (V)this.pop();
                if (((Value)v9).getSize() == 1) {
                    final V v10 = (V)this.pop();
                    if (((Value)v10).getSize() != 1) {
                        this.push(v-3.copyOperation(v-4, (Value)v9));
                        this.push((Value)v10);
                        this.push((Value)v9);
                        break;
                    }
                    final V v3 = (V)this.pop();
                    if (((Value)v3).getSize() == 1) {
                        this.push(v-3.copyOperation(v-4, (Value)v9));
                        this.push((Value)v3);
                        this.push((Value)v10);
                        this.push((Value)v9);
                        break;
                    }
                }
                throw new AnalyzerException(v-4, "Illegal use of DUP_X2");
            }
            case 92: {
                final V v9 = (V)this.pop();
                if (((Value)v9).getSize() != 1) {
                    this.push((Value)v9);
                    this.push(v-3.copyOperation(v-4, (Value)v9));
                    break;
                }
                final V v10 = (V)this.pop();
                if (((Value)v10).getSize() == 1) {
                    this.push((Value)v10);
                    this.push((Value)v9);
                    this.push(v-3.copyOperation(v-4, (Value)v10));
                    this.push(v-3.copyOperation(v-4, (Value)v9));
                    break;
                }
                throw new AnalyzerException(v-4, "Illegal use of DUP2");
            }
            case 93: {
                final V v9 = (V)this.pop();
                if (((Value)v9).getSize() == 1) {
                    final V v10 = (V)this.pop();
                    if (((Value)v10).getSize() == 1) {
                        final V v3 = (V)this.pop();
                        if (((Value)v3).getSize() == 1) {
                            this.push(v-3.copyOperation(v-4, (Value)v10));
                            this.push(v-3.copyOperation(v-4, (Value)v9));
                            this.push((Value)v3);
                            this.push((Value)v10);
                            this.push((Value)v9);
                            break;
                        }
                    }
                }
                else {
                    final V v10 = (V)this.pop();
                    if (((Value)v10).getSize() == 1) {
                        this.push(v-3.copyOperation(v-4, (Value)v9));
                        this.push((Value)v10);
                        this.push((Value)v9);
                        break;
                    }
                }
                throw new AnalyzerException(v-4, "Illegal use of DUP2_X1");
            }
            case 94: {
                final V v9 = (V)this.pop();
                if (((Value)v9).getSize() == 1) {
                    final V v10 = (V)this.pop();
                    if (((Value)v10).getSize() == 1) {
                        final V v3 = (V)this.pop();
                        if (((Value)v3).getSize() != 1) {
                            this.push(v-3.copyOperation(v-4, (Value)v10));
                            this.push(v-3.copyOperation(v-4, (Value)v9));
                            this.push((Value)v3);
                            this.push((Value)v10);
                            this.push((Value)v9);
                            break;
                        }
                        final V v4 = (V)this.pop();
                        if (((Value)v4).getSize() == 1) {
                            this.push(v-3.copyOperation(v-4, (Value)v10));
                            this.push(v-3.copyOperation(v-4, (Value)v9));
                            this.push((Value)v4);
                            this.push((Value)v3);
                            this.push((Value)v10);
                            this.push((Value)v9);
                            break;
                        }
                    }
                }
                else {
                    final V v10 = (V)this.pop();
                    if (((Value)v10).getSize() != 1) {
                        this.push(v-3.copyOperation(v-4, (Value)v9));
                        this.push((Value)v10);
                        this.push((Value)v9);
                        break;
                    }
                    final V v3 = (V)this.pop();
                    if (((Value)v3).getSize() == 1) {
                        this.push(v-3.copyOperation(v-4, (Value)v9));
                        this.push((Value)v3);
                        this.push((Value)v10);
                        this.push((Value)v9);
                        break;
                    }
                }
                throw new AnalyzerException(v-4, "Illegal use of DUP2_X2");
            }
            case 95: {
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                if (((Value)v9).getSize() != 1 || ((Value)v10).getSize() != 1) {
                    throw new AnalyzerException(v-4, "Illegal use of SWAP");
                }
                this.push(v-3.copyOperation(v-4, (Value)v10));
                this.push(v-3.copyOperation(v-4, (Value)v9));
                break;
            }
            case 96:
            case 97:
            case 98:
            case 99:
            case 100:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115: {
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                this.push(v-3.binaryOperation(v-4, (Value)v9, (Value)v10));
                break;
            }
            case 116:
            case 117:
            case 118:
            case 119: {
                this.push(v-3.unaryOperation(v-4, this.pop()));
                break;
            }
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131: {
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                this.push(v-3.binaryOperation(v-4, (Value)v9, (Value)v10));
                break;
            }
            case 132: {
                final int v0 = ((IincInsnNode)v-4).var;
                this.setLocal(v0, v-3.unaryOperation(v-4, this.getLocal(v0)));
                break;
            }
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147: {
                this.push(v-3.unaryOperation(v-4, this.pop()));
                break;
            }
            case 148:
            case 149:
            case 150:
            case 151:
            case 152: {
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                this.push(v-3.binaryOperation(v-4, (Value)v9, (Value)v10));
                break;
            }
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158: {
                v-3.unaryOperation(v-4, this.pop());
                break;
            }
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166: {
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                v-3.binaryOperation(v-4, (Value)v9, (Value)v10);
                break;
            }
            case 167: {
                break;
            }
            case 168: {
                this.push(v-3.newOperation(v-4));
                break;
            }
            case 169: {
                break;
            }
            case 170:
            case 171: {
                v-3.unaryOperation(v-4, this.pop());
                break;
            }
            case 172:
            case 173:
            case 174:
            case 175:
            case 176: {
                final V v9 = (V)this.pop();
                v-3.unaryOperation(v-4, (Value)v9);
                v-3.returnOperation(v-4, (Value)v9, this.returnValue);
                break;
            }
            case 177: {
                if (this.returnValue != null) {
                    throw new AnalyzerException(v-4, "Incompatible return type");
                }
                break;
            }
            case 178: {
                this.push(v-3.newOperation(v-4));
                break;
            }
            case 179: {
                v-3.unaryOperation(v-4, this.pop());
                break;
            }
            case 180: {
                this.push(v-3.unaryOperation(v-4, this.pop()));
                break;
            }
            case 181: {
                final V v10 = (V)this.pop();
                final V v9 = (V)this.pop();
                v-3.binaryOperation(v-4, (Value)v9, (Value)v10);
                break;
            }
            case 182:
            case 183:
            case 184:
            case 185: {
                final List<V> v5 = new ArrayList<V>();
                final String v6 = ((MethodInsnNode)v-4).desc;
                for (int v7 = Type.getArgumentTypes(v6).length; v7 > 0; --v7) {
                    v5.add(0, (V)this.pop());
                }
                if (v-4.getOpcode() != 184) {
                    v5.add(0, (V)this.pop());
                }
                if (Type.getReturnType(v6) == Type.VOID_TYPE) {
                    v-3.naryOperation(v-4, (List)v5);
                    break;
                }
                this.push(v-3.naryOperation(v-4, (List)v5));
                break;
            }
            case 186: {
                final List<V> v5 = new ArrayList<V>();
                final String v6 = ((InvokeDynamicInsnNode)v-4).desc;
                for (int v7 = Type.getArgumentTypes(v6).length; v7 > 0; --v7) {
                    v5.add(0, (V)this.pop());
                }
                if (Type.getReturnType(v6) == Type.VOID_TYPE) {
                    v-3.naryOperation(v-4, (List)v5);
                    break;
                }
                this.push(v-3.naryOperation(v-4, (List)v5));
                break;
            }
            case 187: {
                this.push(v-3.newOperation(v-4));
                break;
            }
            case 188:
            case 189:
            case 190: {
                this.push(v-3.unaryOperation(v-4, this.pop()));
                break;
            }
            case 191: {
                v-3.unaryOperation(v-4, this.pop());
                break;
            }
            case 192:
            case 193: {
                this.push(v-3.unaryOperation(v-4, this.pop()));
                break;
            }
            case 194:
            case 195: {
                v-3.unaryOperation(v-4, this.pop());
                break;
            }
            case 197: {
                final List<V> v5 = new ArrayList<V>();
                for (int v8 = ((MultiANewArrayInsnNode)v-4).dims; v8 > 0; --v8) {
                    v5.add(0, (V)this.pop());
                }
                this.push(v-3.naryOperation(v-4, (List)v5));
                break;
            }
            case 198:
            case 199: {
                v-3.unaryOperation(v-4, this.pop());
                break;
            }
            default: {
                throw new RuntimeException("Illegal opcode " + v-4.getOpcode());
            }
        }
    }
    
    public boolean merge(final Frame<? extends V> v2, final Interpreter<V> v3) throws AnalyzerException {
        if (this.top != v2.top) {
            throw new AnalyzerException(null, "Incompatible stack heights");
        }
        boolean v4 = false;
        for (int a2 = 0; a2 < this.locals + this.top; ++a2) {
            final V a3 = (V)v3.merge(this.values[a2], v2.values[a2]);
            if (!a3.equals((Object)this.values[a2])) {
                this.values[a2] = (Value)a3;
                v4 = true;
            }
        }
        return v4;
    }
    
    public boolean merge(final Frame<? extends V> v1, final boolean[] v2) {
        boolean v3 = false;
        for (int a1 = 0; a1 < this.locals; ++a1) {
            if (!v2[a1] && !this.values[a1].equals(v1.values[a1])) {
                this.values[a1] = v1.values[a1];
                v3 = true;
            }
        }
        return v3;
    }
    
    @Override
    public String toString() {
        final StringBuilder v0 = new StringBuilder();
        for (int v2 = 0; v2 < this.getLocals(); ++v2) {
            v0.append(this.getLocal(v2));
        }
        v0.append(' ');
        for (int v2 = 0; v2 < this.getStackSize(); ++v2) {
            v0.append(this.getStack(v2).toString());
        }
        return v0.toString();
    }
}
