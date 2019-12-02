package org.spongepowered.tools.obfuscation.mirror.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import org.spongepowered.tools.obfuscation.mirror.*;
import java.util.*;

public final class ResolvableMappingMethod extends MappingMethod
{
    private final TypeHandle ownerHandle;
    
    public ResolvableMappingMethod(final TypeHandle a1, final String a2, final String a3) {
        super(a1.getName(), a2, a3);
        this.ownerHandle = a1;
    }
    
    @Override
    public MappingMethod getSuper() {
        if (this.ownerHandle == null) {
            return super.getSuper();
        }
        final String simpleName = this.getSimpleName();
        final String desc = this.getDesc();
        final String javaSignature = TypeUtils.getJavaSignature(desc);
        final TypeHandle superclass = this.ownerHandle.getSuperclass();
        if (superclass != null && superclass.findMethod(simpleName, javaSignature) != null) {
            return superclass.getMappingMethod(simpleName, desc);
        }
        for (final TypeHandle v1 : this.ownerHandle.getInterfaces()) {
            if (v1.findMethod(simpleName, javaSignature) != null) {
                return v1.getMappingMethod(simpleName, desc);
            }
        }
        if (superclass != null) {
            return superclass.getMappingMethod(simpleName, desc).getSuper();
        }
        return super.getSuper();
    }
    
    public MappingMethod move(final TypeHandle a1) {
        return new ResolvableMappingMethod(a1, this.getSimpleName(), this.getDesc());
    }
    
    @Override
    public MappingMethod remap(final String a1) {
        return new ResolvableMappingMethod(this.ownerHandle, a1, this.getDesc());
    }
    
    @Override
    public MappingMethod transform(final String a1) {
        return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), a1);
    }
    
    @Override
    public MappingMethod copy() {
        return new ResolvableMappingMethod(this.ownerHandle, this.getSimpleName(), this.getDesc());
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
}
