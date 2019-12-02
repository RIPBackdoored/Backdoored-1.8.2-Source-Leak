package org.spongepowered.asm.service.mojang;

import org.spongepowered.asm.service.*;
import net.minecraft.launchwrapper.*;

public class Blackboard implements IGlobalPropertyService
{
    public Blackboard() {
        super();
    }
    
    @Override
    public final <T> T getProperty(final String a1) {
        return Launch.blackboard.get(a1);
    }
    
    @Override
    public final void setProperty(final String a1, final Object a2) {
        Launch.blackboard.put(a1, a2);
    }
    
    @Override
    public final <T> T getProperty(final String a1, final T a2) {
        final Object v1 = Launch.blackboard.get(a1);
        return (T)((v1 != null) ? v1 : a2);
    }
    
    @Override
    public final String getPropertyString(final String a1, final String a2) {
        final Object v1 = Launch.blackboard.get(a1);
        return (v1 != null) ? v1.toString() : a2;
    }
}
