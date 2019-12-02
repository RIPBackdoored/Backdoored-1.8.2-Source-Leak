package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class MethodRemapper extends MethodVisitor
{
    protected final Remapper remapper;
    
    public MethodRemapper(final MethodVisitor a1, final Remapper a2) {
        this(327680, a1, a2);
    }
    
    protected MethodRemapper(final int a1, final MethodVisitor a2, final Remapper a3) {
        super(a1, a2);
        this.remapper = a3;
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        final AnnotationVisitor v1 = super.visitAnnotationDefault();
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final AnnotationVisitor v1 = super.visitAnnotation(this.remapper.mapDesc(a1), a2);
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final AnnotationVisitor v1 = super.visitTypeAnnotation(a1, a2, this.remapper.mapDesc(a3), a4);
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        final AnnotationVisitor v1 = super.visitParameterAnnotation(a1, this.remapper.mapDesc(a2), a3);
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public void visitFrame(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        super.visitFrame(a1, a2, this.remapEntries(a2, a3), a4, this.remapEntries(a4, a5));
    }
    
    private Object[] remapEntries(final int v-1, final Object[] v0) {
        for (int v = 0; v < v-1; ++v) {
            if (v0[v] instanceof String) {
                final Object[] a2 = new Object[v-1];
                if (v > 0) {
                    System.arraycopy(v0, 0, a2, 0, v);
                }
                do {
                    final Object a3 = v0[v];
                    a2[v++] = ((a3 instanceof String) ? this.remapper.mapType((String)a3) : a3);
                } while (v < v-1);
                return a2;
            }
        }
        return v0;
    }
    
    @Override
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        super.visitFieldInsn(a1, this.remapper.mapType(a2), this.remapper.mapFieldName(a2, a3, a4), this.remapper.mapDesc(a4));
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4) {
        if (this.api >= 327680) {
            super.visitMethodInsn(a1, a2, a3, a4);
            return;
        }
        this.doVisitMethodInsn(a1, a2, a3, a4, a1 == 185);
    }
    
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.api < 327680) {
            super.visitMethodInsn(a1, a2, a3, a4, a5);
            return;
        }
        this.doVisitMethodInsn(a1, a2, a3, a4, a5);
    }
    
    private void doVisitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.mv != null) {
            this.mv.visitMethodInsn(a1, this.remapper.mapType(a2), this.remapper.mapMethodName(a2, a3, a4), this.remapper.mapMethodDesc(a4), a5);
        }
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String a3, final String a4, final Handle v1, final Object... v2) {
        for (int a5 = 0; a5 < v2.length; ++a5) {
            v2[a5] = this.remapper.mapValue(v2[a5]);
        }
        super.visitInvokeDynamicInsn(this.remapper.mapInvokeDynamicMethodName(a3, a4), this.remapper.mapMethodDesc(a4), (Handle)this.remapper.mapValue(v1), v2);
    }
    
    @Override
    public void visitTypeInsn(final int a1, final String a2) {
        super.visitTypeInsn(a1, this.remapper.mapType(a2));
    }
    
    @Override
    public void visitLdcInsn(final Object a1) {
        super.visitLdcInsn(this.remapper.mapValue(a1));
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        super.visitMultiANewArrayInsn(this.remapper.mapDesc(a1), a2);
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final AnnotationVisitor v1 = super.visitInsnAnnotation(a1, a2, this.remapper.mapDesc(a3), a4);
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        super.visitTryCatchBlock(a1, a2, a3, (a4 == null) ? null : this.remapper.mapType(a4));
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final AnnotationVisitor v1 = super.visitTryCatchAnnotation(a1, a2, this.remapper.mapDesc(a3), a4);
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public void visitLocalVariable(final String a1, final String a2, final String a3, final Label a4, final Label a5, final int a6) {
        super.visitLocalVariable(a1, this.remapper.mapDesc(a2), this.remapper.mapSignature(a3, true), a4, a5, a6);
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int a1, final TypePath a2, final Label[] a3, final Label[] a4, final int[] a5, final String a6, final boolean a7) {
        final AnnotationVisitor v1 = super.visitLocalVariableAnnotation(a1, a2, a3, a4, a5, this.remapper.mapDesc(a6), a7);
        return (v1 == null) ? v1 : new AnnotationRemapper(v1, this.remapper);
    }
}
