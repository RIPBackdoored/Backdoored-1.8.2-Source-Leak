package com.backdoored.mixin;

import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import org.spongepowered.asm.mixin.injection.*;
import net.minecraft.network.play.server.*;
import com.backdoored.*;
import net.minecraft.entity.player.*;
import com.backdoored.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Mixin(value = { NetHandlerPlayClient.class }, priority = 999999999)
public class MixinNetHandlerPlayClient
{
    @Shadow
    private boolean field_147309_h;
    @Shadow
    private WorldClient worldClient;
    @Shadow
    private Minecraft mc;
    
    public MixinNetHandlerPlayClient() {
        super();
    }
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") })
    private void postHandleChunkData(final SPacketChunkData a1, final CallbackInfo a2) {
    }
    
    @Inject(method = { "handleCombatEvent" }, at = { @At("INVOKE") })
    private void onPlayerDeath(final SPacketCombatEvent v2, final CallbackInfo v3) {
        if (v2.eventType == SPacketCombatEvent.Event.ENTITY_DIED) {
            System.out.println("A player died! " + v2.entityId);
            final Entity a2 = Globals.mc.world.getEntityByID(v2.entityId);
            if (a2 instanceof EntityPlayer) {
                final PlayerDeath a3 = new PlayerDeath((EntityPlayer)a2);
                MinecraftForge.EVENT_BUS.post((Event)a3);
            }
        }
    }
}
