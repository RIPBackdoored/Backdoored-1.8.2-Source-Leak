package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.layers.*;
import com.backdoored.hacks.render.*;
import a.a.k.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { LayerArmorBase.class }, priority = 999999999)
public class MixinLayerArmorBase
{
    public MixinLayerArmorBase() {
        super();
    }
    
    @Redirect(method = { "renderEnchantedGlint" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/GlStateManager.color(FFFF)V"))
    private static void renderEnchantedGlint(float a2, float a3, float a4, float v1) {
        if (RainbowEnchant.wv != null && RainbowEnchant.wv.getEnabled()) {
            final Color a5 = a.b();
            a2 = (float)a5.getRed();
            a4 = (float)a5.getBlue();
            a3 = (float)a5.getGreen();
            v1 = (float)a5.getAlpha();
        }
        GlStateManager.color(a2, a3, a4, v1);
    }
}
