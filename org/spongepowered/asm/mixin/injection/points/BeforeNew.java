package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("NEW")
public class BeforeNew extends InjectionPoint
{
    private final String target;
    private final String desc;
    private final int ordinal;
    
    public BeforeNew(final InjectionPointData a1) {
        super(a1);
        this.ordinal = a1.getOrdinal();
        final String v1 = Strings.emptyToNull(a1.get("class", a1.get("target", "")).replace('.', '/'));
        final MemberInfo v2 = MemberInfo.parseAndValidate(v1, a1.getContext());
        this.target = v2.toCtorType();
        this.desc = v2.toCtorDesc();
    }
    
    public boolean hasDescriptor() {
        return this.desc != null;
    }
    
    @Override
    public boolean find(final String v1, final InsnList v2, final Collection<AbstractInsnNode> v3) {
        boolean v4 = false;
        int v5 = 0;
        final Collection<TypeInsnNode> v6 = new ArrayList<TypeInsnNode>();
        final Collection<AbstractInsnNode> v7 = (Collection<AbstractInsnNode>)((this.desc != null) ? v6 : v3);
        for (final AbstractInsnNode a1 : v2) {
            if (a1 instanceof TypeInsnNode && a1.getOpcode() == 187 && this.matchesOwner((TypeInsnNode)a1)) {
                if (this.ordinal == -1 || this.ordinal == v5) {
                    v7.add(a1);
                    v4 = (this.desc == null);
                }
                ++v5;
            }
        }
        if (this.desc != null) {
            for (final TypeInsnNode a2 : v6) {
                if (this.findCtor(v2, a2)) {
                    v3.add(a2);
                    v4 = true;
                }
            }
        }
        return v4;
    }
    
    protected boolean findCtor(final InsnList v-2, final TypeInsnNode v-1) {
        final int v0 = v-2.indexOf(v-1);
        final Iterator<AbstractInsnNode> v2 = v-2.iterator(v0);
        while (v2.hasNext()) {
            final AbstractInsnNode a2 = v2.next();
            if (a2 instanceof MethodInsnNode && a2.getOpcode() == 183) {
                final MethodInsnNode a3 = (MethodInsnNode)a2;
                if ("<init>".equals(a3.name) && a3.owner.equals(v-1.desc) && a3.desc.equals(this.desc)) {
                    return true;
                }
                continue;
            }
        }
        return false;
    }
    
    private boolean matchesOwner(final TypeInsnNode a1) {
        return this.target == null || this.target.equals(a1.desc);
    }
}
