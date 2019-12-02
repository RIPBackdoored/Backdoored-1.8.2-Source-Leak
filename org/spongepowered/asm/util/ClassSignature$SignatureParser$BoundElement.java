package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;

class BoundElement extends TokenElement
{
    private final TokenElement type;
    private final boolean classBound;
    final /* synthetic */ SignatureParser this$1;
    
    BoundElement(final SignatureParser a1, final TokenElement a2, final boolean a3) {
        this.this$1 = a1;
        a1.super();
        this.type = a2;
        this.classBound = a3;
    }
    
    @Override
    public void visitClassType(final String a1) {
        this.token = this.type.token.addBound(a1, this.classBound);
    }
    
    @Override
    public void visitTypeArgument() {
        this.token.addTypeArgument('*');
    }
    
    @Override
    public SignatureVisitor visitTypeArgument(final char a1) {
        return this.this$1.new TypeArgElement(this, a1);
    }
}
