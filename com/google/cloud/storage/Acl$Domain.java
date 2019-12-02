package com.google.cloud.storage;

public static final class Domain extends Entity
{
    private static final long serialVersionUID = -3033025857280447253L;
    
    public Domain(final String a1) {
        super(Type.DOMAIN, a1);
    }
    
    public String getDomain() {
        return this.getValue();
    }
}
