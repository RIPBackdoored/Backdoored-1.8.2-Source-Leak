package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import java.lang.annotation.*;
import javax.tools.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import java.util.*;
import org.spongepowered.asm.mixin.gen.*;
import org.spongepowered.asm.mixin.*;

@SupportedAnnotationTypes({ "org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements" })
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor
{
    public MixinObfuscationProcessorTargets() {
        super();
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> a1, final RoundEnvironment a2) {
        if (a2.processingOver()) {
            this.postProcess(a2);
            return true;
        }
        this.processMixins(a2);
        this.processShadows(a2);
        this.processOverwrites(a2);
        this.processAccessors(a2);
        this.processInvokers(a2);
        this.processImplements(a2);
        this.postProcess(a2);
        return true;
    }
    
    @Override
    protected void postProcess(final RoundEnvironment v2) {
        super.postProcess(v2);
        try {
            this.mixins.writeReferences();
            this.mixins.writeMappings();
        }
        catch (Exception a1) {
            a1.printStackTrace();
        }
    }
    
    private void processShadows(final RoundEnvironment v-3) {
        for (final Element a2 : v-3.getElementsAnnotatedWith(Shadow.class)) {
            final Element a1 = a2.getEnclosingElement();
            if (!(a1 instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a1), a2);
            }
            else {
                final AnnotationHandle v1 = AnnotationHandle.of(a2, Shadow.class);
                if (a2.getKind() == ElementKind.FIELD) {
                    this.mixins.registerShadow((TypeElement)a1, (VariableElement)a2, v1);
                }
                else if (a2.getKind() == ElementKind.METHOD) {
                    this.mixins.registerShadow((TypeElement)a1, (ExecutableElement)a2, v1);
                }
                else {
                    this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method or field", a2);
                }
            }
        }
    }
    
    private void processOverwrites(final RoundEnvironment v-1) {
        for (final Element v1 : v-1.getElementsAnnotatedWith(Overwrite.class)) {
            final Element a1 = v1.getEnclosingElement();
            if (!(a1 instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a1), v1);
            }
            else if (v1.getKind() == ElementKind.METHOD) {
                this.mixins.registerOverwrite((TypeElement)a1, (ExecutableElement)v1);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", v1);
            }
        }
    }
    
    private void processAccessors(final RoundEnvironment v-1) {
        for (final Element v1 : v-1.getElementsAnnotatedWith(Accessor.class)) {
            final Element a1 = v1.getEnclosingElement();
            if (!(a1 instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a1), v1);
            }
            else if (v1.getKind() == ElementKind.METHOD) {
                this.mixins.registerAccessor((TypeElement)a1, (ExecutableElement)v1);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", v1);
            }
        }
    }
    
    private void processInvokers(final RoundEnvironment v-1) {
        for (final Element v1 : v-1.getElementsAnnotatedWith(Invoker.class)) {
            final Element a1 = v1.getEnclosingElement();
            if (!(a1 instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(a1), v1);
            }
            else if (v1.getKind() == ElementKind.METHOD) {
                this.mixins.registerInvoker((TypeElement)a1, (ExecutableElement)v1);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", v1);
            }
        }
    }
    
    private void processImplements(final RoundEnvironment v-1) {
        for (final Element v1 : v-1.getElementsAnnotatedWith(Implements.class)) {
            if (v1.getKind() == ElementKind.CLASS || v1.getKind() == ElementKind.INTERFACE) {
                final AnnotationHandle a1 = AnnotationHandle.of(v1, Implements.class);
                this.mixins.registerSoftImplements((TypeElement)v1, a1);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", v1);
            }
        }
    }
}
