package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

class Subroutine
{
    LabelNode start;
    boolean[] access;
    List<JumpInsnNode> callers;
    
    private Subroutine() {
        super();
    }
    
    Subroutine(final LabelNode a1, final int a2, final JumpInsnNode a3) {
        super();
        this.start = a1;
        this.access = new boolean[a2];
        (this.callers = new ArrayList<JumpInsnNode>()).add(a3);
    }
    
    public Subroutine copy() {
        final Subroutine v1 = new Subroutine();
        v1.start = this.start;
        v1.access = new boolean[this.access.length];
        System.arraycopy(this.access, 0, v1.access, 0, this.access.length);
        v1.callers = new ArrayList<JumpInsnNode>(this.callers);
        return v1;
    }
    
    public boolean merge(final Subroutine v-2) throws AnalyzerException {
        boolean b = false;
        for (int a1 = 0; a1 < this.access.length; ++a1) {
            if (v-2.access[a1] && !this.access[a1]) {
                this.access[a1] = true;
                b = true;
            }
        }
        if (v-2.start == this.start) {
            for (int v0 = 0; v0 < v-2.callers.size(); ++v0) {
                final JumpInsnNode v2 = v-2.callers.get(v0);
                if (!this.callers.contains(v2)) {
                    this.callers.add(v2);
                    b = true;
                }
            }
        }
        return b;
    }
}
