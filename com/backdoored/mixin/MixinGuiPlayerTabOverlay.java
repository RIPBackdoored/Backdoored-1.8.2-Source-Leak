package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.*;

@Mixin(value = { GuiPlayerTabOverlay.class }, priority = 999999999)
public class MixinGuiPlayerTabOverlay
{
    public MixinGuiPlayerTabOverlay() {
        super();
    }
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", ordinal = 0))
    public int noMin(final int a1, final int a2) {
        return a1;
    }
    
    @ModifyConstant(method = { "renderPlayerlist" }, constant = { @Constant(intValue = 20, ordinal = 0) })
    public int getNumRows(final int a1) {
        return 30;
    }
    
    @Redirect(method = { "renderPlayerlist" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isIntegratedServerRunning()Z"))
    public boolean renderPlayerIcons(final Minecraft a1) {
        return true;
    }
}
