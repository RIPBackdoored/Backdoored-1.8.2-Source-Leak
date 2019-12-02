package com.backdoored.mixin;

import org.spongepowered.asm.mixin.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import a.a.d.a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import a.a.d.e.*;

@Mixin({ Block.class })
public class MixinBlock
{
    public MixinBlock() {
        super();
    }
    
    @Inject(method = { "shouldSideBeRendered" }, at = { @At("HEAD") }, cancellable = true)
    public void shouldSideBeRendered(final IBlockState a1, final IBlockAccess a2, final BlockPos a3, final EnumFacing a4, final CallbackInfoReturnable<Boolean> a5) {
        final c v1 = new c(null);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.bz != null) {
            ((Block)this).setLightLevel((float)v1.bz);
            a5.setReturnValue(true);
        }
    }
    
    @Inject(method = { "getAmbientOcclusionLightValue" }, at = { @At("RETURN") }, cancellable = true)
    private void getAmbientOcclusionLightValue(final CallbackInfoReturnable<Float> a1) {
        final b v1 = new b(a1.getReturnValue());
        MinecraftForge.EVENT_BUS.post((Event)v1);
        a1.setReturnValue(v1.ej);
    }
    
    @Inject(method = { "getPackedLightmapCoords" }, at = { @At("HEAD") }, cancellable = true)
    private void getPackedLightmapCoordsWrapper(final IBlockState a1, final IBlockAccess a2, final BlockPos a3, final CallbackInfoReturnable<Integer> a4) {
        final d v1 = new d(null);
        MinecraftForge.EVENT_BUS.post((Event)v1);
        if (v1.el != null) {
            a4.setReturnValue(v1.el);
        }
    }
}
