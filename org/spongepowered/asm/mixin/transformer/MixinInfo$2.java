package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.*;

class MixinInfo$2 implements Function<String, String> {
    final /* synthetic */ MixinInfo this$0;
    
    MixinInfo$2(final MixinInfo a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public String apply(final String a1) {
        return this.this$0.getParent().remapClassName(this.this$0.getClassRef(), a1);
    }
    
    @Override
    public /* bridge */ Object apply(final Object o) {
        return this.apply((String)o);
    }
}