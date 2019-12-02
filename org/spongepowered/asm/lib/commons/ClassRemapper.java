package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class ClassRemapper extends ClassVisitor
{
    protected final Remapper remapper;
    protected String className;
    
    public ClassRemapper(final ClassVisitor a1, final Remapper a2) {
        this(327680, a1, a2);
    }
    
    protected ClassRemapper(final int a1, final ClassVisitor a2, final Remapper a3) {
        super(a1, a2);
        this.remapper = a3;
    }
    
    @Override
    public void visit(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        this.className = a3;
        super.visit(a1, a2, this.remapper.mapType(a3), this.remapper.mapSignature(a4, false), this.remapper.mapType(a5), (String[])((a6 == null) ? null : this.remapper.mapTypes(a6)));
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final AnnotationVisitor v1 = super.visitAnnotation(this.remapper.mapDesc(a1), a2);
        return (v1 == null) ? null : this.createAnnotationRemapper(v1);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final AnnotationVisitor v1 = super.visitTypeAnnotation(a1, a2, this.remapper.mapDesc(a3), a4);
        return (v1 == null) ? null : this.createAnnotationRemapper(v1);
    }
    
    @Override
    public FieldVisitor visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        final FieldVisitor v1 = super.visitField(a1, this.remapper.mapFieldName(this.className, a2, a3), this.remapper.mapDesc(a3), this.remapper.mapSignature(a4, true), this.remapper.mapValue(a5));
        return (v1 == null) ? null : this.createFieldRemapper(v1);
    }
    
    @Override
    public MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        final String v1 = this.remapper.mapMethodDesc(a3);
        final MethodVisitor v2 = super.visitMethod(a1, this.remapper.mapMethodName(this.className, a2, a3), v1, this.remapper.mapSignature(a4, false), (String[])((a5 == null) ? null : this.remapper.mapTypes(a5)));
        return (v2 == null) ? null : this.createMethodRemapper(v2);
    }
    
    @Override
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        super.visitInnerClass(this.remapper.mapType(a1), (a2 == null) ? null : this.remapper.mapType(a2), a3, a4);
    }
    
    @Override
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        super.visitOuterClass(this.remapper.mapType(a1), (a2 == null) ? null : this.remapper.mapMethodName(a1, a2, a3), (a3 == null) ? null : this.remapper.mapMethodDesc(a3));
    }
    
    protected FieldVisitor createFieldRemapper(final FieldVisitor a1) {
        return new FieldRemapper(a1, this.remapper);
    }
    
    protected MethodVisitor createMethodRemapper(final MethodVisitor a1) {
        return new MethodRemapper(a1, this.remapper);
    }
    
    protected AnnotationVisitor createAnnotationRemapper(final AnnotationVisitor a1) {
        return new AnnotationRemapper(a1, this.remapper);
    }
}
