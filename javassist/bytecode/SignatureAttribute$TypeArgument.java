package javassist.bytecode;

public static class TypeArgument
{
    ObjectType arg;
    char wildcard;
    
    TypeArgument(final ObjectType a1, final char a2) {
        super();
        this.arg = a1;
        this.wildcard = a2;
    }
    
    public TypeArgument(final ObjectType a1) {
        this(a1, ' ');
    }
    
    public TypeArgument() {
        this(null, '*');
    }
    
    public static TypeArgument subclassOf(final ObjectType a1) {
        return new TypeArgument(a1, '+');
    }
    
    public static TypeArgument superOf(final ObjectType a1) {
        return new TypeArgument(a1, '-');
    }
    
    public char getKind() {
        return this.wildcard;
    }
    
    public boolean isWildcard() {
        return this.wildcard != ' ';
    }
    
    public ObjectType getType() {
        return this.arg;
    }
    
    @Override
    public String toString() {
        if (this.wildcard == '*') {
            return "?";
        }
        final String v1 = this.arg.toString();
        if (this.wildcard == ' ') {
            return v1;
        }
        if (this.wildcard == '+') {
            return "? extends " + v1;
        }
        return "? super " + v1;
    }
    
    static void encode(final StringBuffer v1, final TypeArgument[] v2) {
        v1.append('<');
        for (int a2 = 0; a2 < v2.length; ++a2) {
            final TypeArgument a3 = v2[a2];
            if (a3.isWildcard()) {
                v1.append(a3.wildcard);
            }
            if (a3.getType() != null) {
                a3.getType().encode(v1);
            }
        }
        v1.append('>');
    }
}
