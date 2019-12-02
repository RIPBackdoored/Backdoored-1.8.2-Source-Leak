package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import org.spongepowered.asm.mixin.*;

enum ErrorPhase
{
    PREPARE {
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
    }, 
    APPLY {
        MixinTransformer$ErrorPhase$2(final String a1, final int a2) {
        }
        
        @Override
        IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler a3, final String a4, final InvalidMixinException a5, final IMixinInfo v1, final IMixinErrorHandler.ErrorAction v2) {
            try {
                return a3.onApplyError(a4, a5, v1, v2);
            }
            catch (AbstractMethodError a6) {
                return v2;
            }
        }
        
        @Override
        protected String getContext(final IMixinInfo a1, final String a2) {
            return String.format("%s -> %s", a1, a2);
        }
    };
    
    private final String text;
    private static final /* synthetic */ ErrorPhase[] $VALUES;
    
    public static ErrorPhase[] values() {
        return ErrorPhase.$VALUES.clone();
    }
    
    public static ErrorPhase valueOf(final String a1) {
        return Enum.valueOf(ErrorPhase.class, a1);
    }
    
    private ErrorPhase() {
        this.text = this.name().toLowerCase();
    }
    
    abstract IMixinErrorHandler.ErrorAction onError(final IMixinErrorHandler p0, final String p1, final InvalidMixinException p2, final IMixinInfo p3, final IMixinErrorHandler.ErrorAction p4);
    
    protected abstract String getContext(final IMixinInfo p0, final String p1);
    
    public String getLogMessage(final String a1, final InvalidMixinException a2, final IMixinInfo a3) {
        return String.format("Mixin %s failed %s: %s %s", this.text, this.getContext(a3, a1), a2.getClass().getName(), a2.getMessage());
    }
    
    public String getErrorMessage(final IMixinInfo a1, final IMixinConfig a2, final MixinEnvironment.Phase a3) {
        return String.format("Mixin [%s] from phase [%s] in config [%s] FAILED during %s", a1, a3, a2, this.name());
    }
    
    ErrorPhase(final String a1, final int a2, final MixinTransformer$1 a3) {
        this();
    }
    
    static {
        $VALUES = new ErrorPhase[] { ErrorPhase.PREPARE, ErrorPhase.APPLY };
    }
}
