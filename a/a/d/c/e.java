package a.a.d.c;

import com.backdoored.event.*;
import net.minecraft.entity.player.*;

public class e extends BackdooredEvent
{
    public int dz;
    public EntityPlayer entityPlayer;
    
    public e(final int dz, final EntityPlayer entityPlayer) {
        super();
        this.dz = dz;
        this.entityPlayer = entityPlayer;
    }
}
