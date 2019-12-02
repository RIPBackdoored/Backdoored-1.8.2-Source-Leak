package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

public class Target implements Comparable<Target>, Iterable<AbstractInsnNode>
{
    public final ClassNode classNode;
    public final MethodNode method;
    public final InsnList insns;
    public final boolean isStatic;
    public final boolean isCtor;
    public final Type[] arguments;
    public final Type returnType;
    private final int maxStack;
    private final int maxLocals;
    private final InjectionNodes injectionNodes;
    private String callbackInfoClass;
    private String callbackDescriptor;
    private int[] argIndices;
    private List<Integer> argMapVars;
    private LabelNode start;
    private LabelNode end;
    
    public Target(final ClassNode a1, final MethodNode a2) {
        super();
        this.injectionNodes = new InjectionNodes();
        this.classNode = a1;
        this.method = a2;
        this.insns = a2.instructions;
        this.isStatic = Bytecode.methodIsStatic(a2);
        this.isCtor = a2.name.equals("<init>");
        this.arguments = Type.getArgumentTypes(a2.desc);
        this.returnType = Type.getReturnType(a2.desc);
        this.maxStack = a2.maxStack;
        this.maxLocals = a2.maxLocals;
    }
    
    public InjectionNodes.InjectionNode addInjectionNode(final AbstractInsnNode a1) {
        return this.injectionNodes.add(a1);
    }
    
    public InjectionNodes.InjectionNode getInjectionNode(final AbstractInsnNode a1) {
        return this.injectionNodes.get(a1);
    }
    
    public int getMaxLocals() {
        return this.maxLocals;
    }
    
    public int getMaxStack() {
        return this.maxStack;
    }
    
    public int getCurrentMaxLocals() {
        return this.method.maxLocals;
    }
    
    public int getCurrentMaxStack() {
        return this.method.maxStack;
    }
    
    public int allocateLocal() {
        return this.allocateLocals(1);
    }
    
    public int allocateLocals(final int a1) {
        final int v1 = this.method.maxLocals;
        final MethodNode method = this.method;
        method.maxLocals += a1;
        return v1;
    }
    
    public void addToLocals(final int a1) {
        this.setMaxLocals(this.maxLocals + a1);
    }
    
    public void setMaxLocals(final int a1) {
        if (a1 > this.method.maxLocals) {
            this.method.maxLocals = a1;
        }
    }
    
    public void addToStack(final int a1) {
        this.setMaxStack(this.maxStack + a1);
    }
    
    public void setMaxStack(final int a1) {
        if (a1 > this.method.maxStack) {
            this.method.maxStack = a1;
        }
    }
    
    public int[] generateArgMap(final Type[] v-3, final int v-2) {
        if (this.argMapVars == null) {
            this.argMapVars = new ArrayList<Integer>();
        }
        final int[] array = new int[v-3.length];
        int a2 = v-2;
        int v1 = 0;
        while (a2 < v-3.length) {
            final int a3 = v-3[a2].getSize();
            array[a2] = this.allocateArgMapLocal(v1, a3);
            v1 += a3;
            ++a2;
        }
        return array;
    }
    
    private int allocateArgMapLocal(final int v-2, final int v-1) {
        if (v-2 >= this.argMapVars.size()) {
            final int a2 = this.allocateLocals(v-1);
            for (int a3 = 0; a3 < v-1; ++a3) {
                this.argMapVars.add(a2 + a3);
            }
            return a2;
        }
        final int v0 = this.argMapVars.get(v-2);
        if (v-1 <= 1 || v-2 + v-1 <= this.argMapVars.size()) {
            return v0;
        }
        final int v2 = this.allocateLocals(1);
        if (v2 == v0 + 1) {
            this.argMapVars.add(v2);
            return v0;
        }
        this.argMapVars.set(v-2, v2);
        this.argMapVars.add(this.allocateLocals(1));
        return v2;
    }
    
    public int[] getArgIndices() {
        if (this.argIndices == null) {
            this.argIndices = this.calcArgIndices(this.isStatic ? 0 : 1);
        }
        return this.argIndices;
    }
    
    private int[] calcArgIndices(int v2) {
        final int[] v3 = new int[this.arguments.length];
        for (int a1 = 0; a1 < this.arguments.length; ++a1) {
            v3[a1] = v2;
            v2 += this.arguments[a1].getSize();
        }
        return v3;
    }
    
    public String getCallbackInfoClass() {
        if (this.callbackInfoClass == null) {
            this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
        }
        return this.callbackInfoClass;
    }
    
    public String getSimpleCallbackDescriptor() {
        return String.format("(L%s;)V", this.getCallbackInfoClass());
    }
    
