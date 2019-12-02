package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.perf.*;
import java.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.mixin.gen.throwables.*;
import org.spongepowered.asm.mixin.refmap.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.util.throwables.*;
import org.spongepowered.asm.lib.tree.*;
import com.google.common.base.*;
import org.spongepowered.asm.lib.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

class MixinPreProcessorStandard
{
    private static final Logger logger;
    protected final MixinInfo mixin;
    protected final MixinInfo.MixinClassNode classNode;
    protected final MixinEnvironment env;
    protected final Profiler profiler;
    private final boolean verboseLogging;
    private final boolean strictUnique;
    private boolean prepared;
    private boolean attached;
    
    MixinPreProcessorStandard(final MixinInfo a1, final MixinInfo.MixinClassNode a2) {
        super();
        this.profiler = MixinEnvironment.getProfiler();
        this.mixin = a1;
        this.classNode = a2;
        this.env = a1.getParent().getEnvironment();
        this.verboseLogging = this.env.getOption(MixinEnvironment.Option.DEBUG_VERBOSE);
        this.strictUnique = this.env.getOption(MixinEnvironment.Option.DEBUG_UNIQUE);
    }
    
    final MixinPreProcessorStandard prepare() {
        if (this.prepared) {
            return this;
        }
        this.prepared = true;
        final Profiler.Section begin = this.profiler.begin("prepare");
        for (final MixinInfo.MixinMethodNode v0 : this.classNode.mixinMethods) {
            final ClassInfo.Method v2 = this.mixin.getClassInfo().findMethod(v0);
            this.prepareMethod(v0, v2);
        }
        for (final FieldNode v3 : this.classNode.fields) {
            this.prepareField(v3);
        }
        begin.end();
        return this;
    }
    
    protected void prepareMethod(final MixinInfo.MixinMethodNode a1, final ClassInfo.Method a2) {
        this.prepareShadow(a1, a2);
        this.prepareSoftImplements(a1, a2);
    }
    
    protected void prepareShadow(final MixinInfo.MixinMethodNode v1, final ClassInfo.Method v2) {
        final AnnotationNode v3 = Annotations.getVisible(v1, Shadow.class);
        if (v3 == null) {
            return;
        }
        final String v4 = Annotations.getValue(v3, "prefix", (Class<?>)Shadow.class);
        if (v1.name.startsWith(v4)) {
            Annotations.setVisible(v1, MixinRenamed.class, "originalName", v1.name);
            final String a1 = v1.name.substring(v4.length());
            v1.name = v2.renameTo(a1);
        }
    }
    
    protected void prepareSoftImplements(final MixinInfo.MixinMethodNode v1, final ClassInfo.Method v2) {
        for (final InterfaceInfo a1 : this.mixin.getSoftImplements()) {
            if (a1.renameMethod(v1)) {
                v2.renameTo(v1.name);
            }
        }
    }
    
    protected void prepareField(final FieldNode a1) {
    }
    
    final MixinPreProcessorStandard conform(final TargetClassContext a1) {
        return this.conform(a1.getClassInfo());
    }
    
    final MixinPreProcessorStandard conform(final ClassInfo v-2) {
        final Profiler.Section begin = this.profiler.begin("conform");
        for (final MixinInfo.MixinMethodNode v1 : this.classNode.mixinMethods) {
            if (v1.isInjector()) {
                final ClassInfo.Method a1 = this.mixin.getClassInfo().findMethod(v1, 10);
                this.conformInjector(v-2, v1, a1);
            }
        }
        begin.end();
        return this;
    }
    
    private void conformInjector(final ClassInfo a1, final MixinInfo.MixinMethodNode a2, final ClassInfo.Method a3) {
        final MethodMapper v1 = a1.getMethodMapper();
        v1.remapHandlerMethod(this.mixin, a2, a3);
    }
    
    MixinTargetContext createContextFor(final TargetClassContext a1) {
        final MixinTargetContext v1 = new MixinTargetContext(this.mixin, this.classNode, a1);
        this.conform(a1);
        this.attach(v1);
        return v1;
    }
    
