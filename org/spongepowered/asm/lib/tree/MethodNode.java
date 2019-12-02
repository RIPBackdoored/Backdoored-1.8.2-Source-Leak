package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class MethodNode extends MethodVisitor
{
    public int access;
    public String name;
    public String desc;
    public String signature;
    public List<String> exceptions;
    public List<ParameterNode> parameters;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public Object annotationDefault;
    public List<AnnotationNode>[] visibleParameterAnnotations;
    public List<AnnotationNode>[] invisibleParameterAnnotations;
    public InsnList instructions;
    public List<TryCatchBlockNode> tryCatchBlocks;
    public int maxStack;
    public int maxLocals;
    public List<LocalVariableNode> localVariables;
    public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
    public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
    private boolean visited;
    
    public MethodNode() {
        this(327680);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public MethodNode(final int a1) {
        super(a1);
        this.instructions = new InsnList();
    }
    
    public MethodNode(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        this(327680, a1, a2, a3, a4, a5);
        if (this.getClass() != MethodNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public MethodNode(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        super(a1);
        this.access = a2;
        this.name = a3;
        this.desc = a4;
        this.signature = a5;
        this.exceptions = new ArrayList<String>((a6 == null) ? 0 : a6.length);
        final boolean v1 = (a2 & 0x400) != 0x0;
        if (!v1) {
            this.localVariables = new ArrayList<LocalVariableNode>(5);
        }
        this.tryCatchBlocks = new ArrayList<TryCatchBlockNode>();
        if (a6 != null) {
            this.exceptions.addAll(Arrays.asList(a6));
        }
        this.instructions = new InsnList();
    }
    
    @Override
    public void visitParameter(final String a1, final int a2) {
        if (this.parameters == null) {
            this.parameters = new ArrayList<ParameterNode>(5);
        }
        this.parameters.add(new ParameterNode(a1, a2));
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return new AnnotationNode(new ArrayList<Object>(0) {
            final /* synthetic */ MethodNode this$0;
            
            MethodNode$1(final int a2) {
                this.this$0 = a1;
                super(a2);
            }
            
            @Override
            public boolean add(final Object a1) {
                this.this$0.annotationDefault = a1;
                return super.add(a1);
            }
        });
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final AnnotationNode v1 = new AnnotationNode(a1);
        if (a2) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.visibleAnnotations.add(v1);
        }
        else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.invisibleAnnotations.add(v1);
        }
        return v1;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final TypeAnnotationNode v1 = new TypeAnnotationNode(a1, a2, a3);
        if (a4) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.visibleTypeAnnotations.add(v1);
        }
        else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.invisibleTypeAnnotations.add(v1);
        }
        return v1;
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int v1, final String v2, final boolean v3) {
        final AnnotationNode v4 = new AnnotationNode(v2);
        if (v3) {
            if (this.visibleParameterAnnotations == null) {
                final int a1 = Type.getArgumentTypes(this.desc).length;
                this.visibleParameterAnnotations = (List<AnnotationNode>[])new List[a1];
            }
            if (this.visibleParameterAnnotations[v1] == null) {
                this.visibleParameterAnnotations[v1] = new ArrayList<AnnotationNode>(1);
            }
            this.visibleParameterAnnotations[v1].add(v4);
        }
        else {
            if (this.invisibleParameterAnnotations == null) {
                final int a2 = Type.getArgumentTypes(this.desc).length;
                this.invisibleParameterAnnotations = (List<AnnotationNode>[])new List[a2];
            }
            if (this.invisibleParameterAnnotations[v1] == null) {
                this.invisibleParameterAnnotations[v1] = new ArrayList<AnnotationNode>(1);
            }
            this.invisibleParameterAnnotations[v1].add(v4);
        }
        return v4;
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        if (this.attrs == null) {
            this.attrs = new ArrayList<Attribute>(1);
        }
        this.attrs.add(a1);
    }
    
    @Override
    public void visitCode() {
    }
    
    @Override
    public void visitFrame(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        this.instructions.add(new FrameNode(a1, a2, (Object[])((a3 == null) ? null : this.getLabelNodes(a3)), a4, (Object[])((a5 == null) ? null : this.getLabelNodes(a5))));
    }
    
    @Override
    public void visitInsn(final int a1) {
        this.instructions.add(new InsnNode(a1));
    }
    
    @Override
    public void visitIntInsn(final int a1, final int a2) {
        this.instructions.add(new IntInsnNode(a1, a2));
    }
    
    @Override
    public void visitVarInsn(final int a1, final int a2) {
        this.instructions.add(new VarInsnNode(a1, a2));
    }
    
    @Override
    public void visitTypeInsn(final int a1, final String a2) {
        this.instructions.add(new TypeInsnNode(a1, a2));
    }
    
    @Override
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        this.instructions.add(new FieldInsnNode(a1, a2, a3, a4));
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4) {
        if (this.api >= 327680) {
            super.visitMethodInsn(a1, a2, a3, a4);
            return;
        }
        this.instructions.add(new MethodInsnNode(a1, a2, a3, a4));
    }
    
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.api < 327680) {
            super.visitMethodInsn(a1, a2, a3, a4, a5);
            return;
        }
        this.instructions.add(new MethodInsnNode(a1, a2, a3, a4, a5));
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String a1, final String a2, final Handle a3, final Object... a4) {
        this.instructions.add(new InvokeDynamicInsnNode(a1, a2, a3, a4));
    }
    
    @Override
    public void visitJumpInsn(final int a1, final Label a2) {
        this.instructions.add(new JumpInsnNode(a1, this.getLabelNode(a2)));
    }
    
    @Override
    public void visitLabel(final Label a1) {
        this.instructions.add(this.getLabelNode(a1));
    }
    
    @Override
    public void visitLdcInsn(final Object a1) {
        this.instructions.add(new LdcInsnNode(a1));
    }
    
    @Override
    public void visitIincInsn(final int a1, final int a2) {
        this.instructions.add(new IincInsnNode(a1, a2));
    }
    
    @Override
    public void visitTableSwitchInsn(final int a1, final int a2, final Label a3, final Label... a4) {
        this.instructions.add(new TableSwitchInsnNode(a1, a2, this.getLabelNode(a3), this.getLabelNodes(a4)));
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label a1, final int[] a2, final Label[] a3) {
        this.instructions.add(new LookupSwitchInsnNode(this.getLabelNode(a1), a2, this.getLabelNodes(a3)));
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        this.instructions.add(new MultiANewArrayInsnNode(a1, a2));
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        AbstractInsnNode v1;
        for (v1 = this.instructions.getLast(); v1.getOpcode() == -1; v1 = v1.getPrevious()) {}
        final TypeAnnotationNode v2 = new TypeAnnotationNode(a1, a2, a3);
        if (a4) {
            if (v1.visibleTypeAnnotations == null) {
                v1.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            v1.visibleTypeAnnotations.add(v2);
        }
        else {
            if (v1.invisibleTypeAnnotations == null) {
                v1.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            v1.invisibleTypeAnnotations.add(v2);
        }
        return v2;
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(a1), this.getLabelNode(a2), this.getLabelNode(a3), a4));
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final TryCatchBlockNode v1 = this.tryCatchBlocks.get((a1 & 0xFFFF00) >> 8);
        final TypeAnnotationNode v2 = new TypeAnnotationNode(a1, a2, a3);
        if (a4) {
            if (v1.visibleTypeAnnotations == null) {
                v1.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            v1.visibleTypeAnnotations.add(v2);
        }
        else {
            if (v1.invisibleTypeAnnotations == null) {
                v1.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            v1.invisibleTypeAnnotations.add(v2);
        }
        return v2;
    }
    
    @Override
    public void visitLocalVariable(final String a1, final String a2, final String a3, final Label a4, final Label a5, final int a6) {
        this.localVariables.add(new LocalVariableNode(a1, a2, a3, this.getLabelNode(a4), this.getLabelNode(a5), a6));
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int a1, final TypePath a2, final Label[] a3, final Label[] a4, final int[] a5, final String a6, final boolean a7) {
        final LocalVariableAnnotationNode v1 = new LocalVariableAnnotationNode(a1, a2, this.getLabelNodes(a3), this.getLabelNodes(a4), a5, a6);
        if (a7) {
            if (this.visibleLocalVariableAnnotations == null) {
                this.visibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1);
            }
            this.visibleLocalVariableAnnotations.add(v1);
        }
        else {
            if (this.invisibleLocalVariableAnnotations == null) {
                this.invisibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1);
            }
            this.invisibleLocalVariableAnnotations.add(v1);
        }
        return v1;
    }
    
    @Override
    public void visitLineNumber(final int a1, final Label a2) {
        this.instructions.add(new LineNumberNode(a1, this.getLabelNode(a2)));
    }
    
    @Override
    public void visitMaxs(final int a1, final int a2) {
        this.maxStack = a1;
        this.maxLocals = a2;
    }
    
    @Override
    public void visitEnd() {
    }
    
    protected LabelNode getLabelNode(final Label a1) {
        if (!(a1.info instanceof LabelNode)) {
            a1.info = new LabelNode();
        }
        return (LabelNode)a1.info;
    }
    
    private LabelNode[] getLabelNodes(final Label[] v2) {
        final LabelNode[] v3 = new LabelNode[v2.length];
        for (int a1 = 0; a1 < v2.length; ++a1) {
            v3[a1] = this.getLabelNode(v2[a1]);
        }
        return v3;
    }
    
    private Object[] getLabelNodes(final Object[] v-1) {
        final Object[] v0 = new Object[v-1.length];
        for (int v2 = 0; v2 < v-1.length; ++v2) {
            Object a1 = v-1[v2];
            if (a1 instanceof Label) {
                a1 = this.getLabelNode((Label)a1);
            }
            v0[v2] = a1;
        }
        return v0;
    }
    
    public void check(final int v-1) {
        if (v-1 == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            for (int v0 = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size(), v2 = 0; v2 < v0; ++v2) {
                final TryCatchBlockNode a1 = this.tryCatchBlocks.get(v2);
                if (a1.visibleTypeAnnotations != null && a1.visibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (a1.invisibleTypeAnnotations != null && a1.invisibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
            }
            for (int v2 = 0; v2 < this.instructions.size(); ++v2) {
                final AbstractInsnNode v3 = this.instructions.get(v2);
                if (v3.visibleTypeAnnotations != null && v3.visibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (v3.invisibleTypeAnnotations != null && v3.invisibleTypeAnnotations.size() > 0) {
                    throw new RuntimeException();
                }
                if (v3 instanceof MethodInsnNode) {
                    final boolean v4 = ((MethodInsnNode)v3).itf;
                    if (v4 != (v3.opcode == 185)) {
                        throw new RuntimeException();
                    }
                }
            }
            if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations.size() > 0) {
                throw new RuntimeException();
            }
        }
    }
    
    public void accept(final ClassVisitor a1) {
        final String[] v1 = new String[this.exceptions.size()];
        this.exceptions.toArray(v1);
        final MethodVisitor v2 = a1.visitMethod(this.access, this.name, this.desc, this.signature, v1);
        if (v2 != null) {
            this.accept(v2);
        }
    }
    
    public void accept(final MethodVisitor v-2) {
        for (int n = (this.parameters == null) ? 0 : this.parameters.size(), v0 = 0; v0 < n; ++v0) {
            final ParameterNode a1 = this.parameters.get(v0);
            v-2.visitParameter(a1.name, a1.access);
        }
        if (this.annotationDefault != null) {
            final AnnotationVisitor v2 = v-2.visitAnnotationDefault();
            AnnotationNode.accept(v2, null, this.annotationDefault);
            if (v2 != null) {
                v2.visitEnd();
            }
        }
        for (int n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final AnnotationNode v3 = this.visibleAnnotations.get(v0);
            v3.accept(v-2.visitAnnotation(v3.desc, true));
        }
        for (int n = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final AnnotationNode v3 = this.invisibleAnnotations.get(v0);
            v3.accept(v-2.visitAnnotation(v3.desc, false));
        }
        for (int n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final TypeAnnotationNode v4 = this.visibleTypeAnnotations.get(v0);
            v4.accept(v-2.visitTypeAnnotation(v4.typeRef, v4.typePath, v4.desc, true));
        }
        for (int n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final TypeAnnotationNode v4 = this.invisibleTypeAnnotations.get(v0);
            v4.accept(v-2.visitTypeAnnotation(v4.typeRef, v4.typePath, v4.desc, false));
        }
        for (int n = (this.visibleParameterAnnotations == null) ? 0 : this.visibleParameterAnnotations.length, v0 = 0; v0 < n; ++v0) {
            final List<?> v5 = this.visibleParameterAnnotations[v0];
            if (v5 != null) {
                for (int v6 = 0; v6 < v5.size(); ++v6) {
                    final AnnotationNode v7 = (AnnotationNode)v5.get(v6);
                    v7.accept(v-2.visitParameterAnnotation(v0, v7.desc, true));
                }
            }
        }
        for (int n = (this.invisibleParameterAnnotations == null) ? 0 : this.invisibleParameterAnnotations.length, v0 = 0; v0 < n; ++v0) {
            final List<?> v5 = this.invisibleParameterAnnotations[v0];
            if (v5 != null) {
                for (int v6 = 0; v6 < v5.size(); ++v6) {
                    final AnnotationNode v7 = (AnnotationNode)v5.get(v6);
                    v7.accept(v-2.visitParameterAnnotation(v0, v7.desc, false));
                }
            }
        }
        if (this.visited) {
            this.instructions.resetLabels();
        }
        for (int n = (this.attrs == null) ? 0 : this.attrs.size(), v0 = 0; v0 < n; ++v0) {
            v-2.visitAttribute(this.attrs.get(v0));
        }
        if (this.instructions.size() > 0) {
            v-2.visitCode();
            for (int n = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size(), v0 = 0; v0 < n; ++v0) {
                this.tryCatchBlocks.get(v0).updateIndex(v0);
                this.tryCatchBlocks.get(v0).accept(v-2);
            }
            this.instructions.accept(v-2);
            for (int n = (this.localVariables == null) ? 0 : this.localVariables.size(), v0 = 0; v0 < n; ++v0) {
                this.localVariables.get(v0).accept(v-2);
            }
            for (int n = (this.visibleLocalVariableAnnotations == null) ? 0 : this.visibleLocalVariableAnnotations.size(), v0 = 0; v0 < n; ++v0) {
                this.visibleLocalVariableAnnotations.get(v0).accept(v-2, true);
            }
            for (int n = (this.invisibleLocalVariableAnnotations == null) ? 0 : this.invisibleLocalVariableAnnotations.size(), v0 = 0; v0 < n; ++v0) {
                this.invisibleLocalVariableAnnotations.get(v0).accept(v-2, false);
            }
            v-2.visitMaxs(this.maxStack, this.maxLocals);
            this.visited = true;
        }
        v-2.visitEnd();
    }
}
