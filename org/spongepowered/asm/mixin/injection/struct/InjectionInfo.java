package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.struct.*;
import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.refmap.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

public abstract class InjectionInfo extends SpecialMethodInfo implements ISliceContext
{
    protected final boolean isStatic;
    protected final Deque<MethodNode> targets;
    protected final MethodSlices slices;
    protected final String atKey;
    protected final List<InjectionPoint> injectionPoints;
    protected final Map<Target, List<InjectionNodes.InjectionNode>> targetNodes;
    protected Injector injector;
    protected InjectorGroupInfo group;
    private final List<MethodNode> injectedMethods;
    private int expectedCallbackCount;
    private int requiredCallbackCount;
    private int maxCallbackCount;
    private int injectedCallbackCount;
    
    protected InjectionInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3) {
        this(a1, a2, a3, "at");
    }
    
    protected InjectionInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3, final String a4) {
        super(a1, a2, a3);
        this.targets = new ArrayDeque<MethodNode>();
        this.injectionPoints = new ArrayList<InjectionPoint>();
        this.targetNodes = new LinkedHashMap<Target, List<InjectionNodes.InjectionNode>>();
        this.injectedMethods = new ArrayList<MethodNode>(0);
        this.expectedCallbackCount = 1;
        this.requiredCallbackCount = 0;
        this.maxCallbackCount = 0;
        this.injectedCallbackCount = 0;
        this.isStatic = Bytecode.methodIsStatic(a2);
        this.slices = MethodSlices.parse(this);
        this.atKey = a4;
        this.readAnnotation();
    }
    
    protected void readAnnotation() {
        if (this.annotation == null) {
            return;
        }
        final String v1 = "@" + Bytecode.getSimpleName(this.annotation);
        final List<AnnotationNode> v2 = this.readInjectionPoints(v1);
        this.findMethods(this.parseTargets(v1), v1);
        this.parseInjectionPoints(v2);
        this.parseRequirements();
        this.injector = this.parseInjector(this.annotation);
    }
    
    protected Set<MemberInfo> parseTargets(final String v-4) {
        final List<String> value = Annotations.getValue(this.annotation, "method", false);
        if (value == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing method name", v-4, this.method.name));
        }
        final Set<MemberInfo> set = new LinkedHashSet<MemberInfo>();
        for (final String v0 : value) {
            try {
                final MemberInfo a1 = MemberInfo.parseAndValidate(v0, this.mixin);
                if (a1.owner != null && !a1.owner.equals(this.mixin.getTargetClassRef())) {
                    throw new InvalidInjectionException(this, String.format("%s annotation on %s specifies a target class '%s', which is not supported", v-4, this.method.name, a1.owner));
                }
                set.add(a1);
            }
            catch (InvalidMemberDescriptorException v2) {
                throw new InvalidInjectionException(this, String.format("%s annotation on %s, has invalid target descriptor: \"%s\". %s", v-4, this.method.name, v0, this.mixin.getReferenceMapper().getStatus()));
            }
        }
        return set;
    }
    
    protected List<AnnotationNode> readInjectionPoints(final String a1) {
        final List<AnnotationNode> v1 = Annotations.getValue(this.annotation, this.atKey, false);
        if (v1 == null) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s is missing '%s' value(s)", a1, this.method.name, this.atKey));
        }
        return v1;
    }
    
    protected void parseInjectionPoints(final List<AnnotationNode> a1) {
        this.injectionPoints.addAll(InjectionPoint.parse(this.mixin, this.method, this.annotation, a1));
    }
    
    protected void parseRequirements() {
        this.group = this.mixin.getInjectorGroups().parseGroup(this.method, this.mixin.getDefaultInjectorGroup()).add(this);
        final Integer v1 = Annotations.getValue(this.annotation, "expect");
        if (v1 != null) {
            this.expectedCallbackCount = v1;
        }
        final Integer v2 = Annotations.getValue(this.annotation, "require");
        if (v2 != null && v2 > -1) {
            this.requiredCallbackCount = v2;
        }
        else if (this.group.isDefault()) {
            this.requiredCallbackCount = this.mixin.getDefaultRequiredInjections();
        }
        final Integer v3 = Annotations.getValue(this.annotation, "allow");
        if (v3 != null) {
            this.maxCallbackCount = Math.max(Math.max(this.requiredCallbackCount, 1), v3);
        }
    }
    
    protected abstract Injector parseInjector(final AnnotationNode p0);
    
    public boolean isValid() {
        return this.targets.size() > 0 && this.injectionPoints.size() > 0;
    }
    
    public void prepare() {
        this.targetNodes.clear();
        for (final MethodNode v0 : this.targets) {
            final Target v2 = this.mixin.getTargetMethod(v0);
            final InjectorTarget v3 = new InjectorTarget(this, v2);
            this.targetNodes.put(v2, this.injector.find(v3, this.injectionPoints));
            v3.dispose();
        }
    }
    
    public void inject() {
        for (final Map.Entry<Target, List<InjectionNodes.InjectionNode>> v1 : this.targetNodes.entrySet()) {
            this.injector.inject(v1.getKey(), v1.getValue());
        }
        this.targets.clear();
    }
    
    public void postInject() {
        for (final MethodNode v1 : this.injectedMethods) {
            this.classNode.methods.add(v1);
        }
        final String v2 = this.getDescription();
        final String v3 = this.mixin.getReferenceMapper().getStatus();
        final String v4 = this.getDynamicInfo();
        if (this.mixin.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_INJECTORS) && this.injectedCallbackCount < this.expectedCallbackCount) {
            throw new InvalidInjectionException(this, String.format("Injection validation failed: %s %s%s in %s expected %d invocation(s) but %d succeeded. %s%s", v2, this.method.name, this.method.desc, this.mixin, this.expectedCallbackCount, this.injectedCallbackCount, v3, v4));
        }
        if (this.injectedCallbackCount < this.requiredCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, (%d/%d) succeeded. %s%s", v2, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.requiredCallbackCount, v3, v4));
        }
        if (this.injectedCallbackCount > this.maxCallbackCount) {
            throw new InjectionError(String.format("Critical injection failure: %s %s%s in %s failed injection check, %d succeeded of %d allowed.%s", v2, this.method.name, this.method.desc, this.mixin, this.injectedCallbackCount, this.maxCallbackCount, v4));
        }
    }
    
    public void notifyInjected(final Target a1) {
    }
    
    protected String getDescription() {
        return "Callback method";
    }
    
    @Override
    public String toString() {
        return describeInjector(this.mixin, this.annotation, this.method);
    }
    
    public Collection<MethodNode> getTargets() {
        return this.targets;
    }
    
    @Override
    public MethodSlice getSlice(final String a1) {
        return this.slices.get(this.getSliceId(a1));
    }
    
    public String getSliceId(final String a1) {
        return "";
    }
    
    public int getInjectedCallbackCount() {
        return this.injectedCallbackCount;
    }
    
    public MethodNode addMethod(final int a1, final String a2, final String a3) {
        final MethodNode v1 = new MethodNode(327680, a1 | 0x1000, a2, a3, null, null);
        this.injectedMethods.add(v1);
        return v1;
    }
    
    public void addCallbackInvocation(final MethodNode a1) {
        ++this.injectedCallbackCount;
    }
    
    private void findMethods(final Set<MemberInfo> v-6, final String v-5) {
        this.targets.clear();
        final int n = this.mixin.getEnvironment().getOption(MixinEnvironment.Option.REFMAP_REMAP) ? 2 : 1;
        for (MemberInfo transform : v-6) {
            for (int n2 = 0, v0 = 0; v0 < n && n2 < 1; ++v0) {
                int v2 = 0;
                for (final MethodNode a2 : this.classNode.methods) {
                    if (transform.matches(a2.name, a2.desc, v2)) {
                        final boolean a3 = Annotations.getVisible(a2, MixinMerged.class) != null;
                        if (transform.matchAll) {
                            if (Bytecode.methodIsStatic(a2) != this.isStatic || a2 == this.method) {
                                continue;
                            }
                            if (a3) {
                                continue;
                            }
                        }
                        this.checkTarget(a2);
                        this.targets.add(a2);
                        ++v2;
                        ++n2;
                    }
                }
                transform = transform.transform(null);
            }
        }
        if (this.targets.size() == 0) {
            throw new InvalidInjectionException(this, String.format("%s annotation on %s could not find any targets matching %s in the target class %s. %s%s", v-5, this.method.name, namesOf(v-6), this.mixin.getTarget(), this.mixin.getReferenceMapper().getStatus(), this.getDynamicInfo()));
        }
    }
    
    private void checkTarget(final MethodNode a1) {
        final AnnotationNode v1 = Annotations.getVisible(a1, MixinMerged.class);
        if (v1 == null) {
            return;
        }
        if (Annotations.getVisible(a1, Final.class) != null) {
            throw new InvalidInjectionException(this, String.format("%s cannot inject into @Final method %s::%s%s merged by %s", this, this.classNode.name, a1.name, a1.desc, Annotations.getValue(v1, "mixin")));
        }
    }
    
    protected String getDynamicInfo() {
        final AnnotationNode v1 = Annotations.getInvisible(this.method, Dynamic.class);
        String v2 = Strings.nullToEmpty((String)Annotations.getValue(v1));
        final Type v3 = Annotations.getValue(v1, "mixin");
        if (v3 != null) {
            v2 = String.format("{%s} %s", v3.getClassName(), v2).trim();
        }
        return (v2.length() > 0) ? String.format(" Method is @Dynamic(%s)", v2) : "";
    }
    
    public static InjectionInfo parse(final MixinTargetContext a1, final MethodNode a2) {
        final AnnotationNode v1 = getInjectorAnnotation(a1.getMixin(), a2);
        if (v1 == null) {
            return null;
        }
        if (v1.desc.endsWith(Inject.class.getSimpleName() + ";")) {
            return new CallbackInjectionInfo(a1, a2, v1);
        }
        if (v1.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
            return new ModifyArgInjectionInfo(a1, a2, v1);
        }
        if (v1.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
            return new ModifyArgsInjectionInfo(a1, a2, v1);
        }
        if (v1.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
            return new RedirectInjectionInfo(a1, a2, v1);
        }
        if (v1.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
            return new ModifyVariableInjectionInfo(a1, a2, v1);
        }
        if (v1.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
            return new ModifyConstantInjectionInfo(a1, a2, v1);
        }
        return null;
    }
    
    public static AnnotationNode getInjectorAnnotation(final IMixinInfo a2, final MethodNode v1) {
        AnnotationNode v2 = null;
        try {
            v2 = Annotations.getSingleVisible(v1, Inject.class, ModifyArg.class, ModifyArgs.class, Redirect.class, ModifyVariable.class, ModifyConstant.class);
        }
        catch (IllegalArgumentException a3) {
            throw new InvalidMixinException(a2, String.format("Error parsing annotations on %s in %s: %s", v1.name, a2.getClassName(), a3.getMessage()));
        }
        return v2;
    }
    
    public static String getInjectorPrefix(final AnnotationNode a1) {
        if (a1 != null) {
            if (a1.desc.endsWith(ModifyArg.class.getSimpleName() + ";")) {
                return "modify";
            }
            if (a1.desc.endsWith(ModifyArgs.class.getSimpleName() + ";")) {
                return "args";
            }
            if (a1.desc.endsWith(Redirect.class.getSimpleName() + ";")) {
                return "redirect";
            }
            if (a1.desc.endsWith(ModifyVariable.class.getSimpleName() + ";")) {
                return "localvar";
            }
            if (a1.desc.endsWith(ModifyConstant.class.getSimpleName() + ";")) {
                return "constant";
            }
        }
        return "handler";
    }
    
    static String describeInjector(final IMixinContext a1, final AnnotationNode a2, final MethodNode a3) {
        return String.format("%s->@%s::%s%s", a1.toString(), Bytecode.getSimpleName(a2), a3.name, a3.desc);
    }
    
    private static String namesOf(final Collection<MemberInfo> v1) {
        int v2 = 0;
        final int v3 = v1.size();
        final StringBuilder v4 = new StringBuilder();
        for (final MemberInfo a1 : v1) {
            if (v2 > 0) {
                if (v2 == v3 - 1) {
                    v4.append(" or ");
                }
                else {
                    v4.append(", ");
                }
            }
            v4.append('\'').append(a1.name).append('\'');
            ++v2;
        }
        return v4.toString();
    }
}
