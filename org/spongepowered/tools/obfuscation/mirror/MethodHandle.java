package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.common.*;
import javax.lang.model.element.*;
import org.spongepowered.tools.obfuscation.mirror.mapping.*;
import com.google.common.base.*;
import org.spongepowered.asm.obfuscation.mapping.*;

public class MethodHandle extends MemberHandle<MappingMethod>
{
    private final ExecutableElement element;
    private final TypeHandle ownerHandle;
    
    public MethodHandle(final TypeHandle a1, final ExecutableElement a2) {
        this(a1, a2, TypeUtils.getName(a2), TypeUtils.getDescriptor(a2));
    }
    
    public MethodHandle(final TypeHandle a1, final String a2, final String a3) {
        this(a1, null, a2, a3);
    }
    
    private MethodHandle(final TypeHandle a1, final ExecutableElement a2, final String a3, final String a4) {
        super((a1 != null) ? a1.getName() : null, a3, a4);
        this.element = a2;
        this.ownerHandle = a1;
    }
    
    public boolean isImaginary() {
        return this.element == null;
    }
    
    public ExecutableElement getElement() {
        return this.element;
    }
    
    @Override
    public Visibility getVisibility() {
        return TypeUtils.getVisibility(this.element);
    }
    
    @Override
    public MappingMethod asMapping(final boolean a1) {
        if (!a1) {
            return new MappingMethod(null, this.getName(), this.getDesc());
        }
        if (this.ownerHandle != null) {
            return new ResolvableMappingMethod(this.ownerHandle, this.getName(), this.getDesc());
        }
        return new MappingMethod(this.getOwner(), this.getName(), this.getDesc());
    }
    
    @Override
    public String toString() {
        final String v1 = (this.getOwner() != null) ? ("L" + this.getOwner() + ";") : "";
        final String v2 = Strings.nullToEmpty(this.getName());
        final String v3 = Strings.nullToEmpty(this.getDesc());
        return String.format("%s%s%s", v1, v2, v3);
    }
    
    public /* bridge */ IMapping asMapping(final boolean a1) {
        return this.asMapping(a1);
    }
}
