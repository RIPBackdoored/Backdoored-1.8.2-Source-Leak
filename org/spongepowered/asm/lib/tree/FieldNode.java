package org.spongepowered.asm.lib.tree;

import java.util.*;
import org.spongepowered.asm.lib.*;

public class FieldNode extends FieldVisitor
{
    public int access;
    public String name;
    public String desc;
    public String signature;
    public Object value;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    
    public FieldNode(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        this(327680, a1, a2, a3, a4, a5);
        if (this.getClass() != FieldNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public FieldNode(final int a1, final int a2, final String a3, final String a4, final String a5, final Object a6) {
        super(a1);
        this.access = a2;
        this.name = a3;
        this.desc = a4;
        this.signature = a5;
        this.value = a6;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        final AnnotationNode v1 = new AnnotationNode(a1);
        if (a2) {
            if (this.visibleAnnotations == null) {
                this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.visibleAnnotations.add(v1);
        }
        else {
            if (this.invisibleAnnotations == null) {
                this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
            }
            this.invisibleAnnotations.add(v1);
        }
        return v1;
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        final TypeAnnotationNode v1 = new TypeAnnotationNode(a1, a2, a3);
        if (a4) {
            if (this.visibleTypeAnnotations == null) {
                this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.visibleTypeAnnotations.add(v1);
        }
        else {
            if (this.invisibleTypeAnnotations == null) {
                this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
            }
            this.invisibleTypeAnnotations.add(v1);
        }
        return v1;
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        if (this.attrs == null) {
            this.attrs = new ArrayList<Attribute>(1);
        }
        this.attrs.add(a1);
    }
    
    @Override
    public void visitEnd() {
    }
    
    public void check(final int a1) {
        if (a1 == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
        }
    }
    
    public void accept(final ClassVisitor v-3) {
        final FieldVisitor visitField = v-3.visitField(this.access, this.name, this.desc, this.signature, this.value);
        if (visitField == null) {
            return;
        }
        for (int n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final AnnotationNode a1 = this.visibleAnnotations.get(v0);
            a1.accept(visitField.visitAnnotation(a1.desc, true));
        }
        for (int n = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final AnnotationNode v2 = this.invisibleAnnotations.get(v0);
            v2.accept(visitField.visitAnnotation(v2.desc, false));
        }
        for (int n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final TypeAnnotationNode v3 = this.visibleTypeAnnotations.get(v0);
            v3.accept(visitField.visitTypeAnnotation(v3.typeRef, v3.typePath, v3.desc, true));
        }
        for (int n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final TypeAnnotationNode v3 = this.invisibleTypeAnnotations.get(v0);
            v3.accept(visitField.visitTypeAnnotation(v3.typeRef, v3.typePath, v3.desc, false));
        }
        for (int n = (this.attrs == null) ? 0 : this.attrs.size(), v0 = 0; v0 < n; ++v0) {
            visitField.visitAttribute(this.attrs.get(v0));
        }
        visitField.visitEnd();
    }
}