    public String getCallbackDescriptor(final Type[] a1, final Type[] a2) {
        return this.getCallbackDescriptor(false, a1, a2, 0, 32767);
    }
    
    public String getCallbackDescriptor(final boolean a3, final Type[] a4, final Type[] a5, final int v1, int v2) {
        if (this.callbackDescriptor == null) {
            this.callbackDescriptor = String.format("(%sL%s;)V", this.method.desc.substring(1, this.method.desc.indexOf(41)), this.getCallbackInfoClass());
        }
        if (!a3 || a4 == null) {
            return this.callbackDescriptor;
        }
        final StringBuilder v3 = new StringBuilder(this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(41)));
        for (int a6 = v1; a6 < a4.length && v2 > 0; ++a6) {
            if (a4[a6] != null) {
                v3.append(a4[a6].getDescriptor());
                --v2;
            }
        }
        return v3.append(")V").toString();
    }
    
    @Override
    public String toString() {
        return String.format("%s::%s%s", this.classNode.name, this.method.name, this.method.desc);
    }
    
    @Override
    public int compareTo(final Target a1) {
        if (a1 == null) {
            return 0;
        }
        return this.toString().compareTo(a1.toString());
    }
    
    public int indexOf(final InjectionNodes.InjectionNode a1) {
        return this.insns.indexOf(a1.getCurrentTarget());
    }
    
    public int indexOf(final AbstractInsnNode a1) {
        return this.insns.indexOf(a1);
    }
    
    public AbstractInsnNode get(final int a1) {
        return this.insns.get(a1);
    }
    
    @Override
    public Iterator<AbstractInsnNode> iterator() {
        return this.insns.iterator();
    }
    
    public MethodInsnNode findInitNodeFor(final TypeInsnNode v-2) {
        final int index = this.indexOf(v-2);
        final Iterator<AbstractInsnNode> v0 = this.insns.iterator(index);
        while (v0.hasNext()) {
            final AbstractInsnNode v2 = v0.next();
            if (v2 instanceof MethodInsnNode && v2.getOpcode() == 183) {
                final MethodInsnNode a1 = (MethodInsnNode)v2;
                if ("<init>".equals(a1.name) && a1.owner.equals(v-2.desc)) {
                    return a1;
                }
                continue;
            }
        }
        return null;
    }
    
    public MethodInsnNode findSuperInitNode() {
        if (!this.isCtor) {
            return null;
        }
        return Bytecode.findSuperInit(this.method, ClassInfo.forName(this.classNode.name).getSuperName());
    }
    
    public void insertBefore(final InjectionNodes.InjectionNode a1, final InsnList a2) {
        this.insns.insertBefore(a1.getCurrentTarget(), a2);
    }
    
    public void insertBefore(final AbstractInsnNode a1, final InsnList a2) {
        this.insns.insertBefore(a1, a2);
    }
    
    public void replaceNode(final AbstractInsnNode a1, final AbstractInsnNode a2) {
        this.insns.insertBefore(a1, a2);
        this.insns.remove(a1);
        this.injectionNodes.replace(a1, a2);
    }
    
    public void replaceNode(final AbstractInsnNode a1, final AbstractInsnNode a2, final InsnList a3) {
        this.insns.insertBefore(a1, a3);
        this.insns.remove(a1);
        this.injectionNodes.replace(a1, a2);
    }
    
    public void wrapNode(final AbstractInsnNode a1, final AbstractInsnNode a2, final InsnList a3, final InsnList a4) {
        this.insns.insertBefore(a1, a3);
        this.insns.insert(a1, a4);
        this.injectionNodes.replace(a1, a2);
    }
    
    public void replaceNode(final AbstractInsnNode a1, final InsnList a2) {
        this.insns.insertBefore(a1, a2);
        this.removeNode(a1);
    }
    
    public void removeNode(final AbstractInsnNode a1) {
        this.insns.remove(a1);
        this.injectionNodes.remove(a1);
    }
    
    public void addLocalVariable(final int a1, final String a2, final String a3) {
        if (this.start == null) {
            this.start = new LabelNode(new Label());
            this.end = new LabelNode(new Label());
            this.insns.insert(this.start);
            this.insns.add(this.end);
        }
        this.addLocalVariable(a1, a2, a3, this.start, this.end);
    }
    
    private void addLocalVariable(final int a1, final String a2, final String a3, final LabelNode a4, final LabelNode a5) {
        if (this.method.localVariables == null) {
            this.method.localVariables = new ArrayList<LocalVariableNode>();
        }
        this.method.localVariables.add(new LocalVariableNode(a2, a3, null, a4, a5, a1));
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((Target)o);
    }
}
