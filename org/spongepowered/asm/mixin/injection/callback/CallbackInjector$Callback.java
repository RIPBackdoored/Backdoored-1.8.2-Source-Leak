package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.code.*;

private class Callback extends InsnList
{
    private final MethodNode handler;
    private final AbstractInsnNode head;
    final Target target;
    final InjectionNodes.InjectionNode node;
    final LocalVariableNode[] locals;
    final Type[] localTypes;
    final int frameSize;
    final int extraArgs;
    final boolean canCaptureLocals;
    final boolean isAtReturn;
    final String desc;
    final String descl;
    final String[] argNames;
    int ctor;
    int invoke;
    private int marshalVar;
    private boolean captureArgs;
    final /* synthetic */ CallbackInjector this$0;
    
    Callback(final CallbackInjector this$0, final MethodNode a4, final Target a5, final InjectionNodes.InjectionNode a6, final LocalVariableNode[] v1, final boolean v2) {
        this.this$0 = this$0;
        super();
        this.marshalVar = -1;
        this.captureArgs = true;
        this.handler = a4;
        this.target = a5;
        this.head = a5.insns.getFirst();
        this.node = a6;
        this.locals = v1;
        this.localTypes = (Type[])((v1 != null) ? new Type[v1.length] : null);
        this.frameSize = Bytecode.getFirstNonArgLocalIndex(a5.arguments, !this$0.isStatic());
        List<String> v3 = null;
        if (v1 != null) {
            final int a7 = this$0.isStatic() ? 0 : 1;
            v3 = new ArrayList<String>();
            for (int a8 = 0; a8 <= v1.length; ++a8) {
                if (a8 == this.frameSize) {
                    v3.add((a5.returnType == Type.VOID_TYPE) ? "ci" : "cir");
                }
                if (a8 < v1.length && v1[a8] != null) {
                    this.localTypes[a8] = Type.getType(v1[a8].desc);
                    if (a8 >= a7) {
                        v3.add(CallbackInjector.meltSnowman(a8, v1[a8].name));
                    }
                }
            }
        }
        this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
        this.argNames = (String[])((v3 != null) ? ((String[])v3.toArray(new String[v3.size()])) : null);
        this.canCaptureLocals = (v2 && v1 != null && v1.length > this.frameSize);
        this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && this.isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
        this.desc = a5.getCallbackDescriptor(this.localTypes, a5.arguments);
        this.descl = a5.getCallbackDescriptor(true, this.localTypes, a5.arguments, this.frameSize, this.extraArgs);
        this.invoke = a5.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
    }
    
    private boolean isValueReturnOpcode(final int a1) {
        return a1 >= 172 && a1 < 177;
    }
    
    String getDescriptor() {
        return this.canCaptureLocals ? this.descl : this.desc;
    }
    
    String getDescriptorWithAllLocals() {
        return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
    }
    
    String getCallbackInfoConstructorDescriptor() {
        return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
    }
    
    void add(final AbstractInsnNode a1, final boolean a2, final boolean a3) {
        this.add(a1, a2, a3, false);
    }
    
    void add(final AbstractInsnNode a1, final boolean a2, final boolean a3, final boolean a4) {
        if (a4) {
            this.target.insns.insertBefore(this.head, a1);
        }
        else {
            this.add(a1);
        }
        this.ctor += (a2 ? 1 : 0);
        this.invoke += (a3 ? 1 : 0);
    }
    
    void inject() {
        this.target.insertBefore(this.node, this);
        this.target.addToStack(Math.max(this.invoke, this.ctor));
    }
    
    boolean checkDescriptor(final String v-2) {
        if (this.getDescriptor().equals(v-2)) {
            return true;
        }
        if (this.target.getSimpleCallbackDescriptor().equals(v-2) && !this.canCaptureLocals) {
            this.captureArgs = false;
            return true;
        }
        final Type[] argumentTypes = Type.getArgumentTypes(v-2);
        final Type[] v0 = Type.getArgumentTypes(this.descl);
        if (argumentTypes.length != v0.length) {
            return false;
        }
        for (int v2 = 0; v2 < v0.length; ++v2) {
            final Type a1 = argumentTypes[v2];
            if (!a1.equals(v0[v2])) {
                if (a1.getSort() == 9) {
                    return false;
                }
                if (Annotations.getInvisibleParameter(this.handler, Coerce.class, v2) == null) {
                    return false;
                }
                if (!Injector.canCoerce(argumentTypes[v2], v0[v2])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    boolean captureArgs() {
        return this.captureArgs;
    }
    
    int marshalVar() {
        if (this.marshalVar < 0) {
            this.marshalVar = this.target.allocateLocal();
        }
        return this.marshalVar;
    }
}
