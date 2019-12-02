package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.transformer.ext.*;

class MixinTransformer$1 implements MixinConfig.IListener {
    final /* synthetic */ IHotSwap val$hotSwapper;
    final /* synthetic */ MixinTransformer this$0;
    
    MixinTransformer$1(final MixinTransformer a1, final IHotSwap val$hotSwapper) {
        this.this$0 = a1;
        this.val$hotSwapper = val$hotSwapper;
        super();
    }
    
    @Override
    public void onPrepare(final MixinInfo a1) {
        this.val$hotSwapper.registerMixinClass(a1.getClassName());
    }
    
    @Override
    public void onInit(final MixinInfo a1) {
    }
}