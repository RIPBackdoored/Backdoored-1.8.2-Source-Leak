package com.backdoored.mixin;

import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.b.g.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { ItemRenderer.class }, priority = 999999999)
public abstract class MixinItemRenderer
{
    public MixinItemRenderer() {
        super();
    }
    
    @Shadow
    public abstract void renderItemInFirstPerson(final AbstractClientPlayer p0, final float p1, final float p2, final EnumHand p3, final float p4, final ItemStack p5, final float p6);
    
    @Inject(method = { "renderWaterOverlayTexture" }, at = { @At("HEAD") }, cancellable = true)
    private void renderWaterOverlayTexture(final float a1, final CallbackInfo a2) {
    }
    
    @Redirect(method = { "renderItemInFirstPerson(F)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemInFirstPerson(Lnet/minecraft/client/entity/AbstractClientPlayer;FFLnet/minecraft/util/EnumHand;FLnet/minecraft/item/ItemStack;F)V"))
    private void renderItemInFirstPerson(final ItemRenderer a1, final AbstractClientPlayer a2, final float a3, final float a4, final EnumHand a5, final float a6, final ItemStack a7, final float a8) {
        final b v1 = new b(a1, a2, a3, a4, a5, a6, a7, a8);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        v1.itemRenderer.renderItemInFirstPerson(v1.abstractClientPlayer, v1.dd, v1.de, v1.enumHand, v1.dg, v1.itemStack, v1.di);
    }
}
