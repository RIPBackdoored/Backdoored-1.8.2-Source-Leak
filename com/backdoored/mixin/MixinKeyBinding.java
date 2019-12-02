package com.backdoored.mixin;

import net.minecraft.client.settings.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import com.backdoored.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ KeyBinding.class })
public class MixinKeyBinding
{
    @Shadow
    private boolean field_74513_e;
    
    public MixinKeyBinding() {
        super();
    }
    
    @Inject(method = { "isKeyDown" }, at = { @At("RETURN") }, cancellable = true)
    private void isKeyDown(final CallbackInfoReturnable<Boolean> a1) {
        final IsKeyboardCreated v1 = new IsKeyboardCreated(a1.getReturnValue(), this.pressed);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        a1.setReturnValue(v1.cu);
    }
}
