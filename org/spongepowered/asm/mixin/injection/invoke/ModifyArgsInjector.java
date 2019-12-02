package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;

public class ModifyArgsInjector extends InvokeInjector
{
    private final ArgsClassGenerator argsClassGenerator;
    
    public ModifyArgsInjector(final InjectionInfo a1) {
        super(a1, "@ModifyArgs");
        this.argsClassGenerator = (ArgsClassGenerator)a1.getContext().getExtensions().getGenerator((Class)ArgsClassGenerator.class);
    }
    
    @Override
    protected void checkTarget(final Target a1) {
        this.checkTargetModifiers(a1, false);
    }
    
    @Override
    protected void inject(final Target a1, final InjectionNodes.InjectionNode a2) {
        this.checkTargetForNode(a1, a2);
        super.inject(a1, a2);
    }
    
    @Override
    protected void injectAtInvoke(final Target a1, final InjectionNodes.InjectionNode a2) {
        final MethodInsnNode v1 = (MethodInsnNode)a2.getCurrentTarget();
        final Type[] v2 = Type.getArgumentTypes(v1.desc);
        if (v2.length == 0) {
            throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " targets a method invocation " + v1.name + v1.desc + " with no arguments!");
        }
        final String v3 = this.argsClassGenerator.getClassRef(v1.desc);
        final boolean v4 = this.verifyTarget(a1);
        final InsnList v5 = new InsnList();
        a1.addToStack(1);
        this.packArgs(v5, v3, v1);
        if (v4) {
            a1.addToStack(Bytecode.getArgsSize(a1.arguments));
            Bytecode.loadArgs(a1.arguments, v5, a1.isStatic ? 0 : 1);
        }
        this.invokeHandler(v5);
        this.unpackArgs(v5, v3, v2);
        a1.insns.insertBefore(v1, v5);
    }
    
    private boolean verifyTarget(final Target v-2) {
        final String format = String.format("(L%s;)V", ArgsClassGenerator.ARGS_REF);
        if (this.methodNode.desc.equals(format)) {
            return false;
        }
        final String a1 = Bytecode.changeDescriptorReturnType(v-2.method.desc, "V");
        final String v1 = String.format("(L%s;%s", ArgsClassGenerator.ARGS_REF, a1.substring(1));
        if (this.methodNode.desc.equals(v1)) {
            return true;
        }
        throw new InvalidInjectionException(this.info, "@ModifyArgs injector " + this + " has an invalid signature " + this.methodNode.desc + ", expected " + format + " or " + v1);
    }
    
    private void packArgs(final InsnList a1, final String a2, final MethodInsnNode a3) {
        final String v1 = Bytecode.changeDescriptorReturnType(a3.desc, "L" + a2 + ";");
        a1.add(new MethodInsnNode(184, a2, "of", v1, false));
        a1.add(new InsnNode(89));
        if (!this.isStatic) {
            a1.add(new VarInsnNode(25, 0));
            a1.add(new InsnNode(95));
        }
    }
    
    private void unpackArgs(final InsnList a3, final String v1, final Type[] v2) {
        for (int a4 = 0; a4 < v2.length; ++a4) {
            if (a4 < v2.length - 1) {
                a3.add(new InsnNode(89));
            }
            a3.add(new MethodInsnNode(182, v1, "$" + a4, "()" + v2[a4].getDescriptor(), false));
            if (a4 < v2.length - 1) {
                if (v2[a4].getSize() == 1) {
                    a3.add(new InsnNode(95));
                }
                else {
                    a3.add(new InsnNode(93));
                    a3.add(new InsnNode(88));
                }
            }
        }
    }
}
