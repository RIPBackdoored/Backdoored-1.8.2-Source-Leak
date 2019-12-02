package com.sun.jna.ptr;

import com.sun.jna.*;

public abstract class ByReference extends PointerType
{
    protected ByReference(final int a1) {
        super();
        this.setPointer(new Memory(a1));
    }
}
