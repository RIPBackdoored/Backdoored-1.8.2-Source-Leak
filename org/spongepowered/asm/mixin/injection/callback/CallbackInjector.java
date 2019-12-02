package org.spongepowered.asm.mixin.injection.callback;

import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.points.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import com.google.common.base.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

public class CallbackInjector extends Injector
{
    private final boolean cancellable;
    private final LocalCapture localCapture;
    private final String identifier;
    private final Map<Integer, String> ids;
    private int totalInjections;
    private int callbackInfoVar;
    private String lastId;
    private String lastDesc;
    private Target lastTarget;
    private String callbackInfoClass;
    
    public CallbackInjector(final InjectionInfo a1, final boolean a2, final LocalCapture a3, final String a4) {
        super(a1);
        this.ids = new HashMap<Integer, String>();
        this.totalInjections = 0;
        this.callbackInfoVar = -1;
        this.cancellable = a2;
        this.localCapture = a3;
        this.identifier = a4;
    }
    
    @Override
    protected void sanityCheck(final Target v1, final List<InjectionPoint> v2) {
        super.sanityCheck(v1, v2);
        if (v1.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' modifier of callback method does not match target in " + this);
        }
        if ("<init>".equals(v1.method.name)) {
            for (final InjectionPoint a1 : v2) {
                if (!a1.getClass().equals(BeforeReturn.class)) {
                    throw new InvalidInjectionException(this.info, "Found injection point type " + a1.getClass().getSimpleName() + " targetting a ctor in " + this + ". Only RETURN allowed for a ctor target");
                }
            }
        }
    }
    
    @Override
    protected void addTargetNode(final Target v1, final List<InjectionNodes.InjectionNode> v2, final AbstractInsnNode v3, final Set<InjectionPoint> v4) {
        final InjectionNodes.InjectionNode v5 = v1.addInjectionNode(v3);
        for (final InjectionPoint a3 : v4) {
            final String a4 = a3.getId();
            if (Strings.isNullOrEmpty(a4)) {
                continue;
            }
            final String a5 = this.ids.get(v5.getId());
            if (a5 != null && !a5.equals(a4)) {
                Injector.logger.warn("Conflicting id for {} insn in {}, found id {} on {}, previously defined as {}", new Object[] { Bytecode.getOpcodeName(v3), v1.toString(), a4, this.info, a5 });
                break;
            }
            this.ids.put(v5.getId(), a4);
        }
        v2.add(v5);
        ++this.totalInjections;
    }
    
    @Override
    protected void inject(final Target a1, final InjectionNodes.InjectionNode a2) {
        LocalVariableNode[] v1 = null;
        if (this.localCapture.isCaptureLocals() || this.localCapture.isPrintLocals()) {
            v1 = Locals.getLocalsAt(this.classNode, a1.method, a2.getCurrentTarget());
        }
        this.inject(new Callback(this.methodNode, a1, a2, v1, this.localCapture.isCaptureLocals()));
    }
    
