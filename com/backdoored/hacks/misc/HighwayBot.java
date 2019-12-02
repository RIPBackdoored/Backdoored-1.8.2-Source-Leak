package com.backdoored.hacks.misc;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import net.minecraft.util.math.*;
import a.a.g.b.e.*;
import net.minecraft.util.*;

@b.a(name = "Highway Bot", description = "Automate highway building", category = CategoriesInit.MISC)
public class HighwayBot extends BaseHack
{
    public HighwayBot() {
        super();
    }
    
    public void onTick() {
        final BlockPos blockPos = new BlockPos(HighwayBot.mc.player.posX, HighwayBot.mc.player.posY, HighwayBot.mc.player.posZ);
        if (c() == g.b.qq) {
            final BlockPos[] array = { blockPos.add(1, 0, -1), blockPos.add(1, 0, 0), blockPos.add(1, 0, 1), blockPos.add(1, 1, -1), blockPos.add(1, 1, 0), blockPos.add(1, 1, 1), blockPos.add(1, 2, -1), blockPos.add(1, 2, 0), blockPos.add(1, 2, 1) };
            final BlockPos[] array2 = { blockPos.add(1, -1, -1), blockPos.add(1, -1, 0), blockPos.add(1, -1, 1), blockPos.add(1, 0, -2), blockPos.add(1, 0, 2) };
        }
    }
    
    public static g.b c() {
        final EnumFacing.Axis axis = HighwayBot.mc.player.getHorizontalFacing().getAxis();
        final EnumFacing.AxisDirection axisDirection = HighwayBot.mc.player.getHorizontalFacing().getAxisDirection();
        switch (g.g$a.qp[axis.ordinal()]) {
            case 1: {
                if (axisDirection == EnumFacing.AxisDirection.POSITIVE) {
                    return g.b.qq;
                }
                return g.b.qs;
            }
            case 2: {
                if (axisDirection == EnumFacing.AxisDirection.POSITIVE) {
                    return g.b.qr;
                }
                return g.b.qt;
            }
            default: {
                return g.b.qq;
            }
        }
    }
}
