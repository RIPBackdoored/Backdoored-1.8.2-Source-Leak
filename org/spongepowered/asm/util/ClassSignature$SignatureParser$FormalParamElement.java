package org.spongepowered.asm.util;

class FormalParamElement extends TokenElement
{
    private final TokenHandle handle;
    final /* synthetic */ SignatureParser this$1;
    
    FormalParamElement(final SignatureParser a1, final String a2) {
        this.this$1 = a1;
        a1.super();
        this.handle = a1.this$0.getType(a2);
        this.token = this.handle.asToken();
    }
}
