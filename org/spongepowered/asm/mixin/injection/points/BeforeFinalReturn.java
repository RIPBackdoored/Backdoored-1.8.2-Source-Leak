package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import java.util.*;

@AtCode("TAIL")
public class BeforeFinalReturn extends InjectionPoint
{
    private final IMixinContext context;
    
    public BeforeFinalReturn(final InjectionPointData a1) {
        super(a1);
        this.context = a1.getContext();
    }
    
    @Override
    public boolean checkPriority(final int a1, final int a2) {
        return true;
    }
    
    @Override
    public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
        AbstractInsnNode v3 = null;
        final int v4 = Type.getReturnType(a3).getOpcode(172);
        for (final AbstractInsnNode a4 : v1) {
            if (a4 instanceof InsnNode && a4.getOpcode() == v4) {
                v3 = a4;
            }
        }
        if (v3 == null) {
            throw new InvalidInjectionException(this.context, "TAIL could not locate a valid RETURN in the target method!");
        }
        v2.add(v3);
        return true;
    }
}
