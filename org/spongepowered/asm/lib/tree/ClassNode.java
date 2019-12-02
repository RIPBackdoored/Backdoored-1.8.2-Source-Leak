package org.spongepowered.asm.lib.tree;

import org.spongepowered.asm.lib.*;
import java.util.*;

public class ClassNode extends ClassVisitor
{
    public int version;
    public int access;
    public String name;
    public String signature;
    public String superName;
    public List<String> interfaces;
    public String sourceFile;
    public String sourceDebug;
    public String outerClass;
    public String outerMethod;
    public String outerMethodDesc;
    public List<AnnotationNode> visibleAnnotations;
    public List<AnnotationNode> invisibleAnnotations;
    public List<TypeAnnotationNode> visibleTypeAnnotations;
    public List<TypeAnnotationNode> invisibleTypeAnnotations;
    public List<Attribute> attrs;
    public List<InnerClassNode> innerClasses;
    public List<FieldNode> fields;
    public List<MethodNode> methods;
    
    public ClassNode() {
        this(327680);
        if (this.getClass() != ClassNode.class) {
            throw new IllegalStateException();
        }
    }
    
    public ClassNode(final int a1) {
        super(a1);
        this.interfaces = new ArrayList<String>();
        this.innerClasses = new ArrayList<InnerClassNode>();
        this.fields = new ArrayList<FieldNode>();
        this.methods = new ArrayList<MethodNode>();
    }
    
    @Override
    public void visit(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
        this.version = a1;
        this.access = a2;
        this.name = a3;
        this.signature = a4;
        this.superName = a5;
        if (a6 != null) {
            this.interfaces.addAll(Arrays.asList(a6));
        }
    }
    
    @Override
    public void visitSource(final String a1, final String a2) {
        this.sourceFile = a1;
        this.sourceDebug = a2;
    }
    
    @Override
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        this.outerClass = a1;
        this.outerMethod = a2;
        this.outerMethodDesc = a3;
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
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        final InnerClassNode v1 = new InnerClassNode(a1, a2, a3, a4);
        this.innerClasses.add(v1);
    }
    
    @Override
    public FieldVisitor visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        final FieldNode v1 = new FieldNode(a1, a2, a3, a4, a5);
        this.fields.add(v1);
        return v1;
    }
    
    @Override
    public MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
        final MethodNode v1 = new MethodNode(a1, a2, a3, a4, a5);
        this.methods.add(v1);
        return v1;
    }
    
    @Override
    public void visitEnd() {
    }
    
    public void check(final int v-1) {
        if (v-1 == 262144) {
            if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
                throw new RuntimeException();
            }
            for (final FieldNode a1 : this.fields) {
                a1.check(v-1);
            }
            for (final MethodNode v1 : this.methods) {
                v1.check(v-1);
            }
        }
    }
    
    public void accept(final ClassVisitor v-3) {
        final String[] a2 = new String[this.interfaces.size()];
        this.interfaces.toArray(a2);
        v-3.visit(this.version, this.access, this.name, this.signature, this.superName, a2);
        if (this.sourceFile != null || this.sourceDebug != null) {
            v-3.visitSource(this.sourceFile, this.sourceDebug);
        }
        if (this.outerClass != null) {
            v-3.visitOuterClass(this.outerClass, this.outerMethod, this.outerMethodDesc);
        }
        for (int n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final AnnotationNode a1 = this.visibleAnnotations.get(v0);
            a1.accept(v-3.visitAnnotation(a1.desc, true));
        }
        for (int n = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final AnnotationNode v2 = this.invisibleAnnotations.get(v0);
            v2.accept(v-3.visitAnnotation(v2.desc, false));
        }
        for (int n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final TypeAnnotationNode v3 = this.visibleTypeAnnotations.get(v0);
            v3.accept(v-3.visitTypeAnnotation(v3.typeRef, v3.typePath, v3.desc, true));
        }
        for (int n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size(), v0 = 0; v0 < n; ++v0) {
            final TypeAnnotationNode v3 = this.invisibleTypeAnnotations.get(v0);
            v3.accept(v-3.visitTypeAnnotation(v3.typeRef, v3.typePath, v3.desc, false));
        }
        for (int n = (this.attrs == null) ? 0 : this.attrs.size(), v0 = 0; v0 < n; ++v0) {
            v-3.visitAttribute(this.attrs.get(v0));
        }
        for (int v0 = 0; v0 < this.innerClasses.size(); ++v0) {
            this.innerClasses.get(v0).accept(v-3);
        }
        for (int v0 = 0; v0 < this.fields.size(); ++v0) {
            this.fields.get(v0).accept(v-3);
        }
        for (int v0 = 0; v0 < this.methods.size(); ++v0) {
            this.methods.get(v0).accept(v-3);
        }
        v-3.visitEnd();
    }
}
