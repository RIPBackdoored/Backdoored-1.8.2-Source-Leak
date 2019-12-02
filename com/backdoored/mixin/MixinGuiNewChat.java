package com.backdoored.mixin;

import net.minecraft.client.*;
import net.minecraft.util.text.*;
import net.minecraft.util.math.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import a.a.d.b.d.a.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiNewChat.class })
public abstract class MixinGuiNewChat
{
    @Shadow
    @Final
    private Minecraft mc;
    @Shadow
    private int field_146250_j;
    @Shadow
    private boolean field_146251_k;
    @Shadow
    @Final
    private List<ChatLine> field_146253_i;
    @Shadow
    @Final
    private List<ChatLine> field_146252_h;
    
    public MixinGuiNewChat() {
        super();
    }
    
    @Shadow
    public abstract void deleteChatLine(final int p0);
    
    @Shadow
    public abstract int getChatWidth();
    
    @Shadow
    public abstract float getChatScale();
    
    @Shadow
    public abstract boolean getChatOpen();
    
    @Shadow
    public abstract void scroll(final int p0);
    
    @Overwrite
    private void setChatLine(final ITextComponent a3, final int a4, final int v1, final boolean v2) {
        if (a4 != 0) {
            this.deleteChatLine(a4);
        }
        for (final ITextComponent a5 : GuiUtilRenderComponents.splitText(a3, MathHelper.floor(this.getChatWidth() / this.getChatScale()), this.mc.fontRenderer, false, false)) {
            if (this.getChatOpen() && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
            this.drawnChatLines.add(0, new ChatLine(v1, a5, a4));
        }
        final d v3 = new d();
        MinecraftForge.EVENT_BUS.post((Event)v3);
        if (v3.getResult() == Event.Result.ALLOW && !v2) {
            this.chatLines.add(0, new ChatLine(v1, a3, a4));
        }
        else {
            while (this.drawnChatLines.size() > 100) {
                this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
            }
            if (!v2) {
                this.chatLines.add(0, new ChatLine(v1, a3, a4));
                while (this.chatLines.size() > 100) {
                    this.chatLines.remove(this.chatLines.size() - 1);
                }
            }
        }
    }
    
    @Redirect(method = { "drawChat" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"))
    private void drawRectBackgroundWrapper(final int a1, final int a2, final int a3, final int a4, final int a5) {
        final b v1 = new b(a5);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        Gui.drawRect(a1, a2, a3, a4, v1.cp.getRGB());
    }
}
