package com.backdoored.mixin.minecraftforge;

import org.spongepowered.asm.mixin.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.client.*;
import net.minecraft.util.text.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EventBus.class })
public class MixinEventBus
{
    public MixinEventBus() {
        super();
    }
    
    @Redirect(method = { "post" }, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/IEventListener;invoke(Lnet/minecraftforge/fml/common/eventhandler/Event;)V", remap = false), remap = false)
    private void invoke(final IEventListener v-1, final Event v0) {
        try {
            v-1.invoke(v0);
        }
        catch (Throwable v) {
            final String a2 = "WARNING!!!! The event bus encountered an error while invoking event " + v0.getClass().getName() + "! Luckily your using Backdoored TM client (Trademarked and patented) so we've prevented your client from crashing! Isn't that lucky!";
            FMLLog.log.warn(a2);
            try {
                Minecraft.getMinecraft().player.sendMessage((ITextComponent)new TextComponentString(a2));
            }
            catch (Throwable a3) {
                a3.printStackTrace();
            }
            v.printStackTrace();
        }
    }
}
