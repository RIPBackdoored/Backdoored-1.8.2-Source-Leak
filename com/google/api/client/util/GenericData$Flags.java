package com.google.api.client.util;

public enum Flags
{
    IGNORE_CASE;
    
    private static final /* synthetic */ Flags[] $VALUES;
    
    public static Flags[] values() {
        return Flags.$VALUES.clone();
    }
    
    public static Flags valueOf(final String a1) {
        return Enum.valueOf(Flags.class, a1);
    }
    
    static {
        $VALUES = new Flags[] { Flags.IGNORE_CASE };
    }
}
