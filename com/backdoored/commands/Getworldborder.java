package com.backdoored.commands;

import com.backdoored.utils.*;
import net.minecraft.world.border.*;

public class Getworldborder extends CommandBase
{
    public Getworldborder() {
        super(new String[] { "getworldborder", "worldborder", "border" });
    }
    
    @Override
    public boolean a(final String[] array) {
        final WorldBorder worldBorder = this.mc.world.getWorldBorder();
        Utils.printMessage("World border is at:\nMinX: " + worldBorder.minX() + "\nMinZ: " + worldBorder.minZ() + "\nMaxX: " + worldBorder.maxX() + "\nMaxZ: " + worldBorder.maxZ() + "\n", "green");
        return true;
    }
    
    @Override
    public String a() {
        return "-worldborder";
    }
}
