package org.spongepowered.asm.mixin.injection.modify;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;

static class SearchState
{
    private final boolean print;
    private final int targetOrdinal;
    private int ordinal;
    private boolean pendingCheck;
    private boolean found;
    private VarInsnNode varNode;
    
    SearchState(final int a1, final boolean a2) {
        super();
        this.ordinal = 0;
        this.pendingCheck = false;
        this.found = false;
        this.targetOrdinal = a1;
        this.print = a2;
    }
    
    boolean success() {
        return this.found;
    }
    
    boolean isPendingCheck() {
        return this.pendingCheck;
    }
    
    void setPendingCheck() {
        this.pendingCheck = true;
    }
    
    void register(final VarInsnNode a1) {
        this.varNode = a1;
    }
    
    void check(final Collection<AbstractInsnNode> a1, final AbstractInsnNode a2, final int a3) {
        this.pendingCheck = false;
        if (a3 != this.varNode.var && (a3 > -2 || !this.print)) {
            return;
        }
        if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
            a1.add(a2);
            this.found = true;
        }
        ++this.ordinal;
        this.varNode = null;
    }
}
