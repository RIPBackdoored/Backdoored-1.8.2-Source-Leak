package org.spongepowered.asm.mixin;

public enum Remap
{
    ALL, 
    FORCE(true), 
    ONLY_PREFIXED, 
    NONE;
    
    private final boolean forceRemap;
    private static final /* synthetic */ Remap[] $VALUES;
    
    public static Remap[] values() {
        return Remap.$VALUES.clone();
    }
    
    public static Remap valueOf(final String a1) {
        return Enum.valueOf(Remap.class, a1);
    }
    
    private Remap() {
        this(false);
    }
    
    private Remap(final boolean a1) {
        this.forceRemap = a1;
    }
    
    public boolean forceRemap() {
        return this.forceRemap;
    }
    
    static {
        $VALUES = new Remap[] { Remap.ALL, Remap.FORCE, Remap.ONLY_PREFIXED, Remap.NONE };
    }
}
