package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class TryCatchBlockNode
{
    public LabelNode start;
    public LabelNode end;
    public LabelNode handler;
    public String type;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    
    public TryCatchBlockNode(final LabelNode a1, final LabelNode a2, final LabelNode a3, final String a4) {
        super();
        this.start = a1;
        this.end = a2;
        this.handler = a3;
        this.type = a4;
    }
    
    public void updateIndex(final int v-2) {
        final int n = 0x42000000 | v-2 << 8;
        if (this.visibleTypeAnnotations != null) {
            for (final TypeAnnotationNode a1 : this.visibleTypeAnnotations) {
                a1.typeRef = n;
            }
        }
        if (this.invisibleTypeAnnotations != null) {
            for (final TypeAnnotationNode v1 : this.invisibleTypeAnnotations) {
                v1.typeRef = n;
            }
        }
    }
    
    public void accept(final MethodVisitor v-1) {
        v-1.visitTryCatchBlock(this.start.getLabel(), this.end.getLabel(), (this.handler == null) ? null : this.handler.getLabel(), this.type);
        for (int v0 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), v2 = 0; v2 < v0; ++v2) {
            final TypeAnnotationNode a1 = this.visibleTypeAnnotations.get(v2);
            a1.accept(v-1.visitTryCatchAnnotation(a1.typeRef, a1.typePath, a1.desc, true));
        }
        for (int v0 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), v2 = 0; v2 < v0; ++v2) {
            final TypeAnnotationNode v3 = this.invisibleTypeAnnotations.get(v2);
            v3.accept(v-1.visitTryCatchAnnotation(v3.typeRef, v3.typePath, v3.desc, false));
        }
    }
}
