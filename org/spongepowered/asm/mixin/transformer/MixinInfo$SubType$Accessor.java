package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.lib.tree.*;

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
