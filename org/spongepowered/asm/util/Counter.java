package org.spongepowered.asm.util;

public final class Counter
{
    public int value;
    
    public Counter() {
        super();
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 != null && a1.getClass() == Counter.class && ((Counter)a1).value == this.value;
    }
    
    @Override
    public int hashCode() {
        return this.value;
    }
}
