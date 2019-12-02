package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class ArrayElement extends AbsTypeVar
{
    private AbsTypeVar array;
    
    private ArrayElement(final AbsTypeVar a1) {
        super();
        this.array = a1;
    }
    
    public static TypeData make(final TypeData a1) throws BadBytecode {
        if (a1 instanceof ArrayType) {
            return ((ArrayType)a1).elementType();
        }
        if (a1 instanceof AbsTypeVar) {
            return new ArrayElement((AbsTypeVar)a1);
        }
        if (a1 instanceof ClassName && !a1.isNullType()) {
            return new ClassName(typeName(a1.getName()));
        }
        throw new BadBytecode("bad AASTORE: " + a1);
    }
    
    @Override
    public void merge(final TypeData v2) {
        try {
            if (!v2.isNullType()) {
                this.array.merge(ArrayType.make(v2));
            }
        }
        catch (BadBytecode a1) {
            throw new RuntimeException("fatal: " + a1);
        }
    }
    
    @Override
    public String getName() {
        return typeName(this.array.getName());
    }
    
    public AbsTypeVar arrayType() {
        return this.array;
    }
    
    @Override
    public BasicType isBasicType() {
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return false;
    }
    
    private static String typeName(final String v1) {
        if (v1.length() > 1 && v1.charAt(0) == '[') {
            final char a1 = v1.charAt(1);
            if (a1 == 'L') {
                return v1.substring(2, v1.length() - 1).replace('/', '.');
            }
            if (a1 == '[') {
                return v1.substring(1);
            }
        }
        return "java.lang.Object";
    }
    
    @Override
    public void setType(final String a1, final ClassPool a2) throws BadBytecode {
        this.array.setType(ArrayType.typeName(a1), a2);
    }
    
    @Override
    protected TypeVar toTypeVar(final int a1) {
        return this.array.toTypeVar(a1 - 1);
    }
    
    @Override
    public TypeData getArrayType(final int a1) throws NotFoundException {
        return this.array.getArrayType(a1 - 1);
    }
    
    @Override
    public int dfs(final ArrayList a1, final int a2, final ClassPool a3) throws NotFoundException {
        return this.array.dfs(a1, a2, a3);
    }
    
    @Override
    String toString2(final HashSet a1) {
        return "*" + this.array.toString2(a1);
    }
    
    static /* bridge */ String access$000(final String a1) {
        return typeName(a1);
    }
}
