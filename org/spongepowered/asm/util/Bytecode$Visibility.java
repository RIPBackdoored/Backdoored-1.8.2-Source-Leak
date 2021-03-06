package org.spongepowered.asm.util;

public enum Visibility
{
    PRIVATE(2), 
    PROTECTED(4), 
    PACKAGE(0), 
    PUBLIC(1);
    
    static final int MASK = 7;
    final int access;
    private static final /* synthetic */ Visibility[] $VALUES;
    
    public static Visibility[] values() {
        return Visibility.$VALUES.clone();
    }
    
    public static Visibility valueOf(final String a1) {
        return Enum.valueOf(Visibility.class, a1);
    }
    
    private Visibility(final int a1) {
        this.access = a1;
    }
    
    static {
        $VALUES = new Visibility[] { Visibility.PRIVATE, Visibility.PROTECTED, Visibility.PACKAGE, Visibility.PUBLIC };
    }
}