    private void inject(final Callback v-1) {
        if (this.localCapture.isPrintLocals()) {
            this.printLocals(v-1);
            this.info.addCallbackInvocation(this.methodNode);
            return;
        }
        MethodNode v0 = this.methodNode;
        if (!v-1.checkDescriptor(this.methodNode.desc)) {
            if (this.info.getTargets().size() > 1) {
                return;
            }
            if (v-1.canCaptureLocals) {
                final MethodNode v2 = Bytecode.findMethod(this.classNode, this.methodNode.name, v-1.getDescriptor());
                if (v2 != null && Annotations.getVisible(v2, Surrogate.class) != null) {
                    v0 = v2;
                }
                else {
                    final String a1 = this.generateBadLVTMessage(v-1);
                    switch (this.localCapture) {
                        case CAPTURE_FAILEXCEPTION: {
                            Injector.logger.error("Injection error: {}", new Object[] { a1 });
                            v0 = this.generateErrorMethod(v-1, "org/spongepowered/asm/mixin/injection/throwables/InjectionError", a1);
                            break;
                        }
                        case CAPTURE_FAILSOFT: {
                            Injector.logger.warn("Injection warning: {}", new Object[] { a1 });
                            return;
                        }
                        default: {
                            Injector.logger.error("Critical injection failure: {}", new Object[] { a1 });
                            throw new InjectionError(a1);
                        }
                    }
                }
            }
            else {
                final String v3 = this.methodNode.desc.replace("Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfo;", "Lorg/spongepowered/asm/mixin/injection/callback/CallbackInfoReturnable;");
                if (v-1.checkDescriptor(v3)) {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! CallbackInfoReturnable is required!");
                }
                final MethodNode v4 = Bytecode.findMethod(this.classNode, this.methodNode.name, v-1.getDescriptor());
                if (v4 == null || Annotations.getVisible(v4, Surrogate.class) == null) {
                    throw new InvalidInjectionException(this.info, "Invalid descriptor on " + this.info + "! Expected " + v-1.getDescriptor() + " but found " + this.methodNode.desc);
                }
                v0 = v4;
            }
        }
        this.dupReturnValue(v-1);
        if (this.cancellable || this.totalInjections > 1) {
            this.createCallbackInfo(v-1, true);
        }
        this.invokeCallback(v-1, v0);
        this.injectCancellationCode(v-1);
        v-1.inject();
        this.info.notifyInjected(v-1.target);
    }
    
    private String generateBadLVTMessage(final Callback a1) {
        final int v1 = a1.target.indexOf(a1.node);
        final List<String> v2 = summariseLocals(this.methodNode.desc, a1.target.arguments.length + 1);
        final List<String> v3 = summariseLocals(a1.getDescriptorWithAllLocals(), a1.frameSize);
        return String.format("LVT in %s has incompatible changes at opcode %d in callback %s.\nExpected: %s\n   Found: %s", a1.target, v1, this, v2, v3);
    }
    
    private MethodNode generateErrorMethod(final Callback a1, final String a2, final String a3) {
        final MethodNode v1 = this.info.addMethod(this.methodNode.access, this.methodNode.name + "$missing", a1.getDescriptor());
        v1.maxLocals = Bytecode.getFirstNonArgLocalIndex(Type.getArgumentTypes(a1.getDescriptor()), !this.isStatic);
        v1.maxStack = 3;
        final InsnList v2 = v1.instructions;
        v2.add(new TypeInsnNode(187, a2));
        v2.add(new InsnNode(89));
        v2.add(new LdcInsnNode(a3));
        v2.add(new MethodInsnNode(183, a2, "<init>", "(Ljava/lang/String;)V", false));
        v2.add(new InsnNode(191));
        return v1;
    }
    
    private void printLocals(final Callback v-5) {
        final Type[] argumentTypes = Type.getArgumentTypes(v-5.getDescriptorWithAllLocals());
        final SignaturePrinter a2 = new SignaturePrinter(v-5.target.method, v-5.argNames);
        final SignaturePrinter signaturePrinter = new SignaturePrinter(this.methodNode.name, v-5.target.returnType, argumentTypes, v-5.argNames);
        signaturePrinter.setModifiers(this.methodNode);
        final PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.kv("Target Class", (Object)this.classNode.name.replace('/', '.'));
        prettyPrinter.kv("Target Method", a2);
        prettyPrinter.kv("Target Max LOCALS", v-5.target.getMaxLocals());
        prettyPrinter.kv("Initial Frame Size", v-5.frameSize);
        prettyPrinter.kv("Callback Name", (Object)this.methodNode.name);
        prettyPrinter.kv("Instruction", "%s %s", v-5.node.getClass().getSimpleName(), Bytecode.getOpcodeName(v-5.node.getCurrentTarget().getOpcode()));
        prettyPrinter.hr();
        if (v-5.locals.length > v-5.frameSize) {
            prettyPrinter.add("  %s  %20s  %s", "LOCAL", "TYPE", "NAME");
            for (int v0 = 0; v0 < v-5.locals.length; ++v0) {
                final String v2 = (v0 == v-5.frameSize) ? ">" : " ";
                if (v-5.locals[v0] != null) {
                    prettyPrinter.add("%s [%3d]  %20s  %-50s %s", v2, v0, SignaturePrinter.getTypeName(v-5.localTypes[v0], false), meltSnowman(v0, v-5.locals[v0].name), (v0 >= v-5.frameSize) ? "<capture>" : "");
                }
                else {
                    final boolean a1 = v0 > 0 && v-5.localTypes[v0 - 1] != null && v-5.localTypes[v0 - 1].getSize() > 1;
                    prettyPrinter.add("%s [%3d]  %20s", v2, v0, a1 ? "<top>" : "-");
                }
            }
            prettyPrinter.hr();
        }
        prettyPrinter.add().add("/**").add(" * Expected callback signature").add(" * /");
        prettyPrinter.add("%s {", signaturePrinter);
        prettyPrinter.add("    // Method body").add("}").add().print(System.err);
    }
    
