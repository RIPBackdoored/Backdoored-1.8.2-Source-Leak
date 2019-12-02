package org.spongepowered.asm.lib.commons;

import java.util.*;

public class SimpleRemapper extends Remapper
{
    private final Map<String, String> mapping;
    
    public SimpleRemapper(final Map<String, String> a1) {
        super();
        this.mapping = a1;
    }
    
    public SimpleRemapper(final String a1, final String a2) {
        super();
        this.mapping = Collections.singletonMap(a1, a2);
    }
    
    @Override
    public String mapMethodName(final String a1, final String a2, final String a3) {
        final String v1 = this.map(a1 + '.' + a2 + a3);
        return (v1 == null) ? a2 : v1;
    }
    
    @Override
    public String mapInvokeDynamicMethodName(final String a1, final String a2) {
        final String v1 = this.map('.' + a1 + a2);
        return (v1 == null) ? a1 : v1;
    }
    
    @Override
    public String mapFieldName(final String a1, final String a2, final String a3) {
        final String v1 = this.map(a1 + '.' + a2);
        return (v1 == null) ? a2 : v1;
    }
    
    @Override
    public String map(final String a1) {
        return this.mapping.get(a1);
    }
}
