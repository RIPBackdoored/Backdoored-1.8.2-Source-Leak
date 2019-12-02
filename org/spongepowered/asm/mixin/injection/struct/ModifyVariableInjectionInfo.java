package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.mixin.injection.modify.*;

public class ModifyVariableInjectionInfo extends InjectionInfo
{
    public ModifyVariableInjectionInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3) {
        super(a1, a2, a3);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode a1) {
        return new ModifyVariableInjector(this, LocalVariableDiscriminator.parse(a1));
    }
    
    @Override
    protected String getDescription() {
        return "Variable modifier method";
    }
}
