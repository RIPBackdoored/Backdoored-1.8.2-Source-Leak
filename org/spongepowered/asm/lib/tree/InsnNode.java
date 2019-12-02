package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class InsnNode extends AbstractInsnNode
{
    public InsnNode(final int a1) {
        super(a1);
    }
    
    @Override
    public int getType() {
        return 0;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitInsn(this.opcode);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new InsnNode(this.opcode).cloneAnnotations(this);
    }
}
