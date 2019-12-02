package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.tools.*;
import java.util.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import java.lang.reflect.*;

class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerOverwrite(final IMixinAnnotationProcessor a1, final AnnotatedMixin a2) {
        super(a1, a2);
    }
    
    public void registerMerge(final ExecutableElement a1) {
        this.validateTargetMethod(a1, null, new AliasedElementName(a1, AnnotationHandle.MISSING), "overwrite", true, true);
    }
    
    public void registerOverwrite(final AnnotatedElementOverwrite v-1) {
        final AliasedElementName v0 = new AliasedElementName(v-1.getElement(), v-1.getAnnotation());
        this.validateTargetMethod((ExecutableElement)v-1.getElement(), v-1.getAnnotation(), v0, "@Overwrite", true, false);
        this.checkConstraints((ExecutableElement)v-1.getElement(), v-1.getAnnotation());
        if (v-1.shouldRemap()) {
            for (final TypeHandle a1 : this.mixin.getTargets()) {
                if (!this.registerOverwriteForTarget(v-1, a1)) {
                    return;
                }
            }
        }
        if (!"true".equalsIgnoreCase(this.ap.getOption("disableOverwriteChecker"))) {
            final Diagnostic.Kind v2 = "error".equalsIgnoreCase(this.ap.getOption("overwriteErrorLevel")) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            final String v3 = this.ap.getJavadocProvider().getJavadoc(v-1.getElement());
            if (v3 == null) {
                this.ap.printMessage(v2, "@Overwrite is missing javadoc comment", v-1.getElement());
                return;
            }
            if (!v3.toLowerCase().contains("@author")) {
                this.ap.printMessage(v2, "@Overwrite is missing an @author tag", v-1.getElement());
            }
            if (!v3.toLowerCase().contains("@reason")) {
                this.ap.printMessage(v2, "@Overwrite is missing an @reason tag", v-1.getElement());
            }
        }
    }
    
    private boolean registerOverwriteForTarget(final AnnotatedElementOverwrite v-3, final TypeHandle v-2) {
        final MappingMethod mappingMethod = v-2.getMappingMethod(v-3.getSimpleName(), v-3.getDesc());
        final ObfuscationData<MappingMethod> v0 = this.obf.getDataProvider().getObfMethod(mappingMethod);
        if (v0.isEmpty()) {
            Diagnostic.Kind a2 = Diagnostic.Kind.ERROR;
            try {
                final Method a3 = ((ExecutableElement)v-3.getElement()).getClass().getMethod("isStatic", (Class<?>[])new Class[0]);
                if (a3.invoke(v-3.getElement(), new Object[0])) {
                    a2 = Diagnostic.Kind.WARNING;
                }
            }
            catch (Exception ex) {}
            this.ap.printMessage(a2, "No obfuscation mapping for @Overwrite method", v-3.getElement());
            return false;
        }
        try {
            this.addMethodMappings(v-3.getSimpleName(), v-3.getDesc(), v0);
        }
        catch (Mappings.MappingConflictException v2) {
            v-3.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + v2.getNew().getSimpleName() + " for target " + v-2 + " conflicts with existing mapping " + v2.getOld().getSimpleName());
            return false;
        }
        return true;
    }
    
    static class AnnotatedElementOverwrite extends AnnotatedElement<ExecutableElement>
    {
        private final boolean shouldRemap;
        
        public AnnotatedElementOverwrite(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
            super((Element)a1, a2);
            this.shouldRemap = a3;
        }
        
        public boolean shouldRemap() {
            return this.shouldRemap;
        }
    }
}
