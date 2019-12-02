package org.spongepowered.asm.util;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.util.throwables.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.asm.*;
import org.spongepowered.asm.lib.tree.analysis.*;
import java.util.*;

public final class Locals
{
    private static final Map<String, List<LocalVariableNode>> calculatedLocalVariables;
    
    private Locals() {
        super();
    }
    
    public static void loadLocals(final Type[] a1, final InsnList a2, int a3, int a4) {
        while (a3 < a1.length && a4 > 0) {
            if (a1[a3] != null) {
                a2.add(new VarInsnNode(a1[a3].getOpcode(21), a3));
                --a4;
            }
            ++a3;
        }
    }
    
    public static LocalVariableNode[] getLocalsAt(final ClassNode v-19, final MethodNode v-18, AbstractInsnNode v-17) {
        for (int a1 = 0; a1 < 3 && (v-17 instanceof LabelNode || v-17 instanceof LineNumberNode); v-17 = nextNode(v-18.instructions, v-17), ++a1) {}
        final ClassInfo forName = ClassInfo.forName(v-19.name);
        if (forName == null) {
            throw new LVTGeneratorException("Could not load class metadata for " + v-19.name + " generating LVT for " + v-18.name);
        }
        final ClassInfo.Method method = forName.findMethod(v-18);
        if (method == null) {
            throw new LVTGeneratorException("Could not locate method metadata for " + v-18.name + " generating LVT in " + v-19.name);
        }
        final List<ClassInfo.FrameData> frames = method.getFrames();
        final LocalVariableNode[] array = new LocalVariableNode[v-18.maxLocals];
        int a4 = 0;
        int n = 0;
        if ((v-18.access & 0x8) == 0x0) {
            array[a4++] = new LocalVariableNode("this", v-19.name, null, null, null, 0);
        }
        for (final Type a2 : Type.getArgumentTypes(v-18.desc)) {
            array[a4] = new LocalVariableNode("arg" + n++, a2.toString(), null, null, null, a4);
            a4 += a2.getSize();
        }
        final int n2 = a4;
        int n3 = -1;
        int n4 = 0;
        for (final AbstractInsnNode abstractInsnNode : v-18.instructions) {
            if (abstractInsnNode instanceof FrameNode) {
                ++n3;
                final FrameNode frameNode = (FrameNode)abstractInsnNode;
                final ClassInfo.FrameData frameData = (n3 < frames.size()) ? frames.get(n3) : null;
                n4 = ((frameData != null && frameData.type == 0) ? Math.min(n4, frameData.locals) : frameNode.local.size());
                for (int n5 = 0, j = 0; j < array.length; ++j, ++n5) {
                    final Object o = (n5 < frameNode.local.size()) ? frameNode.local.get(n5) : null;
                    if (o instanceof String) {
                        array[j] = getLocalVariableAt(v-19, v-18, v-17, j);
                    }
                    else if (o instanceof Integer) {
                        final boolean a3 = o == Opcodes.UNINITIALIZED_THIS || o == Opcodes.NULL;
                        final boolean v1 = o == Opcodes.INTEGER || o == Opcodes.FLOAT;
                        final boolean v2 = o == Opcodes.DOUBLE || o == Opcodes.LONG;
                        if (o != Opcodes.TOP) {
                            if (a3) {
                                array[j] = null;
                            }
                            else {
                                if (!v1 && !v2) {
                                    throw new LVTGeneratorException("Unrecognised locals opcode " + o + " in locals array at position " + n5 + " in " + v-19.name + "." + v-18.name + v-18.desc);
                                }
                                array[j] = getLocalVariableAt(v-19, v-18, v-17, j);
                                if (v2) {
                                    ++j;
                                    array[j] = null;
                                }
                            }
                        }
                    }
                    else {
                        if (o != null) {
                            throw new LVTGeneratorException("Invalid value " + o + " in locals array at position " + n5 + " in " + v-19.name + "." + v-18.name + v-18.desc);
                        }
                        if (j >= n2 && j >= n4 && n4 > 0) {
                            array[j] = null;
                        }
                    }
                }
            }
            else if (abstractInsnNode instanceof VarInsnNode) {
                final VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode;
                array[varInsnNode.var] = getLocalVariableAt(v-19, v-18, v-17, varInsnNode.var);
            }
            if (abstractInsnNode == v-17) {
                break;
            }
        }
        for (int k = 0; k < array.length; ++k) {
            if (array[k] != null && array[k].desc == null) {
                array[k] = null;
            }
        }
        return array;
    }
    
    public static LocalVariableNode getLocalVariableAt(final ClassNode a1, final MethodNode a2, final AbstractInsnNode a3, final int a4) {
        return getLocalVariableAt(a1, a2, a2.instructions.indexOf(a3), a4);
    }
    
