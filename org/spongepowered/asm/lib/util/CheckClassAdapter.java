package org.spongepowered.asm.lib.util;

import java.io.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class CheckClassAdapter extends ClassVisitor
{
    private int version;
    private boolean start;
    private boolean source;
    private boolean outer;
    private boolean end;
    private Map<Label, Integer> labels;
    private boolean checkDataFlow;
    
    public static void main(final String[] v1) throws Exception {
        if (v1.length != 1) {
            System.err.println("Verifies the given class.");
            System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
            return;
        }
        ClassReader v2 = null;
        if (v1[0].endsWith(".class")) {
            final ClassReader a1 = new ClassReader(new FileInputStream(v1[0]));
        }
        else {
            v2 = new ClassReader(v1[0]);
        }
        verify(v2, false, new PrintWriter(System.err));
    }
    
    public static void verify(final ClassReader v-10, final ClassLoader v-9, final boolean v-8, final PrintWriter v-7) {
        final ClassNode a5 = new ClassNode();
        v-10.accept(new CheckClassAdapter(a5, false), 2);
        final Type a6 = (a5.superName == null) ? null : Type.getObjectType(a5.superName);
        final List<MethodNode> methods = a5.methods;
        final List<Type> a7 = new ArrayList<Type>();
        final Iterator<String> a1 = a5.interfaces.iterator();
        while (a1.hasNext()) {
            a7.add(Type.getObjectType(a1.next()));
        }
        for (int i = 0; i < methods.size(); ++i) {
            final MethodNode a2 = methods.get(i);
            final SimpleVerifier a3 = new SimpleVerifier(Type.getObjectType(a5.name), a6, a7, (a5.access & 0x200) != 0x0);
            final Analyzer<BasicValue> v1 = new Analyzer<BasicValue>(a3);
            if (v-9 != null) {
                a3.setClassLoader(v-9);
            }
            try {
                v1.analyze(a5.name, a2);
                if (!v-8) {
                    continue;
                }
            }
            catch (Exception a4) {
                a4.printStackTrace(v-7);
            }
            printAnalyzerResult(a2, v1, v-7);
        }
        v-7.flush();
    }
    
    public static void verify(final ClassReader a1, final boolean a2, final PrintWriter a3) {
        verify(a1, null, a2, a3);
    }
    
    static void printAnalyzerResult(final MethodNode v-7, final Analyzer<BasicValue> v-6, final PrintWriter v-5) {
        final Frame<BasicValue>[] frames = v-6.getFrames();
        final Textifier a6 = new Textifier();
        final TraceMethodVisitor v-8 = new TraceMethodVisitor(a6);
        v-5.println(v-7.name + v-7.desc);
        for (int i = 0; i < v-7.instructions.size(); ++i) {
            v-7.instructions.get(i).accept(v-8);
            final StringBuilder a3 = new StringBuilder();
            final Frame<BasicValue> v1 = frames[i];
            if (v1 == null) {
                a3.append('?');
            }
            else {
                for (int a4 = 0; a4 < v1.getLocals(); ++a4) {
                    a3.append(getShortName(((BasicValue)v1.getLocal(a4)).toString())).append(' ');
                }
                a3.append(" : ");
                for (int a5 = 0; a5 < v1.getStackSize(); ++a5) {
                    a3.append(getShortName(((BasicValue)v1.getStack(a5)).toString())).append(' ');
                }
            }
            while (a3.length() < v-7.maxStack + v-7.maxLocals + 1) {
                a3.append(' ');
            }
            v-5.print(Integer.toString(i + 100000).substring(1));
            v-5.print(" " + (Object)a3 + " : " + a6.text.get(a6.text.size() - 1));
        }
        for (int i = 0; i < v-7.tryCatchBlocks.size(); ++i) {
            v-7.tryCatchBlocks.get(i).accept(v-8);
            v-5.print(" " + a6.text.get(a6.text.size() - 1));
        }
        v-5.println();
    }
    
    private static String getShortName(final String a1) {
        final int v1 = a1.lastIndexOf(47);
        int v2 = a1.length();
        if (a1.charAt(v2 - 1) == ';') {
            --v2;
        }
        return (v1 == -1) ? a1 : a1.substring(v1 + 1, v2);
    }
    
    public CheckClassAdapter(final ClassVisitor a1) {
        this(a1, true);
    }
    
    public CheckClassAdapter(final ClassVisitor a1, final boolean a2) {
        this(327680, a1, a2);
        if (this.getClass() != CheckClassAdapter.class) {
            throw new IllegalStateException();
        }
    }
    
    protected CheckClassAdapter(final int a1, final ClassVisitor a2, final boolean a3) {
        super(a1, a2);
        this.labels = new HashMap<Label, Integer>();
        this.checkDataFlow = a3;
    }
    
    @Override
    public void visit(final int a3, final int a4, final String a5, final String a6, final String v1, final String[] v2) {
        if (this.start) {
            throw new IllegalStateException("visit must be called only once");
        }
        this.start = true;
        this.checkState();
        checkAccess(a4, 423473);
        if (a5 == null || !a5.endsWith("package-info")) {
            CheckMethodAdapter.checkInternalName(a5, "class name");
        }
        if ("java/lang/Object".equals(a5)) {
            if (v1 != null) {
                throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
        }
        else {
            CheckMethodAdapter.checkInternalName(v1, "super class name");
        }
        if (a6 != null) {
            checkClassSignature(a6);
        }
        if ((a4 & 0x200) != 0x0 && !"java/lang/Object".equals(v1)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
        }
        if (v2 != null) {
            for (int a7 = 0; a7 < v2.length; ++a7) {
                CheckMethodAdapter.checkInternalName(v2[a7], "interface name at index " + a7);
            }
        }
        super.visit(this.version = a3, a4, a5, a6, v1, v2);
    }
    
    @Override
    public void visitSource(final String a1, final String a2) {
        this.checkState();
        if (this.source) {
            throw new IllegalStateException("visitSource can be called only once.");
        }
        this.source = true;
        super.visitSource(a1, a2);
    }
    
    @Override
    public void visitOuterClass(final String a1, final String a2, final String a3) {
        this.checkState();
        if (this.outer) {
            throw new IllegalStateException("visitOuterClass can be called only once.");
        }
        this.outer = true;
        if (a1 == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
        }
        if (a3 != null) {
            CheckMethodAdapter.checkMethodDesc(a3);
        }
        super.visitOuterClass(a1, a2, a3);
    }
    
    @Override
    public void visitInnerClass(final String a3, final String a4, final String v1, final int v2) {
        this.checkState();
        CheckMethodAdapter.checkInternalName(a3, "class name");
        if (a4 != null) {
            CheckMethodAdapter.checkInternalName(a4, "outer class name");
        }
        if (v1 != null) {
            int a5;
            for (a5 = 0; a5 < v1.length() && Character.isDigit(v1.charAt(a5)); ++a5) {}
            if (a5 == 0 || a5 < v1.length()) {
                CheckMethodAdapter.checkIdentifier(v1, a5, -1, "inner class name");
            }
        }
        checkAccess(v2, 30239);
        super.visitInnerClass(a3, a4, v1, v2);
    }
    
    @Override
    public FieldVisitor visitField(final int a1, final String a2, final String a3, final String a4, final Object a5) {
        this.checkState();
        checkAccess(a1, 413919);
        CheckMethodAdapter.checkUnqualifiedName(this.version, a2, "field name");
        CheckMethodAdapter.checkDesc(a3, false);
        if (a4 != null) {
            checkFieldSignature(a4);
        }
        if (a5 != null) {
            CheckMethodAdapter.checkConstant(a5);
        }
        final FieldVisitor v1 = super.visitField(a1, a2, a3, a4, a5);
        return new CheckFieldAdapter(v1);
    }
    
    @Override
    public MethodVisitor visitMethod(final int a4, final String a5, final String v1, final String v2, final String[] v3) {
        this.checkState();
        checkAccess(a4, 400895);
        if (!"<init>".equals(a5) && !"<clinit>".equals(a5)) {
            CheckMethodAdapter.checkMethodIdentifier(this.version, a5, "method name");
        }
        CheckMethodAdapter.checkMethodDesc(v1);
        if (v2 != null) {
            checkMethodSignature(v2);
        }
        if (v3 != null) {
            for (int a6 = 0; a6 < v3.length; ++a6) {
                CheckMethodAdapter.checkInternalName(v3[a6], "exception name at index " + a6);
            }
        }
        CheckMethodAdapter v4 = null;
        if (this.checkDataFlow) {
            final CheckMethodAdapter a7 = new CheckMethodAdapter(a4, a5, v1, super.visitMethod(a4, a5, v1, v2, v3), this.labels);
        }
        else {
            v4 = new CheckMethodAdapter(super.visitMethod(a4, a5, v1, v2, v3), this.labels);
        }
        v4.version = this.version;
        return v4;
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        this.checkState();
        CheckMethodAdapter.checkDesc(a1, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(a1, a2));
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.checkState();
        final int v1 = a1 >>> 24;
        if (v1 != 0 && v1 != 17 && v1 != 16) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v1));
        }
        checkTypeRefAndPath(a1, a2);
        CheckMethodAdapter.checkDesc(a3, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(a1, a2, a3, a4));
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        this.checkState();
        if (a1 == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(a1);
    }
    
    @Override
    public void visitEnd() {
        this.checkState();
        this.end = true;
        super.visitEnd();
    }
    
    private void checkState() {
        if (!this.start) {
            throw new IllegalStateException("Cannot visit member before visit has been called.");
        }
        if (this.end) {
            throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
        }
    }
    
    static void checkAccess(final int a1, final int a2) {
        if ((a1 & ~a2) != 0x0) {
            throw new IllegalArgumentException("Invalid access flags: " + a1);
        }
        final int v1 = ((a1 & 0x1) != 0x0) ? 1 : 0;
        final int v2 = ((a1 & 0x2) != 0x0) ? 1 : 0;
        final int v3 = ((a1 & 0x4) != 0x0) ? 1 : 0;
        if (v1 + v2 + v3 > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + a1);
        }
        final int v4 = ((a1 & 0x10) != 0x0) ? 1 : 0;
        final int v5 = ((a1 & 0x400) != 0x0) ? 1 : 0;
        if (v4 + v5 > 1) {
            throw new IllegalArgumentException("final and abstract are mutually exclusive: " + a1);
        }
    }
    
    public static void checkClassSignature(final String a1) {
        int v1 = 0;
        if (getChar(a1, 0) == '<') {
            v1 = checkFormalTypeParameters(a1, v1);
        }
        for (v1 = checkClassTypeSignature(a1, v1); getChar(a1, v1) == 'L'; v1 = checkClassTypeSignature(a1, v1)) {}
        if (v1 != a1.length()) {
            throw new IllegalArgumentException(a1 + ": error at index " + v1);
        }
    }
    
    public static void checkMethodSignature(final String a1) {
        int v1 = 0;
        if (getChar(a1, 0) == '<') {
            v1 = checkFormalTypeParameters(a1, v1);
        }
        for (v1 = checkChar('(', a1, v1); "ZCBSIFJDL[T".indexOf(getChar(a1, v1)) != -1; v1 = checkTypeSignature(a1, v1)) {}
        v1 = checkChar(')', a1, v1);
        if (getChar(a1, v1) == 'V') {
            ++v1;
        }
        else {
            v1 = checkTypeSignature(a1, v1);
        }
        while (getChar(a1, v1) == '^') {
            ++v1;
            if (getChar(a1, v1) == 'L') {
                v1 = checkClassTypeSignature(a1, v1);
            }
            else {
                v1 = checkTypeVariableSignature(a1, v1);
            }
        }
        if (v1 != a1.length()) {
            throw new IllegalArgumentException(a1 + ": error at index " + v1);
        }
    }
    
    public static void checkFieldSignature(final String a1) {
        final int v1 = checkFieldTypeSignature(a1, 0);
        if (v1 != a1.length()) {
            throw new IllegalArgumentException(a1 + ": error at index " + v1);
        }
    }
    
    static void checkTypeRefAndPath(final int v1, final TypePath v2) {
        int v3 = 0;
        switch (v1 >>> 24) {
            case 0:
            case 1:
            case 22: {
                v3 = -65536;
                break;
            }
            case 19:
            case 20:
            case 21:
            case 64:
            case 65:
            case 67:
            case 68:
            case 69:
            case 70: {
                v3 = -16777216;
                break;
            }
            case 16:
            case 17:
            case 18:
            case 23:
            case 66: {
                v3 = -256;
                break;
            }
            case 71:
            case 72:
            case 73:
            case 74:
            case 75: {
                v3 = -16776961;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v1 >>> 24));
            }
        }
        if ((v1 & ~v3) != 0x0) {
            throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(v1));
        }
        if (v2 != null) {
            for (int a2 = 0; a2 < v2.getLength(); ++a2) {
                final int a3 = v2.getStep(a2);
                if (a3 != 0 && a3 != 1 && a3 != 3 && a3 != 2) {
                    throw new IllegalArgumentException("Invalid type path step " + a2 + " in " + v2);
                }
                if (a3 != 3 && v2.getStepArgument(a2) != 0) {
                    throw new IllegalArgumentException("Invalid type path step argument for step " + a2 + " in " + v2);
                }
            }
        }
    }
    
    private static int checkFormalTypeParameters(final String a1, int a2) {
        for (a2 = checkChar('<', a1, a2), a2 = checkFormalTypeParameter(a1, a2); getChar(a1, a2) != '>'; a2 = checkFormalTypeParameter(a1, a2)) {}
        return a2 + 1;
    }
    
    private static int checkFormalTypeParameter(final String a1, int a2) {
        a2 = checkIdentifier(a1, a2);
        a2 = checkChar(':', a1, a2);
        if ("L[T".indexOf(getChar(a1, a2)) != -1) {
            a2 = checkFieldTypeSignature(a1, a2);
        }
        while (getChar(a1, a2) == ':') {
            a2 = checkFieldTypeSignature(a1, a2 + 1);
        }
        return a2;
    }
    
    private static int checkFieldTypeSignature(final String a1, final int a2) {
        switch (getChar(a1, a2)) {
            case 'L': {
                return checkClassTypeSignature(a1, a2);
            }
            case '[': {
                return checkTypeSignature(a1, a2 + 1);
            }
            default: {
                return checkTypeVariableSignature(a1, a2);
            }
        }
    }
    
    private static int checkClassTypeSignature(final String a1, int a2) {
        for (a2 = checkChar('L', a1, a2), a2 = checkIdentifier(a1, a2); getChar(a1, a2) == '/'; a2 = checkIdentifier(a1, a2 + 1)) {}
        if (getChar(a1, a2) == '<') {
            a2 = checkTypeArguments(a1, a2);
        }
        while (getChar(a1, a2) == '.') {
            a2 = checkIdentifier(a1, a2 + 1);
            if (getChar(a1, a2) == '<') {
                a2 = checkTypeArguments(a1, a2);
            }
        }
        return checkChar(';', a1, a2);
    }
    
    private static int checkTypeArguments(final String a1, int a2) {
        for (a2 = checkChar('<', a1, a2), a2 = checkTypeArgument(a1, a2); getChar(a1, a2) != '>'; a2 = checkTypeArgument(a1, a2)) {}
        return a2 + 1;
    }
    
    private static int checkTypeArgument(final String a1, int a2) {
        final char v1 = getChar(a1, a2);
        if (v1 == '*') {
            return a2 + 1;
        }
        if (v1 == '+' || v1 == '-') {
            ++a2;
        }
        return checkFieldTypeSignature(a1, a2);
    }
    
    private static int checkTypeVariableSignature(final String a1, int a2) {
        a2 = checkChar('T', a1, a2);
        a2 = checkIdentifier(a1, a2);
        return checkChar(';', a1, a2);
    }
    
    private static int checkTypeSignature(final String a1, final int a2) {
        switch (getChar(a1, a2)) {
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z': {
                return a2 + 1;
            }
            default: {
                return checkFieldTypeSignature(a1, a2);
            }
        }
    }
    
    private static int checkIdentifier(final String a1, int a2) {
        if (!Character.isJavaIdentifierStart(getChar(a1, a2))) {
            throw new IllegalArgumentException(a1 + ": identifier expected at index " + a2);
        }
        ++a2;
        while (Character.isJavaIdentifierPart(getChar(a1, a2))) {
            ++a2;
        }
        return a2;
    }
    
    private static int checkChar(final char a1, final String a2, final int a3) {
        if (getChar(a2, a3) == a1) {
            return a3 + 1;
        }
        throw new IllegalArgumentException(a2 + ": '" + a1 + "' expected at index " + a3);
    }
    
    private static char getChar(final String a1, final int a2) {
        return (a2 < a1.length()) ? a1.charAt(a2) : '\0';
    }
}
