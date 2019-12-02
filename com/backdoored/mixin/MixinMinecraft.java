package com.backdoored.mixin;

import net.minecraft.client.*;
import net.minecraft.profiler.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.player.*;
import org.lwjgl.input.*;
import com.backdoored.commands.*;
import net.minecraft.client.gui.*;

@Mixin({ Minecraft.class })
public class MixinMinecraft
{
    @Shadow
    @Final
    public Profiler profiler;
    @Shadow
    public GameSettings gameSettings;
    @Shadow
    public GuiScreen guiScreen;
    
    public MixinMinecraft() {
        super();
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endStartSection(Ljava/lang/String;)V", ordinal = 0))
    private void endStartGUISection(final Profiler a1, final String a2) {
        a1.endStartSection("gui");
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureManager;tick()V", ordinal = 0))
    private void tickTextureManagerWithCorrectProfiler(final TextureManager a1) {
        this.profiler.endStartSection("textures");
        a1.tick();
        this.profiler.endStartSection("gui");
    }
    
    @Redirect(method = { "processKeyBinds" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveWrapper(final EntityPlayerSP a1) {
        try {
            if (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN && this.currentScreen == null && Keyboard.getEventKeyState() && Keyboard.getEventCharacter() != '\0' && Command.x != null && Command.x.toCharArray().length >= 1 && Keyboard.getEventCharacter() == Command.x.toCharArray()[0]) {
                ((Minecraft)this).displayGuiScreen((GuiScreen)new GuiChat(Command.x));
            }
        }
        catch (Exception ex) {}
        return a1.isHandActive();
    }
}