    private void createCallbackInfo(final Callback a1, final boolean a2) {
        if (a1.target != this.lastTarget) {
            this.lastId = null;
            this.lastDesc = null;
        }
        this.lastTarget = a1.target;
        final String v1 = this.getIdentifier(a1);
        final String v2 = a1.getCallbackInfoConstructorDescriptor();
        if (v1.equals(this.lastId) && v2.equals(this.lastDesc) && !a1.isAtReturn && !this.cancellable) {
            return;
        }
        this.instanceCallbackInfo(a1, v1, v2, a2);
    }
    
    private void loadOrCreateCallbackInfo(final Callback a1) {
        if (this.cancellable || this.totalInjections > 1) {
            a1.add(new VarInsnNode(25, this.callbackInfoVar), false, true);
        }
        else {
            this.createCallbackInfo(a1, false);
        }
    }
    
    private void dupReturnValue(final Callback a1) {
        if (!a1.isAtReturn) {
            return;
        }
        a1.add(new InsnNode(89));
        a1.add(new VarInsnNode(a1.target.returnType.getOpcode(54), a1.marshalVar()));
    }
    
    protected void instanceCallbackInfo(final Callback a1, final String a2, final String a3, final boolean a4) {
        this.lastId = a2;
        this.lastDesc = a3;
        this.callbackInfoVar = a1.marshalVar();
        this.callbackInfoClass = a1.target.getCallbackInfoClass();
        final boolean v1 = a4 && this.totalInjections > 1 && !a1.isAtReturn && !this.cancellable;
        a1.add(new TypeInsnNode(187, this.callbackInfoClass), true, !a4, v1);
        a1.add(new InsnNode(89), true, true, v1);
        a1.add(new LdcInsnNode(a2), true, !a4, v1);
        a1.add(new InsnNode(this.cancellable ? 4 : 3), true, !a4, v1);
        if (a1.isAtReturn) {
            a1.add(new VarInsnNode(a1.target.returnType.getOpcode(21), a1.marshalVar()), true, !a4);
            a1.add(new MethodInsnNode(183, this.callbackInfoClass, "<init>", a3, false));
        }
        else {
            a1.add(new MethodInsnNode(183, this.callbackInfoClass, "<init>", a3, false), false, false, v1);
        }
        if (a4) {
            a1.target.addLocalVariable(this.callbackInfoVar, "callbackInfo" + this.callbackInfoVar, "L" + this.callbackInfoClass + ";");
            a1.add(new VarInsnNode(58, this.callbackInfoVar), false, false, v1);
        }
    }
    
    private void invokeCallback(final Callback a1, final MethodNode a2) {
        if (!this.isStatic) {
            a1.add(new VarInsnNode(25, 0), false, true);
        }
        if (a1.captureArgs()) {
            Bytecode.loadArgs(a1.target.arguments, a1, this.isStatic ? 0 : 1, -1);
        }
        this.loadOrCreateCallbackInfo(a1);
        if (a1.canCaptureLocals) {
            Locals.loadLocals(a1.localTypes, a1, a1.frameSize, a1.extraArgs);
        }
        this.invokeHandler(a1, a2);
    }
    
