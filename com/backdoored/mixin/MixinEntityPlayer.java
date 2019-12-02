package com.backdoored.mixin;

import net.minecraft.entity.player.*;
import com.mojang.authlib.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import a.a.d.c.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mixin(value = { EntityPlayer.class }, priority = 9900)
public abstract class MixinEntityPlayer extends MixinEntityLivingBase
{
    public MixinEntityPlayer() {
        super();
    }
    
    @Shadow
    public abstract GameProfile getGameProfile();
    
    @ModifyConstant(method = { "attackTargetEntityWithCurrentItem" }, constant = { @Constant(doubleValue = 0.6) })
    private double decelerate(final double a1) {
        return 1.0;
    }
    
    @Redirect(method = { "attackTargetEntityWithCurrentItem" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setSprinting(Z)V"))
    private void dontSprintPlsThx(final EntityPlayer a1, final boolean a2) {
    }
    
    @ModifyConstant(method = { "getPortalCooldown" }, constant = { @Constant(intValue = 10) })
    private int getModifiedPortalCooldown(final int a1) {
        final e v1 = new e(a1, (EntityPlayer)this);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.dz;
    }
}
