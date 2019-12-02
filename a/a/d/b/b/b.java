package a.a.d.b.b;

import com.backdoored.event.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;

public abstract class b extends BackdooredEvent
{
    public NetworkPlayerInfo networkPlayerInfo;
    
    public b(final NetworkPlayerInfo networkPlayerInfo) {
        super();
        this.networkPlayerInfo = networkPlayerInfo;
    }
    
    public static class a extends b
    {
        public ResourceLocation resourceLocation;
        
        public a(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation resourceLocation) {
            super(networkPlayerInfo);
            this.resourceLocation = resourceLocation;
        }
    }
    
    public static class b extends a.a.d.b.b.b
    {
        public boolean cl;
        
        public b(final NetworkPlayerInfo networkPlayerInfo, final boolean cl) {
            super(networkPlayerInfo);
            this.cl = cl;
        }
    }
}