    final MixinPreProcessorStandard attach(final MixinTargetContext a1) {
        if (this.attached) {
            throw new IllegalStateException("Preprocessor was already attached");
        }
        this.attached = true;
        final Profiler.Section v1 = this.profiler.begin("attach");
        Profiler.Section v2 = this.profiler.begin("methods");
        this.attachMethods(a1);
        v2 = v2.next("fields");
        this.attachFields(a1);
        v2 = v2.next("transform");
        this.transform(a1);
        v2.end();
        v1.end();
        return this;
    }
    
    protected void attachMethods(final MixinTargetContext v0) {
        final Iterator<MixinInfo.MixinMethodNode> v = this.classNode.mixinMethods.iterator();
        while (v.hasNext()) {
            final MixinInfo.MixinMethodNode a1 = v.next();
            if (!this.validateMethod(v0, a1)) {
                v.remove();
            }
            else if (this.attachInjectorMethod(v0, a1)) {
                v0.addMixinMethod(a1);
            }
            else if (this.attachAccessorMethod(v0, a1)) {
                v.remove();
            }
            else if (this.attachShadowMethod(v0, a1)) {
                v0.addShadowMethod(a1);
                v.remove();
            }
            else if (this.attachOverwriteMethod(v0, a1)) {
                v0.addMixinMethod(a1);
            }
            else if (this.attachUniqueMethod(v0, a1)) {
                v.remove();
            }
            else {
                this.attachMethod(v0, a1);
                v0.addMixinMethod(a1);
            }
        }
    }
    
