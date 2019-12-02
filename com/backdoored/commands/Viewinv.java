package com.backdoored.commands;

import net.minecraft.client.gui.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import com.backdoored.utils.*;
import java.util.*;

public class Viewinv extends CommandBase
{
    public Viewinv() {
        super(new String[] { "viewinv", "inventory", "inventoryview" });
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length < 1) {
            this.mc.displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.mc.player));
            return true;
        }
        for (final EntityPlayer entityPlayer : this.mc.world.playerEntities) {
            if (entityPlayer.getDisplayNameString().equalsIgnoreCase(array[0])) {
                this.mc.displayGuiScreen((GuiScreen)new GuiInventory(entityPlayer));
                return true;
            }
        }
        Utils.printMessage("Could not find player " + array[0]);
        return false;
    }
    
    @Override
    public String a() {
        return "-viewinv FitMC";
    }
}
