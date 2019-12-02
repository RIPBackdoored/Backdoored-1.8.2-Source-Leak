package org.spongepowered.asm.transformers;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.*;

public class MixinClassWriter extends ClassWriter
{
    public MixinClassWriter(final int a1) {
        super(a1);
    }
    
    public MixinClassWriter(final ClassReader a1, final int a2) {
        super(a1, a2);
    }
    
    @Override
    protected String getCommonSuperClass(final String a1, final String a2) {
        return ClassInfo.getCommonSuperClass(a1, a2).getName();
    }
}
