package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.perf.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.apache.logging.log4j.*;
import java.io.*;
import org.spongepowered.asm.service.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import java.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;

class MixinInfo implements Comparable<MixinInfo>, IMixinInfo
{
    private static final IMixinService classLoaderUtil;
    static int mixinOrder;
    private final transient Logger logger;
    private final transient Profiler profiler;
    private final transient MixinConfig parent;
    private final String name;
    private final String className;
    private final int priority;
    private final boolean virtual;
    private final List<ClassInfo> targetClasses;
    private final List<String> targetClassNames;
    private final transient int order;
    private final transient IMixinService service;
    private final transient IMixinConfigPlugin plugin;
    private final transient MixinEnvironment.Phase phase;
    private final transient ClassInfo info;
    private final transient SubType type;
    private final transient boolean strict;
    private transient State pendingState;
    private transient State state;
    
    MixinInfo(final IMixinService v1, final MixinConfig v2, final String v3, final boolean v4, final IMixinConfigPlugin v5, final boolean v6) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.profiler = MixinEnvironment.getProfiler();
        this.order = MixinInfo.mixinOrder++;
        this.service = v1;
        this.parent = v2;
        this.name = v3;
        this.className = v2.getMixinPackage() + v3;
        this.plugin = v5;
        this.phase = v2.getEnvironment().getPhase();
        this.strict = v2.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_TARGETS);
        try {
            final byte[] a1 = this.loadMixinClass(this.className, v4);
            this.pendingState = new State(a1);
            this.info = this.pendingState.getClassInfo();
            this.type = SubType.getTypeFor(this);
        }
        catch (InvalidMixinException a2) {
            throw a2;
        }
        catch (Exception a3) {
            throw new InvalidMixinException(this, a3);
        }
        if (!this.type.isLoadable()) {
            MixinInfo.classLoaderUtil.registerInvalidClass(this.className);
        }
        try {
            this.priority = this.readPriority(this.pendingState.getClassNode());
            this.virtual = this.readPseudo(this.pendingState.getClassNode());
            this.targetClasses = this.readTargetClasses(this.pendingState.getClassNode(), v6);
            this.targetClassNames = Collections.unmodifiableList(Lists.transform(this.targetClasses, (Function<? super ClassInfo, ? extends String>)Functions.toStringFunction()));
        }
        catch (InvalidMixinException a4) {
            throw a4;
        }
        catch (Exception a5) {
            throw new InvalidMixinException(this, a5);
        }
    }
    
    void validate() {
        if (this.pendingState == null) {
            throw new IllegalStateException("No pending validation state for " + this);
        }
        try {
            this.pendingState.validate(this.type, this.targetClasses);
            this.state = this.pendingState;
        }
        finally {
            this.pendingState = null;
        }
    }
    
    protected List<ClassInfo> readTargetClasses(final MixinClassNode a1, final boolean a2) {
        if (a1 == null) {
            return Collections.emptyList();
        }
        final AnnotationNode v1 = Annotations.getInvisible(a1, Mixin.class);
        if (v1 == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        final List<ClassInfo> v2 = new ArrayList<ClassInfo>();
        final List<Type> v3 = Annotations.getValue(v1, "value");
        final List<String> v4 = Annotations.getValue(v1, "targets");
        if (v3 != null) {
            this.readTargets(v2, (Collection<String>)Lists.transform(v3, (Function<? super Type, ?>)new Function<Type, String>() {
                final /* synthetic */ MixinInfo this$0;
                
                MixinInfo$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public String apply(final Type a1) {
                    return a1.getClassName();
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((Type)o);
                }
            }), a2, false);
        }
        if (v4 != null) {
            this.readTargets(v2, (Collection<String>)Lists.transform(v4, (Function<? super String, ?>)new Function<String, String>() {
                final /* synthetic */ MixinInfo this$0;
                
                MixinInfo$2() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public String apply(final String a1) {
                    return this.this$0.getParent().remapClassName(this.this$0.getClassRef(), a1);
                }
                
                @Override
                public /* bridge */ Object apply(final Object o) {
                    return this.apply((String)o);
                }
            }), a2, true);
        }
        return v2;
    }
    
    private void readTargets(final Collection<ClassInfo> v2, final Collection<String> v3, final boolean v4, final boolean v5) {
        for (final String a4 : v3) {
            final String a5 = a4.replace('/', '.');
            if (MixinInfo.classLoaderUtil.isClassLoaded(a5) && !this.isReloading()) {
                final String a6 = String.format("Critical problem: %s target %s was already transformed.", this, a5);
                if (this.parent.isRequired()) {
                    throw new MixinTargetAlreadyLoadedException(this, a6, a5);
                }
                this.logger.error(a6);
            }
            if (this.shouldApplyMixin(v4, a5)) {
                final ClassInfo a7 = this.getTarget(a5, v5);
                if (a7 == null || v2.contains(a7)) {
                    continue;
                }
                v2.add(a7);
                a7.addMixin(this);
            }
        }
    }
    
    private boolean shouldApplyMixin(final boolean a1, final String a2) {
        final Profiler.Section v1 = this.profiler.begin("plugin");
        final boolean v2 = this.plugin == null || a1 || this.plugin.shouldApplyMixin(a2, this.className);
        v1.end();
        return v2;
    }
    
    private ClassInfo getTarget(final String a1, final boolean a2) throws InvalidMixinException {
        final ClassInfo v1 = ClassInfo.forName(a1);
        if (v1 == null) {
            if (this.isVirtual()) {
                this.logger.debug("Skipping virtual target {} for {}", new Object[] { a1, this });
            }
            else {
                this.handleTargetError(String.format("@Mixin target %s was not found %s", a1, this));
            }
            return null;
        }
        this.type.validateTarget(a1, v1);
        if (a2 && v1.isPublic() && !this.isVirtual()) {
            this.handleTargetError(String.format("@Mixin target %s is public in %s and should be specified in value", a1, this));
        }
        return v1;
    }
    
    private void handleTargetError(final String a1) {
        if (this.strict) {
            this.logger.error(a1);
            throw new InvalidMixinException(this, a1);
        }
        this.logger.warn(a1);
    }
    
    protected int readPriority(final ClassNode a1) {
        if (a1 == null) {
            return this.parent.getDefaultMixinPriority();
        }
        final AnnotationNode v1 = Annotations.getInvisible(a1, Mixin.class);
        if (v1 == null) {
            throw new InvalidMixinException(this, String.format("The mixin '%s' is missing an @Mixin annotation", this.className));
        }
        final Integer v2 = Annotations.getValue(v1, "priority");
        return (v2 == null) ? this.parent.getDefaultMixinPriority() : v2;
    }
    
    protected boolean readPseudo(final ClassNode a1) {
        return Annotations.getInvisible(a1, Pseudo.class) != null;
    }
    
    private boolean isReloading() {
        return this.pendingState instanceof Reloaded;
    }
    
    private State getState() {
        return (this.state != null) ? this.state : this.pendingState;
    }
    
    ClassInfo getClassInfo() {
        return this.info;
    }
    
    @Override
    public IMixinConfig getConfig() {
        return this.parent;
    }
    
    MixinConfig getParent() {
        return this.parent;
    }
    
    @Override
    public int getPriority() {
        return this.priority;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getClassName() {
        return this.className;
    }
    
    @Override
    public String getClassRef() {
        return this.getClassInfo().getName();
    }
    
    @Override
    public byte[] getClassBytes() {
        return this.getState().getClassBytes();
    }
    
    @Override
    public boolean isDetachedSuper() {
        return this.getState().isDetachedSuper();
    }
    
    public boolean isUnique() {
        return this.getState().isUnique();
    }
    
    public boolean isVirtual() {
        return this.virtual;
    }
    
    public boolean isAccessor() {
        return this.type instanceof SubType.Accessor;
    }
    
    public boolean isLoadable() {
        return this.type.isLoadable();
    }
    
    public Level getLoggingLevel() {
        return this.parent.getLoggingLevel();
    }
    
    @Override
    public MixinEnvironment.Phase getPhase() {
        return this.phase;
    }
    
    @Override
    public MixinClassNode getClassNode(final int a1) {
        return this.getState().createClassNode(a1);
    }
    
    @Override
    public List<String> getTargetClasses() {
        return this.targetClassNames;
    }
    
    List<InterfaceInfo> getSoftImplements() {
        return Collections.unmodifiableList(this.getState().getSoftImplements());
    }
    
    Set<String> getSyntheticInnerClasses() {
        return Collections.unmodifiableSet((Set<? extends String>)this.getState().getSyntheticInnerClasses());
    }
    
    Set<String> getInnerClasses() {
        return Collections.unmodifiableSet((Set<? extends String>)this.getState().getInnerClasses());
    }
    
    List<ClassInfo> getTargets() {
        return Collections.unmodifiableList((List<? extends ClassInfo>)this.targetClasses);
    }
    
    Set<String> getInterfaces() {
        return this.getState().getInterfaces();
    }
    
    MixinTargetContext createContextFor(final TargetClassContext a1) {
        final MixinClassNode v1 = this.getClassNode(8);
        final Profiler.Section v2 = this.profiler.begin("pre");
        final MixinTargetContext v3 = this.type.createPreProcessor(v1).prepare().createContextFor(a1);
        v2.end();
        return v3;
    }
    
    private byte[] loadMixinClass(final String v-2, final boolean v-1) throws ClassNotFoundException {
        byte[] v0 = null;
        try {
            if (v-1) {
                final String a1 = this.service.getClassRestrictions(v-2);
                if (a1.length() > 0) {
                    this.logger.error("Classloader restrictions [{}] encountered loading {}, name: {}", new Object[] { a1, this, v-2 });
                }
            }
            v0 = this.service.getBytecodeProvider().getClassBytes(v-2, v-1);
        }
        catch (ClassNotFoundException a2) {
            throw new ClassNotFoundException(String.format("The specified mixin '%s' was not found", v-2));
        }
        catch (IOException v2) {
            this.logger.warn("Failed to load mixin {}, the specified mixin will not be applied", new Object[] { v-2 });
            throw new InvalidMixinException(this, "An error was encountered whilst loading the mixin class", v2);
        }
        return v0;
    }
    
    void reloadMixin(final byte[] a1) {
        if (this.pendingState != null) {
            throw new IllegalStateException("Cannot reload mixin while it is initialising");
        }
        this.pendingState = new Reloaded(this.state, a1);
        this.validate();
    }
    
    @Override
    public int compareTo(final MixinInfo a1) {
        if (a1 == null) {
            return 0;
        }
        if (a1.priority == this.priority) {
            return this.order - a1.order;
        }
        return this.priority - a1.priority;
    }
    
    public void preApply(final String v1, final ClassNode v2) {
        if (this.plugin != null) {
            final Profiler.Section a1 = this.profiler.begin("plugin");
            this.plugin.preApply(v1, v2, this.className, this);
            a1.end();
        }
    }
    
    public void postApply(final String v1, final ClassNode v2) {
        if (this.plugin != null) {
            final Profiler.Section a1 = this.profiler.begin("plugin");
            this.plugin.postApply(v1, v2, this.className, this);
            a1.end();
        }
        this.parent.postApply(v1, v2);
    }
    
    @Override
    public String toString() {
        return String.format("%s:%s", this.parent.getName(), this.name);
    }
    
    @Override
    public /* bridge */ int compareTo(final Object o) {
        return this.compareTo((MixinInfo)o);
    }
    
    @Override
    public /* bridge */ ClassNode getClassNode(final int a1) {
        return this.getClassNode(a1);
    }
    
    static {
        classLoaderUtil = MixinService.getService();
        MixinInfo.mixinOrder = 0;
    }
    
    class MixinMethodNode extends MethodNode
    {
        private final String originalName;
        final /* synthetic */ MixinInfo this$0;
        
        public MixinMethodNode(final MixinInfo a1, final int a2, final String a3, final String a4, final String a5, final String[] a6) {
            this.this$0 = a1;
            super(327680, a2, a3, a4, a5, a6);
            this.originalName = a3;
        }
        
        @Override
        public String toString() {
            return String.format("%s%s", this.originalName, this.desc);
        }
        
        public String getOriginalName() {
            return this.originalName;
        }
        
        public boolean isInjector() {
            return this.getInjectorAnnotation() != null || this.isSurrogate();
        }
        
        public boolean isSurrogate() {
            return this.getVisibleAnnotation(Surrogate.class) != null;
        }
        
        public boolean isSynthetic() {
            return Bytecode.hasFlag(this, 4096);
        }
        
        public AnnotationNode getVisibleAnnotation(final Class<? extends Annotation> a1) {
            return Annotations.getVisible(this, a1);
        }
        
        public AnnotationNode getInjectorAnnotation() {
            return InjectionInfo.getInjectorAnnotation(this.this$0, this);
        }
        
        public IMixinInfo getOwner() {
            return this.this$0;
        }
    }
    
    class MixinClassNode extends ClassNode
    {
        public final List<MixinMethodNode> mixinMethods;
        final /* synthetic */ MixinInfo this$0;
        
        public MixinClassNode(final MixinInfo a1, final MixinInfo a2) {
            this(a1, 327680);
        }
        
        public MixinClassNode(final MixinInfo a1, final int a2) {
            this.this$0 = a1;
            super(a2);
            this.mixinMethods = (List<MixinMethodNode>)this.methods;
        }
        
        public MixinInfo getMixin() {
            return this.this$0;
        }
        
        @Override
        public MethodVisitor visitMethod(final int a1, final String a2, final String a3, final String a4, final String[] a5) {
            final MethodNode v1 = this.this$0.new MixinMethodNode(a1, a2, a3, a4, a5);
            this.methods.add(v1);
            return v1;
        }
    }
    
    class State
    {
        private byte[] mixinBytes;
        private final ClassInfo classInfo;
        private boolean detachedSuper;
        private boolean unique;
        protected final Set<String> interfaces;
        protected final List<InterfaceInfo> softImplements;
        protected final Set<String> syntheticInnerClasses;
        protected final Set<String> innerClasses;
        protected MixinClassNode classNode;
        final /* synthetic */ MixinInfo this$0;
        
        State(final MixinInfo a1, final byte[] a2) {
            this(a1, a2, null);
        }
        
        State(final MixinInfo a1, final byte[] a2, final ClassInfo a3) {
            this.this$0 = a1;
            super();
            this.interfaces = new HashSet<String>();
            this.softImplements = new ArrayList<InterfaceInfo>();
            this.syntheticInnerClasses = new HashSet<String>();
            this.innerClasses = new HashSet<String>();
            this.mixinBytes = a2;
            this.connect();
            this.classInfo = ((a3 != null) ? a3 : ClassInfo.fromClassNode(this.getClassNode()));
        }
        
        private void connect() {
            this.classNode = this.createClassNode(0);
        }
        
        private void complete() {
            this.classNode = null;
        }
        
        ClassInfo getClassInfo() {
            return this.classInfo;
        }
        
        byte[] getClassBytes() {
            return this.mixinBytes;
        }
        
        MixinClassNode getClassNode() {
            return this.classNode;
        }
        
        boolean isDetachedSuper() {
            return this.detachedSuper;
        }
        
        boolean isUnique() {
            return this.unique;
        }
        
        List<? extends InterfaceInfo> getSoftImplements() {
            return this.softImplements;
        }
        
        Set<String> getSyntheticInnerClasses() {
            return this.syntheticInnerClasses;
        }
        
        Set<String> getInnerClasses() {
            return this.innerClasses;
        }
        
        Set<String> getInterfaces() {
            return this.interfaces;
        }
        
        MixinClassNode createClassNode(final int a1) {
            final MixinClassNode v1 = this.this$0.new MixinClassNode(this.this$0);
            final ClassReader v2 = new ClassReader(this.mixinBytes);
            v2.accept(v1, a1);
            return v1;
        }
        
        void validate(final SubType v1, final List<ClassInfo> v2) {
            final MixinPreProcessorStandard v3 = v1.createPreProcessor(this.getClassNode()).prepare();
            for (final ClassInfo a1 : v2) {
                v3.conform(a1);
            }
            v1.validate(this, v2);
            this.detachedSuper = v1.isDetachedSuper();
            this.unique = (Annotations.getVisible(this.getClassNode(), Unique.class) != null);
            this.validateInner();
            this.validateClassVersion();
            this.validateRemappables(v2);
            this.readImplementations(v1);
            this.readInnerClasses();
            this.validateChanges(v1, v2);
            this.complete();
        }
        
        private void validateInner() {
            if (!this.classInfo.isProbablyStatic()) {
                throw new InvalidMixinException(this.this$0, "Inner class mixin must be declared static");
            }
        }
        
        private void validateClassVersion() {
            if (this.classNode.version > MixinEnvironment.getCompatibilityLevel().classVersion()) {
                String format = ".";
                for (final MixinEnvironment.CompatibilityLevel v1 : MixinEnvironment.CompatibilityLevel.values()) {
                    if (v1.classVersion() >= this.classNode.version) {
                        format = String.format(". Mixin requires compatibility level %s or above.", v1.name());
                    }
                }
                throw new InvalidMixinException(this.this$0, "Unsupported mixin class version " + this.classNode.version + format);
            }
        }
        
        private void validateRemappables(final List<ClassInfo> v-2) {
            if (v-2.size() > 1) {
                for (final FieldNode a1 : this.classNode.fields) {
                    this.validateRemappable(Shadow.class, a1.name, Annotations.getVisible(a1, Shadow.class));
                }
                for (final MethodNode v0 : this.classNode.methods) {
                    this.validateRemappable(Shadow.class, v0.name, Annotations.getVisible(v0, Shadow.class));
                    final AnnotationNode v2 = Annotations.getVisible(v0, Overwrite.class);
                    if (v2 != null && ((v0.access & 0x8) == 0x0 || (v0.access & 0x1) == 0x0)) {
                        throw new InvalidMixinException(this.this$0, "Found @Overwrite annotation on " + v0.name + " in " + this.this$0);
                    }
                }
            }
        }
        
        private void validateRemappable(final Class<Shadow> a1, final String a2, final AnnotationNode a3) {
            if (a3 != null && Annotations.getValue(a3, "remap", Boolean.TRUE)) {
                throw new InvalidMixinException(this.this$0, "Found a remappable @" + a1.getSimpleName() + " annotation on " + a2 + " in " + this);
            }
        }
        
        void readImplementations(final SubType v-3) {
            this.interfaces.addAll(this.classNode.interfaces);
            this.interfaces.addAll(v-3.getInterfaces());
            final AnnotationNode invisible = Annotations.getInvisible(this.classNode, Implements.class);
            if (invisible == null) {
                return;
            }
            final List<AnnotationNode> list = Annotations.getValue(invisible);
            if (list == null) {
                return;
            }
            for (final AnnotationNode v1 : list) {
                final InterfaceInfo a1 = InterfaceInfo.fromAnnotation(this.this$0, v1);
                this.softImplements.add(a1);
                this.interfaces.add(a1.getInternalName());
                if (!(this instanceof Reloaded)) {
                    this.classInfo.addInterface(a1.getInternalName());
                }
            }
        }
        
        void readInnerClasses() {
            for (final InnerClassNode v0 : this.classNode.innerClasses) {
                final ClassInfo v2 = ClassInfo.forName(v0.name);
                if ((v0.outerName != null && v0.outerName.equals(this.classInfo.getName())) || v0.name.startsWith(this.classNode.name + "$")) {
                    if (v2.isProbablyStatic() && v2.isSynthetic()) {
                        this.syntheticInnerClasses.add(v0.name);
                    }
                    else {
                        this.innerClasses.add(v0.name);
                    }
                }
            }
        }
        
        protected void validateChanges(final SubType a1, final List<ClassInfo> a2) {
            a1.createPreProcessor(this.classNode).prepare();
        }
    }
    
    class Reloaded extends State
    {
        private final State previous;
        final /* synthetic */ MixinInfo this$0;
        
        Reloaded(final MixinInfo a1, final State a2, final byte[] a3) {
            this.this$0 = a1;
            a1.super(a3, a2.getClassInfo());
            this.previous = a2;
        }
        
        @Override
        protected void validateChanges(final SubType a1, final List<ClassInfo> a2) {
            if (!this.syntheticInnerClasses.equals(this.previous.syntheticInnerClasses)) {
                throw new MixinReloadException(this.this$0, "Cannot change inner classes");
            }
            if (!this.interfaces.equals(this.previous.interfaces)) {
                throw new MixinReloadException(this.this$0, "Cannot change interfaces");
            }
            if (!new HashSet(this.softImplements).equals(new HashSet(this.previous.softImplements))) {
                throw new MixinReloadException(this.this$0, "Cannot change soft interfaces");
            }
            final List<ClassInfo> v1 = this.this$0.readTargetClasses(this.classNode, true);
            if (!new HashSet(v1).equals(new HashSet(a2))) {
                throw new MixinReloadException(this.this$0, "Cannot change target classes");
            }
            final int v2 = this.this$0.readPriority(this.classNode);
            if (v2 != this.this$0.getPriority()) {
                throw new MixinReloadException(this.this$0, "Cannot change mixin priority");
            }
        }
    }
    
    abstract static class SubType
    {
        protected final MixinInfo mixin;
        protected final String annotationType;
        protected final boolean targetMustBeInterface;
        protected boolean detached;
        
        SubType(final MixinInfo a1, final String a2, final boolean a3) {
            super();
            this.mixin = a1;
            this.annotationType = a2;
            this.targetMustBeInterface = a3;
        }
        
        Collection<String> getInterfaces() {
            return (Collection<String>)Collections.emptyList();
        }
        
        boolean isDetachedSuper() {
            return this.detached;
        }
        
        boolean isLoadable() {
            return false;
        }
        
        void validateTarget(final String v1, final ClassInfo v2) {
            final boolean v3 = v2.isInterface();
            if (v3 != this.targetMustBeInterface) {
                final String a1 = v3 ? "" : "not ";
                throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + v1 + " is " + a1 + "an interface in " + this);
            }
        }
        
        abstract void validate(final State p0, final List<ClassInfo> p1);
        
        abstract MixinPreProcessorStandard createPreProcessor(final MixinClassNode p0);
        
        static SubType getTypeFor(final MixinInfo v1) {
            if (!v1.getClassInfo().isInterface()) {
                return new Standard(v1);
            }
            boolean v2 = false;
            for (final ClassInfo.Method a1 : v1.getClassInfo().getMethods()) {
                v2 |= !a1.isAccessor();
            }
            if (v2) {
                return new Interface(v1);
            }
            return new Accessor(v1);
        }
        
        static class Standard extends SubType
        {
            Standard(final MixinInfo a1) {
                super(a1, "@Mixin", false);
            }
            
            @Override
            void validate(final State v-3, final List<ClassInfo> v-2) {
                final ClassNode classNode = v-3.getClassNode();
                for (final ClassInfo v1 : v-2) {
                    if (classNode.superName.equals(v1.getSuperName())) {
                        continue;
                    }
                    if (!v1.hasSuperClass(classNode.superName, ClassInfo.Traversal.SUPER)) {
                        final ClassInfo a2 = ClassInfo.forName(classNode.superName);
                        if (a2.isMixin()) {
                            for (final ClassInfo a3 : a2.getTargets()) {
                                if (v-2.contains(a3)) {
                                    throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + a3.getClassName() + " as its superclass " + a2.getClassName());
                                }
                            }
                        }
                        throw new InvalidMixinException(this.mixin, "Super class '" + classNode.superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + v1 + "'");
                    }
                    this.detached = true;
                }
            }
            
            @Override
            MixinPreProcessorStandard createPreProcessor(final MixinClassNode a1) {
                return new MixinPreProcessorStandard(this.mixin, a1);
            }
        }
        
        static class Interface extends SubType
        {
            Interface(final MixinInfo a1) {
                super(a1, "@Mixin", true);
            }
            
            @Override
            void validate(final State a1, final List<ClassInfo> a2) {
                if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                    throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
                }
                final ClassNode v1 = a1.getClassNode();
                if (!"java/lang/Object".equals(v1.superName)) {
                    throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + v1.superName.replace('/', '.'));
                }
            }
            
            @Override
            MixinPreProcessorStandard createPreProcessor(final MixinClassNode a1) {
                return new MixinPreProcessorInterface(this.mixin, a1);
            }
        }
        
        static class Accessor extends SubType
        {
            private final Collection<String> interfaces;
            
            Accessor(final MixinInfo a1) {
                super(a1, "@Mixin", false);
                (this.interfaces = new ArrayList<String>()).add(a1.getClassRef());
            }
            
            @Override
            boolean isLoadable() {
                return true;
            }
            
            @Override
            Collection<String> getInterfaces() {
                return this.interfaces;
            }
            
            @Override
            void validateTarget(final String a1, final ClassInfo a2) {
                final boolean v1 = a2.isInterface();
                if (v1 && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                    throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
                }
            }
            
            @Override
            void validate(final State a1, final List<ClassInfo> a2) {
                final ClassNode v1 = a1.getClassNode();
                if (!"java/lang/Object".equals(v1.superName)) {
                    throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + v1.superName.replace('/', '.'));
                }
            }
            
            @Override
            MixinPreProcessorStandard createPreProcessor(final MixinClassNode a1) {
                return new MixinPreProcessorAccessor(this.mixin, a1);
            }
        }
    }
}
