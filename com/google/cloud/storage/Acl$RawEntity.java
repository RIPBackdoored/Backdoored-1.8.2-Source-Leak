package com.google.cloud.storage;

public static final class RawEntity extends Entity
{
    private static final long serialVersionUID = 3966205614223053950L;
    
    RawEntity(final String a1) {
        super(Type.UNKNOWN, a1);
    }
    
    @Override
    String toPb() {
        return this.getValue();
    }
}
