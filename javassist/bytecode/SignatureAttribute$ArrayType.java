package javassist.bytecode;

public static class ArrayType extends ObjectType
{
    int dim;
    Type componentType;
    
    public ArrayType(final int a1, final Type a2) {
        super();
        this.dim = a1;
        this.componentType = a2;
    }
    
    public int getDimension() {
        return this.dim;
    }
    
    public Type getComponentType() {
        return this.componentType;
    }
    
    @Override
    public String toString() {
        final StringBuffer v0 = new StringBuffer(this.componentType.toString());
        for (int v2 = 0; v2 < this.dim; ++v2) {
            v0.append("[]");
        }
        return v0.toString();
    }
    
    @Override
    void encode(final StringBuffer v2) {
        for (int a1 = 0; a1 < this.dim; ++a1) {
            v2.append('[');
        }
        this.componentType.encode(v2);
    }
}
