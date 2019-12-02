package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.struct.*;

@AtCode("STORE")
public class AfterStoreLocal extends BeforeLoadLocal
{
    public AfterStoreLocal(final InjectionPointData a1) {
        super(a1, 54, true);
    }
}
