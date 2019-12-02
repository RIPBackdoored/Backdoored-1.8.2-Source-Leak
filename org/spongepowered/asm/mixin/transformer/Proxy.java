package org.spongepowered.asm.mixin.transformer;

import net.minecraft.launchwrapper.*;
import org.spongepowered.asm.service.*;
import org.apache.logging.log4j.*;
import java.util.*;

public final class Proxy implements IClassTransformer, ILegacyClassTransformer
{
    private static List<Proxy> proxies;
    private static MixinTransformer transformer;
    private boolean isActive;
    
    public Proxy() {
        super();
        this.isActive = true;
        for (final Proxy v1 : Proxy.proxies) {
            v1.isActive = false;
        }
        Proxy.proxies.add(this);
        LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[] { Proxy.proxies.size() });
    }
    
    public byte[] transform(final String a1, final String a2, final byte[] a3) {
        if (this.isActive) {
            return Proxy.transformer.transformClassBytes(a1, a2, a3);
        }
        return a3;
    }
    
    public String getName() {
        return this.getClass().getName();
    }
    
    public boolean isDelegationExcluded() {
        return true;
    }
    
    public byte[] transformClassBytes(final String a1, final String a2, final byte[] a3) {
        if (this.isActive) {
            return Proxy.transformer.transformClassBytes(a1, a2, a3);
        }
        return a3;
    }
    
    static {
        Proxy.proxies = new ArrayList<Proxy>();
        Proxy.transformer = new MixinTransformer();
    }
}
