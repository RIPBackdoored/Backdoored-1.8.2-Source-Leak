package com.google.cloud.storage;

public enum HmacKeyState
{
    ACTIVE("ACTIVE"), 
    INACTIVE("INACTIVE"), 
    DELETED("DELETED");
    
    private final String state;
    private static final /* synthetic */ HmacKeyState[] $VALUES;
    
    public static HmacKeyState[] values() {
        return HmacKeyState.$VALUES.clone();
    }
    
    public static HmacKeyState valueOf(final String a1) {
        return Enum.valueOf(HmacKeyState.class, a1);
    }
    
    private HmacKeyState(final String a1) {
        this.state = a1;
    }
    
    static {
        $VALUES = new HmacKeyState[] { HmacKeyState.ACTIVE, HmacKeyState.INACTIVE, HmacKeyState.DELETED };
    }
}
