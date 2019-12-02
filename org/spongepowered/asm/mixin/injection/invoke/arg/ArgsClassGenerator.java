package org.spongepowered.asm.mixin.injection.invoke.arg;

import org.spongepowered.asm.mixin.transformer.ext.*;
import com.google.common.collect.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.util.*;
import org.spongepowered.asm.util.asm.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;

public final class ArgsClassGenerator implements IClassGenerator
{
    public static final String ARGS_NAME;
    public static final String ARGS_REF;
    public static final String GETTER_PREFIX = "$";
    private static final String CLASS_NAME_BASE = "org.spongepowered.asm.synthetic.args.Args$";
    private static final String OBJECT = "java/lang/Object";
    private static final String OBJECT_ARRAY = "[Ljava/lang/Object;";
    private static final String VALUES_FIELD = "values";
    private static final String CTOR_DESC = "([Ljava/lang/Object;)V";
    private static final String SET = "set";
    private static final String SET_DESC = "(ILjava/lang/Object;)V";
    private static final String SETALL = "setAll";
    private static final String SETALL_DESC = "([Ljava/lang/Object;)V";
    private static final String NPE = "java/lang/NullPointerException";
    private static final String NPE_CTOR_DESC = "(Ljava/lang/String;)V";
    private static final String AIOOBE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException";
    private static final String AIOOBE_CTOR_DESC = "(I)V";
    private static final String ACE = "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException";
    private static final String ACE_CTOR_DESC = "(IILjava/lang/String;)V";
    private int nextIndex;
    private final BiMap<String, String> classNames;
    private final Map<String, byte[]> classBytes;
    
    public ArgsClassGenerator() {
        super();
        this.nextIndex = 1;
        this.classNames = (BiMap<String, String>)HashBiMap.create();
        this.classBytes = new HashMap<String, byte[]>();
    }
    
    public String getClassName(final String a1) {
        final String v1 = Bytecode.changeDescriptorReturnType(a1, "V");
        String v2 = this.classNames.get(v1);
        if (v2 == null) {
            v2 = String.format("%s%d", "org.spongepowered.asm.synthetic.args.Args$", this.nextIndex++);
            this.classNames.put(v1, v2);
        }
        return v2;
    }
    
    public String getClassRef(final String a1) {
        return this.getClassName(a1).replace('.', '/');
    }
    
    @Override
    public byte[] generate(final String a1) {
        return this.getBytes(a1);
    }
    
    public byte[] getBytes(final String v2) {
        byte[] v3 = this.classBytes.get(v2);
        if (v3 == null) {
            final String a1 = this.classNames.inverse().get(v2);
            if (a1 == null) {
                return null;
            }
            v3 = this.generateClass(v2, a1);
            this.classBytes.put(v2, v3);
        }
        return v3;
    }
    
    private byte[] generateClass(final String a1, final String a2) {
        final String v1 = a1.replace('.', '/');
        final Type[] v2 = Type.getArgumentTypes(a2);
        ClassVisitor v4;
        final ClassWriter v3 = (ClassWriter)(v4 = new ClassWriter(2));
        if (MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
            v4 = new CheckClassAdapter(v3);
        }
        v4.visit(50, 4129, v1, null, ArgsClassGenerator.ARGS_REF, null);
        v4.visitSource(a1.substring(a1.lastIndexOf(46) + 1) + ".java", null);
        this.generateCtor(v1, a2, v2, v4);
        this.generateToString(v1, a2, v2, v4);
        this.generateFactory(v1, a2, v2, v4);
        this.generateSetters(v1, a2, v2, v4);
        this.generateGetters(v1, a2, v2, v4);
        v4.visitEnd();
        return v3.toByteArray();
    }
    
    private void generateCtor(final String a1, final String a2, final Type[] a3, final ClassVisitor a4) {
        final MethodVisitor v1 = a4.visitMethod(2, "<init>", "([Ljava/lang/Object;)V", null, null);
        v1.visitCode();
        v1.visitVarInsn(25, 0);
        v1.visitVarInsn(25, 1);
        v1.visitMethodInsn(183, ArgsClassGenerator.ARGS_REF, "<init>", "([Ljava/lang/Object;)V", false);
        v1.visitInsn(177);
        v1.visitMaxs(2, 2);
        v1.visitEnd();
    }
    
