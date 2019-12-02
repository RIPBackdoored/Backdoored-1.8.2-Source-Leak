package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;

static class Context extends LocalVariableDiscriminator.Context
{
    final InsnList insns;
    
    public Context(final Type a1, final boolean a2, final Target a3, final AbstractInsnNode a4) {
        super(a1, a2, a3, a4);
        this.insns = new InsnList();
    }
}
