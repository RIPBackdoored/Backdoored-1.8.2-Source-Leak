package com.sun.jna;

private static class PointerArray extends Memory implements PostCallRead
{
    private final Pointer[] original;
    
    public PointerArray(final Pointer[] v2) {
        super(Pointer.SIZE * (v2.length + 1));
        this.original = v2;
        for (int a1 = 0; a1 < v2.length; ++a1) {
            this.setPointer(a1 * Pointer.SIZE, v2[a1]);
        }
        this.setPointer(Pointer.SIZE * v2.length, null);
    }
    
    @Override
    public void read() {
        this.read(0L, this.original, 0, this.original.length);
    }
}
