package a.a.d.a;

import com.backdoored.event.*;
import org.spongepowered.asm.mixin.injection.callback.*;

public class a extends BackdooredEvent
{
    public CallbackInfoReturnable<Boolean> bx;
    
    public a(final CallbackInfoReturnable<Boolean> bx) {
        super();
        this.bx = bx;
    }
}
