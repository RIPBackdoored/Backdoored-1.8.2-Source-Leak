package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import java.lang.annotation.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

public class SyntheticBridgeException extends MixinException
{
    private static final long serialVersionUID = 1L;
    private final Problem problem;
    private final String name;
    private final String desc;
    private final int index;
    private final AbstractInsnNode a;
    private final AbstractInsnNode b;
    
    public SyntheticBridgeException(final Problem a1, final String a2, final String a3, final int a4, final AbstractInsnNode a5, final AbstractInsnNode a6) {
        super(a1.getMessage(a2, a3, a4, a5, a6));
        this.problem = a1;
        this.name = a2;
        this.desc = a3;
        this.index = a4;
        this.a = a5;
        this.b = a6;
    }
    
    public void printAnalysis(final IMixinContext a1, final MethodNode a2, final MethodNode a3) {
        final PrettyPrinter v1 = new PrettyPrinter();
        v1.addWrapped(100, this.getMessage(), new Object[0]).hr();
        v1.add().kv("Method", (Object)(this.name + this.desc)).kv("Problem Type", this.problem).add().hr();
        final String v2 = Annotations.getValue(Annotations.getVisible(a2, MixinMerged.class), "mixin");
        final String v3 = (v2 != null) ? v2 : a1.getTargetClassRef().replace('/', '.');
        this.printMethod(v1.add("Existing method").add().kv("Owner", (Object)v3).add(), a2).hr();
        this.printMethod(v1.add("Incoming method").add().kv("Owner", (Object)a1.getClassRef().replace('/', '.')).add(), a3).hr();
        this.printProblem(v1, a1, a2, a3).print(System.err);
    }
    
    private PrettyPrinter printMethod(final PrettyPrinter v1, final MethodNode v2) {
        int v3 = 0;
        final Iterator<AbstractInsnNode> a1 = v2.instructions.iterator();
        while (a1.hasNext()) {
            v1.kv((v3 == this.index) ? ">>>>" : "", (Object)Bytecode.describeNode(a1.next()));
            ++v3;
        }
        return v1.add();
    }
    
    private PrettyPrinter printProblem(final PrettyPrinter v-14, final IMixinContext v-13, final MethodNode v-12, final MethodNode v-11) {
        final Type objectType = Type.getObjectType(v-13.getTargetClassRef());
        v-14.add("Analysis").add();
        switch (this.problem) {
            case BAD_INSN: {
                v-14.add("The bridge methods are not compatible because they contain incompatible opcodes");
                v-14.add("at index " + this.index + ":").add();
                v-14.kv("Existing opcode: %s", (Object)Bytecode.getOpcodeName(this.a));
                v-14.kv("Incoming opcode: %s", (Object)Bytecode.getOpcodeName(this.b)).add();
                v-14.add("This implies that the bridge methods are from different interfaces. This problem");
                v-14.add("may not be resolvable without changing the base interfaces.").add();
                break;
            }
            case BAD_LOAD: {
                v-14.add("The bridge methods are not compatible because they contain different variables at");
                v-14.add("opcode index " + this.index + ".").add();
                final ListIterator<AbstractInsnNode> iterator = v-12.instructions.iterator();
                final ListIterator<AbstractInsnNode> iterator2 = v-11.instructions.iterator();
                final Type[] argumentTypes = Type.getArgumentTypes(v-12.desc);
                final Type[] argumentTypes2 = Type.getArgumentTypes(v-11.desc);
                int n = 0;
                while (iterator.hasNext() && iterator2.hasNext()) {
                    final AbstractInsnNode abstractInsnNode = iterator.next();
                    final AbstractInsnNode abstractInsnNode2 = iterator2.next();
                    if (abstractInsnNode instanceof VarInsnNode && abstractInsnNode2 instanceof VarInsnNode) {
                        final VarInsnNode a2 = (VarInsnNode)abstractInsnNode;
                        final VarInsnNode a3 = (VarInsnNode)abstractInsnNode2;
                        final Type a4 = (a2.var > 0) ? argumentTypes[a2.var - 1] : objectType;
                        final Type v1 = (a3.var > 0) ? argumentTypes2[a3.var - 1] : objectType;
                        v-14.kv("Target " + n, "%8s %-2d %s", Bytecode.getOpcodeName(a2), a2.var, a4);
                        v-14.kv("Incoming " + n, "%8s %-2d %s", Bytecode.getOpcodeName(a3), a3.var, v1);
                        if (a4.equals(v1)) {
                            v-14.kv("", "Types match: %s", a4);
                        }
                        else if (a4.getSort() != v1.getSort()) {
                            v-14.kv("", (Object)"Types are incompatible");
                        }
                        else if (a4.getSort() == 10) {
                            final ClassInfo a5 = ClassInfo.getCommonSuperClassOrInterface(a4, v1);
                            v-14.kv("", "Common supertype: %s", a5);
                        }
                        v-14.add();
                    }
                    ++n;
                }
                v-14.add("Since this probably means that the methods come from different interfaces, you");
                v-14.add("may have a \"multiple inheritance\" problem, it may not be possible to implement");
                v-14.add("both root interfaces");
                break;
            }
            case BAD_CAST: {
                v-14.add("Incompatible CHECKCAST encountered at opcode " + this.index + ", this could indicate that the bridge");
                v-14.add("is casting down for contravariant generic types. It may be possible to coalesce the");
                v-14.add("bridges by adjusting the types in the target method.").add();
                final Type objectType2 = Type.getObjectType(((TypeInsnNode)this.a).desc);
                final Type objectType3 = Type.getObjectType(((TypeInsnNode)this.b).desc);
                v-14.kv("Target type", objectType2);
                v-14.kv("Incoming type", objectType3);
                v-14.kv("Common supertype", ClassInfo.getCommonSuperClassOrInterface(objectType2, objectType3)).add();
                break;
            }
            case BAD_INVOKE_NAME: {
                v-14.add("Incompatible invocation targets in synthetic bridge. This is extremely unusual");
                v-14.add("and implies that a remapping transformer has incorrectly remapped a method. This");
                v-14.add("is an unrecoverable error.");
                break;
            }
            case BAD_INVOKE_DESC: {
                final MethodInsnNode methodInsnNode = (MethodInsnNode)this.a;
                final MethodInsnNode methodInsnNode2 = (MethodInsnNode)this.b;
                final Type[] argumentTypes3 = Type.getArgumentTypes(methodInsnNode.desc);
                final Type[] v2 = Type.getArgumentTypes(methodInsnNode2.desc);
                if (argumentTypes3.length != v2.length) {
                    final int v3 = Type.getArgumentTypes(v-12.desc).length;
                    final String v4 = (argumentTypes3.length == v3) ? "The TARGET" : ((v2.length == v3) ? " The INCOMING" : "NEITHER");
                    v-14.add("Mismatched invocation descriptors in synthetic bridge implies that a remapping");
                    v-14.add("transformer has incorrectly coalesced a bridge method with a conflicting name.");
                    v-14.add("Overlapping bridge methods should always have the same number of arguments, yet");
                    v-14.add("the target method has %d arguments, the incoming method has %d. This is an", argumentTypes3.length, v2.length);
                    v-14.add("unrecoverable error. %s method has the expected arg count of %d", v4, v3);
                    break;
                }
                final Type v1 = Type.getReturnType(methodInsnNode.desc);
                final Type v5 = Type.getReturnType(methodInsnNode2.desc);
                v-14.add("Incompatible invocation descriptors in synthetic bridge implies that generified");
                v-14.add("types are incompatible over one or more generic superclasses or interfaces. It may");
                v-14.add("be possible to adjust the generic types on implemented members to rectify this");
                v-14.add("problem by coalescing the appropriate generic types.").add();
                this.printTypeComparison(v-14, "return type", v1, v5);
                for (int v6 = 0; v6 < argumentTypes3.length; ++v6) {
                    this.printTypeComparison(v-14, "arg " + v6, argumentTypes3[v6], v2[v6]);
                }
                break;
            }
            case BAD_LENGTH: {
                v-14.add("Mismatched bridge method length implies the bridge methods are incompatible");
                v-14.add("and may originate from different superinterfaces. This is an unrecoverable");
                v-14.add("error.").add();
                break;
            }
        }
        return v-14;
    }
    
