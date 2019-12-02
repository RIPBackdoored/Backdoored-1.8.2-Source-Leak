package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class TypeInsnNode extends AbstractInsnNode
{
    public String desc;
    
    public TypeInsnNode(final int a1, final String a2) {
        super(a1);
        this.desc = a2;
    }
    
    public void setOpcode(final int a1) {
        this.opcode = a1;
    }
    
    @Override
    public int getType() {
        return 3;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitTypeInsn(this.opcode, this.desc);
        this.acceptAnnotations(a1);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new TypeInsnNode(this.opcode, this.desc).cloneAnnotations(this);
    }
}
