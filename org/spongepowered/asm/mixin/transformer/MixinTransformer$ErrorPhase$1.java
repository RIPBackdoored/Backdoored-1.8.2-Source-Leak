package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;

enum MixinTransformer$ErrorPhase$1
{
    MixinTransformer$ErrorPhase$1(final String a1, final int a2) {
    }
    
    @Override
    IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler a3, final String a4, final InvalidMixinException a5, final IMixinInfo v1, final IMixinErrorHandler.ErrorAction v2) {
        try {
            return a3.onPrepareError(v1.getConfig(), a5, v1, v2);
        }
        catch (AbstractMethodError a6) {
            return v2;
        }
    }
    
    @Override
    protected String getContext(final IMixinInfo a1, final String a2) {
        return String.format("preparing %s in %s", a1.getName(), a2);
    }
}