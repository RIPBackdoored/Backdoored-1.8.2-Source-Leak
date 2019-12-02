package com.backdoored.mixin;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import javax.annotation.*;
import net.minecraft.util.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.b.b.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { AbstractClientPlayer.class }, priority = 999999999)
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer
{
    public MixinAbstractClientPlayer() {
        super();
    }
    
    @Shadow
    @Nullable
    protected abstract NetworkPlayerInfo getPlayerInfo();
    
    @Overwrite
    @Nullable
    public ResourceLocation getLocationCape() {
        final NetworkPlayerInfo v1 = this.getPlayerInfo();
        final a v2 = new a(v1);
        MinecraftForge.EVENT_BUS.post((Event)v2);
        if (v2.resourceLocation != null) {
            return v2.resourceLocation;
        }
        return (v1 == null) ? null : v1.getLocationCape();
    }
    
    @Inject(method = { "hasSkin" }, at = { @At("RETURN") }, cancellable = true)
    public void hasSkin(final CallbackInfoReturnable<Boolean> a1) {
        final b.b v1 = new b.b(this.getPlayerInfo(), a1.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        a1.setReturnValue(v1.cl);
    }
    
    @Inject(method = { "getLocationSkin()Lnet/minecraft/util/ResourceLocation;" }, at = { @At("RETURN") }, cancellable = true)
    public void getSkin(final CallbackInfoReturnable<ResourceLocation> a1) {
        final b.a v1 = new b.a(this.getPlayerInfo(), a1.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        a1.setReturnValue(v1.resourceLocation);
    }
}
