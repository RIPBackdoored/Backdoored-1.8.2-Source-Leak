package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;

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
