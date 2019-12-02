package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.lib.*;

public class Local
{
    int ord;
    String name;
    Type type;
    final /* synthetic */ Context this$0;
    
    public Local(final Context a1, final String a2, final Type a3) {
        this.this$0 = a1;
        super();
        this.ord = 0;
        this.name = a2;
        this.type = a3;
    }
    
    @Override
    public String toString() {
        return String.format("Local[ordinal=%d, name=%s, type=%s]", this.ord, this.name, this.type);
    }
}
