package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

protected static class BasicType extends TypeData
{
    private String name;
    private int typeTag;
    private char decodedName;
    
    public BasicType(final String a1, final int a2, final char a3) {
        super();
        this.name = a1;
        this.typeTag = a2;
        this.decodedName = a3;
    }
    
    @Override
    public int getTypeTag() {
        return this.typeTag;
    }
    
    @Override
    public int getTypeData(final ConstPool a1) {
        return 0;
    }
    
    @Override
    public TypeData join() {
        if (this == TypeTag.TOP) {
            return this;
        }
        return super.join();
    }
    
    @Override
    public BasicType isBasicType() {
        return this;
    }
    
    @Override
    public boolean is2WordType() {
        return this.typeTag == 4 || this.typeTag == 3;
    }
    
    @Override
    public boolean eq(final TypeData a1) {
        return this == a1;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public char getDecodedName() {
        return this.decodedName;
    }
    
    @Override
    public void setType(final String a1, final ClassPool a2) throws BadBytecode {
        throw new BadBytecode("conflict: " + this.name + " and " + a1);
    }
    
    @Override
    public TypeData getArrayType(final int v0) throws NotFoundException {
        if (this == TypeTag.TOP) {
            return this;
        }
        if (v0 < 0) {
            throw new NotFoundException("no element type: " + this.name);
        }
        if (v0 == 0) {
            return this;
        }
        final char[] v = new char[v0 + 1];
        for (int a1 = 0; a1 < v0; ++a1) {
            v[a1] = '[';
        }
        v[v0] = this.decodedName;
        return new ClassName(new String(v));
    }
    
    @Override
    String toString2(final HashSet a1) {
        return this.name;
    }
    
    static /* synthetic */ char access$100(final BasicType a1) {
        return a1.decodedName;
    }
}
