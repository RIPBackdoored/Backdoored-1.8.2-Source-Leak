package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;

public class ModifyArgInjector extends InvokeInjector
{
    private final int index;
    private final boolean singleArgMode;
    
    public ModifyArgInjector(final InjectionInfo a1, final int a2) {
        super(a1, "@ModifyArg");
        this.index = a2;
        this.singleArgMode = (this.methodArgs.length == 1);
    }
    
    @Override
    protected void sanityCheck(final Target a1, final List<InjectionPoint> a2) {
        super.sanityCheck(a1, a2);
        if (this.singleArgMode && !this.methodArgs[0].equals(this.returnType)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg return type on " + this + " must match the parameter type. ARG=" + this.methodArgs[0] + " RETURN=" + this.returnType);
        }
    }
    
    @Override
    protected void checkTarget(final Target a1) {
        if (!this.isStatic && a1.isStatic) {
            throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
        }
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
        final int v3 = this.findArgIndex(a1, v2);
        final InsnList v4 = new InsnList();
        int v5 = 0;
        if (this.singleArgMode) {
            v5 = this.injectSingleArgHandler(a1, v2, v3, v4);
        }
        else {
            v5 = this.injectMultiArgHandler(a1, v2, v3, v4);
        }
        a1.insns.insertBefore(v1, v4);
        a1.addToLocals(v5);
        a1.addToStack(2 - (v5 - 1));
    }
    
    private int injectSingleArgHandler(final Target a1, final Type[] a2, final int a3, final InsnList a4) {
        final int[] v1 = this.storeArgs(a1, a2, a4, a3);
        this.invokeHandlerWithArgs(a2, a4, v1, a3, a3 + 1);
        this.pushArgs(a2, a4, v1, a3 + 1, a2.length);
        return v1[v1.length - 1] - a1.getMaxLocals() + a2[a2.length - 1].getSize();
    }
    
    private int injectMultiArgHandler(final Target a1, final Type[] a2, final int a3, final InsnList a4) {
        if (!Arrays.equals(a2, this.methodArgs)) {
            throw new InvalidInjectionException(this.info, "@ModifyArg method " + this + " targets a method with an invalid signature " + Bytecode.getDescriptor(a2) + ", expected " + Bytecode.getDescriptor(this.methodArgs));
        }
        final int[] v1 = this.storeArgs(a1, a2, a4, 0);
        this.pushArgs(a2, a4, v1, 0, a3);
        this.invokeHandlerWithArgs(a2, a4, v1, 0, a2.length);
        this.pushArgs(a2, a4, v1, a3 + 1, a2.length);
        return v1[v1.length - 1] - a1.getMaxLocals() + a2[a2.length - 1].getSize();
    }
    
    protected int findArgIndex(final Target v1, final Type[] v2) {
        if (this.index > -1) {
            if (this.index >= v2.length || !v2[this.index].equals(this.returnType)) {
                throw new InvalidInjectionException(this.info, "Specified index " + this.index + " for @ModifyArg is invalid for args " + Bytecode.getDescriptor(v2) + ", expected " + this.returnType + " on " + this);
            }
            return this.index;
        }
        else {
            int v3 = -1;
            for (int a1 = 0; a1 < v2.length; ++a1) {
                if (v2[a1].equals(this.returnType)) {
                    if (v3 != -1) {
                        throw new InvalidInjectionException(this.info, "Found duplicate args with index [" + v3 + ", " + a1 + "] matching type " + this.returnType + " for @ModifyArg target " + v1 + " in " + this + ". Please specify index of desired arg.");
                    }
                    v3 = a1;
                }
            }
            if (v3 == -1) {
                throw new InvalidInjectionException(this.info, "Could not find arg matching type " + this.returnType + " for @ModifyArg target " + v1 + " in " + this);
            }
            return v3;
        }
    }
}
