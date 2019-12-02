package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.lib.tree.*;

class MixinPreProcessorInterface extends MixinPreProcessorStandard
{
    MixinPreProcessorInterface(final MixinInfo a1, final MixinInfo.MixinClassNode a2) {
        super(a1, a2);
    }
    
    @Override
    protected void prepareMethod(final MixinInfo.MixinMethodNode a1, final ClassInfo.Method a2) {
        if (!Bytecode.hasFlag(a1, 1) && !Bytecode.hasFlag(a1, 4096)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + a2 + " in " + this.mixin);
        }
        super.prepareMethod(a1, a2);
    }
    
    @Override
    protected boolean validateField(final MixinTargetContext a1, final FieldNode a2, final AnnotationNode a3) {
        if (!Bytecode.hasFlag(a2, 8)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + a2.name + " in " + this.mixin);
        }
        return super.validateField(a1, a2, a3);
    }
}
