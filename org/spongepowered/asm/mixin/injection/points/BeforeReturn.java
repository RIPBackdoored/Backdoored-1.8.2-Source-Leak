package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

@AtCode("RETURN")
public class BeforeReturn extends InjectionPoint
{
    private final int ordinal;
    
    public BeforeReturn(final InjectionPointData a1) {
        super(a1);
        this.ordinal = a1.getOrdinal();
    }
    
    @Override
    public boolean checkPriority(final int a1, final int a2) {
        return true;
    }
    
    @Override
    public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
        boolean v3 = false;
        final int v4 = Type.getReturnType(a3).getOpcode(172);
        int v5 = 0;
        for (final AbstractInsnNode a4 : v1) {
            if (a4 instanceof InsnNode && a4.getOpcode() == v4) {
                if (this.ordinal == -1 || this.ordinal == v5) {
                    v2.add(a4);
                    v3 = true;
                }
                ++v5;
            }
        }
        return v3;
    }
}
