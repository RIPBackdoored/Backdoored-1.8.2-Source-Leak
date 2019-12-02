package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import a.a.d.b.d.a.*;
import net.minecraft.client.gui.*;

@Mixin({ GuiChat.class })
public class MixinGuiChat
{
    @Shadow
    protected GuiTextField guiTextField;
    @Shadow
    private String field_146409_v;
    private static String persistenceText;
    
    public MixinGuiChat() {
        super();
    }
    
    @Inject(method = { "onGuiClosed" }, at = { @At("HEAD") })
    private void guiClosedWrapper(final CallbackInfo a1) {
        MixinGuiChat.persistenceText = this.inputField.getText();
    }
    
    @Inject(method = { "<init>()V" }, at = { @At("RETURN") })
    private void createGuiWrapper(final CallbackInfo v2) {
        if (MixinGuiChat.persistenceText != null) {
            final c a1 = new c(this.defaultInputFieldText, MixinGuiChat.persistenceText);
            MinecraftForge.EVENT_BUS.post((Event)a1);
            this.defaultInputFieldText = a1.cq;
        }
    }
    
    @Redirect(method = { "keyTyped(CI)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiChat;sendChatMessage(Ljava/lang/String;)V"))
    private void onSendMessage(final GuiChat a1, final String a2) {
        MixinGuiChat.persistenceText = null;
        a1.sendChatMessage(a2);
    }
    
    @Redirect(method = { "drawScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiChat;drawRect(IIIII)V"))
    private void drawBackgroundWrapper(final int a1, final int a2, final int a3, final int a4, final int a5) {
        final b v1 = new b(a5);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        Gui.drawRect(a1, a2, a3, a4, v1.cp.getRGB());
    }
}
