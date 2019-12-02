package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;

public class InnerClassNode
{
    public String name;
    public String outerName;
    public String innerName;
    public int access;
    
    public InnerClassNode(final String a1, final String a2, final String a3, final int a4) {
        super();
        this.name = a1;
        this.outerName = a2;
        this.innerName = a3;
        this.access = a4;
    }
    
    public void accept(final ClassVisitor a1) {
        a1.visitInnerClass(this.name, this.outerName, this.innerName, this.access);
    }
}
