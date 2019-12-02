package com.backdoored.commands;

import net.minecraft.entity.*;
import com.backdoored.utils.*;
import net.minecraft.entity.player.*;
import java.util.*;

public class Spectate extends CommandBase
{
    public Spectate() {
        super(new String[] { "spectate", "view", "watch", "possess" });
    }
    
    @Override
    public boolean a(final String[] array) {
        try {
            if (array[0].equalsIgnoreCase("off") || array[0].equalsIgnoreCase("self")) {
                this.mc.setRenderViewEntity((Entity)this.mc.player);
                Utils.printMessage("Now viewing from own perspective", "green");
                return true;
            }
            for (final EntityPlayer renderViewEntity : this.mc.world.playerEntities) {
                if (renderViewEntity.getDisplayNameString().equalsIgnoreCase(array[0])) {
                    this.mc.setRenderViewEntity((Entity)renderViewEntity);
                    Utils.printMessage("Now viewing from perspective of '" + renderViewEntity.getDisplayNameString() + "'", "green");
                    return true;
                }
            }
            Utils.printMessage("Couldnt find player '" + array[0] + "'");
        }
        catch (Exception ex) {
            Utils.printMessage("Error: " + ex.getMessage(), "red");
            ex.printStackTrace();
        }
        return false;
    }
    
    @Override
    public String a() {
        return "-spectate <playername/self>";
    }
}
