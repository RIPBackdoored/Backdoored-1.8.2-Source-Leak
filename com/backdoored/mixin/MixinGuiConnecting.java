package com.backdoored.mixin;

import net.minecraft.client.gui.*;
import net.minecraft.network.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ GuiConnecting.class })
public class MixinGuiConnecting extends GuiScreen
{
    @Shadow
    private NetworkManager networkManager;
    
    public MixinGuiConnecting() {
        super();
    }
    
    @Overwrite
    public void drawScreen(final int a1, final int a2, final float a3) {
        this.drawDefaultBackground();
        String v1 = "Unknown";
        final ServerData v2 = this.mc.getCurrentServerData();
        if (v2 != null && v2.serverIP != null) {
            v1 = v2.serverIP;
        }
        if (this.networkManager == null) {
            this.drawCenteredString(this.fontRenderer, "Connecting to " + v1 + "...", this.width / 2, this.height / 2 - 50, 16777215);
        }
        else {
            this.drawCenteredString(this.fontRenderer, "Logging into " + v1 + "...", this.width / 2, this.height / 2 - 50, 16777215);
        }
        super.drawScreen(a1, a2, a3);
    }
}
