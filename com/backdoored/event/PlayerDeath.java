package com.backdoored.event;

import net.minecraft.entity.player.*;

public class PlayerDeath extends BackdooredEvent
{
    private EntityPlayer entityPlayer;
    
    public PlayerDeath(final EntityPlayer entityPlayer) {
        super();
        this.entityPlayer = entityPlayer;
    }
    
    public EntityPlayer getPlayer() {
        return this.entityPlayer;
    }
}
