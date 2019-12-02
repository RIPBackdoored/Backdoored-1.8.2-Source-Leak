package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.refmap.*;

public class ModifyVariableInjector extends Injector
{
    private final LocalVariableDiscriminator discriminator;
    
    public ModifyVariableInjector(final InjectionInfo a1, final LocalVariableDiscriminator a2) {
        super(a1);
        this.discriminator = a2;
    }
    
    @Override
    protected boolean findTargetNodes(final MethodNode a3, final InjectionPoint a4, final InsnList v1, final Collection<AbstractInsnNode> v2) {
        if (a4 instanceof ContextualInjectionPoint) {
            final Target a5 = this.info.getContext().getTargetMethod(a3);
            return ((ContextualInjectionPoint)a4).find(a5, v2);
        }
        return a4.find(a3.desc, v1, v2);
    }
    
    @Override
    protected void sanityCheck(final Target a1, final List<InjectionPoint> a2) {
        super.sanityCheck(a1, a2);
        if (a1.isStatic != this.isStatic) {
            throw new InvalidInjectionException(this.info, "'static' of variable modifier method does not match target in " + this);
        }
        final int v1 = this.discriminator.getOrdinal();
        if (v1 < -1) {
            throw new InvalidInjectionException(this.info, "Invalid ordinal " + v1 + " specified in " + this);
        }
        if (this.discriminator.getIndex() == 0 && !this.isStatic) {
            throw new InvalidInjectionException(this.info, "Invalid index 0 specified in non-static variable modifier " + this);
        }
    }
    
    @Override
    protected void inject(final Target v2, final InjectionNodes.InjectionNode v3) {
        if (v3.isReplaced()) {
            throw new InvalidInjectionException(this.info, "Variable modifier target for " + this + " was removed by another injector");
        }
        final Context v4 = new Context(this.returnType, this.discriminator.isArgsOnly(), v2, v3.getCurrentTarget());
        if (this.discriminator.printLVT()) {
            this.printLocals(v4);
        }
        final String v5 = Bytecode.getDescriptor(new Type[] { this.returnType }, this.returnType);
        if (!v5.equals(this.methodNode.desc)) {
            throw new InvalidInjectionException(this.info, "Variable modifier " + this + " has an invalid signature, expected " + v5 + " but found " + this.methodNode.desc);
        }
        try {
            final int a1 = this.discriminator.findLocal(v4);
            if (a1 > -1) {
                this.inject(v4, a1);
            }
        }
        catch (InvalidImplicitDiscriminatorException a2) {
            if (this.discriminator.printLVT()) {
                this.info.addCallbackInvocation(this.methodNode);
                return;
            }
            throw new InvalidInjectionException(this.info, "Implicit variable modifier injection failed in " + this, a2);
        }
        v2.insns.insertBefore(v4.node, v4.insns);
        v2.addToStack(this.isStatic ? 1 : 2);
    }
    
    private void printLocals(final Context a1) {
        final SignaturePrinter v1 = new SignaturePrinter(this.methodNode.name, this.returnType, this.methodArgs, new String[] { "var" });
        v1.setModifiers(this.methodNode);
        new PrettyPrinter().kvWidth(20).kv("Target Class", (Object)this.classNode.name.replace('/', '.')).kv("Target Method", (Object)a1.target.method.name).kv("Callback Name", (Object)this.methodNode.name).kv("Capture Type", (Object)SignaturePrinter.getTypeName(this.returnType, false)).kv("Instruction", "%s %s", a1.node.getClass().getSimpleName(), Bytecode.getOpcodeName(a1.node.getOpcode())).hr().kv("Match mode", (Object)(this.discriminator.isImplicit(a1) ? "IMPLICIT (match single)" : "EXPLICIT (match by criteria)")).kv("Match ordinal", (this.discriminator.getOrdinal() < 0) ? "any" : Integer.valueOf(this.discriminator.getOrdinal())).kv("Match index", (this.discriminator.getIndex() < a1.baseArgIndex) ? "any" : Integer.valueOf(this.discriminator.getIndex())).kv("Match name(s)", this.discriminator.hasNames() ? this.discriminator.getNames() : "any").kv("Args only", this.discriminator.isArgsOnly()).hr().add(a1).print(System.err);
    }
    
    private void inject(final Context a1, final int a2) {
        if (!this.isStatic) {
            a1.insns.add(new VarInsnNode(25, 0));
        }
        a1.insns.add(new VarInsnNode(this.returnType.getOpcode(21), a2));
        this.invokeHandler(a1.insns);
        a1.insns.add(new VarInsnNode(this.returnType.getOpcode(54), a2));
    }
    
    static class Context extends LocalVariableDiscriminator.Context
    {
        final InsnList insns;
        
        public Context(final Type a1, final boolean a2, final Target a3, final AbstractInsnNode a4) {
            super(a1, a2, a3, a4);
            this.insns = new InsnList();
        }
    }
    
    abstract static class ContextualInjectionPoint extends InjectionPoint
    {
        protected final IMixinContext context;
        
        ContextualInjectionPoint(final IMixinContext a1) {
            super();
            this.context = a1;
        }
        
        @Override
        public boolean find(final String a1, final InsnList a2, final Collection<AbstractInsnNode> a3) {
            throw new InvalidInjectionException(this.context, this.getAtCode() + " injection point must be used in conjunction with @ModifyVariable");
        }
        
        abstract boolean find(final Target p0, final Collection<AbstractInsnNode> p1);
    }
}