    private PrettyPrinter printTypeComparison(final PrettyPrinter a3, final String a4, final Type v1, final Type v2) {
        a3.kv("Target " + a4, "%s", v1);
        a3.kv("Incoming " + a4, "%s", v2);
        if (v1.equals(v2)) {
            a3.kv("Analysis", "Types match: %s", v1);
        }
        else if (v1.getSort() != v2.getSort()) {
            a3.kv("Analysis", (Object)"Types are incompatible");
        }
        else if (v1.getSort() == 10) {
            final ClassInfo a5 = ClassInfo.getCommonSuperClassOrInterface(v1, v2);
            a3.kv("Analysis", "Common supertype: L%s;", a5);
        }
        return a3.add();
    }
    
    public enum Problem
    {
        BAD_INSN("Conflicting opcodes %4$s and %5$s at offset %3$d in synthetic bridge method %1$s%2$s"), 
        BAD_LOAD("Conflicting variable access at offset %3$d in synthetic bridge method %1$s%2$s"), 
        BAD_CAST("Conflicting type cast at offset %3$d in synthetic bridge method %1$s%2$s"), 
        BAD_INVOKE_NAME("Conflicting synthetic bridge target method name in synthetic bridge method %1$s%2$s Existing:%6$s Incoming:%7$s"), 
        BAD_INVOKE_DESC("Conflicting synthetic bridge target method descriptor in synthetic bridge method %1$s%2$s Existing:%8$s Incoming:%9$s"), 
        BAD_LENGTH("Mismatched bridge method length for synthetic bridge method %1$s%2$s unexpected extra opcode at offset %3$d");
        
        private final String message;
        private static final /* synthetic */ Problem[] $VALUES;
        
        public static Problem[] values() {
            return Problem.$VALUES.clone();
        }
        
        public static Problem valueOf(final String a1) {
            return Enum.valueOf(Problem.class, a1);
        }
        
        private Problem(final String a1) {
            this.message = a1;
        }
        
        String getMessage(final String a1, final String a2, final int a3, final AbstractInsnNode a4, final AbstractInsnNode a5) {
            return String.format(this.message, a1, a2, a3, Bytecode.getOpcodeName(a4), Bytecode.getOpcodeName(a4), getInsnName(a4), getInsnName(a5), getInsnDesc(a4), getInsnDesc(a5));
        }
        
        private static String getInsnName(final AbstractInsnNode a1) {
            return (a1 instanceof MethodInsnNode) ? ((MethodInsnNode)a1).name : "";
        }
        
        private static String getInsnDesc(final AbstractInsnNode a1) {
            return (a1 instanceof MethodInsnNode) ? ((MethodInsnNode)a1).desc : "";
        }
        
        static {
            $VALUES = new Problem[] { Problem.BAD_INSN, Problem.BAD_LOAD, Problem.BAD_CAST, Problem.BAD_INVOKE_NAME, Problem.BAD_INVOKE_DESC, Problem.BAD_LENGTH };
        }
    }
}
