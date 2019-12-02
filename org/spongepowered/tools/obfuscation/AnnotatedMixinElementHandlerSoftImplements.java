package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.tools.*;
import org.spongepowered.asm.mixin.*;
import javax.lang.model.type.*;
import java.util.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

public class AnnotatedMixinElementHandlerSoftImplements extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerSoftImplements(final IMixinAnnotationProcessor a1, final AnnotatedMixin a2) {
        super(a1, a2);
    }
    
    public void process(final AnnotationHandle v-5) {
        if (!this.mixin.remap()) {
            return;
        }
        final List<AnnotationHandle> annotationList = v-5.getAnnotationList("value");
        if (annotationList.size() < 1) {
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Empty @Implements annotation", this.mixin.getMixin(), v-5.asMirror());
            return;
        }
        for (final AnnotationHandle annotationHandle : annotationList) {
            final Interface.Remap v3 = annotationHandle.getValue("remap", Interface.Remap.ALL);
            if (v3 == Interface.Remap.NONE) {
                continue;
            }
            try {
                final TypeHandle a1 = new TypeHandle(annotationHandle.getValue("iface"));
                final String v1 = annotationHandle.getValue("prefix");
                this.processSoftImplements(v3, a1, v1);
            }
            catch (Exception v2) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected error: " + v2.getClass().getName() + ": " + v2.getMessage(), this.mixin.getMixin(), annotationHandle.asMirror());
            }
        }
    }
    
    private void processSoftImplements(final Interface.Remap v1, final TypeHandle v2, final String v3) {
        for (final ExecutableElement a1 : v2.getEnclosedElements(ElementKind.METHOD)) {
            this.processMethod(v1, v2, v3, a1);
        }
        for (final TypeHandle a2 : v2.getInterfaces()) {
            this.processSoftImplements(v1, a2, v3);
        }
    }
    
    private void processMethod(final Interface.Remap a4, final TypeHandle v1, final String v2, final ExecutableElement v3) {
        final String v4 = v3.getSimpleName().toString();
        final String v5 = TypeUtils.getJavaSignature(v3);
        final String v6 = TypeUtils.getDescriptor(v3);
        if (a4 != Interface.Remap.ONLY_PREFIXED) {
            final MethodHandle a5 = this.mixin.getHandle().findMethod(v4, v5);
            if (a5 != null) {
                this.addInterfaceMethodMapping(a4, v1, null, a5, v4, v6);
            }
        }
        if (v2 != null) {
            final MethodHandle a6 = this.mixin.getHandle().findMethod(v2 + v4, v5);
            if (a6 != null) {
                this.addInterfaceMethodMapping(a4, v1, v2, a6, v4, v6);
            }
        }
    }
    
    private void addInterfaceMethodMapping(final Interface.Remap a1, final TypeHandle a2, final String a3, final MethodHandle a4, final String a5, final String a6) {
        final MappingMethod v1 = new MappingMethod(a2.getName(), a5, a6);
        final ObfuscationData<MappingMethod> v2 = this.obf.getDataProvider().getObfMethod(v1);
        if (v2.isEmpty()) {
            if (a1.forceRemap()) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, "No obfuscation mapping for soft-implementing method", a4.getElement());
            }
            return;
        }
        this.addMethodMappings(a4.getName(), a6, this.applyPrefix(v2, a3));
    }
    
    private ObfuscationData<MappingMethod> applyPrefix(final ObfuscationData<MappingMethod> v2, final String v3) {
        if (v3 == null) {
            return v2;
        }
        final ObfuscationData<MappingMethod> v4 = new ObfuscationData<MappingMethod>();
        for (final ObfuscationType a2 : v2) {
            final MappingMethod a3 = v2.get(a2);
            v4.put(a2, a3.addPrefix(v3));
        }
        return v4;
    }
}
