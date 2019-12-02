package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class Shift extends InjectionPoint
{
    private final InjectionPoint input;
    private final int shift;
    
    public Shift(final InjectionPoint a1, final int a2) {
        super();
        if (a1 == null) {
            throw new IllegalArgumentException("Must supply an input injection point for SHIFT");
        }
        this.input = a1;
        this.shift = a2;
    }
    
    @Override
    public String toString() {
        return "InjectionPoint(" + this.getClass().getSimpleName() + ")[" + this.input + "]";
    }
    
    @Override
    public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
        final List<AbstractInsnNode> v3 = (v2 instanceof List) ? ((List)v2) : new ArrayList<AbstractInsnNode>(v2);
        this.input.find(a3, v1, v2);
        for (int a4 = 0; a4 < v3.size(); ++a4) {
            v3.set(a4, v1.get(v1.indexOf(v3.get(a4)) + this.shift));
        }
        if (v2 != v3) {
            v2.clear();
            v2.addAll(v3);
        }
        return v2.size() > 0;
    }
}
