package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.*;
import a.a.d.c.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ EntityPig.class })
public class MixinEntityPig
{
    public MixinEntityPig() {
        super();
    }
    
    @ModifyArgs(method = { "travel" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityAnimal;travel(FFF)V"))
    private void travel(final Args a1, final float a2, final float a3, final float a4) {
        final a v1 = new a();
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.getResult() == Event.Result.ALLOW || v1.getResult() == Event.Result.DEFAULT) {
            a1.setAll(a2, a3, a4);
        }
        else {
            a1.setAll(a2, a3, 0);
        }
    }
}
