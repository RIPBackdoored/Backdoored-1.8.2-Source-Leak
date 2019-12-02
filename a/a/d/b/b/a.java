package a.a.d.b.b;

import com.backdoored.event.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;

public class a extends BackdooredEvent
{
    public NetworkPlayerInfo networkPlayerInfo;
    public ResourceLocation resourceLocation;
    
    public a(final NetworkPlayerInfo networkPlayerInfo) {
        super();
        this.networkPlayerInfo = networkPlayerInfo;
        this.resourceLocation = null;
    }
}
