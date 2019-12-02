package org.spongepowered.tools.obfuscation;

import org.spongepowered.asm.util.*;

final class RemapperProxy implements ObfuscationUtil.IClassRemapper
{
    final /* synthetic */ ObfuscationEnvironment this$0;
    
    RemapperProxy(final ObfuscationEnvironment a1) {
        this.this$0 = a1;
        super();
    }
    
    @Override
    public String map(final String a1) {
        if (this.this$0.mappingProvider == null) {
            return null;
        }
        return this.this$0.mappingProvider.getClassMapping(a1);
    }
    
    @Override
    public String unmap(final String a1) {
        if (this.this$0.mappingProvider == null) {
            return null;
        }
        return this.this$0.mappingProvider.getClassMapping(a1);
    }
}
