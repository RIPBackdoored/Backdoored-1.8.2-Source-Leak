package com.sun.jna;

import java.nio.*;

private static class Buffers
{
    private Buffers() {
        super();
    }
    
    static boolean isBuffer(final Class<?> a1) {
        return Buffer.class.isAssignableFrom(a1);
    }
}
