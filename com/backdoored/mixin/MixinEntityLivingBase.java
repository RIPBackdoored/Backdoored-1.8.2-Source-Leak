package com.backdoored.mixin;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import a.a.d.c.*;
import net.minecraft.world.*;
import a.a.d.b.f.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityLivingBase.class })
public abstract class MixinEntityLivingBase extends MixinEntity
{
    public MixinEntityLivingBase() {
        super();
    }
    
    @Shadow
    public void jump() {
    }
    
    @ModifyConstant(method = { "getWaterSlowDown" }, constant = { @Constant(floatValue = 0.8f) })
    public float getWaterSlowDownWrapper(final float a1) {
        final g v1 = new g(a1);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.ec;
    }
    
    @ModifyConstant(method = { "handleJumpWater" }, constant = { @Constant(doubleValue = 0.03999999910593033) })
    public double handleJumpWaterWrap(final double a1) {
        final f v1 = new f(a1);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.eb;
    }
    
    @ModifyConstant(method = { "handleJumpLava" }, constant = { @Constant(doubleValue = 0.03999999910593033) })
    public double handleJumpLavaWrap(final double a1) {
        final f v1 = new f(a1);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.eb;
    }
    
    @Redirect(method = { "travel" }, at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;isRemote:Z", ordinal = 1))
    private boolean isWorldRemoteWrapper(final World a1) {
        final e v1 = new e(a1.isRemote);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.da;
    }
}
