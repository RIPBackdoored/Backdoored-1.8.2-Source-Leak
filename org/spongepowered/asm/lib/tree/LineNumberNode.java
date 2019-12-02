package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LineNumberNode extends AbstractInsnNode
{
    public int line;
    public LabelNode start;
    
    public LineNumberNode(final int a1, final LabelNode a2) {
        super(-1);
        this.line = a1;
        this.start = a2;
    }
    
    @Override
    public int getType() {
        return 15;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitLineNumber(this.line, this.start.getLabel());
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new LineNumberNode(this.line, AbstractInsnNode.clone(this.start, a1));
    }
}
