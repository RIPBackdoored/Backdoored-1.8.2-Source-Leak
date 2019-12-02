package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import javassist.*;
import java.util.*;

public static class ArrayType extends AbsTypeVar
{
    private AbsTypeVar element;
    
    private ArrayType(final AbsTypeVar a1) {
        super();
        this.element = a1;
    }
    
    static TypeData make(final TypeData a1) throws BadBytecode {
        if (a1 instanceof ArrayElement) {
            return ((ArrayElement)a1).arrayType();
        }
        if (a1 instanceof AbsTypeVar) {
            return new ArrayType((AbsTypeVar)a1);
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
                this.element.merge(ArrayElement.make(v2));
            }
        }
        catch (BadBytecode a1) {
            throw new RuntimeException("fatal: " + a1);
        }
    }
    
    @Override
    public String getName() {
        return typeName(this.element.getName());
    }
    
    public AbsTypeVar elementType() {
        return this.element;
    }
    
    @Override
    public BasicType isBasicType() {
        return null;
    }
    
    @Override
    public boolean is2WordType() {
        return false;
    }
    
    public static String typeName(final String a1) {
        if (a1.charAt(0) == '[') {
            return "[" + a1;
        }
        return "[L" + a1.replace('.', '/') + ";";
    }
    
    @Override
    public void setType(final String a1, final ClassPool a2) throws BadBytecode {
        this.element.setType(ArrayElement.access$000(a1), a2);
    }
    
    @Override
    protected TypeVar toTypeVar(final int a1) {
        return this.element.toTypeVar(a1 + 1);
    }
    
    @Override
    public TypeData getArrayType(final int a1) throws NotFoundException {
        return this.element.getArrayType(a1 + 1);
    }
    
    @Override
    public int dfs(final ArrayList a1, final int a2, final ClassPool a3) throws NotFoundException {
        return this.element.dfs(a1, a2, a3);
    }
    
    @Override
    String toString2(final HashSet a1) {
        return "[" + this.element.toString2(a1);
    }
}
