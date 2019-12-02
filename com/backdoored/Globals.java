package com.backdoored;

import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.client.*;

public interface Globals
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final boolean fa = true;
    
    default NetworkManager a() {
        return FMLClientHandler.instance().getClientToServerNetworkManager();
    }
}
