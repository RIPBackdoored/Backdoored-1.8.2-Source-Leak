package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class InvokeDynamicInsnNode extends AbstractInsnNode
{
    public String name;
    public String desc;
    public Handle bsm;
    public Object[] bsmArgs;
    
    public InvokeDynamicInsnNode(final String a1, final String a2, final Handle a3, final Object... a4) {
        super(186);
        this.name = a1;
        this.desc = a2;
        this.bsm = a3;
        this.bsmArgs = a4;
    }
    
    @Override
    public int getType() {
        return 6;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitInvokeDynamicInsn(this.name, this.desc, this.bsm, this.bsmArgs);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new InvokeDynamicInsnNode(this.name, this.desc, this.bsm, this.bsmArgs).cloneAnnotations(this);
    }
}
