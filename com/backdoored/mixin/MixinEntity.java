package com.backdoored.mixin;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.c.*;
import a.a.d.b.f.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ Entity.class })
public abstract class MixinEntity
{
    public MixinEntity() {
        super();
    }
    
    @Shadow
    public abstract int getMaxInPortalTime();
    
    @Shadow
    protected abstract void setFlag(final int p0, final boolean p1);
    
    @Shadow
    public abstract boolean isInWater();
    
    @Inject(method = { "turn" }, at = { @At("HEAD") }, cancellable = true)
    private void turn(float a1, float a2, final CallbackInfo a3) {
        final c v1 = new c((Entity)this, a1, a2);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.isCanceled()) {
            a3.cancel();
        }
        a1 = v1.cx;
        a2 = v1.cy;
    }
    
    @Redirect(method = { "onEntityUpdate" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getMaxInPortalTime()I"))
    private int getModifiedMaxInPortalTime(final Entity a1) {
        final b v1 = new b(a1, this.getMaxInPortalTime());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.dv;
    }
    
    @Redirect(method = { "move" }, slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;onGround:Z", ordinal = 0)), at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isSneaking()Z", ordinal = 0))
    private boolean isSneakingWrapper(final Entity a1) {
        final d v1 = new d(a1.isSneaking());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.cz;
    }
}
