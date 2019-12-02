package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.nio.*;
import a.a.d.b.g.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.io.*;
import net.minecraft.client.shader.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import a.a.g.b.b.*;
import net.minecraft.util.text.*;

@b.a(name = "Better Screenshot", description = "Asyncronous Screenshots", category = CategoriesInit.CLIENT)
public class BetterScreenshot extends BaseHack
{
    private IntBuffer pixelBuffer;
    private int[] pixels;
    
    public BetterScreenshot() {
        super();
    }
    
    @SubscribeEvent
    public void a(final e e) {
        if (this.getEnabled()) {
            e.setCanceled(true);
            this.a(e.do, e.dq, e.dr, e.framebuffer);
            e.iTextComponent = (ITextComponent)new TextComponentString("Creating screenshot...");
        }
    }
    
    public void a(final File file, int framebufferTextureWidth, int framebufferTextureHeight, final Framebuffer framebuffer) {
        final File file2 = new File(file, "screenshots");
        file2.mkdir();
        if (OpenGlHelper.isFramebufferEnabled()) {
            framebufferTextureWidth = framebuffer.framebufferTextureWidth;
            framebufferTextureHeight = framebuffer.framebufferTextureHeight;
        }
        final int n = framebufferTextureWidth * framebufferTextureHeight;
        if (this.pixelBuffer == null || this.pixelBuffer.capacity() < n) {
            this.pixelBuffer = BufferUtils.createIntBuffer(n);
            this.pixels = new int[n];
        }
        GL11.glPixelStorei(3333, 1);
        GL11.glPixelStorei(3317, 1);
        this.pixelBuffer.clear();
        if (OpenGlHelper.isFramebufferEnabled()) {
            GlStateManager.bindTexture(framebuffer.framebufferTexture);
            GL11.glGetTexImage(3553, 0, 32993, 33639, this.pixelBuffer);
        }
        else {
            GL11.glReadPixels(0, 0, framebufferTextureWidth, framebufferTextureHeight, 32993, 33639, this.pixelBuffer);
        }
        this.pixelBuffer.get(this.pixels);
        new Thread(new o(framebufferTextureWidth, framebufferTextureHeight, this.pixels, BetterScreenshot.mc.getFramebuffer(), file2), "Screenshot creation thread").start();
    }
}
