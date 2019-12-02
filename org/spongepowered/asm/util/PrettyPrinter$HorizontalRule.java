package org.spongepowered.asm.util;

import com.google.common.base.*;

class HorizontalRule implements ISpecialEntry
{
    private final char[] hrChars;
    final /* synthetic */ PrettyPrinter this$0;
    
    public HorizontalRule(final PrettyPrinter a1, final char... a2) {
        this.this$0 = a1;
        super();
        this.hrChars = a2;
    }
    
    @Override
    public String toString() {
        return Strings.repeat(new String(this.hrChars), this.this$0.width + 2);
    }
}
