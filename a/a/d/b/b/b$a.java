package a.a.d.b.b;

import net.minecraft.util.*;
import net.minecraft.client.network.*;

public static class a extends b
{
    public ResourceLocation resourceLocation;
    
    public a(final NetworkPlayerInfo networkPlayerInfo, final ResourceLocation resourceLocation) {
        super(networkPlayerInfo);
        this.resourceLocation = resourceLocation;
    }
}
