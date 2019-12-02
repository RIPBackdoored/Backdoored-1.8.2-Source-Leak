package org.spongepowered.tools.obfuscation;

import javax.tools.*;
import javax.annotation.processing.*;
import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.struct.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import javax.lang.model.element.*;
import org.spongepowered.asm.mixin.injection.*;
import java.lang.annotation.*;
import java.util.*;

class AnnotatedMixinElementHandlerInjector extends AnnotatedMixinElementHandler
{
    AnnotatedMixinElementHandlerInjector(final IMixinAnnotationProcessor a1, final AnnotatedMixin a2) {
        super(a1, a2);
    }
    
    public void registerInjector(final AnnotatedElementInjector v-4) {
        if (this.mixin.isInterface()) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", v-4.getElement());
        }
        for (final String s : v-4.getAnnotation().getList("method")) {
            final MemberInfo parse = MemberInfo.parse(s);
            if (parse.name == null) {
                continue;
            }
            try {
                parse.validate();
            }
            catch (InvalidMemberDescriptorException a1) {
                v-4.printMessage(this.ap, Diagnostic.Kind.ERROR, a1.getMessage());
            }
            if (parse.desc != null) {
                this.validateReferencedTarget((ExecutableElement)v-4.getElement(), v-4.getAnnotation(), parse, v-4.toString());
            }
            if (!v-4.shouldRemap()) {
                continue;
            }
            for (final TypeHandle v1 : this.mixin.getTargets()) {
                if (!this.registerInjector(v-4, s, parse, v1)) {
                    break;
                }
            }
        }
    }
    
    private boolean registerInjector(final AnnotatedElementInjector v-9, final String v-8, final MemberInfo v-7, final TypeHandle v-6) {
        final String descriptor = v-6.findDescriptor(v-7);
        if (descriptor == null) {
            final Diagnostic.Kind a1 = this.mixin.isMultiTarget() ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            if (v-6.isSimulated()) {
                v-9.printMessage(this.ap, Diagnostic.Kind.NOTE, v-9 + " target '" + v-8 + "' in @Pseudo mixin will not be obfuscated");
            }
            else if (v-6.isImaginary()) {
                v-9.printMessage(this.ap, a1, v-9 + " target requires method signature because enclosing type information for " + v-6 + " is unavailable");
            }
            else if (!v-7.isInitialiser()) {
                v-9.printMessage(this.ap, a1, "Unable to determine signature for " + v-9 + " target method");
            }
            return true;
        }
        final String string = v-9 + " target " + v-7.name;
        final MappingMethod mappingMethod = v-6.getMappingMethod(v-7.name, descriptor);
        ObfuscationData<MappingMethod> obfuscationData = this.obf.getDataProvider().getObfMethod(mappingMethod);
        if (obfuscationData.isEmpty()) {
            if (v-6.isSimulated()) {
                obfuscationData = this.obf.getDataProvider().getRemappedMethod(mappingMethod);
            }
            else {
                if (v-7.isClassInitialiser()) {
                    return true;
                }
                final Diagnostic.Kind a2 = v-7.isConstructor() ? Diagnostic.Kind.WARNING : Diagnostic.Kind.ERROR;
                v-9.addMessage(a2, "No obfuscation mapping for " + string, v-9.getElement(), v-9.getAnnotation());
                return false;
            }
        }
        final IReferenceManager referenceManager = this.obf.getReferenceManager();
        try {
            if ((v-7.owner == null && this.mixin.isMultiTarget()) || v-6.isSimulated()) {
                obfuscationData = AnnotatedMixinElementHandler.stripOwnerData(obfuscationData);
            }
            referenceManager.addMethodMapping(this.classRef, v-8, obfuscationData);
        }
        catch (ReferenceManager.ReferenceConflictException v2) {
            final String v1 = this.mixin.isMultiTarget() ? "Multi-target" : "Target";
            if (v-9.hasCoerceArgument() && v-7.owner == null && v-7.desc == null) {
                final MemberInfo a3 = MemberInfo.parse(v2.getOld());
                final MemberInfo a4 = MemberInfo.parse(v2.getNew());
                if (a3.name.equals(a4.name)) {
                    obfuscationData = AnnotatedMixinElementHandler.stripDescriptors(obfuscationData);
                    referenceManager.setAllowConflicts(true);
                    referenceManager.addMethodMapping(this.classRef, v-8, obfuscationData);
                    referenceManager.setAllowConflicts(false);
                    v-9.printMessage(this.ap, Diagnostic.Kind.WARNING, "Coerced " + v1 + " reference has conflicting descriptors for " + string + ": Storing bare references " + obfuscationData.values() + " in refMap");
                    return true;
                }
            }
            v-9.printMessage(this.ap, Diagnostic.Kind.ERROR, v1 + " reference conflict for " + string + ": " + v-8 + " -> " + v2.getNew() + " previously defined as " + v2.getOld());
        }
        return true;
    }
    
    public void registerInjectionPoint(final AnnotatedElementInjectionPoint a1, final String a2) {
        if (this.mixin.isInterface()) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, "Injector in interface is unsupported", a1.getElement());
        }
        if (!a1.shouldRemap()) {
            return;
        }
        final String v1 = InjectionPointData.parseType(a1.getAt().getValue("value"));
        final String v2 = a1.getAt().getValue("target");
        if ("NEW".equals(v1)) {
            this.remapNewTarget(String.format(a2, v1 + ".<target>"), v2, a1);
            this.remapNewTarget(String.format(a2, v1 + ".args[class]"), a1.getAtArg("class"), a1);
        }
        else {
            this.remapReference(String.format(a2, v1 + ".<target>"), v2, a1);
        }
    }
    
    protected final void remapNewTarget(final String v-5, final String v-4, final AnnotatedElementInjectionPoint v-3) {
        if (v-4 == null) {
            return;
        }
        final MemberInfo parse = MemberInfo.parse(v-4);
        final String ctorType = parse.toCtorType();
        if (ctorType != null) {
            final String a3 = parse.toCtorDesc();
            final MappingMethod v1 = new MappingMethod(ctorType, ".", (a3 != null) ? a3 : "()V");
            final ObfuscationData<MappingMethod> v2 = this.obf.getDataProvider().getRemappedMethod(v1);
            if (v2.isEmpty()) {
                this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find class mapping for " + v-5 + " '" + ctorType + "'", v-3.getElement(), v-3.getAnnotation().asMirror());
                return;
            }
            final ObfuscationData<String> v3 = new ObfuscationData<String>();
            for (final ObfuscationType a4 : v2) {
                final MappingMethod a5 = v2.get(a4);
                if (a3 == null) {
                    v3.put(a4, a5.getOwner());
                }
                else {
                    v3.put(a4, a5.getDesc().replace(")V", ")L" + a5.getOwner() + ";"));
                }
            }
            this.obf.getReferenceManager().addClassMapping(this.classRef, v-4, v3);
        }
        v-3.notifyRemapped();
    }
    
    protected final void remapReference(final String v-4, final String v-3, final AnnotatedElementInjectionPoint v-2) {
        if (v-3 == null) {
            return;
        }
        final AnnotationMirror mirror = ((this.ap.getCompilerEnvironment() == IMixinAnnotationProcessor.CompilerEnvironment.JDT) ? v-2.getAt() : v-2.getAnnotation()).asMirror();
        final MemberInfo v0 = MemberInfo.parse(v-3);
        if (!v0.isFullyQualified()) {
            final String a1 = (v0.owner == null) ? ((v0.desc == null) ? "owner and signature" : "owner") : "signature";
            this.ap.printMessage(Diagnostic.Kind.ERROR, v-4 + " is not fully qualified, missing " + a1, v-2.getElement(), mirror);
            return;
        }
        try {
            v0.validate();
        }
        catch (InvalidMemberDescriptorException a2) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, a2.getMessage(), v-2.getElement(), mirror);
        }
        try {
            if (v0.isField()) {
                final ObfuscationData<MappingField> a3 = this.obf.getDataProvider().getObfFieldRecursive(v0);
                if (a3.isEmpty()) {
                    this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find field mapping for " + v-4 + " '" + v-3 + "'", v-2.getElement(), mirror);
                    return;
                }
                this.obf.getReferenceManager().addFieldMapping(this.classRef, v-3, v0, a3);
            }
            else {
                final ObfuscationData<MappingMethod> v2 = this.obf.getDataProvider().getObfMethodRecursive(v0);
                if (v2.isEmpty() && (v0.owner == null || !v0.owner.startsWith("java/lang/"))) {
                    this.ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find method mapping for " + v-4 + " '" + v-3 + "'", v-2.getElement(), mirror);
                    return;
                }
                this.obf.getReferenceManager().addMethodMapping(this.classRef, v-3, v0, v2);
            }
        }
        catch (ReferenceManager.ReferenceConflictException v3) {
            this.ap.printMessage(Diagnostic.Kind.ERROR, "Unexpected reference conflict for " + v-4 + ": " + v-3 + " -> " + v3.getNew() + " previously defined as " + v3.getOld(), v-2.getElement(), mirror);
            return;
        }
        v-2.notifyRemapped();
    }
    
    static class AnnotatedElementInjector extends AnnotatedElement<ExecutableElement>
    {
        private final InjectorRemap state;
        
        public AnnotatedElementInjector(final ExecutableElement a1, final AnnotationHandle a2, final InjectorRemap a3) {
            super((Element)a1, a2);
            this.state = a3;
        }
        
        public boolean shouldRemap() {
            return this.state.shouldRemap();
        }
        
        public boolean hasCoerceArgument() {
            if (!this.annotation.toString().equals("@Inject")) {
                return false;
            }
            final Iterator<? extends VariableElement> iterator = ((ExecutableElement)this.element).getParameters().iterator();
            if (iterator.hasNext()) {
                final VariableElement v1 = (VariableElement)iterator.next();
                return AnnotationHandle.of(v1, Coerce.class).exists();
            }
            return false;
        }
        
        public void addMessage(final Diagnostic.Kind a1, final CharSequence a2, final Element a3, final AnnotationHandle a4) {
            this.state.addMessage(a1, a2, a3, a4);
        }
        
        @Override
        public String toString() {
            return this.getAnnotation().toString();
        }
    }
    
    static class AnnotatedElementInjectionPoint extends AnnotatedElement<ExecutableElement>
    {
        private final AnnotationHandle at;
        private Map<String, String> args;
        private final InjectorRemap state;
        
        public AnnotatedElementInjectionPoint(final ExecutableElement a1, final AnnotationHandle a2, final AnnotationHandle a3, final InjectorRemap a4) {
            super((Element)a1, a2);
            this.at = a3;
            this.state = a4;
        }
        
        public boolean shouldRemap() {
            return this.at.getBoolean("remap", this.state.shouldRemap());
        }
        
        public AnnotationHandle getAt() {
            return this.at;
        }
        
        public String getAtArg(final String v-1) {
            if (this.args == null) {
                this.args = new HashMap<String, String>();
                for (final String v1 : this.at.getList("args")) {
                    if (v1 == null) {
                        continue;
                    }
                    final int a1 = v1.indexOf(61);
                    if (a1 > -1) {
                        this.args.put(v1.substring(0, a1), v1.substring(a1 + 1));
                    }
                    else {
                        this.args.put(v1, "");
                    }
                }
            }
            return this.args.get(v-1);
        }
        
        public void notifyRemapped() {
            this.state.notifyRemapped();
        }
    }
}
