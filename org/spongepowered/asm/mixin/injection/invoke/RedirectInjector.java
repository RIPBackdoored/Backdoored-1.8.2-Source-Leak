package org.spongepowered.asm.mixin.injection.invoke;

import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.points.*;
import java.util.*;
import org.spongepowered.asm.util.*;
import com.google.common.primitives.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.collect.*;

public class RedirectInjector extends InvokeInjector
{
    private static final String KEY_NOMINATORS = "nominators";
    private static final String KEY_FUZZ = "fuzz";
    private static final String KEY_OPCODE = "opcode";
    protected Meta meta;
    private Map<BeforeNew, ConstructorRedirectData> ctorRedirectors;
    
    public RedirectInjector(final InjectionInfo a1) {
        this(a1, "@Redirect");
    }
    
    protected RedirectInjector(final InjectionInfo a1, final String a2) {
        super(a1, a2);
        this.ctorRedirectors = new HashMap<BeforeNew, ConstructorRedirectData>();
        final int v1 = a1.getContext().getPriority();
        final boolean v2 = Annotations.getVisible(this.methodNode, Final.class) != null;
        this.meta = new Meta(v1, v2, this.info.toString(), this.methodNode.desc);
    }
    
    @Override
    protected void checkTarget(final Target a1) {
    }
    
