package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public final class TraceAnnotationVisitor extends AnnotationVisitor
{
    private final Printer p;
    
    public TraceAnnotationVisitor(final Printer a1) {
        this(null, a1);
    }
    
    public TraceAnnotationVisitor(final AnnotationVisitor a1, final Printer a2) {
        super(327680, a1);
        this.p = a2;
    }
    
    @Override
    public void visit(final String a1, final Object a2) {
        this.p.visit(a1, a2);
        super.visit(a1, a2);
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        this.p.visitEnum(a1, a2, a3);
        super.visitEnum(a1, a2, a3);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final String a2) {
        final Printer v1 = this.p.visitAnnotation(a1, a2);
        final AnnotationVisitor v2 = (this.av == null) ? null : this.av.visitAnnotation(a1, a2);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public AnnotationVisitor visitArray(final String a1) {
        final Printer v1 = this.p.visitArray(a1);
        final AnnotationVisitor v2 = (this.av == null) ? null : this.av.visitArray(a1);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitEnd() {
        this.p.visitAnnotationEnd();
        super.visitEnd();
    }
}
