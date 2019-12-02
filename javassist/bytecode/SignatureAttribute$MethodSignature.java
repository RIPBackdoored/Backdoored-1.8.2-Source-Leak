package javassist.bytecode;

public static class MethodSignature
{
    TypeParameter[] typeParams;
    Type[] params;
    Type retType;
    ObjectType[] exceptions;
    
    public MethodSignature(final TypeParameter[] a1, final Type[] a2, final Type a3, final ObjectType[] a4) {
        super();
        this.typeParams = ((a1 == null) ? new TypeParameter[0] : a1);
        this.params = ((a2 == null) ? new Type[0] : a2);
        this.retType = ((a3 == null) ? new BaseType("void") : a3);
        this.exceptions = ((a4 == null) ? new ObjectType[0] : a4);
    }
    
    public TypeParameter[] getTypeParameters() {
        return this.typeParams;
    }
    
    public Type[] getParameterTypes() {
        return this.params;
    }
    
    public Type getReturnType() {
        return this.retType;
    }
    
    public ObjectType[] getExceptionTypes() {
        return this.exceptions;
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        TypeParameter.toString(v1, this.typeParams);
        v1.append(" (");
        Type.toString(v1, this.params);
        v1.append(") ");
        v1.append(this.retType);
        if (this.exceptions.length > 0) {
            v1.append(" throws ");
            Type.toString(v1, this.exceptions);
        }
        return v1.toString();
    }
    
    public String encode() {
        final StringBuffer v0 = new StringBuffer();
        if (this.typeParams.length > 0) {
            v0.append('<');
            for (int v2 = 0; v2 < this.typeParams.length; ++v2) {
                this.typeParams[v2].encode(v0);
            }
            v0.append('>');
        }
        v0.append('(');
        for (int v2 = 0; v2 < this.params.length; ++v2) {
            this.params[v2].encode(v0);
        }
        v0.append(')');
        this.retType.encode(v0);
        if (this.exceptions.length > 0) {
            for (int v2 = 0; v2 < this.exceptions.length; ++v2) {
                v0.append('^');
                this.exceptions[v2].encode(v0);
            }
        }
        return v0.toString();
    }
}
