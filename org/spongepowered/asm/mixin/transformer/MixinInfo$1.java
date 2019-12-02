package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.*;
import org.spongepowered.asm.lib.*;

class MixinInfo$1 implements Function<Type, String> {
    final /* synthetic */ MixinInfo this$0;
    
    MixinInfo$1(final MixinInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public String apply(final Type a1) {
        return a1.getClassName();
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((Type)o);
    }
}