package org.spongepowered.asm.util;

public enum Alignment
{
    LEFT, 
    RIGHT;
    
    private static final /* synthetic */ Alignment[] $VALUES;
    
    public static Alignment[] values() {
        return Alignment.$VALUES.clone();
    }
    
    public static Alignment valueOf(final String a1) {
        return Enum.valueOf(Alignment.class, a1);
    }
    
    static {
        $VALUES = new Alignment[] { Alignment.LEFT, Alignment.RIGHT };
    }
}