    private String getIdentifier(final Callback a1) {
        final String v1 = Strings.isNullOrEmpty(this.identifier) ? a1.target.method.name : this.identifier;
        final String v2 = this.ids.get(a1.node.getId());
        return v1 + (Strings.isNullOrEmpty(v2) ? "" : (":" + v2));
    }
    
    protected void injectCancellationCode(final Callback a1) {
        if (!this.cancellable) {
            return;
        }
        a1.add(new VarInsnNode(25, this.callbackInfoVar));
        a1.add(new MethodInsnNode(182, this.callbackInfoClass, CallbackInfo.getIsCancelledMethodName(), CallbackInfo.getIsCancelledMethodSig(), false));
        final LabelNode v1 = new LabelNode();
        a1.add(new JumpInsnNode(153, v1));
        this.injectReturnCode(a1);
        a1.add(v1);
    }
    
    protected void injectReturnCode(final Callback v-1) {
        if (v-1.target.returnType.equals(Type.VOID_TYPE)) {
            v-1.add(new InsnNode(177));
        }
        else {
            v-1.add(new VarInsnNode(25, v-1.marshalVar()));
            final String a1 = CallbackInfoReturnable.getReturnAccessor(v-1.target.returnType);
            final String v1 = CallbackInfoReturnable.getReturnDescriptor(v-1.target.returnType);
            v-1.add(new MethodInsnNode(182, this.callbackInfoClass, a1, v1, false));
            if (v-1.target.returnType.getSort() == 10) {
                v-1.add(new TypeInsnNode(192, v-1.target.returnType.getInternalName()));
            }
            v-1.add(new InsnNode(v-1.target.returnType.getOpcode(172)));
        }
    }
    
    protected boolean isStatic() {
        return this.isStatic;
    }
    
    private static List<String> summariseLocals(final String a1, final int a2) {
        return summariseLocals(Type.getArgumentTypes(a1), a2);
    }
    
    private static List<String> summariseLocals(final Type[] a1, int a2) {
        final List<String> v1 = new ArrayList<String>();
        if (a1 != null) {
            while (a2 < a1.length) {
                if (a1[a2] != null) {
                    v1.add(a1[a2].toString());
                }
                ++a2;
            }
        }
        return v1;
    }
    
    static String meltSnowman(final int a1, final String a2) {
        return (a2 != null && '\u2603' == a2.charAt(0)) ? ("var" + a1) : a2;
    }
    
    private class Callback extends InsnList
    {
        private final MethodNode handler;
        private final AbstractInsnNode head;
        final Target target;
        final InjectionNodes.InjectionNode node;
        final LocalVariableNode[] locals;
        final Type[] localTypes;
        final int frameSize;
        final int extraArgs;
        final boolean canCaptureLocals;
        final boolean isAtReturn;
        final String desc;
        final String descl;
        final String[] argNames;
        int ctor;
        int invoke;
        private int marshalVar;
        private boolean captureArgs;
        final /* synthetic */ CallbackInjector this$0;
        
