package com.backdoored.mixin;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.b.d.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiScreen.class })
public class MixinGuiScreen
{
    @Shadow
    public Minecraft mc;
    
    public MixinGuiScreen() {
        super();
    }
    
    @Inject(method = { "drawWorldBackground(I)V" }, at = { @At("HEAD") }, cancellable = true)
    private void drawWorldBackgroundWrapper(final int a1, final CallbackInfo a2) {
        final a v1 = new a();
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (this.mc.world != null && v1.getResult() == Event.Result.ALLOW) {
            a2.cancel();
        }
    }
}
