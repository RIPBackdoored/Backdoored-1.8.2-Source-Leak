package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.*;
import java.util.*;

abstract static class SubType
{
    protected final MixinInfo mixin;
    protected final String annotationType;
    protected final boolean targetMustBeInterface;
    protected boolean detached;
    
    SubType(final MixinInfo a1, final String a2, final boolean a3) {
        super();
        this.mixin = a1;
        this.annotationType = a2;
        this.targetMustBeInterface = a3;
    }
    
    Collection<String> getInterfaces() {
        return (Collection<String>)Collections.emptyList();
    }
    
    boolean isDetachedSuper() {
        return this.detached;
    }
    
    boolean isLoadable() {
        return false;
    }
    
    void validateTarget(final String v1, final ClassInfo v2) {
        final boolean v3 = v2.isInterface();
        if (v3 != this.targetMustBeInterface) {
            final String a1 = v3 ? "" : "not ";
            throw new InvalidMixinException(this.mixin, this.annotationType + " target type mismatch: " + v1 + " is " + a1 + "an interface in " + this);
        }
    }
    
    abstract void validate(final State p0, final List<ClassInfo> p1);
    
    abstract MixinPreProcessorStandard createPreProcessor(final MixinClassNode p0);
    
    static SubType getTypeFor(final MixinInfo v1) {
        if (!v1.getClassInfo().isInterface()) {
            return new Standard(v1);
        }
        boolean v2 = false;
        for (final ClassInfo.Method a1 : v1.getClassInfo().getMethods()) {
            v2 |= !a1.isAccessor();
        }
        if (v2) {
            return new Interface(v1);
        }
        return new Accessor(v1);
    }
    
    static class Standard extends SubType
    {
        Standard(final MixinInfo a1) {
            super(a1, "@Mixin", false);
        }
        
        @Override
        void validate(final State v-3, final List<ClassInfo> v-2) {
            final ClassNode classNode = v-3.getClassNode();
            for (final ClassInfo v1 : v-2) {
                if (classNode.superName.equals(v1.getSuperName())) {
                    continue;
                }
                if (!v1.hasSuperClass(classNode.superName, ClassInfo.Traversal.SUPER)) {
                    final ClassInfo a2 = ClassInfo.forName(classNode.superName);
                    if (a2.isMixin()) {
                        for (final ClassInfo a3 : a2.getTargets()) {
                            if (v-2.contains(a3)) {
                                throw new InvalidMixinException(this.mixin, "Illegal hierarchy detected. Derived mixin " + this + " targets the same class " + a3.getClassName() + " as its superclass " + a2.getClassName());
                            }
                        }
                    }
                    throw new InvalidMixinException(this.mixin, "Super class '" + classNode.superName.replace('/', '.') + "' of " + this.mixin.getName() + " was not found in the hierarchy of target class '" + v1 + "'");
                }
                this.detached = true;
            }
        }
        
        @Override
        MixinPreProcessorStandard createPreProcessor(final MixinClassNode a1) {
            return new MixinPreProcessorStandard(this.mixin, a1);
        }
    }
    
    static class Interface extends SubType
    {
        Interface(final MixinInfo a1) {
            super(a1, "@Mixin", true);
        }
        
        @Override
        void validate(final State a1, final List<ClassInfo> a2) {
            if (!MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                throw new InvalidMixinException(this.mixin, "Interface mixin not supported in current enviromnment");
            }
            final ClassNode v1 = a1.getClassNode();
            if (!"java/lang/Object".equals(v1.superName)) {
                throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + v1.superName.replace('/', '.'));
            }
        }
        
        @Override
        MixinPreProcessorStandard createPreProcessor(final MixinClassNode a1) {
            return new MixinPreProcessorInterface(this.mixin, a1);
        }
    }
    
    static class Accessor extends SubType
    {
        private final Collection<String> interfaces;
        
        Accessor(final MixinInfo a1) {
            super(a1, "@Mixin", false);
            (this.interfaces = new ArrayList<String>()).add(a1.getClassRef());
        }
        
        @Override
        boolean isLoadable() {
            return true;
        }
        
        @Override
        Collection<String> getInterfaces() {
            return this.interfaces;
        }
        
        @Override
        void validateTarget(final String a1, final ClassInfo a2) {
            final boolean v1 = a2.isInterface();
            if (v1 && !MixinEnvironment.getCompatibilityLevel().supportsMethodsInInterfaces()) {
                throw new InvalidMixinException(this.mixin, "Accessor mixin targetting an interface is not supported in current enviromnment");
            }
        }
        
        @Override
        void validate(final State a1, final List<ClassInfo> a2) {
            final ClassNode v1 = a1.getClassNode();
            if (!"java/lang/Object".equals(v1.superName)) {
                throw new InvalidMixinException(this.mixin, "Super class of " + this + " is invalid, found " + v1.superName.replace('/', '.'));
            }
        }
        
        @Override
        MixinPreProcessorStandard createPreProcessor(final MixinClassNode a1) {
            return new MixinPreProcessorAccessor(this.mixin, a1);
        }
    }
}
