package com.sun.jna;

public class NativeLong extends IntegerType
{
    private static final long serialVersionUID = 1L;
    public static final int SIZE;
    
    public NativeLong() {
        this(0L);
    }
    
    public NativeLong(final long a1) {
        this(a1, false);
    }
    
    public NativeLong(final long a1, final boolean a2) {
        super(NativeLong.SIZE, a1, a2);
    }
    
    static {
        SIZE = Native.LONG_SIZE;
    }
}
