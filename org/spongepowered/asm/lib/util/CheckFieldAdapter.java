package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.*;

public class CheckFieldAdapter extends FieldVisitor
{
    private boolean end;
    
    public CheckFieldAdapter(final FieldVisitor a1) {
        this(327680, a1);
        if (this.getClass() != CheckFieldAdapter.class) {
            throw new IllegalStateException();
        }
    }
    
    protected CheckFieldAdapter(final int a1, final FieldVisitor a2) {
        super(a1, a2);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        this.checkEnd();
        CheckMethodAdapter.checkDesc(a1, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(a1, a2));
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.checkEnd();
        final int v1 = a1 >>> 24;
        if (v1 != 19) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v1));
        }
        CheckClassAdapter.checkTypeRefAndPath(a1, a2);
        CheckMethodAdapter.checkDesc(a3, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(a1, a2, a3, a4));
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        this.checkEnd();
        if (a1 == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(a1);
    }
    
    @Override
    public void visitEnd() {
        this.checkEnd();
        this.end = true;
        super.visitEnd();
    }
    
    private void checkEnd() {
        if (this.end) {
            throw new IllegalStateException("Cannot call a visit method after visitEnd has been called");
        }
    }
}
