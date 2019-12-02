package a.a.d.e;

import com.backdoored.event.*;
import net.minecraft.util.math.*;

public class e extends BackdooredEvent
{
    public final BlockPos blockPos;
    
    public e(final BlockPos blockPos) {
        super();
        this.blockPos = blockPos;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
