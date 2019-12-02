package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class TableSwitchInsnNode extends AbstractInsnNode
{
    public int min;
    public int max;
    public LabelNode dflt;
    public List<LabelNode> labels;
    
    public TableSwitchInsnNode(final int a1, final int a2, final LabelNode a3, final LabelNode... a4) {
        super(170);
        this.min = a1;
        this.max = a2;
        this.dflt = a3;
        this.labels = new ArrayList<LabelNode>();
        if (a4 != null) {
            this.labels.addAll(Arrays.asList(a4));
        }
    }
    
    @Override
    public int getType() {
        return 11;
    }
    
    @Override
    public void accept(final MethodVisitor v2) {
        final Label[] v3 = new Label[this.labels.size()];
        for (int a1 = 0; a1 < v3.length; ++a1) {
            v3[a1] = this.labels.get(a1).getLabel();
        }
        v2.visitTableSwitchInsn(this.min, this.max, this.dflt.getLabel(), v3);
        this.acceptAnnotations(v2);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        return new TableSwitchInsnNode(this.min, this.max, AbstractInsnNode.clone(this.dflt, a1), AbstractInsnNode.clone(this.labels, a1)).cloneAnnotations(this);
    }
}