    private void generateToString(final String a1, final String a2, final Type[] a3, final ClassVisitor a4) {
        final MethodVisitor v1 = a4.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
        v1.visitCode();
        v1.visitLdcInsn("Args" + getSignature(a3));
        v1.visitInsn(176);
        v1.visitMaxs(1, 1);
        v1.visitEnd();
    }
    
    private void generateFactory(final String a3, final String a4, final Type[] v1, final ClassVisitor v2) {
        final String v3 = Bytecode.changeDescriptorReturnType(a4, "L" + a3 + ";");
        final MethodVisitorEx v4 = new MethodVisitorEx(v2.visitMethod(9, "of", v3, null, null));
        v4.visitCode();
        v4.visitTypeInsn(187, a3);
        v4.visitInsn(89);
        v4.visitConstant((byte)v1.length);
        v4.visitTypeInsn(189, "java/lang/Object");
        byte v5 = 0;
        for (final Type a5 : v1) {
            v4.visitInsn(89);
            v4.visitConstant(v5);
            v4.visitVarInsn(a5.getOpcode(21), v5);
            box(v4, a5);
            v4.visitInsn(83);
            v5 += (byte)a5.getSize();
        }
        v4.visitMethodInsn(183, a3, "<init>", "([Ljava/lang/Object;)V", false);
        v4.visitInsn(176);
        v4.visitMaxs(6, Bytecode.getArgsSize(v1));
        v4.visitEnd();
    }
    
    private void generateGetters(final String v2, final String v3, final Type[] v4, final ClassVisitor v5) {
        byte v6 = 0;
        for (final Type a4 : v4) {
            final String a5 = "$" + v6;
            final String a6 = "()" + a4.getDescriptor();
            final MethodVisitorEx a7 = new MethodVisitorEx(v5.visitMethod(1, a5, a6, null, null));
            a7.visitCode();
            a7.visitVarInsn(25, 0);
            a7.visitFieldInsn(180, v2, "values", "[Ljava/lang/Object;");
            a7.visitConstant(v6);
            a7.visitInsn(50);
            unbox(a7, a4);
            a7.visitInsn(a4.getOpcode(172));
            a7.visitMaxs(2, 1);
            a7.visitEnd();
            ++v6;
        }
    }
    
    private void generateSetters(final String a1, final String a2, final Type[] a3, final ClassVisitor a4) {
        this.generateIndexedSetter(a1, a2, a3, a4);
        this.generateMultiSetter(a1, a2, a3, a4);
    }
    
    private void generateIndexedSetter(final String v2, final String v3, final Type[] v4, final ClassVisitor v5) {
        final MethodVisitorEx v6 = new MethodVisitorEx(v5.visitMethod(1, "set", "(ILjava/lang/Object;)V", null, null));
        v6.visitCode();
        final Label v7 = new Label();
        final Label v8 = new Label();
        final Label[] v9 = new Label[v4.length];
        for (int a1 = 0; a1 < v9.length; ++a1) {
            v9[a1] = new Label();
        }
        v6.visitVarInsn(25, 0);
        v6.visitFieldInsn(180, v2, "values", "[Ljava/lang/Object;");
        for (byte a2 = 0; a2 < v4.length; ++a2) {
            v6.visitVarInsn(21, 1);
            v6.visitConstant(a2);
            v6.visitJumpInsn(159, v9[a2]);
        }
        throwAIOOBE(v6, 1);
        for (int a3 = 0; a3 < v4.length; ++a3) {
            final String a4 = Bytecode.getBoxingType(v4[a3]);
            v6.visitLabel(v9[a3]);
            v6.visitVarInsn(21, 1);
            v6.visitVarInsn(25, 2);
            v6.visitTypeInsn(192, (a4 != null) ? a4 : v4[a3].getInternalName());
            v6.visitJumpInsn(167, (a4 != null) ? v8 : v7);
        }
        v6.visitLabel(v8);
        v6.visitInsn(89);
        v6.visitJumpInsn(199, v7);
        throwNPE(v6, "Argument with primitive type cannot be set to NULL");
        v6.visitLabel(v7);
        v6.visitInsn(83);
        v6.visitInsn(177);
        v6.visitMaxs(6, 3);
        v6.visitEnd();
    }
    
