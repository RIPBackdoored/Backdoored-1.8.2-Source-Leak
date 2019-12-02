package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LabelNode extends AbstractInsnNode
{
    private Label label;
    
    public LabelNode() {
        super(-1);
    }
    
    public LabelNode(final Label a1) {
        super(-1);
        this.label = a1;
    }
    
    @Override
    public int getType() {
        return 8;
    }
    
    public Label getLabel() {
        if (this.label == null) {
            this.label = new Label();
        }
        return this.label;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        a1.visitLabel(this.getLabel());
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return a1.get(this);
    }
    
    public void resetLabel() {
        this.label = null;
    }
}
