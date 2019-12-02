package org.spongepowered.asm.lib.util;

import java.util.*;
import org.spongepowered.asm.lib.*;
import java.io.*;

public abstract class Printer
{
    public static final String[] OPCODES;
    public static final String[] TYPES;
    public static final String[] HANDLE_TAG;
    protected final int api;
    protected final StringBuffer buf;
    public final List<Object> text;
    
    protected Printer(final int a1) {
        super();
        this.api = a1;
        this.buf = new StringBuffer();
        this.text = new ArrayList<Object>();
    }
    
    public abstract void visit(final int p0, final int p1, final String p2, final String p3, final String p4, final String[] p5);
    
    public abstract void visitSource(final String p0, final String p1);
    
    public abstract void visitOuterClass(final String p0, final String p1, final String p2);
    
    public abstract Printer visitClassAnnotation(final String p0, final boolean p1);
    
    public Printer visitClassTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitClassAttribute(final Attribute p0);
    
    public abstract void visitInnerClass(final String p0, final String p1, final String p2, final int p3);
    
    public abstract Printer visitField(final int p0, final String p1, final String p2, final String p3, final Object p4);
    
    public abstract Printer visitMethod(final int p0, final String p1, final String p2, final String p3, final String[] p4);
    
    public abstract void visitClassEnd();
    
    public abstract void visit(final String p0, final Object p1);
    
    public abstract void visitEnum(final String p0, final String p1, final String p2);
    
    public abstract Printer visitAnnotation(final String p0, final String p1);
    
    public abstract Printer visitArray(final String p0);
    
    public abstract void visitAnnotationEnd();
    
    public abstract Printer visitFieldAnnotation(final String p0, final boolean p1);
    
    public Printer visitFieldTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitFieldAttribute(final Attribute p0);
    
    public abstract void visitFieldEnd();
    
    public void visitParameter(final String a1, final int a2) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract Printer visitAnnotationDefault();
    
    public abstract Printer visitMethodAnnotation(final String p0, final boolean p1);
    
    public Printer visitMethodTypeAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract Printer visitParameterAnnotation(final int p0, final String p1, final boolean p2);
    
    public abstract void visitMethodAttribute(final Attribute p0);
    
    public abstract void visitCode();
    
    public abstract void visitFrame(final int p0, final int p1, final Object[] p2, final int p3, final Object[] p4);
    
    public abstract void visitInsn(final int p0);
    
    public abstract void visitIntInsn(final int p0, final int p1);
    
    public abstract void visitVarInsn(final int p0, final int p1);
    
    public abstract void visitTypeInsn(final int p0, final String p1);
    
    public abstract void visitFieldInsn(final int p0, final String p1, final String p2, final String p3);
    
    @Deprecated
    public void visitMethodInsn(final int a3, final String a4, final String v1, final String v2) {
        if (this.api >= 327680) {
            final boolean a5 = a3 == 185;
            this.visitMethodInsn(a3, a4, v1, v2, a5);
            return;
        }
        throw new RuntimeException("Must be overriden");
    }
    
    public void visitMethodInsn(final int a1, final String a2, final String a3, final String a4, final boolean a5) {
        if (this.api >= 327680) {
            throw new RuntimeException("Must be overriden");
        }
        if (a5 != (a1 == 185)) {
            throw new IllegalArgumentException("INVOKESPECIAL/STATIC on interfaces require ASM 5");
        }
        this.visitMethodInsn(a1, a2, a3, a4);
    }
    
    public abstract void visitInvokeDynamicInsn(final String p0, final String p1, final Handle p2, final Object... p3);
    
    public abstract void visitJumpInsn(final int p0, final Label p1);
    
    public abstract void visitLabel(final Label p0);
    
    public abstract void visitLdcInsn(final Object p0);
    
    public abstract void visitIincInsn(final int p0, final int p1);
    
    public abstract void visitTableSwitchInsn(final int p0, final int p1, final Label p2, final Label... p3);
    
    public abstract void visitLookupSwitchInsn(final Label p0, final int[] p1, final Label[] p2);
    
    public abstract void visitMultiANewArrayInsn(final String p0, final int p1);
    
    public Printer visitInsnAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitTryCatchBlock(final Label p0, final Label p1, final Label p2, final String p3);
    
    public Printer visitTryCatchAnnotation(final int a1, final TypePath a2, final String a3, final boolean a4) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitLocalVariable(final String p0, final String p1, final String p2, final Label p3, final Label p4, final int p5);
    
    public Printer visitLocalVariableAnnotation(final int a1, final TypePath a2, final Label[] a3, final Label[] a4, final int[] a5, final String a6, final boolean a7) {
        throw new RuntimeException("Must be overriden");
    }
    
    public abstract void visitLineNumber(final int p0, final Label p1);
    
    public abstract void visitMaxs(final int p0, final int p1);
    
    public abstract void visitMethodEnd();
    
    public List<Object> getText() {
        return this.text;
    }
    
    public void print(final PrintWriter a1) {
        printList(a1, this.text);
    }
    
