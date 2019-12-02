package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LdcInsnNode extends AbstractInsnNode
{
    public Object cst;
    
    public LdcInsnNode(final Object a1) {
        super(18);
        this.cst = a1;
    }
    
    @Override
    public int getType() {
        return 9;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitLdcInsn(this.cst);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new LdcInsnNode(this.cst).cloneAnnotations(this);
    }
}
