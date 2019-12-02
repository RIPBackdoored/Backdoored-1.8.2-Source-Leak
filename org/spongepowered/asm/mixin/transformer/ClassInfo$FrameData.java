package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.*;

public static class FrameData
{
    private static final String[] FRAMETYPES;
    public final int index;
    public final int type;
    public final int locals;
    
    FrameData(final int a1, final int a2, final int a3) {
        super();
        this.index = a1;
        this.type = a2;
        this.locals = a3;
    }
    
    FrameData(final int a1, final FrameNode a2) {
        super();
        this.index = a1;
        this.type = a2.type;
        this.locals = ((a2.local != null) ? a2.local.size() : 0);
    }
    
    @Override
    public String toString() {
        return String.format("FrameData[index=%d, type=%s, locals=%d]", this.index, FrameData.FRAMETYPES[this.type + 1], this.locals);
    }
    
    static {
        FRAMETYPES = new String[] { "NEW", "FULL", "APPEND", "CHOP", "SAME", "SAME1" };
    }
}
