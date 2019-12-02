package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.*;

public class MappingFieldSrg extends MappingField
{
    private final String srg;
    
    public MappingFieldSrg(final String a1) {
        super(getOwnerFromSrg(a1), getNameFromSrg(a1), null);
        this.srg = a1;
    }
    
    public MappingFieldSrg(final MappingField a1) {
        super(a1.getOwner(), a1.getName(), null);
        this.srg = a1.getOwner() + "/" + a1.getName();
    }
    
    @Override
    public String serialise() {
        return this.srg;
    }
    
    private static String getNameFromSrg(final String a1) {
        if (a1 == null) {
            return null;
        }
        final int v1 = a1.lastIndexOf(47);
        return (v1 > -1) ? a1.substring(v1 + 1) : a1;
    }
    
    private static String getOwnerFromSrg(final String a1) {
        if (a1 == null) {
            return null;
        }
        final int v1 = a1.lastIndexOf(47);
        return (v1 > -1) ? a1.substring(0, v1) : null;
    }
}
