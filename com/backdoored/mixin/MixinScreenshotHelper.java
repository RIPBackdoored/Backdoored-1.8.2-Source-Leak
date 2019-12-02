package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import java.io.*;
import javax.annotation.*;
import net.minecraft.client.shader.*;
import net.minecraft.util.text.*;
import a.a.d.b.g.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ ScreenShotHelper.class })
public class MixinScreenshotHelper
{
    public MixinScreenshotHelper() {
        super();
    }
    
    @Redirect(method = { "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/ScreenShotHelper;saveScreenshot(Ljava/io/File;Ljava/lang/String;IILnet/minecraft/client/shader/Framebuffer;)Lnet/minecraft/util/text/ITextComponent;"))
    private static ITextComponent saveScreenshot(final File a1, @Nullable final String a2, final int a3, final int a4, final Framebuffer a5) {
        final e v1 = new e(a1, a2, a3, a4, a5);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (!v1.isCanceled()) {
            return ScreenShotHelper.saveScreenshot(a1, (String)null, a3, a4, a5);
        }
        return v1.iTextComponent;
    }
}
