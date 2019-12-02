package org.spongepowered.tools.obfuscation;

import org.spongepowered.tools.obfuscation.interfaces.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;
import javax.tools.*;
import javax.annotation.processing.*;
import org.spongepowered.asm.mixin.gen.*;
import java.util.*;
import org.spongepowered.asm.obfuscation.mapping.common.*;
import com.google.common.base.*;
import org.spongepowered.asm.mixin.refmap.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import org.spongepowered.asm.mixin.injection.struct.*;
import javax.lang.model.type.*;

public class AnnotatedMixinElementHandlerAccessor extends AnnotatedMixinElementHandler implements IMixinContext
{
    public AnnotatedMixinElementHandlerAccessor(final IMixinAnnotationProcessor a1, final AnnotatedMixin a2) {
        super(a1, a2);
    }
    
    @Override
    public ReferenceMapper getReferenceMapper() {
        return null;
    }
    
    @Override
    public String getClassName() {
        return this.mixin.getClassRef().replace('/', '.');
    }
    
    @Override
    public String getClassRef() {
        return this.mixin.getClassRef();
    }
    
    @Override
    public String getTargetClassRef() {
        throw new UnsupportedOperationException("Target class not available at compile time");
    }
    
    @Override
    public IMixinInfo getMixin() {
        throw new UnsupportedOperationException("MixinInfo not available at compile time");
    }
    
    @Override
    public Extensions getExtensions() {
        throw new UnsupportedOperationException("Mixin Extensions not available at compile time");
    }
    
    @Override
    public boolean getOption(final MixinEnvironment.Option a1) {
        throw new UnsupportedOperationException("Options not available at compile time");
    }
    
    @Override
    public int getPriority() {
        throw new UnsupportedOperationException("Priority not available at compile time");
    }
    
    @Override
    public Target getTargetMethod(final MethodNode a1) {
        throw new UnsupportedOperationException("Target not available at compile time");
    }
    
    public void registerAccessor(final AnnotatedElementAccessor v2) {
        if (v2.getAccessorType() == null) {
            v2.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unsupported accessor type");
            return;
        }
        final String v3 = this.getAccessorTargetName(v2);
        if (v3 == null) {
            v2.printMessage(this.ap, Diagnostic.Kind.WARNING, "Cannot inflect accessor target name");
            return;
        }
        v2.setTargetName(v3);
        for (final TypeHandle a1 : this.mixin.getTargets()) {
            if (v2.getAccessorType() == AccessorInfo.AccessorType.METHOD_PROXY) {
                this.registerInvokerForTarget((AnnotatedElementInvoker)v2, a1);
            }
            else {
                this.registerAccessorForTarget(v2, a1);
            }
        }
    }
    
