package org.spongepowered.asm.mixin.transformer;

import java.lang.annotation.*;
import org.spongepowered.asm.util.perf.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.transformer.ext.extensions.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.lib.signature.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.util.throwables.*;
import org.spongepowered.asm.mixin.injection.*;
import com.google.common.collect.*;

class MixinApplicatorStandard
{
    protected static final List<Class<? extends Annotation>> CONSTRAINED_ANNOTATIONS;
    protected static final int[] INITIALISER_OPCODE_BLACKLIST;
    protected final Logger logger;
    protected final TargetClassContext context;
    protected final String targetName;
    protected final ClassNode targetClass;
    protected final Profiler profiler;
    protected final boolean mergeSignatures;
    
    MixinApplicatorStandard(final TargetClassContext a1) {
        super();
        this.logger = LogManager.getLogger("mixin");
        this.profiler = MixinEnvironment.getProfiler();
        this.context = a1;
        this.targetName = a1.getClassName();
        this.targetClass = a1.getClassNode();
        final ExtensionClassExporter v1 = (ExtensionClassExporter)a1.getExtensions().getExtension((Class)ExtensionClassExporter.class);
        this.mergeSignatures = (v1.isDecompilerActive() && MixinEnvironment.getCurrentEnvironment().getOption(MixinEnvironment.Option.DEBUG_EXPORT_DECOMPILE_MERGESIGNATURES));
    }
    
    void apply(final SortedSet<MixinInfo> v-3) {
        final List<MixinTargetContext> list = new ArrayList<MixinTargetContext>();
        for (final MixinInfo a1 : v-3) {
            this.logger.log(a1.getLoggingLevel(), "Mixing {} from {} into {}", new Object[] { a1.getName(), a1.getParent(), this.targetName });
            list.add(a1.createContextFor(this.context));
        }
        MixinTargetContext a2 = null;
        try {
            for (final MixinTargetContext v1 : list) {
                (a2 = v1).preApply(this.targetName, this.targetClass);
            }
            for (final ApplicatorPass v2 : ApplicatorPass.values()) {
                final Profiler.Section v3 = this.profiler.begin("pass", v2.name().toLowerCase());
                for (final MixinTargetContext v4 : list) {
                    this.applyMixin(a2 = v4, v2);
                }
                v3.end();
            }
            for (final MixinTargetContext v1 : list) {
                (a2 = v1).postApply(this.targetName, this.targetClass);
            }
        }
        catch (InvalidMixinException v5) {
            throw v5;
        }
        catch (Exception v6) {
            throw new InvalidMixinException(a2, "Unexpecteded " + v6.getClass().getSimpleName() + " whilst applying the mixin class: " + v6.getMessage(), v6);
        }
        this.applySourceMap(this.context);
        this.context.processDebugTasks();
    }
    
    protected final void applyMixin(final MixinTargetContext a1, final ApplicatorPass a2) {
        switch (a2) {
            case MAIN: {
                this.applySignature(a1);
                this.applyInterfaces(a1);
                this.applyAttributes(a1);
                this.applyAnnotations(a1);
                this.applyFields(a1);
                this.applyMethods(a1);
                this.applyInitialisers(a1);
                break;
            }
            case PREINJECT: {
                this.prepareInjections(a1);
                break;
            }
            case INJECT: {
                this.applyAccessors(a1);
                this.applyInjections(a1);
                break;
            }
            default: {
                throw new IllegalStateException("Invalid pass specified " + a2);
            }
        }
    }
    
    protected void applySignature(final MixinTargetContext a1) {
        if (this.mergeSignatures) {
            this.context.mergeSignature(a1.getSignature());
        }
    }
    
    protected void applyInterfaces(final MixinTargetContext v2) {
        for (final String a1 : v2.getInterfaces()) {
            if (!this.targetClass.interfaces.contains(a1)) {
                this.targetClass.interfaces.add(a1);
                v2.getTargetClassInfo().addInterface(a1);
            }
        }
    }
    
    protected void applyAttributes(final MixinTargetContext a1) {
        if (a1.shouldSetSourceFile()) {
            this.targetClass.sourceFile = a1.getSourceFile();
        }
        this.targetClass.version = Math.max(this.targetClass.version, a1.getMinRequiredClassVersion());
    }
    
    protected void applyAnnotations(final MixinTargetContext a1) {
        final ClassNode v1 = a1.getClassNode();
        Bytecode.mergeAnnotations(v1, this.targetClass);
    }
    
