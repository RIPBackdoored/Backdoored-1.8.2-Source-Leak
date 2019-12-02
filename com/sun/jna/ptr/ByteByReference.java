package com.sun.jna.ptr;

public class ByteByReference extends ByReference
{
    public ByteByReference() {
        this((byte)0);
    }
    
    public ByteByReference(final byte a1) {
        super(1);
        this.setValue(a1);
    }
    
    public void setValue(final byte a1) {
        this.getPointer().setByte(0L, a1);
    }
    
    public byte getValue() {
        return this.getPointer().getByte(0L);
    }
}
