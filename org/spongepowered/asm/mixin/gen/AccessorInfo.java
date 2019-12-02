package org.spongepowered.asm.mixin.gen;

import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.gen.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.*;
import org.apache.logging.log4j.*;
import java.util.regex.*;
import org.spongepowered.asm.util.*;
import java.util.*;
import com.google.common.collect.*;

public class AccessorInfo extends SpecialMethodInfo
{
    protected static final Pattern PATTERN_ACCESSOR;
    protected final Type[] argTypes;
    protected final Type returnType;
    protected final AccessorType type;
    private final Type targetFieldType;
    protected final MemberInfo target;
    protected FieldNode targetField;
    protected MethodNode targetMethod;
    
    public AccessorInfo(final MixinTargetContext a1, final MethodNode a2) {
        this(a1, a2, Accessor.class);
    }
    
    protected AccessorInfo(final MixinTargetContext a1, final MethodNode a2, final Class<? extends Annotation> a3) {
        super(a1, a2, Annotations.getVisible(a2, a3));
        this.argTypes = Type.getArgumentTypes(a2.desc);
        this.returnType = Type.getReturnType(a2.desc);
        this.type = this.initType();
        this.targetFieldType = this.initTargetFieldType();
        this.target = this.initTarget();
    }
    
    protected AccessorType initType() {
        if (this.returnType.equals(Type.VOID_TYPE)) {
            return AccessorType.FIELD_SETTER;
        }
        return AccessorType.FIELD_GETTER;
    }
    
    protected Type initTargetFieldType() {
        switch (this.type) {
            case FIELD_GETTER: {
                if (this.argTypes.length > 0) {
                    throw new InvalidAccessorException(this.mixin, this + " must take exactly 0 arguments, found " + this.argTypes.length);
                }
                return this.returnType;
            }
            case FIELD_SETTER: {
                if (this.argTypes.length != 1) {
                    throw new InvalidAccessorException(this.mixin, this + " must take exactly 1 argument, found " + this.argTypes.length);
                }
                return this.argTypes[0];
            }
            default: {
                throw new InvalidAccessorException(this.mixin, "Computed unsupported accessor type " + this.type + " for " + this);
            }
        }
    }
    
    protected MemberInfo initTarget() {
        final MemberInfo v1 = new MemberInfo(this.getTargetName(), null, this.targetFieldType.getDescriptor());
        this.annotation.visit("target", v1.toString());
        return v1;
    }
    
    protected String getTargetName() {
        final String v0 = Annotations.getValue(this.annotation);
        if (!Strings.isNullOrEmpty(v0)) {
            return MemberInfo.parse(v0, this.mixin).name;
        }
        final String v2 = this.inflectTarget();
        if (v2 == null) {
            throw new InvalidAccessorException(this.mixin, "Failed to inflect target name for " + this + ", supported prefixes: [get, set, is]");
        }
        return v2;
    }
    
