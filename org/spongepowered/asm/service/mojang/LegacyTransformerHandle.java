package org.spongepowered.asm.service.mojang;

import org.spongepowered.asm.service.*;
import net.minecraft.launchwrapper.*;
import javax.annotation.*;

class LegacyTransformerHandle implements ILegacyClassTransformer
{
    private final IClassTransformer transformer;
    
    LegacyTransformerHandle(final IClassTransformer a1) {
        super();
        this.transformer = a1;
    }
    
    @Override
    public String getName() {
        return this.transformer.getClass().getName();
    }
    
    @Override
    public boolean isDelegationExcluded() {
        return this.transformer.getClass().getAnnotation(Resource.class) != null;
    }
    
    @Override
    public byte[] transformClassBytes(final String a1, final String a2, final byte[] a3) {
        return this.transformer.transform(a1, a2, a3);
    }
}
