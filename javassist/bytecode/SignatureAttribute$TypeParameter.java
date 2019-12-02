package javassist.bytecode;

public static class TypeParameter
{
    String name;
    ObjectType superClass;
    ObjectType[] superInterfaces;
    
    TypeParameter(final String a1, final int a2, final int a3, final ObjectType a4, final ObjectType[] a5) {
        super();
        this.name = a1.substring(a2, a3);
        this.superClass = a4;
        this.superInterfaces = a5;
    }
    
    public TypeParameter(final String a1, final ObjectType a2, final ObjectType[] a3) {
        super();
        this.name = a1;
        this.superClass = a2;
        if (a3 == null) {
            this.superInterfaces = new ObjectType[0];
        }
        else {
            this.superInterfaces = a3;
        }
    }
    
    public TypeParameter(final String a1) {
        this(a1, null, null);
    }
    
    public String getName() {
        return this.name;
    }
    
    public ObjectType getClassBound() {
        return this.superClass;
    }
    
    public ObjectType[] getInterfaceBound() {
        return this.superInterfaces;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer(this.getName());
        if (this.superClass != null) {
            sb.append(" extends ").append(this.superClass.toString());
        }
        final int v0 = this.superInterfaces.length;
        if (v0 > 0) {
            for (int v2 = 0; v2 < v0; ++v2) {
                if (v2 > 0 || this.superClass != null) {
                    sb.append(" & ");
                }
                else {
                    sb.append(" extends ");
                }
                sb.append(this.superInterfaces[v2].toString());
            }
        }
        return sb.toString();
    }
    
    static void toString(final StringBuffer a2, final TypeParameter[] v1) {
        a2.append('<');
        for (int a3 = 0; a3 < v1.length; ++a3) {
            if (a3 > 0) {
                a2.append(", ");
            }
            a2.append(v1[a3]);
        }
        a2.append('>');
    }
    
    void encode(final StringBuffer v2) {
        v2.append(this.name);
        if (this.superClass == null) {
            v2.append(":Ljava/lang/Object;");
        }
        else {
            v2.append(':');
            this.superClass.encode(v2);
        }
        for (int a1 = 0; a1 < this.superInterfaces.length; ++a1) {
            v2.append(':');
            this.superInterfaces[a1].encode(v2);
        }
    }
}
