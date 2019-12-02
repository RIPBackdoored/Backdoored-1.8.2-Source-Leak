package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.transformer.*;
import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.mixin.injection.code.*;
import org.spongepowered.asm.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.google.common.base.*;

public class CallbackInjectionInfo extends InjectionInfo
{
    protected CallbackInjectionInfo(final MixinTargetContext a1, final MethodNode a2, final AnnotationNode a3) {
        super(a1, a2, a3);
    }
    
    @Override
    protected Injector parseInjector(final AnnotationNode a1) {
        final boolean v1 = Annotations.getValue(a1, "cancellable", Boolean.FALSE);
        final LocalCapture v2 = Annotations.getValue(a1, "locals", LocalCapture.class, LocalCapture.NO_CAPTURE);
        final String v3 = Annotations.getValue(a1, "id", "");
        return new CallbackInjector(this, v1, v2, v3);
    }
    
    @Override
    public String getSliceId(final String a1) {
        return Strings.nullToEmpty(a1);
    }
}
