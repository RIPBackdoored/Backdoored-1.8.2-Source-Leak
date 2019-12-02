package org.yaml.snakeyaml.introspector;

public enum BeanAccess
{
    DEFAULT, 
    FIELD, 
    PROPERTY;
    
    private static final /* synthetic */ BeanAccess[] $VALUES;
    
    public static BeanAccess[] values() {
        return BeanAccess.$VALUES.clone();
    }
    
    public static BeanAccess valueOf(final String name) {
        return Enum.valueOf(BeanAccess.class, name);
    }
    
    static {
        $VALUES = new BeanAccess[] { BeanAccess.DEFAULT, BeanAccess.FIELD, BeanAccess.PROPERTY };
    }
}
