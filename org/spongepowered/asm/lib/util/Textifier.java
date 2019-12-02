package org.spongepowered.asm.lib.util;

import java.io.*;
import org.spongepowered.asm.lib.signature.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class Textifier extends Printer
{
    public static final int INTERNAL_NAME = 0;
    public static final int FIELD_DESCRIPTOR = 1;
    public static final int FIELD_SIGNATURE = 2;
    public static final int METHOD_DESCRIPTOR = 3;
    public static final int METHOD_SIGNATURE = 4;
    public static final int CLASS_SIGNATURE = 5;
    public static final int TYPE_DECLARATION = 6;
    public static final int CLASS_DECLARATION = 7;
    public static final int PARAMETERS_DECLARATION = 8;
    public static final int HANDLE_DESCRIPTOR = 9;
    protected String tab;
    protected String tab2;
    protected String tab3;
    protected String ltab;
    protected Map<Label, String> labelNames;
    private int access;
    private int valueNumber;
    
    public Textifier() {
        this(327680);
        if (this.getClass() != Textifier.class) {
            throw new IllegalStateException();
        }
    }
    
    protected Textifier(final int a1) {
        super(a1);
        this.tab = "  ";
        this.tab2 = "    ";
        this.tab3 = "      ";
        this.ltab = "   ";
        this.valueNumber = 0;
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
            System.err.println("Prints a disassembled view of the given class.");
            System.err.println("Usage: Textifier [-debug] <fully qualified class name or class file name>");
            return;
        }
        ClassReader v5 = null;
        if (v1[v2].endsWith(".class") || v1[v2].indexOf(92) > -1 || v1[v2].indexOf(47) > -1) {
            final ClassReader a1 = new ClassReader(new FileInputStream(v1[v2]));
        }
        else {
            v5 = new ClassReader(v1[v2]);
        }
        v5.accept(new TraceClassVisitor(new PrintWriter(System.out)), v3);
    }
    
    @Override
    public void visit(final int a5, final int a6, final String v1, final String v2, final String v3, final String[] v4) {
        this.access = a6;
        final int v5 = a5 & 0xFFFF;
        final int v6 = a5 >>> 16;
        this.buf.setLength(0);
        this.buf.append("// class version ").append(v5).append('.').append(v6).append(" (").append(a5).append(")\n");
        if ((a6 & 0x20000) != 0x0) {
            this.buf.append("// DEPRECATED\n");
        }
        this.buf.append("// access flags 0x").append(Integer.toHexString(a6).toUpperCase()).append('\n');
        this.appendDescriptor(5, v2);
        if (v2 != null) {
            final TraceSignatureVisitor a7 = new TraceSignatureVisitor(a6);
            final SignatureReader a8 = new SignatureReader(v2);
            a8.accept(a7);
            this.buf.append("// declaration: ").append(v1).append(a7.getDeclaration()).append('\n');
        }
        this.appendAccess(a6 & 0xFFFFFFDF);
        if ((a6 & 0x2000) != 0x0) {
            this.buf.append("@interface ");
        }
        else if ((a6 & 0x200) != 0x0) {
            this.buf.append("interface ");
        }
        else if ((a6 & 0x4000) == 0x0) {
            this.buf.append("class ");
        }
        this.appendDescriptor(0, v1);
        if (v3 != null && !"java/lang/Object".equals(v3)) {
            this.buf.append(" extends ");
            this.appendDescriptor(0, v3);
            this.buf.append(' ');
        }
        if (v4 != null && v4.length > 0) {
            this.buf.append(" implements ");
            for (int a9 = 0; a9 < v4.length; ++a9) {
                this.appendDescriptor(0, v4[a9]);
                this.buf.append(' ');
            }
        }
        this.buf.append(" {\n\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitSource(final String a1, final String a2) {
        this.buf.setLength(0);
        if (a1 != null) {
            this.buf.append(this.tab).append("// compiled from: ").append(a1).append('\n');
        }
        if (a2 != null) {
            this.buf.append(this.tab).append("// debug info: ").append(a2).append('\n');
        }
        if (this.buf.length() > 0) {
            this.text.add(this.buf.toString());
        }
    }
    
    @Override
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("OUTERCLASS ");
        this.appendDescriptor(0, a1);
        this.buf.append(' ');
        if (a2 != null) {
            this.buf.append(a2).append(' ');
        }
        this.appendDescriptor(3, a3);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Textifier visitClassAnnotation(final String a1, final boolean a2) {
        this.text.add("\n");
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public Printer visitClassTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.text.add("\n");
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public void visitClassAttribute(final Attribute a1) {
        this.text.add("\n");
        this.visitAttribute(a1);
    }
    
    @Override
    public void visitInnerClass(final String a1, final String a2, final String a3, final int a4) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("// access flags 0x");
        this.buf.append(Integer.toHexString(a4 & 0xFFFFFFDF).toUpperCase()).append('\n');
        this.buf.append(this.tab);
        this.appendAccess(a4);
        this.buf.append("INNERCLASS ");
        this.appendDescriptor(0, a1);
        this.buf.append(' ');
        this.appendDescriptor(0, a2);
        this.buf.append(' ');
        this.appendDescriptor(0, a3);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Textifier visitField(final int a4, final String a5, final String v1, final String v2, final Object v3) {
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((a4 & 0x20000) != 0x0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(a4).toUpperCase()).append('\n');
        if (v2 != null) {
            this.buf.append(this.tab);
            this.appendDescriptor(2, v2);
            final TraceSignatureVisitor a6 = new TraceSignatureVisitor(0);
            final SignatureReader a7 = new SignatureReader(v2);
            a7.acceptType(a6);
            this.buf.append(this.tab).append("// declaration: ").append(a6.getDeclaration()).append('\n');
        }
        this.buf.append(this.tab);
        this.appendAccess(a4);
        this.appendDescriptor(1, v1);
        this.buf.append(' ').append(a5);
        if (v3 != null) {
            this.buf.append(" = ");
            if (v3 instanceof String) {
                this.buf.append('\"').append(v3).append('\"');
            }
            else {
                this.buf.append(v3);
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        final Textifier v4 = this.createTextifier();
        this.text.add(v4.getText());
        return v4;
    }
    
    @Override
    public Textifier visitMethod(final int v-4, final String v-3, final String v-2, final String v-1, final String[] v0) {
        this.buf.setLength(0);
        this.buf.append('\n');
        if ((v-4 & 0x20000) != 0x0) {
            this.buf.append(this.tab).append("// DEPRECATED\n");
        }
        this.buf.append(this.tab).append("// access flags 0x").append(Integer.toHexString(v-4).toUpperCase()).append('\n');
        if (v-1 != null) {
            this.buf.append(this.tab);
            this.appendDescriptor(4, v-1);
            final TraceSignatureVisitor a1 = new TraceSignatureVisitor(0);
            final SignatureReader a2 = new SignatureReader(v-1);
            a2.accept(a1);
            final String a3 = a1.getDeclaration();
            final String a4 = a1.getReturnType();
            final String a5 = a1.getExceptions();
            this.buf.append(this.tab).append("// declaration: ").append(a4).append(' ').append(v-3).append(a3);
            if (a5 != null) {
                this.buf.append(" throws ").append(a5);
            }
            this.buf.append('\n');
        }
        this.buf.append(this.tab);
        this.appendAccess(v-4 & 0xFFFFFFBF);
        if ((v-4 & 0x100) != 0x0) {
            this.buf.append("native ");
        }
        if ((v-4 & 0x80) != 0x0) {
            this.buf.append("varargs ");
        }
        if ((v-4 & 0x40) != 0x0) {
            this.buf.append("bridge ");
        }
        if ((this.access & 0x200) != 0x0 && (v-4 & 0x400) == 0x0 && (v-4 & 0x8) == 0x0) {
            this.buf.append("default ");
        }
        this.buf.append(v-3);
        this.appendDescriptor(3, v-2);
        if (v0 != null && v0.length > 0) {
            this.buf.append(" throws ");
            for (int v = 0; v < v0.length; ++v) {
                this.appendDescriptor(0, v0[v]);
                this.buf.append(' ');
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
        final Textifier v2 = this.createTextifier();
        this.text.add(v2.getText());
        return v2;
    }
    
    @Override
    public void visitClassEnd() {
        this.text.add("}\n");
    }
    
    @Override
    public void visit(final String v-2, final Object v-1) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (v-2 != null) {
            this.buf.append(v-2).append('=');
        }
        if (v-1 instanceof String) {
            this.visitString((String)v-1);
        }
        else if (v-1 instanceof Type) {
            this.visitType((Type)v-1);
        }
        else if (v-1 instanceof Byte) {
            this.visitByte((byte)v-1);
        }
        else if (v-1 instanceof Boolean) {
            this.visitBoolean((boolean)v-1);
        }
        else if (v-1 instanceof Short) {
            this.visitShort((short)v-1);
        }
        else if (v-1 instanceof Character) {
            this.visitChar((char)v-1);
        }
        else if (v-1 instanceof Integer) {
            this.visitInt((int)v-1);
        }
        else if (v-1 instanceof Float) {
            this.visitFloat((float)v-1);
        }
        else if (v-1 instanceof Long) {
            this.visitLong((long)v-1);
        }
        else if (v-1 instanceof Double) {
            this.visitDouble((double)v-1);
        }
        else if (v-1.getClass().isArray()) {
            this.buf.append('{');
            if (v-1 instanceof byte[]) {
                final byte[] a2 = (byte[])v-1;
                for (int a3 = 0; a3 < a2.length; ++a3) {
                    this.appendComa(a3);
                    this.visitByte(a2[a3]);
                }
            }
            else if (v-1 instanceof boolean[]) {
                final boolean[] v0 = (boolean[])v-1;
                for (int v2 = 0; v2 < v0.length; ++v2) {
                    this.appendComa(v2);
                    this.visitBoolean(v0[v2]);
                }
            }
            else if (v-1 instanceof short[]) {
                final short[] v3 = (short[])v-1;
                for (int v2 = 0; v2 < v3.length; ++v2) {
                    this.appendComa(v2);
                    this.visitShort(v3[v2]);
                }
            }
            else if (v-1 instanceof char[]) {
                final char[] v4 = (char[])v-1;
                for (int v2 = 0; v2 < v4.length; ++v2) {
                    this.appendComa(v2);
                    this.visitChar(v4[v2]);
                }
            }
            else if (v-1 instanceof int[]) {
                final int[] v5 = (int[])v-1;
                for (int v2 = 0; v2 < v5.length; ++v2) {
                    this.appendComa(v2);
                    this.visitInt(v5[v2]);
                }
            }
            else if (v-1 instanceof long[]) {
                final long[] v6 = (long[])v-1;
                for (int v2 = 0; v2 < v6.length; ++v2) {
                    this.appendComa(v2);
                    this.visitLong(v6[v2]);
                }
            }
            else if (v-1 instanceof float[]) {
                final float[] v7 = (float[])v-1;
                for (int v2 = 0; v2 < v7.length; ++v2) {
                    this.appendComa(v2);
                    this.visitFloat(v7[v2]);
                }
            }
            else if (v-1 instanceof double[]) {
                final double[] v8 = (double[])v-1;
                for (int v2 = 0; v2 < v8.length; ++v2) {
                    this.appendComa(v2);
                    this.visitDouble(v8[v2]);
                }
            }
            this.buf.append('}');
        }
        this.text.add(this.buf.toString());
    }
    
    private void visitInt(final int a1) {
        this.buf.append(a1);
    }
    
    private void visitLong(final long a1) {
        this.buf.append(a1).append('L');
    }
    
    private void visitFloat(final float a1) {
        this.buf.append(a1).append('F');
    }
    
    private void visitDouble(final double a1) {
        this.buf.append(a1).append('D');
    }
    
    private void visitChar(final char a1) {
        this.buf.append("(char)").append((int)a1);
    }
    
    private void visitShort(final short a1) {
        this.buf.append("(short)").append(a1);
    }
    
    private void visitByte(final byte a1) {
        this.buf.append("(byte)").append(a1);
    }
    
    private void visitBoolean(final boolean a1) {
        this.buf.append(a1);
    }
    
    private void visitString(final String a1) {
        Printer.appendString(this.buf, a1);
    }
    
    private void visitType(final Type a1) {
        this.buf.append(a1.getClassName()).append(".class");
    }
    
    @Override
    public void visitEnum(final String a1, final String a2, final String a3) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (a1 != null) {
            this.buf.append(a1).append('=');
        }
        this.appendDescriptor(1, a2);
        this.buf.append('.').append(a3);
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Textifier visitAnnotation(final String a1, final String a2) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (a1 != null) {
            this.buf.append(a1).append('=');
        }
        this.buf.append('@');
        this.appendDescriptor(1, a2);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.text.add(")");
        return v1;
    }
    
    @Override
    public Textifier visitArray(final String a1) {
        this.buf.setLength(0);
        this.appendComa(this.valueNumber++);
        if (a1 != null) {
            this.buf.append(a1).append('=');
        }
        this.buf.append('{');
        this.text.add(this.buf.toString());
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.text.add("}");
        return v1;
    }
    
    @Override
    public void visitAnnotationEnd() {
    }
    
    @Override
    public Textifier visitFieldAnnotation(final String a1, final boolean a2) {
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public Printer visitFieldTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public void visitFieldAttribute(final Attribute a1) {
        this.visitAttribute(a1);
    }
    
    @Override
    public void visitFieldEnd() {
    }
    
    @Override
    public void visitParameter(final String a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("// parameter ");
        this.appendAccess(a2);
        this.buf.append(' ').append((a1 == null) ? "<no name>" : a1).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Textifier visitAnnotationDefault() {
        this.text.add(this.tab2 + "default=");
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.text.add("\n");
        return v1;
    }
    
    @Override
    public Textifier visitMethodAnnotation(final String a1, final boolean a2) {
        return this.visitAnnotation(a1, a2);
    }
    
    @Override
    public Printer visitMethodTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public Textifier visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append('@');
        this.appendDescriptor(1, a2);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.text.add(a3 ? ") // parameter " : ") // invisible, parameter ");
        this.text.add(a1);
        this.text.add("\n");
        return v1;
    }
    
    @Override
    public void visitMethodAttribute(final Attribute a1) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        this.appendDescriptor(-1, a1.type);
        if (a1 instanceof Textifiable) {
            ((Textifiable)a1).textify(this.buf, this.labelNames);
        }
        else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitCode() {
    }
    
    @Override
    public void visitFrame(final int a1, final int a2, final Object[] a3, final int a4, final Object[] a5) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.buf.append("FRAME ");
        switch (a1) {
            case -1:
            case 0: {
                this.buf.append("FULL [");
                this.appendFrameTypes(a2, a3);
                this.buf.append("] [");
                this.appendFrameTypes(a4, a5);
                this.buf.append(']');
                break;
            }
            case 1: {
                this.buf.append("APPEND [");
                this.appendFrameTypes(a2, a3);
                this.buf.append(']');
                break;
            }
            case 2: {
                this.buf.append("CHOP ").append(a2);
                break;
            }
            case 3: {
                this.buf.append("SAME");
                break;
            }
            case 4: {
                this.buf.append("SAME1 ");
                this.appendFrameTypes(1, a5);
                break;
            }
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitInsn(final int a1) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitIntInsn(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append(' ').append((a1 == 188) ? Textifier.TYPES[a2] : Integer.toString(a2)).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitVarInsn(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append(' ').append(a2).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitTypeInsn(final int a1, final String a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append(' ');
        this.appendDescriptor(0, a2);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append(' ');
        this.appendDescriptor(0, a2);
        this.buf.append('.').append(a3).append(" : ");
        this.appendDescriptor(1, a4);
        this.buf.append('\n');
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
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append(' ');
        this.appendDescriptor(0, a2);
        this.buf.append('.').append(a3).append(' ');
        this.appendDescriptor(3, a4);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String v1, final String v2, final Handle v3, final Object... v4) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("INVOKEDYNAMIC").append(' ');
        this.buf.append(v1);
        this.appendDescriptor(3, v2);
        this.buf.append(" [");
        this.buf.append('\n');
        this.buf.append(this.tab3);
        this.appendHandle(v3);
        this.buf.append('\n');
        this.buf.append(this.tab3).append("// arguments:");
        if (v4.length == 0) {
            this.buf.append(" none");
        }
        else {
            this.buf.append('\n');
            for (int a3 = 0; a3 < v4.length; ++a3) {
                this.buf.append(this.tab3);
                final Object a4 = v4[a3];
                if (a4 instanceof String) {
                    Printer.appendString(this.buf, (String)a4);
                }
                else if (a4 instanceof Type) {
                    final Type a5 = (Type)a4;
                    if (a5.getSort() == 11) {
                        this.appendDescriptor(3, a5.getDescriptor());
                    }
                    else {
                        this.buf.append(a5.getDescriptor()).append(".class");
                    }
                }
                else if (a4 instanceof Handle) {
                    this.appendHandle((Handle)a4);
                }
                else {
                    this.buf.append(a4);
                }
                this.buf.append(", \n");
            }
            this.buf.setLength(this.buf.length() - 3);
        }
        this.buf.append('\n');
        this.buf.append(this.tab2).append("]\n");
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitJumpInsn(final int a1, final Label a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append(Textifier.OPCODES[a1]).append(' ');
        this.appendLabel(a2);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLabel(final Label a1) {
        this.buf.setLength(0);
        this.buf.append(this.ltab);
        this.appendLabel(a1);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLdcInsn(final Object a1) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LDC ");
        if (a1 instanceof String) {
            Printer.appendString(this.buf, (String)a1);
        }
        else if (a1 instanceof Type) {
            this.buf.append(((Type)a1).getDescriptor()).append(".class");
        }
        else {
            this.buf.append(a1);
        }
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitIincInsn(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("IINC ").append(a1).append(' ').append(a2).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitTableSwitchInsn(final int a3, final int a4, final Label v1, final Label... v2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TABLESWITCH\n");
        for (int a5 = 0; a5 < v2.length; ++a5) {
            this.buf.append(this.tab3).append(a3 + a5).append(": ");
            this.appendLabel(v2[a5]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        this.appendLabel(v1);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label a3, final int[] v1, final Label[] v2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOOKUPSWITCH\n");
        for (int a4 = 0; a4 < v2.length; ++a4) {
            this.buf.append(this.tab3).append(v1[a4]).append(": ");
            this.appendLabel(v2[a4]);
            this.buf.append('\n');
        }
        this.buf.append(this.tab3).append("default: ");
        this.appendLabel(a3);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MULTIANEWARRAY ");
        this.appendDescriptor(1, a1);
        this.buf.append(' ').append(a2).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Printer visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        return this.visitTypeAnnotation(a1, a2, a3, a4);
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK ");
        this.appendLabel(a1);
        this.buf.append(' ');
        this.appendLabel(a2);
        this.buf.append(' ');
        this.appendLabel(a3);
        this.buf.append(' ');
        this.appendDescriptor(0, a4);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Printer visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("TRYCATCHBLOCK @");
        this.appendDescriptor(1, a3);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(a1);
        this.buf.append(", ").append(a2);
        this.buf.append(a4 ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return v1;
    }
    
    @Override
    public void visitLocalVariable(final String a4, final String a5, final String a6, final Label v1, final Label v2, final int v3) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE ").append(a4).append(' ');
        this.appendDescriptor(1, a5);
        this.buf.append(' ');
        this.appendLabel(v1);
        this.buf.append(' ');
        this.appendLabel(v2);
        this.buf.append(' ').append(v3).append('\n');
        if (a6 != null) {
            this.buf.append(this.tab2);
            this.appendDescriptor(2, a6);
            final TraceSignatureVisitor a7 = new TraceSignatureVisitor(0);
            final SignatureReader a8 = new SignatureReader(a6);
            a8.acceptType(a7);
            this.buf.append(this.tab2).append("// declaration: ").append(a7.getDeclaration()).append('\n');
        }
        this.text.add(this.buf.toString());
    }
    
    @Override
    public Printer visitLocalVariableAnnotation(final int a3, final TypePath a4, final Label[] a5, final Label[] a6, final int[] a7, final String v1, final boolean v2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LOCALVARIABLE @");
        this.appendDescriptor(1, v1);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        final Textifier v3 = this.createTextifier();
        this.text.add(v3.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(a3);
        this.buf.append(", ").append(a4);
        for (int a8 = 0; a8 < a5.length; ++a8) {
            this.buf.append(" [ ");
            this.appendLabel(a5[a8]);
            this.buf.append(" - ");
            this.appendLabel(a6[a8]);
            this.buf.append(" - ").append(a7[a8]).append(" ]");
        }
        this.buf.append(v2 ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return v3;
    }
    
    @Override
    public void visitLineNumber(final int a1, final Label a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("LINENUMBER ").append(a1).append(' ');
        this.appendLabel(a2);
        this.buf.append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMaxs(final int a1, final int a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXSTACK = ").append(a1).append('\n');
        this.text.add(this.buf.toString());
        this.buf.setLength(0);
        this.buf.append(this.tab2).append("MAXLOCALS = ").append(a2).append('\n');
        this.text.add(this.buf.toString());
    }
    
    @Override
    public void visitMethodEnd() {
    }
    
    public Textifier visitAnnotation(final String a1, final boolean a2) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        this.appendDescriptor(1, a1);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.text.add(a2 ? ")\n" : ") // invisible\n");
        return v1;
    }
    
    public Textifier visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append('@');
        this.appendDescriptor(1, a3);
        this.buf.append('(');
        this.text.add(this.buf.toString());
        final Textifier v1 = this.createTextifier();
        this.text.add(v1.getText());
        this.buf.setLength(0);
        this.buf.append(") : ");
        this.appendTypeReference(a1);
        this.buf.append(", ").append(a2);
        this.buf.append(a4 ? "\n" : " // invisible\n");
        this.text.add(this.buf.toString());
        return v1;
    }
    
    public void visitAttribute(final Attribute a1) {
        this.buf.setLength(0);
        this.buf.append(this.tab).append("ATTRIBUTE ");
        this.appendDescriptor(-1, a1.type);
        if (a1 instanceof Textifiable) {
            ((Textifiable)a1).textify(this.buf, null);
        }
        else {
            this.buf.append(" : unknown\n");
        }
        this.text.add(this.buf.toString());
    }
    
    protected Textifier createTextifier() {
        return new Textifier();
    }
    
    protected void appendDescriptor(final int a1, final String a2) {
        if (a1 == 5 || a1 == 2 || a1 == 4) {
            if (a2 != null) {
                this.buf.append("// signature ").append(a2).append('\n');
            }
        }
        else {
            this.buf.append(a2);
        }
    }
    
    protected void appendLabel(final Label a1) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap<Label, String>();
        }
        String v1 = this.labelNames.get(a1);
        if (v1 == null) {
            v1 = "L" + this.labelNames.size();
            this.labelNames.put(a1, v1);
        }
        this.buf.append(v1);
    }
    
    protected void appendHandle(final Handle a1) {
        final int v1 = a1.getTag();
        this.buf.append("// handle kind 0x").append(Integer.toHexString(v1)).append(" : ");
        boolean v2 = false;
        switch (v1) {
            case 1: {
                this.buf.append("GETFIELD");
                break;
            }
            case 2: {
                this.buf.append("GETSTATIC");
                break;
            }
            case 3: {
                this.buf.append("PUTFIELD");
                break;
            }
            case 4: {
                this.buf.append("PUTSTATIC");
                break;
            }
            case 9: {
                this.buf.append("INVOKEINTERFACE");
                v2 = true;
                break;
            }
            case 7: {
                this.buf.append("INVOKESPECIAL");
                v2 = true;
                break;
            }
            case 6: {
                this.buf.append("INVOKESTATIC");
                v2 = true;
                break;
            }
            case 5: {
                this.buf.append("INVOKEVIRTUAL");
                v2 = true;
                break;
            }
            case 8: {
                this.buf.append("NEWINVOKESPECIAL");
                v2 = true;
                break;
            }
        }
        this.buf.append('\n');
        this.buf.append(this.tab3);
        this.appendDescriptor(0, a1.getOwner());
        this.buf.append('.');
        this.buf.append(a1.getName());
        if (!v2) {
            this.buf.append('(');
        }
        this.appendDescriptor(9, a1.getDesc());
        if (!v2) {
            this.buf.append(')');
        }
    }
    
    private void appendAccess(final int a1) {
        if ((a1 & 0x1) != 0x0) {
            this.buf.append("public ");
        }
        if ((a1 & 0x2) != 0x0) {
            this.buf.append("private ");
        }
        if ((a1 & 0x4) != 0x0) {
            this.buf.append("protected ");
        }
        if ((a1 & 0x10) != 0x0) {
            this.buf.append("final ");
        }
        if ((a1 & 0x8) != 0x0) {
            this.buf.append("static ");
        }
        if ((a1 & 0x20) != 0x0) {
            this.buf.append("synchronized ");
        }
        if ((a1 & 0x40) != 0x0) {
            this.buf.append("volatile ");
        }
        if ((a1 & 0x80) != 0x0) {
            this.buf.append("transient ");
        }
        if ((a1 & 0x400) != 0x0) {
            this.buf.append("abstract ");
        }
        if ((a1 & 0x800) != 0x0) {
            this.buf.append("strictfp ");
        }
        if ((a1 & 0x1000) != 0x0) {
            this.buf.append("synthetic ");
        }
        if ((a1 & 0x8000) != 0x0) {
            this.buf.append("mandated ");
        }
        if ((a1 & 0x4000) != 0x0) {
            this.buf.append("enum ");
        }
    }
    
    private void appendComa(final int a1) {
        if (a1 != 0) {
            this.buf.append(", ");
        }
    }
    
    private void appendTypeReference(final int a1) {
        final TypeReference v1 = new TypeReference(a1);
        switch (v1.getSort()) {
            case 0: {
                this.buf.append("CLASS_TYPE_PARAMETER ").append(v1.getTypeParameterIndex());
                break;
            }
            case 1: {
                this.buf.append("METHOD_TYPE_PARAMETER ").append(v1.getTypeParameterIndex());
                break;
            }
            case 16: {
                this.buf.append("CLASS_EXTENDS ").append(v1.getSuperTypeIndex());
                break;
            }
            case 17: {
                this.buf.append("CLASS_TYPE_PARAMETER_BOUND ").append(v1.getTypeParameterIndex()).append(", ").append(v1.getTypeParameterBoundIndex());
                break;
            }
            case 18: {
                this.buf.append("METHOD_TYPE_PARAMETER_BOUND ").append(v1.getTypeParameterIndex()).append(", ").append(v1.getTypeParameterBoundIndex());
                break;
            }
            case 19: {
                this.buf.append("FIELD");
                break;
            }
            case 20: {
                this.buf.append("METHOD_RETURN");
                break;
            }
            case 21: {
                this.buf.append("METHOD_RECEIVER");
                break;
            }
            case 22: {
                this.buf.append("METHOD_FORMAL_PARAMETER ").append(v1.getFormalParameterIndex());
                break;
            }
            case 23: {
                this.buf.append("THROWS ").append(v1.getExceptionIndex());
                break;
            }
            case 64: {
                this.buf.append("LOCAL_VARIABLE");
                break;
            }
            case 65: {
                this.buf.append("RESOURCE_VARIABLE");
                break;
            }
            case 66: {
                this.buf.append("EXCEPTION_PARAMETER ").append(v1.getTryCatchBlockIndex());
                break;
            }
            case 67: {
                this.buf.append("INSTANCEOF");
                break;
            }
            case 68: {
                this.buf.append("NEW");
                break;
            }
            case 69: {
                this.buf.append("CONSTRUCTOR_REFERENCE");
                break;
            }
            case 70: {
                this.buf.append("METHOD_REFERENCE");
                break;
            }
            case 71: {
                this.buf.append("CAST ").append(v1.getTypeArgumentIndex());
                break;
            }
            case 72: {
                this.buf.append("CONSTRUCTOR_INVOCATION_TYPE_ARGUMENT ").append(v1.getTypeArgumentIndex());
                break;
            }
            case 73: {
                this.buf.append("METHOD_INVOCATION_TYPE_ARGUMENT ").append(v1.getTypeArgumentIndex());
                break;
            }
            case 74: {
                this.buf.append("CONSTRUCTOR_REFERENCE_TYPE_ARGUMENT ").append(v1.getTypeArgumentIndex());
                break;
            }
            case 75: {
                this.buf.append("METHOD_REFERENCE_TYPE_ARGUMENT ").append(v1.getTypeArgumentIndex());
                break;
            }
        }
    }
    
    private void appendFrameTypes(final int v2, final Object[] v3) {
        for (int a2 = 0; a2 < v2; ++a2) {
            if (a2 > 0) {
                this.buf.append(' ');
            }
            if (v3[a2] instanceof String) {
                final String a3 = (String)v3[a2];
                if (a3.startsWith("[")) {
                    this.appendDescriptor(1, a3);
                }
                else {
                    this.appendDescriptor(0, a3);
                }
            }
            else if (v3[a2] instanceof Integer) {
                switch ((int)v3[a2]) {
                    case 0: {
                        this.appendDescriptor(1, "T");
                        break;
                    }
                    case 1: {
                        this.appendDescriptor(1, "I");
                        break;
                    }
                    case 2: {
                        this.appendDescriptor(1, "F");
                        break;
                    }
                    case 3: {
                        this.appendDescriptor(1, "D");
                        break;
                    }
                    case 4: {
                        this.appendDescriptor(1, "J");
                        break;
                    }
                    case 5: {
                        this.appendDescriptor(1, "N");
                        break;
                    }
                    case 6: {
                        this.appendDescriptor(1, "U");
                        break;
                    }
                }
            }
            else {
                this.appendLabel((Label)v3[a2]);
            }
        }
    }
    
    @Override
    public /* bridge */ Printer visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        return this.visitParameterAnnotation(a1, a2, a3);
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
    public /* bridge */ Printer visitMethod(final int v-4, final String v-5, final String v-6, final String v-7, final String[] v0) {
        return this.visitMethod(v-4, v-5, v-6, v-7, v0);
    }
    
    @Override
    public /* bridge */ Printer visitField(final int a4, final String a5, final String v1, final String v2, final Object v3) {
        return this.visitField(a4, a5, v1, v2, v3);
    }
    
    @Override
    public /* bridge */ Printer visitClassAnnotation(final String a1, final boolean a2) {
        return this.visitClassAnnotation(a1, a2);
    }
}
