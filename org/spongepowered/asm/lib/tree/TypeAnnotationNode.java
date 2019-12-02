package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;

public class TypeAnnotationNode extends AnnotationNode
{
    public int typeRef;
    public TypePath typePath;
    
    public TypeAnnotationNode(final int a1, final TypePath a2, final String a3) {
        this(327680, a1, a2, a3);
        if (this.getClass() != TypeAnnotationNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public TypeAnnotationNode(final int a1, final int a2, final TypePath a3, final String a4) {
        super(a1, a4);
        this.typeRef = a2;
        this.typePath = a3;
    }
}
