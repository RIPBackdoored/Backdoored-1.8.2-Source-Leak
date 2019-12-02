package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import java.util.*;
import javax.lang.model.type.*;
import javax.tools.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.struct.*;

class AnnotatedMixin
{
    private final AnnotationHandle annotation;
    private final Messager messager;
    private final ITypeHandleProvider typeProvider;
    private final IObfuscationManager obf;
    private final IMappingConsumer mappings;
    private final TypeElement mixin;
    private final List<ExecutableElement> methods;
    private final TypeHandle handle;
    private final List<TypeHandle> targets;
    private final TypeHandle primaryTarget;
    private final String classRef;
    private final boolean remap;
    private final boolean virtual;
    private final AnnotatedMixinElementHandlerOverwrite overwrites;
    private final AnnotatedMixinElementHandlerShadow shadows;
    private final AnnotatedMixinElementHandlerInjector injectors;
    private final AnnotatedMixinElementHandlerAccessor accessors;
    private final AnnotatedMixinElementHandlerSoftImplements softImplements;
    private boolean validated;
    
    public AnnotatedMixin(final IMixinAnnotationProcessor a1, final TypeElement a2) {
        super();
        this.targets = new ArrayList<TypeHandle>();
        this.validated = false;
        this.typeProvider = a1.getTypeProvider();
        this.obf = a1.getObfuscationManager();
        this.mappings = this.obf.createMappingConsumer();
        this.messager = a1;
        this.mixin = a2;
        this.handle = new TypeHandle(a2);
        this.methods = new ArrayList<ExecutableElement>((Collection<? extends ExecutableElement>)this.handle.getEnclosedElements(ElementKind.METHOD));
        this.virtual = this.handle.getAnnotation(Pseudo.class).exists();
        this.annotation = this.handle.getAnnotation(Mixin.class);
        this.classRef = TypeUtils.getInternalName(a2);
        this.primaryTarget = this.initTargets();
        this.remap = (this.annotation.getBoolean("remap", true) && this.targets.size() > 0);
        this.overwrites = new AnnotatedMixinElementHandlerOverwrite(a1, this);
        this.shadows = new AnnotatedMixinElementHandlerShadow(a1, this);
        this.injectors = new AnnotatedMixinElementHandlerInjector(a1, this);
        this.accessors = new AnnotatedMixinElementHandlerAccessor(a1, this);
        this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(a1, this);
    }
    
    AnnotatedMixin runValidators(final IMixinValidator.ValidationPass v1, final Collection<IMixinValidator> v2) {
        for (final IMixinValidator a1 : v2) {
            if (!a1.validate(v1, this.mixin, this.annotation, this.targets)) {
                break;
            }
        }
        if (v1 == IMixinValidator.ValidationPass.FINAL && !this.validated) {
            this.validated = true;
            this.runFinalValidation();
        }
        return this;
    }
    
    private TypeHandle initTargets() {
        TypeHandle typeHandle = null;
        try {
            for (final TypeMirror v0 : this.annotation.getList()) {
                final TypeHandle v2 = new TypeHandle((DeclaredType)v0);
                if (this.targets.contains(v2)) {
                    continue;
                }
                this.addTarget(v2);
                if (typeHandle != null) {
                    continue;
                }
                typeHandle = v2;
            }
        }
        catch (Exception ex) {
            this.printMessage(Diagnostic.Kind.WARNING, "Error processing public targets: " + ex.getClass().getName() + ": " + ex.getMessage(), this);
        }
        try {
            for (final String v3 : this.annotation.getList("targets")) {
                TypeHandle v2 = this.typeProvider.getTypeHandle(v3);
                if (this.targets.contains(v2)) {
                    continue;
                }
                if (this.virtual) {
                    v2 = this.typeProvider.getSimulatedHandle(v3, this.mixin.asType());
                }
                else {
                    if (v2 == null) {
                        this.printMessage(Diagnostic.Kind.ERROR, "Mixin target " + v3 + " could not be found", this);
                        return null;
                    }
                    if (v2.isPublic()) {
                        this.printMessage(Diagnostic.Kind.WARNING, "Mixin target " + v3 + " is public and must be specified in value", this);
                        return null;
                    }
                }
                this.addSoftTarget(v2, v3);
                if (typeHandle != null) {
                    continue;
                }
                typeHandle = v2;
            }
        }
        catch (Exception ex) {
            this.printMessage(Diagnostic.Kind.WARNING, "Error processing private targets: " + ex.getClass().getName() + ": " + ex.getMessage(), this);
        }
        if (typeHandle == null) {
            this.printMessage(Diagnostic.Kind.ERROR, "Mixin has no targets", this);
        }
        return typeHandle;
    }
    
