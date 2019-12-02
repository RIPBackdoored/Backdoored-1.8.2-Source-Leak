package com.backdoored.mixin;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;
import a.a.d.b.c.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer
{
    public MixinFontRenderer() {
        super();
    }
    
    @Shadow
    protected abstract void renderStringAtPos(final String p0, final boolean p1);
    
    @Redirect(method = { "renderString" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderStringAtPos(Ljava/lang/String;Z)V"))
    private void renderStrPosWrapper(final FontRenderer a1, final String a2, final boolean a3) {
        final a v1 = new a(a2);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        this.renderStringAtPos(v1.cn, a3);
    }
}
