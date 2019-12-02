package com.backdoored.mixin;

import net.minecraft.client.gui.*;
import java.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ GuiMainMenu.class })
public class MixinGuiMainMenu
{
    private static String[] devs;
    @Shadow
    private String field_73975_c;
    @Final
    @Shadow
    private static Random field_175374_h;
    
    public MixinGuiMainMenu() {
        super();
    }
    
    @Inject(method = { "Lnet/minecraft/client/gui/GuiMainMenu;<init>()V" }, at = { @At("RETURN") })
    public void postConstructor(final CallbackInfo a1) {
        this.splashText = getRandomSplash();
    }
    
    private static String getRandomSplash() {
        return MixinGuiMainMenu.devs[MixinGuiMainMenu.RANDOM.nextInt(MixinGuiMainMenu.devs.length)] + " owns me and all";
    }
    
    static {
        MixinGuiMainMenu.devs = new String[] { "cookiedragon234", "tigermouthbear", "carbolemons", "fsck" };
    }
}
