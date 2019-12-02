package org.spongepowered.asm.mixin.injection.code;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.apache.logging.log4j.*;
import java.util.*;

public abstract class Injector
{
    protected static final Logger logger;
    protected InjectionInfo info;
    protected final ClassNode classNode;
    protected final MethodNode methodNode;
    protected final Type[] methodArgs;
    protected final Type returnType;
    protected final boolean isStatic;
    
    public Injector(final InjectionInfo a1) {
        this(a1.getClassNode(), a1.getMethod());
        this.info = a1;
    }
    
    private Injector(final ClassNode a1, final MethodNode a2) {
        super();
        this.classNode = a1;
        this.methodNode = a2;
        this.methodArgs = Type.getArgumentTypes(this.methodNode.desc);
        this.returnType = Type.getReturnType(this.methodNode.desc);
        this.isStatic = Bytecode.methodIsStatic(this.methodNode);
    }
    
    @Override
    public String toString() {
        return String.format("%s::%s", this.classNode.name, this.methodNode.name);
    }
    
    public final List<InjectionNodes.InjectionNode> find(final InjectorTarget v1, final List<InjectionPoint> v2) {
        this.sanityCheck(v1.getTarget(), v2);
        final List<InjectionNodes.InjectionNode> v3 = new ArrayList<InjectionNodes.InjectionNode>();
        for (final TargetNode a1 : this.findTargetNodes(v1, v2)) {
            this.addTargetNode(v1.getTarget(), v3, a1.insn, a1.nominators);
        }
        return v3;
    }
    
    protected void addTargetNode(final Target a1, final List<InjectionNodes.InjectionNode> a2, final AbstractInsnNode a3, final Set<InjectionPoint> a4) {
        a2.add(a1.addInjectionNode(a3));
    }
    
    public final void inject(final Target v2, final List<InjectionNodes.InjectionNode> v3) {
        for (final InjectionNodes.InjectionNode a1 : v3) {
            if (a1.isRemoved()) {
                if (!this.info.getContext().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                    continue;
                }
                Injector.logger.warn("Target node for {} was removed by a previous injector in {}", new Object[] { this.info, v2 });
            }
            else {
                this.inject(v2, a1);
            }
        }
        for (final InjectionNodes.InjectionNode a2 : v3) {
            this.postInject(v2, a2);
        }
    }
    
    private Collection<TargetNode> findTargetNodes(final InjectorTarget v-8, final List<InjectionPoint> v-7) {
        final IMixinContext context = this.info.getContext();
        final MethodNode method = v-8.getMethod();
        final Map<Integer, TargetNode> map = new TreeMap<Integer, TargetNode>();
        final Collection<AbstractInsnNode> a3 = new ArrayList<AbstractInsnNode>(32);
        for (final InjectionPoint injectionPoint : v-7) {
            a3.clear();
            if (v-8.isMerged() && !context.getClassName().equals(v-8.getMergedBy()) && !injectionPoint.checkPriority(v-8.getMergedPriority(), context.getPriority())) {
                throw new InvalidInjectionException(this.info, String.format("%s on %s with priority %d cannot inject into %s merged by %s with priority %d", injectionPoint, this, context.getPriority(), v-8, v-8.getMergedBy(), v-8.getMergedPriority()));
            }
            if (!this.findTargetNodes(method, injectionPoint, v-8.getSlice(injectionPoint), a3)) {
                continue;
            }
            for (final AbstractInsnNode v1 : a3) {
                final Integer a1 = method.instructions.indexOf(v1);
                TargetNode a2 = map.get(a1);
                if (a2 == null) {
                    a2 = new TargetNode(v1);
                    map.put(a1, a2);
                }
                a2.nominators.add(injectionPoint);
            }
        }
        return map.values();
    }
    
    protected boolean findTargetNodes(final MethodNode a1, final InjectionPoint a2, final InsnList a3, final Collection<AbstractInsnNode> a4) {
        return a2.find(a1.desc, a3, a4);
    }
    
    protected void sanityCheck(final Target a1, final List<InjectionPoint> a2) {
        if (a1.classNode != this.classNode) {
            throw new InvalidInjectionException(this.info, "Target class does not match injector class in " + this);
        }
    }
    
    protected abstract void inject(final Target p0, final InjectionNodes.InjectionNode p1);
    
    protected void postInject(final Target a1, final InjectionNodes.InjectionNode a2) {
    }
    
    protected AbstractInsnNode invokeHandler(final InsnList a1) {
        return this.invokeHandler(a1, this.methodNode);
    }
    
    protected AbstractInsnNode invokeHandler(final InsnList a1, final MethodNode a2) {
        final boolean v1 = (a2.access & 0x2) != 0x0;
        final int v2 = this.isStatic ? 184 : (v1 ? 183 : 182);
        final MethodInsnNode v3 = new MethodInsnNode(v2, this.classNode.name, a2.name, a2.desc, false);
        a1.add(v3);
        this.info.addCallbackInvocation(a2);
        return v3;
    }
    
    protected void throwException(final InsnList a1, final String a2, final String a3) {
        a1.add(new TypeInsnNode(187, a2));
        a1.add(new InsnNode(89));
        a1.add(new LdcInsnNode(a3));
        a1.add(new MethodInsnNode(183, a2, "<init>", "(Ljava/lang/String;)V", false));
        a1.add(new InsnNode(191));
    }
    
    public static boolean canCoerce(final Type a1, final Type a2) {
        if (a1.getSort() == 10 && a2.getSort() == 10) {
            return canCoerce(ClassInfo.forType(a1), ClassInfo.forType(a2));
        }
        return canCoerce(a1.getDescriptor(), a2.getDescriptor());
    }
    
    public static boolean canCoerce(final String a1, final String a2) {
        return a1.length() <= 1 && a2.length() <= 1 && canCoerce(a1.charAt(0), a2.charAt(0));
    }
    
    public static boolean canCoerce(final char a1, final char a2) {
        return a2 == 'I' && "IBSCZ".indexOf(a1) > -1;
    }
    
    private static boolean canCoerce(final ClassInfo a1, final ClassInfo a2) {
        return a1 != null && a2 != null && (a2 == a1 || a2.hasSuperClass(a1));
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    public static final class TargetNode
    {
        final AbstractInsnNode insn;
        final Set<InjectionPoint> nominators;
        
        TargetNode(final AbstractInsnNode a1) {
            super();
            this.nominators = new HashSet<InjectionPoint>();
            this.insn = a1;
        }
        
        public AbstractInsnNode getNode() {
            return this.insn;
        }
        
        public Set<InjectionPoint> getNominators() {
            return Collections.unmodifiableSet((Set<? extends InjectionPoint>)this.nominators);
        }
        
        @Override
        public boolean equals(final Object a1) {
            return a1 != null && a1.getClass() == TargetNode.class && ((TargetNode)a1).insn == this.insn;
        }
        
        @Override
        public int hashCode() {
            return this.insn.hashCode();
        }
    }
}
