package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public final class TraceMethodVisitor extends MethodVisitor
{
    public final Printer p;
    
    public TraceMethodVisitor(final Printer a1) {
        this(null, a1);
    }
    
    public TraceMethodVisitor(final MethodVisitor a1, final Printer a2) {
        super(327680, a1);
        this.p = a2;
    }
    
    @Override
    public void visitParameter(final String a1, final int a2) {
        this.p.visitParameter(a1, a2);
        super.visitParameter(a1, a2);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final Printer v1 = this.p.visitMethodAnnotation(a1, a2);
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitAnnotation(a1, a2);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final Printer v1 = this.p.visitMethodTypeAnnotation(a1, a2, a3, a4);
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitTypeAnnotation(a1, a2, a3, a4);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        this.p.visitMethodAttribute(a1);
        super.visitAttribute(a1);
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        final Printer v1 = this.p.visitAnnotationDefault();
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitAnnotationDefault();
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        final Printer v1 = this.p.visitParameterAnnotation(a1, a2, a3);
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitParameterAnnotation(a1, a2, a3);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitCode() {
        this.p.visitCode();
        super.visitCode();
    }
    
    @Override
    public void visitFrame(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        this.p.visitFrame(a1, a2, a3, a4, a5);
        super.visitFrame(a1, a2, a3, a4, a5);
    }
    
    @Override
    public void visitInsn(final int a1) {
        this.p.visitInsn(a1);
        super.visitInsn(a1);
    }
    
    @Override
    public void visitIntInsn(final int a1, final int a2) {
        this.p.visitIntInsn(a1, a2);
        super.visitIntInsn(a1, a2);
    }
    
    @Override
    public void visitVarInsn(final int a1, final int a2) {
        this.p.visitVarInsn(a1, a2);
        super.visitVarInsn(a1, a2);
    }
    
    @Override
    public void visitTypeInsn(final int a1, final String a2) {
        this.p.visitTypeInsn(a1, a2);
        super.visitTypeInsn(a1, a2);
    }
    
    @Override
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        this.p.visitFieldInsn(a1, a2, a3, a4);
        super.visitFieldInsn(a1, a2, a3, a4);
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4) {
        if (this.api >= 327680) {
            super.visitMethodInsn(a1, a2, a3, a4);
            return;
        }
        this.p.visitMethodInsn(a1, a2, a3, a4);
        if (this.mv != null) {
            this.mv.visitMethodInsn(a1, a2, a3, a4);
        }
    }
    
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.api < 327680) {
            super.visitMethodInsn(a1, a2, a3, a4, a5);
            return;
        }
        this.p.visitMethodInsn(a1, a2, a3, a4, a5);
        if (this.mv != null) {
            this.mv.visitMethodInsn(a1, a2, a3, a4, a5);
        }
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String a1, final String a2, final Handle a3, final Object... a4) {
        this.p.visitInvokeDynamicInsn(a1, a2, a3, a4);
        super.visitInvokeDynamicInsn(a1, a2, a3, a4);
    }
    
    @Override
    public void visitJumpInsn(final int a1, final Label a2) {
        this.p.visitJumpInsn(a1, a2);
        super.visitJumpInsn(a1, a2);
    }
    
    @Override
    public void visitLabel(final Label a1) {
        this.p.visitLabel(a1);
        super.visitLabel(a1);
    }
    
    @Override
    public void visitLdcInsn(final Object a1) {
        this.p.visitLdcInsn(a1);
        super.visitLdcInsn(a1);
    }
    
    @Override
    public void visitIincInsn(final int a1, final int a2) {
        this.p.visitIincInsn(a1, a2);
        super.visitIincInsn(a1, a2);
    }
    
    @Override
    public void visitTableSwitchInsn(final int a1, final int a2, final Label a3, final Label... a4) {
        this.p.visitTableSwitchInsn(a1, a2, a3, a4);
        super.visitTableSwitchInsn(a1, a2, a3, a4);
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label a1, final int[] a2, final Label[] a3) {
        this.p.visitLookupSwitchInsn(a1, a2, a3);
        super.visitLookupSwitchInsn(a1, a2, a3);
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        this.p.visitMultiANewArrayInsn(a1, a2);
        super.visitMultiANewArrayInsn(a1, a2);
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final Printer v1 = this.p.visitInsnAnnotation(a1, a2, a3, a4);
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitInsnAnnotation(a1, a2, a3, a4);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        this.p.visitTryCatchBlock(a1, a2, a3, a4);
        super.visitTryCatchBlock(a1, a2, a3, a4);
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final Printer v1 = this.p.visitTryCatchAnnotation(a1, a2, a3, a4);
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitTryCatchAnnotation(a1, a2, a3, a4);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitLocalVariable(final String a1, final String a2, final String a3, final Label a4, final Label a5, final int a6) {
        this.p.visitLocalVariable(a1, a2, a3, a4, a5, a6);
        super.visitLocalVariable(a1, a2, a3, a4, a5, a6);
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int a1, final TypePath a2, final Label[] a3, final Label[] a4, final int[] a5, final String a6, final boolean a7) {
        final Printer v1 = this.p.visitLocalVariableAnnotation(a1, a2, a3, a4, a5, a6, a7);
        final AnnotationVisitor v2 = (this.mv == null) ? null : this.mv.visitLocalVariableAnnotation(a1, a2, a3, a4, a5, a6, a7);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitLineNumber(final int a1, final Label a2) {
        this.p.visitLineNumber(a1, a2);
        super.visitLineNumber(a1, a2);
    }
    
    @Override
    public void visitMaxs(final int a1, final int a2) {
        this.p.visitMaxs(a1, a2);
        super.visitMaxs(a1, a2);
    }
    
    @Override
    public void visitEnd() {
        this.p.visitMethodEnd();
        super.visitEnd();
    }
}
