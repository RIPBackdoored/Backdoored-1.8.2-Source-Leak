package com.backdoored.mixin;

import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.math.*;
import a.a.d.a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ BlockLadder.class })
public class MixinBlockLadder
{
    @Shadow
    @Final
    public static PropertyDirection propertyDirection;
    
    public MixinBlockLadder() {
        super();
    }
    
    @Inject(method = { "getBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getBoundingBox(final IBlockState a1, final IBlockAccess a2, final BlockPos a3, final CallbackInfoReturnable<AxisAlignedBB> a4) {
        final d v1 = new d(a1, a2, a3, MixinBlockLadder.FACING, a4);
        MinecraftForge.EVENT_BUS.post((Event)v1);
    }
}
