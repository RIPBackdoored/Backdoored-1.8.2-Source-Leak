package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.spectator.categories.*;
import net.minecraft.client.network.*;
import net.minecraft.world.*;
import a.a.d.c.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ TeleportToPlayer.class })
public class MixinTeleportToPlayer
{
    public MixinTeleportToPlayer() {
        super();
    }
    
    @Redirect(method = { "<init>(Ljava/util/Collection;)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/NetworkPlayerInfo;getGameType()Lnet/minecraft/world/GameType;"))
    public GameType getPlayerTypeWrapper(final NetworkPlayerInfo a1) {
        final d v1 = new d(a1, a1.getGameType());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        return v1.gameType;
    }
}
