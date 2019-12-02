package com.sun.jna;

public abstract class PointerType implements NativeMapped
{
    private Pointer pointer;
    
    protected PointerType() {
        super();
        this.pointer = Pointer.NULL;
    }
    
    protected PointerType(final Pointer a1) {
        super();
        this.pointer = a1;
    }
    
    @Override
    public Class<?> nativeType() {
        return Pointer.class;
    }
    
    @Override
    public Object toNative() {
        return this.getPointer();
    }
    
    public Pointer getPointer() {
        return this.pointer;
    }
    
    public void setPointer(final Pointer a1) {
        this.pointer = a1;
    }
    
    @Override
    public Object fromNative(final Object v-1, final FromNativeContext v0) {
        if (v-1 == null) {
            return null;
        }
        try {
            final PointerType a1 = (PointerType)this.getClass().newInstance();
            a1.pointer = (Pointer)v-1;
            return a1;
        }
        catch (InstantiationException a2) {
            throw new IllegalArgumentException("Can't instantiate " + this.getClass());
        }
        catch (IllegalAccessException v) {
            throw new IllegalArgumentException("Not allowed to instantiate " + this.getClass());
        }
    }
    
    @Override
    public int hashCode() {
        return (this.pointer != null) ? this.pointer.hashCode() : 0;
    }
    
    @Override
    public boolean equals(final Object v2) {
        if (v2 == this) {
            return true;
        }
        if (!(v2 instanceof PointerType)) {
            return false;
        }
        final Pointer a1 = ((PointerType)v2).getPointer();
        if (this.pointer == null) {
            return a1 == null;
        }
        return this.pointer.equals(a1);
    }
    
    @Override
    public String toString() {
        return (this.pointer == null) ? "NULL" : (this.pointer.toString() + " (" + super.toString() + ")");
    }
}
