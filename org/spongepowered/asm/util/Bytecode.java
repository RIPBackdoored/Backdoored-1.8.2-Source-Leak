package org.spongepowered.asm.util;

import java.util.regex.*;
import java.io.*;
import org.spongepowered.asm.lib.util.*;
import java.lang.reflect.*;
import org.spongepowered.asm.lib.*;
import com.google.common.base.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.primitives.*;
import org.spongepowered.asm.util.throwables.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;

public final class Bytecode
{
    public static final int[] CONSTANTS_INT;
    public static final int[] CONSTANTS_FLOAT;
    public static final int[] CONSTANTS_DOUBLE;
    public static final int[] CONSTANTS_LONG;
    public static final int[] CONSTANTS_ALL;
    private static final Object[] CONSTANTS_VALUES;
    private static final String[] CONSTANTS_TYPES;
    private static final String[] BOXING_TYPES;
    private static final String[] UNBOXING_METHODS;
    private static final Class<?>[] MERGEABLE_MIXIN_ANNOTATIONS;
    private static Pattern mergeableAnnotationPattern;
    private static final Logger logger;
    
    private Bytecode() {
        super();
    }
    
    public static MethodNode findMethod(final ClassNode a2, final String a3, final String v1) {
        for (final MethodNode a4 : a2.methods) {
            if (a4.name.equals(a3) && a4.desc.equals(v1)) {
                return a4;
            }
        }
        return null;
    }
    
    public static AbstractInsnNode findInsn(final MethodNode a2, final int v1) {
        for (final AbstractInsnNode a3 : a2.instructions) {
            if (a3.getOpcode() == v1) {
                return a3;
            }
        }
        return null;
    }
    
    public static MethodInsnNode findSuperInit(final MethodNode v-2, final String v-1) {
        if (!"<init>".equals(v-2.name)) {
            return null;
        }
        int v0 = 0;
        for (final AbstractInsnNode a2 : v-2.instructions) {
            if (a2 instanceof TypeInsnNode && a2.getOpcode() == 187) {
                ++v0;
            }
            else {
                if (!(a2 instanceof MethodInsnNode) || a2.getOpcode() != 183) {
                    continue;
                }
                final MethodInsnNode a3 = (MethodInsnNode)a2;
                if (!"<init>".equals(a3.name)) {
                    continue;
                }
                if (v0 > 0) {
                    --v0;
                }
                else {
                    if (a3.owner.equals(v-1)) {
                        return a3;
                    }
                    continue;
                }
            }
        }
        return null;
    }
    
    public static void textify(final ClassNode a1, final OutputStream a2) {
        a1.accept(new TraceClassVisitor(new PrintWriter(a2)));
    }
    
    public static void textify(final MethodNode a1, final OutputStream a2) {
        final TraceClassVisitor v1 = new TraceClassVisitor(new PrintWriter(a2));
        final MethodVisitor v2 = v1.visitMethod(a1.access, a1.name, a1.desc, a1.signature, a1.exceptions.toArray(new String[0]));
        a1.accept(v2);
        v1.visitEnd();
    }
    
    public static void dumpClass(final ClassNode a1) {
        final ClassWriter v1 = new ClassWriter(3);
        a1.accept(v1);
        dumpClass(v1.toByteArray());
    }
    
    public static void dumpClass(final byte[] a1) {
        final ClassReader v1 = new ClassReader(a1);
        CheckClassAdapter.verify(v1, true, new PrintWriter(System.out));
    }
    
    public static void printMethodWithOpcodeIndices(final MethodNode v1) {
        System.err.printf("%s%s\n", v1.name, v1.desc);
        int v2 = 0;
        final Iterator<AbstractInsnNode> a1 = v1.instructions.iterator();
        while (a1.hasNext()) {
            System.err.printf("[%4d] %s\n", v2++, describeNode(a1.next()));
        }
    }
    
    public static void printMethod(final MethodNode v1) {
        System.err.printf("%s%s\n", v1.name, v1.desc);
        final Iterator<AbstractInsnNode> a1 = v1.instructions.iterator();
        while (a1.hasNext()) {
            System.err.print("  ");
            printNode(a1.next());
        }
    }
    
    public static void printNode(final AbstractInsnNode a1) {
        System.err.printf("%s\n", describeNode(a1));
    }
    
