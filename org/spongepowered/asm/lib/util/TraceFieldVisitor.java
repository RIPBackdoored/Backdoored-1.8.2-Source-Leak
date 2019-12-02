package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public final class TraceFieldVisitor extends FieldVisitor
{
    public final Printer p;
    
    public TraceFieldVisitor(final Printer a1) {
        this(null, a1);
    }
    
    public TraceFieldVisitor(final FieldVisitor a1, final Printer a2) {
        super(327680, a1);
        this.p = a2;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final Printer v1 = this.p.visitFieldAnnotation(a1, a2);
        final AnnotationVisitor v2 = (this.fv == null) ? null : this.fv.visitAnnotation(a1, a2);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final Printer v1 = this.p.visitFieldTypeAnnotation(a1, a2, a3, a4);
        final AnnotationVisitor v2 = (this.fv == null) ? null : this.fv.visitTypeAnnotation(a1, a2, a3, a4);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        this.p.visitFieldAttribute(a1);
        super.visitAttribute(a1);
    }
    
    @Override
    public void visitEnd() {
        this.p.visitFieldEnd();
        super.visitEnd();
    }
}
