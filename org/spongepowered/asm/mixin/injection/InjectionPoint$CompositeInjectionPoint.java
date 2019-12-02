package org.spongepowered.asm.mixin.injection;

import com.google.common.base.*;

abstract static class CompositeInjectionPoint extends InjectionPoint
{
    protected final InjectionPoint[] components;
    
    protected CompositeInjectionPoint(final InjectionPoint... a1) {
        super();
        if (a1 == null || a1.length < 2) {
            throw new IllegalArgumentException("Must supply two or more component injection points for composite point!");
        }
        this.components = a1;
    }
    
    @Override
    public String toString() {
        return "CompositeInjectionPoint(" + this.getClass().getSimpleName() + ")[" + Joiner.on(',').join((Object[])this.components) + "]";
    }
}
