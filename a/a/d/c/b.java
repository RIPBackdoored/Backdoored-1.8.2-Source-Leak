package a.a.d.c;

import com.backdoored.event.*;
import net.minecraft.entity.*;

public class b extends BackdooredEvent
{
    public Entity entity;
    public int dv;
    
    public b(final Entity entity, final int dv) {
        super();
        this.entity = entity;
        this.dv = dv;
    }
}
