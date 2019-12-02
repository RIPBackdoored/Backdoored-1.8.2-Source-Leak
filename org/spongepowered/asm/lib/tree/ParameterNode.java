package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;

public class ParameterNode
{
    public String name;
    public int access;
    
    public ParameterNode(final String a1, final int a2) {
        super();
        this.name = a1;
        this.access = a2;
    }
    
    public void accept(final MethodVisitor a1) {
        a1.visitParameter(this.name, this.access);
    }
}
