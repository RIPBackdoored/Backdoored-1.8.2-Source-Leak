package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

@AtCode("JUMP")
public class JumpInsnPoint extends InjectionPoint
{
    private final int opCode;
    private final int ordinal;
    
    public JumpInsnPoint(final InjectionPointData a1) {
        super();
        this.opCode = a1.getOpcode(-1, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 198, 199, -1);
        this.ordinal = a1.getOrdinal();
    }
    
    @Override
    public boolean find(final String a3, final InsnList v1, final Collection<AbstractInsnNode> v2) {
        boolean v3 = false;
        int v4 = 0;
        for (final AbstractInsnNode a4 : v1) {
            if (a4 instanceof JumpInsnNode && (this.opCode == -1 || a4.getOpcode() == this.opCode)) {
                if (this.ordinal == -1 || this.ordinal == v4) {
                    v2.add(a4);
                    v3 = true;
                }
                ++v4;
            }
        }
        return v3;
    }
}