    @Override
    protected void addTargetNode(final Target v1, final List<InjectionNodes.InjectionNode> v2, final AbstractInsnNode v3, final Set<InjectionPoint> v4) {
        final InjectionNodes.InjectionNode v5 = v1.getInjectionNode(v3);
        ConstructorRedirectData v6 = null;
        int v7 = 8;
        int v8 = 0;
        if (v5 != null) {
            final Meta a1 = v5.getDecoration("redirector");
            if (a1 != null && a1.getOwner() != this) {
                if (a1.priority >= this.meta.priority) {
                    Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, this.meta.priority, a1.name, a1.priority });
                    return;
                }
                if (a1.isFinal) {
                    throw new InvalidInjectionException(this.info, String.format("%s conflict: %s failed because target was already remapped by %s", this.annotationType, this, a1.name));
                }
            }
        }
        for (final InjectionPoint a2 : v4) {
            if (a2 instanceof BeforeNew) {
                v6 = this.getCtorRedirect((BeforeNew)a2);
                v6.wildcard = !((BeforeNew)a2).hasDescriptor();
            }
            else {
                if (!(a2 instanceof BeforeFieldAccess)) {
                    continue;
                }
                final BeforeFieldAccess a3 = (BeforeFieldAccess)a2;
                v7 = a3.getFuzzFactor();
                v8 = a3.getArrayOpcode();
            }
        }
        final InjectionNodes.InjectionNode v9 = v1.addInjectionNode(v3);
        v9.decorate("redirector", this.meta);
        v9.decorate("nominators", v4);
        if (v3 instanceof TypeInsnNode && v3.getOpcode() == 187) {
            v9.decorate("ctor", v6);
        }
        else {
            v9.decorate("fuzz", v7);
            v9.decorate("opcode", v8);
        }
        v2.add(v9);
    }
    
    private ConstructorRedirectData getCtorRedirect(final BeforeNew a1) {
        ConstructorRedirectData v1 = this.ctorRedirectors.get(a1);
        if (v1 == null) {
            v1 = new ConstructorRedirectData();
            this.ctorRedirectors.put(a1, v1);
        }
        return v1;
    }
    
    @Override
    protected void inject(final Target a1, final InjectionNodes.InjectionNode a2) {
        if (!this.preInject(a2)) {
            return;
        }
        if (a2.isReplaced()) {
            throw new UnsupportedOperationException("Redirector target failure for " + this.info);
        }
        if (a2.getCurrentTarget() instanceof MethodInsnNode) {
            this.checkTargetForNode(a1, a2);
            this.injectAtInvoke(a1, a2);
            return;
        }
        if (a2.getCurrentTarget() instanceof FieldInsnNode) {
            this.checkTargetForNode(a1, a2);
            this.injectAtFieldAccess(a1, a2);
            return;
        }
        if (!(a2.getCurrentTarget() instanceof TypeInsnNode) || a2.getCurrentTarget().getOpcode() != 187) {
            throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting an invalid insn in %s in %s", this.annotationType, a1, this));
        }
        if (!this.isStatic && a1.isStatic) {
            throw new InvalidInjectionException(this.info, String.format("non-static callback method %s has a static target which is not supported", this));
        }
        this.injectAtConstructor(a1, a2);
    }
    
    protected boolean preInject(final InjectionNodes.InjectionNode a1) {
        final Meta v1 = a1.getDecoration("redirector");
        if (v1.getOwner() != this) {
            Injector.logger.warn("{} conflict. Skipping {} with priority {}, already redirected by {} with priority {}", new Object[] { this.annotationType, this.info, this.meta.priority, v1.name, v1.priority });
            return false;
        }
        return true;
    }
    
    @Override
    protected void postInject(final Target v1, final InjectionNodes.InjectionNode v2) {
        super.postInject(v1, v2);
        if (v2.getOriginalTarget() instanceof TypeInsnNode && v2.getOriginalTarget().getOpcode() == 187) {
            final ConstructorRedirectData a1 = v2.getDecoration("ctor");
            if (a1.wildcard && a1.injected == 0) {
                throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", this.annotationType, v1));
            }
        }
    }
    
    @Override
    protected void injectAtInvoke(final Target v1, final InjectionNodes.InjectionNode v2) {
        final RedirectedInvoke v3 = new RedirectedInvoke(v1, (MethodInsnNode)v2.getCurrentTarget());
        this.validateParams(v3);
        final InsnList v4 = new InsnList();
        int v5 = Bytecode.getArgsSize(v3.locals) + 1;
        int v6 = 1;
        int[] v7 = this.storeArgs(v1, v3.locals, v4, 0);
        if (v3.captureTargetArgs) {
            final int a1 = Bytecode.getArgsSize(v1.arguments);
            v5 += a1;
            v6 += a1;
            v7 = Ints.concat(new int[][] { v7, v1.getArgIndices() });
        }
        final AbstractInsnNode v8 = this.invokeHandlerWithArgs(this.methodArgs, v4, v7);
        v1.replaceNode(v3.node, v8, v4);
        v1.addToLocals(v5);
        v1.addToStack(v6);
    }
    
    protected void validateParams(final RedirectedInvoke v-3) {
        int n = this.methodArgs.length;
        final String format = String.format("%s handler method %s", this.annotationType, this);
        if (!v-3.returnType.equals(this.returnType)) {
            throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Expected return type %s found %s", format, this.returnType, v-3.returnType));
        }
        for (int v0 = 0; v0 < n; ++v0) {
            Type v2 = null;
            if (v0 >= this.methodArgs.length) {
                throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Not enough arguments found for capture of target method args, expected %d but found %d", format, n, this.methodArgs.length));
            }
            final Type v3 = this.methodArgs[v0];
            if (v0 < v-3.locals.length) {
                v2 = v-3.locals[v0];
            }
            else {
                v-3.captureTargetArgs = true;
                n = Math.max(n, v-3.locals.length + v-3.target.arguments.length);
                final int a1 = v0 - v-3.locals.length;
                if (a1 >= v-3.target.arguments.length) {
                    throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected additional target argument with type %s at index %d", format, v3, v0));
                }
                v2 = v-3.target.arguments[a1];
            }
            final AnnotationNode v4 = Annotations.getInvisibleParameter(this.methodNode, Coerce.class, v0);
            if (v3.equals(v2)) {
                if (v4 != null && this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                    Injector.logger.warn("Redundant @Coerce on {} argument {}, {} is identical to {}", new Object[] { format, v0, v2, v3 });
                }
            }
            else {
                final boolean v5 = Injector.canCoerce(v3, v2);
                if (v4 == null) {
                    throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Found unexpected argument type %s at index %d, expected %s", format, v3, v0, v2));
                }
                if (!v5) {
                    throw new InvalidInjectionException(this.info, String.format("%s has an invalid signature. Cannot @Coerce argument type %s at index %d to %s", format, v2, v0, v3));
                }
            }
        }
    }
    
    private void injectAtFieldAccess(final Target v2, final InjectionNodes.InjectionNode v3) {
        final FieldInsnNode v4 = (FieldInsnNode)v3.getCurrentTarget();
        final int v5 = v4.getOpcode();
        final Type v6 = Type.getType("L" + v4.owner + ";");
        final Type v7 = Type.getType(v4.desc);
        final int v8 = (v7.getSort() == 9) ? v7.getDimensions() : 0;
        final int v9 = (this.returnType.getSort() == 9) ? this.returnType.getDimensions() : 0;
        if (v9 > v8) {
            throw new InvalidInjectionException(this.info, "Dimensionality of handler method is greater than target array on " + this);
        }
        if (v9 == 0 && v8 > 0) {
            final int a1 = v3.getDecoration("fuzz");
            final int a2 = v3.getDecoration("opcode");
            this.injectAtArrayField(v2, v4, v5, v6, v7, a1, a2);
        }
        else {
            this.injectAtScalarField(v2, v4, v5, v6, v7);
        }
    }
    
    private void injectAtArrayField(final Target a4, final FieldInsnNode a5, final int a6, final Type a7, final Type v1, final int v2, int v3) {
        final Type v4 = v1.getElementType();
        if (a6 != 178 && a6 != 180) {
            throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for array access %s", Bytecode.getOpcodeName(a6), this.info));
        }
        if (this.returnType.getSort() != 0) {
            if (v3 != 190) {
                v3 = v4.getOpcode(46);
            }
            final AbstractInsnNode a8 = BeforeFieldAccess.findArrayNode(a4.insns, a5, v3, v2);
            this.injectAtGetArray(a4, a5, a8, a7, v1);
        }
        else {
            final AbstractInsnNode a9 = BeforeFieldAccess.findArrayNode(a4.insns, a5, v4.getOpcode(79), v2);
            this.injectAtSetArray(a4, a5, a9, a7, v1);
        }
    }
    
    private void injectAtGetArray(final Target a1, final FieldInsnNode a2, final AbstractInsnNode a3, final Type a4, final Type a5) {
        final String v1 = getGetArrayHandlerDescriptor(a3, this.returnType, a5);
        final boolean v2 = this.checkDescriptor(v1, a1, "array getter");
        this.injectArrayRedirect(a1, a2, a3, v2, "array getter");
    }
    
    private void injectAtSetArray(final Target a1, final FieldInsnNode a2, final AbstractInsnNode a3, final Type a4, final Type a5) {
        final String v1 = Bytecode.generateDescriptor(null, (Object[])getArrayArgs(a5, 1, a5.getElementType()));
        final boolean v2 = this.checkDescriptor(v1, a1, "array setter");
        this.injectArrayRedirect(a1, a2, a3, v2, "array setter");
    }
    
    public void injectArrayRedirect(final Target a3, final FieldInsnNode a4, final AbstractInsnNode a5, final boolean v1, final String v2) {
        if (a5 == null) {
            final String a6 = "";
            throw new InvalidInjectionException(this.info, String.format("Array element %s on %s could not locate a matching %s instruction in %s. %s", this.annotationType, this, v2, a3, a6));
        }
        if (!this.isStatic) {
            a3.insns.insertBefore(a4, new VarInsnNode(25, 0));
            a3.addToStack(1);
        }
        final InsnList v3 = new InsnList();
        if (v1) {
            this.pushArgs(a3.arguments, v3, a3.getArgIndices(), 0, a3.arguments.length);
            a3.addToStack(Bytecode.getArgsSize(a3.arguments));
        }
        a3.replaceNode(a5, this.invokeHandler(v3), v3);
    }
    
    public void injectAtScalarField(final Target a1, final FieldInsnNode a2, final int a3, final Type a4, final Type a5) {
        AbstractInsnNode v1 = null;
        final InsnList v2 = new InsnList();
        if (a3 == 178 || a3 == 180) {
            v1 = this.injectAtGetField(v2, a1, a2, a3 == 178, a4, a5);
        }
        else {
            if (a3 != 179 && a3 != 181) {
                throw new InvalidInjectionException(this.info, String.format("Unspported opcode %s for %s", Bytecode.getOpcodeName(a3), this.info));
            }
            v1 = this.injectAtPutField(v2, a1, a2, a3 == 179, a4, a5);
        }
        a1.replaceNode(a2, v1, v2);
    }
    
    private AbstractInsnNode injectAtGetField(final InsnList a1, final Target a2, final FieldInsnNode a3, final boolean a4, final Type a5, final Type a6) {
        final String v1 = a4 ? Bytecode.generateDescriptor(a6, new Object[0]) : Bytecode.generateDescriptor(a6, a5);
        final boolean v2 = this.checkDescriptor(v1, a2, "getter");
        if (!this.isStatic) {
            a1.add(new VarInsnNode(25, 0));
            if (!a4) {
                a1.add(new InsnNode(95));
            }
        }
        if (v2) {
            this.pushArgs(a2.arguments, a1, a2.getArgIndices(), 0, a2.arguments.length);
            a2.addToStack(Bytecode.getArgsSize(a2.arguments));
        }
        a2.addToStack(this.isStatic ? 0 : 1);
        return this.invokeHandler(a1);
    }
    
    private AbstractInsnNode injectAtPutField(final InsnList a3, final Target a4, final FieldInsnNode a5, final boolean a6, final Type v1, final Type v2) {
        final String v3 = a6 ? Bytecode.generateDescriptor(null, v2) : Bytecode.generateDescriptor(null, v1, v2);
        final boolean v4 = this.checkDescriptor(v3, a4, "setter");
        if (!this.isStatic) {
            if (a6) {
                a3.add(new VarInsnNode(25, 0));
                a3.add(new InsnNode(95));
            }
            else {
                final int a7 = a4.allocateLocals(v2.getSize());
                a3.add(new VarInsnNode(v2.getOpcode(54), a7));
                a3.add(new VarInsnNode(25, 0));
                a3.add(new InsnNode(95));
                a3.add(new VarInsnNode(v2.getOpcode(21), a7));
            }
        }
        if (v4) {
            this.pushArgs(a4.arguments, a3, a4.getArgIndices(), 0, a4.arguments.length);
            a4.addToStack(Bytecode.getArgsSize(a4.arguments));
        }
        a4.addToStack((!this.isStatic && !a6) ? 1 : 0);
        return this.invokeHandler(a3);
    }
    
    protected boolean checkDescriptor(final String a1, final Target a2, final String a3) {
        if (this.methodNode.desc.equals(a1)) {
            return false;
        }
        final int v1 = a1.indexOf(41);
        final String v2 = String.format("%s%s%s", a1.substring(0, v1), Joiner.on("").join((Object[])a2.arguments), a1.substring(v1));
        if (this.methodNode.desc.equals(v2)) {
            return true;
        }
        throw new InvalidInjectionException(this.info, String.format("%s method %s %s has an invalid signature. Expected %s but found %s", this.annotationType, a3, this, a1, this.methodNode.desc));
    }
    
    protected void injectAtConstructor(final Target v2, final InjectionNodes.InjectionNode v3) {
        final ConstructorRedirectData v4 = v3.getDecoration("ctor");
        if (v4 == null) {
            throw new InvalidInjectionException(this.info, String.format("%s ctor redirector has no metadata, the injector failed a preprocessing phase", this.annotationType));
        }
        final TypeInsnNode v5 = (TypeInsnNode)v3.getCurrentTarget();
        final AbstractInsnNode v6 = v2.get(v2.indexOf(v5) + 1);
        final MethodInsnNode v7 = v2.findInitNodeFor(v5);
        if (v7 != null) {
            final boolean v8 = v6.getOpcode() == 89;
            final String v9 = v7.desc.replace(")V", ")L" + v5.desc + ";");
            boolean v10 = false;
            try {
                v10 = this.checkDescriptor(v9, v2, "constructor");
            }
            catch (InvalidInjectionException a1) {
                if (!v4.wildcard) {
                    throw a1;
                }
                return;
            }
            if (v8) {
                v2.removeNode(v6);
            }
            if (this.isStatic) {
                v2.removeNode(v5);
            }
            else {
                v2.replaceNode(v5, new VarInsnNode(25, 0));
            }
            final InsnList v11 = new InsnList();
            if (v10) {
                this.pushArgs(v2.arguments, v11, v2.getArgIndices(), 0, v2.arguments.length);
                v2.addToStack(Bytecode.getArgsSize(v2.arguments));
            }
            this.invokeHandler(v11);
            if (v8) {
                final LabelNode a2 = new LabelNode();
                v11.add(new InsnNode(89));
                v11.add(new JumpInsnNode(199, a2));
                this.throwException(v11, "java/lang/NullPointerException", String.format("%s constructor handler %s returned null for %s", this.annotationType, this, v5.desc.replace('/', '.')));
                v11.add(a2);
                v2.addToStack(1);
            }
            else {
                v11.add(new InsnNode(87));
            }
            v2.replaceNode(v7, v11);
            final ConstructorRedirectData constructorRedirectData = v4;
            ++constructorRedirectData.injected;
            return;
        }
        if (!v4.wildcard) {
            throw new InvalidInjectionException(this.info, String.format("%s ctor invocation was not found in %s", this.annotationType, v2));
        }
    }
    
    private static String getGetArrayHandlerDescriptor(final AbstractInsnNode a1, final Type a2, final Type a3) {
        if (a1 != null && a1.getOpcode() == 190) {
            return Bytecode.generateDescriptor(Type.INT_TYPE, (Object[])getArrayArgs(a3, 0, new Type[0]));
        }
        return Bytecode.generateDescriptor(a2, (Object[])getArrayArgs(a3, 1, new Type[0]));
    }
    
    private static Type[] getArrayArgs(final Type a2, final int a3, final Type... v1) {
        final int v2 = a2.getDimensions() + a3;
        final Type[] v3 = new Type[v2 + v1.length];
        for (int a4 = 0; a4 < v3.length; ++a4) {
            v3[a4] = ((a4 == 0) ? a2 : ((a4 < v2) ? Type.INT_TYPE : v1[v2 - a4]));
        }
        return v3;
    }
    
    class Meta
    {
        public static final String KEY = "redirector";
        final int priority;
        final boolean isFinal;
        final String name;
        final String desc;
        final /* synthetic */ RedirectInjector this$0;
        
        public Meta(final RedirectInjector a1, final int a2, final boolean a3, final String a4, final String a5) {
            this.this$0 = a1;
            super();
            this.priority = a2;
            this.isFinal = a3;
            this.name = a4;
            this.desc = a5;
        }
        
        RedirectInjector getOwner() {
            return this.this$0;
        }
    }
    
    static class ConstructorRedirectData
    {
        public static final String KEY = "ctor";
        public boolean wildcard;
        public int injected;
        
        ConstructorRedirectData() {
            super();
            this.wildcard = false;
            this.injected = 0;
        }
    }
    
    static class RedirectedInvoke
    {
        final Target target;
        final MethodInsnNode node;
        final Type returnType;
        final Type[] args;
        final Type[] locals;
        boolean captureTargetArgs;
        
        RedirectedInvoke(final Target a1, final MethodInsnNode a2) {
            super();
            this.captureTargetArgs = false;
            this.target = a1;
            this.node = a2;
            this.returnType = Type.getReturnType(a2.desc);
            this.args = Type.getArgumentTypes(a2.desc);
            this.locals = (Type[])((a2.getOpcode() == 184) ? this.args : ObjectArrays.concat((Object)Type.getType("L" + a2.owner + ";"), (Object[])this.args));
        }
    }
}