    private void printMessage(final Diagnostic.Kind a1, final CharSequence a2, final AnnotatedMixin a3) {
        this.messager.printMessage(a1, a2, this.mixin, this.annotation.asMirror());
    }
    
    private void addSoftTarget(final TypeHandle a1, final String a2) {
        final ObfuscationData<String> v1 = this.obf.getDataProvider().getObfClass(a1);
        if (!v1.isEmpty()) {
            this.obf.getReferenceManager().addClassMapping(this.classRef, a2, v1);
        }
        this.addTarget(a1);
    }
    
    private void addTarget(final TypeHandle a1) {
        this.targets.add(a1);
    }
    
    @Override
    public String toString() {
        return this.mixin.getSimpleName().toString();
    }
    
    public AnnotationHandle getAnnotation() {
        return this.annotation;
    }
    
    public TypeElement getMixin() {
        return this.mixin;
    }
    
    public TypeHandle getHandle() {
        return this.handle;
    }
    
    public String getClassRef() {
        return this.classRef;
    }
    
    public boolean isInterface() {
        return this.mixin.getKind() == ElementKind.INTERFACE;
    }
    
    @Deprecated
    public TypeHandle getPrimaryTarget() {
        return this.primaryTarget;
    }
    
    public List<TypeHandle> getTargets() {
        return this.targets;
    }
    
    public boolean isMultiTarget() {
        return this.targets.size() > 1;
    }
    
    public boolean remap() {
        return this.remap;
    }
    
    public IMappingConsumer getMappings() {
        return this.mappings;
    }
    
    private void runFinalValidation() {
        for (final ExecutableElement v1 : this.methods) {
            this.overwrites.registerMerge(v1);
        }
    }
    
    public void registerOverwrite(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
        this.methods.remove(a1);
        this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(a1, a2, a3));
    }
    
    public void registerShadow(final VariableElement a1, final AnnotationHandle a2, final boolean a3) {
        this.shadows.registerShadow(this.shadows.new AnnotatedElementShadowField(a1, a2, a3));
    }
    
    public void registerShadow(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
        this.methods.remove(a1);
        this.shadows.registerShadow(this.shadows.new AnnotatedElementShadowMethod(a1, a2, a3));
    }
    
    public void registerInjector(final ExecutableElement v-8, final AnnotationHandle v-7, final InjectorRemap v-6) {
        this.methods.remove(v-8);
        this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(v-8, v-7, v-6));
        final List<AnnotationHandle> annotationList = v-7.getAnnotationList("at");
        for (final AnnotationHandle a1 : annotationList) {
            this.registerInjectionPoint(v-8, v-7, a1, v-6, "@At(%s)");
        }
        final List<AnnotationHandle> annotationList2 = v-7.getAnnotationList("slice");
        for (final AnnotationHandle annotationHandle : annotationList2) {
            final String a2 = annotationHandle.getValue("id", "");
            final AnnotationHandle a3 = annotationHandle.getAnnotation("from");
            if (a3 != null) {
                this.registerInjectionPoint(v-8, v-7, a3, v-6, "@Slice[" + a2 + "](from=@At(%s))");
            }
            final AnnotationHandle v1 = annotationHandle.getAnnotation("to");
            if (v1 != null) {
                this.registerInjectionPoint(v-8, v-7, v1, v-6, "@Slice[" + a2 + "](to=@At(%s))");
            }
        }
    }
    
    public void registerInjectionPoint(final ExecutableElement a1, final AnnotationHandle a2, final AnnotationHandle a3, final InjectorRemap a4, final String a5) {
        this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(a1, a2, a3, a4), a5);
    }
    
    public void registerAccessor(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
        this.methods.remove(a1);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(a1, a2, a3));
    }
    
    public void registerInvoker(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
        this.methods.remove(a1);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(a1, a2, a3));
    }
    
    public void registerSoftImplements(final AnnotationHandle a1) {
        this.softImplements.process(a1);
    }
}
