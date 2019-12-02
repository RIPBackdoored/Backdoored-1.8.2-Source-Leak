package org.spongepowered.asm.util;

class TokenHandle implements IToken
{
    final Token token;
    boolean array;
    char wildcard;
    final /* synthetic */ ClassSignature this$0;
    
    TokenHandle(final ClassSignature a1) {
        this(a1, new Token());
    }
    
    TokenHandle(final ClassSignature a1, final Token a2) {
        this.this$0 = a1;
        super();
        this.token = a2;
    }
    
    @Override
    public IToken setArray(final boolean a1) {
        this.array |= a1;
        return this;
    }
    
    @Override
    public IToken setWildcard(final char a1) {
        if ("+-".indexOf(a1) > -1) {
            this.wildcard = a1;
        }
        return this;
    }
    
    @Override
    public String asBound() {
        return this.token.asBound();
    }
    
    @Override
    public String asType() {
        final StringBuilder v1 = new StringBuilder();
        if (this.wildcard > '\0') {
            v1.append(this.wildcard);
        }
        if (this.array) {
            v1.append('[');
        }
        return v1.append(this.this$0.getTypeVar(this)).toString();
    }
    
    @Override
    public Token asToken() {
        return this.token;
    }
    
    @Override
    public String toString() {
        return this.token.toString();
    }
    
    public TokenHandle clone() {
        return this.this$0.new TokenHandle(this.token);
    }
    
    public /* bridge */ Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
}