    private static LocalVariableNode getLocalVariableAt(final ClassNode a3, final MethodNode a4, final int v1, final int v2) {
        LocalVariableNode v3 = null;
        LocalVariableNode v4 = null;
        for (final LocalVariableNode a5 : getLocalVariableTable(a3, a4)) {
            if (a5.index != v2) {
                continue;
            }
            if (isOpcodeInRange(a4.instructions, a5, v1)) {
                v3 = a5;
            }
            else {
                if (v3 != null) {
                    continue;
                }
                v4 = a5;
            }
        }
        if (v3 == null && !a4.localVariables.isEmpty()) {
            for (final LocalVariableNode a6 : getGeneratedLocalVariableTable(a3, a4)) {
                if (a6.index == v2 && isOpcodeInRange(a4.instructions, a6, v1)) {
                    v3 = a6;
                }
            }
        }
        return (v3 != null) ? v3 : v4;
    }
    
    private static boolean isOpcodeInRange(final InsnList a1, final LocalVariableNode a2, final int a3) {
        return a1.indexOf(a2.start) < a3 && a1.indexOf(a2.end) > a3;
    }
    
    public static List<LocalVariableNode> getLocalVariableTable(final ClassNode a1, final MethodNode a2) {
        if (a2.localVariables.isEmpty()) {
            return getGeneratedLocalVariableTable(a1, a2);
        }
        return a2.localVariables;
    }
    
    public static List<LocalVariableNode> getGeneratedLocalVariableTable(final ClassNode a1, final MethodNode a2) {
        final String v1 = String.format("%s.%s%s", a1.name, a2.name, a2.desc);
        List<LocalVariableNode> v2 = Locals.calculatedLocalVariables.get(v1);
        if (v2 != null) {
            return v2;
        }
        v2 = generateLocalVariableTable(a1, a2);
        Locals.calculatedLocalVariables.put(v1, v2);
        return v2;
    }
    
    public static List<LocalVariableNode> generateLocalVariableTable(final ClassNode v-16, final MethodNode v-15) {
        List<Type> a3 = null;
        if (v-16.interfaces != null) {
            a3 = new ArrayList<Type>();
            for (final String a1 : v-16.interfaces) {
                a3.add(Type.getObjectType(a1));
            }
        }
        Type objectType = null;
        if (v-16.superName != null) {
            objectType = Type.getObjectType(v-16.superName);
        }
        final Analyzer<BasicValue> analyzer = new Analyzer<BasicValue>(new MixinVerifier(Type.getObjectType(v-16.name), objectType, a3, false));
        try {
            analyzer.analyze(v-16.name, v-15);
        }
        catch (AnalyzerException a2) {
            a2.printStackTrace();
        }
        final Frame<BasicValue>[] frames = analyzer.getFrames();
        final int size = v-15.instructions.size();
        final List<LocalVariableNode> list = new ArrayList<LocalVariableNode>();
        final LocalVariableNode[] array = new LocalVariableNode[v-15.maxLocals];
        final BasicValue[] array2 = new BasicValue[v-15.maxLocals];
        final LabelNode[] array3 = new LabelNode[size];
        final String[] array4 = new String[v-15.maxLocals];
        for (int i = 0; i < size; ++i) {
            final Frame<BasicValue> frame = frames[i];
            if (frame != null) {
                LabelNode a4 = null;
                for (int j = 0; j < frame.getLocals(); ++j) {
                    final BasicValue v0 = (BasicValue)frame.getLocal(j);
                    if (v0 != null || array2[j] != null) {
                        if (v0 == null || !v0.equals(array2[j])) {
                            if (a4 == null) {
                                final AbstractInsnNode v2 = v-15.instructions.get(i);
                                if (v2 instanceof LabelNode) {
                                    a4 = (LabelNode)v2;
                                }
                                else {
                                    a4 = (array3[i] = new LabelNode());
                                }
                            }
                            if (v0 == null && array2[j] != null) {
                                list.add(array[j]);
                                array[j].end = a4;
                                array[j] = null;
                            }
                            else if (v0 != null) {
                                if (array2[j] != null) {
                                    list.add(array[j]);
                                    array[j].end = a4;
                                    array[j] = null;
                                }
                                final String v3 = (v0.getType() != null) ? v0.getType().getDescriptor() : array4[j];
                                array[j] = new LocalVariableNode("var" + j, v3, null, a4, null, j);
                                if (v3 != null) {
                                    array4[j] = v3;
                                }
                            }
                            array2[j] = v0;
                        }
                    }
                }
            }
        }
        LabelNode labelNode = null;
        for (int k = 0; k < array.length; ++k) {
            if (array[k] != null) {
                if (labelNode == null) {
                    labelNode = new LabelNode();
                    v-15.instructions.add(labelNode);
                }
                array[k].end = labelNode;
                list.add(array[k]);
            }
        }
        for (int k = size - 1; k >= 0; --k) {
            if (array3[k] != null) {
                v-15.instructions.insert(v-15.instructions.get(k), array3[k]);
            }
        }
        return list;
    }
    
    private static AbstractInsnNode nextNode(final InsnList a1, final AbstractInsnNode a2) {
        final int v1 = a1.indexOf(a2) + 1;
        if (v1 > 0 && v1 < a1.size()) {
            return a1.get(v1);
        }
        return a2;
    }
    
    static {
        calculatedLocalVariables = new HashMap<String, List<LocalVariableNode>>();
    }
}
