package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class LookupSwitchInsnNode extends AbstractInsnNode
{
    public LabelNode dflt;
    public List<Integer> keys;
    public List<LabelNode> labels;
    
    public LookupSwitchInsnNode(final LabelNode a3, final int[] v1, final LabelNode[] v2) {
        super(171);
        this.dflt = a3;
        this.keys = new ArrayList<Integer>((v1 == null) ? 0 : v1.length);
        this.labels = new ArrayList<LabelNode>((v2 == null) ? 0 : v2.length);
        if (v1 != null) {
            for (int a4 = 0; a4 < v1.length; ++a4) {
                this.keys.add(v1[a4]);
            }
        }
        if (v2 != null) {
            this.labels.addAll(Arrays.asList(v2));
        }
    }
    
    @Override
    public int getType() {
        return 12;
    }
    
    @Override
    public void accept(final MethodVisitor v-2) {
        final int[] a2 = new int[this.keys.size()];
        for (int a1 = 0; a1 < a2.length; ++a1) {
            a2[a1] = this.keys.get(a1);
        }
        final Label[] v0 = new Label[this.labels.size()];
        for (int v2 = 0; v2 < v0.length; ++v2) {
            v0[v2] = this.labels.get(v2).getLabel();
        }
        v-2.visitLookupSwitchInsn(this.dflt.getLabel(), a2, v0);
        this.acceptAnnotations(v-2);
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> a1) {
        final LookupSwitchInsnNode v1 = new LookupSwitchInsnNode(AbstractInsnNode.clone(this.dflt, a1), null, AbstractInsnNode.clone(this.labels, a1));
        v1.keys.addAll(this.keys);
        return v1.cloneAnnotations(this);
    }
}
