package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.tools.obfuscation.mapping.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.asm.util.*;
import javax.tools.*;
import org.spongepowered.asm.util.throwables.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.*;
import javax.annotation.processing.*;

abstract class AnnotatedMixinElementHandler
{
    protected final AnnotatedMixin mixin;
    protected final String classRef;
    protected final IMixinAnnotationProcessor ap;
    protected final IObfuscationManager obf;
    private IMappingConsumer mappings;
    
    AnnotatedMixinElementHandler(final IMixinAnnotationProcessor a1, final AnnotatedMixin a2) {
        super();
        this.ap = a1;
        this.mixin = a2;
        this.classRef = a2.getClassRef();
        this.obf = a1.getObfuscationManager();
    }
    
    private IMappingConsumer getMappings() {
        if (this.mappings == null) {
            final IMappingConsumer v1 = this.mixin.getMappings();
            if (v1 instanceof Mappings) {
                this.mappings = ((Mappings)v1).asUnique();
            }
            else {
                this.mappings = v1;
            }
        }
        return this.mappings;
    }
    
    protected final void addFieldMappings(final String v1, final String v2, final ObfuscationData<MappingField> v3) {
        for (final ObfuscationType a2 : v3) {
            final MappingField a3 = v3.get(a2);
            this.addFieldMapping(a2, v1, a3.getSimpleName(), v2, a3.getDesc());
        }
    }
    
    protected final void addFieldMapping(final ObfuscationType a1, final ShadowElementName a2, final String a3, final String a4) {
        this.addFieldMapping(a1, a2.name(), a2.obfuscated(), a3, a4);
    }
    
    protected final void addFieldMapping(final ObfuscationType a1, final String a2, final String a3, final String a4, final String a5) {
        final MappingField v1 = new MappingField(this.classRef, a2, a4);
        final MappingField v2 = new MappingField(this.classRef, a3, a5);
        this.getMappings().addFieldMapping(a1, v1, v2);
    }
    
    protected final void addMethodMappings(final String v1, final String v2, final ObfuscationData<MappingMethod> v3) {
        for (final ObfuscationType a2 : v3) {
            final MappingMethod a3 = v3.get(a2);
            this.addMethodMapping(a2, v1, a3.getSimpleName(), v2, a3.getDesc());
        }
    }
    
    protected final void addMethodMapping(final ObfuscationType a1, final ShadowElementName a2, final String a3, final String a4) {
        this.addMethodMapping(a1, a2.name(), a2.obfuscated(), a3, a4);
    }
    
    protected final void addMethodMapping(final ObfuscationType a1, final String a2, final String a3, final String a4, final String a5) {
        final MappingMethod v1 = new MappingMethod(this.classRef, a2, a4);
        final MappingMethod v2 = new MappingMethod(this.classRef, a3, a5);
        this.getMappings().addMethodMapping(a1, v1, v2);
    }
    
    protected final void checkConstraints(final ExecutableElement v-1, final AnnotationHandle v0) {
        try {
            final ConstraintParser.Constraint a2 = ConstraintParser.parse(v0.getValue("constraints"));
            try {
                a2.check(this.ap.getTokenProvider());
            }
            catch (ConstraintViolationException a3) {
                this.ap.printMessage(Diagnostic.Kind.ERROR, a3.getMessage(), v-1, v0.asMirror());
            }
        }
        catch (InvalidConstraintException v) {
            this.ap.printMessage(Diagnostic.Kind.WARNING, v.getMessage(), v-1, v0.asMirror());
        }
    }
    
    protected final void validateTarget(final Element a1, final AnnotationHandle a2, final AliasedElementName a3, final String a4) {
        if (a1 instanceof ExecutableElement) {
            this.validateTargetMethod((ExecutableElement)a1, a2, a3, a4, false, false);
        }
        else if (a1 instanceof VariableElement) {
            this.validateTargetField((VariableElement)a1, a2, a3, a4);
        }
    }
    
