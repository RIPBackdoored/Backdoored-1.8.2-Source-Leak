package org.spongepowered.asm.util;

class SuperClassElement extends TokenElement
{
    final /* synthetic */ SignatureParser this$1;
    
    SuperClassElement(final SignatureParser a1) {
        this.this$1 = a1;
        a1.super();
    }
    
    @Override
    public void visitEnd() {
        this.this$1.this$0.setSuperClass(this.token);
    }
}
