package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;

public static class UninitData extends ClassName
{
    int offset;
    boolean initialized;
    
    UninitData(final int a1, final String a2) {
        super(a2);
        this.offset = a1;
        this.initialized = false;
    }
    
    public UninitData copy() {
        return new UninitData(this.offset, this.getName());
    }
    
    @Override
    public int getTypeTag() {
        return 8;
    }
    
    @Override
    public int getTypeData(final ConstPool a1) {
        return this.offset;
    }
    
    @Override
    public TypeData join() {
        if (this.initialized) {
            return new TypeVar(new ClassName(this.getName()));
        }
        return new UninitTypeVar(this.copy());
    }
    
    @Override
    public boolean isUninit() {
        return true;
    }
    
    @Override
    public boolean eq(final TypeData v2) {
        if (v2 instanceof UninitData) {
            final UninitData a1 = (UninitData)v2;
            return this.offset == a1.offset && this.getName().equals(a1.getName());
        }
        return false;
    }
    
    public int offset() {
        return this.offset;
    }
    
    @Override
    public void constructorCalled(final int a1) {
        if (a1 == this.offset) {
            this.initialized = true;
        }
    }
    
    @Override
    String toString2(final HashSet a1) {
        return this.getName() + "," + this.offset;
    }
}
