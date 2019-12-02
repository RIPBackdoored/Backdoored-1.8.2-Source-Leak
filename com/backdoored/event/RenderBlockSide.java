package com.backdoored.event;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;

@Cancelable
public class RenderBlockSide extends BackdooredEvent
{
    public final IBlockState iBlockState;
    public final IBlockAccess iBlockAccess;
    public final BlockPos blockPos;
    public final EnumFacing enumFacing;
    
    public RenderBlockSide(final IBlockState iBlockState, final IBlockAccess iBlockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        super();
        this.iBlockState = iBlockState;
        this.iBlockAccess = iBlockAccess;
        this.blockPos = blockPos;
        this.enumFacing = enumFacing;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
