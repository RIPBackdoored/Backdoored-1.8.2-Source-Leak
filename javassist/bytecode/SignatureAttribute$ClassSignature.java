package javassist.bytecode;

public static class ClassSignature
{
    TypeParameter[] params;
    ClassType superClass;
    ClassType[] interfaces;
    
    public ClassSignature(final TypeParameter[] a1, final ClassType a2, final ClassType[] a3) {
        super();
        this.params = ((a1 == null) ? new TypeParameter[0] : a1);
        this.superClass = ((a2 == null) ? ClassType.OBJECT : a2);
        this.interfaces = ((a3 == null) ? new ClassType[0] : a3);
    }
    
    public ClassSignature(final TypeParameter[] a1) {
        this(a1, null, null);
    }
    
    public TypeParameter[] getParameters() {
        return this.params;
    }
    
    public ClassType getSuperClass() {
        return this.superClass;
    }
    
    public ClassType[] getInterfaces() {
        return this.interfaces;
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        TypeParameter.toString(v1, this.params);
        v1.append(" extends ").append(this.superClass);
        if (this.interfaces.length > 0) {
            v1.append(" implements ");
            Type.toString(v1, this.interfaces);
        }
        return v1.toString();
    }
    
    public String encode() {
        final StringBuffer v0 = new StringBuffer();
        if (this.params.length > 0) {
            v0.append('<');
            for (int v2 = 0; v2 < this.params.length; ++v2) {
                this.params[v2].encode(v0);
            }
            v0.append('>');
        }
        this.superClass.encode(v0);
        for (int v2 = 0; v2 < this.interfaces.length; ++v2) {
            this.interfaces[v2].encode(v0);
        }
        return v0.toString();
    }
}
