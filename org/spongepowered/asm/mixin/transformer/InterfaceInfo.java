package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.*;
import org.spongepowered.asm.mixin.transformer.throwables.*;
import org.spongepowered.asm.mixin.extensibility.*;
import java.util.*;
import org.spongepowered.asm.mixin.transformer.meta.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.lib.tree.*;

public final class InterfaceInfo
{
    private final MixinInfo mixin;
    private final String prefix;
    private final Type iface;
    private final boolean unique;
    private Set<String> methods;
    
    private InterfaceInfo(final MixinInfo a1, final String a2, final Type a3, final boolean a4) {
        super();
        if (a2 == null || a2.length() < 2 || !a2.endsWith("$")) {
            throw new InvalidMixinException(a1, String.format("Prefix %s for iface %s is not valid", a2, a3.toString()));
        }
        this.mixin = a1;
        this.prefix = a2;
        this.iface = a3;
        this.unique = a4;
    }
    
    private void initMethods() {
        this.methods = new HashSet<String>();
        this.readInterface(this.iface.getInternalName());
    }
    
    private void readInterface(final String v-2) {
        final ClassInfo forName = ClassInfo.forName(v-2);
        for (final ClassInfo.Method a1 : forName.getMethods()) {
            this.methods.add(a1.toString());
        }
        for (final String v1 : forName.getInterfaces()) {
            this.readInterface(v1);
        }
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public Type getIface() {
        return this.iface;
    }
    
    public String getName() {
        return this.iface.getClassName();
    }
    
    public String getInternalName() {
        return this.iface.getInternalName();
    }
    
    public boolean isUnique() {
        return this.unique;
    }
    
    public boolean renameMethod(final MethodNode a1) {
        if (this.methods == null) {
            this.initMethods();
        }
        if (!a1.name.startsWith(this.prefix)) {
            if (this.methods.contains(a1.name + a1.desc)) {
                this.decorateUniqueMethod(a1);
            }
            return false;
        }
        final String v1 = a1.name.substring(this.prefix.length());
        final String v2 = v1 + a1.desc;
        if (!this.methods.contains(v2)) {
            throw new InvalidMixinException(this.mixin, String.format("%s does not exist in target interface %s", v1, this.getName()));
        }
        if ((a1.access & 0x1) == 0x0) {
            throw new InvalidMixinException(this.mixin, String.format("%s cannot implement %s because it is not visible", v1, this.getName()));
        }
        Annotations.setVisible(a1, MixinRenamed.class, "originalName", a1.name, "isInterfaceMember", true);
        this.decorateUniqueMethod(a1);
        a1.name = v1;
        return true;
    }
    
    private void decorateUniqueMethod(final MethodNode a1) {
        if (!this.unique) {
            return;
        }
        if (Annotations.getVisible(a1, Unique.class) == null) {
            Annotations.setVisible(a1, Unique.class, new Object[0]);
            this.mixin.getClassInfo().findMethod(a1).setUnique(true);
        }
    }
    
    static InterfaceInfo fromAnnotation(final MixinInfo a1, final AnnotationNode a2) {
        final String v1 = Annotations.getValue(a2, "prefix");
        final Type v2 = Annotations.getValue(a2, "iface");
        final Boolean v3 = Annotations.getValue(a2, "unique");
        if (v1 == null || v2 == null) {
            throw new InvalidMixinException(a1, String.format("@Interface annotation on %s is missing a required parameter", a1));
        }
        return new InterfaceInfo(a1, v1, v2, v3 != null && v3);
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (a1 == null || this.getClass() != a1.getClass()) {
            return false;
        }
        final InterfaceInfo v1 = (InterfaceInfo)a1;
        return this.mixin.equals(v1.mixin) && this.prefix.equals(v1.prefix) && this.iface.equals(v1.iface);
    }
    
    @Override
    public int hashCode() {
        int v1 = this.mixin.hashCode();
        v1 = 31 * v1 + this.prefix.hashCode();
        v1 = 31 * v1 + this.iface.hashCode();
        return v1;
    }
}
