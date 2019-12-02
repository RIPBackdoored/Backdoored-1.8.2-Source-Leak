package org.spongepowered.asm.mixin.transformer;

import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;

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
