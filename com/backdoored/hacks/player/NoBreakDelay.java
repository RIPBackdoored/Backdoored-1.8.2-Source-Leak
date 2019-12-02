package com.backdoored.hacks.player;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.client.multiplayer.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.utils.*;

@b.a(name = "No Break Delay", description = "like fast place but for breaking", category = CategoriesInit.PLAYER)
public class NoBreakDelay extends BaseHack
{
    public NoBreakDelay() {
        super();
    }
    
    public void onTick() {
        if (!this.getEnabled()) {
            return;
        }
        try {
            ObfuscationReflectionHelper.setPrivateValue((Class)PlayerControllerMP.class, (Object)NoBreakDelay.mc.playerController, (Object)0, new String[] { "blockHitDelay", "field_78781_i" });
        }
        catch (Exception ex) {
            this.setEnabled(false);
            Utils.printMessage("Disabled fastplace due to error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
