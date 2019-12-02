package a.a.d.d;

import com.backdoored.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;

@Cancelable
public class a extends BackdooredEvent
{
    public Packet packet;
    
    public a(final Packet<?> packet) {
        super();
        this.packet = packet;
    }
    
    public boolean isCancelable() {
        return true;
    }
}
