package org.spongepowered.asm.lib;

public abstract class ClassVisitor
{
    protected final int api;
    protected ClassVisitor cv;
    
    public ClassVisitor(final int a1) {
        this(a1, null);
    }
    
    public ClassVisitor(final int a1, final ClassVisitor a2) {
        super();
        if (a1 != 262144 && a1 != 327680) {
            throw new IllegalArgumentException();
        }
        this.api = a1;
        this.cv = a2;
    }
    
    public void visit(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        if (this.cv != null) {
            this.cv.visit(a1, a2, a3, a4, a5, a6);
        }
    }
    
    public void visitSource(final String a1, final String a2) {
        if (this.cv != null) {
            this.cv.visitSource(a1, a2);
        }
    }
    
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        if (this.cv != null) {
            this.cv.visitOuterClass(a1, a2, a3);
        }
    }
    
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        if (this.cv != null) {
            return this.cv.visitAnnotation(a1, a2);
        }
        return null;
    }
    
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        if (this.api < 327680) {
            throw new RuntimeException();
        }
        if (this.cv != null) {
            return this.cv.visitTypeAnnotation(a1, a2, a3, a4);
        }
        return null;
    }
    
    public void visitAttribute(final Attribute a1) {
        if (this.cv != null) {
            this.cv.visitAttribute(a1);
        }
    }
    
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        if (this.cv != null) {
            this.cv.visitInnerClass(a1, a2, a3, a4);
        }
    }
    
    public FieldVisitor visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        if (this.cv != null) {
            return this.cv.visitField(a1, a2, a3, a4, a5);
        }
        return null;
    }
    
    public MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        if (this.cv != null) {
            return this.cv.visitMethod(a1, a2, a3, a4, a5);
        }
        return null;
    }
    
    public void visitEnd() {
        if (this.cv != null) {
            this.cv.visitEnd();
        }
    }
}
