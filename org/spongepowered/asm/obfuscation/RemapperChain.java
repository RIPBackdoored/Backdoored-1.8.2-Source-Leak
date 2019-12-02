package org.spongepowered.asm.obfuscation;

import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;

public class RemapperChain implements IRemapper
{
    private final List<IRemapper> remappers;
    
    public RemapperChain() {
        super();
        this.remappers = new ArrayList<IRemapper>();
    }
    
    @Override
    public String toString() {
        return String.format("RemapperChain[%d]", this.remappers.size());
    }
    
    public RemapperChain add(final IRemapper a1) {
        this.remappers.add(a1);
        return this;
    }
    
    @Override
    public String mapMethodName(final String v1, String v2, final String v3) {
        for (final IRemapper a2 : this.remappers) {
            final String a3 = a2.mapMethodName(v1, v2, v3);
            if (a3 != null && !a3.equals(v2)) {
                v2 = a3;
            }
        }
        return v2;
    }
    
    @Override
    public String mapFieldName(final String v1, String v2, final String v3) {
        for (final IRemapper a2 : this.remappers) {
            final String a3 = a2.mapFieldName(v1, v2, v3);
            if (a3 != null && !a3.equals(v2)) {
                v2 = a3;
            }
        }
        return v2;
    }
    
    @Override
    public String map(String v-1) {
        for (final IRemapper v1 : this.remappers) {
            final String a1 = v1.map(v-1);
            if (a1 != null && !a1.equals(v-1)) {
                v-1 = a1;
            }
        }
        return v-1;
    }
    
    @Override
    public String unmap(String v-1) {
        for (final IRemapper v1 : this.remappers) {
            final String a1 = v1.unmap(v-1);
            if (a1 != null && !a1.equals(v-1)) {
                v-1 = a1;
            }
        }
        return v-1;
    }
    
    @Override
    public String mapDesc(String v-1) {
        for (final IRemapper v1 : this.remappers) {
            final String a1 = v1.mapDesc(v-1);
            if (a1 != null && !a1.equals(v-1)) {
                v-1 = a1;
            }
        }
        return v-1;
    }
    
    @Override
    public String unmapDesc(String v-1) {
        for (final IRemapper v1 : this.remappers) {
            final String a1 = v1.unmapDesc(v-1);
            if (a1 != null && !a1.equals(v-1)) {
                v-1 = a1;
            }
        }
        return v-1;
    }
}
