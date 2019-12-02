package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class UninitTypeVar extends AbsTypeVar
{
    protected TypeData type;
    
    public UninitTypeVar(final UninitData a1) {
        super();
        this.type = a1;
    }
    
    @Override
    public int getTypeTag() {
        return this.type.getTypeTag();
    }
    
    @Override
    public int getTypeData(final ConstPool a1) {
        return this.type.getTypeData(a1);
    }
    
    @Override
    public BasicType isBasicType() {
        return this.type.isBasicType();
    }
    
    @Override
    public boolean is2WordType() {
        return this.type.is2WordType();
    }
    
    @Override
    public boolean isUninit() {
        return this.type.isUninit();
    }
    
    @Override
    public boolean eq(final TypeData a1) {
        return this.type.eq(a1);
    }
    
    @Override
    public String getName() {
        return this.type.getName();
    }
    
    @Override
    protected TypeVar toTypeVar(final int a1) {
        return null;
    }
    
    @Override
    public TypeData join() {
        return this.type.join();
    }
    
    @Override
    public void setType(final String a1, final ClassPool a2) throws BadBytecode {
        this.type.setType(a1, a2);
    }
    
    @Override
    public void merge(final TypeData a1) {
        if (!a1.eq(this.type)) {
            this.type = TypeTag.TOP;
        }
    }
    
    @Override
    public void constructorCalled(final int a1) {
        this.type.constructorCalled(a1);
    }
    
    public int offset() {
        if (this.type instanceof UninitData) {
            return ((UninitData)this.type).offset;
        }
        throw new RuntimeException("not available");
    }
    
    @Override
    public TypeData getArrayType(final int a1) throws NotFoundException {
        return this.type.getArrayType(a1);
    }
    
    @Override
    String toString2(final HashSet a1) {
        return "";
    }
}
