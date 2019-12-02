package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class MultiANewArrayInsnNode extends AbstractInsnNode
{
    public String desc;
    public int dims;
    
    public MultiANewArrayInsnNode(final String a1, final int a2) {
        super(197);
        this.desc = a1;
        this.dims = a2;
    }
    
    @Override
    public int getType() {
        return 13;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitMultiANewArrayInsn(this.desc, this.dims);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new MultiANewArrayInsnNode(this.desc, this.dims).cloneAnnotations(this);
    }
}
