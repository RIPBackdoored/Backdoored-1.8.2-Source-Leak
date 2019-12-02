package com.backdoored.hacks.movement;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.client.gui.*;
import org.lwjgl.input.*;
import net.minecraft.client.entity.*;
import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Gui Move", description = "Walk while in guis", category = CategoriesInit.MOVEMENT)
public class GuiMove extends BaseHack
{
    public GuiMove() {
        super();
    }
    
    public void onTick() {
        if (this.getEnabled() && GuiMove.mc.currentScreen != null && !(GuiMove.mc.currentScreen instanceof GuiChat)) {
            if (Keyboard.isKeyDown(200)) {
                final EntityPlayerSP player = GuiMove.mc.player;
                player.rotationPitch -= 2.0f;
            }
            if (Keyboard.isKeyDown(208)) {
                final EntityPlayerSP player2 = GuiMove.mc.player;
                player2.rotationPitch += 2.0f;
            }
            if (Keyboard.isKeyDown(203)) {
                final EntityPlayerSP player3 = GuiMove.mc.player;
                player3.rotationYaw -= 2.0f;
            }
            if (Keyboard.isKeyDown(205)) {
                final EntityPlayerSP player4 = GuiMove.mc.player;
                player4.rotationYaw += 2.0f;
            }
        }
    }
    
    @SubscribeEvent
    public void isKeyboardCreated(final IsKeyboardCreated event) {
        if (this.getEnabled() && !(GuiMove.mc.currentScreen instanceof GuiChat)) {
            event.cu = event.cv;
        }
    }
}