    public static String describeNode(final AbstractInsnNode v-1) {
        if (v-1 == null) {
            return String.format("   %-14s ", "null");
        }
        if (v-1 instanceof LabelNode) {
            return String.format("[%s]", ((LabelNode)v-1).getLabel());
        }
        String v0 = String.format("   %-14s ", v-1.getClass().getSimpleName().replace("Node", ""));
        if (v-1 instanceof JumpInsnNode) {
            v0 += String.format("[%s] [%s]", getOpcodeName(v-1), ((JumpInsnNode)v-1).label.getLabel());
        }
        else if (v-1 instanceof VarInsnNode) {
            v0 += String.format("[%s] %d", getOpcodeName(v-1), ((VarInsnNode)v-1).var);
        }
        else if (v-1 instanceof MethodInsnNode) {
            final MethodInsnNode a1 = (MethodInsnNode)v-1;
            v0 += String.format("[%s] %s %s %s", getOpcodeName(v-1), a1.owner, a1.name, a1.desc);
        }
        else if (v-1 instanceof FieldInsnNode) {
            final FieldInsnNode v2 = (FieldInsnNode)v-1;
            v0 += String.format("[%s] %s %s %s", getOpcodeName(v-1), v2.owner, v2.name, v2.desc);
        }
        else if (v-1 instanceof LineNumberNode) {
            final LineNumberNode v3 = (LineNumberNode)v-1;
            v0 += String.format("LINE=[%d] LABEL=[%s]", v3.line, v3.start.getLabel());
        }
        else if (v-1 instanceof LdcInsnNode) {
            v0 += ((LdcInsnNode)v-1).cst;
        }
        else if (v-1 instanceof IntInsnNode) {
            v0 += ((IntInsnNode)v-1).operand;
        }
        else if (v-1 instanceof FrameNode) {
            v0 += String.format("[%s] ", getOpcodeName(((FrameNode)v-1).type, "H_INVOKEINTERFACE", -1));
        }
        else {
            v0 += String.format("[%s] ", getOpcodeName(v-1));
        }
        return v0;
    }
    
    public static String getOpcodeName(final AbstractInsnNode a1) {
        return (a1 != null) ? getOpcodeName(a1.getOpcode()) : "";
    }
    
    public static String getOpcodeName(final int a1) {
        return getOpcodeName(a1, "UNINITIALIZED_THIS", 1);
    }
    
    private static String getOpcodeName(final int a3, final String v1, final int v2) {
        if (a3 >= v2) {
            boolean a4 = false;
            try {
                for (final Field a5 : Opcodes.class.getDeclaredFields()) {
                    if (a4 || a5.getName().equals(v1)) {
                        a4 = true;
                        if (a5.getType() == Integer.TYPE && a5.getInt(null) == a3) {
                            return a5.getName();
                        }
                    }
                }
            }
            catch (Exception ex) {}
        }
        return (a3 >= 0) ? String.valueOf(a3) : "UNKNOWN";
    }
    
