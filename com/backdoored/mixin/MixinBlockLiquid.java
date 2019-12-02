package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockLiquid.class })
public class MixinBlockLiquid
{
    public MixinBlockLiquid() {
        super();
    }
    
    @Inject(method = { "canCollideCheck" }, at = { @At("HEAD") }, cancellable = true)
    public void canCollideWithLiquid(final IBlockState a1, final boolean a2, final CallbackInfoReturnable<Boolean> a3) {
        final a v1 = new a(a3);
        MinecraftForge.EVENT_BUS.post((Event)v1);
    }
}
