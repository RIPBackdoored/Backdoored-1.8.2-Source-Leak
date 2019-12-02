package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;

public class LocalVariableNode
{
    public String name;
    public String desc;
    public String signature;
    public LabelNode start;
    public LabelNode end;
    public int index;
    
    public LocalVariableNode(final String a1, final String a2, final String a3, final LabelNode a4, final LabelNode a5, final int a6) {
        super();
        this.name = a1;
        this.desc = a2;
        this.signature = a3;
        this.start = a4;
        this.end = a5;
        this.index = a6;
    }
    
    public void accept(final MethodVisitor a1) {
        a1.visitLocalVariable(this.name, this.desc, this.signature, this.start.getLabel(), this.end.getLabel(), this.index);
    }
}
