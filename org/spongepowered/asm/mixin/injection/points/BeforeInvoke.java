package org.spongepowered.asm.mixin.injection.points;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

@AtCode("INVOKE")
public class BeforeInvoke extends InjectionPoint
{
    protected final MemberInfo target;
    protected final boolean allowPermissive;
    protected final int ordinal;
    protected final String className;
    protected final IMixinContext context;
    protected final Logger logger;
    private boolean log;
    
    public BeforeInvoke(final InjectionPointData a1) {
        super(a1);
        this.logger = LogManager.getLogger("mixin");
        this.log = false;
        this.target = a1.getTarget();
        this.ordinal = a1.getOrdinal();
        this.log = a1.get("log", false);
        this.className = this.getClassName();
        this.context = a1.getContext();
        this.allowPermissive = (this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP) && this.context.getOption(MixinEnvironment.Option.REFMAP_REMAP_ALLOW_PERMISSIVE) && !this.context.getReferenceMapper().isDefault());
    }
    
    private String getClassName() {
        final AtCode v1 = this.getClass().getAnnotation(AtCode.class);
        return String.format("@At(%s)", (v1 != null) ? v1.value() : this.getClass().getSimpleName().toUpperCase());
    }
    
    public BeforeInvoke setLogging(final boolean a1) {
        this.log = a1;
        return this;
    }
    
    @Override
    public boolean find(final String a1, final InsnList a2, final Collection<AbstractInsnNode> a3) {
        this.log("{} is searching for an injection point in method with descriptor {}", this.className, a1);
        if (!this.find(a1, a2, a3, this.target, SearchType.STRICT) && this.target.desc != null && this.allowPermissive) {
            this.logger.warn("STRICT match for {} using \"{}\" in {} returned 0 results, attempting permissive search. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, this.target, this.context });
            return this.find(a1, a2, a3, this.target, SearchType.PERMISSIVE);
        }
        return true;
    }
    
    protected boolean find(final String a4, final InsnList a5, final Collection<AbstractInsnNode> v1, final MemberInfo v2, final SearchType v3) {
        if (v2 == null) {
            return false;
        }
        final MemberInfo v4 = (v3 == SearchType.PERMISSIVE) ? v2.transform(null) : v2;
        int v5 = 0;
        int v6 = 0;
        for (final AbstractInsnNode a6 : a5) {
            if (this.matchesInsn(a6)) {
                final MemberInfo a7 = new MemberInfo(a6);
                this.log("{} is considering insn {}", this.className, a7);
                if (v4.matches(a7.owner, a7.name, a7.desc)) {
                    this.log("{} > found a matching insn, checking preconditions...", this.className);
                    if (this.matchesInsn(a7, v5)) {
                        this.log("{} > > > found a matching insn at ordinal {}", this.className, v5);
                        if (this.addInsn(a5, v1, a6)) {
                            ++v6;
                        }
                        if (this.ordinal == v5) {
                            break;
                        }
                    }
                    ++v5;
                }
            }
            this.inspectInsn(a4, a5, a6);
        }
        if (v3 == SearchType.PERMISSIVE && v6 > 1) {
            this.logger.warn("A permissive match for {} using \"{}\" in {} matched {} instructions, this may cause unexpected behaviour. To inhibit permissive search set mixin.env.allowPermissiveMatch=false", new Object[] { this.className, v2, this.context, v6 });
        }
        return v6 > 0;
    }
    
    protected boolean addInsn(final InsnList a1, final Collection<AbstractInsnNode> a2, final AbstractInsnNode a3) {
        a2.add(a3);
        return true;
    }
    
    protected boolean matchesInsn(final AbstractInsnNode a1) {
        return a1 instanceof MethodInsnNode;
    }
    
    protected void inspectInsn(final String a1, final InsnList a2, final AbstractInsnNode a3) {
    }
    
    protected boolean matchesInsn(final MemberInfo a1, final int a2) {
        this.log("{} > > comparing target ordinal {} with current ordinal {}", this.className, this.ordinal, a2);
        return this.ordinal == -1 || this.ordinal == a2;
    }
    
    protected void log(final String a1, final Object... a2) {
        if (this.log) {
            this.logger.info(a1, a2);
        }
    }
    
    public enum SearchType
    {
        STRICT, 
        PERMISSIVE;
        
        private static final /* synthetic */ SearchType[] $VALUES;
        
        public static SearchType[] values() {
            return SearchType.$VALUES.clone();
        }
        
        public static SearchType valueOf(final String a1) {
            return Enum.valueOf(SearchType.class, a1);
        }
        
        static {
            $VALUES = new SearchType[] { SearchType.STRICT, SearchType.PERMISSIVE };
        }
    }
}
