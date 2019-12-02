package org.spongepowered.asm.obfuscation.mapping.common;

import org.spongepowered.asm.obfuscation.mapping.*;
import com.google.common.base.*;

public class MappingMethod implements IMapping<MappingMethod>
{
    private final String owner;
    private final String name;
    private final String desc;
    
    public MappingMethod(final String a1, final String a2) {
        this(getOwnerFromName(a1), getBaseName(a1), a2);
    }
    
    public MappingMethod(final String a1, final String a2, final String a3) {
        super();
        this.owner = a1;
        this.name = a2;
        this.desc = a3;
    }
    
    @Override
    public Type getType() {
        return Type.METHOD;
    }
    
    @Override
    public String getName() {
        if (this.name == null) {
            return null;
        }
        return ((this.owner != null) ? (this.owner + "/") : "") + this.name;
    }
    
    @Override
    public String getSimpleName() {
        return this.name;
    }
    
    @Override
    public String getOwner() {
        return this.owner;
    }
    
    @Override
    public String getDesc() {
        return this.desc;
    }
    
    @Override
    public MappingMethod getSuper() {
        return null;
    }
    
    public boolean isConstructor() {
        return "<init>".equals(this.name);
    }
    
    @Override
    public MappingMethod move(final String a1) {
        return new MappingMethod(a1, this.getSimpleName(), this.getDesc());
    }
    
    @Override
    public MappingMethod remap(final String a1) {
        return new MappingMethod(this.getOwner(), a1, this.getDesc());
    }
    
    @Override
    public MappingMethod transform(final String a1) {
        return new MappingMethod(this.getOwner(), this.getSimpleName(), a1);
    }
    
    @Override
    public MappingMethod copy() {
        return new MappingMethod(this.getOwner(), this.getSimpleName(), this.getDesc());
    }
    
    public MappingMethod addPrefix(final String a1) {
        final String v1 = this.getSimpleName();
        if (v1 == null || v1.startsWith(a1)) {
            return this;
        }
        return new MappingMethod(this.getOwner(), a1 + v1, this.getDesc());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName(), this.getDesc());
    }
    
    @Override
    public boolean equals(final Object a1) {
        return this == a1 || (a1 instanceof MappingMethod && Objects.equal(this.name, ((MappingMethod)a1).name) && Objects.equal(this.desc, ((MappingMethod)a1).desc));
    }
    
    @Override
    public String serialise() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        final String v1 = this.getDesc();
        return String.format("%s%s%s", this.getName(), (v1 != null) ? " " : "", (v1 != null) ? v1 : "");
    }
    
    private static String getBaseName(final String a1) {
        if (a1 == null) {
            return null;
        }
        final int v1 = a1.lastIndexOf(47);
        return (v1 > -1) ? a1.substring(v1 + 1) : a1;
    }
    
    private static String getOwnerFromName(final String a1) {
        if (a1 == null) {
            return null;
        }
        final int v1 = a1.lastIndexOf(47);
        return (v1 > -1) ? a1.substring(0, v1) : null;
    }
    
    @Override
    public /* bridge */ Object getSuper() {
        return this.getSuper();
    }
    
    @Override
    public /* bridge */ Object copy() {
        return this.copy();
    }
    
    @Override
    public /* bridge */ Object transform(final String a1) {
        return this.transform(a1);
    }
    
    @Override
    public /* bridge */ Object remap(final String a1) {
        return this.remap(a1);
    }
    
    @Override
    public /* bridge */ Object move(final String a1) {
        return this.move(a1);
    }
}
