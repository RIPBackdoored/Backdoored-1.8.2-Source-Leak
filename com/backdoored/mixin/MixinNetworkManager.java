package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.d.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.channel.*;
import com.backdoored.event.*;
import io.netty.handler.timeout.*;

@Mixin(value = { NetworkManager.class }, priority = 999999999)
public class MixinNetworkManager
{
    public MixinNetworkManager() {
        super();
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> a1, final CallbackInfo a2) {
        final c v1 = new c(a1);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.isCanceled() && a2.isCancellable()) {
            a2.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelRead(final ChannelHandlerContext a1, final Packet<?> a2, final CallbackInfo a3) {
        final PacketRecieved v1 = new PacketRecieved(a2);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.isCanceled() && a3.isCancellable()) {
            a3.cancel();
        }
    }
    
    @Inject(method = { "exceptionCaught" }, at = { @At("HEAD") }, cancellable = true)
    private void exceptionCaught(final ChannelHandlerContext a1, final Throwable a2, final CallbackInfo a3) {
        if (!(a2 instanceof TimeoutException)) {
            a2.printStackTrace();
            a3.cancel();
        }
    }
}
