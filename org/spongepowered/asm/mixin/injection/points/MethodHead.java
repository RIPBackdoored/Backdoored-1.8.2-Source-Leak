package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("HEAD")
public class MethodHead extends InjectionPoint
{
    public MethodHead(final InjectionPointData a1) {
        super(a1);
    }
    
    @Override
    public boolean checkPriority(final int a1, final int a2) {
        return true;
    }
    
    @Override
    public boolean find(final String a1, final InsnList a2, final Collection<AbstractInsnNode> a3) {
        a3.add(a2.getFirst());
        return true;
    }
}
