package com.backdoored.commands;

import net.minecraftforge.common.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.backdoored.utils.*;

public class TeleportFinder extends CommandBase
{
    private int bj;
    
    public TeleportFinder() {
        super("Teleport Finder");
        this.bj = 0;
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    private void a(final PacketRecieved packetRecieved) {
        if (packetRecieved.packet instanceof SPacketPlayerPosLook) {
            this.bj = ((SPacketPlayerPosLook)packetRecieved.packet).getTeleportId();
        }
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length < 1) {
            Utils.printMessage("Incorrect num arguments");
            return false;
        }
        if (array[0].equalsIgnoreCase("id")) {
            Utils.printMessage("ID: " + this.bj);
            return true;
        }
        return false;
    }
    
    @Override
    public String a() {
        return null;
    }
}