    protected boolean validateMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2) {
        return true;
    }
    
    protected boolean attachInjectorMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2) {
        return a2.isInjector();
    }
    
    protected boolean attachAccessorMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2) {
        return this.attachAccessorMethod(a1, a2, SpecialMethod.ACCESSOR) || this.attachAccessorMethod(a1, a2, SpecialMethod.INVOKER);
    }
    
    protected boolean attachAccessorMethod(final MixinTargetContext a3, final MixinInfo.MixinMethodNode v1, final SpecialMethod v2) {
        final AnnotationNode v3 = v1.getVisibleAnnotation(v2.annotation);
        if (v3 == null) {
            return false;
        }
        final String v4 = v2 + " method " + v1.name;
        final ClassInfo.Method v5 = this.getSpecialMethod(v1, v2);
        if (MixinEnvironment.getCompatibilityLevel().isAtLeast(MixinEnvironment.CompatibilityLevel.JAVA_8) && v5.isStatic()) {
            if (this.mixin.getTargets().size() > 1) {
                throw new InvalidAccessorException(a3, v4 + " in multi-target mixin is invalid. Mixin must have exactly 1 target.");
            }
            final String a4 = a3.getUniqueName(v1, true);
            MixinPreProcessorStandard.logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique method {}{} to {} in {}", new Object[] { v1.name, v1.desc, a4, this.mixin });
            v1.name = v5.renameTo(a4);
        }
        else {
            if (!v5.isAbstract()) {
                throw new InvalidAccessorException(a3, v4 + " is not abstract");
            }
            if (v5.isStatic()) {
                throw new InvalidAccessorException(a3, v4 + " cannot be static");
            }
        }
        a3.addAccessorMethod(v1, v2.annotation);
        return true;
    }
    
    protected boolean attachShadowMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2) {
        return this.attachSpecialMethod(a1, a2, SpecialMethod.SHADOW);
    }
    
    protected boolean attachOverwriteMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2) {
        return this.attachSpecialMethod(a1, a2, SpecialMethod.OVERWRITE);
    }
    
    protected boolean attachSpecialMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2, final SpecialMethod a3) {
        final AnnotationNode v1 = a2.getVisibleAnnotation(a3.annotation);
        if (v1 == null) {
            return false;
        }
        if (a3.isOverwrite) {
            this.checkMixinNotUnique(a2, a3);
        }
        final ClassInfo.Method v2 = this.getSpecialMethod(a2, a3);
        MethodNode v3 = a1.findMethod(a2, v1);
        if (v3 == null) {
            if (a3.isOverwrite) {
                return false;
            }
            v3 = a1.findRemappedMethod(a2);
            if (v3 == null) {
                throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s was not located in the target class %s. %s%s", a3, a2.name, this.mixin, a1.getTarget(), a1.getReferenceMapper().getStatus(), getDynamicInfo(a2)));
            }
            a2.name = v2.renameTo(v3.name);
        }
        if ("<init>".equals(v3.name)) {
            throw new InvalidMixinException(this.mixin, String.format("Nice try! %s in %s cannot alias a constructor", a2.name, this.mixin));
        }
        if (!Bytecode.compareFlags(a2, v3, 8)) {
            throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of %s method %s in %s does not match the target", a3, a2.name, this.mixin));
        }
        this.conformVisibility(a1, a2, a3, v3);
        if (!v3.name.equals(a2.name)) {
            if (a3.isOverwrite && (v3.access & 0x2) == 0x0) {
                throw new InvalidMixinException(this.mixin, "Non-private method cannot be aliased. Found " + v3.name);
            }
            a2.name = v2.renameTo(v3.name);
        }
        return true;
    }
    
    private void conformVisibility(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2, final SpecialMethod a3, final MethodNode a4) {
        final Bytecode.Visibility v1 = Bytecode.getVisibility(a4);
        final Bytecode.Visibility v2 = Bytecode.getVisibility(a2);
        if (v2.ordinal() >= v1.ordinal()) {
            if (v1 == Bytecode.Visibility.PRIVATE && v2.ordinal() > Bytecode.Visibility.PRIVATE.ordinal()) {
                a1.getTarget().addUpgradedMethod(a4);
            }
            return;
        }
        final String v3 = String.format("%s %s method %s in %s cannot reduce visibiliy of %s target method", v2, a3, a2.name, this.mixin, v1);
        if (a3.isOverwrite && !this.mixin.getParent().conformOverwriteVisibility()) {
            throw new InvalidMixinException(this.mixin, v3);
        }
        if (v2 == Bytecode.Visibility.PRIVATE) {
            if (a3.isOverwrite) {
                MixinPreProcessorStandard.logger.warn("Static binding violation: {}, visibility will be upgraded.", new Object[] { v3 });
            }
            a1.addUpgradedMethod(a2);
            Bytecode.setVisibility(a2, v1);
        }
    }
    
    protected ClassInfo.Method getSpecialMethod(final MixinInfo.MixinMethodNode a1, final SpecialMethod a2) {
        final ClassInfo.Method v1 = this.mixin.getClassInfo().findMethod(a1, 10);
        this.checkMethodNotUnique(v1, a2);
        return v1;
    }
    
    protected void checkMethodNotUnique(final ClassInfo.Method a1, final SpecialMethod a2) {
        if (a1.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s in %s cannot be @Unique", a2, a1.getName(), this.mixin));
        }
    }
    
    protected void checkMixinNotUnique(final MixinInfo.MixinMethodNode a1, final SpecialMethod a2) {
        if (this.mixin.isUnique()) {
            throw new InvalidMixinException(this.mixin, String.format("%s method %s found in a @Unique mixin %s", a2, a1.name, this.mixin));
        }
    }
    
    protected boolean attachUniqueMethod(final MixinTargetContext v2, final MixinInfo.MixinMethodNode v3) {
        final ClassInfo.Method v4 = this.mixin.getClassInfo().findMethod(v3, 10);
        if (v4 == null || (!v4.isUnique() && !this.mixin.isUnique() && !v4.isSynthetic())) {
            return false;
        }
        if (v4.isSynthetic()) {
            v2.transformDescriptor(v3);
            v4.remapTo(v3.desc);
        }
        final MethodNode v5 = v2.findMethod(v3, null);
        if (v5 == null) {
            return false;
        }
        final String v6 = v4.isSynthetic() ? "synthetic" : "@Unique";
        if (Bytecode.getVisibility(v3).ordinal() < Bytecode.Visibility.PUBLIC.ordinal()) {
            final String a1 = v2.getUniqueName(v3, false);
            MixinPreProcessorStandard.logger.log(this.mixin.getLoggingLevel(), "Renaming {} method {}{} to {} in {}", new Object[] { v6, v3.name, v3.desc, a1, this.mixin });
            v3.name = v4.renameTo(a1);
            return false;
        }
        if (this.strictUnique) {
            throw new InvalidMixinException(this.mixin, String.format("Method conflict, %s method %s in %s cannot overwrite %s%s in %s", v6, v3.name, this.mixin, v5.name, v5.desc, v2.getTarget()));
        }
        final AnnotationNode v7 = Annotations.getVisible(v3, Unique.class);
        if (v7 == null || !Annotations.getValue(v7, "silent", Boolean.FALSE)) {
            if (Bytecode.hasFlag(v3, 64)) {
                try {
                    Bytecode.compareBridgeMethods(v5, v3);
                    MixinPreProcessorStandard.logger.debug("Discarding sythetic bridge method {} in {} because existing method in {} is compatible", new Object[] { v6, v3.name, this.mixin, v2.getTarget() });
                    return true;
                }
                catch (SyntheticBridgeException a2) {
                    if (this.verboseLogging || this.env.getOption(MixinEnvironment.Option.DEBUG_VERIFY)) {
                        a2.printAnalysis(v2, v5, v3);
                    }
                    throw new InvalidMixinException(this.mixin, a2.getMessage());
                }
            }
            MixinPreProcessorStandard.logger.warn("Discarding {} public method {} in {} because it already exists in {}", new Object[] { v6, v3.name, this.mixin, v2.getTarget() });
            return true;
        }
        v2.addMixinMethod(v3);
        return true;
    }
    
    protected void attachMethod(final MixinTargetContext a1, final MixinInfo.MixinMethodNode a2) {
        final ClassInfo.Method v1 = this.mixin.getClassInfo().findMethod(a2);
        if (v1 == null) {
            return;
        }
        final ClassInfo.Method v2 = this.mixin.getClassInfo().findMethodInHierarchy(a2, ClassInfo.SearchType.SUPER_CLASSES_ONLY);
        if (v2 != null && v2.isRenamed()) {
            a2.name = v1.renameTo(v2.getName());
        }
        final MethodNode v3 = a1.findMethod(a2, null);
        if (v3 != null) {
            this.conformVisibility(a1, a2, SpecialMethod.MERGE, v3);
        }
    }
    
    protected void attachFields(final MixinTargetContext v-7) {
        final Iterator<FieldNode> iterator = this.classNode.fields.iterator();
        while (iterator.hasNext()) {
            final FieldNode fieldNode = iterator.next();
            final AnnotationNode visible = Annotations.getVisible(fieldNode, Shadow.class);
            final boolean b = visible != null;
            if (!this.validateField(v-7, fieldNode, visible)) {
                iterator.remove();
            }
            else {
                final ClassInfo.Field field = this.mixin.getClassInfo().findField(fieldNode);
                v-7.transformDescriptor(fieldNode);
                field.remapTo(fieldNode.desc);
                if (field.isUnique() && b) {
                    throw new InvalidMixinException(this.mixin, String.format("@Shadow field %s cannot be @Unique", fieldNode.name));
                }
                FieldNode fieldNode2 = v-7.findField(fieldNode, visible);
                if (fieldNode2 == null) {
                    if (visible == null) {
                        continue;
                    }
                    fieldNode2 = v-7.findRemappedField(fieldNode);
                    if (fieldNode2 == null) {
                        throw new InvalidMixinException(this.mixin, String.format("Shadow field %s was not located in the target class %s. %s%s", fieldNode.name, v-7.getTarget(), v-7.getReferenceMapper().getStatus(), getDynamicInfo(fieldNode)));
                    }
                    fieldNode.name = field.renameTo(fieldNode2.name);
                }
                if (!Bytecode.compareFlags(fieldNode, fieldNode2, 8)) {
                    throw new InvalidMixinException(this.mixin, String.format("STATIC modifier of @Shadow field %s in %s does not match the target", fieldNode.name, this.mixin));
                }
                if (field.isUnique()) {
                    if ((fieldNode.access & 0x6) != 0x0) {
                        final String a1 = v-7.getUniqueName(fieldNode);
                        MixinPreProcessorStandard.logger.log(this.mixin.getLoggingLevel(), "Renaming @Unique field {}{} to {} in {}", new Object[] { fieldNode.name, fieldNode.desc, a1, this.mixin });
                        fieldNode.name = field.renameTo(a1);
                    }
                    else {
                        if (this.strictUnique) {
                            throw new InvalidMixinException(this.mixin, String.format("Field conflict, @Unique field %s in %s cannot overwrite %s%s in %s", fieldNode.name, this.mixin, fieldNode2.name, fieldNode2.desc, v-7.getTarget()));
                        }
                        MixinPreProcessorStandard.logger.warn("Discarding @Unique public field {} in {} because it already exists in {}. Note that declared FIELD INITIALISERS will NOT be removed!", new Object[] { fieldNode.name, this.mixin, v-7.getTarget() });
                        iterator.remove();
                    }
                }
                else {
                    if (!fieldNode2.desc.equals(fieldNode.desc)) {
                        throw new InvalidMixinException(this.mixin, String.format("The field %s in the target class has a conflicting signature", fieldNode.name));
                    }
                    if (!fieldNode2.name.equals(fieldNode.name)) {
                        if ((fieldNode2.access & 0x2) == 0x0 && (fieldNode2.access & 0x1000) == 0x0) {
                            throw new InvalidMixinException(this.mixin, "Non-private field cannot be aliased. Found " + fieldNode2.name);
                        }
                        fieldNode.name = field.renameTo(fieldNode2.name);
                    }
                    iterator.remove();
                    if (!b) {
                        continue;
                    }
                    final boolean v0 = field.isDecoratedFinal();
                    if (this.verboseLogging && Bytecode.hasFlag(fieldNode2, 16) != v0) {
                        final String v2 = v0 ? "@Shadow field {}::{} is decorated with @Final but target is not final" : "@Shadow target {}::{} is final but shadow is not decorated with @Final";
                        MixinPreProcessorStandard.logger.warn(v2, new Object[] { this.mixin, fieldNode.name });
                    }
                    v-7.addShadowField(fieldNode, field);
                }
            }
        }
    }
    
    protected boolean validateField(final MixinTargetContext a1, final FieldNode a2, final AnnotationNode a3) {
        if (Bytecode.hasFlag(a2, 8) && !Bytecode.hasFlag(a2, 2) && !Bytecode.hasFlag(a2, 4096) && a3 == null) {
            throw new InvalidMixinException(a1, String.format("Mixin %s contains non-private static field %s:%s", a1, a2.name, a2.desc));
        }
        final String v1 = Annotations.getValue(a3, "prefix", (Class<?>)Shadow.class);
        if (a2.name.startsWith(v1)) {
            throw new InvalidMixinException(a1, String.format("@Shadow field %s.%s has a shadow prefix. This is not allowed.", a1, a2.name));
        }
        if (!"super$".equals(a2.name)) {
            return true;
        }
        if (a2.access != 2) {
            throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must be private and non-final", a1, a2.name));
        }
        if (!a2.desc.equals("L" + this.mixin.getClassRef() + ";")) {
            throw new InvalidMixinException(this.mixin, String.format("Imaginary super field %s.%s must have the same type as the parent mixin (%s)", a1, a2.name, this.mixin.getClassName()));
        }
        return false;
    }
    
    protected void transform(final MixinTargetContext v-2) {
        for (final MethodNode v0 : this.classNode.methods) {
            for (final AbstractInsnNode a1 : v0.instructions) {
                if (a1 instanceof MethodInsnNode) {
                    this.transformMethod((MethodInsnNode)a1);
                }
                else {
                    if (!(a1 instanceof FieldInsnNode)) {
                        continue;
                    }
                    this.transformField((FieldInsnNode)a1);
                }
            }
        }
    }
    
    protected void transformMethod(final MethodInsnNode a1) {
        final Profiler.Section v1 = this.profiler.begin("meta");
        final ClassInfo v2 = ClassInfo.forName(a1.owner);
        if (v2 == null) {
            throw new RuntimeException(new ClassNotFoundException(a1.owner.replace('/', '.')));
        }
        final ClassInfo.Method v3 = v2.findMethodInHierarchy(a1, ClassInfo.SearchType.ALL_CLASSES, 2);
        v1.end();
        if (v3 != null && v3.isRenamed()) {
            a1.name = v3.getName();
        }
    }
    
    protected void transformField(final FieldInsnNode a1) {
        final Profiler.Section v1 = this.profiler.begin("meta");
        final ClassInfo v2 = ClassInfo.forName(a1.owner);
        if (v2 == null) {
            throw new RuntimeException(new ClassNotFoundException(a1.owner.replace('/', '.')));
        }
        final ClassInfo.Field v3 = v2.findField(a1, 2);
        v1.end();
        if (v3 != null && v3.isRenamed()) {
            a1.name = v3.getName();
        }
    }
    
    protected static String getDynamicInfo(final MethodNode a1) {
        return getDynamicInfo("Method", Annotations.getInvisible(a1, Dynamic.class));
    }
    
    protected static String getDynamicInfo(final FieldNode a1) {
        return getDynamicInfo("Field", Annotations.getInvisible(a1, Dynamic.class));
    }
    
    private static String getDynamicInfo(final String a1, final AnnotationNode a2) {
        String v1 = Strings.nullToEmpty((String)Annotations.getValue(a2));
        final Type v2 = Annotations.getValue(a2, "mixin");
        if (v2 != null) {
            v1 = String.format("{%s} %s", v2.getClassName(), v1).trim();
        }
        return (v1.length() > 0) ? String.format(" %s is @Dynamic(%s)", a1, v1) : "";
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    enum SpecialMethod
    {
        MERGE(true), 
        OVERWRITE(true, (Class<? extends Annotation>)Overwrite.class), 
        SHADOW(false, (Class<? extends Annotation>)Shadow.class), 
        ACCESSOR(false, (Class<? extends Annotation>)Accessor.class), 
        INVOKER(false, (Class<? extends Annotation>)Invoker.class);
        
        final boolean isOverwrite;
        final Class<? extends Annotation> annotation;
        final String description;
        private static final /* synthetic */ SpecialMethod[] $VALUES;
        
        public static SpecialMethod[] values() {
            return SpecialMethod.$VALUES.clone();
        }
        
        public static SpecialMethod valueOf(final String a1) {
            return Enum.valueOf(SpecialMethod.class, a1);
        }
        
        private SpecialMethod(final boolean a1, final Class<? extends Annotation> a2) {
            this.isOverwrite = a1;
            this.annotation = a2;
            this.description = "@" + Bytecode.getSimpleName(a2);
        }
        
        private SpecialMethod(final boolean a1) {
            this.isOverwrite = a1;
            this.annotation = null;
            this.description = "overwrite";
        }
        
        @Override
        public String toString() {
            return this.description;
        }
        
        static {
            $VALUES = new SpecialMethod[] { SpecialMethod.MERGE, SpecialMethod.OVERWRITE, SpecialMethod.SHADOW, SpecialMethod.ACCESSOR, SpecialMethod.INVOKER };
        }
    }
}
