package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class FieldInsnNode extends AbstractInsnNode
{
    public String owner;
    public String name;
    public String desc;
    
    public FieldInsnNode(final int a1, final String a2, final String a3, final String a4) {
        super(a1);
        this.owner = a2;
        this.name = a3;
        this.desc = a4;
    }
    
    public void setOpcode(final int a1) {
        this.opcode = a1;
    }
    
    @Override
    public int getType() {
        return 4;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitFieldInsn(this.opcode, this.owner, this.name, this.desc);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new FieldInsnNode(this.opcode, this.owner, this.name, this.desc).cloneAnnotations(this);
    }
}
