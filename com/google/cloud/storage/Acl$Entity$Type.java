package com.google.cloud.storage;

public enum Type
{
    DOMAIN, 
    GROUP, 
    USER, 
    PROJECT, 
    UNKNOWN;
    
    private static final /* synthetic */ Type[] $VALUES;
    
    public static Type[] values() {
        return Type.$VALUES.clone();
    }
    
    public static Type valueOf(final String a1) {
        return Enum.valueOf(Type.class, a1);
    }
    
    static {
        $VALUES = new Type[] { Type.DOMAIN, Type.GROUP, Type.USER, Type.PROJECT, Type.UNKNOWN };
    }
}