    protected void applyFields(final MixinTargetContext a1) {
        this.mergeShadowFields(a1);
        this.mergeNewFields(a1);
    }
    
    protected void mergeShadowFields(final MixinTargetContext v-3) {
        for (final Map.Entry<FieldNode, ClassInfo.Field> entry : v-3.getShadowFields()) {
            final FieldNode a1 = entry.getKey();
            final FieldNode v1 = this.findTargetField(a1);
            if (v1 != null) {
                Bytecode.mergeAnnotations(a1, v1);
                if (!entry.getValue().isDecoratedMutable() || Bytecode.hasFlag(v1, 2)) {
                    continue;
                }
                final FieldNode fieldNode = v1;
                fieldNode.access &= 0xFFFFFFEF;
            }
        }
    }
    
    protected void mergeNewFields(final MixinTargetContext v-2) {
        for (final FieldNode v0 : v-2.getFields()) {
            final FieldNode v2 = this.findTargetField(v0);
            if (v2 == null) {
                this.targetClass.fields.add(v0);
                if (v0.signature == null) {
                    continue;
                }
                if (this.mergeSignatures) {
                    final SignatureVisitor a1 = v-2.getSignature().getRemapper();
                    new SignatureReader(v0.signature).accept(a1);
                    v0.signature = a1.toString();
                }
                else {
                    v0.signature = null;
                }
            }
        }
    }
    
    protected void applyMethods(final MixinTargetContext v-1) {
        for (final MethodNode a1 : v-1.getShadowMethods()) {
            this.applyShadowMethod(v-1, a1);
        }
        for (final MethodNode v1 : v-1.getMethods()) {
            this.applyNormalMethod(v-1, v1);
        }
    }
    
    protected void applyShadowMethod(final MixinTargetContext a1, final MethodNode a2) {
        final MethodNode v1 = this.findTargetMethod(a2);
        if (v1 != null) {
            Bytecode.mergeAnnotations(a2, v1);
        }
    }
    
    protected void applyNormalMethod(final MixinTargetContext a1, final MethodNode a2) {
        a1.transformMethod(a2);
        if (!a2.name.startsWith("<")) {
            this.checkMethodVisibility(a1, a2);
            this.checkMethodConstraints(a1, a2);
            this.mergeMethod(a1, a2);
        }
        else if ("<clinit>".equals(a2.name)) {
            this.appendInsns(a1, a2);
        }
    }
    
    protected void mergeMethod(final MixinTargetContext v2, final MethodNode v3) {
        final boolean v4 = Annotations.getVisible(v3, Overwrite.class) != null;
        final MethodNode v5 = this.findTargetMethod(v3);
        if (v5 != null) {
            if (this.isAlreadyMerged(v2, v3, v4, v5)) {
                return;
            }
            final AnnotationNode a1 = Annotations.getInvisible(v3, Intrinsic.class);
            if (a1 != null) {
                if (this.mergeIntrinsic(v2, v3, v4, v5, a1)) {
                    v2.getTarget().methodMerged(v3);
                    return;
                }
            }
            else {
                if (v2.requireOverwriteAnnotations() && !v4) {
                    throw new InvalidMixinException(v2, String.format("%s%s in %s cannot overwrite method in %s because @Overwrite is required by the parent configuration", v3.name, v3.desc, v2, v2.getTarget().getClassName()));
                }
                this.targetClass.methods.remove(v5);
            }
        }
        else if (v4) {
            throw new InvalidMixinException(v2, String.format("Overwrite target \"%s\" was not located in target class %s", v3.name, v2.getTargetClassRef()));
        }
        this.targetClass.methods.add(v3);
        v2.methodMerged(v3);
        if (v3.signature != null) {
            if (this.mergeSignatures) {
                final SignatureVisitor a2 = v2.getSignature().getRemapper();
                new SignatureReader(v3.signature).accept(a2);
                v3.signature = a2.toString();
            }
            else {
                v3.signature = null;
            }
        }
    }
    
