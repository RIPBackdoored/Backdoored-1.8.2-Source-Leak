package org.spongepowered.asm.mixin.injection;

import org.spongepowered.asm.lib.tree.*;
import java.util.*;

static final class Union extends CompositeInjectionPoint
{
    public Union(final InjectionPoint... a1) {
        super(a1);
    }
    
    @Override
    public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
        final LinkedHashSet<AbstractInsnNode> v3 = new LinkedHashSet<AbstractInsnNode>();
        for (int a4 = 0; a4 < this.components.length; ++a4) {
            this.components[a4].find(a3, v1, v3);
        }
        v2.addAll(v3);
        return v3.size() > 0;
    }
}
