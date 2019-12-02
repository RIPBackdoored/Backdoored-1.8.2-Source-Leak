package com.sun.jna.ptr;

public class ShortByReference extends ByReference
{
    public ShortByReference() {
        this((short)0);
    }
    
    public ShortByReference(final short a1) {
        super(2);
        this.setValue(a1);
    }
    
    public void setValue(final short a1) {
        this.getPointer().setShort(0L, a1);
    }
    
    public short getValue() {
        return this.getPointer().getShort(0L);
    }
}