    protected boolean isAlreadyMerged(final MixinTargetContext a1, final MethodNode a2, final boolean a3, final MethodNode a4) {
        final AnnotationNode v1 = Annotations.getVisible(a4, MixinMerged.class);
        if (v1 == null) {
            if (Annotations.getVisible(a4, Final.class) != null) {
                this.logger.warn("Overwrite prohibited for @Final method {} in {}. Skipping method.", new Object[] { a2.name, a1 });
                return true;
            }
            return false;
        }
        else {
            final String v2 = Annotations.getValue(v1, "sessionId");
            if (!this.context.getSessionId().equals(v2)) {
                throw new ClassFormatError("Invalid @MixinMerged annotation found in" + a1 + " at " + a2.name + " in " + this.targetClass.name);
            }
            if (Bytecode.hasFlag(a4, 4160) && Bytecode.hasFlag(a2, 4160)) {
                if (a1.getEnvironment().getOption(MixinEnvironment.Option.DEBUG_VERBOSE)) {
                    this.logger.warn("Synthetic bridge method clash for {} in {}", new Object[] { a2.name, a1 });
                }
                return true;
            }
            final String v3 = Annotations.getValue(v1, "mixin");
            final int v4 = Annotations.getValue(v1, "priority");
            if (v4 >= a1.getPriority() && !v3.equals(a1.getClassName())) {
                this.logger.warn("Method overwrite conflict for {} in {}, previously written by {}. Skipping method.", new Object[] { a2.name, a1, v3 });
                return true;
            }
            if (Annotations.getVisible(a4, Final.class) != null) {
                this.logger.warn("Method overwrite conflict for @Final method {} in {} declared by {}. Skipping method.", new Object[] { a2.name, a1, v3 });
                return true;
            }
            return false;
        }
    }
    
    protected boolean mergeIntrinsic(final MixinTargetContext a3, final MethodNode a4, final boolean a5, final MethodNode v1, final AnnotationNode v2) {
        if (a5) {
            throw new InvalidMixinException(a3, "@Intrinsic is not compatible with @Overwrite, remove one of these annotations on " + a4.name + " in " + a3);
        }
        final String v3 = a4.name + a4.desc;
        if (Bytecode.hasFlag(a4, 8)) {
            throw new InvalidMixinException(a3, "@Intrinsic method cannot be static, found " + v3 + " in " + a3);
        }
        if (!Bytecode.hasFlag(a4, 4096)) {
            final AnnotationNode a6 = Annotations.getVisible(a4, MixinRenamed.class);
            if (a6 == null || !Annotations.getValue(a6, "isInterfaceMember", Boolean.FALSE)) {
                throw new InvalidMixinException(a3, "@Intrinsic method must be prefixed interface method, no rename encountered on " + v3 + " in " + a3);
            }
        }
        if (!Annotations.getValue(v2, "displace", Boolean.FALSE)) {
            this.logger.log(a3.getLoggingLevel(), "Skipping Intrinsic mixin method {} for {}", new Object[] { v3, a3.getTargetClassRef() });
            return true;
        }
        this.displaceIntrinsic(a3, a4, v1);
        return false;
    }
    
    protected void displaceIntrinsic(final MixinTargetContext v2, final MethodNode v3, final MethodNode v4) {
        final String v5 = "proxy+" + v4.name;
        for (final AbstractInsnNode a4 : v3.instructions) {
            if (a4 instanceof MethodInsnNode && a4.getOpcode() != 184) {
                final MethodInsnNode a5 = (MethodInsnNode)a4;
                if (!a5.owner.equals(this.targetClass.name) || !a5.name.equals(v4.name) || !a5.desc.equals(v4.desc)) {
                    continue;
                }
                a5.name = v5;
            }
        }
        v4.name = v5;
    }
    
    protected final void appendInsns(final MixinTargetContext v-2, final MethodNode v-1) {
        if (Type.getReturnType(v-1.desc) != Type.VOID_TYPE) {
            throw new IllegalArgumentException("Attempted to merge insns from a method which does not return void");
        }
        final MethodNode v0 = this.findTargetMethod(v-1);
        if (v0 != null) {
            final AbstractInsnNode v2 = Bytecode.findInsn(v0, 177);
            if (v2 != null) {
                for (final AbstractInsnNode a3 : v-1.instructions) {
                    if (!(a3 instanceof LineNumberNode) && a3.getOpcode() != 177) {
                        v0.instructions.insertBefore(v2, a3);
                    }
                }
                v0.maxLocals = Math.max(v0.maxLocals, v-1.maxLocals);
                v0.maxStack = Math.max(v0.maxStack, v-1.maxStack);
            }
            return;
        }
        this.targetClass.methods.add(v-1);
    }
    
