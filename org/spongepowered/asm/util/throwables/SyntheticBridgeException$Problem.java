package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;

public enum Problem
{
    BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"), 
    BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"), 
    BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"), 
    BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"), 
    BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"), 
    BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");
    
    private final String message;
    private static final /* synthetic */ Problem[] $VALUES;
    
    public static Problem[] values() {
        return Problem.$VALUES.clone();
    }
    
    public static Problem valueOf(final String a1) {
        return Enum.valueOf(Problem.class, a1);
    }
    
    private Problem(final String a1) {
        this.message = a1;
    }
    
    String getMessage(final String a1, final String a2, final int a3, final AbstractInsnNode a4, final AbstractInsnNode a5) {
        return String.format(this.message, a1, a2, a3, Bytecode.getOpcodeName(a4), Bytecode.getOpcodeName(a4), getInsnName(a4), getInsnName(a5), getInsnDesc(a4), getInsnDesc(a5));
    }
    
    private static String getInsnName(final AbstractInsnNode a1) {
        return (a1 instanceof MethodInsnNode) ? ((MethodInsnNode)a1).name : "";
    }
    
    private static String getInsnDesc(final AbstractInsnNode a1) {
        return (a1 instanceof MethodInsnNode) ? ((MethodInsnNode)a1).desc : "";
    }
    
    static {
        $VALUES = new Problem[] { Problem.BAD_INSN, Problem.BAD_LOAD, Problem.BAD_CAST, Problem.BAD_INVOKE_NAME, Problem.BAD_INVOKE_DESC, Problem.BAD_LENGTH };
    }
}
