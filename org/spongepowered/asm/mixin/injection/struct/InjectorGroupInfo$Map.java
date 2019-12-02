package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.lib.tree.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.throwables.*;

public static final class Map extends HashMap<String, InjectorGroupInfo>
{
    private static final long serialVersionUID = 1L;
    private static final InjectorGroupInfo NO_GROUP;
    
    public Map() {
        super();
    }
    
    @Override
    public InjectorGroupInfo get(final Object a1) {
        return this.forName(a1.toString());
    }
    
    public InjectorGroupInfo forName(final String a1) {
        InjectorGroupInfo v1 = super.get(a1);
        if (v1 == null) {
            v1 = new InjectorGroupInfo(a1);
            this.put(a1, v1);
        }
        return v1;
    }
    
    public InjectorGroupInfo parseGroup(final MethodNode a1, final String a2) {
        return this.parseGroup(Annotations.getInvisible(a1, Group.class), a2);
    }
    
    public InjectorGroupInfo parseGroup(final AnnotationNode a1, final String a2) {
        if (a1 == null) {
            return Map.NO_GROUP;
        }
        String v1 = Annotations.getValue(a1, "name");
        if (v1 == null || v1.isEmpty()) {
            v1 = a2;
        }
        final InjectorGroupInfo v2 = this.forName(v1);
        final Integer v3 = Annotations.getValue(a1, "min");
        if (v3 != null && v3 != -1) {
            v2.setMinRequired(v3);
        }
        final Integer v4 = Annotations.getValue(a1, "max");
        if (v4 != null && v4 != -1) {
            v2.setMaxAllowed(v4);
        }
        return v2;
    }
    
    public void validateAll() throws InjectionValidationException {
        for (final InjectorGroupInfo v1 : ((HashMap<K, InjectorGroupInfo>)this).values()) {
            v1.validate();
        }
    }
    
    @Override
    public /* bridge */ Object get(final Object a1) {
        return this.get(a1);
    }
    
    static {
        NO_GROUP = new InjectorGroupInfo("NONE", true);
    }
}
