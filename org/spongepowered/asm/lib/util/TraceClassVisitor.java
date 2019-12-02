package org.spongepowered.asm.lib.util;

import java.io.*;
import org.spongepowered.asm.lib.*;

public final class TraceClassVisitor extends ClassVisitor
{
    private final PrintWriter pw;
    public final Printer p;
    
    public TraceClassVisitor(final PrintWriter a1) {
        this(null, a1);
    }
    
    public TraceClassVisitor(final ClassVisitor a1, final PrintWriter a2) {
        this(a1, new Textifier(), a2);
    }
    
    public TraceClassVisitor(final ClassVisitor a1, final Printer a2, final PrintWriter a3) {
        super(327680, a1);
        this.pw = a3;
        this.p = a2;
    }
    
    @Override
    public void visit(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        this.p.visit(a1, a2, a3, a4, a5, a6);
        super.visit(a1, a2, a3, a4, a5, a6);
    }
    
    @Override
    public void visitSource(final String a1, final String a2) {
        this.p.visitSource(a1, a2);
        super.visitSource(a1, a2);
    }
    
    @Override
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        this.p.visitOuterClass(a1, a2, a3);
        super.visitOuterClass(a1, a2, a3);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final Printer v1 = this.p.visitClassAnnotation(a1, a2);
        final AnnotationVisitor v2 = (this.cv == null) ? null : this.cv.visitAnnotation(a1, a2);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final Printer v1 = this.p.visitClassTypeAnnotation(a1, a2, a3, a4);
        final AnnotationVisitor v2 = (this.cv == null) ? null : this.cv.visitTypeAnnotation(a1, a2, a3, a4);
        return new TraceAnnotationVisitor(v2, v1);
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        this.p.visitClassAttribute(a1);
        super.visitAttribute(a1);
    }
    
    @Override
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        this.p.visitInnerClass(a1, a2, a3, a4);
        super.visitInnerClass(a1, a2, a3, a4);
    }
    
    @Override
    public FieldVisitor visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        final Printer v1 = this.p.visitField(a1, a2, a3, a4, a5);
        final FieldVisitor v2 = (this.cv == null) ? null : this.cv.visitField(a1, a2, a3, a4, a5);
        return new TraceFieldVisitor(v2, v1);
    }
    
    @Override
    public MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        final Printer v1 = this.p.visitMethod(a1, a2, a3, a4, a5);
        final MethodVisitor v2 = (this.cv == null) ? null : this.cv.visitMethod(a1, a2, a3, a4, a5);
        return new TraceMethodVisitor(v2, v1);
    }
    
    @Override
    public void visitEnd() {
        this.p.visitClassEnd();
        if (this.pw != null) {
            this.p.print(this.pw);
            this.pw.flush();
        }
        super.visitEnd();
    }
}
