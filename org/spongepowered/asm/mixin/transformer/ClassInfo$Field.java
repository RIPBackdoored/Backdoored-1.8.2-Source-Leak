package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.lib.tree.*;
import org.spongepowered.asm.util.*;
import java.lang.annotation.*;
import org.spongepowered.asm.mixin.*;

class Field extends Member
{
    final /* synthetic */ ClassInfo this$0;
    
    public Field(final ClassInfo a1, final Member a2) {
        this.this$0 = a1;
        super(a2);
    }
    
    public Field(final ClassInfo a1, final FieldNode a2) {
        this(a1, a2, false);
    }
    
    public Field(final ClassInfo v1, final FieldNode v2, final boolean v3) {
        this.this$0 = v1;
        super(Type.FIELD, v2.name, v2.desc, v2.access, v3);
        this.setUnique(Annotations.getVisible(v2, Unique.class) != null);
        if (Annotations.getVisible(v2, Shadow.class) != null) {
            final boolean a1 = Annotations.getVisible(v2, Final.class) != null;
            final boolean a2 = Annotations.getVisible(v2, Mutable.class) != null;
            this.setDecoratedFinal(a1, a2);
        }
    }
    
    public Field(final ClassInfo a1, final String a2, final String a3, final int a4) {
        this.this$0 = a1;
        super(Type.FIELD, a2, a3, a4, false);
    }
    
    public Field(final ClassInfo a1, final String a2, final String a3, final int a4, final boolean a5) {
        this.this$0 = a1;
        super(Type.FIELD, a2, a3, a4, a5);
    }
    
    @Override
    public ClassInfo getOwner() {
        return this.this$0;
    }
    
    @Override
    public boolean equals(final Object a1) {
        return a1 instanceof Field && super.equals(a1);
    }
    
    @Override
    protected String getDisplayFormat() {
        return "%s:%s";
    }
}
