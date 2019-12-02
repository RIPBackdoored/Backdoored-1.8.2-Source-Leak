package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class AnnotationRemapper extends AnnotationVisitor
{
    protected final Remapper remapper;
    
    public AnnotationRemapper(final AnnotationVisitor a1, final Remapper a2) {
        this(327680, a1, a2);
    }
    
    protected AnnotationRemapper(final int a1, final AnnotationVisitor a2, final Remapper a3) {
        super(a1, a2);
        this.remapper = a3;
    }
    
    @Override
    public void visit(final String a1, final Object a2) {
        this.av.visit(a1, this.remapper.mapValue(a2));
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        this.av.visitEnum(a1, this.remapper.mapDesc(a2), a3);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final String a2) {
        final AnnotationVisitor v1 = this.av.visitAnnotation(a1, this.remapper.mapDesc(a2));
        return (v1 == null) ? null : ((v1 == this.av) ? this : new AnnotationRemapper(v1, this.remapper));
    }
    
    @Override
    public AnnotationVisitor visitArray(final String a1) {
        final AnnotationVisitor v1 = this.av.visitArray(a1);
        return (v1 == null) ? null : ((v1 == this.av) ? this : new AnnotationRemapper(v1, this.remapper));
    }
}
