package org.spongepowered.asm.util.asm;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;

public class MethodVisitorEx extends MethodVisitor
{
    public MethodVisitorEx(final MethodVisitor a1) {
        super(327680, a1);
    }
    
    public void visitConstant(final byte a1) {
        if (a1 > -2 && a1 < 6) {
            this.visitInsn(Bytecode.CONSTANTS_INT[a1 + 1]);
            return;
        }
        this.visitIntInsn(16, a1);
    }
}
