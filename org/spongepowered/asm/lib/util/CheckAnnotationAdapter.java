package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public class CheckAnnotationAdapter extends AnnotationVisitor
{
    private final boolean named;
    private boolean end;
    
    public CheckAnnotationAdapter(final AnnotationVisitor a1) {
        this(a1, true);
    }
    
    CheckAnnotationAdapter(final AnnotationVisitor a1, final boolean a2) {
        super(327680, a1);
        this.named = a2;
    }
    
    @Override
    public void visit(final String v1, final Object v2) {
        this.checkEnd();
        this.checkName(v1);
        if (!(v2 instanceof Byte) && !(v2 instanceof Boolean) && !(v2 instanceof Character) && !(v2 instanceof Short) && !(v2 instanceof Integer) && !(v2 instanceof Long) && !(v2 instanceof Float) && !(v2 instanceof Double) && !(v2 instanceof String) && !(v2 instanceof Type) && !(v2 instanceof byte[]) && !(v2 instanceof boolean[]) && !(v2 instanceof char[]) && !(v2 instanceof short[]) && !(v2 instanceof int[]) && !(v2 instanceof long[]) && !(v2 instanceof float[]) && !(v2 instanceof double[])) {
            throw new IllegalArgumentException("Invalid annotation value");
        }
        if (v2 instanceof Type) {
            final int a1 = ((Type)v2).getSort();
            if (a1 == 11) {
                throw new IllegalArgumentException("Invalid annotation value");
            }
        }
        if (this.av != null) {
            this.av.visit(v1, v2);
        }
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        this.checkEnd();
        this.checkName(a1);
        CheckMethodAdapter.checkDesc(a2, false);
        if (a3 == null) {
            throw new IllegalArgumentException("Invalid enum value");
        }
        if (this.av != null) {
            this.av.visitEnum(a1, a2, a3);
        }
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final String a2) {
        this.checkEnd();
        this.checkName(a1);
        CheckMethodAdapter.checkDesc(a2, false);
        return new CheckAnnotationAdapter((this.av == null) ? null : this.av.visitAnnotation(a1, a2));
    }
    
    @Override
    public AnnotationVisitor visitArray(final String a1) {
        this.checkEnd();
        this.checkName(a1);
        return new CheckAnnotationAdapter((this.av == null) ? null : this.av.visitArray(a1), false);
    }
    
    @Override
    public void visitEnd() {
        this.checkEnd();
        this.end = true;
        if (this.av != null) {
            this.av.visitEnd();
        }
    }
    
    private void checkEnd() {
        if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }
    
    private void checkName(final String a1) {
        if (this.named && a1 == null) {
            throw new IllegalArgumentException("Annotation value name must not be null");
        }
    }
}
