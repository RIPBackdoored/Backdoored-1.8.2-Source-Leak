package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class Analyzer<V extends java.lang.Object> implements Opcodes
{
    private final Interpreter<V> interpreter;
    private int n;
    private InsnList insns;
    private List<TryCatchBlockNode>[] handlers;
    private Frame<V>[] frames;
    private Subroutine[] subroutines;
    private boolean[] queued;
    private int[] queue;
    private int top;
    
    public Analyzer(final Interpreter<V> a1) {
        super();
        this.interpreter = a1;
    }
    
    public Frame<V>[] analyze(final String v-2, final MethodNode v-1) throws AnalyzerException {
        if ((v-1.access & 0x500) != 0x0) {
            return this.frames = (Frame<V>[])new Frame[0];
        }
        this.n = v-1.instructions.size();
        this.insns = v-1.instructions;
        this.handlers = (List<TryCatchBlockNode>[])new List[this.n];
        this.frames = (Frame<V>[])new Frame[this.n];
        this.subroutines = new Subroutine[this.n];
        this.queued = new boolean[this.n];
        this.queue = new int[this.n];
        this.top = 0;
        for (int v0 = 0; v0 < v-1.tryCatchBlocks.size(); ++v0) {
            final TryCatchBlockNode v2 = v-1.tryCatchBlocks.get(v0);
            final int v3 = this.insns.indexOf(v2.start);
            for (int v4 = this.insns.indexOf(v2.end), a2 = v3; a2 < v4; ++a2) {
                List<TryCatchBlockNode> a3 = this.handlers[a2];
                if (a3 == null) {
                    a3 = new ArrayList<TryCatchBlockNode>();
                    this.handlers[a2] = a3;
                }
                a3.add(v2);
            }
        }
        final Subroutine v5 = new Subroutine(null, v-1.maxLocals, null);
        final List<AbstractInsnNode> v6 = new ArrayList<AbstractInsnNode>();
        final Map<LabelNode, Subroutine> v7 = new HashMap<LabelNode, Subroutine>();
        this.findSubroutine(0, v5, v6);
        while (!v6.isEmpty()) {
            final JumpInsnNode v8 = v6.remove(0);
            Subroutine v9 = v7.get(v8.label);
            if (v9 == null) {
                v9 = new Subroutine(v8.label, v-1.maxLocals, v8);
                v7.put(v8.label, v9);
                this.findSubroutine(this.insns.indexOf(v8.label), v9, v6);
            }
            else {
                v9.callers.add(v8);
            }
        }
        for (int v4 = 0; v4 < this.n; ++v4) {
            if (this.subroutines[v4] != null && this.subroutines[v4].start == null) {
                this.subroutines[v4] = null;
            }
        }
        final Frame<V> v10 = this.newFrame(v-1.maxLocals, v-1.maxStack);
        final Frame<V> v11 = this.newFrame(v-1.maxLocals, v-1.maxStack);
        v10.setReturn(this.interpreter.newValue(Type.getReturnType(v-1.desc)));
        final Type[] v12 = Type.getArgumentTypes(v-1.desc);
        int v13 = 0;
        if ((v-1.access & 0x8) == 0x0) {
            final Type v14 = Type.getObjectType(v-2);
            v10.setLocal(v13++, this.interpreter.newValue(v14));
        }
        for (int v15 = 0; v15 < v12.length; ++v15) {
            v10.setLocal(v13++, this.interpreter.newValue(v12[v15]));
            if (v12[v15].getSize() == 2) {
                v10.setLocal(v13++, this.interpreter.newValue((Type)null));
            }
        }
        while (v13 < v-1.maxLocals) {
            v10.setLocal(v13++, this.interpreter.newValue((Type)null));
        }
        this.merge(0, v10, null);
        this.init(v-2, v-1);
        while (this.top > 0) {
            final int[] queue = this.queue;
            final int top = this.top - 1;
            this.top = top;
            final int v15 = queue[top];
            final Frame<V> v16 = this.frames[v15];
            Subroutine v17 = this.subroutines[v15];
            this.queued[v15] = false;
            AbstractInsnNode v18 = null;
            try {
                v18 = v-1.instructions.get(v15);
                final int v19 = v18.getOpcode();
                final int v20 = v18.getType();
                if (v20 == 8 || v20 == 15 || v20 == 14) {
                    this.merge(v15 + 1, v16, v17);
                    this.newControlFlowEdge(v15, v15 + 1);
                }
                else {
                    v10.init((Frame<? extends V>)v16).execute(v18, this.interpreter);
                    v17 = ((v17 == null) ? null : v17.copy());
                    if (v18 instanceof JumpInsnNode) {
                        final JumpInsnNode v21 = (JumpInsnNode)v18;
                        if (v19 != 167 && v19 != 168) {
                            this.merge(v15 + 1, v10, v17);
                            this.newControlFlowEdge(v15, v15 + 1);
                        }
                        final int v22 = this.insns.indexOf(v21.label);
                        if (v19 == 168) {
                            this.merge(v22, v10, new Subroutine(v21.label, v-1.maxLocals, v21));
                        }
                        else {
                            this.merge(v22, v10, v17);
                        }
                        this.newControlFlowEdge(v15, v22);
                    }
                    else if (v18 instanceof LookupSwitchInsnNode) {
                        final LookupSwitchInsnNode v23 = (LookupSwitchInsnNode)v18;
                        int v22 = this.insns.indexOf(v23.dflt);
                        this.merge(v22, v10, v17);
                        this.newControlFlowEdge(v15, v22);
                        for (int v24 = 0; v24 < v23.labels.size(); ++v24) {
                            final LabelNode v25 = v23.labels.get(v24);
                            v22 = this.insns.indexOf(v25);
                            this.merge(v22, v10, v17);
                            this.newControlFlowEdge(v15, v22);
                        }
                    }
                    else if (v18 instanceof TableSwitchInsnNode) {
                        final TableSwitchInsnNode v26 = (TableSwitchInsnNode)v18;
                        int v22 = this.insns.indexOf(v26.dflt);
                        this.merge(v22, v10, v17);
                        this.newControlFlowEdge(v15, v22);
                        for (int v24 = 0; v24 < v26.labels.size(); ++v24) {
                            final LabelNode v25 = v26.labels.get(v24);
                            v22 = this.insns.indexOf(v25);
                            this.merge(v22, v10, v17);
                            this.newControlFlowEdge(v15, v22);
                        }
                    }
                    else if (v19 == 169) {
                        if (v17 == null) {
                            throw new AnalyzerException(v18, "RET instruction outside of a sub routine");
                        }
                        for (int v27 = 0; v27 < v17.callers.size(); ++v27) {
                            final JumpInsnNode v28 = v17.callers.get(v27);
                            final int v24 = this.insns.indexOf(v28);
                            if (this.frames[v24] != null) {
                                this.merge(v24 + 1, this.frames[v24], v10, this.subroutines[v24], v17.access);
                                this.newControlFlowEdge(v15, v24 + 1);
                            }
                        }
                    }
                    else if (v19 != 191 && (v19 < 172 || v19 > 177)) {
                        if (v17 != null) {
                            if (v18 instanceof VarInsnNode) {
                                final int v27 = ((VarInsnNode)v18).var;
                                v17.access[v27] = true;
                                if (v19 == 22 || v19 == 24 || v19 == 55 || v19 == 57) {
                                    v17.access[v27 + 1] = true;
                                }
                            }
                            else if (v18 instanceof IincInsnNode) {
                                final int v27 = ((IincInsnNode)v18).var;
                                v17.access[v27] = true;
                            }
                        }
                        this.merge(v15 + 1, v10, v17);
                        this.newControlFlowEdge(v15, v15 + 1);
                    }
                }
                final List<TryCatchBlockNode> v29 = this.handlers[v15];
                if (v29 == null) {
                    continue;
                }
                for (int v22 = 0; v22 < v29.size(); ++v22) {
                    final TryCatchBlockNode v30 = v29.get(v22);
                    Type v31;
                    if (v30.type == null) {
                        v31 = Type.getObjectType("java/lang/Throwable");
                    }
                    else {
                        v31 = Type.getObjectType(v30.type);
                    }
                    final int v32 = this.insns.indexOf(v30.handler);
                    if (this.newControlFlowExceptionEdge(v15, v30)) {
                        v11.init((Frame<? extends V>)v16);
                        v11.clearStack();
                        v11.push(this.interpreter.newValue(v31));
                        this.merge(v32, v11, v17);
                    }
                }
            }
            catch (AnalyzerException v33) {
                throw new AnalyzerException(v33.node, "Error at instruction " + v15 + ": " + v33.getMessage(), v33);
            }
            catch (Exception v34) {
                throw new AnalyzerException(v18, "Error at instruction " + v15 + ": " + v34.getMessage(), v34);
            }
        }
        return this.frames;
    }
    
    private void findSubroutine(int v-3, final Subroutine v-2, final List<AbstractInsnNode> v-1) throws AnalyzerException {
        while (v-3 >= 0 && v-3 < this.n) {
            if (this.subroutines[v-3] != null) {
                return;
            }
            this.subroutines[v-3] = v-2.copy();
            final AbstractInsnNode v0 = this.insns.get(v-3);
            if (v0 instanceof JumpInsnNode) {
                if (v0.getOpcode() == 168) {
                    v-1.add(v0);
                }
                else {
                    final JumpInsnNode a1 = (JumpInsnNode)v0;
                    this.findSubroutine(this.insns.indexOf(a1.label), v-2, v-1);
                }
            }
            else if (v0 instanceof TableSwitchInsnNode) {
                final TableSwitchInsnNode v2 = (TableSwitchInsnNode)v0;
                this.findSubroutine(this.insns.indexOf(v2.dflt), v-2, v-1);
                for (int a2 = v2.labels.size() - 1; a2 >= 0; --a2) {
                    final LabelNode a3 = v2.labels.get(a2);
                    this.findSubroutine(this.insns.indexOf(a3), v-2, v-1);
                }
            }
            else if (v0 instanceof LookupSwitchInsnNode) {
                final LookupSwitchInsnNode v3 = (LookupSwitchInsnNode)v0;
                this.findSubroutine(this.insns.indexOf(v3.dflt), v-2, v-1);
                for (int v4 = v3.labels.size() - 1; v4 >= 0; --v4) {
                    final LabelNode v5 = v3.labels.get(v4);
                    this.findSubroutine(this.insns.indexOf(v5), v-2, v-1);
                }
            }
            final List<TryCatchBlockNode> v6 = this.handlers[v-3];
            if (v6 != null) {
                for (int v4 = 0; v4 < v6.size(); ++v4) {
                    final TryCatchBlockNode v7 = v6.get(v4);
                    this.findSubroutine(this.insns.indexOf(v7.handler), v-2, v-1);
                }
            }
            switch (v0.getOpcode()) {
                case 167:
                case 169:
                case 170:
                case 171:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 191: {
                    return;
                }
                default: {
                    ++v-3;
                    continue;
                }
            }
        }
        throw new AnalyzerException(null, "Execution can fall off end of the code");
    }
    
    public Frame<V>[] getFrames() {
        return this.frames;
    }
    
    public List<TryCatchBlockNode> getHandlers(final int a1) {
        return this.handlers[a1];
    }
    
    protected void init(final String a1, final MethodNode a2) throws AnalyzerException {
    }
    
    protected Frame<V> newFrame(final int a1, final int a2) {
        return new Frame<V>(a1, a2);
    }
    
    protected Frame<V> newFrame(final Frame<? extends V> a1) {
        return new Frame<V>(a1);
    }
    
    protected void newControlFlowEdge(final int a1, final int a2) {
    }
    
    protected boolean newControlFlowExceptionEdge(final int a1, final int a2) {
        return true;
    }
    
    protected boolean newControlFlowExceptionEdge(final int a1, final TryCatchBlockNode a2) {
        return this.newControlFlowExceptionEdge(a1, this.insns.indexOf(a2.handler));
    }
    
    private void merge(final int a3, final Frame<V> v1, final Subroutine v2) throws AnalyzerException {
        final Frame<V> v3 = this.frames[a3];
        final Subroutine v4 = this.subroutines[a3];
        boolean v5 = false;
        if (v3 == null) {
            this.frames[a3] = this.newFrame((Frame<? extends V>)v1);
            final boolean a4 = true;
        }
        else {
            v5 = v3.merge((Frame<? extends V>)v1, this.interpreter);
        }
        if (v4 == null) {
            if (v2 != null) {
                this.subroutines[a3] = v2.copy();
                v5 = true;
            }
        }
        else if (v2 != null) {
            v5 |= v4.merge(v2);
        }
        if (v5 && !this.queued[a3]) {
            this.queued[a3] = true;
            this.queue[this.top++] = a3;
        }
    }
    
    private void merge(final int a3, final Frame<V> a4, final Frame<V> a5, final Subroutine v1, final boolean[] v2) throws AnalyzerException {
        final Frame<V> v3 = this.frames[a3];
        final Subroutine v4 = this.subroutines[a3];
        a5.merge((Frame<? extends V>)a4, v2);
        boolean v5 = false;
        if (v3 == null) {
            this.frames[a3] = this.newFrame((Frame<? extends V>)a5);
            final boolean a6 = true;
        }
        else {
            v5 = v3.merge((Frame<? extends V>)a5, this.interpreter);
        }
        if (v4 != null && v1 != null) {
            v5 |= v4.merge(v1);
        }
        if (v5 && !this.queued[a3]) {
            this.queued[a3] = true;
            this.queue[this.top++] = a3;
        }
    }
}
