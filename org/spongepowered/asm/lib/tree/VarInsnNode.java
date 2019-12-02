package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class VarInsnNode extends AbstractInsnNode
{
    public int var;
    
    public VarInsnNode(final int a1, final int a2) {
        super(a1);
        this.var = a2;
    }
    
    public void setOpcode(final int a1) {
        this.opcode = a1;
    }
    
    @Override
    public int getType() {
        return 2;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitVarInsn(this.opcode, this.var);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new VarInsnNode(this.opcode, this.var).cloneAnnotations(this);
    }
}