    private void generateMultiSetter(final String a4, final String v1, final Type[] v2, final ClassVisitor v3) {
        final MethodVisitorEx v4 = new MethodVisitorEx(v3.visitMethod(1, "setAll", "([Ljava/lang/Object;)V", null, null));
        v4.visitCode();
        final Label v5 = new Label();
        final Label v6 = new Label();
        int v7 = 6;
        v4.visitVarInsn(25, 1);
        v4.visitInsn(190);
        v4.visitInsn(89);
        v4.visitConstant((byte)v2.length);
        v4.visitJumpInsn(159, v5);
        v4.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException");
        v4.visitInsn(89);
        v4.visitInsn(93);
        v4.visitInsn(88);
        v4.visitConstant((byte)v2.length);
        v4.visitLdcInsn(getSignature(v2));
        v4.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException", "<init>", "(IILjava/lang/String;)V", false);
        v4.visitInsn(191);
        v4.visitLabel(v5);
        v4.visitInsn(87);
        v4.visitVarInsn(25, 0);
        v4.visitFieldInsn(180, a4, "values", "[Ljava/lang/Object;");
        for (byte a5 = 0; a5 < v2.length; ++a5) {
            v4.visitInsn(89);
            v4.visitConstant(a5);
            v4.visitVarInsn(25, 1);
            v4.visitConstant(a5);
            v4.visitInsn(50);
            final String a6 = Bytecode.getBoxingType(v2[a5]);
            v4.visitTypeInsn(192, (a6 != null) ? a6 : v2[a5].getInternalName());
            if (a6 != null) {
                v4.visitInsn(89);
                v4.visitJumpInsn(198, v6);
                v7 = 7;
            }
            v4.visitInsn(83);
        }
        v4.visitInsn(177);
        v4.visitLabel(v6);
        throwNPE(v4, "Argument with primitive type cannot be set to NULL");
        v4.visitInsn(177);
        v4.visitMaxs(v7, 2);
        v4.visitEnd();
    }
    
    private static void throwNPE(final MethodVisitorEx a1, final String a2) {
        a1.visitTypeInsn(187, "java/lang/NullPointerException");
        a1.visitInsn(89);
        a1.visitLdcInsn(a2);
        a1.visitMethodInsn(183, "java/lang/NullPointerException", "<init>", "(Ljava/lang/String;)V", false);
        a1.visitInsn(191);
    }
    
    private static void throwAIOOBE(final MethodVisitorEx a1, final int a2) {
        a1.visitTypeInsn(187, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException");
        a1.visitInsn(89);
        a1.visitVarInsn(21, a2);
        a1.visitMethodInsn(183, "org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException", "<init>", "(I)V", false);
        a1.visitInsn(191);
    }
    
    private static void box(final MethodVisitor a2, final Type v1) {
        final String v2 = Bytecode.getBoxingType(v1);
        if (v2 != null) {
            final String a3 = String.format("(%s)L%s;", v1.getDescriptor(), v2);
            a2.visitMethodInsn(184, v2, "valueOf", a3, false);
        }
    }
    
    private static void unbox(final MethodVisitor v1, final Type v2) {
        final String v3 = Bytecode.getBoxingType(v2);
        if (v3 != null) {
            final String a1 = Bytecode.getUnboxingMethod(v2);
            final String a2 = "()" + v2.getDescriptor();
            v1.visitTypeInsn(192, v3);
            v1.visitMethodInsn(182, v3, a1, a2, false);
        }
        else {
            v1.visitTypeInsn(192, v2.getInternalName());
        }
    }
    
    private static String getSignature(final Type[] a1) {
        return new SignaturePrinter("", null, a1).setFullyQualified(true).getFormattedArgs();
    }
    
    static {
        ARGS_NAME = Args.class.getName();
        ARGS_REF = ArgsClassGenerator.ARGS_NAME.replace('.', '/');
    }
}
