package org.spongepowered.tools.obfuscation;

import java.util.*;
import javax.tools.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import javax.lang.model.element.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;

class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerShadow(final IMixinAnnotationProcessor a1, final AnnotatedMixin a2) {
        super(a1, a2);
    }
    
    public void registerShadow(final AnnotatedElementShadow<?, ?> v2) {
        this.validateTarget(v2.getElement(), v2.getAnnotation(), v2.getName(), "@Shadow");
        if (!v2.shouldRemap()) {
            return;
        }
        for (final TypeHandle a1 : this.mixin.getTargets()) {
            this.registerShadowForTarget(v2, a1);
        }
    }
    
    private void registerShadowForTarget(final AnnotatedElementShadow<?, ?> v-3, final TypeHandle v-2) {
        final ObfuscationData<? extends IMapping<?>> obfuscationData = (ObfuscationData<? extends IMapping<?>>)v-3.getObfuscationData(this.obf.getDataProvider(), v-2);
        if (obfuscationData.isEmpty()) {
            final String a1 = this.mixin.isMultiTarget() ? (" in target " + v-2) : "";
            if (v-2.isSimulated()) {
                v-3.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + a1 + " for @Shadow " + v-3);
            }
            else {
                v-3.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + a1 + " for @Shadow " + v-3);
            }
            return;
        }
        for (final ObfuscationType v1 : obfuscationData) {
            try {
                v-3.addMapping(v1, (IMapping<?>)obfuscationData.get(v1));
            }
            catch (Mappings.MappingConflictException a2) {
                v-3.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + v-3 + ": " + a2.getNew().getSimpleName() + " for target " + v-2 + " conflicts with existing mapping " + a2.getOld().getSimpleName());
            }
        }
    }
    
    abstract static class AnnotatedElementShadow<E extends java.lang.Object, M extends java.lang.Object> extends AnnotatedElement<E>
    {
        private final boolean shouldRemap;
        private final ShadowElementName name;
        private final IMapping.Type type;
        
        protected AnnotatedElementShadow(final E a1, final AnnotationHandle a2, final boolean a3, final IMapping.Type a4) {
            super((Element)a1, a2);
            this.shouldRemap = a3;
            this.name = new ShadowElementName((Element)a1, a2);
            this.type = a4;
        }
        
        public boolean shouldRemap() {
            return this.shouldRemap;
        }
        
        public ShadowElementName getName() {
            return this.name;
        }
        
        public IMapping.Type getElementType() {
            return this.type;
        }
        
        @Override
        public String toString() {
            return this.getElementType().name().toLowerCase();
        }
        
        public ShadowElementName setObfuscatedName(final IMapping<?> a1) {
            return this.setObfuscatedName(a1.getSimpleName());
        }
        
        public ShadowElementName setObfuscatedName(final String a1) {
            return this.getName().setObfuscatedName(a1);
        }
        
        public ObfuscationData<M> getObfuscationData(final IObfuscationDataProvider a1, final TypeHandle a2) {
            return a1.getObfEntry((IMapping<M>)this.getMapping(a2, this.getName().toString(), this.getDesc()));
        }
        
        public abstract M getMapping(final TypeHandle p0, final String p1, final String p2);
        
        public abstract void addMapping(final ObfuscationType p0, final IMapping<?> p1);
    }
    
    class AnnotatedElementShadowField extends AnnotatedElementShadow<VariableElement, MappingField>
    {
        final /* synthetic */ AnnotatedMixinElementHandlerShadow this$0;
        
        public AnnotatedElementShadowField(final AnnotatedMixinElementHandlerShadow a1, final VariableElement a2, final AnnotationHandle a3, final boolean a4) {
            this.this$0 = a1;
            super((Element)a2, a3, a4, IMapping.Type.FIELD);
        }
        
        @Override
        public MappingField getMapping(final TypeHandle a1, final String a2, final String a3) {
            return new MappingField(a1.getName(), a2, a3);
        }
        
        @Override
        public void addMapping(final ObfuscationType a1, final IMapping<?> a2) {
            this.this$0.addFieldMapping(a1, this.setObfuscatedName(a2), this.getDesc(), a2.getDesc());
        }
        
        public /* bridge */ IMapping getMapping(final TypeHandle a1, final String a2, final String a3) {
            return this.getMapping(a1, a2, a3);
        }
    }
    
    class AnnotatedElementShadowMethod extends AnnotatedElementShadow<ExecutableElement, MappingMethod>
    {
        final /* synthetic */ AnnotatedMixinElementHandlerShadow this$0;
        
        public AnnotatedElementShadowMethod(final AnnotatedMixinElementHandlerShadow a1, final ExecutableElement a2, final AnnotationHandle a3, final boolean a4) {
            this.this$0 = a1;
            super((Element)a2, a3, a4, IMapping.Type.METHOD);
        }
        
        @Override
        public MappingMethod getMapping(final TypeHandle a1, final String a2, final String a3) {
            return a1.getMappingMethod(a2, a3);
        }
        
        @Override
        public void addMapping(final ObfuscationType a1, final IMapping<?> a2) {
            this.this$0.addMethodMapping(a1, this.setObfuscatedName(a2), this.getDesc(), a2.getDesc());
        }
        
        public /* bridge */ IMapping getMapping(final TypeHandle a1, final String a2, final String a3) {
            return this.getMapping(a1, a2, a3);
        }
    }
}