    protected String inflectTarget() {
        return inflectTarget(this.method.name, this.type, this.toString(), this.mixin, this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE));
    }
    
    public static String inflectTarget(final String a5, final AccessorType v1, final String v2, final IMixinContext v3, final boolean v4) {
        final Matcher v5 = AccessorInfo.PATTERN_ACCESSOR.matcher(a5);
        if (v5.matches()) {
            final String a6 = v5.group(1);
            final String a7 = v5.group(3);
            final String a8 = v5.group(4);
            final String a9 = String.format("%s%s", toLowerCase(a7, !isUpperCase(a8)), a8);
            if (!v1.isExpectedPrefix(a6) && v4) {
                LogManager.getLogger("mixin").warn("Unexpected prefix for {}, found [{}] expecting {}", new Object[] { v2, a6, v1.getExpectedPrefixes() });
            }
            return MemberInfo.parse(a9, v3).name;
        }
        return null;
    }
    
    public final MemberInfo getTarget() {
        return this.target;
    }
    
    public final Type getTargetFieldType() {
        return this.targetFieldType;
    }
    
    public final FieldNode getTargetField() {
        return this.targetField;
    }
    
    public final MethodNode getTargetMethod() {
        return this.targetMethod;
    }
    
    public final Type getReturnType() {
        return this.returnType;
    }
    
    public final Type[] getArgTypes() {
        return this.argTypes;
    }
    
    @Override
    public String toString() {
        return String.format("%s->@%s[%s]::%s%s", this.mixin.toString(), Bytecode.getSimpleName(this.annotation), this.type.toString(), this.method.name, this.method.desc);
    }
    
    public void locate() {
        this.targetField = this.findTargetField();
    }
    
    public MethodNode generate() {
        final MethodNode v1 = this.type.getGenerator(this).generate();
        Bytecode.mergeAnnotations(this.method, v1);
        return v1;
    }
    
    private FieldNode findTargetField() {
        return this.findTarget(this.classNode.fields);
    }
    
    protected <TNode> TNode findTarget(final List<TNode> v-5) {
        TNode tNode = null;
        final List<TNode> list = new ArrayList<TNode>();
        for (final TNode next : v-5) {
            final String a1 = getNodeDesc(next);
            if (a1 != null) {
                if (!a1.equals(this.target.desc)) {
                    continue;
                }
                final String v1 = getNodeName(next);
                if (v1 == null) {
                    continue;
                }
                if (v1.equals(this.target.name)) {
                    tNode = next;
                }
                if (!v1.equalsIgnoreCase(this.target.name)) {
                    continue;
                }
                list.add(next);
            }
        }
        if (tNode != null) {
            if (list.size() > 1) {
                LogManager.getLogger("mixin").debug("{} found an exact match for {} but other candidates were found!", new Object[] { this, this.target });
            }
            return tNode;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        final String s = (list.size() == 0) ? "No" : "Multiple";
        throw new InvalidAccessorException(this, s + " candidates were found matching " + this.target + " in " + this.classNode.name + " for " + this);
    }
    
    private static <TNode> String getNodeDesc(final TNode a1) {
        return (a1 instanceof MethodNode) ? ((MethodNode)a1).desc : ((a1 instanceof FieldNode) ? ((FieldNode)a1).desc : null);
    }
    
    private static <TNode> String getNodeName(final TNode a1) {
        return (a1 instanceof MethodNode) ? ((MethodNode)a1).name : ((a1 instanceof FieldNode) ? ((FieldNode)a1).name : null);
    }
    
    public static AccessorInfo of(final MixinTargetContext a1, final MethodNode a2, final Class<? extends Annotation> a3) {
        if (a3 == Accessor.class) {
            return new AccessorInfo(a1, a2);
        }
        if (a3 == Invoker.class) {
            return new InvokerInfo(a1, a2);
        }
        throw new InvalidAccessorException(a1, "Could not parse accessor for unknown type " + a3.getName());
    }
    
    private static String toLowerCase(final String a1, final boolean a2) {
        return a2 ? a1.toLowerCase() : a1;
    }
    
    private static boolean isUpperCase(final String a1) {
        return a1.toUpperCase().equals(a1);
    }
    
    static {
        PATTERN_ACCESSOR = Pattern.compile("^(get|set|is|invoke|call)(([A-Z])(.*?))(_\\$md.*)?$");
    }
    
    public enum AccessorType
    {
        FIELD_GETTER((Set)ImmutableSet.of((Object)"get", (Object)"is")) {
            AccessorInfo$AccessorType$1(final String a2, final int a3, final Set a1) {
            }
            
            @Override
            AccessorGenerator getGenerator(final AccessorInfo a1) {
                return new AccessorGeneratorFieldGetter(a1);
            }
        }, 
        FIELD_SETTER((Set)ImmutableSet.of("set")) {
            AccessorInfo$AccessorType$2(final String a2, final int a3, final Set a1) {
            }
            
            @Override
            AccessorGenerator getGenerator(final AccessorInfo a1) {
                return new AccessorGeneratorFieldSetter(a1);
            }
        }, 
        METHOD_PROXY((Set)ImmutableSet.of((Object)"call", (Object)"invoke")) {
            AccessorInfo$AccessorType$3(final String a2, final int a3, final Set a1) {
            }
            
            @Override
            AccessorGenerator getGenerator(final AccessorInfo a1) {
                return new AccessorGeneratorMethodProxy(a1);
            }
        };
        
        private final Set<String> expectedPrefixes;
        private static final /* synthetic */ AccessorType[] $VALUES;
        
        public static AccessorType[] values() {
            return AccessorType.$VALUES.clone();
        }
        
        public static AccessorType valueOf(final String a1) {
            return Enum.valueOf(AccessorType.class, a1);
        }
        
        private AccessorType(final Set<String> a1) {
            this.expectedPrefixes = a1;
        }
        
        public boolean isExpectedPrefix(final String a1) {
            return this.expectedPrefixes.contains(a1);
        }
        
        public String getExpectedPrefixes() {
            return this.expectedPrefixes.toString();
        }
        
        abstract AccessorGenerator getGenerator(final AccessorInfo p0);
        
        AccessorType(final String a1, final int a2, final Set a3, final AccessorInfo$1 a4) {
            this(a3);
        }
        
        static {
            $VALUES = new AccessorType[] { AccessorType.FIELD_GETTER, AccessorType.FIELD_SETTER, AccessorType.METHOD_PROXY };
        }
    }
}
