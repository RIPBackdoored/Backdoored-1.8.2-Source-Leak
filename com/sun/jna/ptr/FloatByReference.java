package com.sun.jna.ptr;

public class FloatByReference extends ByReference
{
    public FloatByReference() {
        this(0.0f);
    }
    
    public FloatByReference(final float a1) {
        super(4);
        this.setValue(a1);
    }
    
    public void setValue(final float a1) {
        this.getPointer().setFloat(0L, a1);
    }
    
    public float getValue() {
        return this.getPointer().getFloat(0L);
    }
}
