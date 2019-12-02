package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class MethodInsnNode extends AbstractInsnNode
{
    public String owner;
    public String name;
    public String desc;
    public boolean itf;
    
    @Deprecated
    public MethodInsnNode(final int a1, final String a2, final String a3, final String a4) {
        this(a1, a2, a3, a4, a1 == 185);
    }
    
    public MethodInsnNode(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        super(a1);
        this.owner = a2;
        this.name = a3;
        this.desc = a4;
        this.itf = a5;
    }
    
    public void setOpcode(final int a1) {
        this.opcode = a1;
    }
    
    @Override
    public int getType() {
        return 5;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitMethodInsn(this.opcode, this.owner, this.name, this.desc, this.itf);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new MethodInsnNode(this.opcode, this.owner, this.name, this.desc, this.itf);
    }
}
