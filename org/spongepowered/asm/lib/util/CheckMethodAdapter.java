package org.spongepowered.asm.lib.util;

import java.lang.reflect.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.io.*;
import java.util.*;
import org.spongepowered.asm.lib.*;

public class CheckMethodAdapter extends MethodVisitor
{
    public int version;
    private int access;
    private boolean startCode;
    private boolean endCode;
    private boolean endMethod;
    private int insnCount;
    private final Map<Label, Integer> labels;
    private Set<Label> usedLabels;
    private int expandedFrames;
    private int compressedFrames;
    private int lastFrame;
    private List<Label> handlers;
    private static final int[] TYPE;
    private static Field labelStatusField;
    
    public CheckMethodAdapter(final MethodVisitor a1) {
        this(a1, new HashMap<Label, Integer>());
    }
    
    public CheckMethodAdapter(final MethodVisitor a1, final Map<Label, Integer> a2) {
        this(327680, a1, a2);
        if (this.getClass() != CheckMethodAdapter.class) {
            throw new IllegalStateException();
        }
    }
    
    protected CheckMethodAdapter(final int a1, final MethodVisitor a2, final Map<Label, Integer> a3) {
        super(a1, a2);
        this.lastFrame = -1;
        this.labels = a3;
        this.usedLabels = new HashSet<Label>();
        this.handlers = new ArrayList<Label>();
    }
    
    public CheckMethodAdapter(final int a1, final String a2, final String a3, final MethodVisitor a4, final Map<Label, Integer> a5) {
        this(new MethodNode(327680, a1, a2, a3, null, null) {
            final /* synthetic */ MethodVisitor val$cmv;
            
            CheckMethodAdapter$1(final int a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
                super(a1, a2, a3, a4, a5, a6);
            }
            
            @Override
            public void visitEnd() {
                final Analyzer<BasicValue> v-6 = new Analyzer<BasicValue>(new BasicVerifier());
                try {
                    v-6.analyze("dummy", this);
                }
                catch (Exception v0) {
                    if (v0 instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
                        throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
                    }
                    v0.printStackTrace();
                    final StringWriter v2 = new StringWriter();
                    final PrintWriter v3 = new PrintWriter(v2, true);
                    CheckClassAdapter.printAnalyzerResult(this, v-6, v3);
                    v3.close();
                    throw new RuntimeException(v0.getMessage() + ' ' + v2.toString());
                }
                this.accept(a4);
            }
        }, a5);
        this.access = a1;
    }
    
    @Override
    public void visitParameter(final String a1, final int a2) {
        if (a1 != null) {
            checkUnqualifiedName(this.version, a1, "name");
        }
        CheckClassAdapter.checkAccess(a2, 36880);
        super.visitParameter(a1, a2);
    }
    
    @Override
    public AnnotationVisitor visitAnnotation(final String a1, final boolean a2) {
        this.checkEndMethod();
        checkDesc(a1, false);
        return new CheckAnnotationAdapter(super.visitAnnotation(a1, a2));
    }
    
