package org.spongepowered.asm.lib;

public abstract class FieldVisitor
{
    protected final int api;
    protected FieldVisitor fv;
    
    public FieldVisitor(final int a1) {
        this(a1, null);
    }
    
    public FieldVisitor(final int a1, final FieldVisitor a2) {
        super();
        if (a1 != 262144 && a1 != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = a1;
        this.fv = a2;
    }
    
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        if (this.fv != null) {
            return this.fv.visitAnnotation(a1, a2);
        }
        return null;
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.fv != null) {
            return this.fv.visitTypeAnnotation(a1, a2, a3, a4);
        }
        return null;
    }
    
    public void visitAttribute(final Attribute a1) {
        if (this.fv != null) {
            this.fv.visitAttribute(a1);
        }
    }
    
    public void visitEnd() {
        if (this.fv != null) {
            this.fv.visitEnd();
        }
    }
}
