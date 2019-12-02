package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class LocalVariableAnnotationNode extends TypeAnnotationNode
{
    public List<LabelNode> start;
    public List<LabelNode> end;
    public List<Integer> index;
    
    public LocalVariableAnnotationNode(final int a1, final TypePath a2, final LabelNode[] a3, final LabelNode[] a4, final int[] a5, final String a6) {
        this(327680, a1, a2, a3, a4, a5, a6);
    }
    
    public LocalVariableAnnotationNode(final int a3, final int a4, final TypePath a5, final LabelNode[] a6, final LabelNode[] a7, final int[] v1, final String v2) {
        super(a3, a4, a5, v2);
        (this.start = new ArrayList<LabelNode>(a6.length)).addAll(Arrays.asList(a6));
        (this.end = new ArrayList<LabelNode>(a7.length)).addAll(Arrays.asList(a7));
        this.index = new ArrayList<Integer>(v1.length);
        for (final int a8 : v1) {
            this.index.add(a8);
        }
    }
    
    public void accept(final MethodVisitor v1, final boolean v2) {
        final Label[] v3 = new Label[this.start.size()];
        final Label[] v4 = new Label[this.end.size()];
        final int[] v5 = new int[this.index.size()];
        for (int a1 = 0; a1 < v3.length; ++a1) {
            v3[a1] = this.start.get(a1).getLabel();
            v4[a1] = this.end.get(a1).getLabel();
            v5[a1] = this.index.get(a1);
        }
        this.accept(v1.visitLocalVariableAnnotation(this.typeRef, this.typePath, v3, v4, v5, this.desc, true));
    }
}
