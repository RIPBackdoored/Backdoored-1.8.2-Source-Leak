package org.spongepowered.asm.mixin.transformer;

enum Type
{
    METHOD, 
    FIELD;
    
    private static final /* synthetic */ Type[] $VALUES;
    
    public static Type[] values() {
        return Type.$VALUES.clone();
    }
    
    public static Type valueOf(final String a1) {
        return Enum.valueOf(Type.class, a1);
    }
    
    static {
        $VALUES = new Type[] { Type.METHOD, Type.FIELD };
    }
}
