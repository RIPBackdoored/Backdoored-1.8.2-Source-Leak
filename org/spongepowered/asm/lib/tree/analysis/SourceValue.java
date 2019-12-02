package org.spongepowered.asm.lib.tree.analysis;

import java.util.*;
import org.spongepowered.asm.lib.tree.*;

public class SourceValue implements Value
{
    public final int size;
    public final Set<AbstractInsnNode> insns;
    
    public SourceValue(final int a1) {
        this(a1, SmallSet.emptySet());
    }
    
    public SourceValue(final int a1, final AbstractInsnNode a2) {
        super();
        this.size = a1;
        this.insns = new SmallSet<AbstractInsnNode>(a2, null);
    }
    
    public SourceValue(final int a1, final Set<AbstractInsnNode> a2) {
        super();
        this.size = a1;
        this.insns = a2;
    }
    
    public int getSize() {
        return this.size;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (!(a1 instanceof SourceValue)) {
            return false;
        }
        final SourceValue v1 = (SourceValue)a1;
        return this.size == v1.size && this.insns.equals(v1.insns);
    }
    
    @Override
    public int hashCode() {
        return this.insns.hashCode();
    }
}
