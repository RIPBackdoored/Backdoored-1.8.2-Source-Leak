package org.spongepowered.asm.lib.util;

import java.io.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class ASMifier extends Printer
{
    protected final String name;
    protected final int id;
    protected Map<Label, String> labelNames;
    private static final int ACCESS_CLASS = 262144;
    private static final int ACCESS_FIELD = 524288;
    private static final int ACCESS_INNER = 1048576;
    
    public ASMifier() {
        this(327680, "cw", 0);
        if (this.getClass() != ASMifier.class) {
            throw new IllegalStateException();
        }
    }
    
    protected ASMifier(final int a1, final String a2, final int a3) {
        super(a1);
        this.name = a2;
        this.id = a3;
    }
    
    public static void main(final String[] v1) throws Exception {
        int v2 = 0;
        int v3 = 2;
        boolean v4 = true;
        if (v1.length < 1 || v1.length > 2) {
            v4 = false;
        }
        if (v4 && "-debug".equals(v1[0])) {
            v2 = 1;
            v3 = 0;
            if (v1.length != 2) {
                v4 = false;
            }
        }
        if (!v4) {
            System.err.println("Prints the ASM code to generate the given class.");
            System.err.println("Usage: ASMifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        ClassReader v5 = null;
        if (v1[v2].endsWith(".class") || v1[v2].indexOf(92) > -1 || v1[v2].indexOf(47) > -1) {
            final ClassReader a1 = new ClassReader(new FileInputStream(v1[v2]));
        }
        else {
            v5 = new ClassReader(v1[v2]);
        }
        v5.accept(new TraceClassVisitor(null, new ASMifier(), new PrintWriter(System.out)), v3);
    }
    
    @Override
    public void visit(final int a4, final int a5, final String a6, final String v1, final String v2, final String[] v3) {
        final int v4 = a6.lastIndexOf(47);
        String v5 = null;
        if (v4 == -1) {
            final String a7 = a6;
        }
        else {
            this.text.add("package asm." + a6.substring(0, v4).replace('/', '.') + ";\n");
            v5 = a6.substring(v4 + 1);
        }
        this.text.add("import java.util.*;\n");
        this.text.add("import org.objectweb.asm.*;\n");
        this.text.add("public class " + v5 + "Dump implements Opcodes {\n\n");
        this.text.add("public static byte[] dump () throws Exception {\n\n");
        this.text.add("ClassWriter cw = new ClassWriter(0);\n");
        this.text.add("FieldVisitor fv;\n");
        this.text.add("MethodVisitor mv;\n");
        this.text.add("AnnotationVisitor av0;\n\n");
        this.buf.setLength(0);
        this.buf.append("cw.visit(");
        switch (a4) {
            case 196653: {
                this.buf.append("V1_1");
                break;
            }
            case 46: {
                this.buf.append("V1_2");
                break;
            }
            case 47: {
                this.buf.append("V1_3");
                break;
            }
            case 48: {
                this.buf.append("V1_4");
                break;
            }
            case 49: {
                this.buf.append("V1_5");
                break;
            }
            case 50: {
                this.buf.append("V1_6");
                break;
            }
            case 51: {
                this.buf.append("V1_7");
                break;
            }
            default: {
                this.buf.append(a4);
                break;
            }
        }
        this.buf.append(", ");
        this.appendAccess(a5 | 0x40000);
        this.buf.append(", ");
        this.appendConstant(a6);
        this.buf.append(", ");
        this.appendConstant(v1);
        this.buf.append(", ");
        this.appendConstant(v2);
        this.buf.append(", ");
        if (v3 != null && v3.length > 0) {
            this.buf.append("new String[] {");
            for (int a8 = 0; a8 < v3.length; ++a8) {
                this.buf.append((a8 == 0) ? " " : ", ");
                this.appendConstant(v3[a8]);
            }
            this.buf.append(" }");
        }
        else {
            this.buf.append("null");
        }
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitSource(final String a1, final String a2) {
        this.buf.setLength(0);
        this.buf.append("cw.visitSource(");
        this.appendConstant(a1);
        this.buf.append(", ");
        this.appendConstant(a2);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        this.buf.setLength(0);
        this.buf.append("cw.visitOuterClass(");
        this.appendConstant(a1);
        this.buf.append(", ");
        this.appendConstant(a2);
        this.buf.append(", ");
        this.appendConstant(a3);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitClassAnnotation(final String a1, final boolean a2) {
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public ASMifier visitClassTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public void visitClassAttribute(final Attribute a1) {
        this.visitAttribute(a1);
    }
    
    @Override
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        this.buf.setLength(0);
        this.buf.append("cw.visitInnerClass(");
        this.appendConstant(a1);
        this.buf.append(", ");
        this.appendConstant(a2);
        this.buf.append(", ");
        this.appendConstant(a3);
        this.buf.append(", ");
        this.appendAccess(a4 | 0x100000);
        this.buf.append(");\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("fv = cw.visitField(");
        this.appendAccess(a1 | 0x80000);
        this.buf.append(", ");
        this.appendConstant(a2);
        this.buf.append(", ");
        this.appendConstant(a3);
        this.buf.append(", ");
        this.appendConstant(a4);
        this.buf.append(", ");
        this.appendConstant(a5);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("fv", 0);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    @Override
    public ASMifier visitMethod(final int a3, final String a4, final String a5, final String v1, final String[] v2) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("mv = cw.visitMethod(");
        this.appendAccess(a3);
        this.buf.append(", ");
        this.appendConstant(a4);
        this.buf.append(", ");
        this.appendConstant(a5);
        this.buf.append(", ");
        this.appendConstant(v1);
        this.buf.append(", ");
        if (v2 != null && v2.length > 0) {
            this.buf.append("new String[] {");
            for (int a6 = 0; a6 < v2.length; ++a6) {
                this.buf.append((a6 == 0) ? " " : ", ");
                this.appendConstant(v2[a6]);
            }
            this.buf.append(" }");
        }
        else {
            this.buf.append("null");
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v3 = this.createASMifier("mv", 0);
        this.text.add(v3.getText());
        this.text.add("}\n");
        return v3;
    }
    
    @Override
    public void visitClassEnd() {
        this.text.add("cw.visitEnd();\n\n");
        this.text.add("return cw.toByteArray();\n");
        this.text.add("}\n");
        this.text.add("}\n");
    }
    
    @Override
    public void visit(final String a1, final Object a2) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visit(");
        appendConstant(this.buf, a1);
        this.buf.append(", ");
        appendConstant(this.buf, a2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visitEnum(");
        appendConstant(this.buf, a1);
        this.buf.append(", ");
        appendConstant(this.buf, a2);
        this.buf.append(", ");
        appendConstant(this.buf, a3);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitAnnotation(final String a1, final String a2) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
        this.buf.append(this.id).append(".visitAnnotation(");
        appendConstant(this.buf, a1);
        this.buf.append(", ");
        appendConstant(this.buf, a2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("av", this.id + 1);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    @Override
    public ASMifier visitArray(final String a1) {
        this.buf.setLength(0);
        this.buf.append("{\n");
        this.buf.append("AnnotationVisitor av").append(this.id + 1).append(" = av");
        this.buf.append(this.id).append(".visitArray(");
        appendConstant(this.buf, a1);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("av", this.id + 1);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    @Override
    public void visitAnnotationEnd() {
        this.buf.setLength(0);
        this.buf.append("av").append(this.id).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitFieldAnnotation(final String a1, final boolean a2) {
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public ASMifier visitFieldTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public void visitFieldAttribute(final Attribute a1) {
        this.visitAttribute(a1);
    }
    
    @Override
    public void visitFieldEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitParameter(final String a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitParameter(");
        Printer.appendString(this.buf, a1);
        this.buf.append(", ");
        this.appendAccess(a2);
        this.text.add(this.buf.append(");\n").toString());
    }
    
    @Override
    public ASMifier visitAnnotationDefault() {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotationDefault();\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("av", 0);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    @Override
    public ASMifier visitMethodAnnotation(final String a1, final boolean a2) {
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public ASMifier visitMethodTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public ASMifier visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitParameterAnnotation(").append(a1).append(", ");
        this.appendConstant(a2);
        this.buf.append(", ").append(a3).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("av", 0);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    @Override
    public void visitMethodAttribute(final Attribute a1) {
        this.visitAttribute(a1);
    }
    
    @Override
    public void visitCode() {
        this.text.add(this.name + ".visitCode();\n");
    }
    
    @Override
    public void visitFrame(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        this.buf.setLength(0);
        switch (a1) {
            case -1:
            case 0: {
                this.declareFrameTypes(a2, a3);
                this.declareFrameTypes(a4, a5);
                if (a1 == -1) {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_NEW, ");
                }
                else {
                    this.buf.append(this.name).append(".visitFrame(Opcodes.F_FULL, ");
                }
                this.buf.append(a2).append(", new Object[] {");
                this.appendFrameTypes(a2, a3);
                this.buf.append("}, ").append(a4).append(", new Object[] {");
                this.appendFrameTypes(a4, a5);
                this.buf.append('}');
                break;
            }
            case 1: {
                this.declareFrameTypes(a2, a3);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_APPEND,").append(a2).append(", new Object[] {");
                this.appendFrameTypes(a2, a3);
                this.buf.append("}, 0, null");
                break;
            }
            case 2: {
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_CHOP,").append(a2).append(", null, 0, null");
                break;
            }
            case 3: {
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME, 0, null, 0, null");
                break;
            }
            case 4: {
                this.declareFrameTypes(1, a5);
                this.buf.append(this.name).append(".visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {");
                this.appendFrameTypes(1, a5);
                this.buf.append('}');
                break;
            }
        }
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitInsn(final int a1) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInsn(").append(ASMifier.OPCODES[a1]).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitIntInsn(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIntInsn(").append(ASMifier.OPCODES[a1]).append(", ").append((a1 == 188) ? ASMifier.TYPES[a2] : Integer.toString(a2)).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitVarInsn(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitVarInsn(").append(ASMifier.OPCODES[a1]).append(", ").append(a2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitTypeInsn(final int a1, final String a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitTypeInsn(").append(ASMifier.OPCODES[a1]).append(", ");
        this.appendConstant(a2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitFieldInsn(").append(ASMifier.OPCODES[a1]).append(", ");
        this.appendConstant(a2);
        this.buf.append(", ");
        this.appendConstant(a3);
        this.buf.append(", ");
        this.appendConstant(a4);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Deprecated
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4) {
        if (this.api >= 327680) {
            super.visitMethodInsn(a1, a2, a3, a4);
            return;
        }
        this.doVisitMethodInsn(a1, a2, a3, a4, a1 == 185);
    }
    
    @Override
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.api < 327680) {
            super.visitMethodInsn(a1, a2, a3, a4, a5);
            return;
        }
        this.doVisitMethodInsn(a1, a2, a3, a4, a5);
    }
    
    private void doVisitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMethodInsn(").append(ASMifier.OPCODES[a1]).append(", ");
        this.appendConstant(a2);
        this.buf.append(", ");
        this.appendConstant(a3);
        this.buf.append(", ");
        this.appendConstant(a4);
        this.buf.append(", ");
        this.buf.append(a5 ? "true" : "false");
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String a3, final String a4, final Handle v1, final Object... v2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitInvokeDynamicInsn(");
        this.appendConstant(a3);
        this.buf.append(", ");
        this.appendConstant(a4);
        this.buf.append(", ");
        this.appendConstant(v1);
        this.buf.append(", new Object[]{");
        for (int a5 = 0; a5 < v2.length; ++a5) {
            this.appendConstant(v2[a5]);
            if (a5 != v2.length - 1) {
                this.buf.append(", ");
            }
        }
        this.buf.append("});\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitJumpInsn(final int a1, final Label a2) {
        this.buf.setLength(0);
        this.declareLabel(a2);
        this.buf.append(this.name).append(".visitJumpInsn(").append(ASMifier.OPCODES[a1]).append(", ");
        this.appendLabel(a2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLabel(final Label a1) {
        this.buf.setLength(0);
        this.declareLabel(a1);
        this.buf.append(this.name).append(".visitLabel(");
        this.appendLabel(a1);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLdcInsn(final Object a1) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLdcInsn(");
        this.appendConstant(a1);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitIincInsn(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitIincInsn(").append(a1).append(", ").append(a2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitTableSwitchInsn(final int a4, final int v1, final Label v2, final Label... v3) {
        this.buf.setLength(0);
        for (int a5 = 0; a5 < v3.length; ++a5) {
            this.declareLabel(v3[a5]);
        }
        this.declareLabel(v2);
        this.buf.append(this.name).append(".visitTableSwitchInsn(").append(a4).append(", ").append(v1).append(", ");
        this.appendLabel(v2);
        this.buf.append(", new Label[] {");
        for (int a6 = 0; a6 < v3.length; ++a6) {
            this.buf.append((a6 == 0) ? " " : ", ");
            this.appendLabel(v3[a6]);
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label v2, final int[] v3, final Label[] v4) {
        this.buf.setLength(0);
        for (int a1 = 0; a1 < v4.length; ++a1) {
            this.declareLabel(v4[a1]);
        }
        this.declareLabel(v2);
        this.buf.append(this.name).append(".visitLookupSwitchInsn(");
        this.appendLabel(v2);
        this.buf.append(", new int[] {");
        for (int a2 = 0; a2 < v3.length; ++a2) {
            this.buf.append((a2 == 0) ? " " : ", ").append(v3[a2]);
        }
        this.buf.append(" }, new Label[] {");
        for (int a3 = 0; a3 < v4.length; ++a3) {
            this.buf.append((a3 == 0) ? " " : ", ");
            this.appendLabel(v4[a3]);
        }
        this.buf.append(" });\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMultiANewArrayInsn(");
        this.appendConstant(a1);
        this.buf.append(", ").append(a2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation("visitInsnAnnotation", a1, a2, a3, a4);
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        this.buf.setLength(0);
        this.declareLabel(a1);
        this.declareLabel(a2);
        this.declareLabel(a3);
        this.buf.append(this.name).append(".visitTryCatchBlock(");
        this.appendLabel(a1);
        this.buf.append(", ");
        this.appendLabel(a2);
        this.buf.append(", ");
        this.appendLabel(a3);
        this.buf.append(", ");
        this.appendConstant(a4);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public ASMifier visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation("visitTryCatchAnnotation", a1, a2, a3, a4);
    }
    
    @Override
    public void visitLocalVariable(final String a1, final String a2, final String a3, final Label a4, final Label a5, final int a6) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLocalVariable(");
        this.appendConstant(a1);
        this.buf.append(", ");
        this.appendConstant(a2);
        this.buf.append(", ");
        this.appendConstant(a3);
        this.buf.append(", ");
        this.appendLabel(a4);
        this.buf.append(", ");
        this.appendLabel(a5);
        this.buf.append(", ").append(a6).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Printer visitLocalVariableAnnotation(final int a5, final TypePath a6, final Label[] a7, final Label[] v1, final int[] v2, final String v3, final boolean v4) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitLocalVariableAnnotation(");
        this.buf.append(a5);
        if (a6 == null) {
            this.buf.append(", null, ");
        }
        else {
            this.buf.append(", TypePath.fromString(\"").append(a6).append("\"), ");
        }
        this.buf.append("new Label[] {");
        for (int a8 = 0; a8 < a7.length; ++a8) {
            this.buf.append((a8 == 0) ? " " : ", ");
            this.appendLabel(a7[a8]);
        }
        this.buf.append(" }, new Label[] {");
        for (int a9 = 0; a9 < v1.length; ++a9) {
            this.buf.append((a9 == 0) ? " " : ", ");
            this.appendLabel(v1[a9]);
        }
        this.buf.append(" }, new int[] {");
        for (int a10 = 0; a10 < v2.length; ++a10) {
            this.buf.append((a10 == 0) ? " " : ", ").append(v2[a10]);
        }
        this.buf.append(" }, ");
        this.appendConstant(v3);
        this.buf.append(", ").append(v4).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v5 = this.createASMifier("av", 0);
        this.text.add(v5.getText());
        this.text.add("}\n");
        return v5;
    }
    
    @Override
    public void visitLineNumber(final int a1, final Label a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitLineNumber(").append(a1).append(", ");
        this.appendLabel(a2);
        this.buf.append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMaxs(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitMaxs(").append(a1).append(", ").append(a2).append(");\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMethodEnd() {
        this.buf.setLength(0);
        this.buf.append(this.name).append(".visitEnd();\n");
        this.text.add(this.buf.toString());
    }
    
    public ASMifier visitAnnotation(final String a1, final boolean a2) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".visitAnnotation(");
        this.appendConstant(a1);
        this.buf.append(", ").append(a2).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("av", 0);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    public ASMifier visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation("visitTypeAnnotation", a1, a2, a3, a4);
    }
    
    public ASMifier visitTypeAnnotation(final String a1, final int a2, final TypePath a3, final String a4, final boolean a5) {
        this.buf.setLength(0);
        this.buf.append("{\n").append("av0 = ").append(this.name).append(".").append(a1).append("(");
        this.buf.append(a2);
        if (a3 == null) {
            this.buf.append(", null, ");
        }
        else {
            this.buf.append(", TypePath.fromString(\"").append(a3).append("\"), ");
        }
        this.appendConstant(a4);
        this.buf.append(", ").append(a5).append(");\n");
        this.text.add(this.buf.toString());
        final ASMifier v1 = this.createASMifier("av", 0);
        this.text.add(v1.getText());
        this.text.add("}\n");
        return v1;
    }
    
    public void visitAttribute(final Attribute a1) {
        this.buf.setLength(0);
        this.buf.append("// ATTRIBUTE ").append(a1.type).append('\n');
        if (a1 instanceof ASMifiable) {
            if (this.labelNames == null) {
                this.labelNames = new HashMap<Label, String>();
            }
            this.buf.append("{\n");
            ((ASMifiable)a1).asmify(this.buf, "attr", this.labelNames);
            this.buf.append(this.name).append(".visitAttribute(attr);\n");
            this.buf.append("}\n");
        }
        this.text.add(this.buf.toString());
    }
    
    protected ASMifier createASMifier(final String a1, final int a2) {
        return new ASMifier(327680, a1, a2);
    }
    
    void appendAccess(final int a1) {
        boolean v1 = true;
        if ((a1 & 0x1) != 0x0) {
            this.buf.append("ACC_PUBLIC");
            v1 = false;
        }
        if ((a1 & 0x2) != 0x0) {
            this.buf.append("ACC_PRIVATE");
            v1 = false;
        }
        if ((a1 & 0x4) != 0x0) {
            this.buf.append("ACC_PROTECTED");
            v1 = false;
        }
        if ((a1 & 0x10) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_FINAL");
            v1 = false;
        }
        if ((a1 & 0x8) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STATIC");
            v1 = false;
        }
        if ((a1 & 0x20) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            if ((a1 & 0x40000) == 0x0) {
                this.buf.append("ACC_SYNCHRONIZED");
            }
            else {
                this.buf.append("ACC_SUPER");
            }
            v1 = false;
        }
        if ((a1 & 0x40) != 0x0 && (a1 & 0x80000) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VOLATILE");
            v1 = false;
        }
        if ((a1 & 0x40) != 0x0 && (a1 & 0x40000) == 0x0 && (a1 & 0x80000) == 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_BRIDGE");
            v1 = false;
        }
        if ((a1 & 0x80) != 0x0 && (a1 & 0x40000) == 0x0 && (a1 & 0x80000) == 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_VARARGS");
            v1 = false;
        }
        if ((a1 & 0x80) != 0x0 && (a1 & 0x80000) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_TRANSIENT");
            v1 = false;
        }
        if ((a1 & 0x100) != 0x0 && (a1 & 0x40000) == 0x0 && (a1 & 0x80000) == 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_NATIVE");
            v1 = false;
        }
        if ((a1 & 0x4000) != 0x0 && ((a1 & 0x40000) != 0x0 || (a1 & 0x80000) != 0x0 || (a1 & 0x100000) != 0x0)) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ENUM");
            v1 = false;
        }
        if ((a1 & 0x2000) != 0x0 && ((a1 & 0x40000) != 0x0 || (a1 & 0x100000) != 0x0)) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ANNOTATION");
            v1 = false;
        }
        if ((a1 & 0x400) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_ABSTRACT");
            v1 = false;
        }
        if ((a1 & 0x200) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_INTERFACE");
            v1 = false;
        }
        if ((a1 & 0x800) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_STRICT");
            v1 = false;
        }
        if ((a1 & 0x1000) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_SYNTHETIC");
            v1 = false;
        }
        if ((a1 & 0x20000) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_DEPRECATED");
            v1 = false;
        }
        if ((a1 & 0x8000) != 0x0) {
            if (!v1) {
                this.buf.append(" + ");
            }
            this.buf.append("ACC_MANDATED");
            v1 = false;
        }
        if (v1) {
            this.buf.append('0');
        }
    }
    
    protected void appendConstant(final Object a1) {
        appendConstant(this.buf, a1);
    }
    
    static void appendConstant(final StringBuffer v-2, final Object v-1) {
        if (v-1 == null) {
            v-2.append("null");
        }
        else if (v-1 instanceof String) {
            Printer.appendString(v-2, (String)v-1);
        }
        else if (v-1 instanceof Type) {
            v-2.append("Type.getType(\"");
            v-2.append(((Type)v-1).getDescriptor());
            v-2.append("\")");
        }
        else if (v-1 instanceof Handle) {
            v-2.append("new Handle(");
            final Handle a1 = (Handle)v-1;
            v-2.append("Opcodes.").append(ASMifier.HANDLE_TAG[a1.getTag()]).append(", \"");
            v-2.append(a1.getOwner()).append("\", \"");
            v-2.append(a1.getName()).append("\", \"");
            v-2.append(a1.getDesc()).append("\")");
        }
        else if (v-1 instanceof Byte) {
            v-2.append("new Byte((byte)").append(v-1).append(')');
        }
        else if (v-1 instanceof Boolean) {
            v-2.append(v-1 ? "Boolean.TRUE" : "Boolean.FALSE");
        }
        else if (v-1 instanceof Short) {
            v-2.append("new Short((short)").append(v-1).append(')');
        }
        else if (v-1 instanceof Character) {
            final int a2 = (char)v-1;
            v-2.append("new Character((char)").append(a2).append(')');
        }
        else if (v-1 instanceof Integer) {
            v-2.append("new Integer(").append(v-1).append(')');
        }
        else if (v-1 instanceof Float) {
            v-2.append("new Float(\"").append(v-1).append("\")");
        }
        else if (v-1 instanceof Long) {
            v-2.append("new Long(").append(v-1).append("L)");
        }
        else if (v-1 instanceof Double) {
            v-2.append("new Double(\"").append(v-1).append("\")");
        }
        else if (v-1 instanceof byte[]) {
            final byte[] v0 = (byte[])v-1;
            v-2.append("new byte[] {");
            for (int v2 = 0; v2 < v0.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append(v0[v2]);
            }
            v-2.append('}');
        }
        else if (v-1 instanceof boolean[]) {
            final boolean[] v3 = (boolean[])v-1;
            v-2.append("new boolean[] {");
            for (int v2 = 0; v2 < v3.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append(v3[v2]);
            }
            v-2.append('}');
        }
        else if (v-1 instanceof short[]) {
            final short[] v4 = (short[])v-1;
            v-2.append("new short[] {");
            for (int v2 = 0; v2 < v4.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append("(short)").append(v4[v2]);
            }
            v-2.append('}');
        }
        else if (v-1 instanceof char[]) {
            final char[] v5 = (char[])v-1;
            v-2.append("new char[] {");
            for (int v2 = 0; v2 < v5.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append("(char)").append((int)v5[v2]);
            }
            v-2.append('}');
        }
        else if (v-1 instanceof int[]) {
            final int[] v6 = (int[])v-1;
            v-2.append("new int[] {");
            for (int v2 = 0; v2 < v6.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append(v6[v2]);
            }
            v-2.append('}');
        }
        else if (v-1 instanceof long[]) {
            final long[] v7 = (long[])v-1;
            v-2.append("new long[] {");
            for (int v2 = 0; v2 < v7.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append(v7[v2]).append('L');
            }
            v-2.append('}');
        }
        else if (v-1 instanceof float[]) {
            final float[] v8 = (float[])v-1;
            v-2.append("new float[] {");
            for (int v2 = 0; v2 < v8.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append(v8[v2]).append('f');
            }
            v-2.append('}');
        }
        else if (v-1 instanceof double[]) {
            final double[] v9 = (double[])v-1;
            v-2.append("new double[] {");
            for (int v2 = 0; v2 < v9.length; ++v2) {
                v-2.append((v2 == 0) ? "" : ",").append(v9[v2]).append('d');
            }
            v-2.append('}');
        }
    }
    
    private void declareFrameTypes(final int v1, final Object[] v2) {
        for (int a1 = 0; a1 < v1; ++a1) {
            if (v2[a1] instanceof Label) {
                this.declareLabel((Label)v2[a1]);
            }
        }
    }
    
    private void appendFrameTypes(final int v1, final Object[] v2) {
        for (int a1 = 0; a1 < v1; ++a1) {
            if (a1 > 0) {
                this.buf.append(", ");
            }
            if (v2[a1] instanceof String) {
                this.appendConstant(v2[a1]);
            }
            else if (v2[a1] instanceof Integer) {
                switch ((int)v2[a1]) {
                    case 0: {
                        this.buf.append("Opcodes.TOP");
                        break;
                    }
                    case 1: {
                        this.buf.append("Opcodes.INTEGER");
                        break;
                    }
                    case 2: {
                        this.buf.append("Opcodes.FLOAT");
                        break;
                    }
                    case 3: {
                        this.buf.append("Opcodes.DOUBLE");
                        break;
                    }
                    case 4: {
                        this.buf.append("Opcodes.LONG");
                        break;
                    }
                    case 5: {
                        this.buf.append("Opcodes.NULL");
                        break;
                    }
                    case 6: {
                        this.buf.append("Opcodes.UNINITIALIZED_THIS");
                        break;
                    }
                }
            }
            else {
                this.appendLabel((Label)v2[a1]);
            }
        }
    }
    
    protected void declareLabel(final Label a1) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap<Label, String>();
        }
        String v1 = this.labelNames.get(a1);
        if (v1 == null) {
            v1 = "l" + this.labelNames.size();
            this.labelNames.put(a1, v1);
            this.buf.append("Label ").append(v1).append(" = new Label();\n");
        }
    }
    
    protected void appendLabel(final Label a1) {
        this.buf.append(this.labelNames.get(a1));
    }
    
    @Override
    public /* bridge */ Printer visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTryCatchAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public /* bridge */ Printer visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitInsnAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public /* bridge */ Printer visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        return this.visitParameterAnnotation(a1, a2, a3);
    }
    
    @Override
    public /* bridge */ Printer visitMethodTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitMethodTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public /* bridge */ Printer visitMethodAnnotation(final String a1, final boolean a2) {
        return this.visitMethodAnnotation(a1, a2);
    }
    
    @Override
    public /* bridge */ Printer visitAnnotationDefault() {
        return this.visitAnnotationDefault();
    }
    
    @Override
    public /* bridge */ Printer visitFieldTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitFieldTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public /* bridge */ Printer visitFieldAnnotation(final String a1, final boolean a2) {
        return this.visitFieldAnnotation(a1, a2);
    }
    
    @Override
    public /* bridge */ Printer visitArray(final String a1) {
        return this.visitArray(a1);
    }
    
    @Override
    public /* bridge */ Printer visitAnnotation(final String a1, final String a2) {
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public /* bridge */ Printer visitMethod(final int a3, final String a4, final String a5, final String v1, final String[] v2) {
        return this.visitMethod(a3, a4, a5, v1, v2);
    }
    
    @Override
    public /* bridge */ Printer visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        return this.visitField(a1, a2, a3, a4, a5);
    }
    
    @Override
    public /* bridge */ Printer visitClassTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitClassTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public /* bridge */ Printer visitClassAnnotation(final String a1, final boolean a2) {
        return this.visitClassAnnotation(a1, a2);
    }
}