    protected final void validateTargetMethod(final ExecutableElement a5, final AnnotationHandle a6, final AliasedElementName v1, final String v2, final boolean v3, final boolean v4) {
        final String v5 = TypeUtils.getJavaSignature(a5);
        for (final TypeHandle a7 : this.mixin.getTargets()) {
            if (a7.isImaginary()) {
                continue;
            }
            MethodHandle a8 = a7.findMethod(a5);
            if (a8 == null && v1.hasPrefix()) {
                a8 = a7.findMethod(v1.baseName(), v5);
            }
            if (a8 == null && v1.hasAliases()) {
                for (final String a9 : v1.getAliases()) {
                    if ((a8 = a7.findMethod(a9, v5)) != null) {
                        break;
                    }
                }
            }
            if (a8 != null) {
                if (!v3) {
                    continue;
                }
                this.validateMethodVisibility(a5, a6, v2, a7, a8);
            }
            else {
                if (v4) {
                    continue;
                }
                this.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + v2 + " method in " + a7, a5, a6);
            }
        }
    }
    
    private void validateMethodVisibility(final ExecutableElement a1, final AnnotationHandle a2, final String a3, final TypeHandle a4, final MethodHandle a5) {
        final Visibility v1 = a5.getVisibility();
        if (v1 == null) {
            return;
        }
        final Visibility v2 = TypeUtils.getVisibility(a1);
        final String v3 = "visibility of " + v1 + " method in " + a4;
        if (v1.ordinal() > v2.ordinal()) {
            this.printMessage(Diagnostic.Kind.WARNING, v2 + " " + a3 + " method cannot reduce " + v3, a1, a2);
        }
        else if (v1 == Visibility.PRIVATE && v2.ordinal() > v1.ordinal()) {
            this.printMessage(Diagnostic.Kind.WARNING, v2 + " " + a3 + " method will upgrade " + v3, a1, a2);
        }
    }
    
    protected final void validateTargetField(final VariableElement v2, final AnnotationHandle v3, final AliasedElementName v4, final String v5) {
        final String v6 = v2.asType().toString();
        for (final TypeHandle a4 : this.mixin.getTargets()) {
            if (a4.isImaginary()) {
                continue;
            }
            FieldHandle a5 = a4.findField(v2);
            if (a5 != null) {
                continue;
            }
            final List<String> a6 = v4.getAliases();
            for (final String a7 : a6) {
                if ((a5 = a4.findField(a7, v6)) != null) {
                    break;
                }
            }
            if (a5 != null) {
                continue;
            }
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + v5 + " field in " + a4, v2, v3.asMirror());
        }
    }
    
    protected final void validateReferencedTarget(final ExecutableElement a4, final AnnotationHandle v1, final MemberInfo v2, final String v3) {
        final String v4 = v2.toDescriptor();
        for (final TypeHandle a5 : this.mixin.getTargets()) {
            if (a5.isImaginary()) {
                continue;
            }
            final MethodHandle a6 = a5.findMethod(v2.name, v4);
            if (a6 != null) {
                continue;
            }
            this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + v3 + " in " + a5, a4, v1.asMirror());
        }
    }
    
    private void printMessage(final Diagnostic.Kind a1, final String a2, final Element a3, final AnnotationHandle a4) {
        if (a4 == null) {
            this.ap.printMessage(a1, a2, a3);
        }
        else {
            this.ap.printMessage(a1, a2, a3, a4.asMirror());
        }
    }
    
    protected static <T extends java.lang.Object> ObfuscationData<T> stripOwnerData(final ObfuscationData<T> v-2) {
        final ObfuscationData<T> obfuscationData = new ObfuscationData<T>();
        for (final ObfuscationType v1 : v-2) {
            final T a1 = v-2.get(v1);
            obfuscationData.put(v1, ((IMapping<T>)a1).move((String)null));
        }
        return obfuscationData;
    }
    
    protected static <T extends java.lang.Object> ObfuscationData<T> stripDescriptors(final ObfuscationData<T> v-2) {
        final ObfuscationData<T> obfuscationData = new ObfuscationData<T>();
        for (final ObfuscationType v1 : v-2) {
            final T a1 = v-2.get(v1);
            obfuscationData.put(v1, ((IMapping<T>)a1).transform((String)null));
        }
        return obfuscationData;
    }
    
    abstract static class AnnotatedElement<E extends java.lang.Object>
    {
        protected final E element;
        protected final AnnotationHandle annotation;
        private final String desc;
        
        public AnnotatedElement(final E a1, final AnnotationHandle a2) {
            super();
            this.element = (Element)a1;
            this.annotation = a2;
            this.desc = TypeUtils.getDescriptor((Element)a1);
        }
        
        public E getElement() {
            return (E)this.element;
        }
        
        public AnnotationHandle getAnnotation() {
            return this.annotation;
        }
        
        public String getSimpleName() {
            return this.getElement().getSimpleName().toString();
        }
        
        public String getDesc() {
            return this.desc;
        }
        
        public final void printMessage(final Messager a1, final Diagnostic.Kind a2, final CharSequence a3) {
            a1.printMessage(a2, a3, this.element, this.annotation.asMirror());
        }
    }
    
    static class AliasedElementName
    {
        protected final String originalName;
        private final List<String> aliases;
        private boolean caseSensitive;
        
        public AliasedElementName(final Element a1, final AnnotationHandle a2) {
            super();
            this.originalName = a1.getSimpleName().toString();
            this.aliases = a2.getList("aliases");
        }
        
        public AliasedElementName setCaseSensitive(final boolean a1) {
            this.caseSensitive = a1;
            return this;
        }
        
        public boolean isCaseSensitive() {
            return this.caseSensitive;
        }
        
        public boolean hasAliases() {
            return this.aliases.size() > 0;
        }
        
        public List<String> getAliases() {
            return this.aliases;
        }
        
        public String elementName() {
            return this.originalName;
        }
        
        public String baseName() {
            return this.originalName;
        }
        
        public boolean hasPrefix() {
            return false;
        }
    }
    
    static class ShadowElementName extends AliasedElementName
    {
        private final boolean hasPrefix;
        private final String prefix;
        private final String baseName;
        private String obfuscated;
        
        ShadowElementName(final Element a1, final AnnotationHandle a2) {
            super(a1, a2);
            this.prefix = a2.getValue("prefix", "shadow$");
            boolean v1 = false;
            String v2 = this.originalName;
            if (v2.startsWith(this.prefix)) {
                v1 = true;
                v2 = v2.substring(this.prefix.length());
            }
            this.hasPrefix = v1;
            final String s = v2;
            this.baseName = s;
            this.obfuscated = s;
        }
        
        @Override
        public String toString() {
            return this.baseName;
        }
        
        @Override
        public String baseName() {
            return this.baseName;
        }
        
        public ShadowElementName setObfuscatedName(final IMapping<?> a1) {
            this.obfuscated = a1.getName();
            return this;
        }
        
        public ShadowElementName setObfuscatedName(final String a1) {
            this.obfuscated = a1;
            return this;
        }
        
        @Override
        public boolean hasPrefix() {
            return this.hasPrefix;
        }
        
        public String prefix() {
            return this.hasPrefix ? this.prefix : "";
        }
        
        public String name() {
            return this.prefix(this.baseName);
        }
        
        public String obfuscated() {
            return this.prefix(this.obfuscated);
        }
        
        public String prefix(final String a1) {
            return this.hasPrefix ? (this.prefix + a1) : a1;
        }
    }
}
