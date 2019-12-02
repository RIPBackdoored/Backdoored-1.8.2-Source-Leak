package org.spongepowered.asm.obfuscation.mapping.common;

import org.spongepowered.asm.obfuscation.mapping.*;
import com.google.common.base.*;

public class MappingField implements IMapping<MappingField>
{
    private final String owner;
    private final String name;
    private final String desc;
    
    public MappingField(final String a1, final String a2) {
        this(a1, a2, null);
    }
    
    public MappingField(final String a1, final String a2, final String a3) {
        super();
        this.owner = a1;
        this.name = a2;
        this.desc = a3;
    }
    
    @Override
    public Type getType() {
        return Type.FIELD;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public final String getSimpleName() {
        return this.name;
    }
    
    @Override
    public final String getOwner() {
        return this.owner;
    }
    
    @Override
    public final String getDesc() {
        return this.desc;
    }
    
    @Override
    public MappingField getSuper() {
        return null;
    }
    
    @Override
    public MappingField move(final String a1) {
        return new MappingField(a1, this.getName(), this.getDesc());
    }
    
    @Override
    public MappingField remap(final String a1) {
        return new MappingField(this.getOwner(), a1, this.getDesc());
    }
    
    @Override
    public MappingField transform(final String a1) {
        return new MappingField(this.getOwner(), this.getName(), a1);
    }
    
    @Override
    public MappingField copy() {
        return new MappingField(this.getOwner(), this.getName(), this.getDesc());
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(this.toString());
    }
    
    @Override
    public boolean equals(final Object a1) {
        return this == a1 || (a1 instanceof MappingField && Objects.equal(this.toString(), ((MappingField)a1).toString()));
    }
    
    @Override
    public String serialise() {
        return this.toString();
    }
    
    @Override
    public String toString() {
        return String.format("L%s;%s:%s", this.getOwner(), this.getName(), Strings.nullToEmpty(this.getDesc()));
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
