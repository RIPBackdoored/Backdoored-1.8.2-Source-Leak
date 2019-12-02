package javassist.bytecode;

public static class NestedClassType extends ClassType
{
    ClassType parent;
    
    NestedClassType(final String a1, final int a2, final int a3, final TypeArgument[] a4, final ClassType a5) {
        super(a1, a2, a3, a4);
        this.parent = a5;
    }
    
    public NestedClassType(final ClassType a1, final String a2, final TypeArgument[] a3) {
        super(a2, a3);
        this.parent = a1;
    }
    
    @Override
    public ClassType getDeclaringClass() {
        return this.parent;
    }
}
