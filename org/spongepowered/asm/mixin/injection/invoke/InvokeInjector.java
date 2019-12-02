package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.code.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

public abstract class InvokeInjector extends Injector
{
    protected final String annotationType;
    
    public InvokeInjector(final InjectionInfo a1, final String a2) {
        super(a1);
        this.annotationType = a2;
    }
    
    @Override
    protected void sanityCheck(final Target a1, final List<InjectionPoint> a2) {
        super.sanityCheck(a1, a2);
        this.checkTarget(a1);
    }
    
    protected void checkTarget(final Target a1) {
        this.checkTargetModifiers(a1, true);
    }
    
    protected final void checkTargetModifiers(final Target a1, final boolean a2) {
        if (a2 && a1.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' modifier of handler method does not match target in " + this);
        }
        if (!a2 && !this.isStatic && a1.isStatic) {
            throw new InvalidInjectionException(this.info, "non-static callback method " + this + " targets a static method which is not supported");
        }
    }
    
    protected void checkTargetForNode(final Target v-3, final InjectionNodes.InjectionNode v-2) {
        if (v-3.isCtor) {
            final MethodInsnNode a1 = v-3.findSuperInitNode();
            final int a2 = v-3.indexOf(a1);
            final int v1 = v-3.indexOf(v-2.getCurrentTarget());
            if (v1 <= a2) {
                if (!this.isStatic) {
                    throw new InvalidInjectionException(this.info, "Pre-super " + this.annotationType + " invocation must be static in " + this);
                }
                return;
            }
        }
        this.checkTargetModifiers(v-3, true);
    }
    
    @Override
    protected void inject(final Target a1, final InjectionNodes.InjectionNode a2) {
        if (!(a2.getCurrentTarget() instanceof MethodInsnNode)) {
            throw new InvalidInjectionException(this.info, this.annotationType + " annotation on is targetting a non-method insn in " + a1 + " in " + this);
        }
        this.injectAtInvoke(a1, a2);
    }
    
    protected abstract void injectAtInvoke(final Target p0, final InjectionNodes.InjectionNode p1);
    
    protected AbstractInsnNode invokeHandlerWithArgs(final Type[] a1, final InsnList a2, final int[] a3) {
        return this.invokeHandlerWithArgs(a1, a2, a3, 0, a1.length);
    }
    
    protected AbstractInsnNode invokeHandlerWithArgs(final Type[] a1, final InsnList a2, final int[] a3, final int a4, final int a5) {
        if (!this.isStatic) {
            a2.add(new VarInsnNode(25, 0));
        }
        this.pushArgs(a1, a2, a3, a4, a5);
        return this.invokeHandler(a2);
    }
    
    protected int[] storeArgs(final Target a1, final Type[] a2, final InsnList a3, final int a4) {
        final int[] v1 = a1.generateArgMap(a2, a4);
        this.storeArgs(a2, a3, v1, a4, a2.length);
        return v1;
    }
    
    protected void storeArgs(final Type[] a3, final InsnList a4, final int[] a5, final int v1, final int v2) {
        for (int a6 = v2 - 1; a6 >= v1; --a6) {
            a4.add(new VarInsnNode(a3[a6].getOpcode(54), a5[a6]));
        }
    }
    
    protected void pushArgs(final Type[] a3, final InsnList a4, final int[] a5, final int v1, final int v2) {
        for (int a6 = v1; a6 < v2; ++a6) {
            a4.add(new VarInsnNode(a3[a6].getOpcode(21), a5[a6]));
        }
    }
}
