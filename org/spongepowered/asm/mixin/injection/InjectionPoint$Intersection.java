package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import java.lang.reflect.*;

static final class Intersection extends CompositeInjectionPoint
{
    public Intersection(final InjectionPoint... a1) {
        super(a1);
    }
    
    @Override
    public boolean find(final String v-7, final InsnList v-6, final Collection<AbstractInsnNode> v-5) {
        boolean b = false;
        final ArrayList<AbstractInsnNode>[] array = (ArrayList<AbstractInsnNode>[])Array.newInstance(ArrayList.class, this.components.length);
        for (int a1 = 0; a1 < this.components.length; ++a1) {
            array[a1] = new ArrayList<AbstractInsnNode>();
            this.components[a1].find(v-7, v-6, array[a1]);
        }
        final ArrayList<AbstractInsnNode> list = array[0];
        for (int i = 0; i < list.size(); ++i) {
            final AbstractInsnNode a2 = list.get(i);
            final boolean v1 = true;
            for (int a3 = 1; a3 < array.length && array[a3].contains(a2); ++a3) {}
            if (v1) {
                v-5.add(a2);
                b = true;
            }
        }
        return b;
    }
}
