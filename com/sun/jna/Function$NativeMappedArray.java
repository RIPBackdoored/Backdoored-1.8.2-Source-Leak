package com.sun.jna;

private static class NativeMappedArray extends Memory implements PostCallRead
{
    private final NativeMapped[] original;
    
    public NativeMappedArray(final NativeMapped[] a1) {
        super(Native.getNativeSize(a1.getClass(), a1));
        this.setValue(0L, this.original = a1, this.original.getClass());
    }
    
    @Override
    public void read() {
        this.getValue(0L, this.original.getClass(), this.original);
    }
}
