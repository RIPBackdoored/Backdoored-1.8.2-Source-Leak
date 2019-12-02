package com.backdoored.mixin;

import net.minecraft.entity.item.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.c.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityItem.class })
public abstract class MixinEntityItem
{
    @Shadow
    private int field_145804_b;
    
    public MixinEntityItem() {
        super();
    }
    
    @Inject(method = { "setPickupDelay" }, at = { @At("RETURN") }, cancellable = true)
    public void setPickupDelayWrap(final int a1, final CallbackInfo a2) {
        final c v1 = new c(this.pickupDelay);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.pickupDelay = v1.dw;
    }
    
    @Inject(method = { "setDefaultPickupDelay" }, at = { @At("RETURN") }, cancellable = true)
    public void setDefaultPickupDelayWrap(final CallbackInfo a1) {
        final c v1 = new c(this.pickupDelay);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.pickupDelay = v1.dw;
    }
    
    @Inject(method = { "setNoPickupDelay" }, at = { @At("RETURN") }, cancellable = true)
    public void setNoPickupDelayWrap(final CallbackInfo a1) {
        final c v1 = new c(this.pickupDelay);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.pickupDelay = v1.dw;
    }
}
