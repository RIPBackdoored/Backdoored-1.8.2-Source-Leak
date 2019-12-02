package org.spongepowered.asm.mixin.gen;

import java.util.*;

enum AccessorInfo$AccessorType$3
{
    AccessorInfo$AccessorType$3(final String a2, final int a3, final Set a1) {
    }
    
    @Override
    AccessorGenerator getGenerator(final AccessorInfo a1) {
        return new AccessorGeneratorMethodProxy(a1);
    }
}