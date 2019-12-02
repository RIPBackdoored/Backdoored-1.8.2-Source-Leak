package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.tree.*;

public class AnalyzerException extends Exception
{
    public final AbstractInsnNode node;
    
    public AnalyzerException(final AbstractInsnNode a1, final String a2) {
        super(a2);
        this.node = a1;
    }
    
    public AnalyzerException(final AbstractInsnNode a1, final String a2, final Throwable a3) {
        super(a2, a3);
        this.node = a1;
    }
    
    public AnalyzerException(final AbstractInsnNode a1, final String a2, final Object a3, final Value a4) {
        super(((a2 == null) ? "Expected " : (a2 + ": expected ")) + a3 + ", but found " + a4);
        this.node = a1;
    }
}
