package com.backdoored.commands;

import a.a.a.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import com.backdoored.utils.*;

public class Fakeplayer extends CommandBase
{
    public Fakeplayer() {
        super("fakeplayer");
    }
    
    @Override
    public boolean a(final String[] array) {
        try {
            if (array.length < 1) {
                return false;
            }
            final UUID fromString = UUID.fromString(d.b(array[0]));
            System.out.print("UUID LOCATED: " + fromString.toString());
            final EntityOtherPlayerMP entityOtherPlayerMP = new EntityOtherPlayerMP((World)this.mc.world, new GameProfile(fromString, array[0]));
            entityOtherPlayerMP.copyLocationAndAnglesFrom((Entity)this.mc.player);
            entityOtherPlayerMP.readFromNBT(this.mc.player.writeToNBT(new NBTTagCompound()));
            for (final int n : new int[] { -21, -69, -911, -420, -666, -2003 }) {
                if (this.mc.world.getEntityByID(n) == null) {
                    this.mc.world.addEntityToWorld(n, (Entity)entityOtherPlayerMP);
                    return true;
                }
            }
            for (int j = -1; j > -400; --j) {
                if (this.mc.world.getEntityByID(j) == null) {
                    this.mc.world.addEntityToWorld(j, (Entity)entityOtherPlayerMP);
                    return true;
                }
            }
            Utils.printMessage("No entity ids available", "gold");
            return false;
        }
        catch (Exception ex) {
            Utils.printMessage(ex.getMessage(), "gold");
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public String a() {
        return "-fakeplayer DanTDM";
    }
}
