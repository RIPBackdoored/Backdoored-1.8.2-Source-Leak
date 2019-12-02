package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public abstract class AbstractInsnNode
{
    public static final int INSN = 0;
    public static final int INT_INSN = 1;
    public static final int VAR_INSN = 2;
    public static final int TYPE_INSN = 3;
    public static final int FIELD_INSN = 4;
    public static final int METHOD_INSN = 5;
    public static final int INVOKE_DYNAMIC_INSN = 6;
    public static final int JUMP_INSN = 7;
    public static final int LABEL = 8;
    public static final int LDC_INSN = 9;
    public static final int IINC_INSN = 10;
    public static final int TABLESWITCH_INSN = 11;
    public static final int LOOKUPSWITCH_INSN = 12;
    public static final int MULTIANEWARRAY_INSN = 13;
    public static final int FRAME = 14;
    public static final int LINE = 15;
    protected int opcode;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    AbstractInsnNode prev;
    AbstractInsnNode next;
    int index;
    
    protected AbstractInsnNode(final int a1) {
        super();
        this.opcode = a1;
        this.index = -1;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public abstract int getType();
    
    public AbstractInsnNode getPrevious() {
        return this.prev;
    }
    
    public AbstractInsnNode getNext() {
        return this.next;
    }
    
    public abstract void accept(final MethodVisitor p0);
    
    protected final void acceptAnnotations(final MethodVisitor v-1) {
        for (int v0 = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), v2 = 0; v2 < v0; ++v2) {
            final TypeAnnotationNode a1 = this.visibleTypeAnnotations.get(v2);
            a1.accept(v-1.visitInsnAnnotation(a1.typeRef, a1.typePath, a1.desc, true));
        }
        for (int v0 = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), v2 = 0; v2 < v0; ++v2) {
            final TypeAnnotationNode v3 = this.invisibleTypeAnnotations.get(v2);
            v3.accept(v-1.visitInsnAnnotation(v3.typeRef, v3.typePath, v3.desc, false));
        }
    }
    
    public abstract AbstractInsnNode clone(final Map<LabelNode, LabelNode> p0);
    
    static LabelNode clone(final LabelNode a1, final Map<LabelNode, LabelNode> a2) {
        return a2.get(a1);
    }
    
    static LabelNode[] clone(final List<LabelNode> a2, final Map<LabelNode, LabelNode> v1) {
        final LabelNode[] v2 = new LabelNode[a2.size()];
        for (int a3 = 0; a3 < v2.length; ++a3) {
            v2[a3] = v1.get(a2.get(a3));
        }
        return v2;
    }
    
    protected final AbstractInsnNode cloneAnnotations(final AbstractInsnNode v-2) {
        if (v-2.visibleTypeAnnotations != null) {
            this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
            for (int i = 0; i < v-2.visibleTypeAnnotations.size(); ++i) {
                final TypeAnnotationNode a1 = v-2.visibleTypeAnnotations.get(i);
                final TypeAnnotationNode v1 = new TypeAnnotationNode(a1.typeRef, a1.typePath, a1.desc);
                a1.accept(v1);
                this.visibleTypeAnnotations.add(v1);
            }
        }
        if (v-2.invisibleTypeAnnotations != null) {
            this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>();
            for (int i = 0; i < v-2.invisibleTypeAnnotations.size(); ++i) {
                final TypeAnnotationNode v2 = v-2.invisibleTypeAnnotations.get(i);
                final TypeAnnotationNode v1 = new TypeAnnotationNode(v2.typeRef, v2.typePath, v2.desc);
                v2.accept(v1);
                this.invisibleTypeAnnotations.add(v1);
            }
        }
        return this;
    }
}
