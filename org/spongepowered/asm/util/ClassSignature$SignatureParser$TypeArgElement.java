package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

class TypeArgElement extends TokenElement
{
    private final TokenElement type;
    private final char wildcard;
    final /* synthetic */ SignatureParser this$1;
    
    TypeArgElement(final SignatureParser a1, final TokenElement a2, final char a3) {
        this.this$1 = a1;
        a1.super();
        this.type = a2;
        this.wildcard = a3;
    }
    
    @Override
    public SignatureVisitor visitArrayType() {
        this.type.setArray();
        return this;
    }
    
    @Override
    public void visitBaseType(final char a1) {
        this.token = this.type.addTypeArgument(a1).asToken();
    }
    
    @Override
    public void visitTypeVariable(final String a1) {
        final TokenHandle v1 = this.this$1.this$0.getType(a1);
        this.token = this.type.addTypeArgument(v1).setWildcard(this.wildcard).asToken();
    }
    
    @Override
    public void visitClassType(final String a1) {
        this.token = this.type.addTypeArgument(a1).setWildcard(this.wildcard).asToken();
    }
    
    @Override
    public void visitTypeArgument() {
        this.token.addTypeArgument('*');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        return this.this$1.new TypeArgElement(this, a1);
    }
    
    @Override
    public void visitEnd() {
    }
}
