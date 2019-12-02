package org.spongepowered.asm.mixin.transformer;

enum InitialiserInjectionMode
{
    DEFAULT, 
    SAFE;
    
    private static final /* synthetic */ InitialiserInjectionMode[] $VALUES;
    
    public static InitialiserInjectionMode[] values() {
        return InitialiserInjectionMode.$VALUES.clone();
    }
    
    public static InitialiserInjectionMode valueOf(final String a1) {
        return Enum.valueOf(InitialiserInjectionMode.class, a1);
    }
    
    static {
        $VALUES = new InitialiserInjectionMode[] { InitialiserInjectionMode.DEFAULT, InitialiserInjectionMode.SAFE };
    }
}
