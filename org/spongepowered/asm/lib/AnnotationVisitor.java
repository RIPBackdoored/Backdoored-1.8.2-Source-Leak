package org.spongepowered.asm.lib;

public abstract class AnnotationVisitor
{
    protected final int api;
    protected AnnotationVisitor av;
    
    public AnnotationVisitor(final int a1) {
        this(a1, null);
    }
    
    public AnnotationVisitor(final int a1, final AnnotationVisitor a2) {
        super();
        if (a1 != 262144 && a1 != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = a1;
        this.av = a2;
    }
    
    public void visit(final String a1, final Object a2) {
        if (this.av != null) {
            this.av.visit(a1, a2);
        }
    }
    
    public void visitEnum(final String a1, final String a2, final String a3) {
        if (this.av != null) {
            this.av.visitEnum(a1, a2, a3);
        }
    }
    
    public AnnotationVisitor visitAnnotation(final String a1, final String a2) {
        if (this.av != null) {
            return this.av.visitAnnotation(a1, a2);
        }
        return null;
    }
    
    public AnnotationVisitor visitArray(final String a1) {
        if (this.av != null) {
            return this.av.visitArray(a1);
        }
        return null;
    }
    
    public void visitEnd() {
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
}
