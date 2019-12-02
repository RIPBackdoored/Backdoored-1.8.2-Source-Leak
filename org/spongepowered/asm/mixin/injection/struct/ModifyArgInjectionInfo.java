package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.invoke.*;

public class ModifyArgInjectionInfo extends InjectionInfo
{
    public ModifyArgInjectionInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3) {
        super(a1, a2, a3);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode a1) {
        final int v1 = Annotations.getValue(a1, "index", -1);
        return new ModifyArgInjector(this, v1);
    }
    
    @Override
    protected String getDescription() {
        return "Argument modifier method";
    }
}
