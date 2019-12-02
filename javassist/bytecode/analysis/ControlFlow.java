package javassist.bytecode.analysis;

import javassist.*;
import javassist.bytecode.*;
import javassist.bytecode.stackmap.*;
import java.util.*;

public class ControlFlow
{
    private CtClass clazz;
    private MethodInfo methodInfo;
    private Block[] basicBlocks;
    private Frame[] frames;
    
    public ControlFlow(final CtMethod a1) throws BadBytecode {
        this(a1.getDeclaringClass(), a1.getMethodInfo2());
    }
    
    public ControlFlow(final CtClass v-6, final MethodInfo v-5) throws BadBytecode {
        super();
        this.clazz = v-6;
        this.methodInfo = v-5;
        this.frames = null;
        this.basicBlocks = (Block[])new BasicBlock.Maker() {
            final /* synthetic */ ControlFlow this$0;
            
            ControlFlow$1() {
                this.this$0 = a1;
                super();
            }
            
            @Override
            protected BasicBlock makeBlock(final int a1) {
                return new Block(a1, this.this$0.methodInfo);
            }
            
            @Override
            protected BasicBlock[] makeArray(final int a1) {
                return new Block[a1];
            }
        }.make(v-5);
        if (this.basicBlocks == null) {
            this.basicBlocks = new Block[0];
        }
        final int length = this.basicBlocks.length;
        final int[] array = new int[length];
        for (int a2 = 0; a2 < length; ++a2) {
            final Block a3 = this.basicBlocks[a2];
            a3.index = a2;
            a3.entrances = new Block[a3.incomings()];
            array[a2] = 0;
        }
        for (int i = 0; i < length; ++i) {
            final Block block = this.basicBlocks[i];
            for (int v0 = 0; v0 < block.exits(); ++v0) {
                final Block v2 = block.exit(v0);
                v2.entrances[array[v2.index]++] = block;
            }
            final Catcher[] v3 = block.catchers();
            for (int v4 = 0; v4 < v3.length; ++v4) {
                final Block v5 = v3[v4].node;
                v5.entrances[array[v5.index]++] = block;
            }
        }
    }
    
    public Block[] basicBlocks() {
        return this.basicBlocks;
    }
    
    public Frame frameAt(final int a1) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(this.clazz, this.methodInfo);
        }
        return this.frames[a1];
    }
    
    public Node[] dominatorTree() {
        final int length = this.basicBlocks.length;
        if (length == 0) {
            return null;
        }
        final Node[] array = new Node[length];
        final boolean[] array2 = new boolean[length];
        final int[] v0 = new int[length];
        for (int v2 = 0; v2 < length; ++v2) {
            array[v2] = new Node(this.basicBlocks[v2]);
            array2[v2] = false;
        }
        final Access v3 = new Access(array) {
            final /* synthetic */ ControlFlow this$0;
            
            ControlFlow$2(final Node[] a2) {
                this.this$0 = a1;
                super(a2);
            }
            
            @Override
            BasicBlock[] exits(final Node a1) {
                return a1.block.getExit();
            }
            
            @Override
            BasicBlock[] entrances(final Node a1) {
                return a1.block.entrances;
            }
        };
        array[0].makeDepth1stTree(null, array2, 0, v0, v3);
        do {
            for (int v4 = 0; v4 < length; ++v4) {
                array2[v4] = false;
            }
        } while (array[0].makeDominatorTree(array2, v0, v3));
        setChildren(array);
        return array;
    }
    
    public Node[] postDominatorTree() {
        final int length = this.basicBlocks.length;
        if (length == 0) {
            return null;
        }
        final Node[] array = new Node[length];
        final boolean[] array2 = new boolean[length];
        final int[] v0 = new int[length];
        for (int v2 = 0; v2 < length; ++v2) {
            array[v2] = new Node(this.basicBlocks[v2]);
            array2[v2] = false;
        }
        final Access v3 = new Access(array) {
            final /* synthetic */ ControlFlow this$0;
            
            ControlFlow$3(final Node[] a2) {
                this.this$0 = a1;
                super(a2);
            }
            
            @Override
            BasicBlock[] exits(final Node a1) {
                return a1.block.entrances;
            }
            
            @Override
            BasicBlock[] entrances(final Node a1) {
                return a1.block.getExit();
            }
        };
        int v4 = 0;
        for (int v5 = 0; v5 < length; ++v5) {
            if (array[v5].block.exits() == 0) {
                v4 = array[v5].makeDepth1stTree(null, array2, v4, v0, v3);
            }
        }
        boolean v6;
        do {
            for (int v7 = 0; v7 < length; ++v7) {
                array2[v7] = false;
            }
            v6 = false;
            for (int v7 = 0; v7 < length; ++v7) {
                if (array[v7].block.exits() == 0 && array[v7].makeDominatorTree(array2, v0, v3)) {
                    v6 = true;
                }
            }
        } while (v6);
        setChildren(array);
        return array;
    }
    
    static /* synthetic */ MethodInfo access$000(final ControlFlow a1) {
        return a1.methodInfo;
    }
    
    public static class Block extends BasicBlock
    {
        public Object clientData;
        int index;
        MethodInfo method;
        Block[] entrances;
        
        Block(final int a1, final MethodInfo a2) {
            super(a1);
            this.clientData = null;
            this.method = a2;
        }
        
        @Override
        protected void toString2(final StringBuffer v2) {
            super.toString2(v2);
            v2.append(", incoming{");
            for (int a1 = 0; a1 < this.entrances.length; ++a1) {
                v2.append(this.entrances[a1].position).append(", ");
            }
            v2.append("}");
        }
        
        BasicBlock[] getExit() {
            return this.exit;
        }
        
        public int index() {
            return this.index;
        }
        
        public int position() {
            return this.position;
        }
        
        public int length() {
            return this.length;
        }
        
        public int incomings() {
            return this.incoming;
        }
        
        public Block incoming(final int a1) {
            return this.entrances[a1];
        }
        
        public int exits() {
            return (this.exit == null) ? 0 : this.exit.length;
        }
        
        public Block exit(final int a1) {
            return (Block)this.exit[a1];
        }
        
        public Catcher[] catchers() {
            final ArrayList v1 = new ArrayList();
            for (Catch v2 = this.toCatch; v2 != null; v2 = v2.next) {
                v1.add(new Catcher(v2));
            }
            return v1.toArray(new Catcher[v1.size()]);
        }
    }
    
    abstract static class Access
    {
        Node[] all;
        
        Access(final Node[] a1) {
            super();
            this.all = a1;
        }
        
        Node node(final BasicBlock a1) {
            return this.all[((Block)a1).index];
        }
        
        abstract BasicBlock[] exits(final Node p0);
        
        abstract BasicBlock[] entrances(final Node p0);
    }
    
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
    
    public static class Catcher
    {
        private Block node;
        private int typeIndex;
        
        Catcher(final BasicBlock.Catch a1) {
            super();
            this.node = (Block)a1.body;
            this.typeIndex = a1.typeIndex;
        }
        
        public Block block() {
            return this.node;
        }
        
        public String type() {
            if (this.typeIndex == 0) {
                return "java.lang.Throwable";
            }
            return this.node.method.getConstPool().getClassInfo(this.typeIndex);
        }
        
        static /* synthetic */ Block access$100(final Catcher a1) {
            return a1.node;
        }
    }
}
