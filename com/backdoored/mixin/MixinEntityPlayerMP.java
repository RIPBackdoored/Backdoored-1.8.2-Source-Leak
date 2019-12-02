package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPlayerMP.class })
public class MixinEntityPlayerMP
{
    public MixinEntityPlayerMP() {
        super();
    }
    
    @Inject(method = { "onDeath" }, at = { @At("HEAD") })
    public void onDeath(final DamageSource a1, final CallbackInfo a2) {
        System.out.println("Mixin Death");
    }
    
    @Redirect(method = { "isEntityInvulnerable" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isEntityInvulnerable(Lnet/minecraft/util/DamageSource;)Z"))
    private boolean isEntityInvulnerable(final EntityPlayer a1, final DamageSource a2) {
        return false;
    }
    
    @Redirect(method = { "isEntityInvulnerable" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayerMP;isInvulnerableDimensionChange()Z"))
    private boolean isEntityInvulnerable(final EntityPlayerMP a1) {
        return false;
    }
}
