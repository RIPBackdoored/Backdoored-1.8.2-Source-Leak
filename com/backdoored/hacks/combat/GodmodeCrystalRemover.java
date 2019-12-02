package com.backdoored.hacks.combat;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import com.backdoored.event.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

@b.a(name = "Godmode Crystal Remover", description = "fixes crystals not removing when in god mode", category = CategoriesInit.COMBAT)
public class GodmodeCrystalRemover extends BaseHack
{
    public GodmodeCrystalRemover() {
        super();
    }
    
    @SubscribeEvent
    public void a(final PacketRecieved packetRecieved) {
        if (this.getEnabled() && packetRecieved.packet instanceof SPacketSoundEffect) {
            final SPacketSoundEffect sPacketSoundEffect = (SPacketSoundEffect)packetRecieved.packet;
            if (sPacketSoundEffect.getCategory() == SoundCategory.BLOCKS && sPacketSoundEffect.getSound() == SoundEvents.ENTITY_GENERIC_EXPLODE) {
                for (final Entity entity : GodmodeCrystalRemover.mc.world.loadedEntityList) {
                    if (entity instanceof EntityEnderCrystal && entity.getDistance(sPacketSoundEffect.getX(), sPacketSoundEffect.getY(), sPacketSoundEffect.getZ()) <= 6.0) {
                        entity.setDead();
                    }
                }
            }
        }
    }
}
