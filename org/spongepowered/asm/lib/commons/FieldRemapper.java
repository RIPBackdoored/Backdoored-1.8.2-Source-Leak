package org.spongepowered.asm.lib.commons;

import org.spongepowered.asm.lib.*;

public class FieldRemapper extends FieldVisitor
{
    private final Remapper remapper;
    
    public FieldRemapper(final FieldVisitor a1, final Remapper a2) {
        this(327680, a1, a2);
    }
    
    protected FieldRemapper(final int a1, final FieldVisitor a2, final Remapper a3) {
        super(a1, a2);
        this.remapper = a3;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final AnnotationVisitor v1 = this.fv.visitAnnotation(this.remapper.mapDesc(a1), a2);
        return (v1 == null) ? null : new AnnotationRemapper(v1, this.remapper);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final AnnotationVisitor v1 = super.visitTypeAnnotation(a1, a2, this.remapper.mapDesc(a3), a4);
        return (v1 == null) ? null : new AnnotationRemapper(v1, this.remapper);
    }
}
