package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;

public static final class TargetNode
{
    final AbstractInsnNode insn;
    final Set<InjectionPoint> nominators;
    
    TargetNode(final AbstractInsnNode a1) {
        super();
        this.nominators = new HashSet<InjectionPoint>();
        this.insn = a1;
    }
    
    public AbstractInsnNode getNode() {
        return this.insn;
    }
    
    public Set<InjectionPoint> getNominators() {
        return Collections.unmodifiableSet((Set<? extends InjectionPoint>)this.nominators);
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 != null && a1.getClass() == TargetNode.class && ((TargetNode)a1).insn == this.insn;
    }
    
    @Override
    public int hashCode() {
        return this.insn.hashCode();
    }
}
