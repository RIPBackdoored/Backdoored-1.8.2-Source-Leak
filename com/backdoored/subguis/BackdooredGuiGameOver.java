package com.backdoored.subguis;

import net.minecraft.util.text.*;
import javax.annotation.*;
import net.minecraft.client.gui.*;
import java.io.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.input.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BackdooredGuiGameOver extends GuiGameOver
{
    public GuiScreen guiScreen;
    public int bai;
    
    public BackdooredGuiGameOver(@Nullable final ITextComponent textComponent, final GuiScreen guiScreen) {
        super(textComponent);
        this.bai = 0;
        this.guiScreen = guiScreen;
    }
    
    public void initGui() {
        super.initGui();
        final GuiButton guiButton = new GuiButton(420, this.width / 2 - 100, this.height / 4 + 120, "Hide Death Screen");
        this.buttonList.add(guiButton);
        guiButton.enabled = true;
    }
    
    protected void actionPerformed(final GuiButton guiButton) throws IOException {
        if (guiButton.id == 420) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.player.isDead = false;
            this.bai = (int)this.mc.player.getHealth();
            this.mc.player.setHealth(1.0f);
        }
        super.actionPerformed(guiButton);
    }
    
    public void updateScreen() {
        if (this.mc.player != null && !this.mc.player.isDead && this.mc.player.getHealth() > 0.0f) {
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
        }
        else {
            super.updateScreen();
        }
    }
    
    @SubscribeEvent
    public void a(final GuiScreenEvent.KeyboardInputEvent keyboardInputEvent) {
        if (Keyboard.getEventKeyState()) {
            final int eventKey = Keyboard.getEventKey();
            if (eventKey != 0 && Keyboard.getKeyName(eventKey).equalsIgnoreCase("ESCAPE")) {
                this.mc.displayGuiScreen(this.guiScreen);
                this.mc.player.isDead = true;
                this.mc.player.setHealth((float)this.bai);
            }
        }
    }
}
