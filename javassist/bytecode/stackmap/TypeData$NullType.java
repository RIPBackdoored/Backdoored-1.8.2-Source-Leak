package javassist.bytecode.stackmap;

import javassist.bytecode.*;

public static class NullType extends ClassName
{
    public NullType() {
        super("null-type");
    }
    
    @Override
    public int getTypeTag() {
        return 5;
    }
    
    @Override
    public boolean isNullType() {
        return true;
    }
    
    @Override
    public int getTypeData(final ConstPool a1) {
        return 0;
    }
    
    @Override
    public TypeData getArrayType(final int a1) {
        return this;
    }
}