    private void registerAccessorForTarget(final AnnotatedElementAccessor v2, final TypeHandle v3) {
        FieldHandle v4 = v3.findField(v2.getTargetName(), v2.getTargetTypeName(), false);
        if (v4 == null) {
            if (!v3.isImaginary()) {
                v2.printMessage(this.ap, Diagnostic.Kind.ERROR, "Could not locate @Accessor target " + v2 + " in target " + v3);
                return;
            }
            v4 = new FieldHandle(v3.getName(), v2.getTargetName(), v2.getDesc());
        }
        if (!v2.shouldRemap()) {
            return;
        }
        ObfuscationData<MappingField> v5 = this.obf.getDataProvider().getObfField(v4.asMapping(false).move(v3.getName()));
        if (v5.isEmpty()) {
            final String a1 = this.mixin.isMultiTarget() ? (" in target " + v3) : "";
            v2.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + a1 + " for @Accessor target " + v2);
            return;
        }
        v5 = AnnotatedMixinElementHandler.stripOwnerData(v5);
        try {
            this.obf.getReferenceManager().addFieldMapping(this.mixin.getClassRef(), v2.getTargetName(), v2.getContext(), v5);
        }
        catch (ReferenceManager.ReferenceConflictException a2) {
            v2.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Accessor target " + v2 + ": " + a2.getNew() + " for target " + v3 + " conflicts with existing mapping " + a2.getOld());
        }
    }
    
    private void registerInvokerForTarget(final AnnotatedElementInvoker v2, final TypeHandle v3) {
        MethodHandle v4 = v3.findMethod(v2.getTargetName(), v2.getTargetTypeName(), false);
        if (v4 == null) {
            if (!v3.isImaginary()) {
                v2.printMessage(this.ap, Diagnostic.Kind.ERROR, "Could not locate @Invoker target " + v2 + " in target " + v3);
                return;
            }
            v4 = new MethodHandle(v3, v2.getTargetName(), v2.getDesc());
        }
        if (!v2.shouldRemap()) {
            return;
        }
        ObfuscationData<MappingMethod> v5 = this.obf.getDataProvider().getObfMethod(v4.asMapping(false).move(v3.getName()));
        if (v5.isEmpty()) {
            final String a1 = this.mixin.isMultiTarget() ? (" in target " + v3) : "";
            v2.printMessage(this.ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + a1 + " for @Accessor target " + v2);
            return;
        }
        v5 = AnnotatedMixinElementHandler.stripOwnerData(v5);
        try {
            this.obf.getReferenceManager().addMethodMapping(this.mixin.getClassRef(), v2.getTargetName(), v2.getContext(), v5);
        }
        catch (ReferenceManager.ReferenceConflictException a2) {
            v2.printMessage(this.ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Invoker target " + v2 + ": " + a2.getNew() + " for target " + v3 + " conflicts with existing mapping " + a2.getOld());
        }
    }
    
    private String getAccessorTargetName(final AnnotatedElementAccessor a1) {
        final String v1 = a1.getAnnotationValue();
        if (Strings.isNullOrEmpty(v1)) {
            return this.inflectAccessorTarget(a1);
        }
        return v1;
    }
    
    private String inflectAccessorTarget(final AnnotatedElementAccessor a1) {
        return AccessorInfo.inflectTarget(a1.getSimpleName(), a1.getAccessorType(), "", this, false);
    }
    
    @Override
    public /* bridge */ IReferenceMapper getReferenceMapper() {
        return this.getReferenceMapper();
    }
    
    static class AnnotatedElementAccessor extends AnnotatedElement<ExecutableElement>
    {
        private final boolean shouldRemap;
        private final TypeMirror returnType;
        private String targetName;
        
        public AnnotatedElementAccessor(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
            super((Element)a1, a2);
            this.shouldRemap = a3;
            this.returnType = ((ExecutableElement)this.getElement()).getReturnType();
        }
        
        public boolean shouldRemap() {
            return this.shouldRemap;
        }
        
        public String getAnnotationValue() {
            return this.getAnnotation().getValue();
        }
        
        public TypeMirror getTargetType() {
            switch (this.getAccessorType()) {
                case FIELD_GETTER: {
                    return this.returnType;
                }
                case FIELD_SETTER: {
                    return ((VariableElement)((ExecutableElement)this.getElement()).getParameters().get(0)).asType();
                }
                default: {
                    return null;
                }
            }
        }
        
        public String getTargetTypeName() {
            return TypeUtils.getTypeName(this.getTargetType());
        }
        
        public String getAccessorDesc() {
            return TypeUtils.getInternalName(this.getTargetType());
        }
        
        public MemberInfo getContext() {
            return new MemberInfo(this.getTargetName(), null, this.getAccessorDesc());
        }
        
        public AccessorInfo.AccessorType getAccessorType() {
            return (this.returnType.getKind() == TypeKind.VOID) ? AccessorInfo.AccessorType.FIELD_SETTER : AccessorInfo.AccessorType.FIELD_GETTER;
        }
        
        public void setTargetName(final String a1) {
            this.targetName = a1;
        }
        
        public String getTargetName() {
            return this.targetName;
        }
        
        @Override
        public String toString() {
            return (this.targetName != null) ? this.targetName : "<invalid>";
        }
    }
    
    static class AnnotatedElementInvoker extends AnnotatedElementAccessor
    {
        public AnnotatedElementInvoker(final ExecutableElement a1, final AnnotationHandle a2, final boolean a3) {
            super(a1, a2, a3);
        }
        
        @Override
        public String getAccessorDesc() {
            return TypeUtils.getDescriptor((ExecutableElement)this.getElement());
        }
        
        @Override
        public AccessorInfo.AccessorType getAccessorType() {
            return AccessorInfo.AccessorType.METHOD_PROXY;
        }
        
        @Override
        public String getTargetTypeName() {
            return TypeUtils.getJavaSignature(this.getElement());
        }
    }
}
