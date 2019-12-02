package javassist.bytecode;

public static class ClassType extends ObjectType
{
    String name;
    TypeArgument[] arguments;
    public static ClassType OBJECT;
    
    static ClassType make(final String a1, final int a2, final int a3, final TypeArgument[] a4, final ClassType a5) {
        if (a5 == null) {
            return new ClassType(a1, a2, a3, a4);
        }
        return new NestedClassType(a1, a2, a3, a4, a5);
    }
    
    ClassType(final String a1, final int a2, final int a3, final TypeArgument[] a4) {
        super();
        this.name = a1.substring(a2, a3).replace('/', '.');
        this.arguments = a4;
    }
    
    public ClassType(final String a1, final TypeArgument[] a2) {
        super();
        this.name = a1;
        this.arguments = a2;
    }
    
    public ClassType(final String a1) {
        this(a1, null);
    }
    
    public String getName() {
        return this.name;
    }
    
    public TypeArgument[] getTypeArguments() {
        return this.arguments;
    }
    
    public ClassType getDeclaringClass() {
        return null;
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        final ClassType v2 = this.getDeclaringClass();
        if (v2 != null) {
            v1.append(v2.toString()).append('.');
        }
        return this.toString2(v1);
    }
    
    private String toString2(final StringBuffer v0) {
        v0.append(this.name);
        if (this.arguments != null) {
            v0.append('<');
            for (int v = this.arguments.length, a1 = 0; a1 < v; ++a1) {
                if (a1 > 0) {
                    v0.append(", ");
                }
                v0.append(this.arguments[a1].toString());
            }
            v0.append('>');
        }
        return v0.toString();
    }
    
    @Override
    public String jvmTypeName() {
        final StringBuffer v1 = new StringBuffer();
        final ClassType v2 = this.getDeclaringClass();
        if (v2 != null) {
            v1.append(v2.jvmTypeName()).append('$');
        }
        return this.toString2(v1);
    }
    
    @Override
    void encode(final StringBuffer a1) {
        a1.append('L');
        this.encode2(a1);
        a1.append(';');
    }
    
    void encode2(final StringBuffer a1) {
        final ClassType v1 = this.getDeclaringClass();
        if (v1 != null) {
            v1.encode2(a1);
            a1.append('$');
        }
        a1.append(this.name.replace('.', '/'));
        if (this.arguments != null) {
            TypeArgument.encode(a1, this.arguments);
        }
    }
    
    static {
        ClassType.OBJECT = new ClassType("java.lang.Object", null);
    }
}
