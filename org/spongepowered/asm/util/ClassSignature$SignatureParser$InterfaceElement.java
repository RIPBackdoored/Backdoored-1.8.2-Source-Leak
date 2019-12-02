package org.spongepowered.asm.util;

class InterfaceElement extends TokenElement
{
    final /* synthetic */ SignatureParser this$1;
    
    InterfaceElement(final SignatureParser a1) {
        this.this$1 = a1;
        a1.super();
    }
    
    @Override
    public void visitEnd() {
        this.this$1.this$0.addInterface(this.token);
    }
}