    protected void applyInitialisers(final MixinTargetContext v2) {
        final MethodNode v3 = this.getConstructor(v2);
        if (v3 == null) {
            return;
        }
        final Deque<AbstractInsnNode> v4 = this.getInitialiser(v2, v3);
        if (v4 == null || v4.size() == 0) {
            return;
        }
        for (final MethodNode a1 : this.targetClass.methods) {
            if ("<init>".equals(a1.name)) {
                a1.maxStack = Math.max(a1.maxStack, v3.maxStack);
                this.injectInitialiser(v2, a1, v4);
            }
        }
    }
    
    protected MethodNode getConstructor(final MixinTargetContext v2) {
        MethodNode v3 = null;
        for (final MethodNode a1 : v2.getMethods()) {
            if ("<init>".equals(a1.name) && Bytecode.methodHasLineNumbers(a1)) {
                if (v3 == null) {
                    v3 = a1;
                }
                else {
                    this.logger.warn(String.format("Mixin %s has multiple constructors, %s was selected\n", v2, v3.desc));
                }
            }
        }
        return v3;
    }
    
    private Range getConstructorRange(final MethodNode v-6) {
        boolean b = false;
        AbstractInsnNode abstractInsnNode = null;
        int line = 0;
        int n = 0;
        int a2 = 0;
        int v0 = -1;
        for (final AbstractInsnNode a1 : v-6.instructions) {
            if (a1 instanceof LineNumberNode) {
                line = ((LineNumberNode)a1).line;
                b = true;
            }
            else if (a1 instanceof MethodInsnNode) {
                if (a1.getOpcode() != 183 || !"<init>".equals(((MethodInsnNode)a1).name) || v0 != -1) {
                    continue;
                }
                v0 = v-6.instructions.indexOf(a1);
                n = line;
            }
            else if (a1.getOpcode() == 181) {
                b = false;
            }
            else {
                if (a1.getOpcode() != 177) {
                    continue;
                }
                if (b) {
                    a2 = line;
                }
                else {
                    a2 = n;
                    abstractInsnNode = a1;
                }
            }
        }
        if (abstractInsnNode != null) {
            final LabelNode v3 = new LabelNode(new Label());
            v-6.instructions.insertBefore(abstractInsnNode, v3);
            v-6.instructions.insertBefore(abstractInsnNode, new LineNumberNode(n, v3));
        }
        return new Range(n, a2, v0);
    }
    
    protected final Deque<AbstractInsnNode> getInitialiser(final MixinTargetContext v-9, final MethodNode v-8) {
        final Range constructorRange = this.getConstructorRange(v-8);
        if (!constructorRange.isValid()) {
            return null;
        }
        int line = 0;
        final Deque<AbstractInsnNode> deque = new ArrayDeque<AbstractInsnNode>();
        boolean excludes = false;
        int n = -1;
        LabelNode labelNode = null;
        final Iterator<AbstractInsnNode> iterator = v-8.instructions.iterator(constructorRange.marker);
        while (iterator.hasNext()) {
            final AbstractInsnNode v0 = iterator.next();
            if (v0 instanceof LineNumberNode) {
                line = ((LineNumberNode)v0).line;
                final AbstractInsnNode a1 = v-8.instructions.get(v-8.instructions.indexOf(v0) + 1);
                if (line == constructorRange.end && a1.getOpcode() != 177) {
                    excludes = true;
                    n = 177;
                }
                else {
                    excludes = constructorRange.excludes(line);
                    n = -1;
                }
            }
            else {
                if (!excludes) {
                    continue;
                }
                if (labelNode != null) {
                    deque.add(labelNode);
                    labelNode = null;
                }
                if (v0 instanceof LabelNode) {
                    labelNode = (LabelNode)v0;
                }
                else {
                    final int v2 = v0.getOpcode();
                    if (v2 == n) {
                        n = -1;
                    }
                    else {
                        for (final int a2 : MixinApplicatorStandard.INITIALISER_OPCODE_BLACKLIST) {
                            if (v2 == a2) {
                                throw new InvalidMixinException(v-9, "Cannot handle " + Bytecode.getOpcodeName(v2) + " opcode (0x" + Integer.toHexString(v2).toUpperCase() + ") in class initialiser");
                            }
                        }
                        deque.add(v0);
                    }
                }
            }
        }
        final AbstractInsnNode abstractInsnNode = deque.peekLast();
        if (abstractInsnNode != null && abstractInsnNode.getOpcode() != 181) {
            throw new InvalidMixinException(v-9, "Could not parse initialiser, expected 0xB5, found 0x" + Integer.toHexString(abstractInsnNode.getOpcode()) + " in " + v-9);
        }
        return deque;
    }
    
