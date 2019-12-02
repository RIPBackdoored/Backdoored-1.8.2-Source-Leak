package a.a.d.b.f;

import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class c extends BackdooredEvent
{
    public Entity entity;
    public float cx;
    public float cy;
    
    public c(final Entity entity, final float cx, final float cy) {
        super();
        this.cx = cx;
        this.cy = cy;
    }
}
