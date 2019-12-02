package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$2
{
    AccessorInfo$AccessorType$2(final String a2, final int a3, final Set a1) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo a1) {
        return new AccessorGeneratorFieldSetter(a1);
    }
}