package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import java.util.*;

@AtCode("FIELD")
public class BeforeFieldAccess extends BeforeInvoke
{
    private static final String ARRAY_GET = "get";
    private static final String ARRAY_SET = "set";
    private static final String ARRAY_LENGTH = "length";
    public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
    private final int opcode;
    private final int arrOpcode;
    private final int fuzzFactor;
    
    public BeforeFieldAccess(final InjectionPointData a1) {
        super(a1);
        this.opcode = a1.getOpcode(-1, 180, 181, 178, 179, -1);
        final String v1 = a1.get("array", "");
        this.arrOpcode = ("get".equalsIgnoreCase(v1) ? 46 : ("set".equalsIgnoreCase(v1) ? 79 : ("length".equalsIgnoreCase(v1) ? 190 : 0)));
        this.fuzzFactor = Math.min(Math.max(a1.get("fuzz", 8), 1), 32);
    }
    
    public int getFuzzFactor() {
        return this.fuzzFactor;
    }
    
    public int getArrayOpcode() {
        return this.arrOpcode;
    }
    
    private int getArrayOpcode(final String a1) {
        if (this.arrOpcode != 190) {
            return Type.getType(a1).getElementType().getOpcode(this.arrOpcode);
        }
        return this.arrOpcode;
    }
    
    @Override
    protected boolean matchesInsn(final AbstractInsnNode a1) {
        return a1 instanceof FieldInsnNode && (((FieldInsnNode)a1).getOpcode() == this.opcode || this.opcode == -1) && (this.arrOpcode == 0 || ((a1.getOpcode() == 178 || a1.getOpcode() == 180) && Type.getType(((FieldInsnNode)a1).desc).getSort() == 9));
    }
    
    @Override
    protected boolean addInsn(final InsnList v1, final Collection<AbstractInsnNode> v2, final AbstractInsnNode v3) {
        if (this.arrOpcode > 0) {
            final FieldInsnNode a1 = (FieldInsnNode)v3;
            final int a2 = this.getArrayOpcode(a1.desc);
            this.log("{} > > > > searching for array access opcode {} fuzz={}", this.className, Bytecode.getOpcodeName(a2), this.fuzzFactor);
            if (findArrayNode(v1, a1, a2, this.fuzzFactor) == null) {
                this.log("{} > > > > > failed to locate matching insn", this.className);
                return false;
            }
        }
        this.log("{} > > > > > adding matching insn", this.className);
        return super.addInsn(v1, v2, v3);
    }
    
    public static AbstractInsnNode findArrayNode(final InsnList a4, final FieldInsnNode v1, final int v2, final int v3) {
        int v4 = 0;
        final Iterator<AbstractInsnNode> a5 = a4.iterator(a4.indexOf(v1) + 1);
        while (a5.hasNext()) {
            final AbstractInsnNode a6 = a5.next();
            if (a6.getOpcode() == v2) {
                return a6;
            }
            if (a6.getOpcode() == 190 && v4 == 0) {
                return null;
            }
            if (a6 instanceof FieldInsnNode) {
                final FieldInsnNode a7 = (FieldInsnNode)a6;
                if (a7.desc.equals(v1.desc) && a7.name.equals(v1.name) && a7.owner.equals(v1.owner)) {
                    return null;
                }
            }
            if (v4++ > v3) {
                return null;
            }
        }
        return null;
    }
}
