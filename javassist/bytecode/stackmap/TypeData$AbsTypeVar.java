package javassist.bytecode.stackmap;

import javassist.bytecode.*;

public abstract static class AbsTypeVar extends TypeData
{
    public AbsTypeVar() {
        super();
    }
    
    public abstract void merge(final TypeData p0);
    
    @Override
    public int getTypeTag() {
        return 7;
    }
    
    @Override
    public int getTypeData(final ConstPool a1) {
        return a1.addClassInfo(this.getName());
    }
    
    @Override
    public boolean eq(final TypeData a1) {
        return this.getName().equals(a1.getName());
    }
}
