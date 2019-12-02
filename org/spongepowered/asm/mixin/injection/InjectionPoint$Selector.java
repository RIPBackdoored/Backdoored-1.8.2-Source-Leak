package org.spongepowered.asm.mixin.injection;

public enum Selector
{
    FIRST, 
    LAST, 
    ONE;
    
    public static final Selector DEFAULT;
    private static final /* synthetic */ Selector[] $VALUES;
    
    public static Selector[] values() {
        return Selector.$VALUES.clone();
    }
    
    public static Selector valueOf(final String a1) {
        return Enum.valueOf(Selector.class, a1);
    }
    
    static {
        $VALUES = new Selector[] { Selector.FIRST, Selector.LAST, Selector.ONE };
        DEFAULT = Selector.FIRST;
    }
}
