package com.sun.jna;

import java.lang.reflect.*;

public class StructureWriteContext extends ToNativeContext
{
    private Structure struct;
    private Field field;
    
    StructureWriteContext(final Structure a1, final Field a2) {
        super();
        this.struct = a1;
        this.field = a2;
    }
    
    public Structure getStructure() {
        return this.struct;
    }
    
    public Field getField() {
        return this.field;
    }
}
