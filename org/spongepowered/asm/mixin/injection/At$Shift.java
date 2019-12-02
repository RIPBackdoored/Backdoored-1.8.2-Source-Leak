package org.spongepowered.asm.mixin.injection;

public enum Shift
{
    NONE, 
    BEFORE, 
    AFTER, 
    BY;
    
    private static final /* synthetic */ Shift[] $VALUES;
    
    public static Shift[] values() {
        return Shift.$VALUES.clone();
    }
    
    public static Shift valueOf(final String a1) {
        return Enum.valueOf(Shift.class, a1);
    }
    
    static {
        $VALUES = new Shift[] { Shift.NONE, Shift.BEFORE, Shift.AFTER, Shift.BY };
    }
}
