package a.a.d.a;

import com.backdoored.event.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.util.math.*;
import net.minecraft.block.properties.*;

public class d extends BackdooredEvent
{
    public IBlockState iBlockState;
    public IBlockAccess iBlockAccess;
    public BlockPos blockPos;
    public CallbackInfoReturnable<AxisAlignedBB> cd;
    public PropertyDirection propertyDirection;
    
    public d(final IBlockState iBlockState, final IBlockAccess iBlockAccess, final BlockPos blockPos, final PropertyDirection propertyDirection, final CallbackInfoReturnable<AxisAlignedBB> cd) {
        super();
        this.iBlockState = iBlockState;
        this.iBlockAccess = iBlockAccess;
        this.blockPos = blockPos;
        this.propertyDirection = propertyDirection;
        this.cd = cd;
    }
}
