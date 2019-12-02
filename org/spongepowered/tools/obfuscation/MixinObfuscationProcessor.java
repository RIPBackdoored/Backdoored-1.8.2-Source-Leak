package org.spongepowered.tools.obfuscation;

import javax.annotation.processing.*;
import org.spongepowered.asm.mixin.*;
import java.lang.annotation.*;
import javax.lang.model.element.*;
import javax.tools.*;
import javax.lang.model.*;
import java.util.*;

public abstract class MixinObfuscationProcessor extends AbstractProcessor
{
    protected AnnotatedMixins mixins;
    
    public MixinObfuscationProcessor() {
        super();
    }
    
    @Override
    public synchronized void init(final ProcessingEnvironment a1) {
        super.init(a1);
        this.mixins = AnnotatedMixins.getMixinsForEnvironment(a1);
    }
    
    protected void processMixins(final RoundEnvironment v2) {
        this.mixins.onPassStarted();
        for (final Element a1 : v2.getElementsAnnotatedWith(Mixin.class)) {
            if (a1.getKind() == ElementKind.CLASS || a1.getKind() == ElementKind.INTERFACE) {
                this.mixins.registerMixin((TypeElement)a1);
            }
            else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Mixin annotation on an element which is not a class or interface", a1);
            }
        }
    }
    
    protected void postProcess(final RoundEnvironment a1) {
        this.mixins.onPassCompleted(a1);
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        try {
            return SourceVersion.valueOf("RELEASE_8");
        }
        catch (IllegalArgumentException ex) {
            return super.getSupportedSourceVersion();
        }
    }
    
    @Override
    public Set<String> getSupportedOptions() {
        return SupportedOptions.getAllOptions();
    }
}
