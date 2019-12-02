package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.invoke.util.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.injection.code.*;

public class ModifyConstantInjector extends RedirectInjector
{
    private static final int OPCODE_OFFSET = 6;
    
    public ModifyConstantInjector(final InjectionInfo a1) {
        super(a1, "@ModifyConstant");
    }
    
    @Override
    protected void inject(final Target a1, final InjectionNodes.InjectionNode a2) {
        if (!this.preInject(a2)) {
            return;
        }
        if (a2.isReplaced()) {
            throw new UnsupportedOperationException("Target failure for " + this.info);
        }
        final AbstractInsnNode v1 = a2.getCurrentTarget();
        if (v1 instanceof JumpInsnNode) {
            this.checkTargetModifiers(a1, false);
            this.injectExpandedConstantModifier(a1, (JumpInsnNode)v1);
            return;
        }
        if (Bytecode.isConstant(v1)) {
            this.checkTargetModifiers(a1, false);
            this.injectConstantModifier(a1, v1);
            return;
        }
        throw new InvalidInjectionException(this.info, this.annotationType + " annotation is targetting an invalid insn in " + a1 + " in " + this);
    }
    
    private void injectExpandedConstantModifier(final Target a1, final JumpInsnNode a2) {
        final int v1 = a2.getOpcode();
        if (v1 < 155 || v1 > 158) {
            throw new InvalidInjectionException(this.info, this.annotationType + " annotation selected an invalid opcode " + Bytecode.getOpcodeName(v1) + " in " + a1 + " in " + this);
        }
        final InsnList v2 = new InsnList();
        v2.add(new InsnNode(3));
        final AbstractInsnNode v3 = this.invokeConstantHandler(Type.getType("I"), a1, v2, v2);
        v2.add(new JumpInsnNode(v1 + 6, a2.label));
        a1.replaceNode(a2, v3, v2);
        a1.addToStack(1);
    }
    
    private void injectConstantModifier(final Target a1, final AbstractInsnNode a2) {
        final Type v1 = Bytecode.getConstantType(a2);
        if (v1.getSort() <= 5 && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
            this.checkNarrowing(a1, a2, v1);
        }
        final InsnList v2 = new InsnList();
        final InsnList v3 = new InsnList();
        final AbstractInsnNode v4 = this.invokeConstantHandler(v1, a1, v2, v3);
        a1.wrapNode(a2, v4, v2, v3);
    }
    
    private AbstractInsnNode invokeConstantHandler(final Type a1, final Target a2, final InsnList a3, final InsnList a4) {
        final String v1 = Bytecode.generateDescriptor(a1, a1);
        final boolean v2 = this.checkDescriptor(v1, a2, "getter");
        if (!this.isStatic) {
            a3.insert(new VarInsnNode(25, 0));
            a2.addToStack(1);
        }
        if (v2) {
            this.pushArgs(a2.arguments, a4, a2.getArgIndices(), 0, a2.arguments.length);
            a2.addToStack(Bytecode.getArgsSize(a2.arguments));
        }
        return this.invokeHandler(a4);
    }
    
    private void checkNarrowing(final Target v-6, final AbstractInsnNode v-5, final Type v-4) {
        final AbstractInsnNode popInsn = new InsnFinder().findPopInsn(v-6, v-5);
        if (popInsn == null) {
            return;
        }
        if (popInsn instanceof FieldInsnNode) {
            final FieldInsnNode a1 = (FieldInsnNode)popInsn;
            final Type a2 = Type.getType(a1.desc);
            this.checkNarrowing(v-6, v-5, v-4, a2, v-6.indexOf(popInsn), String.format("%s %s %s.%s", Bytecode.getOpcodeName(popInsn), SignaturePrinter.getTypeName(a2, false), a1.owner.replace('/', '.'), a1.name));
        }
        else if (popInsn.getOpcode() == 172) {
            this.checkNarrowing(v-6, v-5, v-4, v-6.returnType, v-6.indexOf(popInsn), "RETURN " + SignaturePrinter.getTypeName(v-6.returnType, false));
        }
        else if (popInsn.getOpcode() == 54) {
            final int var = ((VarInsnNode)popInsn).var;
            final LocalVariableNode localVariable = Locals.getLocalVariableAt(v-6.classNode, v-6.method, popInsn, var);
            if (localVariable != null && localVariable.desc != null) {
                final String a3 = (localVariable.name != null) ? localVariable.name : "unnamed";
                final Type v1 = Type.getType(localVariable.desc);
                this.checkNarrowing(v-6, v-5, v-4, v1, v-6.indexOf(popInsn), String.format("ISTORE[var=%d] %s %s", var, SignaturePrinter.getTypeName(v1, false), a3));
            }
        }
    }
    
    private void checkNarrowing(final Target a6, final AbstractInsnNode v1, final Type v2, final Type v3, final int v4, final String v5) {
        final int v6 = v2.getSort();
        final int v7 = v3.getSort();
        if (v7 < v6) {
            final String a7 = SignaturePrinter.getTypeName(v2, false);
            final String a8 = SignaturePrinter.getTypeName(v3, false);
            final String a9 = (v7 == 1) ? ". Implicit conversion to <boolean> can cause nondeterministic (JVM-specific) behaviour!" : "";
            final Level a10 = (v7 == 1) ? Level.ERROR : Level.WARN;
            Injector.logger.log(a10, "Narrowing conversion of <{}> to <{}> in {} target {} at opcode {} ({}){}", new Object[] { a7, a8, this.info, a6, v4, v5, a9 });
        }
    }
}
