package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class FrameNode extends AbstractInsnNode
{
    public int type;
    public List<Object> local;
    public List<Object> stack;
    
    private FrameNode() {
        super(-1);
    }
    
    public FrameNode(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        super(-1);
        switch (this.type = a1) {
            case -1:
            case 0: {
                this.local = asList(a2, a3);
                this.stack = asList(a4, a5);
                break;
            }
            case 1: {
                this.local = asList(a2, a3);
                break;
            }
            case 2: {
                this.local = Arrays.asList(new Object[a2]);
            }
            case 4: {
                this.stack = asList(1, a5);
                break;
            }
        }
    }
    
    @Override
    public int getType() {
        return 14;
    }
    
    @Override
    public void accept(final MethodVisitor a1) {
        switch (this.type) {
            case -1:
            case 0: {
                a1.visitFrame(this.type, this.local.size(), asArray(this.local), this.stack.size(), asArray(this.stack));
                break;
            }
            case 1: {
                a1.visitFrame(this.type, this.local.size(), asArray(this.local), 0, null);
                break;
            }
            case 2: {
                a1.visitFrame(this.type, this.local.size(), null, 0, null);
                break;
            }
            case 3: {
                a1.visitFrame(this.type, 0, null, 0, null);
                break;
            }
            case 4: {
                a1.visitFrame(this.type, 0, null, 1, asArray(this.stack));
                break;
            }
        }
    }
    
    @Override
    public AbstractInsnNode clone(final Map<LabelNode, LabelNode> v-1) {
        final FrameNode v0 = new FrameNode();
        v0.type = this.type;
        if (this.local != null) {
            v0.local = new ArrayList<Object>();
            for (int v2 = 0; v2 < this.local.size(); ++v2) {
                Object a1 = this.local.get(v2);
                if (a1 instanceof LabelNode) {
                    a1 = v-1.get(a1);
                }
                v0.local.add(a1);
            }
        }
        if (this.stack != null) {
            v0.stack = new ArrayList<Object>();
            for (int v2 = 0; v2 < this.stack.size(); ++v2) {
                Object v3 = this.stack.get(v2);
                if (v3 instanceof LabelNode) {
                    v3 = v-1.get(v3);
                }
                v0.stack.add(v3);
            }
        }
        return v0;
    }
    
    private static List<Object> asList(final int a1, final Object[] a2) {
        return Arrays.asList(a2).subList(0, a1);
    }
    
    private static Object[] asArray(final List<Object> v-1) {
        final Object[] v0 = new Object[v-1.size()];
        for (int v2 = 0; v2 < v0.length; ++v2) {
            Object a1 = v-1.get(v2);
            if (a1 instanceof LabelNode) {
                a1 = ((LabelNode)a1).getLabel();
            }
            v0[v2] = a1;
        }
        return v0;
    }
}
