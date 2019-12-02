package javassist.bytecode;

public abstract static class Type
{
    public Type() {
        super();
    }
    
    abstract void encode(final StringBuffer p0);
    
    static void toString(final StringBuffer a2, final Type[] v1) {
        for (int a3 = 0; a3 < v1.length; ++a3) {
            if (a3 > 0) {
                a2.append(", ");
            }
            a2.append(v1[a3]);
        }
    }
    
    public String jvmTypeName() {
        return this.toString();
    }
}
