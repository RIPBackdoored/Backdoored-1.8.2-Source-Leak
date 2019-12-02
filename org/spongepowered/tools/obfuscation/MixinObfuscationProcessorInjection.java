package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.util.*;

@SupportedAnnotationTypes({ "org.spongepowered.asm.mixin.injection.Inject", "org.spongepowered.asm.mixin.injection.ModifyArg", "org.spongepowered.asm.mixin.injection.ModifyArgs", "org.spongepowered.asm.mixin.injection.Redirect", "org.spongepowered.asm.mixin.injection.At" })
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor
{
    public MixinObfuscationProcessorInjection() {
        super();
    }
    
    @Override
    public boolean process(final Set<? extends TypeElement> a1, final RoundEnvironment a2) {
        if (a2.processingOver()) {
            this.postProcess(a2);
            return true;
        }
        this.processMixins(a2);
        this.processInjectors(a2, Inject.class);
        this.processInjectors(a2, ModifyArg.class);
        this.processInjectors(a2, ModifyArgs.class);
        this.processInjectors(a2, Redirect.class);
        this.processInjectors(a2, ModifyVariable.class);
        this.processInjectors(a2, ModifyConstant.class);
        this.postProcess(a2);
        return true;
    }
    
    @Override
    protected void postProcess(final RoundEnvironment v2) {
        super.postProcess(v2);
        try {
            this.mixins.writeReferences();
        }
        catch (Exception a1) {
            a1.printStackTrace();
        }
    }
    
    private void processInjectors(final RoundEnvironment v-2, final Class<? extends Annotation> v-1) {
        for (final Element v1 : v-2.getElementsAnnotatedWith(v-1)) {
            final Element a1 = v1.getEnclosingElement();
            if (!(a1 instanceof TypeElement)) {
                throw new IllegalStateException("@" + v-1.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(a1));
            }
            final AnnotationHandle a2 = AnnotationHandle.of(v1, v-1);
            if (v1.getKind() == ElementKind.METHOD) {
                this.mixins.registerInjector((TypeElement)a1, (ExecutableElement)v1, a2);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.WARNING, "Found an @" + v-1.getSimpleName() + " annotation on an element which is not a method: " + v1.toString());
            }
        }
    }
}