        Callback(final CallbackInjector this$0, final MethodNode a4, final Target a5, final InjectionNodes.InjectionNode a6, final LocalVariableNode[] v1, final boolean v2) {
            this.this$0 = this$0;
            super();
            this.marshalVar = -1;
            this.captureArgs = true;
            this.handler = a4;
            this.target = a5;
            this.head = a5.insns.getFirst();
            this.node = a6;
            this.locals = v1;
            this.localTypes = (Type[])((v1 != null) ? new Type[v1.length] : null);
            this.frameSize = Bytecode.getFirstNonArgLocalIndex(a5.arguments, !this$0.isStatic());
            List<String> v3 = null;
            if (v1 != null) {
                final int a7 = this$0.isStatic() ? 0 : 1;
                v3 = new ArrayList<String>();
                for (int a8 = 0; a8 <= v1.length; ++a8) {
                    if (a8 == this.frameSize) {
                        v3.add((a5.returnType == Type.VOID_TYPE) ? "ci" : "cir");
                    }
                    if (a8 < v1.length && v1[a8] != null) {
                        this.localTypes[a8] = Type.getType(v1[a8].desc);
                        if (a8 >= a7) {
                            v3.add(CallbackInjector.meltSnowman(a8, v1[a8].name));
                        }
                    }
                }
            }
            this.extraArgs = Math.max(0, Bytecode.getFirstNonArgLocalIndex(this.handler) - (this.frameSize + 1));
            this.argNames = (String[])((v3 != null) ? ((String[])v3.toArray(new String[v3.size()])) : null);
            this.canCaptureLocals = (v2 && v1 != null && v1.length > this.frameSize);
            this.isAtReturn = (this.node.getCurrentTarget() instanceof InsnNode && this.isValueReturnOpcode(this.node.getCurrentTarget().getOpcode()));
            this.desc = a5.getCallbackDescriptor(this.localTypes, a5.arguments);
            this.descl = a5.getCallbackDescriptor(true, this.localTypes, a5.arguments, this.frameSize, this.extraArgs);
            this.invoke = a5.arguments.length + (this.canCaptureLocals ? (this.localTypes.length - this.frameSize) : 0);
        }
        
        private boolean isValueReturnOpcode(final int a1) {
            return a1 >= 172 && a1 < 177;
        }
        
        String getDescriptor() {
            return this.canCaptureLocals ? this.descl : this.desc;
        }
        
        String getDescriptorWithAllLocals() {
            return this.target.getCallbackDescriptor(true, this.localTypes, this.target.arguments, this.frameSize, 32767);
        }
        
        String getCallbackInfoConstructorDescriptor() {
            return this.isAtReturn ? CallbackInfo.getConstructorDescriptor(this.target.returnType) : CallbackInfo.getConstructorDescriptor();
        }
        
        void add(final AbstractInsnNode a1, final boolean a2, final boolean a3) {
            this.add(a1, a2, a3, false);
        }
        
        void add(final AbstractInsnNode a1, final boolean a2, final boolean a3, final boolean a4) {
            if (a4) {
                this.target.insns.insertBefore(this.head, a1);
            }
            else {
                this.add(a1);
            }
            this.ctor += (a2 ? 1 : 0);
            this.invoke += (a3 ? 1 : 0);
        }
        
        void inject() {
            this.target.insertBefore(this.node, this);
            this.target.addToStack(Math.max(this.invoke, this.ctor));
        }
        
        boolean checkDescriptor(final String v-2) {
            if (this.getDescriptor().equals(v-2)) {
                return true;
            }
            if (this.target.getSimpleCallbackDescriptor().equals(v-2) && !this.canCaptureLocals) {
                this.captureArgs = false;
                return true;
            }
            final Type[] argumentTypes = Type.getArgumentTypes(v-2);
            final Type[] v0 = Type.getArgumentTypes(this.descl);
            if (argumentTypes.length != v0.length) {
                return false;
            }
            for (int v2 = 0; v2 < v0.length; ++v2) {
                final Type a1 = argumentTypes[v2];
                if (!a1.equals(v0[v2])) {
                    if (a1.getSort() == 9) {
                        return false;
                    }
                    if (Annotations.getInvisibleParameter(this.handler, Coerce.class, v2) == null) {
                        return false;
                    }
                    if (!Injector.canCoerce(argumentTypes[v2], v0[v2])) {
                        return false;
                    }
                }
            }
            return true;
        }
        
        boolean captureArgs() {
            return this.captureArgs;
        }
        
        int marshalVar() {
            if (this.marshalVar < 0) {
                this.marshalVar = this.target.allocateLocal();
            }
            return this.marshalVar;
        }
    }
}
