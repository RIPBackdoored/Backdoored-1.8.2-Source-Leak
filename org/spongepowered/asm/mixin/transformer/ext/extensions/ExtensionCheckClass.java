package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.transformers.*;
import org.spongepowered.asm.lib.util.*;
import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.throwables.*;

public class ExtensionCheckClass implements IExtension
{
    public ExtensionCheckClass() {
        super();
    }
    
    @Override
    public boolean checkActive(final MixinEnvironment a1) {
        return a1.getOption(MixinEnvironment.Option.DEBUG_VERIFY);
    }
    
    @Override
    public void preApply(final ITargetClassContext a1) {
    }
    
    @Override
    public void postApply(final ITargetClassContext v2) {
        try {
            v2.getClassNode().accept(new CheckClassAdapter(new MixinClassWriter(2)));
        }
        catch (RuntimeException a1) {
            throw new ValidationFailedException(a1.getMessage(), a1);
        }
    }
    
    @Override
    public void export(final MixinEnvironment a1, final String a2, final boolean a3, final byte[] a4) {
    }
    
    public static class ValidationFailedException extends MixinException
    {
        private static final long serialVersionUID = 1L;
        
        public ValidationFailedException(final String a1, final Throwable a2) {
            super(a1, a2);
        }
        
        public ValidationFailedException(final String a1) {
            super(a1);
        }
        
        public ValidationFailedException(final Throwable a1) {
            super(a1);
        }
    }
}
