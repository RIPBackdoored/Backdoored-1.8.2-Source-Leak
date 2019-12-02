package org.spongepowered.asm.util;

import org.spongepowered.asm.lib.signature.*;
import java.util.*;

class SignatureRemapper extends SignatureWriter
{
    private final Set<String> localTypeVars;
    final /* synthetic */ ClassSignature this$0;
    
    SignatureRemapper(final ClassSignature a1) {
        this.this$0 = a1;
        super();
        this.localTypeVars = new HashSet<String>();
    }
    
    @Override
    public void visitFormalTypeParameter(final String a1) {
        this.localTypeVars.add(a1);
        super.visitFormalTypeParameter(a1);
    }
    
    @Override
    public void visitTypeVariable(final String v2) {
        if (!this.localTypeVars.contains(v2)) {
            final TypeVar a1 = this.this$0.getTypeVar(v2);
            if (a1 != null) {
                super.visitTypeVariable(a1.toString());
                return;
            }
        }
        super.visitTypeVariable(v2);
    }
}
