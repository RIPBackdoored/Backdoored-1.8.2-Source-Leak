package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.subguis.*;
import net.minecraft.client.gui.*;
import com.backdoored.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Anti Death Screen", description = "Prevents the death screen from incorrectly coming up during combat", category = CategoriesInit.COMBAT)
public class AntiDeathScreen extends BaseHack
{
    public static AntiDeathScreen INSTANCE;
    
    public AntiDeathScreen() {
        super();
        AntiDeathScreen.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onOpenGUI(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            try {
                event.setGui((GuiScreen)new BackdooredGuiGameOver((ITextComponent)ObfuscationReflectionHelper.getPrivateValue((Class)GuiGameOver.class, (Object)event.getGui(), new String[] { "causeOfDeath", "field_184871_f" }), event.getGui()));
            }
            catch (Exception ex) {
                Utils.printMessage("Disabled Anti Death Screen due to an error: " + ex.toString());
                ex.printStackTrace();
                this.setEnabled(false);
            }
        }
    }
}
