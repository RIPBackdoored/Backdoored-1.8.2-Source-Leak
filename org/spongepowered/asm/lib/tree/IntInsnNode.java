package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class IntInsnNode extends AbstractInsnNode
{
    public int operand;
    
    public IntInsnNode(final int a1, final int a2) {
        super(a1);
        this.operand = a2;
    }
    
    public void setOpcode(final int a1) {
        this.opcode = a1;
    }
    
    @Override
    public int getType() {
        return 1;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitIntInsn(this.opcode, this.operand);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new IntInsnNode(this.opcode, this.operand).cloneAnnotations(this);
    }
}
