package com.backdoored.mixin;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.*;
import com.backdoored.hacks.render.*;
import a.a.k.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { RenderItem.class }, priority = 999999999)
public abstract class MixinRenderItem
{
    public MixinRenderItem() {
        super();
    }
    
    @Shadow
    protected abstract void renderModel(final IBakedModel p0, final ItemStack p1);
    
    @ModifyArg(method = { "renderEffect" }, at = @At(value = "INVOKE", target = "net/minecraft/client/renderer/RenderItem.renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;I)V"), index = 1)
    private int renderModel(final int a1) {
        if (RainbowEnchant.wv != null && RainbowEnchant.wv.getEnabled()) {
            return a.b().getRGB();
        }
        return a1;
    }
    
    @Redirect(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderItem;renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"))
    private void renderItemFromModelWrapper(final RenderItem a1, final IBakedModel a2, final ItemStack a3) {
        final a.a.d.b.g.a v1 = new a.a.d.b.g.a();
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.renderModel(a2, a3);
    }
    
    @Redirect(method = { "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/renderer/block/model/IBakedModel;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableRescaleNormal()V"))
    private void rescaleNormalWrapper() {
        final a.a.d.b.g.a v1 = new a.a.d.b.g.a();
        MinecraftForge.EVENT_BUS.post((Event)v1);
    }
}