    public static boolean methodHasLineNumbers(final MethodNode v1) {
        final Iterator<AbstractInsnNode> a1 = v1.instructions.iterator();
        while (a1.hasNext()) {
            if (a1.next() instanceof LineNumberNode) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean methodIsStatic(final MethodNode a1) {
        return (a1.access & 0x8) == 0x8;
    }
    
    public static boolean fieldIsStatic(final FieldNode a1) {
        return (a1.access & 0x8) == 0x8;
    }
    
    public static int getFirstNonArgLocalIndex(final MethodNode a1) {
        return getFirstNonArgLocalIndex(Type.getArgumentTypes(a1.desc), (a1.access & 0x8) == 0x0);
    }
    
    public static int getFirstNonArgLocalIndex(final Type[] a1, final boolean a2) {
        return getArgsSize(a1) + (a2 ? 1 : 0);
    }
    
    public static int getArgsSize(final Type[] v1) {
        int v2 = 0;
        for (final Type a1 : v1) {
            v2 += a1.getSize();
        }
        return v2;
    }
    
    public static void loadArgs(final Type[] a1, final InsnList a2, final int a3) {
        loadArgs(a1, a2, a3, -1);
    }
    
    public static void loadArgs(final Type[] a1, final InsnList a2, final int a3, final int a4) {
        loadArgs(a1, a2, a3, a4, null);
    }
    
    public static void loadArgs(final Type[] a2, final InsnList a3, final int a4, final int a5, final Type[] v1) {
        int v2 = a4;
        int v3 = 0;
        for (final Type a6 : a2) {
            a3.add(new VarInsnNode(a6.getOpcode(21), v2));
            if (v1 != null && v3 < v1.length && v1[v3] != null) {
                a3.add(new TypeInsnNode(192, v1[v3].getInternalName()));
            }
            v2 += a6.getSize();
            if (a5 >= a4 && v2 >= a5) {
                return;
            }
            ++v3;
        }
    }
    
    public static Map<LabelNode, LabelNode> cloneLabels(final InsnList v-1) {
        final Map<LabelNode, LabelNode> v0 = new HashMap<LabelNode, LabelNode>();
        for (final AbstractInsnNode a1 : v-1) {
            if (a1 instanceof LabelNode) {
                v0.put((LabelNode)a1, new LabelNode(((LabelNode)a1).getLabel()));
            }
        }
        return v0;
    }
    
    public static String generateDescriptor(final Object a2, final Object... v1) {
        final StringBuilder v2 = new StringBuilder().append('(');
        for (final Object a3 : v1) {
            v2.append(toDescriptor(a3));
        }
        return v2.append(')').append((a2 != null) ? toDescriptor(a2) : "V").toString();
    }
    
    private static String toDescriptor(final Object a1) {
        if (a1 instanceof String) {
            return (String)a1;
        }
        if (a1 instanceof Type) {
            return a1.toString();
        }
        if (a1 instanceof Class) {
            return Type.getDescriptor((Class<?>)a1);
        }
        return (a1 == null) ? "" : a1.toString();
    }
    
    public static String getDescriptor(final Type[] a1) {
        return "(" + Joiner.on("").join((Object[])a1) + ")";
    }
    
    public static String getDescriptor(final Type[] a1, final Type a2) {
        return getDescriptor(a1) + a2.toString();
    }
    
    public static String changeDescriptorReturnType(final String a1, final String a2) {
        if (a1 == null) {
            return null;
        }
        if (a2 == null) {
            return a1;
        }
        return a1.substring(0, a1.lastIndexOf(41) + 1) + a2;
    }
    
    public static String getSimpleName(final Class<? extends Annotation> a1) {
        return a1.getSimpleName();
    }
    
    public static String getSimpleName(final AnnotationNode a1) {
        return getSimpleName(a1.desc);
    }
    
    public static String getSimpleName(final String a1) {
        final int v1 = Math.max(a1.lastIndexOf(47), 0);
        return a1.substring(v1 + 1).replace(";", "");
    }
    
    public static boolean isConstant(final AbstractInsnNode a1) {
        return a1 != null && Ints.contains(Bytecode.CONSTANTS_ALL, a1.getOpcode());
    }
    
    public static Object getConstant(final AbstractInsnNode v1) {
        if (v1 == null) {
            return null;
        }
        if (v1 instanceof LdcInsnNode) {
            return ((LdcInsnNode)v1).cst;
        }
        if (!(v1 instanceof IntInsnNode)) {
            final int v2 = Ints.indexOf(Bytecode.CONSTANTS_ALL, v1.getOpcode());
            return (v2 < 0) ? null : Bytecode.CONSTANTS_VALUES[v2];
        }
        final int a1 = ((IntInsnNode)v1).operand;
        if (v1.getOpcode() == 16 || v1.getOpcode() == 17) {
            return a1;
        }
        throw new IllegalArgumentException("IntInsnNode with invalid opcode " + v1.getOpcode() + " in getConstant");
    }
    
    public static Type getConstantType(final AbstractInsnNode v1) {
        if (v1 == null) {
            return null;
        }
        if (!(v1 instanceof LdcInsnNode)) {
            final int v2 = Ints.indexOf(Bytecode.CONSTANTS_ALL, v1.getOpcode());
            return (v2 < 0) ? null : Type.getType(Bytecode.CONSTANTS_TYPES[v2]);
        }
        final Object a1 = ((LdcInsnNode)v1).cst;
        if (a1 instanceof Integer) {
            return Type.getType("I");
        }
        if (a1 instanceof Float) {
            return Type.getType("F");
        }
        if (a1 instanceof Long) {
            return Type.getType("J");
        }
        if (a1 instanceof Double) {
            return Type.getType("D");
        }
        if (a1 instanceof String) {
            return Type.getType("Ljava/lang/String;");
        }
        if (a1 instanceof Type) {
            return Type.getType("Ljava/lang/Class;");
        }
        throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + a1.getClass() + " in getConstant");
    }
    
    public static boolean hasFlag(final ClassNode a1, final int a2) {
        return (a1.access & a2) == a2;
    }
    
    public static boolean hasFlag(final MethodNode a1, final int a2) {
        return (a1.access & a2) == a2;
    }
    
    public static boolean hasFlag(final FieldNode a1, final int a2) {
        return (a1.access & a2) == a2;
    }
    
    public static boolean compareFlags(final MethodNode a1, final MethodNode a2, final int a3) {
        return hasFlag(a1, a3) == hasFlag(a2, a3);
    }
    
    public static boolean compareFlags(final FieldNode a1, final FieldNode a2, final int a3) {
        return hasFlag(a1, a3) == hasFlag(a2, a3);
    }
    
    public static Visibility getVisibility(final MethodNode a1) {
        return getVisibility(a1.access & 0x7);
    }
    
    public static Visibility getVisibility(final FieldNode a1) {
        return getVisibility(a1.access & 0x7);
    }
    
    private static Visibility getVisibility(final int a1) {
        if ((a1 & 0x4) != 0x0) {
            return Visibility.PROTECTED;
        }
        if ((a1 & 0x2) != 0x0) {
            return Visibility.PRIVATE;
        }
        if ((a1 & 0x1) != 0x0) {
            return Visibility.PUBLIC;
        }
        return Visibility.PACKAGE;
    }
    
    public static void setVisibility(final MethodNode a1, final Visibility a2) {
        a1.access = setVisibility(a1.access, a2.access);
    }
    
    public static void setVisibility(final FieldNode a1, final Visibility a2) {
        a1.access = setVisibility(a1.access, a2.access);
    }
    
    public static void setVisibility(final MethodNode a1, final int a2) {
        a1.access = setVisibility(a1.access, a2);
    }
    
    public static void setVisibility(final FieldNode a1, final int a2) {
        a1.access = setVisibility(a1.access, a2);
    }
    
    private static int setVisibility(final int a1, final int a2) {
        return (a1 & 0xFFFFFFF8) | (a2 & 0x7);
    }
    
    public static int getMaxLineNumber(final ClassNode v1, final int v2, final int v3) {
        int v4 = 0;
        for (final MethodNode a3 : v1.methods) {
            for (final AbstractInsnNode a5 : a3.instructions) {
                if (a5 instanceof LineNumberNode) {
                    v4 = Math.max(v4, ((LineNumberNode)a5).line);
                }
            }
        }
        return Math.max(v2, v4 + v3);
    }
    
    public static String getBoxingType(final Type a1) {
        return (a1 == null) ? null : Bytecode.BOXING_TYPES[a1.getSort()];
    }
    
    public static String getUnboxingMethod(final Type a1) {
        return (a1 == null) ? null : Bytecode.UNBOXING_METHODS[a1.getSort()];
    }
    
    public static void mergeAnnotations(final ClassNode a1, final ClassNode a2) {
        a2.visibleAnnotations = mergeAnnotations(a1.visibleAnnotations, a2.visibleAnnotations, "class", a1.name);
        a2.invisibleAnnotations = mergeAnnotations(a1.invisibleAnnotations, a2.invisibleAnnotations, "class", a1.name);
    }
    
    public static void mergeAnnotations(final MethodNode a1, final MethodNode a2) {
        a2.visibleAnnotations = mergeAnnotations(a1.visibleAnnotations, a2.visibleAnnotations, "method", a1.name);
        a2.invisibleAnnotations = mergeAnnotations(a1.invisibleAnnotations, a2.invisibleAnnotations, "method", a1.name);
    }
    
    public static void mergeAnnotations(final FieldNode a1, final FieldNode a2) {
        a2.visibleAnnotations = mergeAnnotations(a1.visibleAnnotations, a2.visibleAnnotations, "field", a1.name);
        a2.invisibleAnnotations = mergeAnnotations(a1.invisibleAnnotations, a2.invisibleAnnotations, "field", a1.name);
    }
    
    private static List<AnnotationNode> mergeAnnotations(final List<AnnotationNode> a4, List<AnnotationNode> v1, final String v2, final String v3) {
        try {
            if (a4 == null) {
                return v1;
            }
            if (v1 == null) {
                v1 = new ArrayList<AnnotationNode>();
            }
            for (final AnnotationNode a5 : a4) {
                if (!isMergeableAnnotation(a5)) {
                    continue;
                }
                final Iterator<AnnotationNode> a6 = v1.iterator();
                while (a6.hasNext()) {
                    if (a6.next().desc.equals(a5.desc)) {
                        a6.remove();
                        break;
                    }
                }
                v1.add(a5);
            }
        }
        catch (Exception a7) {
            Bytecode.logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[] { v2, v3 });
        }
        return v1;
    }
    
