package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class IincInsnNode extends AbstractInsnNode
{
    public int var;
    public int incr;
    
    public IincInsnNode(final int a1, final int a2) {
        super(132);
        this.var = a1;
        this.incr = a2;
    }
    
    @Override
    public int getType() {
        return 10;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitIincInsn(this.var, this.incr);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new IincInsnNode(this.var, this.incr).cloneAnnotations(this);
    }
}
