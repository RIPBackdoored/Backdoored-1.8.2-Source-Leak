package javassist.bytecode;

public static class TypeVariable extends ObjectType
{
    String name;
    
    TypeVariable(final String a1, final int a2, final int a3) {
        super();
        this.name = a1.substring(a2, a3);
    }
    
    public TypeVariable(final String a1) {
        super();
        this.name = a1;
    }
    
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    void encode(final StringBuffer a1) {
        a1.append('T').append(this.name).append(';');
    }
}
