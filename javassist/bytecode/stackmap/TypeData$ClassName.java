package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class ClassName extends TypeData
{
    private String name;
    
    public ClassName(final String a1) {
        super();
        this.name = a1;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public BasicType isBasicType() {
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return false;
    }
    
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
        return this.name.equals(a1.getName());
    }
    
    @Override
    public void setType(final String a1, final ClassPool a2) throws BadBytecode {
    }
    
    @Override
    public TypeData getArrayType(final int v0) throws NotFoundException {
        if (v0 == 0) {
            return this;
        }
        if (v0 > 0) {
            final char[] v = new char[v0];
            for (int a1 = 0; a1 < v0; ++a1) {
                v[a1] = '[';
            }
            String v2 = this.getName();
            if (v2.charAt(0) != '[') {
                v2 = "L" + v2.replace('.', '/') + ";";
            }
            return new ClassName(new String(v) + v2);
        }
        for (int v3 = 0; v3 < -v0; ++v3) {
            if (this.name.charAt(v3) != '[') {
                throw new NotFoundException("no " + v0 + " dimensional array type: " + this.getName());
            }
        }
        final char v4 = this.name.charAt(-v0);
        if (v4 == '[') {
            return new ClassName(this.name.substring(-v0));
        }
        if (v4 == 'L') {
            return new ClassName(this.name.substring(-v0 + 1, this.name.length() - 1).replace('/', '.'));
        }
        if (v4 == BasicType.access$100(TypeTag.DOUBLE)) {
            return TypeTag.DOUBLE;
        }
        if (v4 == BasicType.access$100(TypeTag.FLOAT)) {
            return TypeTag.FLOAT;
        }
        if (v4 == BasicType.access$100(TypeTag.LONG)) {
            return TypeTag.LONG;
        }
        return TypeTag.INTEGER;
    }
    
    @Override
    String toString2(final HashSet a1) {
        return this.name;
    }
}
