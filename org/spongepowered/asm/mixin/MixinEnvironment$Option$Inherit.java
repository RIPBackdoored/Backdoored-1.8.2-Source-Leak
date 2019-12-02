package org.spongepowered.asm.mixin;

private enum Inherit
{
    INHERIT, 
    ALLOW_OVERRIDE, 
    INDEPENDENT, 
    ALWAYS_FALSE;
    
    private static final /* synthetic */ Inherit[] $VALUES;
    
    public static Inherit[] values() {
        return Inherit.$VALUES.clone();
    }
    
    public static Inherit valueOf(final String a1) {
        return Enum.valueOf(Inherit.class, a1);
    }
    
    static {
        $VALUES = new Inherit[] { Inherit.INHERIT, Inherit.ALLOW_OVERRIDE, Inherit.INDEPENDENT, Inherit.ALWAYS_FALSE };
    }
}