    protected final void injectInitialiser(final MixinTargetContext v1, final MethodNode v2, final Deque<AbstractInsnNode> v3) {
        final Map<LabelNode, LabelNode> v4 = Bytecode.cloneLabels(v2.instructions);
        AbstractInsnNode v5 = this.findInitialiserInjectionPoint(v1, v2, v3);
        if (v5 == null) {
            this.logger.warn("Failed to locate initialiser injection point in <init>{}, initialiser was not mixed in.", new Object[] { v2.desc });
            return;
        }
        for (final AbstractInsnNode a2 : v3) {
            if (a2 instanceof LabelNode) {
                continue;
            }
            if (a2 instanceof JumpInsnNode) {
                throw new InvalidMixinException(v1, "Unsupported JUMP opcode in initialiser in " + v1);
            }
            final AbstractInsnNode a3 = a2.clone(v4);
            v2.instructions.insert(v5, a3);
            v5 = a3;
        }
    }
    
    protected AbstractInsnNode findInitialiserInjectionPoint(final MixinTargetContext v-8, final MethodNode v-7, final Deque<AbstractInsnNode> v-6) {
        final Set<String> set = new HashSet<String>();
        for (final AbstractInsnNode a1 : v-6) {
            if (a1.getOpcode() == 181) {
                set.add(fieldKey((FieldInsnNode)a1));
            }
        }
        final InitialiserInjectionMode initialiserInjectionMode = this.getInitialiserInjectionMode(v-8.getEnvironment());
        final String name = v-8.getTargetClassInfo().getName();
        final String superName = v-8.getTargetClassInfo().getSuperName();
        AbstractInsnNode abstractInsnNode = null;
        for (final AbstractInsnNode v2 : v-7.instructions) {
            if (v2.getOpcode() == 183 && "<init>".equals(((MethodInsnNode)v2).name)) {
                final String a2 = ((MethodInsnNode)v2).owner;
                if (!a2.equals(name) && !a2.equals(superName)) {
                    continue;
                }
                abstractInsnNode = v2;
                if (initialiserInjectionMode == InitialiserInjectionMode.SAFE) {
                    break;
                }
                continue;
            }
            else {
                if (v2.getOpcode() != 181 || initialiserInjectionMode != InitialiserInjectionMode.DEFAULT) {
                    continue;
                }
                final String a3 = fieldKey((FieldInsnNode)v2);
                if (!set.contains(a3)) {
                    continue;
                }
                abstractInsnNode = v2;
            }
        }
        return abstractInsnNode;
    }
    
    private InitialiserInjectionMode getInitialiserInjectionMode(final MixinEnvironment v2) {
        final String v3 = v2.getOptionValue(MixinEnvironment.Option.INITIALISER_INJECTION_MODE);
        if (v3 == null) {
            return InitialiserInjectionMode.DEFAULT;
        }
        try {
            return InitialiserInjectionMode.valueOf(v3.toUpperCase());
        }
        catch (Exception a1) {
            this.logger.warn("Could not parse unexpected value \"{}\" for mixin.initialiserInjectionMode, reverting to DEFAULT", new Object[] { v3 });
            return InitialiserInjectionMode.DEFAULT;
        }
    }
    
    private static String fieldKey(final FieldInsnNode a1) {
        return String.format("%s:%s", a1.desc, a1.name);
    }
    
    protected void prepareInjections(final MixinTargetContext a1) {
        a1.prepareInjections();
    }
    
    protected void applyInjections(final MixinTargetContext a1) {
        a1.applyInjections();
    }
    
    protected void applyAccessors(final MixinTargetContext v2) {
        final List<MethodNode> v3 = v2.generateAccessors();
        for (final MethodNode a1 : v3) {
            if (!a1.name.startsWith("<")) {
                this.mergeMethod(v2, a1);
            }
        }
    }
    
    protected void checkMethodVisibility(final MixinTargetContext a1, final MethodNode a2) {
        if (Bytecode.hasFlag(a2, 8) && !Bytecode.hasFlag(a2, 2) && !Bytecode.hasFlag(a2, 4096) && Annotations.getVisible(a2, Overwrite.class) == null) {
            throw new InvalidMixinException(a1, String.format("Mixin %s contains non-private static method %s", a1, a2));
        }
    }
    