    private static boolean isMergeableAnnotation(final AnnotationNode a1) {
        return !a1.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF) || Bytecode.mergeableAnnotationPattern.matcher(a1.desc).matches();
    }
    
    private static Pattern getMergeableAnnotationPattern() {
        final StringBuilder v0 = new StringBuilder("^L(");
        for (int v2 = 0; v2 < Bytecode.MERGEABLE_MIXIN_ANNOTATIONS.length; ++v2) {
            if (v2 > 0) {
                v0.append('|');
            }
            v0.append(Bytecode.MERGEABLE_MIXIN_ANNOTATIONS[v2].getName().replace('.', '/'));
        }
        return Pattern.compile(v0.append(");$").toString());
    }
    
    public static void compareBridgeMethods(final MethodNode v-6, final MethodNode v-5) {
        final ListIterator<AbstractInsnNode> iterator = v-6.instructions.iterator();
        final ListIterator<AbstractInsnNode> iterator2 = v-5.instructions.iterator();
        int n = 0;
        while (iterator.hasNext() && iterator2.hasNext()) {
            final AbstractInsnNode a3 = iterator.next();
            final AbstractInsnNode v0 = iterator2.next();
            if (!(a3 instanceof LabelNode)) {
                if (a3 instanceof MethodInsnNode) {
                    final MethodInsnNode a1 = (MethodInsnNode)a3;
                    final MethodInsnNode a2 = (MethodInsnNode)v0;
                    if (!a1.name.equals(a2.name)) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, v-6.name, v-6.desc, n, a3, v0);
                    }
                    if (!a1.desc.equals(a2.desc)) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, v-6.name, v-6.desc, n, a3, v0);
                    }
                }
                else {
                    if (a3.getOpcode() != v0.getOpcode()) {
                        throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, v-6.name, v-6.desc, n, a3, v0);
                    }
                    if (a3 instanceof VarInsnNode) {
                        final VarInsnNode v2 = (VarInsnNode)a3;
                        final VarInsnNode v3 = (VarInsnNode)v0;
                        if (v2.var != v3.var) {
                            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, v-6.name, v-6.desc, n, a3, v0);
                        }
                    }
                    else if (a3 instanceof TypeInsnNode) {
                        final TypeInsnNode v4 = (TypeInsnNode)a3;
                        final TypeInsnNode v5 = (TypeInsnNode)v0;
                        if (v4.getOpcode() == 192 && !v4.desc.equals(v5.desc)) {
                            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, v-6.name, v-6.desc, n, a3, v0);
                        }
                    }
                }
            }
            ++n;
        }
        if (iterator.hasNext() || iterator2.hasNext()) {
            throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, v-6.name, v-6.desc, n, null, null);
        }
    }
    
    static {
        CONSTANTS_INT = new int[] { 2, 3, 4, 5, 6, 7, 8 };
        CONSTANTS_FLOAT = new int[] { 11, 12, 13 };
        CONSTANTS_DOUBLE = new int[] { 14, 15 };
        CONSTANTS_LONG = new int[] { 9, 10 };
        CONSTANTS_ALL = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18 };
        CONSTANTS_VALUES = new Object[] { null, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, 0.0f, 1.0f, 2.0f, 0.0, 1.0 };
        CONSTANTS_TYPES = new String[] { null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I" };
        BOXING_TYPES = new String[] { null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null };
        UNBOXING_METHODS = new String[] { null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null };
        MERGEABLE_MIXIN_ANNOTATIONS = new Class[] { Overwrite.class, Intrinsic.class, Final.class, Debug.class };
        Bytecode.mergeableAnnotationPattern = getMergeableAnnotationPattern();
        logger = LogManager.getLogger("mixin");
    }
    
    public enum Visibility
    {
        PRIVATE(2), 
        PROTECTED(4), 
        PACKAGE(0), 
        PUBLIC(1);
        
        static final int MASK = 7;
        final int access;
        private static final /* synthetic */ Visibility[] $VALUES;
        
        public static Visibility[] values() {
            return Visibility.$VALUES.clone();
        }
        
        public static Visibility valueOf(final String a1) {
            return Enum.valueOf(Visibility.class, a1);
        }
        
        private Visibility(final int a1) {
            this.access = a1;
        }
        
        static {
            $VALUES = new Visibility[] { Visibility.PRIVATE, Visibility.PROTECTED, Visibility.PACKAGE, Visibility.PUBLIC };
        }
    }
}
