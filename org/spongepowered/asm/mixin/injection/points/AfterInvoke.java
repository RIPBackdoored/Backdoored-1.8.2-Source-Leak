package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("INVOKE_ASSIGN")
public class AfterInvoke extends BeforeInvoke
{
    public AfterInvoke(final InjectionPointData a1) {
        super(a1);
    }
    
    @Override
    protected boolean addInsn(final InsnList a1, final Collection<AbstractInsnNode> a2, AbstractInsnNode a3) {
        final MethodInsnNode v1 = (MethodInsnNode)a3;
        if (Type.getReturnType(v1.desc) == Type.VOID_TYPE) {
            return false;
        }
        a3 = InjectionPoint.nextNode(a1, a3);
        if (a3 instanceof VarInsnNode && a3.getOpcode() >= 54) {
            a3 = InjectionPoint.nextNode(a1, a3);
        }
        a2.add(a3);
        return true;
    }
}
