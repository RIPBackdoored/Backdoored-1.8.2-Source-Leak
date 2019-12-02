package org.spongepowered.asm.mixin.injection.invoke;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import com.google.common.collect.*;

static class RedirectedInvoke
{
    final Target target;
    final MethodInsnNode node;
    final Type returnType;
    final Type[] args;
    final Type[] locals;
    boolean captureTargetArgs;
    
    RedirectedInvoke(final Target a1, final MethodInsnNode a2) {
        super();
        this.captureTargetArgs = false;
        this.target = a1;
        this.node = a2;
        this.returnType = Type.getReturnType(a2.desc);
        this.args = Type.getArgumentTypes(a2.desc);
        this.locals = (Type[])((a2.getOpcode() == 184) ? this.args : ObjectArrays.concat((Object)Type.getType("L" + a2.owner + ";"), (Object[])this.args));
    }
}
