package org.spongepowered.asm.lib;

public abstract class MethodVisitor
{
    protected final int api;
    protected MethodVisitor mv;
    
    public MethodVisitor(final int a1) {
        this(a1, null);
    }
    
    public MethodVisitor(final int a1, final MethodVisitor a2) {
        super();
        if (a1 != 262144 && a1 != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = a1;
        this.mv = a2;
    }
    
    public void visitParameter(final String a1, final int a2) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            this.mv.visitParameter(a1, a2);
        }
    }
    
    public AnnotationVisitor visitAnnotationDefault() {
        if (this.mv != null) {
            return this.mv.visitAnnotationDefault();
        }
        return null;
    }
    
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        if (this.mv != null) {
            return this.mv.visitAnnotation(a1, a2);
        }
        return null;
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitTypeAnnotation(a1, a2, a3, a4);
        }
        return null;
    }
    
    public AnnotationVisitor visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        if (this.mv != null) {
            return this.mv.visitParameterAnnotation(a1, a2, a3);
        }
        return null;
    }
    
    public void visitAttribute(final Attribute a1) {
        if (this.mv != null) {
            this.mv.visitAttribute(a1);
        }
    }
    
    public void visitCode() {
        if (this.mv != null) {
            this.mv.visitCode();
        }
    }
    
    public void visitFrame(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        if (this.mv != null) {
            this.mv.visitFrame(a1, a2, a3, a4, a5);
        }
    }
    
    public void visitInsn(final int a1) {
        if (this.mv != null) {
            this.mv.visitInsn(a1);
        }
    }
    
    public void visitIntInsn(final int a1, final int a2) {
        if (this.mv != null) {
            this.mv.visitIntInsn(a1, a2);
        }
    }
    
    public void visitVarInsn(final int a1, final int a2) {
        if (this.mv != null) {
            this.mv.visitVarInsn(a1, a2);
        }
    }
    
    public void visitTypeInsn(final int a1, final String a2) {
        if (this.mv != null) {
            this.mv.visitTypeInsn(a1, a2);
        }
    }
    
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        if (this.mv != null) {
            this.mv.visitFieldInsn(a1, a2, a3, a4);
        }
    }
    
    @Deprecated
    public void visitMethodInsn(final int a3, final String a4, final String v1, final String v2) {
        if (this.api >= 327680) {
            final boolean a5 = a3 == 185;
            this.visitMethodInsn(a3, a4, v1, v2, a5);
            return;
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(a3, a4, v1, v2);
        }
    }
    
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.api >= 327680) {
            if (this.mv != null) {
                this.mv.visitMethodInsn(a1, a2, a3, a4, a5);
            }
            return;
        }
        if (a5 != (a1 == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
        }
        this.visitMethodInsn(a1, a2, a3, a4);
    }
    
    public void visitInvokeDynamicInsn(final String a1, final String a2, final Handle a3, final Object... a4) {
        if (this.mv != null) {
            this.mv.visitInvokeDynamicInsn(a1, a2, a3, a4);
        }
    }
    
    public void visitJumpInsn(final int a1, final Label a2) {
        if (this.mv != null) {
            this.mv.visitJumpInsn(a1, a2);
        }
    }
    
    public void visitLabel(final Label a1) {
        if (this.mv != null) {
            this.mv.visitLabel(a1);
        }
    }
    
    public void visitLdcInsn(final Object a1) {
        if (this.mv != null) {
            this.mv.visitLdcInsn(a1);
        }
    }
    
    public void visitIincInsn(final int a1, final int a2) {
        if (this.mv != null) {
            this.mv.visitIincInsn(a1, a2);
        }
    }
    
    public void visitTableSwitchInsn(final int a1, final int a2, final Label a3, final Label... a4) {
        if (this.mv != null) {
            this.mv.visitTableSwitchInsn(a1, a2, a3, a4);
        }
    }
    
    public void visitLookupSwitchInsn(final Label a1, final int[] a2, final Label[] a3) {
        if (this.mv != null) {
            this.mv.visitLookupSwitchInsn(a1, a2, a3);
        }
    }
    
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        if (this.mv != null) {
            this.mv.visitMultiANewArrayInsn(a1, a2);
        }
    }
    
    public AnnotationVisitor visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitInsnAnnotation(a1, a2, a3, a4);
        }
        return null;
    }
    
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        if (this.mv != null) {
            this.mv.visitTryCatchBlock(a1, a2, a3, a4);
        }
    }
    
    public AnnotationVisitor visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitTryCatchAnnotation(a1, a2, a3, a4);
        }
        return null;
    }
    
    public void visitLocalVariable(final String a1, final String a2, final String a3, final Label a4, final Label a5, final int a6) {
        if (this.mv != null) {
            this.mv.visitLocalVariable(a1, a2, a3, a4, a5, a6);
        }
    }
    
    public AnnotationVisitor visitLocalVariableAnnotation(final int a1, final TypePath a2, final Label[] a3, final Label[] a4, final int[] a5, final String a6, final boolean a7) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.mv != null) {
            return this.mv.visitLocalVariableAnnotation(a1, a2, a3, a4, a5, a6, a7);
        }
        return null;
    }
    
    public void visitLineNumber(final int a1, final Label a2) {
        if (this.mv != null) {
            this.mv.visitLineNumber(a1, a2);
        }
    }
    
    public void visitMaxs(final int a1, final int a2) {
        if (this.mv != null) {
            this.mv.visitMaxs(a1, a2);
        }
    }
    
    public void visitEnd() {
        if (this.mv != null) {
            this.mv.visitEnd();
        }
    }
}
