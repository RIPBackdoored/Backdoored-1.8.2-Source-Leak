package javassist.bytecode.analysis;

import javassist.bytecode.stackmap.*;

public static class Node
{
    private Block block;
    private Node parent;
    private Node[] children;
    
    Node(final Block a1) {
        super();
        this.block = a1;
        this.parent = null;
    }
    
    @Override
    public String toString() {
        final StringBuffer v0 = new StringBuffer();
        v0.append("Node[pos=").append(this.block().position());
        v0.append(", parent=");
        v0.append((this.parent == null) ? "*" : Integer.toString(this.parent.block().position()));
        v0.append(", children{");
        for (int v2 = 0; v2 < this.children.length; ++v2) {
            v0.append(this.children[v2].block().position()).append(", ");
        }
        v0.append("}]");
        return v0.toString();
    }
    
    public Block block() {
        return this.block;
    }
    
    public Node parent() {
        return this.parent;
    }
    
    public int children() {
        return this.children.length;
    }
    
    public Node child(final int a1) {
        return this.children[a1];
    }
    
    int makeDepth1stTree(final Node a4, final boolean[] a5, int v1, final int[] v2, final Access v3) {
        final int v4 = this.block.index;
        if (a5[v4]) {
            return v1;
        }
        a5[v4] = true;
        this.parent = a4;
        final BasicBlock[] v5 = v3.exits(this);
        if (v5 != null) {
            for (int a6 = 0; a6 < v5.length; ++a6) {
                final Node a7 = v3.node(v5[a6]);
                v1 = a7.makeDepth1stTree(this, a5, v1, v2, v3);
            }
        }
        v2[v4] = v1++;
        return v1;
    }
    
    boolean makeDominatorTree(final boolean[] v-6, final int[] v-5, final Access v-4) {
        final int index = this.block.index;
        if (v-6[index]) {
            return false;
        }
        v-6[index] = true;
        boolean b = false;
        final BasicBlock[] exits = v-4.exits(this);
        if (exits != null) {
            for (int a2 = 0; a2 < exits.length; ++a2) {
                final Node a3 = v-4.node(exits[a2]);
                if (a3.makeDominatorTree(v-6, v-5, v-4)) {
                    b = true;
                }
            }
        }
        final BasicBlock[] v0 = v-4.entrances(this);
        if (v0 != null) {
            for (int v2 = 0; v2 < v0.length; ++v2) {
                if (this.parent != null) {
                    final Node a4 = getAncestor(this.parent, v-4.node(v0[v2]), v-5);
                    if (a4 != this.parent) {
                        this.parent = a4;
                        b = true;
                    }
                }
            }
        }
        return b;
    }
    
    private static Node getAncestor(Node a1, Node a2, final int[] a3) {
        while (a1 != a2) {
            if (a3[a1.block.index] < a3[a2.block.index]) {
                a1 = a1.parent;
            }
            else {
                a2 = a2.parent;
            }
            if (a1 == null || a2 == null) {
                return null;
            }
        }
        return a1;
    }
    
    private static void setChildren(final Node[] v-3) {
        final int length = v-3.length;
        final int[] array = new int[length];
        for (int a1 = 0; a1 < length; ++a1) {
            array[a1] = 0;
        }
        for (int v0 = 0; v0 < length; ++v0) {
            final Node v2 = v-3[v0].parent;
            if (v2 != null) {
                final int[] array2 = array;
                final int index = v2.block.index;
                ++array2[index];
            }
        }
        for (int v0 = 0; v0 < length; ++v0) {
            v-3[v0].children = new Node[array[v0]];
        }
        for (int v0 = 0; v0 < length; ++v0) {
            array[v0] = 0;
        }
        for (int v0 = 0; v0 < length; ++v0) {
            final Node v2 = v-3[v0];
            final Node v3 = v2.parent;
            if (v3 != null) {
                v3.children[array[v3.block.index]++] = v2;
            }
        }
    }
    
    static /* synthetic */ Block access$200(final Node a1) {
        return a1.block;
    }
    
    static /* bridge */ void access$300(final Node[] a1) {
        setChildren(a1);
    }
}