    protected void applySourceMap(final TargetClassContext a1) {
        this.targetClass.sourceDebug = a1.getSourceMap().toString();
    }
    
    protected void checkMethodConstraints(final MixinTargetContext v2, final MethodNode v3) {
        for (final Class<? extends Annotation> a2 : MixinApplicatorStandard.CONSTRAINED_ANNOTATIONS) {
            final AnnotationNode a3 = Annotations.getVisible(v3, a2);
            if (a3 != null) {
                this.checkConstraints(v2, v3, a3);
            }
        }
    }
    
    protected final void checkConstraints(final MixinTargetContext v-2, final MethodNode v-1, final AnnotationNode v0) {
        try {
            final ConstraintParser.Constraint a3 = ConstraintParser.parse(v0);
            try {
                a3.check(v-2.getEnvironment());
            }
            catch (ConstraintViolationException a5) {
                final String a4 = String.format("Constraint violation: %s on %s in %s", a5.getMessage(), v-1, v-2);
                this.logger.warn(a4);
                if (!v-2.getEnvironment().getOption(MixinEnvironment.Option.IGNORE_CONSTRAINTS)) {
                    throw new InvalidMixinException(v-2, a4, a5);
                }
            }
        }
        catch (InvalidConstraintException v) {
            throw new InvalidMixinException(v-2, v.getMessage());
        }
    }
    
    protected final MethodNode findTargetMethod(final MethodNode v2) {
        for (final MethodNode a1 : this.targetClass.methods) {
            if (a1.name.equals(v2.name) && a1.desc.equals(v2.desc)) {
                return a1;
            }
        }
        return null;
    }
    
    protected final FieldNode findTargetField(final FieldNode v2) {
        for (final FieldNode a1 : this.targetClass.fields) {
            if (a1.name.equals(v2.name)) {
                return a1;
            }
        }
        return null;
    }
    
    static {
        CONSTRAINED_ANNOTATIONS = ImmutableList.of((Object)Overwrite.class, (Object)Inject.class, (Object)ModifyArg.class, (Object)ModifyArgs.class, (Object)Redirect.class, (Object)ModifyVariable.class, (Object)ModifyConstant.class);
        INITIALISER_OPCODE_BLACKLIST = new int[] { 177, 21, 22, 23, 24, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 79, 80, 81, 82, 83, 84, 85, 86 };
    }
    
    enum ApplicatorPass
    {
        MAIN, 
        PREINJECT, 
        INJECT;
        
        private static final /* synthetic */ ApplicatorPass[] $VALUES;
        
        public static ApplicatorPass[] values() {
            return ApplicatorPass.$VALUES.clone();
        }
        
        public static ApplicatorPass valueOf(final String a1) {
            return Enum.valueOf(ApplicatorPass.class, a1);
        }
        
        static {
            $VALUES = new ApplicatorPass[] { ApplicatorPass.MAIN, ApplicatorPass.PREINJECT, ApplicatorPass.INJECT };
        }
    }
    
    enum InitialiserInjectionMode
    {
        DEFAULT, 
        SAFE;
        
        private static final /* synthetic */ InitialiserInjectionMode[] $VALUES;
        
        public static InitialiserInjectionMode[] values() {
            return InitialiserInjectionMode.$VALUES.clone();
        }
        
        public static InitialiserInjectionMode valueOf(final String a1) {
            return Enum.valueOf(InitialiserInjectionMode.class, a1);
        }
        
        static {
            $VALUES = new InitialiserInjectionMode[] { InitialiserInjectionMode.DEFAULT, InitialiserInjectionMode.SAFE };
        }
    }
    
    class Range
    {
        final int start;
        final int end;
        final int marker;
        final /* synthetic */ MixinApplicatorStandard this$0;
        
        Range(final MixinApplicatorStandard a1, final int a2, final int a3, final int a4) {
            this.this$0 = a1;
            super();
            this.start = a2;
            this.end = a3;
            this.marker = a4;
        }
        
        boolean isValid() {
            return this.start != 0 && this.end != 0 && this.end >= this.start;
        }
        
        boolean contains(final int a1) {
            return a1 >= this.start && a1 <= this.end;
        }
        
        boolean excludes(final int a1) {
            return a1 < this.start || a1 > this.end;
        }
        
        @Override
        public String toString() {
            return String.format("Range[%d-%d,%d,valid=%s)", this.start, this.end, this.marker, this.isValid());
        }
    }
}