    public static void appendString(final StringBuffer v1, final String v2) {
        v1.append('\"');
        for (int a2 = 0; a2 < v2.length(); ++a2) {
            final char a3 = v2.charAt(a2);
            if (a3 == '\n') {
                v1.append("\\n");
            }
            else if (a3 == '\r') {
                v1.append("\\r");
            }
            else if (a3 == '\\') {
                v1.append("\\\\");
            }
            else if (a3 == '\"') {
                v1.append("\\\"");
            }
            else if (a3 < ' ' || a3 > '\u007f') {
                v1.append("\\u");
                if (a3 < '\u0010') {
                    v1.append("000");
                }
                else if (a3 < '\u0100') {
                    v1.append("00");
                }
                else if (a3 < '\u1000') {
                    v1.append('0');
                }
                v1.append(Integer.toString(a3, 16));
            }
            else {
                v1.append(a3);
            }
        }
        v1.append('\"');
    }
    
    static void printList(final PrintWriter v1, final List<?> v2) {
        for (int a2 = 0; a2 < v2.size(); ++a2) {
            final Object a3 = v2.get(a2);
            if (a3 instanceof List) {
                printList(v1, (List<?>)a3);
            }
            else {
                v1.print(a3.toString());
            }
        }
    }
    
    static {
        String v1 = "NOP,ACONST_NULL,ICONST_M1,ICONST_0,ICONST_1,ICONST_2,ICONST_3,ICONST_4,ICONST_5,LCONST_0,LCONST_1,FCONST_0,FCONST_1,FCONST_2,DCONST_0,DCONST_1,BIPUSH,SIPUSH,LDC,,,ILOAD,LLOAD,FLOAD,DLOAD,ALOAD,,,,,,,,,,,,,,,,,,,,,IALOAD,LALOAD,FALOAD,DALOAD,AALOAD,BALOAD,CALOAD,SALOAD,ISTORE,LSTORE,FSTORE,DSTORE,ASTORE,,,,,,,,,,,,,,,,,,,,,IASTORE,LASTORE,FASTORE,DASTORE,AASTORE,BASTORE,CASTORE,SASTORE,POP,POP2,DUP,DUP_X1,DUP_X2,DUP2,DUP2_X1,DUP2_X2,SWAP,IADD,LADD,FADD,DADD,ISUB,LSUB,FSUB,DSUB,IMUL,LMUL,FMUL,DMUL,IDIV,LDIV,FDIV,DDIV,IREM,LREM,FREM,DREM,INEG,LNEG,FNEG,DNEG,ISHL,LSHL,ISHR,LSHR,IUSHR,LUSHR,IAND,LAND,IOR,LOR,IXOR,LXOR,IINC,I2L,I2F,I2D,L2I,L2F,L2D,F2I,F2L,F2D,D2I,D2L,D2F,I2B,I2C,I2S,LCMP,FCMPL,FCMPG,DCMPL,DCMPG,IFEQ,IFNE,IFLT,IFGE,IFGT,IFLE,IF_ICMPEQ,IF_ICMPNE,IF_ICMPLT,IF_ICMPGE,IF_ICMPGT,IF_ICMPLE,IF_ACMPEQ,IF_ACMPNE,GOTO,JSR,RET,TABLESWITCH,LOOKUPSWITCH,IRETURN,LRETURN,FRETURN,DRETURN,ARETURN,RETURN,GETSTATIC,PUTSTATIC,GETFIELD,PUTFIELD,INVOKEVIRTUAL,INVOKESPECIAL,INVOKESTATIC,INVOKEINTERFACE,INVOKEDYNAMIC,NEW,NEWARRAY,ANEWARRAY,ARRAYLENGTH,ATHROW,CHECKCAST,INSTANCEOF,MONITORENTER,MONITOREXIT,,MULTIANEWARRAY,IFNULL,IFNONNULL,";
        OPCODES = new String[200];
        int v2 = 0;
        int v4;
        for (int v3 = 0; (v4 = v1.indexOf(44, v3)) > 0; v3 = v4 + 1) {
            Printer.OPCODES[v2++] = ((v3 + 1 == v4) ? null : v1.substring(v3, v4));
        }
        v1 = "T_BOOLEAN,T_CHAR,T_FLOAT,T_DOUBLE,T_BYTE,T_SHORT,T_INT,T_LONG,";
        TYPES = new String[12];
        int v3 = 0;
        v2 = 4;
        while ((v4 = v1.indexOf(44, v3)) > 0) {
            Printer.TYPES[v2++] = v1.substring(v3, v4);
            v3 = v4 + 1;
        }
        v1 = "H_GETFIELD,H_GETSTATIC,H_PUTFIELD,H_PUTSTATIC,H_INVOKEVIRTUAL,H_INVOKESTATIC,H_INVOKESPECIAL,H_NEWINVOKESPECIAL,H_INVOKEINTERFACE,";
        HANDLE_TAG = new String[10];
        v3 = 0;
        v2 = 1;
        while ((v4 = v1.indexOf(44, v3)) > 0) {
            Printer.HANDLE_TAG[v2++] = v1.substring(v3, v4);
            v3 = v4 + 1;
        }
    }
}