    @Override
    public AnnotationVisitor visitTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.checkEndMethod();
        final int v1 = a1 >>> 24;
        if (v1 != 1 && v1 != 18 && v1 != 20 && v1 != 21 && v1 != 22 && v1 != 23) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v1));
        }
        CheckClassAdapter.checkTypeRefAndPath(a1, a2);
        checkDesc(a3, false);
        return new CheckAnnotationAdapter(super.visitTypeAnnotation(a1, a2, a3, a4));
    }
    
    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        this.checkEndMethod();
        return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
    }
    
    @Override
    public AnnotationVisitor visitParameterAnnotation(final int a1, final String a2, final boolean a3) {
        this.checkEndMethod();
        checkDesc(a2, false);
        return new CheckAnnotationAdapter(super.visitParameterAnnotation(a1, a2, a3));
    }
    
    @Override
    public void visitAttribute(final Attribute a1) {
        this.checkEndMethod();
        if (a1 == null) {
            throw new IllegalArgumentException("Invalid attribute (must not be null)");
        }
        super.visitAttribute(a1);
    }
    
    @Override
    public void visitCode() {
        if ((this.access & 0x400) != 0x0) {
            throw new RuntimeException("Abstract methods cannot have code");
        }
        this.startCode = true;
        super.visitCode();
    }
    
    @Override
    public void visitFrame(final int v-5, final int v-4, final Object[] v-3, final int v-2, final Object[] v-1) {
        if (this.insnCount == this.lastFrame) {
            throw new IllegalStateException("At most one frame can be visited at a given code location.");
        }
        this.lastFrame = this.insnCount;
        int v1 = 0;
        final int v2;
        switch (v-5) {
            case -1:
            case 0: {
                final int a1 = 0;
                final int a2 = 0;
                break;
            }
            case 3: {
                final int a3 = 0;
                final int a4 = 0;
                break;
            }
            case 4: {
                final int a5 = 0;
                v1 = 1;
                break;
            }
            case 1:
            case 2: {
                v2 = 3;
                v1 = 0;
                break;
            }
            default: {
                throw new IllegalArgumentException("Invalid frame type " + v-5);
            }
        }
        if (v-4 > v2) {
            throw new IllegalArgumentException("Invalid nLocal=" + v-4 + " for frame type " + v-5);
        }
        if (v-2 > v1) {
            throw new IllegalArgumentException("Invalid nStack=" + v-2 + " for frame type " + v-5);
        }
        if (v-5 != 2) {
            if (v-4 > 0 && (v-3 == null || v-3.length < v-4)) {
                throw new IllegalArgumentException("Array local[] is shorter than nLocal");
            }
            for (int v3 = 0; v3 < v-4; ++v3) {
                this.checkFrameValue(v-3[v3]);
            }
        }
        if (v-2 > 0 && (v-1 == null || v-1.length < v-2)) {
            throw new IllegalArgumentException("Array stack[] is shorter than nStack");
        }
        for (int v3 = 0; v3 < v-2; ++v3) {
            this.checkFrameValue(v-1[v3]);
        }
        if (v-5 == -1) {
            ++this.expandedFrames;
        }
        else {
            ++this.compressedFrames;
        }
        if (this.expandedFrames > 0 && this.compressedFrames > 0) {
            throw new RuntimeException("Expanded and compressed frames must not be mixed.");
        }
        super.visitFrame(v-5, v-4, v-3, v-2, v-1);
    }
    
    @Override
    public void visitInsn(final int a1) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 0);
        super.visitInsn(a1);
        ++this.insnCount;
    }
    
    @Override
    public void visitIntInsn(final int a1, final int a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 1);
        switch (a1) {
            case 16: {
                checkSignedByte(a2, "Invalid operand");
                break;
            }
            case 17: {
                checkSignedShort(a2, "Invalid operand");
                break;
            }
            default: {
                if (a2 < 4 || a2 > 11) {
                    throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + a2);
                }
                break;
            }
        }
        super.visitIntInsn(a1, a2);
        ++this.insnCount;
    }
    
    @Override
    public void visitVarInsn(final int a1, final int a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 2);
        checkUnsignedShort(a2, "Invalid variable index");
        super.visitVarInsn(a1, a2);
        ++this.insnCount;
    }
    
    @Override
    public void visitTypeInsn(final int a1, final String a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 3);
        checkInternalName(a2, "type");
        if (a1 == 187 && a2.charAt(0) == '[') {
            throw new IllegalArgumentException("NEW cannot be used to create arrays: " + a2);
        }
        super.visitTypeInsn(a1, a2);
        ++this.insnCount;
    }
    
    @Override
    public void visitFieldInsn(final int a1, final String a2, final String a3, final String a4) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 4);
        checkInternalName(a2, "owner");
        checkUnqualifiedName(this.version, a3, "name");
        checkDesc(a4, false);
        super.visitFieldInsn(a1, a2, a3, a4);
        ++this.insnCount;
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
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 5);
        if (a1 != 183 || !"<init>".equals(a3)) {
            checkMethodIdentifier(this.version, a3, "name");
        }
        checkInternalName(a2, "owner");
        checkMethodDesc(a4);
        if (a1 == 182 && a5) {
            throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
        }
        if (a1 == 185 && !a5) {
            throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
        }
        if (a1 == 183 && a5 && (this.version & 0xFFFF) < 52) {
            throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
        }
        if (this.mv != null) {
            this.mv.visitMethodInsn(a1, a2, a3, a4, a5);
        }
        ++this.insnCount;
    }
    
    @Override
    public void visitInvokeDynamicInsn(final String a3, final String a4, final Handle v1, final Object... v2) {
        this.checkStartCode();
        this.checkEndCode();
        checkMethodIdentifier(this.version, a3, "name");
        checkMethodDesc(a4);
        if (v1.getTag() != 6 && v1.getTag() != 8) {
            throw new IllegalArgumentException("invalid handle tag " + v1.getTag());
        }
        for (int a5 = 0; a5 < v2.length; ++a5) {
            this.checkLDCConstant(v2[a5]);
        }
        super.visitInvokeDynamicInsn(a3, a4, v1, v2);
        ++this.insnCount;
    }
    
    @Override
    public void visitJumpInsn(final int a1, final Label a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkOpcode(a1, 6);
        this.checkLabel(a2, false, "label");
        checkNonDebugLabel(a2);
        super.visitJumpInsn(a1, a2);
        this.usedLabels.add(a2);
        ++this.insnCount;
    }
    
    @Override
    public void visitLabel(final Label a1) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLabel(a1, false, "label");
        if (this.labels.get(a1) != null) {
            throw new IllegalArgumentException("Already visited label");
        }
        this.labels.put(a1, this.insnCount);
        super.visitLabel(a1);
    }
    
    @Override
    public void visitLdcInsn(final Object a1) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLDCConstant(a1);
        super.visitLdcInsn(a1);
        ++this.insnCount;
    }
    
    @Override
    public void visitIincInsn(final int a1, final int a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkUnsignedShort(a1, "Invalid variable index");
        checkSignedShort(a2, "Invalid increment");
        super.visitIincInsn(a1, a2);
        ++this.insnCount;
    }
    
    @Override
    public void visitTableSwitchInsn(final int a4, final int v1, final Label v2, final Label... v3) {
        this.checkStartCode();
        this.checkEndCode();
        if (v1 < a4) {
            throw new IllegalArgumentException("Max = " + v1 + " must be greater than or equal to min = " + a4);
        }
        this.checkLabel(v2, false, "default label");
        checkNonDebugLabel(v2);
        if (v3 == null || v3.length != v1 - a4 + 1) {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
        }
        for (int a5 = 0; a5 < v3.length; ++a5) {
            this.checkLabel(v3[a5], false, "label at index " + a5);
            checkNonDebugLabel(v3[a5]);
        }
        super.visitTableSwitchInsn(a4, v1, v2, v3);
        for (int a6 = 0; a6 < v3.length; ++a6) {
            this.usedLabels.add(v3[a6]);
        }
        ++this.insnCount;
    }
    
    @Override
    public void visitLookupSwitchInsn(final Label v1, final int[] v2, final Label[] v3) {
        this.checkEndCode();
        this.checkStartCode();
        this.checkLabel(v1, false, "default label");
        checkNonDebugLabel(v1);
        if (v2 == null || v3 == null || v2.length != v3.length) {
            throw new IllegalArgumentException("There must be the same number of keys and labels");
        }
        for (int a1 = 0; a1 < v3.length; ++a1) {
            this.checkLabel(v3[a1], false, "label at index " + a1);
            checkNonDebugLabel(v3[a1]);
        }
        super.visitLookupSwitchInsn(v1, v2, v3);
        this.usedLabels.add(v1);
        for (int a2 = 0; a2 < v3.length; ++a2) {
            this.usedLabels.add(v3[a2]);
        }
        ++this.insnCount;
    }
    
    @Override
    public void visitMultiANewArrayInsn(final String a1, final int a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkDesc(a1, false);
        if (a1.charAt(0) != '[') {
            throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + a1);
        }
        if (a2 < 1) {
            throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + a2);
        }
        if (a2 > a1.lastIndexOf(91) + 1) {
            throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + a2);
        }
        super.visitMultiANewArrayInsn(a1, a2);
        ++this.insnCount;
    }
    
    @Override
    public AnnotationVisitor visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.checkStartCode();
        this.checkEndCode();
        final int v1 = a1 >>> 24;
        if (v1 != 67 && v1 != 68 && v1 != 69 && v1 != 70 && v1 != 71 && v1 != 72 && v1 != 73 && v1 != 74 && v1 != 75) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v1));
        }
        CheckClassAdapter.checkTypeRefAndPath(a1, a2);
        checkDesc(a3, false);
        return new CheckAnnotationAdapter(super.visitInsnAnnotation(a1, a2, a3, a4));
    }
    
    @Override
    public void visitTryCatchBlock(final Label a1, final Label a2, final Label a3, final String a4) {
        this.checkStartCode();
        this.checkEndCode();
        this.checkLabel(a1, false, "start label");
        this.checkLabel(a2, false, "end label");
        this.checkLabel(a3, false, "handler label");
        checkNonDebugLabel(a1);
        checkNonDebugLabel(a2);
        checkNonDebugLabel(a3);
        if (this.labels.get(a1) != null || this.labels.get(a2) != null || this.labels.get(a3) != null) {
            throw new IllegalStateException("Try catch blocks must be visited before their labels");
        }
        if (a4 != null) {
            checkInternalName(a4, "type");
        }
        super.visitTryCatchBlock(a1, a2, a3, a4);
        this.handlers.add(a1);
        this.handlers.add(a2);
    }
    
    @Override
    public AnnotationVisitor visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        this.checkStartCode();
        this.checkEndCode();
        final int v1 = a1 >>> 24;
        if (v1 != 66) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v1));
        }
        CheckClassAdapter.checkTypeRefAndPath(a1, a2);
        checkDesc(a3, false);
        return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(a1, a2, a3, a4));
    }
    
    @Override
    public void visitLocalVariable(final String a1, final String a2, final String a3, final Label a4, final Label a5, final int a6) {
        this.checkStartCode();
        this.checkEndCode();
        checkUnqualifiedName(this.version, a1, "name");
        checkDesc(a2, false);
        this.checkLabel(a4, true, "start label");
        this.checkLabel(a5, true, "end label");
        checkUnsignedShort(a6, "Invalid variable index");
        final int v1 = this.labels.get(a4);
        final int v2 = this.labels.get(a5);
        if (v2 < v1) {
            throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
        }
        super.visitLocalVariable(a1, a2, a3, a4, a5, a6);
    }
    
    @Override
    public AnnotationVisitor visitLocalVariableAnnotation(final int a5, final TypePath a6, final Label[] a7, final Label[] v1, final int[] v2, final String v3, final boolean v4) {
        this.checkStartCode();
        this.checkEndCode();
        final int v5 = a5 >>> 24;
        if (v5 != 64 && v5 != 65) {
            throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(v5));
        }
        CheckClassAdapter.checkTypeRefAndPath(a5, a6);
        checkDesc(v3, false);
        if (a7 == null || v1 == null || v2 == null || v1.length != a7.length || v2.length != a7.length) {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
        }
        for (int a8 = 0; a8 < a7.length; ++a8) {
            this.checkLabel(a7[a8], true, "start label");
            this.checkLabel(v1[a8], true, "end label");
            checkUnsignedShort(v2[a8], "Invalid variable index");
            final int a9 = this.labels.get(a7[a8]);
            final int a10 = this.labels.get(v1[a8]);
            if (a10 < a9) {
                throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
            }
        }
        return super.visitLocalVariableAnnotation(a5, a6, a7, v1, v2, v3, v4);
    }
    
    @Override
    public void visitLineNumber(final int a1, final Label a2) {
        this.checkStartCode();
        this.checkEndCode();
        checkUnsignedShort(a1, "Invalid line number");
        this.checkLabel(a2, true, "start label");
        super.visitLineNumber(a1, a2);
    }
    
    @Override
    public void visitMaxs(final int v-3, final int v-2) {
        this.checkStartCode();
        this.checkEndCode();
        this.endCode = true;
        for (final Label a1 : this.usedLabels) {
            if (this.labels.get(a1) == null) {
                throw new IllegalStateException("Undefined label used");
            }
        }
        int i = 0;
        while (i < this.handlers.size()) {
            final Integer a2 = this.labels.get(this.handlers.get(i++));
            final Integer v1 = this.labels.get(this.handlers.get(i++));
            if (a2 == null || v1 == null) {
                throw new IllegalStateException("Undefined try catch block labels");
            }
            if (v1 <= a2) {
                throw new IllegalStateException("Emty try catch block handler range");
            }
        }
        checkUnsignedShort(v-3, "Invalid max stack");
        checkUnsignedShort(v-2, "Invalid max locals");
        super.visitMaxs(v-3, v-2);
    }
    
    @Override
    public void visitEnd() {
        this.checkEndMethod();
        this.endMethod = true;
        super.visitEnd();
    }
    
    void checkStartCode() {
        if (!this.startCode) {
            throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
        }
    }
    
    void checkEndCode() {
        if (this.endCode) {
            throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
        }
    }
    
    void checkEndMethod() {
        if (this.endMethod) {
            throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
        }
    }
    
    void checkFrameValue(final Object a1) {
        if (a1 == Opcodes.TOP || a1 == Opcodes.INTEGER || a1 == Opcodes.FLOAT || a1 == Opcodes.LONG || a1 == Opcodes.DOUBLE || a1 == Opcodes.NULL || a1 == Opcodes.UNINITIALIZED_THIS) {
            return;
        }
        if (a1 instanceof String) {
            checkInternalName((String)a1, "Invalid stack frame value");
            return;
        }
        if (!(a1 instanceof Label)) {
            throw new IllegalArgumentException("Invalid stack frame value: " + a1);
        }
        this.usedLabels.add((Label)a1);
    }
    
    static void checkOpcode(final int a1, final int a2) {
        if (a1 < 0 || a1 > 199 || CheckMethodAdapter.TYPE[a1] != a2) {
            throw new IllegalArgumentException("Invalid opcode: " + a1);
        }
    }
    
    static void checkSignedByte(final int a1, final String a2) {
        if (a1 < -128 || a1 > 127) {
            throw new IllegalArgumentException(a2 + " (must be a signed byte): " + a1);
        }
    }
    
    static void checkSignedShort(final int a1, final String a2) {
        if (a1 < -32768 || a1 > 32767) {
            throw new IllegalArgumentException(a2 + " (must be a signed short): " + a1);
        }
    }
    
    static void checkUnsignedShort(final int a1, final String a2) {
        if (a1 < 0 || a1 > 65535) {
            throw new IllegalArgumentException(a2 + " (must be an unsigned short): " + a1);
        }
    }
    
    static void checkConstant(final Object a1) {
        if (!(a1 instanceof Integer) && !(a1 instanceof Float) && !(a1 instanceof Long) && !(a1 instanceof Double) && !(a1 instanceof String)) {
            throw new IllegalArgumentException("Invalid constant: " + a1);
        }
    }
    
    void checkLDCConstant(final Object v0) {
        if (v0 instanceof Type) {
            final int a1 = ((Type)v0).getSort();
            if (a1 != 10 && a1 != 9 && a1 != 11) {
                throw new IllegalArgumentException("Illegal LDC constant value");
            }
            if (a1 != 11 && (this.version & 0xFFFF) < 49) {
                throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
            }
            if (a1 == 11 && (this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
            }
        }
        else if (v0 instanceof Handle) {
            if ((this.version & 0xFFFF) < 51) {
                throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
            }
            final int v = ((Handle)v0).getTag();
            if (v < 1 || v > 9) {
                throw new IllegalArgumentException("invalid handle tag " + v);
            }
        }
        else {
            checkConstant(v0);
        }
    }
    
    static void checkUnqualifiedName(final int a2, final String a3, final String v1) {
        if ((a2 & 0xFFFF) < 49) {
            checkIdentifier(a3, v1);
        }
        else {
            for (int a4 = 0; a4 < a3.length(); ++a4) {
                if (".;[/".indexOf(a3.charAt(a4)) != -1) {
                    throw new IllegalArgumentException("Invalid " + v1 + " (must be a valid unqualified name): " + a3);
                }
            }
        }
    }
    
    static void checkIdentifier(final String a1, final String a2) {
        checkIdentifier(a1, 0, -1, a2);
    }
    
    static void checkIdentifier(final String a2, final int a3, final int a4, final String v1) {
        if (a2 != null) {
            if (a4 == -1) {
                if (a2.length() <= a3) {
                    throw new IllegalArgumentException("Invalid " + v1 + " (must not be null or empty)");
                }
            }
            else if (a4 <= a3) {
                throw new IllegalArgumentException("Invalid " + v1 + " (must not be null or empty)");
            }
            if (!Character.isJavaIdentifierStart(a2.charAt(a3))) {
                throw new IllegalArgumentException("Invalid " + v1 + " (must be a valid Java identifier): " + a2);
            }
            for (int v2 = (a4 == -1) ? a2.length() : a4, a5 = a3 + 1; a5 < v2; ++a5) {
                if (!Character.isJavaIdentifierPart(a2.charAt(a5))) {
                    throw new IllegalArgumentException("Invalid " + v1 + " (must be a valid Java identifier): " + a2);
                }
            }
            return;
        }
        throw new IllegalArgumentException("Invalid " + v1 + " (must not be null or empty)");
    }
    
    static void checkMethodIdentifier(final int a3, final String v1, final String v2) {
        if (v1 == null || v1.length() == 0) {
            throw new IllegalArgumentException("Invalid " + v2 + " (must not be null or empty)");
        }
        if ((a3 & 0xFFFF) >= 49) {
            for (int a4 = 0; a4 < v1.length(); ++a4) {
                if (".;[/<>".indexOf(v1.charAt(a4)) != -1) {
                    throw new IllegalArgumentException("Invalid " + v2 + " (must be a valid unqualified name): " + v1);
                }
            }
            return;
        }
        if (!Character.isJavaIdentifierStart(v1.charAt(0))) {
            throw new IllegalArgumentException("Invalid " + v2 + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + v1);
        }
        for (int a5 = 1; a5 < v1.length(); ++a5) {
            if (!Character.isJavaIdentifierPart(v1.charAt(a5))) {
                throw new IllegalArgumentException("Invalid " + v2 + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + v1);
            }
        }
    }
    
    static void checkInternalName(final String a1, final String a2) {
        if (a1 == null || a1.length() == 0) {
            throw new IllegalArgumentException("Invalid " + a2 + " (must not be null or empty)");
        }
        if (a1.charAt(0) == '[') {
            checkDesc(a1, false);
        }
        else {
            checkInternalName(a1, 0, -1, a2);
        }
    }
    
    static void checkInternalName(final String a4, final int v1, final int v2, final String v3) {
        final int v4 = (v2 == -1) ? a4.length() : v2;
        try {
            int a5 = v1;
            int a6;
            do {
                a6 = a4.indexOf(47, a5 + 1);
                if (a6 == -1 || a6 > v4) {
                    a6 = v4;
                }
                checkIdentifier(a4, a5, a6, null);
                a5 = a6 + 1;
            } while (a6 != v4);
        }
        catch (IllegalArgumentException a7) {
            throw new IllegalArgumentException("Invalid " + v3 + " (must be a fully qualified class name in internal form): " + a4);
        }
    }
    
    static void checkDesc(final String a1, final boolean a2) {
        final int v1 = checkDesc(a1, 0, a2);
        if (v1 != a1.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + a1);
        }
    }
    
    static int checkDesc(final String v1, final int v2, final boolean v3) {
        if (v1 == null || v2 >= v1.length()) {
            throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
        }
        switch (v1.charAt(v2)) {
            case 'V': {
                if (v3) {
                    return v2 + 1;
                }
                throw new IllegalArgumentException("Invalid descriptor: " + v1);
            }
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'Z': {
                return v2 + 1;
            }
            case '[': {
                int a1;
                for (a1 = v2 + 1; a1 < v1.length() && v1.charAt(a1) == '['; ++a1) {}
                if (a1 < v1.length()) {
                    return checkDesc(v1, a1, false);
                }
                throw new IllegalArgumentException("Invalid descriptor: " + v1);
            }
            case 'L': {
                final int a2 = v1.indexOf(59, v2);
                if (a2 == -1 || a2 - v2 < 2) {
                    throw new IllegalArgumentException("Invalid descriptor: " + v1);
                }
                try {
                    checkInternalName(v1, v2 + 1, a2, null);
                }
                catch (IllegalArgumentException a3) {
                    throw new IllegalArgumentException("Invalid descriptor: " + v1);
                }
                return a2 + 1;
            }
            default: {
                throw new IllegalArgumentException("Invalid descriptor: " + v1);
            }
        }
    }
    
    static void checkMethodDesc(final String a1) {
        if (a1 == null || a1.length() == 0) {
            throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
        }
        if (a1.charAt(0) != '(' || a1.length() < 3) {
            throw new IllegalArgumentException("Invalid descriptor: " + a1);
        }
        int v1 = 1;
        Label_0143: {
            if (a1.charAt(v1) != ')') {
                while (a1.charAt(v1) != 'V') {
                    v1 = checkDesc(a1, v1, false);
                    if (v1 >= a1.length() || a1.charAt(v1) == ')') {
                        break Label_0143;
                    }
                }
                throw new IllegalArgumentException("Invalid descriptor: " + a1);
            }
        }
        v1 = checkDesc(a1, v1 + 1, true);
        if (v1 != a1.length()) {
            throw new IllegalArgumentException("Invalid descriptor: " + a1);
        }
    }
    
    void checkLabel(final Label a1, final boolean a2, final String a3) {
        if (a1 == null) {
            throw new IllegalArgumentException("Invalid " + a3 + " (must not be null)");
        }
        if (a2 && this.labels.get(a1) == null) {
            throw new IllegalArgumentException("Invalid " + a3 + " (must be visited first)");
        }
    }
    
    private static void checkNonDebugLabel(final Label v1) {
        final Field v2 = getLabelStatusField();
        int v3 = 0;
        try {
            v3 = (int)((v2 == null) ? 0 : v2.get(v1));
        }
        catch (IllegalAccessException a1) {
            throw new Error("Internal error");
        }
        if ((v3 & 0x1) != 0x0) {
            throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
        }
    }
    
    private static Field getLabelStatusField() {
        if (CheckMethodAdapter.labelStatusField == null) {
            CheckMethodAdapter.labelStatusField = getLabelField("a");
            if (CheckMethodAdapter.labelStatusField == null) {
                CheckMethodAdapter.labelStatusField = getLabelField("status");
            }
        }
        return CheckMethodAdapter.labelStatusField;
    }
    
    private static Field getLabelField(final String v0) {
        try {
            final Field a1 = Label.class.getDeclaredField(v0);
            a1.setAccessible(true);
            return a1;
        }
        catch (NoSuchFieldException v) {
            return null;
        }
    }
    
    static {
        final String v0 = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
        TYPE = new int[v0.length()];
        for (int v2 = 0; v2 < CheckMethodAdapter.TYPE.length; ++v2) {
            CheckMethodAdapter.TYPE[v2] = v0.charAt(v2) - 'A' - 1;
        }
    }
}
