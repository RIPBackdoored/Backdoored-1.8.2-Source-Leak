package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class JumpInsnNode extends AbstractInsnNode
{
    public LabelNode label;
    
    public JumpInsnNode(final int a1, final LabelNode a2) {
        super(a1);
        this.label = a2;
    }
    
    public void setOpcode(final int a1) {
        this.opcode = a1;
    }
    
    @Override
    public int getType() {
        return 7;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitJumpInsn(this.opcode, this.label.getLabel());
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new JumpInsnNode(this.opcode, AbstractInsnNode.clone(this.label, a1)).cloneAnnotations(this);
    }
}
