package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

abstract class TokenElement extends SignatureElement
{
    protected Token token;
    private boolean array;
    final /* synthetic */ SignatureParser this$1;
    
    TokenElement(final SignatureParser a1) {
        this.this$1 = a1;
        a1.super();
    }
    
    public Token getToken() {
        if (this.token == null) {
            this.token = new Token();
        }
        return this.token;
    }
    
    protected void setArray() {
        this.array = true;
    }
    
    private boolean getArray() {
        final boolean v1 = this.array;
        this.array = false;
        return v1;
    }
    
    @Override
    public void visitClassType(final String a1) {
        this.getToken().setType(a1);
    }
    
    @Override
    public SignatureVisitor visitClassBound() {
        this.getToken();
        return this.this$1.new BoundElement(this, true);
    }
    
    @Override
    public SignatureVisitor visitInterfaceBound() {
        this.getToken();
        return this.this$1.new BoundElement(this, false);
    }
    
    @Override
    public void visitInnerClassType(final String a1) {
        this.token.addInnerClass(a1);
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.setArray();
        return this;
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        return this.this$1.new TypeArgElement(this, a1);
    }
    
    Token addTypeArgument() {
        return this.token.addTypeArgument('*').asToken();
    }
    
    IToken addTypeArgument(final char a1) {
        return this.token.addTypeArgument(a1).setArray(this.getArray());
    }
    
    IToken addTypeArgument(final String a1) {
        return this.token.addTypeArgument(a1).setArray(this.getArray());
    }
    
    IToken addTypeArgument(final Token a1) {
        return this.token.addTypeArgument(a1).setArray(this.getArray());
    }
    
    IToken addTypeArgument(final TokenHandle a1) {
        return this.token.addTypeArgument(a1).setArray(this.getArray());
    }
}
